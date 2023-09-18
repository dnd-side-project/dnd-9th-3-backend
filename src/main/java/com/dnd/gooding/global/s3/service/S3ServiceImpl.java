package com.dnd.gooding.global.s3.service;

import java.io.File;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.dnd.gooding.domain.file.domain.FileCreate;
import com.dnd.gooding.global.s3.controller.port.S3Service;
import com.dnd.gooding.global.s3.exception.IllegalArgumentS3Exception;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class S3ServiceImpl implements S3Service {
	private final AmazonS3Client amazonS3Client;
	@Value("${cloud.aws.s3.bucket}")
	private String bucket;
	@Value("${spring.environment}")
	private String environment;
	@Value("${spring.file-dir}")
	private String basicDir;

	@Override
	public void delete(String fileKey) {
		DeleteObjectRequest deleteObjectRequest = new DeleteObjectRequest(bucket, fileKey);
		amazonS3Client.deleteObject(deleteObjectRequest);
	}

	@Override
	public FileCreate upload(MultipartFile multipartFile) throws IOException {
		FileCreate fileCreate = new FileCreate(environment, basicDir);
		File uploadFile = fileCreate.convert(multipartFile)  // 파일 변환할 수 없으면 에러
			.orElseThrow(() -> new IllegalArgumentS3Exception(multipartFile.getName()));
  		// S3에 저장된 파일 이름
		String fileUrl = putS3(uploadFile, bucket, uploadFile.getName()); // s3로 업로드
		removeNewFile(uploadFile);
		return fileCreate.create(fileUrl);
	}

	private String putS3(File uploadFile, String bucket, String fileName) {
		amazonS3Client.putObject(new PutObjectRequest(bucket, fileName, uploadFile).withCannedAcl(
			CannedAccessControlList.PublicRead));
		return amazonS3Client.getUrl(bucket, fileName).toString();
	}

	private void removeNewFile(File targetFile) {
		if(targetFile.delete()) {
			log.info("[S3UploadService] removeNewFile : File delete success");
			return;
		}
		log.info("[S3UploadService] removeNewFile : File delete fail");
	}
}
