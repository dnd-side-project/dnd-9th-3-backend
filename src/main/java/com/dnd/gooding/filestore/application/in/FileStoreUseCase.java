package com.dnd.gooding.filestore.application.in;

import java.io.File;

public interface FileStoreUseCase {

	String putFile(String key, File file);
	void deleteFile(String key);
}
