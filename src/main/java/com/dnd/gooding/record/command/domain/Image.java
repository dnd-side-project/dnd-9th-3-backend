package com.dnd.gooding.record.command.domain;

import java.time.LocalDateTime;
import javax.persistence.*;
import lombok.Builder;
import lombok.Getter;

@Getter
@Entity
@Table(name = "image")
public class Image {

    @EmbeddedId private ImageId id;

    @Column(name = "image_path")
    private String path;

    @Column(name = "type")
    private String type;

    @Column(name = "upload_time")
    private LocalDateTime uploadTime;

    protected Image() {}

    @Builder
    public Image(ImageId id, String path, String type, LocalDateTime uploadTime) {
        this.id = id;
        this.path = path;
        this.type = type;
        this.uploadTime = uploadTime;
    }
}
