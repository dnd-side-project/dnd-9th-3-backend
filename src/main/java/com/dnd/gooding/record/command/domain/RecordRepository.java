package com.dnd.gooding.record.command.domain;

import java.security.SecureRandom;
import java.util.Date;
import java.util.Optional;
import org.springframework.data.repository.Repository;

public interface RecordRepository extends Repository<Record, RecordNo> {

    Optional<Record> findById(RecordNo recordNo);

    void save(Record gilog);

    void delete(Record gilog);

    default RecordNo nextRecordNo() {
        int randomNo = new SecureRandom().nextInt(900000) + 100000;
        String number = String.format("%tY%<tm%<td%<tH-%d", new Date(), randomNo);
        return new RecordNo(number);
    }
}
