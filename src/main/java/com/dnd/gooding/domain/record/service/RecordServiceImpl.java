package com.dnd.gooding.domain.record.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dnd.gooding.domain.record.model.Record;
import com.dnd.gooding.domain.record.repository.RecordRepository;
import com.dnd.gooding.domain.user.exception.UserNotFoundException;
import com.dnd.gooding.domain.user.model.User;
import com.dnd.gooding.domain.user.repository.UserJpaRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RecordServiceImpl implements RecordService {

	private final UserJpaRepository userJpaRepository;
	private final RecordRepository recordRepository;

	@Transactional
	@Override
	public Long create(Long userId) {
		User user = userJpaRepository.findById(userId)
			.orElseThrow(() -> new UserNotFoundException(userId));
		Record record = Record.createRecord(user);
		recordRepository.save(record);
		return record.getId();
	}
}
