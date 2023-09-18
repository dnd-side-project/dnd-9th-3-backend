package com.dnd.gooding.domain.file.controller.port;

import com.dnd.gooding.domain.file.domain.FileCreate;
import com.dnd.gooding.domain.record.domain.Record;
import com.dnd.gooding.domain.user.domain.User;

public interface FileService {
	String upload(FileCreate fileCreate, User user);
	Record upload(FileCreate fileCreate, Record record);
}
