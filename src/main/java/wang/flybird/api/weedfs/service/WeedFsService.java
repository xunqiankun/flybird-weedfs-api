package wang.flybird.api.weedfs.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

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
import wang.flybird.api.security.JwtTokenUtil;
import wang.flybird.entity.WfFile;
import wang.flybird.entity.enums.TrueOrFalse;
import wang.flybird.entity.repository.WfFileRepository;
import wang.flybird.utils.date.DateUtils;
import wang.flybird.utils.idwoker.IdWorker;

@Getter
@Setter
@Service
public class WeedFsService {
	
	protected Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private FileSource fileSource;

	@Autowired
	private FileTemplate fileTemplate;
	
	@Autowired
	private WfFileRepository wfFileRepository;
	
	@Autowired
	private IdWorker idWorker;
	
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	
	public FileHandleStatus savefile(String fileName, InputStream stream){
		FileHandleStatus fileHandleStatus = null;
		try {
			fileHandleStatus = fileTemplate.saveFileByStream(fileName, stream);
			
			String Fid = fileHandleStatus.getFileId();
			long Fsize = fileHandleStatus.getSize();
			saveWfFile(Fid,fileName,Fsize);
			
			logger.debug(fileHandleStatus.toString());
		} catch (IOException e) {
			logger.error("",e);
		}
		
		return fileHandleStatus;
	}
	
	private void saveWfFile(String fid,String fileName,long fsize){
		String id = idWorker.getStrId();
		String userid = jwtTokenUtil.getUserIdFromRequest();
		Date uptime = DateUtils.getCurrentDate();
		String delflag = TrueOrFalse.FALSE.getValue();
		
		WfFile wfFile = new WfFile();
		wfFile.setId(id);
		wfFile.setUsername(userid);
		wfFile.setFid(fid);
		wfFile.setFname(fileName);
		wfFile.setFsize(fsize);
		wfFile.setUptime(uptime);
		wfFile.setDelflag(delflag);
		
		wfFileRepository.save(wfFile);
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
