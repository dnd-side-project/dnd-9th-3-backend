package com.dnd.gooding.record.ui;

import com.dnd.gooding.record.command.application.UploadRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/record")
public class RecordController {

    @PostMapping(value = "/upload")
    public ResponseEntity<Void> upload(
            @RequestPart("files") List<MultipartFile> files,
            @RequestPart("memberId") @Valid String memberId,
            @RequestPart("uploadRequest") UploadRequest uploadRequest) {
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
