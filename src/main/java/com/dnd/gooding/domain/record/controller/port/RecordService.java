package com.dnd.gooding.domain.record.controller.port;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.dnd.gooding.domain.record.controller.request.UploadRequest;
import com.dnd.gooding.domain.record.domain.Record;

public interface RecordService {

	List<Record> findByUserId(Long userId);
	List<Record> findByUserIdAndRecordDate(Long userId, String recordDate);
	Record create(String oauthId, UploadRequest uploadRequest);
	Record findByUserIdAndRecordId(Long userId, Long recordId);
	void upload(List<MultipartFile> files, Record record) throws IOException;
	Record updateThumbnailUrl(MultipartFile thumbnail, Record record) throws IOException;
	Record save(Record record);
	List<Record> findByFeedSave(Long saveUserId);
	void delete(Record record);
}
