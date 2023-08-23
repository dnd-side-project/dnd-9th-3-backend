package com.dnd.gooding.domain.feed.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dnd.gooding.domain.feed.model.Feed;

public interface FeedRepository extends JpaRepository<Feed, Long>, FeedRepositoryCustom {
}
