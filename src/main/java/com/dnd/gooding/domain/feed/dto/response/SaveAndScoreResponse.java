package com.dnd.gooding.domain.feed.dto.response;

import com.dnd.gooding.domain.feed.model.Feed;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class SaveAndScoreResponse {

	private String feedSave;
	private Integer feedScore;

	public static SaveAndScoreResponse from(Feed feed) {
		return SaveAndScoreResponse.builder()
			.feedSave(feed.getFeedSave())
			.feedScore(feed.getFeedScore())
			.build();
	}
}
