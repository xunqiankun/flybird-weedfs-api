package wang.flybird.config;

import java.io.IOException;

import org.lokra.seaweedfs.core.FileSource;
import org.lokra.seaweedfs.core.FileTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import wang.flybird.api.weedfs.service.WeedFsService;

@Configuration
public class WeedFsConfig {
	@Bean(name = "fileSource")
    public FileSource fileSource(
    		@Value(value = "${weedfs.host}") String host,
    		@Value(value = "${weedfs.port}") int port){
		FileSource fileSource = new FileSource();
		// SeaweedFS master server host
		fileSource.setHost(host);
		// SeaweedFS master server port
		fileSource.setPort(port);
		// Startup manager and listens for the change
		try {
			fileSource.startup();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		return fileSource;
    }
	
	@Bean(name = "fileTemplate")
	public FileTemplate fileTemplate(FileSource fileSource){
		FileTemplate template = new FileTemplate(fileSource.getConnection());
		return template;
	}
	
	@Bean(name = "weedFsService")
	public WeedFsService weedFsService(FileSource fileSource,FileTemplate fileTemplate){
		WeedFsService weedFsService = new WeedFsService();
		weedFsService.setFileSource(fileSource);
		weedFsService.setFileTemplate(fileTemplate);
		return weedFsService;
	}
}
