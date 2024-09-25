package com.dnd.gooding.record.ui;

import com.dnd.gooding.record.command.application.in.BookMarkUseCase;
import com.dnd.gooding.record.command.application.in.CreateRecordUseCase;
import com.dnd.gooding.record.command.application.in.DeleteRecordUseCase;
import com.dnd.gooding.record.command.application.in.RecordReplaceUseCase;
import com.dnd.gooding.record.command.dto.Pageable;
import com.dnd.gooding.record.command.dto.RecordPlace;
import com.dnd.gooding.record.query.application.RecordQueryService;
import com.dnd.gooding.record.query.dto.FeedData;
import com.dnd.gooding.record.ui.dto.request.RecordRequest;
import com.dnd.gooding.token.command.domain.dto.JwtAuthentication;
import com.dnd.gooding.user.command.domain.MemberId;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class RecordController {

    private final CreateRecordUseCase createRecordUseCase;
    private final DeleteRecordUseCase deleteRecordUseCase;
    private final RecordReplaceUseCase recordReplaceUseCase;
    private final BookMarkUseCase bookMarkUseCase;
    private final RecordQueryService recordQueryService;

    @GetMapping("/feed")
    public ResponseEntity<List<FeedData>> getFeeds(Authentication authentication) {
        JwtAuthentication jwtAuthentication = (JwtAuthentication) authentication.getPrincipal();
        return ResponseEntity.status(HttpStatus.OK).body(recordQueryService.getFeed());
    }

    @PostMapping("/record")
    public ResponseEntity<Void> create(
            Authentication authentication,
            @RequestPart("files") List<MultipartFile> files,
            @RequestPart("recordRequest") String recordRequestJson)
            throws IOException {
        JwtAuthentication jwtAuthentication = (JwtAuthentication) authentication.getPrincipal();
        ObjectMapper objectMapper = new ObjectMapper();
        RecordRequest recordRequest = objectMapper.readValue(recordRequestJson, RecordRequest.class);
        recordRequest.setRecorderMemberId(MemberId.of(jwtAuthentication.getId()));
        recordRequest.setFiles(files);
        createRecordUseCase.create(recordRequest);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping("/record")
    public ResponseEntity<Void> delete(@RequestParam String recordNo) {
        deleteRecordUseCase.delete(recordNo);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/record/place")
    public ResponseEntity<Pageable<List<RecordPlace>>> getPlace(
            @RequestParam String keyword, @RequestParam int page, @RequestParam int size) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(recordReplaceUseCase.getPlaces(keyword, page, size));
    }

    @PostMapping("/bookmark")
    public ResponseEntity<Void> bookmark(
            Authentication authentication, @RequestBody String recordNo) {
        JwtAuthentication jwtAuthentication = (JwtAuthentication) authentication.getPrincipal();
        bookMarkUseCase.bookMark(jwtAuthentication.getId(), recordNo);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
