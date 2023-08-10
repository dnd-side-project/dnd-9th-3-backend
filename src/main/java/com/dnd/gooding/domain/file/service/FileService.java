package com.dnd.gooding.domain.file.service;

import com.dnd.gooding.domain.file.dto.FileCreate;
import com.dnd.gooding.domain.record.model.Record;

public interface FileService {
	void upload(FileCreate fileCreate, Record record);
}
