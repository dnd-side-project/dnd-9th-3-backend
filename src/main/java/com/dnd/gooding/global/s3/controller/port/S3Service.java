package com.dnd.gooding.global.s3.controller.port;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import com.dnd.gooding.domain.file.domain.FileCreate;

public interface S3Service {
	void delete(String fileKey);
	FileCreate upload(MultipartFile multipartFile) throws IOException;
}
