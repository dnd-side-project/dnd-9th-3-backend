package com.dnd.gooding.record.query;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "record")
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

  protected RecordData() {}

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
}
