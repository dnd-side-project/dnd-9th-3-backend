package com.dnd.gooding.domain.file.controller.port;

import com.dnd.gooding.domain.file.domain.FileCreate;
import com.dnd.gooding.domain.user.infrastructure.UserEntity;

public interface FileService {
	String upload(FileCreate fileCreate, UserEntity userEntity);
	void upload(FileCreate fileCreate, Record record);
	void thumbnailUpdate(Long recordId, String thumbnailUrl);
}
