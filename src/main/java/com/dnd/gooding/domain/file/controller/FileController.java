package com.dnd.gooding.domain.file.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.dnd.gooding.global.common.dto.ErrorResponse;
import com.dnd.gooding.global.s3.service.S3UploadService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "File", description = "파일 API")
@RestController
@RequiredArgsConstructor
public class FileController {

	private final S3UploadService s3UploadService;

	@Operation(summary = "기록 내용을 업로드한다.",
		responses = {
			@ApiResponse(responseCode = "201", description = "정상처리"),
			@ApiResponse(responseCode = "400", description = "파일 변환에 실패했습니다.",
				content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
	})
	@PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<Void> upload(
		@RequestPart("images") List<MultipartFile> images,
		@RequestPart("videos") List<MultipartFile> videos,
		@RequestParam("oauthId") String oauthId) throws IOException {
		s3UploadService.upload(images, "images", oauthId);
		s3UploadService.upload(videos, "videos", oauthId);
		return ResponseEntity
			.status(HttpStatus.CREATED)
			.build();
	}

	// @PostMapping("/video-upload")
	// public String videoUpload(
	// 	@RequestParam("oauthId") String oauthId,
	// 	@RequestParam("data") MultipartFile multipartFile) throws IOException {
	// 	FileCreate fileCreate = s3UploadService.upload(multipartFile);
	// 	return fileCreate.getFileUrl();
	// }
}
