package wang.flybird.api.weedfs.controller;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.lokra.seaweedfs.core.file.FileHandleStatus;
import org.lokra.seaweedfs.core.http.StreamResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import wang.flybird.api.weedfs.service.WeedFsService;
import wang.flybird.config.annotation.SysLogAnnotation;
import wang.flybird.utils.net.CookieUtil;

@RestController
@RequestMapping(path = "/api/weedfs")
@Api(value="WeedFs文件服务接口")
public class WeedFsController {
	
	protected Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private WeedFsService weedFsService;
	
	@SysLogAnnotation("上传文件")
	@RequestMapping(path = "/uploadfile", method = RequestMethod.POST)
	@ApiOperation(value = "上传文件",notes = "上传文件说明")
	@ApiImplicitParams({
	   @ApiImplicitParam(name="fileName",value="文件名",allowableValues="",required=true,dataType="string",paramType="query"),
	   @ApiImplicitParam(name="file",value="文件",allowableValues="",required=true,dataType="file",paramType="form")
	 })
	public FileHandleStatus uploadfile(String fileName, MultipartFile file){
		FileHandleStatus fileHandleStatus = null;
		try {
			
			fileHandleStatus = weedFsService.savefile(fileName, file.getInputStream());
		}catch (IOException e) {
			logger.error(null, e);
		}
		
		return fileHandleStatus;
	}
	
	@SysLogAnnotation("获取文件")
	@RequestMapping(path = "/getfile", method = {RequestMethod.POST,RequestMethod.GET})
	@ApiOperation(value = "获取文件",notes = "获取文件说明")
	@ApiImplicitParams({
	   @ApiImplicitParam(name = "token", value = "token", required = true,paramType = "header"),
	   @ApiImplicitParam(name="fid",value="文件id",allowableValues="",required=true,dataType="string",paramType="query"),
	   @ApiImplicitParam(name="fileName",value="文件名称",allowableValues="",required=true,dataType="string",paramType="query")
	 })
	public void getfile(String fid , String fileName , HttpServletResponse response){
		
		//
		//
		/**
		 * 验证用户权限
		 * 1.对请求参数进行解密
		 * 2.验证是否过期
		 * 3.
		 */
		
		
		StreamResponse StreamResponse = null;
		StreamResponse = weedFsService.getfile(fid);
		
		ServletOutputStream out;
		InputStream inputStream = StreamResponse.getInputStream();
		
		try {
	      response.setContentType(MediaType.MULTIPART_FORM_DATA_VALUE);
	      response.setHeader("Content-Disposition", "attachment;fileName="+fileName);
	          
		  out = response.getOutputStream();
		  
//		  int b = 0;  
//          byte[] buffer = new byte[512];  
//          while (b != -1){  
//              b = inputStream.read(buffer);  
//              //4.写到输出流(out)中  
//              out.write(buffer,0,b);  
//          }  
		  
		  byte[] buffer = new byte[1024];
	      int len = -1;
	      while((len = inputStream.read(buffer)) != -1){
	      	out.write(buffer,0,len);
	
          }
	      inputStream.close();
	      out.close();
		  
          inputStream.close();  
          out.close();  
          out.flush(); 
          
            
		
		} catch (IOException e) {  
            e.printStackTrace();  
        }

	}
	
	@SysLogAnnotation("获取文件")
	@RequestMapping(path = "/getfileByToken", method = {RequestMethod.POST,RequestMethod.GET})
	@ApiOperation(value = "获取文件",notes = "获取文件说明")
	@ApiImplicitParams({
		@ApiImplicitParam(name="token",value="权限令牌",allowableValues="",required=true,dataType="string",paramType="query"),
	   @ApiImplicitParam(name="fid",value="文件id",allowableValues="",required=true,dataType="string",paramType="query"),
	   @ApiImplicitParam(name="fileName",value="文件名称",allowableValues="",required=true,dataType="string",paramType="query")
	 })
	public void getfile(String fid , String fileName , String token, HttpServletResponse response){
		
		//
		//
		/**
		 * 验证用户权限
		 * 1.对请求参数进行解密
		 * 2.验证是否过期
		 * 3.
		 */
		
		if(!"123456".equals(token)){
			
		}else{

			StreamResponse StreamResponse = null;
			StreamResponse = weedFsService.getfile(fid);
			
			ServletOutputStream out;
			InputStream inputStream = StreamResponse.getInputStream();
			
			try {
		      response.setContentType(MediaType.MULTIPART_FORM_DATA_VALUE);
		      response.setHeader("Content-Disposition", "attachment;fileName="+fileName);
		          
			  out = response.getOutputStream();
			  
//			  int b = 0;  
//	          byte[] buffer = new byte[512];  
//	          while (b != -1){  
//	              b = inputStream.read(buffer);  
//	              //4.写到输出流(out)中  
//	              out.write(buffer,0,b);  
//	          }  
			  
			  byte[] buffer = new byte[1024];
		      int len = -1;
		      while((len = inputStream.read(buffer)) != -1){
		      	out.write(buffer,0,len);
		
	          }
		      inputStream.close();
		      out.close();
			  
	          inputStream.close();  
	          out.close();  
	          out.flush(); 
	          
	            
			
			} catch (IOException e) {  
	            e.printStackTrace();  
	        }

		}
		
	}
	
}
