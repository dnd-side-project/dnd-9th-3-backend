package com.dnd.gooding.record.command.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.persistence.*;

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
  private LocalDateTime recordDate;

  @OneToMany(
      cascade = {CascadeType.PERSIST, CascadeType.REMOVE},
      orphanRemoval = true,
      fetch = FetchType.LAZY)
  @JoinColumn(name = "record_number")
  @OrderColumn(name = "list_idx")
  private List<Image> images = new ArrayList<>();

  public Record(
      RecordNo number,
      Coordinate coordinate,
      Recorder recorder,
      String title,
      String description,
      RecordState state,
      long recordScore,
      LocalDateTime recordDate,
      List<Image> images) {
    setNumber(number);
    setRecorder(recorder);
    this.title = title;
    this.description = description;
    this.coordinate = coordinate;
    this.state = state;
    this.recordScore = recordScore;
    this.recordDate = recordDate;
    this.images.addAll(images);
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

  public LocalDateTime getRecordDate() {
    return recordDate;
  }

  public List<Image> getImages() {
    return Collections.unmodifiableList(images);
  }

  public void changeImages(List<Image> newImages) {
    images.clear();
    images.addAll(newImages);
  }

  private void setNumber(RecordNo number) {
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
