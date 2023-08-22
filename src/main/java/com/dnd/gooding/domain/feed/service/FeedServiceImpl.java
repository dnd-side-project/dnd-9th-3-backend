package com.dnd.gooding.domain.feed.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dnd.gooding.domain.feed.dto.FeedResponse;
import com.dnd.gooding.domain.feed.repository.FeedRepository;
import com.dnd.gooding.domain.record.model.Record;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FeedServiceImpl implements FeedService {

	private final FeedRepository feedRepository;

	@Override
	public List<FeedResponse> findByRecordIsNotUserId(Long userId, Pageable pageable) {
		Page<Record> records = feedRepository.findByRecordIsNotUserId(userId, pageable);
		return records.stream()
			.map(FeedResponse::new)
			.collect(Collectors.toList());
	}
}
