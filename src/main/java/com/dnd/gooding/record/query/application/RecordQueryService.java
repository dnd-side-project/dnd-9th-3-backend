package com.dnd.gooding.record.query.application;

import com.dnd.gooding.record.command.domain.RecordNo;
import com.dnd.gooding.record.exception.NoRecordException;
import com.dnd.gooding.record.query.dao.ImageDataDao;
import com.dnd.gooding.record.query.dao.RecordDataDao;
import com.dnd.gooding.record.query.dto.ImageData;
import com.dnd.gooding.record.query.dto.RecordData;
import com.dnd.gooding.user.command.domain.MemberId;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RecordQueryService {

    private final RecordDataDao recordDataDao;
    private final ImageDataDao imageDataDao;

    public RecordQueryService(RecordDataDao recordDataDao, ImageDataDao imageDataDao) {
        this.recordDataDao = recordDataDao;
        this.imageDataDao = imageDataDao;
    }

    @Transactional(readOnly = true)
    public RecordData getRecord(RecordNo recordNo) {
        return recordDataDao.findById(recordNo.getNumber()).orElseThrow(NoRecordException::new);
    }

    @Transactional(readOnly = true)
    public List<RecordData> getRecord(MemberId recorderMemberId) {
        List<RecordData> records = recordDataDao.findByRecorderId(recorderMemberId.getId());
        List<ImageData> images = imageDataDao.findByRecordNumberIn(toRecordIds(records));
        Map<String, List<ImageData>> imageMap =
                images.stream().collect(Collectors.groupingBy(ImageData::getRecordNumber));
        records.forEach(x -> x.setImages(imageMap.get(x.getRecordNumber())));
        return records;
    }

    private List<String> toRecordIds(List<RecordData> records) {
        return records.stream().map(RecordData::getRecordNumber).toList();
    }
}
