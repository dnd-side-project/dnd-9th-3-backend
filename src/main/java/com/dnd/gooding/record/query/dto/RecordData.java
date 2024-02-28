package com.dnd.gooding.record.query.dto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.*;

import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.Subselect;
import org.hibernate.annotations.Synchronize;

import lombok.Builder;

@Entity
@Table(name = "record")
@Immutable
@Subselect(
    """
		select
			r.record_number,
			r.place_title,
			r.place_latitude,
			r.place_longitude,
			r.recorder_id,
			r.recorder_name,
			r.title,
			r.description
		from record r
		"""
)
@Synchronize({"record"})
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

    @Column(name = "description")
    private String description;

    @Transient
    private List<ImageData> images = new ArrayList<>();

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
        String description) {
        this.recordNumber = recordNumber;
        this.placeTitle = placeTitle;
        this.placeLatitude = placeLatitude;
        this.placeLongitude = placeLongitude;
        this.recorderId = recorderId;
        this.recorderName = recorderName;
        this.title = title;
        this.description = description;
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

    public List<ImageData> getImages() {
        return Collections.unmodifiableList(images);
    }

    public void setImages(List<ImageData> images) {
        this.images = images;
    }
}
