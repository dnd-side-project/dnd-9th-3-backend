package com.dnd.gooding.record.query;

import java.util.List;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.repository.Repository;

public interface RecordDataDao extends Repository<RecordData, String> {

  RecordData findById(String recordNumber);

  List<RecordData> findAll(Specification<RecordData> spec);
}
