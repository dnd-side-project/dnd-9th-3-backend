package com.dnd.gooding.domain.record.repository;

import com.dnd.gooding.domain.record.model.Record;

import java.util.List;
import java.util.Optional;

public interface RecordRepositoryCustom {

    Optional<List<Record>> findByUserId(Long id);
}
