package com.dnd.gooding.record.query.dao;

import com.dnd.gooding.record.query.dto.RecordData;
import java.util.List;

public interface RecordDataDaoCustom {

    RecordData findById(String recorderMemberId);

    List<RecordData> findByRecorderId(String recorderMemberId);
}
