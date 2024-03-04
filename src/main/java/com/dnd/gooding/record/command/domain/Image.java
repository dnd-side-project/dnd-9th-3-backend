package com.dnd.gooding.record.command.domain;

import java.time.LocalDateTime;
import javax.persistence.*;
import lombok.Builder;

@Entity
@Table(name = "image")
public class Image {

    @EmbeddedId private ImageId id;

    @Column(name = "image_path")
    private String path;

    @Column(name = "upload_time")
    private LocalDateTime uploadTime;

    protected Image() {}

    @Builder
    public Image(ImageId id, String path, LocalDateTime uploadTime) {
        this.id = id;
        this.path = path;
        this.uploadTime = uploadTime;
    }

    public ImageId getId() {
        return id;
    }

    public String getPath() {
        return path;
    }

    public LocalDateTime getUploadTime() {
        return uploadTime;
    }
}
