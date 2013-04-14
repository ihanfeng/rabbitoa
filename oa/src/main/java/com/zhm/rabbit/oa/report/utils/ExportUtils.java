package com.zhm.rabbit.oa.report.utils;



import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRHtmlExporter;
import net.sf.jasperreports.engine.export.JRHtmlExporterParameter;
import net.sf.jasperreports.engine.export.JRRtfExporter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;

public class ExportUtils
{
	public static void exportExcel(JasperPrint jasperPrint,
		   HttpServletRequest request, HttpServletResponse response,String title) throws IOException, JRException {
		   /*
		    * 设置头信息
		    */
		   response.setContentType("application/vnd.ms-excel");
		   response.setHeader("Content-Transfer-Encoding","binary");   
		   response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");        
		   response.setHeader("Pragma", "public");  
		   String fileName = new String((title+".xls").getBytes("gb2312"), "ISO8859-1");
		   response.setHeader("Content-disposition", "attachment; filename="
		     + fileName);
		   ServletOutputStream ouputStream = response.getOutputStream();
		   JRXlsExporter exporter = new JRXlsExporter();
		   exporter
		     .setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
		   exporter.setParameter(JRExporterParameter.OUTPUT_STREAM,
		     ouputStream);
		   exporter.setParameter(
		     JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS,
		     Boolean.TRUE);
		   exporter.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET,
		     Boolean.FALSE);
		   exporter.setParameter(
		     JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND,
		     Boolean.FALSE);
		   exporter.exportReport();
		   ouputStream.flush();
		   ouputStream.close();
		 }
		 /**
		  * 导出pdf，注意此处中文问题， 1）在ireport的classpath中加入iTextAsian.jar
		  * 2）在ireport画jrxml时，pdf font name ：STSong-Light ，pdf encoding ：
		  * UniGB-UCS2-H
		  */
		 public static void exportPdf(JasperPrint jasperPrint,
		   HttpServletRequest request, HttpServletResponse response,String title) throws IOException, JRException {
		   response.setContentType("application/pdf");
		   response.setHeader("Content-Transfer-Encoding","binary");   
		   response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");        
		   response.setHeader("Pragma", "public");  
		   String fileName = new String((title+".pdf").getBytes("gb2312"), "ISO8859-1");
		   response.setHeader("Content-disposition", "attachment; filename="
		     + fileName);
		   ServletOutputStream ouputStream = response.getOutputStream();
		   JasperExportManager.exportReportToPdfStream(jasperPrint,
		     ouputStream);
		   ouputStream.flush();
		   ouputStream.close();
		 }
		 
		 public static void exportHtml(JasperPrint jasperPrint,
				   HttpServletRequest request, HttpServletResponse resp,String title) throws IOException, JRException {
			 JRExporter htmlExporter = new JRHtmlExporter();  
	        //设定输出格式
	        ByteArrayOutputStream htmlOut = new ByteArrayOutputStream();  
	        
	        htmlExporter.setParameter(JRHtmlExporterParameter.JASPER_PRINT,  
	                jasperPrint);  
	        htmlExporter.setParameter(JRHtmlExporterParameter.OUTPUT_STREAM,  
	        		htmlOut);  
	        htmlExporter.setParameter(JRHtmlExporterParameter.CHARACTER_ENCODING,  
	                "utf-8");  
	        htmlExporter  
	                .setParameter(JRHtmlExporterParameter.IS_USING_IMAGES_TO_ALIGN,  
	                        Boolean.FALSE);  
	        htmlExporter.exportReport();  
	                
//	        // 使用JRPdfExproter导出器导出pdf  
//	        JRPdfExporter exporter = new JRPdfExporter();  
//	        // 设置JasperPrintList  
//	        exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);  
//	        exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, ouputStream);  
//	        exporter.exportReport();
	        resp.setContentType("text/html; charset=utf-8");
	        PrintWriter out = resp.getWriter();
	        out.print(new String(htmlOut.toByteArray(),"utf-8"));
	        out.close();
	        htmlOut.close();  
		 }
		 /**
		  * 导出word
		  */
		 public static void exportWord(JasperPrint jasperPrint,
		   HttpServletRequest request, HttpServletResponse response,String title)
		   throws JRException, IOException {
		  response.setContentType("application/msword;charset=utf-8");
		  response.setHeader("Content-Transfer-Encoding","binary");   
		   response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");        
		   response.setHeader("Pragma", "public");  
		  String fileName = new String((title+".doc").getBytes("gb2312"), "ISO8859-1");
		  response.setHeader("Content-disposition", "attachment; filename="
		    + fileName);
		  JRExporter exporter = new JRRtfExporter();
		  exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
		  exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, response
		    .getOutputStream());
		  exporter.exportReport();
		 }
		 /**
		  * 打印
		  */
		 public static void exportPrint(JasperPrint jasperPrint,HttpServletRequest request,
		   HttpServletResponse response)
		   throws IOException {
		  response.setContentType("application/octet-stream");
		  ServletOutputStream ouputStream = response.getOutputStream();
		  ObjectOutputStream oos = new ObjectOutputStream(ouputStream);
		  oos.writeObject(jasperPrint);
		  oos.flush();
		  oos.close();
		  ouputStream.flush();
		  ouputStream.close();
		 }
}
