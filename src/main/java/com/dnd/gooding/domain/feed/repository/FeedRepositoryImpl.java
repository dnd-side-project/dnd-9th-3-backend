package com.dnd.gooding.domain.feed.repository;

import static com.dnd.gooding.domain.file.model.QFile.*;
import static com.dnd.gooding.domain.record.model.QRecord.*;
import static com.dnd.gooding.domain.user.model.QUser.*;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.dnd.gooding.domain.feed.model.Feed;
import com.dnd.gooding.domain.record.model.Record;
import com.dnd.gooding.global.common.repository.Querydsl4RepositorySupport;
import com.querydsl.core.types.dsl.BooleanExpression;

public class FeedRepositoryImpl extends Querydsl4RepositorySupport implements FeedRepositoryCustom {

	public FeedRepositoryImpl() {
		super(Feed.class);
	}

	@Override
	public Page<Record> findByRecordIsNotUserId(Long userId, Pageable pageable) {
		return applyPagination(pageable, contentQuery -> contentQuery
			.select(record).distinct()
			.from(record)
			.join(record.user, user).fetchJoin()
			.join(record.files, file).fetchJoin()
			.where(userIdNotEquals(userId))
			.orderBy(
				record.createdDate.desc()
			), countQuery -> countQuery
			.select(record).distinct()
			.from(record)
			.join(record.user, user).fetchJoin()
			.join(record.files, file).fetchJoin()
			.where(userIdNotEquals(userId))
			.orderBy(
				record.createdDate.desc()
			)
		);
	}

	private BooleanExpression userIdNotEquals(Long userId) {
		return record.user.id.ne(userId);
	}
}
