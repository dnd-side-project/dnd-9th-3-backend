package com.dnd.gooding.domain.file.dto.response;

import com.dnd.gooding.domain.file.model.File;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RecordFileResponse {

    private Long id;
    private String fileUrl;

    public RecordFileResponse(File file) {
        this.id = file.getId();
        this.fileUrl = file.getFileUrl();
    }
}
