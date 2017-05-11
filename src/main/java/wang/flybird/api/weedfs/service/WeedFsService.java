package wang.flybird.api.weedfs.service;

import java.io.IOException;
import java.io.InputStream;

import org.lokra.seaweedfs.core.FileSource;
import org.lokra.seaweedfs.core.FileTemplate;
import org.lokra.seaweedfs.core.file.FileHandleStatus;
import org.lokra.seaweedfs.core.http.StreamResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Service
public class WeedFsService {
	
	protected Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private FileSource fileSource;

	@Autowired
	private FileTemplate fileTemplate;
	
	public FileHandleStatus savefile(String fileName, InputStream stream){
		FileHandleStatus fileHandleStatus = null;
		try {
			fileHandleStatus = fileTemplate.saveFileByStream(fileName, stream);
			logger.debug(fileHandleStatus.toString());
		} catch (IOException e) {
			logger.error("",e);
		}
		
		return fileHandleStatus;
	}
	
	public StreamResponse getfile(String fid){
		
		StreamResponse streamResponse = null;
		try {
			streamResponse = fileTemplate.getFileStream(fid);
		} catch (IOException e) {
			logger.error("",e);
		}
		
		return streamResponse;
	}
}
