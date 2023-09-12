package com.dnd.gooding.domain.record.service.port;

import java.util.List;

import com.dnd.gooding.domain.record.domain.Record;

public interface RecordRepository {

	List<Record> findByUserId(Long userId);
	Record findByUserIdAndRecordId(Long userId, Long recordId);
	Record save(Record record);
	void delete(Record record);
}
