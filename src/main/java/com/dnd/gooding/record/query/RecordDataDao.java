package com.dnd.gooding.record.query;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface RecordDataDao extends Repository<RecordData, String> {

    RecordData findById(String recordNumber);

    List<RecordData> findAll(Specification<RecordData> spec);
}
