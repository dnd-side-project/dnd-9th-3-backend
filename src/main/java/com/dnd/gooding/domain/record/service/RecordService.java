package com.dnd.gooding.domain.record.service;

import com.dnd.gooding.domain.record.dto.request.UploadRequest;
import com.dnd.gooding.domain.record.model.Record;

public interface RecordService {

	Record create(String oauthId, UploadRequest uploadRequest);
}
