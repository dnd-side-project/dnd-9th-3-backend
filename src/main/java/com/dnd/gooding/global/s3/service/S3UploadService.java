package com.dnd.gooding.global.s3.service;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import javax.imageio.ImageIO;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.dnd.gooding.domain.record.service.RecordService;
import com.dnd.gooding.domain.user.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.dnd.gooding.domain.file.dto.FileCreate;
import com.dnd.gooding.domain.file.service.FileService;
import com.dnd.gooding.domain.record.model.Record;
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
	private final RecordService recordService;
	@Value("${cloud.aws.s3.bucket}")
	private String bucket;
	@Value("${spring.environment}")
	private String environment;
	@Value("${spring.file-dir}")
	private String basicDir;

	public String userImageUpload(MultipartFile profileImage, User user) throws IOException {
		if (!profileImage.isEmpty()) {
			FileCreate fileCreate = upload(profileImage, "images");
			return fileService.upload(fileCreate, user);
		}
		return null;
	}

	public void thumbnailUpload(MultipartFile thumbnail, String thumbnailExtension, Record record) throws IOException {
		if(!thumbnail.isEmpty()) {
			FileCreate fileCreate = upload(thumbnail, thumbnailExtension);
			fileService.upload(fileCreate, record);
			recordService.thumbnailUpdate(record.getId(), fileCreate.getFileUrl());
		}
	}

	public void upload(List<MultipartFile> files, String dirName, Record record) throws IOException {
		for(MultipartFile file : files) {
			if(!file.isEmpty()) {
				FileCreate fileCreate = upload(file, dirName);
				fileService.upload(fileCreate, record);
			}
		}
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

	private File imageThumbnail(File uploadFile, String tPath) throws IOException {
		File tFile = new File(tPath);
		double ratio = 2;
		BufferedImage oImage = ImageIO.read(uploadFile);
		int tWidth = (int)(oImage.getWidth() / ratio);
		int tHeight = (int)(oImage.getHeight() / ratio);

		BufferedImage tImage = new BufferedImage(tWidth, tHeight, BufferedImage.TYPE_3BYTE_BGR);
		Graphics2D graphics2D = tImage.createGraphics();
		Image image = oImage.getScaledInstance(tWidth, tHeight, Image.SCALE_SMOOTH);
		graphics2D.drawImage(image, 0, 0, tWidth, tHeight, null);
		graphics2D.dispose();

		ImageIO.write(tImage, "jpg", tFile);
		return tFile;
	}
}
