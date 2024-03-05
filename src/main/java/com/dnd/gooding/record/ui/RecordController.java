package com.dnd.gooding.record.ui;

import com.dnd.gooding.record.command.application.RecordRequest;
import com.dnd.gooding.record.command.application.RecordService;
import com.dnd.gooding.token.command.model.JwtAuthentication;
import com.dnd.gooding.user.command.domain.MemberId;
import java.io.IOException;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/record")
public class RecordController {

    private final RecordService recordService;

    public RecordController(RecordService recordService) {
        this.recordService = recordService;
    }

    @PostMapping
    public ResponseEntity<Void> create(
            Authentication authentication,
            @RequestPart("files") List<MultipartFile> files,
            @RequestPart("recordRequest") RecordRequest recordRequest)
            throws IOException {
        JwtAuthentication jwtAuthentication = (JwtAuthentication) authentication.getPrincipal();
        recordRequest.setRecorderMemberId(MemberId.of(jwtAuthentication.getId()));
        recordRequest.setFiles(files);
        recordService.create(recordRequest);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping
    public ResponseEntity<Void> delete(@RequestParam String recordNo) {
        recordService.delete(recordNo);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
