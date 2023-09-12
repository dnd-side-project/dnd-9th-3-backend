package com.dnd.gooding.domain.record.controller;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.dnd.gooding.domain.file.domain.File;
import com.dnd.gooding.domain.record.controller.port.RecordService;
import com.dnd.gooding.domain.record.controller.request.UploadRequest;
import com.dnd.gooding.domain.record.controller.response.MyRecordResponse;
import com.dnd.gooding.domain.record.domain.Record;
import com.dnd.gooding.global.common.controller.response.ErrorResponse;
import com.dnd.gooding.global.s3.controller.port.S3Service;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Builder;
import lombok.RequiredArgsConstructor;

@Tag(name = "Record", description = "기록 API")
@RestController
@RequestMapping("/api/v1/record")
@Builder
@RequiredArgsConstructor
public class RecordController {

	private final S3Service s3Service;
	private final RecordService recordService;

	@Operation(summary = "나의 기록 내용을 조회한다.",
		responses = {
			@ApiResponse(responseCode = "200", description = "정상처리"),
			@ApiResponse(responseCode = "404", description = "존재하지 않는 사용자입니다.",
				content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
	})
	@GetMapping(value = "/my-record", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<MyRecordResponse>> findByUserId(
		@RequestParam("userId") Long userId) {
		return ResponseEntity
			.status(HttpStatus.OK)
			.body(recordService.findByUserId(userId).stream()
				.map(MyRecordResponse::from)
				.collect(Collectors.toList()));
	}

	@Operation(summary = "날짜별로 기록을 조회한다.",
		responses = {
			@ApiResponse(responseCode = "200", description = "정상처리"),
			@ApiResponse(responseCode = "404", description = "존재하지 않는 사용자입니다.",
				content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
	})
	@GetMapping(value = "/date", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<MyRecordResponse>> findByUserIdAndDate(
		@RequestParam Long userId,
		@Parameter(description = "기록날짜", example = "202308") @RequestParam String recordDate) {
		return ResponseEntity
			.status(HttpStatus.OK)
			.body(recordService.findByUserIdAndDate(userId, recordDate).stream()
				.map(MyRecordResponse::from)
				.collect(Collectors.toList()));
	}

	@Operation(summary = "기록 내용을 업로드한다.",
		responses = {
			@ApiResponse(responseCode = "201", description = "정상처리"),
			@ApiResponse(responseCode = "400", description = "파일 변환에 실패했습니다.",
				content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
	})
	@PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<Void> upload(
		@RequestPart("oauthId") @Valid String oauthId,
		@RequestPart(value = "thumbnail") MultipartFile thumbnail,
		@RequestPart(value = "files", required = false) List<MultipartFile> files,
		@RequestPart("uploadRequest") UploadRequest uploadRequest) throws IOException {
		Record record = recordService.create(oauthId, uploadRequest);
		record = recordService.updateThumbnailUrl(thumbnail, record);
		recordService.upload(files, record);
		return ResponseEntity
			.status(HttpStatus.CREATED)
			.build();
	}

	@Operation(summary = "기록을 삭제한다.",
		responses = {
			@ApiResponse(responseCode = "201", description = "정상처리")
	})
	@DeleteMapping(value = "/delete")
	public ResponseEntity<Void> delete(
		@RequestParam Long userId,
		@RequestParam Long recordId) {
		Record record = recordService.findByUserIdAndRecordId(userId, recordId);
		for(File file : record.getFiles()) {
			s3Service.delete(file.extractExtension(file.getFileUrl()));
		}
		recordService.delete(record);
		return ResponseEntity
			.status(HttpStatus.NO_CONTENT)
			.build();
	}
}
