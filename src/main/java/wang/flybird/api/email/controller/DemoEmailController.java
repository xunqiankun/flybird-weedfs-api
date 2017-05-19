package wang.flybird.api.email.controller;

import io.swagger.annotations.ApiOperation;
import wang.flybird.api.email.service.Pair;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.h2.util.New;
import org.springframework.web.bind.annotation.RequestMapping;  
import org.springframework.web.bind.annotation.RequestMethod;  
import org.springframework.web.bind.annotation.ResponseBody;  
import org.springframework.web.bind.annotation.RestController;  
  
  
@RestController  
@RequestMapping(value="/email")   
public class DemoEmailController{  
  
    @Resource  
    private wang.flybird.api.email.service.EmailService emailService;  
    
    /*** 
     * 创建表头 
     * @param workbook 
     * @param sheet 
     */  
    private void createTitle(HSSFWorkbook workbook, HSSFSheet sheet)  
    {  
        HSSFRow row = sheet.createRow(0);  
        //设置列宽，setColumnWidth的第二个参数要乘以256，这个参数的单位是1/256个字符宽度  
        sheet.setColumnWidth(2, 12*256);  
        sheet.setColumnWidth(3, 17*256);  
          
        //设置为居中加粗  
        HSSFCellStyle style = workbook.createCellStyle();  
        HSSFFont font = workbook.createFont();  
        font.setBold(true);  
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);  
        style.setFont(font);  
          
        HSSFCell cell;  
        cell = row.createCell(0);  
        cell.setCellValue("序号");  
        cell.setCellStyle(style);  
          
        cell = row.createCell(1);  
        cell.setCellValue("金额");  
        cell.setCellStyle(style);  
          
        cell = row.createCell(2);  
        cell.setCellValue("描述");  
        cell.setCellStyle(style);  
          
        cell = row.createCell(3);  
        cell.setCellValue("日期");  
        cell.setCellStyle(style);  
    }  
      
    /** 
     * 测试邮件发送 
     */  
    @ApiOperation(value="测试邮件发送", notes="sendSimpleMail")  
    @RequestMapping(value = "/getTestDemoEmail", method = RequestMethod.GET)  
    public @ResponseBody String sendSimpleMail() throws Exception {  
        String sendTo = "553074511@qq.com";  
        String titel = "测试邮件标题";  
        String content = "测试邮件内容";  
        emailService.sendSimpleMail(sendTo, titel, content);  
        return "secusse";  
    }  
    
    /** 
     * 测试邮件发送 
     */  
    @ApiOperation(value="测试邮件发送", notes="sendAttachmentsMail")  
    @RequestMapping(value = "/sendAttachmentsMail", method = RequestMethod.GET)  
    public @ResponseBody String sendAttachmentsMail() throws Exception {  
        String sendTo = "553074511@qq.com";  
        String titel = "测试邮件标题";  
        String content = "测试邮件内容";  
        
        HSSFWorkbook workbook = new HSSFWorkbook();  
        HSSFSheet sheet = workbook.createSheet("统计表");  
        createTitle(workbook, sheet);
        
        //设置日期格式  
        HSSFCellStyle style=workbook.createCellStyle();  
        style.setDataFormat(HSSFDataFormat.getBuiltinFormat("m/d/yy h:mm"));  
        
        //新增数据行，并且设置单元格数据  
//        int rowNum = 1;  
//        for (StatisticsInfo statisticsInfo:entities) {  
//              
//            HSSFRow row = sheet.createRow(rowNum);  
//            row.createCell(0).setCellValue(statisticsInfo.getId());  
//            row.createCell(1).setCellValue(statisticsInfo.getMoney().toString());  
//            row.createCell(2).setCellValue(statisticsInfo.getDescription());  
//            HSSFCell cell = row.createCell(3);  
//            cell.setCellValue(statisticsInfo.getCurrentdate());  
//            cell.setCellStyle(style);  
//            rowNum++;  
//        }  
        
        //拼装blobName  
        String fileName = "test.xls";  
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");  
        String dateTime = dateFormat.format(new Date());  
        String blobName =  dateTime + "/" + UUID.randomUUID().toString().replaceAll("-", "") + "/" + fileName;  

        
        
        List<Pair<String, File>> attachments = new ArrayList<Pair<String,File>>() ;
        File file = new File(fileName);
//        File file = new File
        FileOutputStream fileout = new FileOutputStream(file);		
        workbook.write(fileout);
        fileout.flush();    
        fileout.close();
        		
        Pair<String, File> pair = new Pair<String, File>(blobName, file);
        
        attachments.add(pair);
        
        emailService.sendAttachmentsMail(sendTo, titel, content,attachments);  
        return "secusse";  
    } 
} 
