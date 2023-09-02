package com.dnd.gooding.global.s3.service;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.dnd.gooding.domain.file.controller.port.FileService;
import com.dnd.gooding.domain.file.domain.FileCreate;
import com.dnd.gooding.domain.user.domain.User;
import com.dnd.gooding.domain.user.infrastructure.UserEntity;
import com.dnd.gooding.global.common.enums.FileType;
import com.dnd.gooding.global.s3.controller.port.S3Service;
import com.dnd.gooding.global.s3.exception.IllegalArgumentS3Exception;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class S3ServiceImpl implements S3Service {

	private final AmazonS3Client amazonS3Client;
	private final FileService fileService;
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
	public String upload(MultipartFile profileImage, UserEntity userEntity) throws IOException {
		if (!profileImage.isEmpty()) {
			FileCreate fileCreate = upload(profileImage, FileType.images.name());
			return fileService.upload(fileCreate, userEntity);
		}
		return null;
	}

	private FileCreate upload(MultipartFile multipartFile, String dirName) throws IOException {
		FileCreate fileCreate = new FileCreate(environment, basicDir);

		File uploadFile = fileCreate.convert(multipartFile)  // 파일 변환할 수 없으면 에러
			.orElseThrow(() -> new IllegalArgumentS3Exception(multipartFile.getName()));

		String fileName = dirName + "/" + UUID.randomUUID() + uploadFile.getName();   // S3에 저장된 파일 이름
		String fileUrl = putS3(uploadFile, bucket, fileName); // s3로 업로드
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
