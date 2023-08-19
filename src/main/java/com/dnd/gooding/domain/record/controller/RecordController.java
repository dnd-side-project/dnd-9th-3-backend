package com.dnd.gooding.domain.record.controller;

import java.io.IOException;
import java.util.List;

import com.dnd.gooding.domain.record.dto.response.MyRecordResponse;
import com.dnd.gooding.domain.record.model.Record;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
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

import javax.validation.Valid;

@Tag(name = "Record", description = "기록 API")
@RestController
@RequestMapping("/api/v1/record")
@RequiredArgsConstructor
public class RecordController {

	private final RecordService recordService;
	private final S3UploadService s3UploadService;

	@Operation(summary = "나의 기록 내용을 조회한다.",
			responses = {
					@ApiResponse(responseCode = "200", description = "정상처리"),
					@ApiResponse(responseCode = "404", description = "존재하지 않는 사용자입니다.",
							content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
			})
	@GetMapping(value = "/my-record", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<MyRecordResponse>> findRecordByUserId(
			@RequestParam("userId") Long userId) {
		return ResponseEntity
				.status(HttpStatus.OK)
				.body(recordService.findByUserId(userId));
	}

	@Operation(summary = "날짜별로 기록을 조회한다.",
			responses = {
					@ApiResponse(responseCode = "200", description = "정상처리"),
					@ApiResponse(responseCode = "404", description = "존재하지 않는 사용자입니다.",
							content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
			})
	@GetMapping(value = "/date", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<MyRecordResponse>> findRecordByDate(
			@RequestParam Long userId,
			@RequestParam String recordDate) {
		return ResponseEntity
				.status(HttpStatus.OK)
				.body(recordService.findRecordByDate(userId, recordDate));
	}

	@Transactional
	@Operation(summary = "기록 내용을 업로드한다.",
		responses = {
			@ApiResponse(responseCode = "201", description = "정상처리"),
			@ApiResponse(responseCode = "400", description = "파일 변환에 실패했습니다.",
				content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
		})
	@PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<Void> upload(
		@RequestPart(value = "thumbnail") MultipartFile thumbnail,
		@RequestPart("thumbnailDirectory") String thumbnailDirectory,
		@RequestPart(value = "images", required = false) List<MultipartFile> images,
		@RequestPart(value = "videos", required = false) List<MultipartFile> videos,
		@RequestPart("oauthId") @Valid String oauthId,
		@RequestPart("uploadRequest") UploadRequest uploadRequest
	) throws IOException {
		 Record record = recordService.create(oauthId, uploadRequest);
		 s3UploadService.thumbnailUpload(thumbnail, thumbnailDirectory, record);
		 record = recordService.findByRecordId(record.getId());
		 s3UploadService.upload(images, "images", record);
		 record = recordService.findByRecordId(record.getId());
		 s3UploadService.upload(videos, "videos", record);
		return ResponseEntity
			.status(HttpStatus.CREATED)
			.build();
	}
}
