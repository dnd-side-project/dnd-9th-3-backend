package com.dnd.gooding.domain.file.service.port;

import com.dnd.gooding.domain.file.domain.File;

public interface FileRepository {

	File save(File file);
}
