package com.dnd.gooding.record.ui;

import com.dnd.gooding.record.command.application.in.CreateRecordUseCase;
import com.dnd.gooding.record.command.application.in.DeleteRecordUseCase;
import com.dnd.gooding.record.ui.dto.request.RecordRequest;
import com.dnd.gooding.token.command.domain.dto.JwtAuthentication;
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

    private final CreateRecordUseCase createRecordUseCase;
    private final DeleteRecordUseCase deleteRecordUseCase;

    public RecordController(
            CreateRecordUseCase createRecordUseCase, DeleteRecordUseCase deleteRecordUseCase) {
        this.createRecordUseCase = createRecordUseCase;
        this.deleteRecordUseCase = deleteRecordUseCase;
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
        createRecordUseCase.create(recordRequest);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping
    public ResponseEntity<Void> delete(@RequestParam String recordNo) {
        deleteRecordUseCase.delete(recordNo);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
