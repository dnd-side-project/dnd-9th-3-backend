package com.dnd.gooding.domain.file.service;

import com.dnd.gooding.domain.file.dto.FileCreate;
import com.dnd.gooding.domain.record.model.Record;
import com.dnd.gooding.domain.user.model.User;

public interface FileService {

	String upload(FileCreate fileCreate, User user);
	void upload(FileCreate fileCreate, Record record);
	void thumbnailUpdate(Long recordId, String thumbnailUrl);
}
