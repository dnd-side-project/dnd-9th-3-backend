package com.dnd.gooding.domain.feed.controller.response;

import com.dnd.gooding.domain.feed.domain.Feed;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Schema(description = "피드 저장")
@Getter
@Builder
public class SaveScoreResponse {

	private String feedSave;
	private Integer feedScore;

	public static SaveScoreResponse from(Feed feed) {
		return SaveScoreResponse.builder()
			.feedSave(feed.getFeedSave())
			.feedScore(feed.getFeedScore())
			.build();
	}
}
