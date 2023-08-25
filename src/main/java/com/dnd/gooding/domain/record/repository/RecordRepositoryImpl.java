package com.dnd.gooding.domain.record.repository;

import com.dnd.gooding.domain.record.model.Record;
import com.querydsl.core.types.ConstantImpl;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.StringTemplate;
import com.querydsl.jpa.impl.JPAQueryFactory;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

import static com.dnd.gooding.domain.feed.model.QFeed.*;
import static com.dnd.gooding.domain.file.model.QFile.file;
import static com.dnd.gooding.domain.record.model.QRecord.record;

public class RecordRepositoryImpl implements RecordRepositoryCustom {

    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    public RecordRepositoryImpl(EntityManager em) {
        this.em = em;
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Optional<List<Record>> findByUserId(Long id) {
        return Optional.ofNullable(queryFactory
                .select(record).distinct()
                .from(record)
                .join(record.files, file).fetchJoin()
                .orderBy(
                    record.lastModifiedDate.desc()
                )
                .where(userIdEquals(id))
                .fetch());
    }

    @Override
    public Optional<List<Record>> findRecordByDate(Long userId, String recordDate) {
        return Optional.ofNullable(queryFactory
                .select(record).distinct()
                .from(record)
                .join(record.files, file).fetchJoin()
                .where(userIdEquals(userId), recordDateEquals(recordDate))
                .orderBy(
                    record.lastModifiedDate.desc()
                )
                .fetch());
    }

    @Override
    public Optional<List<Record>> findFeedBySave(Long saveUserId) {
        return Optional.ofNullable(queryFactory
            .select(record).distinct()
            .from(feed)
            .join(record).on(feed.record.id.eq(record.id).and(feed.user.id.eq(record.user.id))).fetchJoin()
            .join(record.files, file).fetchJoin()
            .where(saveUserIdEquals(saveUserId), feedSaveEquals("Y"))
            .orderBy(
                record.lastModifiedDate.desc()
            )
            .fetch());
    }

    @Override
    public Record findByRecordId(Long userId, Long recordId) {
        return queryFactory
                .select(record).distinct()
                .from(record)
                .join(record.files, file).fetchJoin()
                .where(userIdEquals(userId), recordIdEquals(recordId))
                .fetchOne();
    }

    @Override
    public void thumbnailUpdate(Long recordId, String thumbnailUrl) {
        Long count = queryFactory
                .update(record)
                .set(record.thumbnailUrl, thumbnailUrl)
                .where(recordIdEquals(recordId))
                .execute();
        em.flush();
        em.clear();
    }

    private BooleanExpression recordDateEquals(String recordDate) {
        StringTemplate formatDate = Expressions.stringTemplate(
                "DATE_FORMAT({0}, {1})",
                record.recordDate,
                ConstantImpl.create("%Y%m")
        );
        return formatDate.eq(recordDate);
    }

    private BooleanExpression feedSaveEquals(String feedSave) {
        return feed.feedSave.eq(feedSave);
    }

    private BooleanExpression saveUserIdEquals(Long saveUserId) {
        return feed.saveUserId.eq(saveUserId);
    }

    private BooleanExpression recordIdEquals(Long recordId) {
        return record.id.eq(recordId);
    }

    private BooleanExpression userIdEquals(Long id) {
        return record.user.id.eq(id);
    }
}
