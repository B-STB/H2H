package org.stb.service;

import java.util.List;

public interface FileDHTService {

	List<String> getFileList();
	
	void addFile(String fileName);
	
	void downloadFile(String fileName);
	
	void startObserver();
	
	void stopObserver();
	
}
