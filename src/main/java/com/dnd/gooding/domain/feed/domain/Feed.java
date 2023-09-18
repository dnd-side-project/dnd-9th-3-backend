package com.dnd.gooding.domain.feed.domain;

import com.dnd.gooding.domain.record.domain.Record;
import com.dnd.gooding.domain.user.domain.User;

import lombok.Builder;
import lombok.Getter;

@Getter
public class Feed {

	private final Long id;
	private final Long saveUserId;
	private final User user;
	private final Record record;
	private final Integer feedScore;
	private final String feedSave;

	public Feed() {
		this.id = null;
		this.saveUserId = null;
		this.user = null;
		this.record = null;
		this.feedScore = 0;
		this.feedSave = "N";
	}

	@Builder
	public Feed(Long id, Long saveUserId, User user, Record record, Integer feedScore,
		String feedSave) {
		this.id = id;
		this.saveUserId = saveUserId;
		this.user = user;
		this.record = record;
		this.feedScore = feedScore;
		this.feedSave = feedSave;
	}

	public Feed update(String feedSave, Integer feedScore) {
		return Feed.builder()
			.id(id)
			.saveUserId(saveUserId)
			.user(user)
			.record(record)
			.feedScore(feedScore)
			.feedSave(feedSave)
			.build();
	}

	public static Feed create(User user, Record record, Long saveUserId, String feedSave, Integer feedScore) {
		return Feed.builder()
			.saveUserId(saveUserId)
			.user(user)
			.record(record)
			.feedScore(feedScore)
			.feedSave(feedSave)
			.build();
	}
}
