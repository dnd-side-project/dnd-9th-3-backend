package com.dnd.gooding.domain.feed.service;

import java.util.List;
import java.util.stream.Collectors;

import com.dnd.gooding.domain.record.model.RecordOpenStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dnd.gooding.domain.feed.dto.response.FeedResponse;
import com.dnd.gooding.domain.feed.repository.FeedRepository;
import com.dnd.gooding.domain.record.model.Record;
import com.dnd.gooding.global.common.model.InterestType;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FeedServiceImpl implements FeedService {

	private final FeedRepository feedRepository;

	@Override
	public Page<FeedResponse> findByRecordByInterestCodeAndIsNotUserId(Long userId, List<InterestType> interestCodes, Pageable pageable) {
		Page<Record> records = feedRepository.findByRecordByInterestCodeAndIsNotUserId(userId, interestCodes, pageable);
		return new PageImpl<>(records.stream()
				.filter(record -> RecordOpenStatus.PUBLIC.name().equals(record.getRecordOpen().name()))
				.map(FeedResponse::new)
				.collect(Collectors.toList()),
				pageable, records.getTotalElements());
	}
}
