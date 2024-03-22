package com.dnd.gooding.unit.mock;

import com.dnd.gooding.record.command.domain.Record;
import com.dnd.gooding.record.command.domain.RecordNo;
import com.dnd.gooding.record.command.domain.repository.RecordRepository;
import java.util.Optional;

public class FakeRecordRepository implements RecordRepository {

    @Override
    public Optional<Record> findById(RecordNo recordNo) {
        return Optional.empty();
    }

    @Override
    public void save(Record gilog) {}

    @Override
    public void delete(Record gilog) {}
}
