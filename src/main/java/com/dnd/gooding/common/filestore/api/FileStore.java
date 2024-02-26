package com.dnd.gooding.common.filestore.api;

import java.io.File;

public interface FileStore {

    void delete(String key);

    String create(File file, String bucket, String fileName);
}
