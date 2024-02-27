package com.dnd.gooding.record.query.dao;

import com.dnd.gooding.record.command.domain.Record;

import java.util.List;

public interface RecordDataDaoCustom {

    Record findById(String recorderMemberId);

    List<Record> findByRecorderId(String recorderMemberId);
}
