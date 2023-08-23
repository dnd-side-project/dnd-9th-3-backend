package com.dnd.gooding.domain.feed.controller;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.dnd.gooding.domain.feed.dto.response.FeedResponse;
import com.dnd.gooding.domain.feed.service.FeedService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Tag(name = "Feed", description = "피드 API")
@RestController
@RequestMapping("/api/v1/feed")
@RequiredArgsConstructor
public class FeedController {

	private final FeedService feedService;

	@Operation(summary = "로그인한 사용자의 관심사로 피드를 조회한다.",
		responses = {
			@ApiResponse(responseCode = "200", description = "정상처리")
		})
	@Parameters({
		@Parameter(name = "userId", description = "로그인한 사용자 ID", required = true),
		@Parameter(name = "page", description = "페이지 번호"),
		@Parameter(name = "size", description = "페이지 크기")
	})
	@GetMapping(value = "/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Page<FeedResponse>> feed(
		@PathVariable Long userId,
		@RequestParam(name = "interestCodes") List<String> interestCodes,
		@Parameter(hidden = true) Pageable pageable) {
		return ResponseEntity
			.ok()
			.body(feedService.findByRecordByInterestCodeAndIsNotUserId(userId, interestCodes, pageable));
	}
}
