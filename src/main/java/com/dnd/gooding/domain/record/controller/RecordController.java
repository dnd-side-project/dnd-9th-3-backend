package com.dnd.gooding.domain.record.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dnd.gooding.domain.record.dto.RecordResponse;
import com.dnd.gooding.domain.record.service.RecordService;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "Record", description = "기록 API")
@RestController
@RequestMapping("/api/v1/record")
@RequiredArgsConstructor
public class RecordController {

	private final RecordService recordService;

	@GetMapping(value = "/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<RecordResponse> getById() {
		return null;
	}
}
