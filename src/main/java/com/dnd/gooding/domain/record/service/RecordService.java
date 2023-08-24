package com.dnd.gooding.domain.record.service;

import com.dnd.gooding.domain.record.dto.request.UploadRequest;
import com.dnd.gooding.domain.record.dto.response.MyRecordResponse;
import com.dnd.gooding.domain.record.model.Record;

import java.util.List;

public interface RecordService {

	List<MyRecordResponse> findByUserId(Long userId);
	List<MyRecordResponse> findRecordByDate(Long userId, String recordDate);
	List<MyRecordResponse> findFeedBySave(Long saveUserId);
	Record findByRecordId(Long userId, Long recordId);
	Record create(String oauthId, UploadRequest uploadRequest);
	void delete(Record record);
}
