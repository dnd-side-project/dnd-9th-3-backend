package com.dnd.gooding.domain.record.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dnd.gooding.domain.record.model.Record;

public interface RecordRepository extends JpaRepository<Record, Long>, RecordRepositoryCustom {
}
