package com.dnd.gooding.record.infra;

import com.dnd.gooding.common.filestore.infra.S3ServiceStore;
import com.dnd.gooding.record.command.domain.FileService;
import java.io.File;
import org.springframework.stereotype.Service;

@Service
public class FileServiceImpl implements FileService {

    private final S3ServiceStore s3ServiceStore;

    public FileServiceImpl(S3ServiceStore s3ServiceStore) {
        this.s3ServiceStore = s3ServiceStore;
    }

    @Override
    public String putFile(String key, File file) {
        return s3ServiceStore.putFile(key, file);
    }

    @Override
    public void deleteFile(String key) {
        s3ServiceStore.deleteFile(key);
    }
}