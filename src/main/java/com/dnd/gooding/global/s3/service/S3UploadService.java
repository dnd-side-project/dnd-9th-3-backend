package com.dnd.gooding.global.s3.service;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.dnd.gooding.domain.file.dto.FileCreate;
import com.dnd.gooding.domain.file.service.FileService;
import com.dnd.gooding.global.s3.exception.IllegalArgumentS3Exception;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class S3UploadService {
	private final Logger logger = LoggerFactory.getLogger(S3UploadService.class);

	private final AmazonS3Client amazonS3Client;
	private final FileService fileService;
	private final String BUCKET = "dnd9th-3";

	@Value("${spring.environment}")
	private String environment;
	@Value("${spring.file-dir}")
	private String basicDir;

	public void upload(List<MultipartFile> files, String dirName, String oauthId) throws IOException {
		for(MultipartFile file : files) {
			FileCreate fileCreate = upload(file, dirName);
		}
	}

	private FileCreate upload(MultipartFile multipartFile, String dirName) throws IOException {
		FileCreate fileCreate = new FileCreate(environment, basicDir);

		File uploadFile = fileCreate.convert(multipartFile)  // 파일 변환할 수 없으면 에러
			.orElseThrow(() -> new IllegalArgumentS3Exception(multipartFile.getName()));

		String fileName = dirName + "/" + UUID.randomUUID() + uploadFile.getName();   // S3에 저장된 파일 이름
		String fileUrl = putS3(uploadFile, BUCKET, fileName); // s3로 업로드
		removeNewFile(uploadFile);
		return fileCreate.create(fileUrl);
	}

	/**
	 * S3로 업로드
	 * @param uploadFile
	 * @param bucket
	 * @param fileName
	 * @return
	 */
	private String putS3(File uploadFile, String bucket, String fileName) {
		amazonS3Client.putObject(new PutObjectRequest(bucket, fileName, uploadFile).withCannedAcl(
			CannedAccessControlList.PublicRead));
		return amazonS3Client.getUrl(bucket, fileName).toString();
	}

	private void removeNewFile(File targetFile) {
		if(targetFile.delete()) {
			logger.info("[S3UploadService] removeNewFile : File delete success");
			return;
		}
		logger.info("[S3UploadService] removeNewFile : File delete fail");
	}
}
