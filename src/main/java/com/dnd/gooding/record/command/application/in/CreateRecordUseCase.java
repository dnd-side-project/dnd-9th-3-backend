package com.dnd.gooding.record.command.application.in;

import com.dnd.gooding.record.command.domain.Record;
import com.dnd.gooding.record.ui.dto.request.RecordRequest;
import java.io.IOException;

public interface CreateRecordUseCase {

    Record create(RecordRequest recordRequest) throws IOException;
}
