package com.dnd.gooding.record.ui;

import com.dnd.gooding.record.command.application.in.CreateRecordUseCase;
import com.dnd.gooding.record.command.application.in.DeleteRecordUseCase;
import com.dnd.gooding.record.command.application.in.RecordReplaceUseCase;
import com.dnd.gooding.record.command.dto.RecordPlace;
import com.dnd.gooding.record.ui.dto.request.RecordRequest;
import com.dnd.gooding.token.command.domain.dto.JwtAuthentication;
import com.dnd.gooding.user.command.domain.MemberId;
import java.io.IOException;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/record")
public class RecordController {

    private final CreateRecordUseCase createRecordUseCase;
    private final DeleteRecordUseCase deleteRecordUseCase;
    private final RecordReplaceUseCase recordReplaceUseCase;

    public RecordController(
            CreateRecordUseCase createRecordUseCase,
            DeleteRecordUseCase deleteRecordUseCase,
            RecordReplaceUseCase recordReplaceUseCase) {
        this.createRecordUseCase = createRecordUseCase;
        this.deleteRecordUseCase = deleteRecordUseCase;
        this.recordReplaceUseCase = recordReplaceUseCase;
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

    @GetMapping("/place")
    public ResponseEntity<List<RecordPlace>> getPlace(
            @RequestParam String keyword, @RequestParam int page, @RequestParam int size) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(recordReplaceUseCase.getPlaces(keyword, page, size));
    }
}
