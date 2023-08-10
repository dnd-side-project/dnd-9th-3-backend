package com.dnd.gooding.domain.record.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.dnd.gooding.domain.record.dto.request.UploadRequest;
import com.dnd.gooding.domain.record.service.RecordService;
import com.dnd.gooding.global.common.dto.ErrorResponse;
import com.dnd.gooding.global.s3.service.S3UploadService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "Record", description = "기록 API")
@RestController
@RequestMapping("/api/v1/record")
@RequiredArgsConstructor
public class RecordController {

	private final RecordService recordService;
	private final S3UploadService s3UploadService;

	@Operation(summary = "기록 내용을 업로드한다.",
		responses = {
			@ApiResponse(responseCode = "201", description = "정상처리"),
			@ApiResponse(responseCode = "400", description = "파일 변환에 실패했습니다.",
				content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
		})
	@PostMapping(value = "/upload")
	public ResponseEntity<Void> upload(
		@RequestPart("images") List<MultipartFile> images,
		@RequestPart("videos") List<MultipartFile> videos,
		@RequestParam("oauthId") String oauthId,
		@ModelAttribute("uploadRequest") UploadRequest uploadRequest
	) throws IOException {
		// Record record = recordService.create(oauthId, uploadRequest);
		// s3UploadService.upload(images, "images", record);
		// s3UploadService.upload(videos, "videos", record);
		System.out.println(uploadRequest);
		return ResponseEntity
			.status(HttpStatus.CREATED)
			.build();
	}
}
