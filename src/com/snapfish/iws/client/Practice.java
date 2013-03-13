package com.snapfish.iws.client;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class Practice {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub

		
		FileInputStream myxls=new FileInputStream("C://Users//"+"sryedlapalli"+"//Desktop//UMAPI"+".xls");
		HSSFWorkbook wb=new HSSFWorkbook(myxls);
		HSSFSheet sheet=wb.createSheet("Results");
		HSSFRow rowhead=sheet.createRow(0);
		rowhead.createCell(1).setCellValue("API");
		rowhead.createCell(2).setCellValue("Payload");
		rowhead.createCell(3).setCellValue("Response");
		rowhead.createCell(4).setCellValue("Responsebody");
		FileOutputStream fileOut=new FileOutputStream("C://Users//"+"sryedlapalli"+"//Desktop//UMAPI"+".xls");
		wb.write(fileOut);
		fileOut.close();
		
	}

}
