package com.dnd.gooding.domain.feed.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.dnd.gooding.domain.feed.model.Feed;
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
import com.dnd.gooding.domain.record.service.RecordService;
import com.dnd.gooding.domain.user.model.User;
import com.dnd.gooding.domain.user.controller.port.UserService;
import com.dnd.gooding.global.common.model.InterestType;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FeedServiceImpl implements FeedService {

	private final FeedRepository feedRepository;
	private final UserService userService;
	private final RecordService recordService;

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

	@Transactional
	@Override
	public void save(Long recordUserId, Long recordId, Long saveUserId, String feedSave, Integer feedScore) {
		Feed feed = findByUserIdAndRecordId(recordUserId, recordId, saveUserId);
		if (ObjectUtils.isEmpty(feed)) {
			User user = userService.findByUserId(recordUserId);
			Record record = recordService.findByRecordId(recordUserId, recordId);
			feedRepository.save(Feed.create(user, record, saveUserId, feedSave, feedScore));
		} else {
			feed.changeFeedSave(feedSave, feedScore);
		}
	}

	@Override
	public Feed findByUserIdAndRecordId(Long userId, Long recordId, Long saveUserId) {
		return feedRepository.findByUserIdAndRecordId(userId, recordId, saveUserId);
	}
}
