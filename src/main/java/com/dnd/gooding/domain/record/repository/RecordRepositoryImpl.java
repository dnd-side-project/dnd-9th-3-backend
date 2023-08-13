package com.dnd.gooding.domain.record.repository;

import com.dnd.gooding.domain.record.model.Record;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

import static com.dnd.gooding.domain.file.model.QFile.file;
import static com.dnd.gooding.domain.record.model.QRecord.record;

public class RecordRepositoryImpl implements RecordRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public RecordRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Optional<List<Record>> findByUserId(Long id) {
        return Optional.ofNullable(queryFactory
                .select(record).distinct()
                .from(record)
                .join(record.files, file).fetchJoin()
                .where(userIdEquals(id))
                .fetch());
    }

    private BooleanExpression userIdEquals(Long id) {
        return record.user.id.eq(id);
    }
}
