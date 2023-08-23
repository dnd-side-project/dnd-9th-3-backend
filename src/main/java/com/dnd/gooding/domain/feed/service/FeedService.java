package com.dnd.gooding.domain.feed.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.dnd.gooding.domain.feed.dto.response.FeedResponse;

public interface FeedService {

	Page<FeedResponse> findByRecordIsNotUserId(Long userId, Pageable pageable);
}
