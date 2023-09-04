package com.dnd.gooding.domain.file.service;

import org.springframework.stereotype.Service;

import com.dnd.gooding.domain.file.controller.port.FileService;
import com.dnd.gooding.domain.file.domain.FileCreate;
import com.dnd.gooding.domain.user.domain.User;

import lombok.Builder;

@Service
@Builder
public class FileServiceImpl implements FileService {

	@Override
	public String upload(FileCreate fileCreate, User user) {
		return null;
	}

	@Override
	public void upload(FileCreate fileCreate, Record record) {

	}

	@Override
	public void thumbnailUpdate(Long recordId, String thumbnailUrl) {

	}
}
