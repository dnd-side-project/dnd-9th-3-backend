package com.dnd.gooding.record.query.dto;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Builder;
import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.Synchronize;

@Entity
@Table(name = "image")
@Immutable
@Synchronize({"image"})
public class ImageData {

    @Id
    @Column(name = "image_id")
    private Long id;

    @Column(name = "image_path")
    private String path;

    @Column(name = "upload_time")
    private LocalDateTime uploadTime;

    @Column(name = "record_number")
    private String recordNumber;

    protected ImageData() {}

    @Builder
    public ImageData(Long id, String path, LocalDateTime uploadTime, String recordNumber) {
        this.id = id;
        this.path = path;
        this.uploadTime = uploadTime;
        this.recordNumber = recordNumber;
    }

    public Long getId() {
        return id;
    }

    public String getPath() {
        return path;
    }

    public LocalDateTime getUploadTime() {
        return uploadTime;
    }

    public String getRecordNumber() {
        return recordNumber;
    }
}
