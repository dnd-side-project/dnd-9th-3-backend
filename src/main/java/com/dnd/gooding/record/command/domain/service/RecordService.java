package com.dnd.gooding.record.command.domain.service;

import com.dnd.gooding.record.command.application.in.CreateRecordUseCase;
import com.dnd.gooding.record.command.application.in.DeleteRecordUseCase;
import com.dnd.gooding.record.command.application.in.RecordReplaceUseCase;
import com.dnd.gooding.record.command.application.out.RecordFilePort;
import com.dnd.gooding.record.command.application.out.RecordMemberPort;
import com.dnd.gooding.record.command.application.out.RecordReplacePort;
import com.dnd.gooding.record.command.domain.Coordinate;
import com.dnd.gooding.record.command.domain.Image;
import com.dnd.gooding.record.command.domain.ImageId;
import com.dnd.gooding.record.command.domain.Record;
import com.dnd.gooding.record.command.domain.RecordNo;
import com.dnd.gooding.record.command.domain.Recorder;
import com.dnd.gooding.record.command.domain.repository.RecordRepository;
import com.dnd.gooding.record.command.dto.RecordPlace;
import com.dnd.gooding.record.exception.NoRecordException;
import com.dnd.gooding.record.ui.dto.request.RecordRequest;
import com.dnd.gooding.util.FileCreateUtil;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
public class RecordService
        implements CreateRecordUseCase, DeleteRecordUseCase, RecordReplaceUseCase {

    private final RecordRepository recordRepository;
    private final RecordMemberPort recordMemberPort;
    private final RecordFilePort recordFilePort;
    private final RecordReplacePort recordReplacePort;

    public RecordService(
            RecordRepository recordRepository,
            RecordMemberPort recordMemberPort,
            RecordFilePort recordFilePort,
            RecordReplacePort recordReplacePort) {
        this.recordRepository = recordRepository;
        this.recordMemberPort = recordMemberPort;
        this.recordFilePort = recordFilePort;
        this.recordReplacePort = recordReplacePort;
    }

    @Transactional
    public Record create(RecordRequest recordRequest) throws IOException {

        List<Image> images = new ArrayList<>();
        for (MultipartFile multipartFile : recordRequest.getFiles()) {
            File file = FileCreateUtil.convert(multipartFile);
            String path = recordFilePort.putFile(file.getName(), file);
            Image image =
                    Image.builder()
                            .id(ImageId.of(file.getName()))
                            .path(path)
                            .uploadTime(LocalDateTime.now())
                            .build();
            images.add(image);
        }

        RecordNo recordNo = recordRepository.nextRecordNo();
        Coordinate coordinate =
                Coordinate.builder()
                        .placeTitle(recordRequest.getPlaceTitle())
                        .placeLatitude(recordRequest.getPlaceLatitude())
                        .placeLongitude(recordRequest.getPlaceLongitude())
                        .build();
        Recorder recorder = recordMemberPort.createRecorder(recordRequest.getRecorderMemberId());
        Record gilog =
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
        recordRepository.save(gilog);
        return gilog;
    }

    @Transactional
    public void delete(String recordNo) {
        Record gilog =
                recordRepository.findById(RecordNo.of(recordNo)).orElseThrow(NoRecordException::new);
        for (Image image : gilog.getImages()) {
            recordFilePort.deleteFile(image.getId().getId());
        }
        recordRepository.delete(gilog);
    }

    @Transactional(readOnly = true)
    @Override
    public List<RecordPlace> getPlaces(String keyword, int page, int size) {
        return recordReplacePort.getPlaces(keyword, page, size);
    }
}
