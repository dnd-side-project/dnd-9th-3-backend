package com.dnd.gooding.record.command.application.out;

import java.io.File;

public interface RecordFilePort {

    String putFile(String key, File file);

    void deleteFile(String key);
}
