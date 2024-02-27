package com.dnd.gooding.record.query.dao;

import com.dnd.gooding.record.command.domain.Record;
import org.springframework.data.repository.Repository;

public interface RecordDataDao extends Repository<Record, String>, RecordDataDaoCustom {}
