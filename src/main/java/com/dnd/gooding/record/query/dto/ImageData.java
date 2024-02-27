package com.dnd.gooding.record.query.dto;

public class ImageData {

    private Long id;

    private String path;

    public ImageData(Long id, String path) {
        this.id = id;
        this.path = path;
    }

    public Long getId() {
        return id;
    }

    public String getPath() {
        return path;
    }
}
