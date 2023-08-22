package com.dnd.gooding.domain.feed.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.dnd.gooding.domain.feed.dto.FeedResponse;

public interface FeedService {

	List<FeedResponse> findByRecordIsNotUserId(Long userId, Pageable pageable);
}
