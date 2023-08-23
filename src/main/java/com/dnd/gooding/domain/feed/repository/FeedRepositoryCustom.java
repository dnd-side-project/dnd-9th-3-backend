package com.dnd.gooding.domain.feed.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.dnd.gooding.domain.record.model.Record;

public interface FeedRepositoryCustom {

	Page<Record> findByRecordIsNotUserId(Long userId, Pageable pageable);
}
