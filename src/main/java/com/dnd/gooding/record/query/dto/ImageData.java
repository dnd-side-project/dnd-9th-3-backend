package com.dnd.gooding.record.query.dto;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.Synchronize;

@Getter
@Entity
@Table(name = "image")
@Immutable
@Synchronize({"image"})
public class ImageData {

    @Id
    @Column(name = "image_id")
    private String id;

    @Column(name = "image_path")
    private String path;

    @Column(name = "type")
    private String type;

    @Column(name = "upload_time")
    private LocalDateTime uploadTime;

    @Column(name = "record_number")
    private String recordNumber;

    protected ImageData() {}

    @Builder
    public ImageData(String id, String path, String type, LocalDateTime uploadTime, String recordNumber) {
        this.id = id;
        this.path = path;
        this.type = type;
        this.uploadTime = uploadTime;
        this.recordNumber = recordNumber;
    }
}
