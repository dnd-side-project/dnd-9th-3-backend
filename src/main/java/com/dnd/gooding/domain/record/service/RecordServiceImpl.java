package com.dnd.gooding.domain.record.service;

import com.dnd.gooding.domain.record.dto.response.MyRecordResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dnd.gooding.domain.record.dto.request.UploadRequest;
import com.dnd.gooding.domain.record.model.Record;
import com.dnd.gooding.domain.record.repository.RecordRepository;
import com.dnd.gooding.domain.user.exception.UserNotFoundException;
import com.dnd.gooding.domain.user.model.User;
import com.dnd.gooding.domain.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RecordServiceImpl implements RecordService {

	private final UserRepository userRepository;
	private final RecordRepository recordRepository;

	@Override
	public List<MyRecordResponse> findByUserId(Long userId) {
		List<Record> records = recordRepository.findByUserId(userId)
				.orElseThrow(() -> new UserNotFoundException(userId));
		List<MyRecordResponse> myRecords = records.stream()
				.map(record -> new MyRecordResponse(record))
				.collect(Collectors.toList());
		return myRecords;
	}

	@Override
	public Record create(String oauthId, UploadRequest uploadRequest) {
		User user = userRepository.findByOauthId(oauthId)
			.orElseThrow(() -> new UserNotFoundException(oauthId));
		Record record = Record.create(uploadRequest, user);
		recordRepository.save(record);
		return record;
	}

	@Override
	public void thumbnailUpdate(Long recordId, String thumbnailUrl) {
		recordRepository.thumbnailUpdate(recordId, thumbnailUrl);
	}
}
