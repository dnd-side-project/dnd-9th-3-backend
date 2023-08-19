package com.dnd.gooding.domain.record.dto.response;

import com.dnd.gooding.domain.file.dto.response.RecordFileResponse;
import com.dnd.gooding.domain.record.model.Record;
import com.dnd.gooding.domain.record.model.RecordOpenStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class MyRecordResponse {

    private Long id;
    private String title;
    private String description;

    private String thumbnailUrl;
    private LocalDateTime recordDate;
    private String placeTitle;
    private Double placeLatitude;
    private Double placeLongitude;
    private Integer recordScore;
    private RecordOpenStatus recordOpen;
    private List<RecordFileResponse> files;

    public MyRecordResponse(Record record) {
        this.id = record.getId();
        this.title = record.getTitle();
        this.description = record.getDescription();
        this.thumbnailUrl = record.getThumbnailUrl();
        this.recordDate = record.getRecordDate();
        this.placeTitle = record.getPlaceTitle();
        this.placeLatitude = record.getPlaceLatitude();
        this.placeLongitude = record.getPlaceLongitude();
        this.recordScore = record.getRecordScore();
        this.recordOpen = record.getRecordOpen();
        this.files = record.getFiles().stream()
                .map(file -> new RecordFileResponse(file))
                .collect(Collectors.toList());
    }
}
