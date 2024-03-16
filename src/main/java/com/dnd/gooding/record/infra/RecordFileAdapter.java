package com.dnd.gooding.record.infra;

import com.dnd.gooding.filestore.application.in.FileStoreUseCase;
import com.dnd.gooding.record.command.application.out.RecordFilePort;
import java.io.File;
import org.springframework.stereotype.Service;

@Service
public class RecordFileAdapter implements RecordFilePort {

    private final FileStoreUseCase fileStoreUseCase;

    public RecordFileAdapter(FileStoreUseCase fileStoreUseCase) {
        this.fileStoreUseCase = fileStoreUseCase;
    }

    @Override
    public String putFile(String key, File file) {
        return fileStoreUseCase.putFile(key, file);
    }

    @Override
    public void deleteFile(String key) {
        fileStoreUseCase.deleteFile(key);
    }
}
