package com.dnd.gooding.domain.feed.service.port;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.dnd.gooding.domain.feed.domain.Feed;
import com.dnd.gooding.domain.record.domain.Record;
import com.dnd.gooding.global.common.enums.InterestType;

public interface FeedRepository {

	Page<Record> findByInterestAndIsNotUserId(Long userId, List<InterestType> interestCodes, Pageable pageable);
	Optional<Feed> findByUserIdAndRecordId(Long userId, Long recordId, Long saveUserId);
	Feed save(Feed feed);
}
