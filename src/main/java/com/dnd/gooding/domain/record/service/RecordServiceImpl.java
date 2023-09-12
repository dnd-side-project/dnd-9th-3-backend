package com.dnd.gooding.domain.record.service;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.dnd.gooding.domain.file.controller.port.FileService;
import com.dnd.gooding.domain.file.domain.File;
import com.dnd.gooding.domain.file.domain.FileCreate;
import com.dnd.gooding.domain.record.controller.port.RecordService;
import com.dnd.gooding.domain.record.controller.request.UploadRequest;
import com.dnd.gooding.domain.record.domain.Record;
import com.dnd.gooding.domain.record.service.port.RecordRepository;
import com.dnd.gooding.domain.user.domain.User;
import com.dnd.gooding.domain.user.exception.UserNotFoundException;
import com.dnd.gooding.domain.user.service.port.UserRepository;
import com.dnd.gooding.global.s3.controller.port.S3Service;

import lombok.Builder;
import lombok.RequiredArgsConstructor;

@Builder
@Service
@RequiredArgsConstructor
public class RecordServiceImpl implements RecordService {

	private final RecordRepository recordRepository;
	private final UserRepository userRepository;
	private final FileService fileService;
	private final S3Service s3Service;

	@Override
	public List<Record> findByUserId(Long userId) {
		return recordRepository.findByUserId(userId);
	}

	@Override
	public List<Record> findByUserIdAndDate(Long userId, String recordDate) {
		return null;
	}

	@Transactional
	@Override
	public Record create(String oauthId, UploadRequest uploadRequest) {
		User user = userRepository.findByOauthId(oauthId)
			.orElseThrow(() -> new UserNotFoundException(oauthId));
		Record record = Record.create(uploadRequest, user);
		record = recordRepository.save(record);
		return record;
	}

	@Override
	public Record findByUserIdAndRecordId(Long userId, Long recordId) {
		return recordRepository.findByUserIdAndRecordId(userId, recordId);
	}

	@Transactional
	@Override
	public void upload(List<MultipartFile> files, Record record) throws IOException {
		for(MultipartFile file : files) {
			if (!file.isEmpty()) {
				FileCreate fileCreate = s3Service.upload(file);
				fileService.upload(fileCreate, record);
			}
		}
	}

	@Transactional
	@Override
	public Record updateThumbnailUrl(MultipartFile thumbnail, Record record) throws IOException {
		if (!thumbnail.isEmpty()) {
			FileCreate fileCreate = s3Service.upload(thumbnail);
			record = fileService.upload(fileCreate, record);
			record = record.changeThumbnailUrl(fileCreate.getFileUrl());
			record = recordRepository.save(record);
		}
		return record;
	}

	@Transactional
	@Override
	public Record save(Record record) {
		return recordRepository.save(record);
	}

	@Transactional
	@Override
	public void delete(Record record) {
		recordRepository.delete(record);
	}
}
