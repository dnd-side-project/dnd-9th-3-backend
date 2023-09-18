package com.dnd.gooding.domain.feed.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dnd.gooding.domain.feed.controller.port.FeedService;
import com.dnd.gooding.domain.feed.controller.response.FeedResponse;
import com.dnd.gooding.domain.feed.controller.response.SaveScoreResponse;
import com.dnd.gooding.domain.feed.domain.Feed;
import com.dnd.gooding.global.common.enums.InterestType;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Builder;
import lombok.RequiredArgsConstructor;

@Tag(name = "Feed", description = "피드 API")
@RestController
@RequestMapping("/api/v1/feed")
@Builder
@RequiredArgsConstructor
public class FeedController {

	private final FeedService feedService;

	@Operation(summary = "로그인한 사용자의 관심사로 피드를 조회한다.",
		responses = {
			@ApiResponse(responseCode = "200", description = "정상처리")
	})
	@Parameters({
		@Parameter(name = "userId", description = "로그인한 사용자 ID", required = true),
		@Parameter(name = "interestCodes", description = "로그인한 사용자 관심사"),
		@Parameter(name = "page", description = "페이지 번호"),
		@Parameter(name = "size", description = "페이지 크기")
	})
	@GetMapping(value = "/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Page<FeedResponse>> findByInterestAndIsNotUserId(
		@PathVariable Long userId,
		@Parameter(hidden = true) @RequestParam(name = "interestCodes") List<InterestType> interestCodes,
		@Parameter(hidden = true) Pageable pageable) {
		return ResponseEntity
			.ok()
			.body(feedService.findByInterestAndIsNotUserId(userId, interestCodes, pageable));
	}

	@Operation(summary = "피드의 저장과 점수를 조회한다.",
		responses = {
			@ApiResponse(responseCode = "200", description = "정상처리")
	})
	@GetMapping(value = "/{recordUserId}/{recordId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<SaveScoreResponse> findByUserIdAndRecordId(
		@PathVariable Long recordUserId, @PathVariable Long recordId,
		@RequestParam Long saveUserId) {
		Feed feed = feedService.findByUserIdAndRecordId(recordUserId, recordId, saveUserId);
		return ResponseEntity
			.ok()
			.body(SaveScoreResponse.from(feed));
	}

	@Operation(summary = "다른 사람의 피드를 저장한다.",
		responses = {
			@ApiResponse(responseCode = "204", description = "정상처리")
	})
	@PostMapping(value = "/{recordUserId}/{recordId}", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public ResponseEntity<Void> save(
		@PathVariable Long recordUserId, @PathVariable Long recordId,
		Long saveUserId, String feedSave, Integer feedScore) {
		feedService.save(recordUserId, recordId, saveUserId, feedSave, feedScore);
		return ResponseEntity
			.status(HttpStatus.NO_CONTENT)
			.build();
	}
}
