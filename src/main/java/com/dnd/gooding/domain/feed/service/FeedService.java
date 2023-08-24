package com.dnd.gooding.domain.feed.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.dnd.gooding.domain.feed.dto.response.FeedResponse;
import com.dnd.gooding.domain.feed.model.Feed;
import com.dnd.gooding.global.common.model.InterestType;

public interface FeedService {

	Page<FeedResponse> findByRecordByInterestCodeAndIsNotUserId(Long userId, List<InterestType> interestCodes, Pageable pageable);
	void save(Long recordUserId, Long recordId, Long saveUserId, String feedSave, Integer feedScore);

	Feed findByUserIdAndRecordId(Long userId, Long recordId, Long saveUserId);
}
