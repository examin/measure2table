package com.sport.Utils;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;

public class ExcelUtils {

    private static void createExcel(ArrayList<Dimesion> allDimesions) {

        String excelFileName = "/home/examin/Videos/Axslogic.xlsx";
        XSSFWorkbook workbook = new XSSFWorkbook();
        Boolean firstTime = true;
        for (Dimesion currDimension : allDimesions) {

            String workBookname = currDimension.getName();
            if (!firstTime) {
                try {
                    workbook = new XSSFWorkbook(new FileInputStream(excelFileName));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                firstTime = false;

            }
            XSSFSheet sheet = workbook.createSheet(workBookname);
            int rowNum = 0;
            Set<String> directKeys = currDimension.getMeasureFoldersInDimension().getMeasures().keySet();
            for (String curr : directKeys) {
                Row row = sheet.createRow(rowNum++);
                Cell cell1 = row.createCell(0);
                cell1.setCellValue(curr);

                Cell cell2 = row.createCell(1);
                cell2.setCellValue(currDimension.getMeasureFoldersInDimension().getMeasures().get(curr));
            }


            System.out.println("Done");

        }

        try {
            FileOutputStream outputStream = new FileOutputStream(excelFileName);
            workbook.write(outputStream);
            workbook.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
