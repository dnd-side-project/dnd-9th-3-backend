package com.dnd.gooding.domain.record.service;

import com.dnd.gooding.domain.file.model.File;
import com.dnd.gooding.domain.record.dto.response.MyRecordResponse;
import com.dnd.gooding.global.s3.service.S3Service;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import com.dnd.gooding.domain.record.dto.request.UploadRequest;
import com.dnd.gooding.domain.record.model.Record;
import com.dnd.gooding.domain.record.repository.RecordRepository;
import com.dnd.gooding.domain.user.exception.UserNotFoundException;
import com.dnd.gooding.domain.user.model.User;
import com.dnd.gooding.domain.user.repository.UserJpaRepository;

import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RecordServiceImpl implements RecordService {

	private final S3Service s3Service;
	private final UserJpaRepository userJpaRepository;
	private final RecordRepository recordRepository;

	@Override
	public List<MyRecordResponse> findByUserId(Long userId) {
		List<Record> records = recordRepository.findByUserId(userId)
				.orElseThrow(() -> new UserNotFoundException(userId));
		return records.stream()
				.map(MyRecordResponse::new)
				.collect(Collectors.toList());
	}

	@Override
	public List<MyRecordResponse> findRecordByDate(Long userId, String recordDate) {
		List<Record> records = recordRepository.findRecordByDate(userId, recordDate)
				.orElseThrow(() -> new UserNotFoundException(userId));
		return records.stream()
				.map(MyRecordResponse::new)
				.collect(Collectors.toList());
	}

	@Override
	public List<MyRecordResponse> findFeedBySave(Long saveUserId) {
		List<Record> records = recordRepository.findFeedBySave(saveUserId)
			.orElse(null);
		if (ObjectUtils.isEmpty(records)) {
			return new ArrayList<>();
		} else {
			return records.stream()
				.map(MyRecordResponse::new)
				.collect(Collectors.toList());
		}
	}

	@Override
	public Record findByRecordId(Long userId, Long recordId) {
		return recordRepository.findByRecordId(userId, recordId);
	}

	@Override
	public Record create(String oauthId, UploadRequest uploadRequest) {
		User user = userJpaRepository.findByOauthId(oauthId)
			.orElseThrow(() -> new UserNotFoundException(oauthId));
		Record record = Record.create(uploadRequest, user);
		recordRepository.save(record);
		return record;
	}

	@Transactional
	@Override
	public void delete(Record record) {
		for(File file : record.getFiles()) {
			s3Service.delete(file.extractFilePath(file.getFileUrl()));
		}
		recordRepository.delete(record);
	}
}
