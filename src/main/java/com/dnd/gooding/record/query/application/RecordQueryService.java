package com.dnd.gooding.record.query.application;

import com.dnd.gooding.record.command.application.NoRecordException;
import com.dnd.gooding.record.query.dao.RecordDataDao;
import com.dnd.gooding.record.query.dto.RecordData;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RecordQueryService {

    private final RecordDataDao recordDataDao;

    public RecordQueryService(RecordDataDao recordDataDao) {
        this.recordDataDao = recordDataDao;
    }

    @Transactional(readOnly = true)
    public RecordData getRecord(String recordNo) {
        return recordDataDao.findById(recordNo).orElseThrow(NoRecordException::new);
    }
}
