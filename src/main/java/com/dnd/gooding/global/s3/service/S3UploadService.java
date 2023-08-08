package com.dnd.gooding.global.s3.service;

import java.io.File;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class S3UploadService {

	private final Logger logger = LoggerFactory.getLogger(S3UploadService.class);

	@Value("${spring.environment}")
	private String environment;
	@Value("${spring.file-dir}")
	private String basicDir;
	private String fileDir;

	private final AmazonS3Client amazonS3Client;

	@PostConstruct
	private void init() {
		if ("local".equals(environment)) {
			this.fileDir = System.getProperty("user.dir") + this.basicDir;
		} else if ("development".equals(environment)) {
			this.fileDir = basicDir;
		}
	}

	public String upload(MultipartFile multipartFile, String bucket, String dirName, String ouathId) throws IOException {
		File uploadFile = convert(multipartFile, ouathId)
			.orElseThrow(() -> new IllegalArgumentException("error: MultipartFile -> File convert fail"));
		return upload(uploadFile, bucket, dirName);
	}

	/**
	 * S3로 파일 업로드
	 * @param uploadFile
	 * @param bucket
	 * @param dirName
	 * @return
	 */
	private String upload(File uploadFile, String bucket, String dirName) {
		String fileName = dirName + "/" + UUID.randomUUID() + uploadFile.getName();
		String uploadImageUrl = putS3(uploadFile, bucket, fileName);
		removeNewFile(uploadFile);
		return uploadImageUrl;
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

	/**
	 * 로컬에 파일 저장하기
	 * @param multipartFile
	 * @return
	 */
	private Optional<File> convert(MultipartFile multipartFile, String ouathId) throws IOException {
		if (multipartFile.isEmpty()) {
			return Optional.empty();
		}

		String originalFileName = multipartFile.getOriginalFilename();
		String storeFileName = createStoreFileName(originalFileName);

		File file = new File(fileDir + storeFileName);
		multipartFile.transferTo(file);

		return Optional.of(file);
	}

	/**
	 * 파일이름 생성
	 * @description 파일 이름이 이미 업로드된 파일들과 겹치지 않게 UUID를 사용한다.
	 * @param originalFileName
	 * @return
	 */
	private String createStoreFileName(String originalFileName) {
		String extension = extractExtension(originalFileName);
		String uuid = UUID.randomUUID().toString();
		return uuid + "." + extension;
	}

	/**
	 * 확장자 추출
	 * @description 사용자가 업로드한 파일에서 확장자를 추출한다.
	 * @param originalFileName
	 * @return
	 */
	private String extractExtension(String originalFileName) {
		int index = originalFileName.lastIndexOf(".");
		return originalFileName.substring(index + 1);
	}
}
