package org.stb.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.hive2hive.core.api.interfaces.IH2HNode;
import org.hive2hive.core.exceptions.NoPeerConnectionException;
import org.hive2hive.core.exceptions.NoSessionException;
import org.hive2hive.core.processes.files.list.FileNode;
import org.hive2hive.processframework.exceptions.InvalidProcessStateException;
import org.hive2hive.processframework.exceptions.ProcessExecutionException;
import org.hive2hive.processframework.interfaces.IProcessComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.stb.service.FileDHTService;

public class FileDHTServiceImpl implements FileDHTService {

	Logger LOGGER = LoggerFactory.getLogger(FileDHTServiceImpl.class);
	@Override
	public List<String> getFileList(IH2HNode node) throws NoPeerConnectionException, NoSessionException, InvalidProcessStateException, ProcessExecutionException {
		IProcessComponent<FileNode> fileListProcess = node.getFileManager()
				.createFileListProcess();
		FileNode root = fileListProcess.execute();
		List<String> fileOnSTBList =printRecursively(root, 0);
		return fileOnSTBList;
	}

	@Override
	public void addFile(String fileName) {
		// TODO Auto-generated method stub

	}

	@Override
	public void downloadFile(String fileName) {
		// TODO Auto-generated method stub

	}

	@Override
	public void startObserver() {
		// TODO Auto-generated method stub

	}

	@Override
	public void stopObserver() {
		// TODO Auto-generated method stub

	}

	
	private List<String> printRecursively(FileNode node, int level) {
		
		List<String> fileList = new ArrayList<>();
		if (node.getParent() != null) {
			// skip the root node
			StringBuilder spaces = new StringBuilder("*");
			for (int i = 0; i < level; i++) {
				spaces.append(" ");
			}
			
			String nodeName =spaces.toString() + node.getName();
			fileList.add(nodeName);
			print(nodeName);
		}

		if (node.isFolder()) {
			for (FileNode child : node.getChildren()) {
				printRecursively(child, level + 1);
			}
		}
		return fileList;
	}
	
	public void print(String message) {
		LOGGER.info(message);
	}

	@Override
	public void syncFilesWithDHT(List<String> fileListOnDHT, File file) {
		
		
		
	}
}
