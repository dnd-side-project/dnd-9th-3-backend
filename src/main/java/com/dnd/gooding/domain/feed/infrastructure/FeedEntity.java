package com.dnd.gooding.domain.feed.infrastructure;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.dnd.gooding.domain.feed.domain.Feed;
import com.dnd.gooding.domain.record.infrastructure.RecordEntity;
import com.dnd.gooding.domain.user.infrastructure.UserEntity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "feed")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FeedEntity {

	@Id
	@Column(name = "feed_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "save_user_id")
	private Long saveUserId;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private UserEntity userEntity;

	@ManyToOne
	@JoinColumn(name = "record_id")
	private RecordEntity recordEntity;

	@Column(name = "feed_score", nullable = false)
	private Integer feedScore;

	@Column(name = "feed_save", nullable = false)
	private String feedSave;

	public static FeedEntity create(Feed feed) {
		FeedEntity feedEntity = new FeedEntity();
		feedEntity.id = feed.getId();
		feedEntity.saveUserId = feed.getSaveUserId();
		feedEntity.feedScore = feed.getFeedScore();
		feedEntity.feedSave = feed.getFeedSave();
		feedEntity.userEntity = UserEntity.from(feed.getUser());
		feedEntity.recordEntity = RecordEntity.from(feed.getRecord());
		return feedEntity;
	}

	public Feed toModel() {
		return Feed.builder()
			.id(id)
			.saveUserId(saveUserId)
			.user(userEntity.toModel())
			.record(recordEntity.toModel())
			.feedScore(feedScore)
			.feedSave(feedSave)
			.build();
	}
}
