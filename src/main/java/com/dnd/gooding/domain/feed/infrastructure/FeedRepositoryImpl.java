package com.dnd.gooding.domain.feed.infrastructure;

import static com.dnd.gooding.domain.feed.infrastructure.QFeedEntity.*;
import static com.dnd.gooding.domain.file.infrastructure.QFileEntity.*;
import static com.dnd.gooding.domain.onboard.infrastructure.QOnboardEntity.*;
import static com.dnd.gooding.domain.record.infrastructure.QRecordEntity.*;
import static com.dnd.gooding.domain.user.infrastructure.QUserEntity.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.util.ObjectUtils;

import com.dnd.gooding.domain.feed.domain.Feed;
import com.dnd.gooding.domain.feed.service.port.FeedRepository;
import com.dnd.gooding.domain.record.domain.Record;
import com.dnd.gooding.domain.record.infrastructure.RecordEntity;
import com.dnd.gooding.global.common.enums.InterestType;
import com.dnd.gooding.global.common.repository.Querydsl4RepositorySupport;
import com.querydsl.core.types.dsl.BooleanExpression;

@Repository
public class FeedRepositoryImpl extends Querydsl4RepositorySupport implements FeedRepository {

	private final FeedJpaRepository feedJpaRepository;

	public FeedRepositoryImpl(FeedJpaRepository feedJpaRepository) {
		super(FeedEntity.class);
		this.feedJpaRepository = feedJpaRepository;
	}

	@Override
	public Page<Record> findByInterestAndIsNotUserId(Long userId, List<InterestType> interestCodes, Pageable pageable) {
		Page<RecordEntity> recordEntities = applyPagination(pageable, contentQuery -> contentQuery
			.select(recordEntity).distinct()
			.from(recordEntity)
			.join(recordEntity.userEntity, userEntity).fetchJoin()
			.join(fileEntity)
				.on(recordEntity.id.eq(fileEntity.recordEntity.id)
				.and(recordEntity.userEntity.id.eq(fileEntity.userEntity.id))).fetchJoin()
			.join(userEntity.onboards, onboardEntity)
			.where(userIdNotEquals(userId), interestTypeEquals(interestCodes))
			.orderBy(
				recordEntity.lastModifiedDate.desc()
			), countQuery -> countQuery
			.select(recordEntity).distinct()
			.from(recordEntity)
			.join(recordEntity.userEntity, userEntity).fetchJoin()
			.join(fileEntity)
				.on(recordEntity.id.eq(fileEntity.recordEntity.id)
				.and(recordEntity.userEntity.id.eq(fileEntity.userEntity.id))).fetchJoin()
			.leftJoin(userEntity.onboards, onboardEntity)
			.where(userIdNotEquals(userId), interestTypeEquals(interestCodes))
			.orderBy(
				recordEntity.lastModifiedDate.desc()
			)
		);
		return new PageImpl<>(recordEntities.stream()
			.map(RecordEntity::toModel)
			.collect(Collectors.toList()),
			pageable, recordEntities.getTotalElements());
	}

	@Override
	public Optional<Feed> findByUserIdAndRecordId(Long userId, Long recordId, Long saveUserId) {
		return Optional.ofNullable(
			select(feedEntity)
			.from(feedEntity)
			.where(userIdEquals(userId), recordIdEquals(recordId), saveUserIdEquals(saveUserId))
			.fetchOne())
			.map(FeedEntity::toModel);
	}

	@Override
	public Feed save(Feed feed) {
		return feedJpaRepository.save(FeedEntity.create(feed)).toModel();
	}

	private BooleanExpression userIdNotEquals(Long userId) {
		return recordEntity.userEntity.id.ne(userId);
	}

	private BooleanExpression interestTypeEquals(List<InterestType> interestCodes) {
		if(ObjectUtils.isEmpty(interestCodes)) {
			return null;
		}
		return recordEntity.interestType.in(interestCodes);
	}

	private BooleanExpression userIdEquals(Long userId) {
		return feedEntity.userEntity.id.eq(userId);
	}

	private BooleanExpression recordIdEquals(Long recordId) {
		return feedEntity.recordEntity.id.eq(recordId);
	}

	private BooleanExpression saveUserIdEquals(Long saveUserId) {
		return feedEntity.saveUserId.eq(saveUserId);
	}
}
