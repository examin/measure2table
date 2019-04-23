package com.sport.Utils;

import com.sport.MeasureCondition;
import com.sport.MeasureConditionMapping;
import com.sport.ProductStandardMeasure;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.TreeMap;

public class ExcelUtils {
    public static void createExcel123(HashMap<String, Integer> Rows, String workBookname,Boolean needNewFile) {
        HashSet<String> itr = new HashSet(Rows.keySet());
        String excelFileName = "/home/examin/Videos/Finalize.xlsx";
        XSSFWorkbook workbook = new XSSFWorkbook();
        Boolean firstTime = needNewFile;
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
        Row row = sheet.createRow(rowNum++);
        Cell cell1 = row.createCell(0);
        cell1.setCellValue("Id");

        Cell cell2 = row.createCell(1);
        cell2.setCellValue(workBookname.replaceAll("(?i)measure",""));

        Cell cell3 = row.createCell(2);
        cell3.setCellValue("active");

        for (String currRow : itr) {
            row = sheet.createRow(rowNum++);
            cell1 = row.createCell(0);
            cell1.setCellValue(Rows.get(currRow));

            cell2 = row.createCell(1);
            cell2.setCellValue(currRow);

            cell3 = row.createCell(2);
            cell3.setCellValue(1);

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
        System.out.println("Done");
    }


    public static void createWorksheet1(HashMap<String, MeasureCondition> measureConditionMap) {
        HashSet<String> itr = new HashSet(measureConditionMap.keySet());
        String excelFileName = "/home/examin/Videos/Finalize.xlsx";
        XSSFWorkbook workbook = new XSSFWorkbook();
        Boolean firstTime = false;
        String workBookname = "MeasureCondition";
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
        Row row = sheet.createRow(rowNum++);
        Cell cell1 = row.createCell(0);
        cell1.setCellValue("ConditionId");

        Cell cell2 = row.createCell(1);
        cell2.setCellValue("conditionLeftField");

        Cell cell3 = row.createCell(2);
        cell3.setCellValue("conditionRightField");

        Cell cell4 = row.createCell(3);
        cell4.setCellValue("conditionRightValue");

        Cell cell5 = row.createCell(4);
        cell5.setCellValue("measureOperationId");

        Cell cell6 = row.createCell(5);
        cell6.setCellValue("active");

        Cell cell7 = row.createCell(6);
        cell7.setCellValue("Reference");


        for (String currRow : itr) {
            MeasureCondition now = measureConditionMap.get(currRow);
            row = sheet.createRow(rowNum++);
            cell1 = row.createCell(0);
            cell1.setCellValue(now.getConditionId());

            cell2 = row.createCell(1);
            cell2.setCellValue(now.getConditionLeftField());

            cell3 = row.createCell(2);
            cell3.setCellValue(now.getConditionRightField());

            cell4 = row.createCell(3);
            cell4.setCellValue(now.getConditionRightValue());

            cell5 = row.createCell(4);
            cell5.setCellValue(now.getMeasureOperationId());

            cell6 = row.createCell(5);
            cell6.setCellValue(now.getActive());

            cell7 = row.createCell(6);
            cell7.setCellValue(currRow);

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
        System.out.println("Done");
    }
    public static void createWorksheet2(TreeMap<String, ProductStandardMeasure> measureConditionMap) {
        HashSet<String> itr = new HashSet(measureConditionMap.keySet());
        String excelFileName = "/home/examin/Videos/Finalize.xlsx";
        XSSFWorkbook workbook = new XSSFWorkbook();
        Boolean firstTime = false;
        String workBookname = "ProductStandardMeasure";
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
        Row row = sheet.createRow(rowNum++);
        Cell cell1 = row.createCell(0);
        cell1.setCellValue("MeasureId");

        Cell cell2 = row.createCell(1);
        cell2.setCellValue("MeasureName");

        Cell cell3 = row.createCell(2);
        cell3.setCellValue("MeasureFunction");

        Cell cell4 = row.createCell(3);
        cell4.setCellValue("MeasureField");

        Cell cell5 = row.createCell(4);
        cell5.setCellValue("ProductName");

        Cell cell6 = row.createCell(5);
        cell6.setCellValue("SubscriberId");

        Cell cell7 = row.createCell(6);
        cell7.setCellValue("active");

        Cell cell8 = row.createCell(7);
        cell8.setCellValue("Reference");


        for (String currRow : itr) {
            ProductStandardMeasure now = measureConditionMap.get(currRow);
            row = sheet.createRow(rowNum++);
            cell1 = row.createCell(0);
            cell1.setCellValue(now.getId());

            cell2 = row.createCell(1);
            cell2.setCellValue(now.getMeasureName());

            cell3 = row.createCell(2);
            cell3.setCellValue(now.getMeasureFunctionId());

            cell4 = row.createCell(3);
            cell4.setCellValue(now.getMeasureField());

            cell5 = row.createCell(4);
            cell5.setCellValue(now.getProductName());

            cell6 = row.createCell(5);
            cell6.setCellValue(now.getSubscriberId());

            cell7 = row.createCell(6);
            cell7.setCellValue(now.getActive());

            cell8 = row.createCell(7);
            cell8.setCellValue(currRow);

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
        System.out.println("Done");
    }

    public static void createWorksheet3(TreeMap<String, ArrayList<MeasureConditionMapping>> measureConditionMappingIdMap) {
        HashSet<String> itr = new HashSet(measureConditionMappingIdMap.keySet());
        String excelFileName = "/home/examin/Videos/Finalize.xlsx";
        XSSFWorkbook workbook = new XSSFWorkbook();
        Boolean firstTime = false;
        String workBookname = "MeasureConditionMapping";
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
        Row row = sheet.createRow(rowNum++);
        Cell cell1 = row.createCell(0);
        cell1.setCellValue("Id");

        Cell cell2 = row.createCell(1);
        cell2.setCellValue("measureId");

        Cell cell3 = row.createCell(2);
        cell3.setCellValue("measureConditionid");

        Cell cell4 = row.createCell(3);
        cell4.setCellValue("conditionIndex");

        Cell cell5 = row.createCell(4);
        cell5.setCellValue("conditionJoin");

        Cell cell6 = row.createCell(5);
        cell6.setCellValue("active");

        Cell cell7 = row.createCell(6);
        cell7.setCellValue("Reference");


        for (String currRow : itr) {
            ArrayList<MeasureConditionMapping> mappingList = measureConditionMappingIdMap.get(currRow);
            String reference = currRow;
            for(MeasureConditionMapping now : mappingList ) {
                row = sheet.createRow(rowNum++);
                cell1 = row.createCell(0);
                cell1.setCellValue(now.getID());

                cell2 = row.createCell(1);
                cell2.setCellValue(now.getMeasureId());

                cell3 = row.createCell(2);
                cell3.setCellValue(now.getMeasureConditionid());

                cell4 = row.createCell(3);
                cell4.setCellValue(now.getConditionIndex());

                cell5 = row.createCell(4);
                cell5.setCellValue(now.getConditionJoin());

                cell6 = row.createCell(5);
                cell6.setCellValue(now.getActive());

                cell7 = row.createCell(6);
                cell7.setCellValue(reference);

                reference ="";
            }

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
        System.out.println("Done");
    }
}
