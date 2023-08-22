package com.dnd.gooding.domain.feed.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dnd.gooding.domain.feed.dto.FeedResponse;
import com.dnd.gooding.domain.feed.service.FeedService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "Feed", description = "피드 API")
@RestController
@RequestMapping("/api/v1/feed")
@RequiredArgsConstructor
public class FeedController {

	private final FeedService feedService;

	@Operation(summary = "피드를 조회한다.",
		responses = {
			@ApiResponse(responseCode = "200", description = "정상처리")
		})
	@GetMapping(value = "/{userId}")
	public ResponseEntity<List<FeedResponse>> feed(
		@PathVariable Long userId, Pageable pageable) {
		return ResponseEntity
			.ok()
			.body(feedService.findByRecordIsNotUserId(userId, pageable));
	}
}
