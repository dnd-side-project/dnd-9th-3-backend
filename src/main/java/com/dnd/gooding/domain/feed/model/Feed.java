package com.dnd.gooding.domain.feed.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.dnd.gooding.domain.record.model.Record;
import com.dnd.gooding.domain.user.model.User;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "feed")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Feed {

	@Id
	@Column(name = "feed_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "save_user_id")
	private Long saveUserId;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	@ManyToOne
	@JoinColumn(name = "record_id")
	private Record record;

	@Column(name = "feed_score", nullable = false)
	private Integer feedScore;

	@Column(name = "feed_save", nullable = false)
	private String feedSave;

	public void changeFeedSave(String feedSave, Integer feedScore) {
		this.feedSave = feedSave;
		this.feedScore = feedScore;
	}

	public static Feed create(User user, Record record, Long saveUserId,
		String feedSave, Integer feedScore) {
		Feed feed = new Feed();
		feed.saveUserId = saveUserId;
		feed.feedScore = feedScore;
		feed.feedSave = feedSave;
		feed.user = user;
		feed.record = record;
		return feed;
	}
}
