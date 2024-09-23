package com.dnd.gooding.record.query.application;

import com.dnd.gooding.record.command.domain.RecordNo;
import com.dnd.gooding.record.exception.NoRecordException;
import com.dnd.gooding.record.query.dao.FeedDataDao;
import com.dnd.gooding.record.query.dao.ImageDataDao;
import com.dnd.gooding.record.query.dao.RecordDataDao;
import com.dnd.gooding.record.query.dto.FeedData;
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
    private final FeedDataDao feedDataDao;

    public RecordQueryService(
            RecordDataDao recordDataDao, ImageDataDao imageDataDao, FeedDataDao feedDataDao) {
        this.recordDataDao = recordDataDao;
        this.imageDataDao = imageDataDao;
        this.feedDataDao = feedDataDao;
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

    @Transactional(readOnly = true)
    public List<FeedData> getFeed() {
        List<FeedData> feeds = feedDataDao.findFeeds();
        List<ImageData> images = imageDataDao.findByRecordNumberIn(toFeedIds(feeds));
        Map<String, List<ImageData>> imageMap =
                images.stream().collect(Collectors.groupingBy(ImageData::getRecordNumber));
        feeds.forEach(x -> x.setImages(imageMap.get(x.getRecordNumber())));
        return feeds;
    }

    private List<String> toRecordIds(List<RecordData> records) {
        return records.stream().map(RecordData::getRecordNumber).collect(Collectors.toList());
    }

    private List<String> toFeedIds(List<FeedData> records) {
        return records.stream().map(FeedData::getRecordNumber).collect(Collectors.toList());
    }
}
