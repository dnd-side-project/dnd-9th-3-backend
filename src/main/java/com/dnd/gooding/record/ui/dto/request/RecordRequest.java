package com.dnd.gooding.record.ui.dto.request;

import com.dnd.gooding.common.model.Interest;
import com.dnd.gooding.common.model.RecordState;
import com.dnd.gooding.user.command.domain.MemberId;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

public class RecordRequest {

    @JsonIgnore private MemberId recorderMemberId;

    @NotNull private String title;

    private String description;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime recordDate;

    private String placeTitle;
    private Double placeLatitude;
    private Double placeLongitude;
    @NotNull private RecordState state;
    @NotNull private Integer recordScore;
    private Set<Interest> interests = new HashSet<>();

    private List<MultipartFile> files = new ArrayList<>();

    public RecordRequest() {}

    public RecordRequest(
            MemberId recorderMemberId,
            String title,
            String description,
            LocalDateTime recordDate,
            String placeTitle,
            Double placeLatitude,
            Double placeLongitude,
            RecordState state,
            Integer recordScore,
            Set<Interest> interests,
            List<MultipartFile> files) {
        this.recorderMemberId = recorderMemberId;
        this.title = title;
        this.description = description;
        this.recordDate = recordDate;
        this.placeTitle = placeTitle;
        this.placeLatitude = placeLatitude;
        this.placeLongitude = placeLongitude;
        this.state = state;
        this.recordScore = recordScore;
        this.interests = interests;
        this.files = files;
    }

    public MemberId getRecorderMemberId() {
        return recorderMemberId;
    }

    public void setRecorderMemberId(MemberId recorderMemberId) {
        this.recorderMemberId = recorderMemberId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getRecordDate() {
        return recordDate;
    }

    public void setRecordDate(LocalDateTime recordDate) {
        this.recordDate = recordDate;
    }

    public String getPlaceTitle() {
        return placeTitle;
    }

    public void setPlaceTitle(String placeTitle) {
        this.placeTitle = placeTitle;
    }

    public Double getPlaceLatitude() {
        return placeLatitude;
    }

    public void setPlaceLatitude(Double placeLatitude) {
        this.placeLatitude = placeLatitude;
    }

    public Double getPlaceLongitude() {
        return placeLongitude;
    }

    public void setPlaceLongitude(Double placeLongitude) {
        this.placeLongitude = placeLongitude;
    }

    public RecordState getState() {
        return state;
    }

    public void setState(RecordState state) {
        this.state = state;
    }

    public Integer getRecordScore() {
        return recordScore;
    }

    public void setRecordScore(Integer recordScore) {
        this.recordScore = recordScore;
    }

    public Set<Interest> getInterests() {
        return interests;
    }

    public void setInterests(Set<Interest> interests) {
        this.interests = interests;
    }

    public List<MultipartFile> getFiles() {
        return files;
    }

    public void setFiles(List<MultipartFile> files) {
        this.files = files;
    }
}
