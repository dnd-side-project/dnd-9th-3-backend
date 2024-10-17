package com.dnd.gooding.record.query.dto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.Subselect;
import org.hibernate.annotations.Synchronize;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Immutable
@Synchronize({"record", "bookmark"})
public class RecordData {

    @Id
    @Column(name = "record_number")
    private String recordNumber;

    @Column(name = "place_title")
    private String placeTitle;

    @Column(name = "place_latitude")
    private Double placeLatitude;

    @Column(name = "place_longitude")
    private Double placeLongitude;

    @Column(name = "recorder_id")
    private String recorderId;

    @Column(name = "recorder_name")
    private String recorderName;

    @Column(name = "title")
    private String title;

    @Column(name = "record_date")
    private Date recordDate;

    @Column(name = "description")
    private String description;

    @Transient private List<ImageData> images = new ArrayList<>();

    @Builder
    public RecordData(
            String recordNumber,
            String placeTitle,
            Double placeLatitude,
            Double placeLongitude,
            String recorderId,
            String recorderName,
            String title,
            Date recordDate,
            String description) {
        this.recordNumber = recordNumber;
        this.placeTitle = placeTitle;
        this.placeLatitude = placeLatitude;
        this.placeLongitude = placeLongitude;
        this.recorderId = recorderId;
        this.recorderName = recorderName;
        this.title = title;
        this.recordDate = recordDate;
        this.description = description;
    }

    public List<ImageData> getImages() {
        return Collections.unmodifiableList(images);
    }

    public void changeImages(List<ImageData> images) {
        this.images = images;
    }
}
