package com.dnd.gooding.domain.record.service;

import com.dnd.gooding.domain.record.dto.request.UploadRequest;
import com.dnd.gooding.domain.record.dto.response.MyRecordResponse;
import com.dnd.gooding.domain.record.model.Record;

import java.util.List;

public interface RecordService {

	List<MyRecordResponse> findByUserId(Long userId);

	Record findByRecordId(Long recordId);
	Record create(String oauthId, UploadRequest uploadRequest);

	void thumbnailUpdate(Long recordId, String thumbnailUrl);
}
