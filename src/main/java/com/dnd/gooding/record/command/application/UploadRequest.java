package com.dnd.gooding.record.command.application;

import com.dnd.gooding.common.model.Interest;
import com.dnd.gooding.common.model.RecordOpenStatus;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import javax.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

public class UploadRequest {
    private String description;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime recordDate;

    private String placeTitle;
    private Double placeLatitude;
    private Double placeLongitude;
    @NotNull private RecordOpenStatus recordOpen;
    @NotNull private Integer recordScore;
    private Set<Interest> interests = new HashSet<>();

    public UploadRequest() {}

    public UploadRequest(
            String description,
            LocalDateTime recordDate,
            String placeTitle,
            Double placeLatitude,
            Double placeLongitude,
            RecordOpenStatus recordOpen,
            Integer recordScore,
            Set<Interest> interests) {
        this.description = description;
        this.recordDate = recordDate;
        this.placeTitle = placeTitle;
        this.placeLatitude = placeLatitude;
        this.placeLongitude = placeLongitude;
        this.recordOpen = recordOpen;
        this.recordScore = recordScore;
        this.interests = interests;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getRecordDate() {
        return recordDate;
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

    public RecordOpenStatus getRecordOpen() {
        return recordOpen;
    }

    public Integer getRecordScore() {
        return recordScore;
    }

    public Set<Interest> getInterests() {
        return interests;
    }
}
