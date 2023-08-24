package com.dnd.gooding.domain.feed.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.dnd.gooding.domain.record.model.RecordOpenStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

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

	/**
	 * 로그인한 사용자의 관심사 기반으로 피드를 기록 등록된 순으로 보여주고
	 * 관심사로 등록된 피드가 없다면 기록 등록된 랜덤 피드 순으로 보여준다.
	 * @param userId
	 * @param interestCodes
	 * @param pageable
	 * @return
	 */
	@Override
	public Page<FeedResponse> findByRecordByInterestCodeAndIsNotUserId(Long userId, List<InterestType> interestCodes, Pageable pageable) {
		Page<Record> records = feedRepository.findByRecordByInterestCodeAndIsNotUserId(userId, interestCodes, pageable);
		if (ObjectUtils.isEmpty(records.getContent())) {
			records = feedRepository.findByRecordByInterestCodeAndIsNotUserId(userId, new ArrayList<InterestType>(), pageable);
		}
		return new PageImpl<>(records.stream()
				.filter(record -> RecordOpenStatus.PUBLIC.name().equals(record.getRecordOpen().name()))
				.map(FeedResponse::new)
				.collect(Collectors.toList()),
				pageable, records.getTotalElements());
	}
}
