package com.dnd.gooding.domain.file.controller;

import java.io.IOException;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.dnd.gooding.global.s3.service.S3UploadService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "File", description = "파일 API")
@RestController
@RequestMapping("/api/v1/file")
@RequiredArgsConstructor
public class FileController {

	private final S3UploadService s3UploadService;

	@Operation(summary = "사용자를 검색한다.",
		responses = {
			@ApiResponse(responseCode = "200", description = "정상처리")
	})
	@PostMapping("/image-upload")
	@ResponseBody
	public String imageUpload(
		@RequestParam("oauthId") String ouathId,
		@RequestParam("data") MultipartFile multipartFile) throws IOException {
		return s3UploadService.upload(multipartFile, "dnd9th-3", "image", ouathId);
	}

	@PostMapping("/video-upload")
	@ResponseBody
	public String videoUpload(
		@RequestParam("oauthId") String ouathId,
		@RequestParam("data") MultipartFile multipartFile) throws IOException {
		return s3UploadService.upload(multipartFile, "dnd9th-3", "video", ouathId);
	}
}
