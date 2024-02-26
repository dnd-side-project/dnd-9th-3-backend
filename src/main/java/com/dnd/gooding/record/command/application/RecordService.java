package com.dnd.gooding.record.command.application;

import com.dnd.gooding.record.command.domain.*;
import com.dnd.gooding.record.command.domain.Record;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
public class RecordService {

    private final RecordRepository recordRepository;
    private final RecorderService recorderService;

    public RecordService(RecordRepository recordRepository, RecorderService recorderService) {
        this.recordRepository = recordRepository;
        this.recorderService = recorderService;
    }

    @Transactional
    public void create(RecordRequest recordRequest) {

        List<Image> images = new ArrayList<>();
        for (MultipartFile file : recordRequest.getFiles()) {}

        RecordNo recordNo = recordRepository.nextRecordNo();
        Coordinate coordinate =
                Coordinate.builder()
                        .placeTitle(recordRequest.getPlaceTitle())
                        .placeLatitude(recordRequest.getPlaceLatitude())
                        .placeLongitude(recordRequest.getPlaceLongitude())
                        .build();
        Recorder recorder = recorderService.createRecorder(recordRequest.getRecorderMemberId());
        Record record =
                Record.builder()
                        .number(recordNo)
                        .coordinate(coordinate)
                        .recorder(recorder)
                        .title(recordRequest.getTitle())
                        .description(recordRequest.getDescription())
                        .state(recordRequest.getState())
                        .recordScore(recordRequest.getRecordScore())
                        .recordDate(recordRequest.getRecordDate())
                        .images(images)
                        .build();
        recordRepository.save(record);
    }

    @Transactional
    public void delete(String recordNo) {
        Record record =
                recordRepository.findById(RecordNo.of(recordNo)).orElseThrow(NoRecordException::new);
        recordRepository.delete(record);
    }
}
