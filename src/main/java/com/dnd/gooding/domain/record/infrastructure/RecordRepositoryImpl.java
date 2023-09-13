package com.dnd.gooding.domain.record.infrastructure;

import static com.dnd.gooding.domain.file.infrastructure.QFileEntity.*;
import static com.dnd.gooding.domain.onboard.infrastructure.QOnboardEntity.*;
import static com.dnd.gooding.domain.record.infrastructure.QRecordEntity.*;
import static com.dnd.gooding.domain.user.infrastructure.QUserEntity.*;
import static java.util.stream.Collectors.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import com.dnd.gooding.domain.record.domain.Record;
import com.dnd.gooding.domain.record.exception.RecordNotFoundException;
import com.dnd.gooding.domain.record.service.port.RecordRepository;
import com.querydsl.core.types.ConstantImpl;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.StringTemplate;
import com.querydsl.jpa.impl.JPAQueryFactory;

@Repository
public class RecordRepositoryImpl implements RecordRepository {

	private final EntityManager em;
	private final JPAQueryFactory queryFactory;
	private final RecordJpaRepository recordJpaRepository;

	public RecordRepositoryImpl(EntityManager em, RecordJpaRepository recordJpaRepository) {
		this.em = em;
		this.queryFactory = new JPAQueryFactory(em);
		this.recordJpaRepository = recordJpaRepository;
	}

	@Override
	public List<Record> findByUserId(Long userId) {
		List<RecordEntity> recordEntities = Optional.ofNullable(queryFactory
			.select(recordEntity).distinct()
			.from(recordEntity)
			.join(recordEntity.userEntity, userEntity).fetchJoin()
			.join(fileEntity)
				.on(recordEntity.id.eq(fileEntity.recordEntity.id)
				.and(recordEntity.userEntity.id.eq(fileEntity.userEntity.id))).fetchJoin()
			.leftJoin(userEntity.onboards, onboardEntity)
			.orderBy(
				recordEntity.lastModifiedDate.desc()
			)
			.where(userIdEquals(userId))
			.fetch())
			.orElseGet(ArrayList::new);
		return recordEntities.stream().map(RecordEntity::toModel).collect(toList());
	}

	@Override
	public List<Record> findByUserIdAndRecordDate(Long userId, String recordDate) {
		List<RecordEntity> recordEntities = Optional.ofNullable(queryFactory
			.select(recordEntity).distinct()
			.from(recordEntity)
			.join(recordEntity.files, fileEntity).fetchJoin()
			.where(userIdEquals(userId), recordDateEquals(recordDate))
			.orderBy(
				recordEntity.lastModifiedDate.desc()
			)
			.fetch())
			.orElseGet(ArrayList::new);
		return recordEntities.stream().map(RecordEntity::toModel).collect(toList());
	}

	@Override
	public Record findByUserIdAndRecordId(Long userId, Long recordId) {
		return Optional.ofNullable(queryFactory
			.select(recordEntity).distinct()
			.from(recordEntity)
			.join(recordEntity.userEntity, userEntity).fetchJoin()
			.join(fileEntity)
				.on(recordEntity.id.eq(fileEntity.recordEntity.id)
				.and(recordEntity.userEntity.id.eq(fileEntity.userEntity.id))).fetchJoin()
			.leftJoin(userEntity.onboards, onboardEntity)
			.where(userIdEquals(userId), recordIdEquals(recordId))
			.fetchOne())
			.orElseThrow(() -> new RecordNotFoundException(recordId)).toModel();
	}

	@Override
	public Record save(Record record) {
		return recordJpaRepository.save(RecordEntity.from(record)).toModel();
	}

	@Override
	public void delete(Record record) {
		recordJpaRepository.delete(RecordEntity.from(record));
	}

	private BooleanExpression recordDateEquals(String recordDate) {
		StringTemplate formatDate = Expressions.stringTemplate(
			"DATE_FORMAT({0}, {1})",
			recordEntity.recordDate,
			ConstantImpl.create("%Y%m")
		);
		return formatDate.eq(recordDate);
	}

	private BooleanExpression userIdEquals(Long userId) {
		return recordEntity.userEntity.id.eq(userId);
	}

	private BooleanExpression recordIdEquals(Long recordId) {
		return recordEntity.id.eq(recordId);
	}
}
