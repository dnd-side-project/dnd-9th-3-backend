package com.dnd.gooding.domain.feed.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.dnd.gooding.domain.feed.dto.response.FeedResponse;

public interface FeedService {

	Page<FeedResponse> findByRecordByInterestCodeAndIsNotUserId(Long userId, List<String> interestCodes, Pageable pageable);
}
