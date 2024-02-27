package com.dnd.gooding.record.query.application;

import com.dnd.gooding.record.command.application.NoRecordException;
import com.dnd.gooding.record.command.domain.RecordNo;
import com.dnd.gooding.record.query.dao.RecordDataDao;
import com.dnd.gooding.record.query.dto.RecordData;
import com.dnd.gooding.user.command.domain.MemberId;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RecordQueryService {

    private final RecordDataDao recordDataDao;

    public RecordQueryService(RecordDataDao recordDataDao) {
        this.recordDataDao = recordDataDao;
    }

    @Transactional(readOnly = true)
    public RecordData getRecord(RecordNo recordNo) {
//        return Optional.of(recordDataDao.findById(recordNo.getNumber())).orElseThrow(NoRecordException::new);
        return null;
    }

    @Transactional(readOnly = true)
    public List<RecordData> getRecord(MemberId recorderMemberId) {
        return null;
    }
}
