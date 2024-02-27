package com.dnd.gooding.record.query.dto;

import com.dnd.gooding.record.command.domain.Image;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import lombok.Builder;

public class RecordData {

    private String number;

    private String placeTitle;

    private Double placeLatitude;

    private Double placeLongitude;

    private String recorderId;

    private String recorderName;

    private String title;
    private String description;

    private List<ImageData> images = new ArrayList<>();

    protected RecordData() {}

    @Builder
    public RecordData(
            String number,
            String placeTitle,
            Double placeLatitude,
            Double placeLongitude,
            String recorderId,
            String recorderName,
            String title,
            String description,
            List<ImageData> images) {
        this.number = number;
        this.placeTitle = placeTitle;
        this.placeLatitude = placeLatitude;
        this.placeLongitude = placeLongitude;
        this.recorderId = recorderId;
        this.recorderName = recorderName;
        this.title = title;
        this.description = description;
        this.images.addAll(images);
    }

    public String getNumber() {
        return number;
    }

    public String getPlaceTitle() {
        return placeTitle;
    }

    public Double getPlaceLatitude() {
        return placeLatitude;
    }

    public Double getPlaceLongitude() {
        return placeLongitude;
    }

    public String getRecorderId() {
        return recorderId;
    }

    public String getRecorderName() {
        return recorderName;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public List<ImageData> getImages() {
        return Collections.unmodifiableList(images);
    }
}
