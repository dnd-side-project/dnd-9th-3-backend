package com.dnd.gooding.domain.record.service.port;

import java.util.List;

import com.dnd.gooding.domain.record.domain.Record;

public interface RecordRepository {

	List<Record> findByUserId(Long userId);
	List<Record> findByUserIdAndRecordDate(Long userId, String recordDate);
	Record findByUserIdAndRecordId(Long userId, Long recordId);
	Record save(Record record);
	List<Record> findByFeedSave(Long saveUserId);
	void delete(Record record);
}
