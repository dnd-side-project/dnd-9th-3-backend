package com.dnd.gooding.record.query.dao;

import static com.dnd.gooding.record.command.domain.QImage.image;
import static com.dnd.gooding.record.command.domain.QRecord.record;

import com.dnd.gooding.record.command.domain.RecordNo;
import com.dnd.gooding.record.query.dto.RecordData;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import javax.persistence.EntityManager;

public class RecordDataDaoImpl implements RecordDataDaoCustom {

    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    public RecordDataDaoImpl(EntityManager em) {
        this.em = em;
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public RecordData findById(String recorderMemberId) {
        return null;
    }

    @Override
    public List<RecordData> findByRecorderId(String recorderMemberId) {
        return queryFactory
                .select(Projections.constructor(RecordData.class,
                        record.number.number,
                        record.coordinate.placeTitle,
                        record.coordinate.placeLatitude,
                        record.coordinate.placeLongitude,
                        record.recorder.memberId.id,
                        record.recorder.memberName,
                        record.title,
                        record.description,
                        record.images))
                .distinct()
                .from(record)
                .join(record.images, image)
                .where(recorderMemberIdEquals(recorderMemberId))
                .fetch();
    }

    private BooleanExpression recorderMemberIdEquals(String recorderMemberId) {
        return record.number.eq(RecordNo.of(recorderMemberId));
    }
}
