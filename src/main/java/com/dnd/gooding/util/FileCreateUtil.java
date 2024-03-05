package com.dnd.gooding.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.UUID;
import org.springframework.web.multipart.MultipartFile;

public class FileCreateUtil {

    private static final String OS_NAME = "os.name";
    private static final String USER_HOME = "user.home";

    private FileCreateUtil() {}

    public static File convert(MultipartFile multipartFile) throws IOException {
        String newName = createFileName(multipartFile.getOriginalFilename());
        String path = Paths.get(getOsPath("gooding"), newName).toString();
        File file = new File(path);
        multipartFile.transferTo(file);
        return file;
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

    private static String getOsPath(String appHome) {
        if (isWindow())
            return (new File(String.format("%s:\\%s", getSystemDriver(), appHome))).getPath();
        if (isMacOS()) return (new File(String.format("/%s/%s", "Users", appHome))).getPath();
        if (isLinux()) return (new File(String.format("/%s/%s", "home", appHome))).getPath();
        return (new File(String.format("/%s", appHome))).getPath();
    }

    private static String getUserHomeDir() {
        return System.getProperty(USER_HOME);
    }

    private static String getSystemDriver() {
        String driverName = null;
        if (isWindow()) {
            String userDir = getUserHomeDir();
            driverName = userDir.substring(0, 1);
        }
        return driverName;
    }

    private static boolean isWindow() {
        return (System.getProperty(OS_NAME).toLowerCase().contains("win"));
    }

    private static boolean isMacOS() {
        return (System.getProperty(OS_NAME).toLowerCase().contains("mac"));
    }

    private static boolean isLinux() {
        return (System.getProperty(OS_NAME).toLowerCase().contains("linux"));
    }
}
