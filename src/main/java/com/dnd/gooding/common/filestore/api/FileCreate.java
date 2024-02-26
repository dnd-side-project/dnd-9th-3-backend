package com.dnd.gooding.common.filestore.api;

import java.io.File;
import java.io.IOException;
import java.util.UUID;
import org.springframework.web.multipart.MultipartFile;

public class FileCreate {

    private FileCreate() {}

    public static String convert(MultipartFile multipartFile) throws IOException {
        String originName = multipartFile.getOriginalFilename();
        String newName = createFileName(originName);

        String fileDir = System.getProperty("user.dir") + "/src/main/resources/static/" + newName;
        File file = new File(fileDir);
        multipartFile.transferTo(file);

        return fileDir;
    }

    private static String createFileName(String originName) {
        String extension = extractExtension(originName);
        String uuid = UUID.randomUUID().toString();
        return uuid + "." + extension;
    }

    private static String extractExtension(String originName) {
        int position = originName.lastIndexOf(".");
        return originName.substring(position + 1);
    }
}
