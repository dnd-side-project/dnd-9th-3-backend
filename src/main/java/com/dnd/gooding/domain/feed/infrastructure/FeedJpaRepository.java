package com.dnd.gooding.domain.feed.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedJpaRepository extends JpaRepository<FeedEntity, Long> {
}
