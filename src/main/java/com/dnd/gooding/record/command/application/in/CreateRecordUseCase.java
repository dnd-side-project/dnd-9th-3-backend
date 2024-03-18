package com.dnd.gooding.record.command.application.in;

import java.io.IOException;

import com.dnd.gooding.record.ui.dto.request.RecordRequest;

public interface CreateRecordUseCase {

	void create(RecordRequest recordRequest) throws IOException;
}
