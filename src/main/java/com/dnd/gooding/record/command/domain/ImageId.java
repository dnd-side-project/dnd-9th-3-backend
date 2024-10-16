package com.dnd.gooding.record.command.domain;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import lombok.Getter;

@Getter
@Embeddable
public class ImageId implements Serializable {

    @Column(name = "image_id")
    private String id;

    protected ImageId() {}

    public ImageId(String id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ImageId imageId = (ImageId) o;
        return Objects.equals(id, imageId.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public static ImageId of(String id) {
        return new ImageId(id);
    }
}
