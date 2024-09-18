package com.dnd.gooding.record.command.domain;

import com.dnd.gooding.common.jpa.InterestConverter;
import com.dnd.gooding.common.model.Interest;
import com.dnd.gooding.common.model.InterestSet;
import com.dnd.gooding.common.model.RecordState;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Set;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Embedded;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Builder;

@Entity
@Table(name = "record")
@Access(AccessType.FIELD)
public class Record {

    @EmbeddedId private RecordNo number;
    @Embedded private Coordinate coordinate;
    @Embedded private Recorder recorder;
    private String title;
    private String description;

    @Column(name = "state")
    @Enumerated(EnumType.STRING)
    private RecordState state;

    @Column(name = "record_score")
    private long recordScore;

    @Column(name = "record_date")
    private Date recordDate;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "record_number")
    private List<Image> images = new ArrayList<>();

    @Column(name = "interests")
    @Convert(converter = InterestConverter.class)
    private InterestSet interests;

    protected Record() {}

    @Builder
    public Record(
            RecordNo number,
            Coordinate coordinate,
            Recorder recorder,
            String title,
            String description,
            RecordState state,
            long recordScore,
            Date recordDate,
            List<Image> images,
            InterestSet interests) {
        setRecordNo(number);
        setRecorder(recorder);
        this.title = title;
        this.description = description;
        this.coordinate = coordinate;
        this.state = state;
        this.recordScore = recordScore;
        this.recordDate = recordDate;
        this.images.addAll(images);
        this.interests = interests;
    }

    public RecordNo getNumber() {
        return number;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public Recorder getRecorder() {
        return recorder;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public RecordState getState() {
        return state;
    }

    public long getRecordScore() {
        return recordScore;
    }

    public Date getRecordDate() {
        return recordDate;
    }

    public List<Image> getImages() {
        return Collections.unmodifiableList(images);
    }

    public InterestSet getInterests() {
        return interests;
    }

    public void changeImages(List<Image> newImages) {
        images.clear();
        images.addAll(newImages);
    }

    public void changeInterests(Set<Interest> interests) {
        this.interests = new InterestSet(interests);
    }

    private void setRecordNo(RecordNo number) {
        if (number == null) {
            throw new IllegalArgumentException("no number");
        }
        this.number = number;
    }

    private void setRecorder(Recorder recorder) {
        if (recorder == null) {
            throw new IllegalArgumentException("no recorder");
        }
        this.recorder = recorder;
    }
}
