package com.dnd.gooding.domain.record.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dnd.gooding.domain.record.dto.request.UploadRequest;
import com.dnd.gooding.domain.record.model.Record;
import com.dnd.gooding.domain.record.repository.RecordRepository;
import com.dnd.gooding.domain.user.exception.UserNotFoundException;
import com.dnd.gooding.domain.user.model.User;
import com.dnd.gooding.domain.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RecordServiceImpl implements RecordService {

	private final UserRepository userRepository;
	private final RecordRepository recordRepository;

	@Transactional
	@Override
	public Record create(String oauthId, UploadRequest uploadRequest) {
		User user = userRepository.findByOauthId(oauthId)
			.orElseThrow(() -> new UserNotFoundException(oauthId));
		Record record = Record.create(uploadRequest, user);
		recordRepository.save(record);
		return record;
	}
}
