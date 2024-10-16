package com.dnd.gooding.record.command.application.in;

import com.dnd.gooding.record.command.dto.Pageable;
import com.dnd.gooding.record.command.dto.RecordPlace;
import java.util.List;

public interface RecordReplaceUseCase {

    Pageable<List<RecordPlace>> getPlaces(String keyword, int page, int size);
}
