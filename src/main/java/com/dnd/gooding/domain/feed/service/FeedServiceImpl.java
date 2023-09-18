package com.dnd.gooding.domain.feed.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import com.dnd.gooding.domain.feed.controller.port.FeedService;
import com.dnd.gooding.domain.feed.controller.response.FeedResponse;
import com.dnd.gooding.domain.feed.domain.Feed;
import com.dnd.gooding.domain.feed.service.port.FeedRepository;
import com.dnd.gooding.domain.record.controller.port.RecordService;
import com.dnd.gooding.domain.record.domain.Record;
import com.dnd.gooding.domain.record.domain.RecordOpenStatus;
import com.dnd.gooding.domain.user.controller.port.UserService;
import com.dnd.gooding.domain.user.domain.User;
import com.dnd.gooding.global.common.enums.InterestType;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FeedServiceImpl implements FeedService {

	private final FeedRepository feedRepository;
	private final UserService userService;
	private final RecordService recordService;

	@Transactional(readOnly = true)
	@Override
	public Page<FeedResponse> findByInterestAndIsNotUserId(Long userId, List<InterestType> interestCodes,
		Pageable pageable) {
		Page<Record> records = feedRepository.findByInterestAndIsNotUserId(userId, interestCodes, pageable);
		if (ObjectUtils.isEmpty(records.getContent())) {
			records = feedRepository.findByInterestAndIsNotUserId(userId, new ArrayList<>(), pageable);
		}
		return new PageImpl<>(records.stream()
			.filter(record -> RecordOpenStatus.PUBLIC.name().equals(record.getRecordOpen().name()))
			.map(FeedResponse::from)
			.collect(Collectors.toList()),
			pageable, records.getTotalElements());
	}

	@Transactional(readOnly = true)
	@Override
	public Feed findByUserIdAndRecordId(Long userId, Long recordId, Long saveUserId) {
		return feedRepository.findByUserIdAndRecordId(userId, recordId, saveUserId).orElseGet(Feed::new);
	}

	@Transactional
	@Override
	public void save(Long recordUserId, Long recordId, Long saveUserId, String feedSave, Integer feedScore) {
		Feed feed = findByUserIdAndRecordId(recordUserId, recordId, saveUserId);
		if (feed.getId() == null) {
			User user = userService.findByUserId(recordUserId);
			Record record = recordService.findByUserIdAndRecordId(recordUserId, recordId);
			feedRepository.save(Feed.create(user, record, saveUserId, feedSave, feedScore));
		} else {
			feedRepository.save(feed.update(feedSave, feedScore));
		}
	}
}
