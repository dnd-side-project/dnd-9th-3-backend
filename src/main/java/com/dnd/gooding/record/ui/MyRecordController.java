package com.dnd.gooding.record.ui;

import com.dnd.gooding.record.query.application.RecordQueryService;
import com.dnd.gooding.record.query.dto.RecordData;
import com.dnd.gooding.token.command.domain.dto.JwtAuthentication;
import com.dnd.gooding.user.command.domain.MemberId;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/my/record")
public class MyRecordController {

    private final RecordQueryService recordQueryService;

    public MyRecordController(RecordQueryService recordQueryService) {
        this.recordQueryService = recordQueryService;
    }

    @GetMapping
    public ResponseEntity<List<RecordData>> getRecord(Authentication authentication) {
        JwtAuthentication jwtAuthentication = (JwtAuthentication) authentication.getPrincipal();
        List<RecordData> recordData =
                recordQueryService.getRecord(MemberId.of(jwtAuthentication.getId()));
        return ResponseEntity.status(HttpStatus.OK).body(recordData);
    }
}
