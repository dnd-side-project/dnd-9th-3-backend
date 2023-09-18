package com.dnd.gooding.domain.record.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RecordJpaRepository extends JpaRepository<RecordEntity, Long> {
}
