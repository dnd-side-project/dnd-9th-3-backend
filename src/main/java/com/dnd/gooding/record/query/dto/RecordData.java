package com.dnd.gooding.record.query.dto;

import com.dnd.gooding.record.command.domain.Image;
import lombok.Builder;
import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.Subselect;
import org.hibernate.annotations.Synchronize;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Entity
@Immutable
@Subselect(
        """
            select
                r.record_number,
                r.place_title,
                r.place_latitude,
                r.placeLongitude,
                r.recorder_id,
                r.recorderName,
                r.title,
                r.description,
                i.image_id,
                i.image_path
            from record r
            left join image i
                on r.record_number = i.record_number
            """
)
@Synchronize({"record", "image"})
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

    private String title;
    private String description;

    @JoinColumn(name = "record_number")
    private List<Image> images = new ArrayList<>();

    protected RecordData() {}

    @Builder
    public RecordData(
            String recordNumber,
            String placeTitle,
            Double placeLatitude,
            Double placeLongitude,
            String recorderId,
            String recorderName,
            String title,
            String description,
            List<Image> images) {
        this.recordNumber = recordNumber;
        this.placeTitle = placeTitle;
        this.placeLatitude = placeLatitude;
        this.placeLongitude = placeLongitude;
        this.recorderId = recorderId;
        this.recorderName = recorderName;
        this.title = title;
        this.description = description;
        this.images.addAll(images);
    }

    public String getRecordNumber() {
        return recordNumber;
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

    public List<Image> getImages() {
        return Collections.unmodifiableList(images);
    }
}
