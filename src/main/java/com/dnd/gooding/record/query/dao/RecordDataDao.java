package com.dnd.gooding.record.query.dao;

import com.dnd.gooding.record.query.dto.RecordData;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.repository.Repository;

public interface RecordDataDao extends Repository<RecordData, String> {

    Optional<RecordData> findById(String recordNumber);

    List<RecordData> findByRecorderId(String recorderMemberId);

    List<RecordData> findAll(Specification<RecordData> spec);
}
