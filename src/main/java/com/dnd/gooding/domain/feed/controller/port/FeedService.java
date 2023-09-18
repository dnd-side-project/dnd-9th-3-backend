package com.dnd.gooding.domain.feed.controller.port;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.dnd.gooding.domain.feed.controller.response.FeedResponse;
import com.dnd.gooding.domain.feed.domain.Feed;
import com.dnd.gooding.global.common.enums.InterestType;

public interface FeedService {

	Page<FeedResponse> findByInterestAndIsNotUserId(Long userId, List<InterestType> interestCodes, Pageable pageable);
	Feed findByUserIdAndRecordId(Long userId, Long recordId, Long saveUserId);
	void save(Long recordUserId, Long recordId, Long saveUserId, String feedSave, Integer feedScore);
}
