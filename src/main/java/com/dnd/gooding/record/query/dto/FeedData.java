package com.dnd.gooding.record.query.dto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.Synchronize;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Immutable
@Synchronize({"record", "member", "oauth"})
public class FeedData {

    @Id
    @Column(name = "record_number")
    private String recordNumber;

    @Column(name = "place_latitude")
    private Double placeLatitude;

    @Column(name = "place_longitude")
    private Double placeLongitude;

    @Column(name = "place_title")
    private String placeTitle;

    @Column(name = "description")
    private String description;

    @Column(name = "record_date")
    private Date recordDate;

    @Column(name = "record_score")
    private long recordScore;

    @Column(name = "recorder_id")
    private String recorderId;

    @Column(name = "recorder_name")
    private String recorderName;

    @Column(name = "title")
    private String title;

    @Column(name = "name")
    private String name;

    @Column(name = "image_url")
    private String imageUrl;

    @Transient private List<ImageData> images = new ArrayList<>();

    @Builder
    public FeedData(
            String recordNumber,
            Double placeLatitude,
            Double placeLongitude,
            String placeTitle,
            String description,
            Date recordDate,
            long recordScore,
            String recorderId,
            String recorderName,
            String title,
            String name,
            String imageUrl) {
        this.recordNumber = recordNumber;
        this.placeLatitude = placeLatitude;
        this.placeLongitude = placeLongitude;
        this.placeTitle = placeTitle;
        this.description = description;
        this.recordDate = recordDate;
        this.recordScore = recordScore;
        this.recorderId = recorderId;
        this.recorderName = recorderName;
        this.title = title;
        this.name = name;
        this.imageUrl = imageUrl;
    }

    public List<ImageData> getImages() {
        return Collections.unmodifiableList(images);
    }

    public void setImages(List<ImageData> images) {
        this.images = images;
    }
}
