package com.dnd.gooding.domain.file.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dnd.gooding.domain.file.controller.port.FileService;
import com.dnd.gooding.domain.file.domain.File;
import com.dnd.gooding.domain.file.domain.FileCreate;
import com.dnd.gooding.domain.file.service.port.FileRepository;
import com.dnd.gooding.domain.record.domain.Record;
import com.dnd.gooding.domain.user.domain.User;

import lombok.Builder;
import lombok.RequiredArgsConstructor;

@Builder
@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {

	private final FileRepository fileRepository;

	@Override
	public String upload(FileCreate fileCreate, User user) {
		File file = File.create(fileCreate, user);
		return null;
	}

	@Transactional
	@Override
	public Record upload(FileCreate fileCreate, Record record) {
		File file = File.create(fileCreate, record);
		file = fileRepository.save(file);
		record = record.changeFile(file);
		return record;
	}
}
