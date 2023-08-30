package com.dnd.gooding.global.s3.controller.port;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import com.dnd.gooding.domain.user.domain.User;

public interface S3Service {

	void delete(String fileKey);
	String upload(MultipartFile profileImage, User user) throws IOException;
}
