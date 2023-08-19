package com.dnd.gooding.domain.file.service;

import com.dnd.gooding.domain.user.model.User;
import org.springframework.stereotype.Service;

import com.dnd.gooding.domain.file.dto.FileCreate;
import com.dnd.gooding.domain.file.model.File;
import com.dnd.gooding.domain.file.repository.FileRepository;
import com.dnd.gooding.domain.record.model.Record;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {

	private final FileRepository fileRepository;

	@Override
	public String upload(FileCreate fileCreate, User user) {
		File file = File.create(fileCreate, user);
		fileRepository.save(file);
		return file.getFileUrl();
	}

	@Override
	public void upload(FileCreate fileCreate, Record record) {
		File file = File.create(fileCreate, record);
		fileRepository.save(file);
	}
}
