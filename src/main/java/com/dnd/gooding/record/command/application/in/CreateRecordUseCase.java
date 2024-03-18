package com.dnd.gooding.record.command.application.in;

import com.dnd.gooding.record.ui.dto.request.RecordRequest;
import java.io.IOException;

public interface CreateRecordUseCase {

    void create(RecordRequest recordRequest) throws IOException;
}
