package com.dnd.gooding.domain.feed.repository;

import static com.dnd.gooding.domain.feed.model.QFeed.*;
import static com.dnd.gooding.domain.file.model.QFile.*;
import static com.dnd.gooding.domain.onboarding.model.QOnboarding.*;
import static com.dnd.gooding.domain.record.model.QRecord.*;
import static com.dnd.gooding.domain.user.model.QUser.*;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.dnd.gooding.domain.feed.model.Feed;
import com.dnd.gooding.domain.record.model.Record;
import com.dnd.gooding.global.common.model.InterestType;
import com.dnd.gooding.global.common.repository.Querydsl4RepositorySupport;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.springframework.util.ObjectUtils;

public class FeedRepositoryImpl extends Querydsl4RepositorySupport implements FeedRepositoryCustom {

	public FeedRepositoryImpl() {
		super(Feed.class);
	}

	@Override
	public Page<Record> findByRecordByInterestCodeAndIsNotUserId(Long userId, List<InterestType> interestCodes, Pageable pageable) {
		return applyPagination(pageable, contentQuery -> contentQuery
			.select(record).distinct()
			.from(record)
			.join(record.user, user).fetchJoin()
			.join(record.files, file).fetchJoin()
			.join(user.onboardings, onboarding)
			.where(userIdNotEquals(userId), interestTypeEquals(interestCodes))
			.orderBy(
				record.lastModifiedDate.desc()
			), countQuery -> countQuery
			.select(record).distinct()
			.from(record)
			.join(record.user, user).fetchJoin()
			.join(record.files, file).fetchJoin()
			.join(user.onboardings, onboarding)
			.where(userIdNotEquals(userId), interestTypeEquals(interestCodes))
		);
	}

	@Override
	public Feed findByUserIdAndRecordId(Long userId, Long recordId, Long saveUserId) {
		return select(feed)
			.from(feed)
			.where(userIdEquals(userId), recordIdEquals(recordId), saveUserIdEquals(saveUserId))
			.fetchOne();
	}

	private BooleanExpression userIdNotEquals(Long userId) {
		return record.user.id.ne(userId);
	}

	private BooleanExpression interestTypeEquals(List<InterestType> interestCodes) {
		if(ObjectUtils.isEmpty(interestCodes)) {
			return null;
		}
		return record.interestType.in(interestCodes);
	}

	private BooleanExpression userIdEquals(Long userId) {
		return feed.user.id.eq(userId);
	}

	private BooleanExpression recordIdEquals(Long recordId) {
		return feed.record.id.eq(recordId);
	}

	private BooleanExpression saveUserIdEquals(Long saveUserId) {
		return feed.saveUserId.eq(saveUserId);
	}
}
