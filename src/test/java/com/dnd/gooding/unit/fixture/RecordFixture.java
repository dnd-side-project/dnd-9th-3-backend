package com.dnd.gooding.unit.fixture;

import com.dnd.gooding.common.model.RecordState;
import com.dnd.gooding.record.command.domain.Coordinate;
import com.dnd.gooding.record.command.domain.Record;
import com.dnd.gooding.record.command.domain.RecordNo;
import com.dnd.gooding.record.command.domain.Recorder;
import com.dnd.gooding.user.command.domain.MemberId;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

public class RecordFixture {

    public static Record getRecord() {
        return Record.builder()
                .number(RecordNo.of("20240322-12356"))
                .title("ocean view")
                .description("good")
                .recordDate(new Date())
                .recorder(
                        Recorder.builder()
                                .memberId(MemberId.of("youg1322@naver.com"))
                                .memberName("haeyong")
                                .build())
                .coordinate(
                        Coordinate.builder()
                                .placeTitle("seoul")
                                .placeLatitude(1123.567)
                                .placeLongitude(1516771.23)
                                .build())
                .state(RecordState.PRIVATE)
                .recordScore(20)
                .images(new ArrayList<>())
                .build();
    }

    private static List<MultipartFile> getFiles() throws IOException {
        List<MultipartFile> files = new ArrayList<>();
        MockMultipartFile file =
                getMockMultipartFile("sample", "txt", "src/test/resources/static/sample.txt");
        files.add(file);
        return files;
    }

    private static MockMultipartFile getMockMultipartFile(
            String fileName, String contentType, String path) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(new File(path));
        return new MockMultipartFile(
                fileName, fileName + "." + contentType, contentType, fileInputStream);
    }
}
