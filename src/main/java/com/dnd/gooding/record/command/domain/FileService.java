package com.dnd.gooding.record.command.domain;

import java.io.File;

public interface FileService {

    String putFile(String key, File file);

    void deleteFile(String key);
}
