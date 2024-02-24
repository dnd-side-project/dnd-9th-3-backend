package com.dnd.gooding.record.ui;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/record")
public class RecordController {

    @PostMapping
    public ResponseEntity<Void> getRecord() {
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
