package com.dnd.gooding.domain.file.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

	@Transactional
	@Override
	public void upload(FileCreate fileCreate, Record record) {
		File file = File.create(fileCreate, record);
		fileRepository.save(file);
	}
}
