package com.dnd.gooding.record.command.domain.repository;

import com.dnd.gooding.record.command.domain.Record;
import com.dnd.gooding.record.command.domain.RecordNo;
import java.security.SecureRandom;
import java.util.Date;
import java.util.Optional;
import org.springframework.data.repository.Repository;

public interface RecordRepository extends Repository<Record, RecordNo> {

    Optional<Record> findById(RecordNo recordNo);

    void save(Record record);

    void delete(Record record);

    default RecordNo nextRecordNo() {
        int randomNo = new SecureRandom().nextInt(900000) + 100000;
        String number = String.format("%tY%<tm%<td%<tH-%d", new Date(), randomNo);
        return new RecordNo(number);
    }
}
