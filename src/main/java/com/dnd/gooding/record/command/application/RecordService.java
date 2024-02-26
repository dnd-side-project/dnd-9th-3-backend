package com.dnd.gooding.record.command.application;

import com.dnd.gooding.common.filestore.api.FileCreate;
import com.dnd.gooding.record.command.domain.*;
import com.dnd.gooding.record.command.domain.Record;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
public class RecordService {

    private final RecordRepository recordRepository;
    private final RecorderService recorderService;
    private final FileService fileService;

    public RecordService(
            RecordRepository recordRepository, RecorderService recorderService, FileService fileService) {
        this.recordRepository = recordRepository;
        this.recorderService = recorderService;
        this.fileService = fileService;
    }

    @Transactional
    public void create(RecordRequest recordRequest) throws IOException {

        List<Image> images = new ArrayList<>();
        for (MultipartFile multipartFile : recordRequest.getFiles()) {
            // File file = FileCreate.convert(multipartFile);
            // String fileUrl = fileService.createFile(file);
            String fileUrl = FileCreate.convert(multipartFile);
            Image image = Image.builder().path(fileUrl).uploadTime(LocalDateTime.now()).build();
            images.add(image);
        }

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
