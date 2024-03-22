package com.dnd.gooding.unit.record.fixture;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

public class RecordFileFixture {

    public static List<MultipartFile> getFiles() throws IOException {
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
