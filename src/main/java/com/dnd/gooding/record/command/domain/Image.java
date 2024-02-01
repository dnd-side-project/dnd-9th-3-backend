package com.dnd.gooding.record.command.domain;

import java.time.LocalDateTime;
import javax.persistence.*;

@Entity
@Table(name = "image")
public class Image {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "image_id")
  private Long id;

  @Column(name = "image_path")
  private String path;

  @Column(name = "upload_time")
  private LocalDateTime uploadTime;

  protected Image() {}

  public Image(String path, LocalDateTime uploadTime) {
    this.path = path;
    this.uploadTime = uploadTime;
  }

  public String getPath() {
    return path;
  }

  public LocalDateTime getUploadTime() {
    return uploadTime;
  }
}
