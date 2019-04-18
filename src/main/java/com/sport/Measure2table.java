package com.sport;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Measure2table {

    public static void main(String[] args) {
        HashMap<String, String> allMeasures = getAllMeasures();
        HashMap<String, Integer> measureLogicalOperationMap = getMeasureLogicalOperation();
        HashMap<String, Integer> measureAggregationFunctionMap = getMeasureAggregationFunction();
        HashMap<String, Integer> measureArithmeticOperationMap = getMeasureArithmeticOperation();
        HashMap<String, MeasureCondition> measureConditionMap = getMeasureCondition(measureLogicalOperationMap);
        HashMap<String, ProductStandardMeasure> productStandardMeasureMap = getProductStandardMeasureMap(allMeasures);
        System.out.println("ddvd");


    }

    private static HashMap<String, ProductStandardMeasure> getProductStandardMeasureMap(HashMap<String, String> allMeasures) {
        HashMap<String, ProductStandardMeasure> productStandardMeasures = new HashMap<>();
        HashSet<Solution> measuresFullSring = new HashSet(allMeasures.keySet());
        Iterator itr = measuresFullSring.iterator();
        int count = 0;
        while (itr.hasNext()) {
//            int numOfMatches  =  Pattern.compile("").matcher(itr.next().toString()).groupCount();
//            if(numOfMatches==1){
//                System.out.println("one found");
//            }

            Stack<Integer> stk = new Stack<Integer>();
            String curr = itr.next().toString();
            for (int i = 0; i < curr.length(); i++) {
                char ch = curr.charAt(i);
                if (ch == '(')
                    stk.push(i);
                else if (ch == ')') {
                    try {
                        int p = stk.pop() + 1;
                    } catch (Exception e) {
                        break;

                    }
                }
            }
            if (stk.isEmpty()) {
                count++;
            }
        }

        return productStandardMeasures;
    }


    private static HashMap<String, String> getAllMeasures() {
        HashMap<String, String> measureMap = new HashMap<>(); //this one have measureFullstring as id
        try {
            //Create the input stream from the xlsx/xls file
            FileInputStream fis = new FileInputStream("/home/examin/Videos/new/Axslogic.xlsx");
            Workbook workbook = new XSSFWorkbook(fis);
            int numberOfSheets = workbook.getNumberOfSheets();
            for (int i = 1; i < 2 /*numberOfSheets*/; i++) {
                int index = 1;
                Sheet sheet = workbook.getSheetAt(i);
                Iterator<Row> rowIterator = sheet.iterator();
                while (rowIterator.hasNext()) {
                    String measureFullString = "";
                    String measureName = "";

                    //Get the row object
                    Row row = rowIterator.next();

                    //Every row has columns, get the column iterator and iterate over them
                    Iterator<Cell> cellIterator = row.cellIterator();
                    while (cellIterator.hasNext()) {
                        //Get the Cell object
                        Cell cell = cellIterator.next();
                        switch (cell.getColumnIndex()) {
                            case 0:
                                measureName = cell.getStringCellValue();

                                break;
                            case 1:
                                measureFullString = cell.getStringCellValue();

                        }

                    }
                    //System.out.printf("%d, : %s\n",index++,measureName);
                    //System.out.print((measureMap.containsKey(measureFullString)) ? "*" + measureName + " and " + measureMap.get(measureFullString) + " \n" : "");
                    measureMap.put(measureFullString, measureName);
                }
            }
        } catch (Exception e) {

        }
        return measureMap;
    }

    private static HashMap<String, MeasureCondition> getMeasureCondition(HashMap<String, Integer> logicalOperation) {
        HashMap<String, MeasureCondition> measureConditions = new HashMap<>();

        //todo :  replace the next string with connection to xml2csv tool and using https://regex101.com/r/8NJFLE/2/ one can use group 1,2 and 3 to make objects of measureconditions.
        String[] values = new String[]{"ACCN_IN_FORC = 0", "ACCN_IN_FORC > 0", "ACCN_WITH_CASH_SAL_IN_MONT > 0", "ACCN_WITH_RETL_SALE_IN_MONT > 0", "ACTV_FLAG = 0", "ACTV_FLAG = 1", "APPL_DAT = STAT_DAT", "APPL_DAT_FK = STAT_DAT_FK", "APPL_SCOR < SCOR_CUTF", "APPL_SCOR = 0", "APPL_SCOR > 0", "BUR_SCOR > 0", "DECS_STAT_FK = 1", "DECS_STAT_FK = 2", "DECS_STAT_FK = 3", "DECS_STAT_FK = 4", "DELN_FK < 10", "DELN_FK < 11", "DELN_FK < 12", "DELN_FK < 13", "DELN_FK < 14", "DELN_FK < 3", "DELN_FK < 4", "DELN_FK < 5", "DELN_FK < 6", "DELN_FK < 7", "DELN_FK < 8", "DELN_FK < 9", "DELN_FK < PREV_MONT_DELN_FK", "DELN_FK = 1", "DELN_FK = 10", "DELN_FK = 11", "DELN_FK = 12", "DELN_FK = 13", "DELN_FK = 2", "DELN_FK = 3", "DELN_FK = 4", "DELN_FK = 5", "DELN_FK = 6", "DELN_FK = 7", "DELN_FK = 8", "DELN_FK = 9", "DELN_FK = PREV_MONT_DELN_FK", "DELN_FK > 1", "DELN_FK > 10", "DELN_FK > 11", "DELN_FK > 12", "DELN_FK > 13", "DELN_FK > 2", "DELN_FK > 3", "DELN_FK > 4", "DELN_FK > 5", "DELN_FK > 6", "DELN_FK > 7", "DELN_FK > 8", "DELN_FK > 9", "DELN_FK > PREV_MONT_DELN_FK", "DOCM_DU = 1", "EVR_CASH_EVR_RETL_ACCN > 0", "EVR_CASH_NEVR_RETL_ACCN > 0", "EXCP_INDC = 1", "MVL_ASSOXZ1 > 0", "MVL_ASSOXZ2 > 0", "NEVR_CASH_EVR_RETL_ACCN > 0", "NEVR_CASH_NEVR_RETL_ACCN > 0", "PREV_DAY_ACCN_IN_FORC > 0", "PREV_DAY_DELN_FK = 1", "PREV_DAY_DELN_FK = 10", "PREV_DAY_DELN_FK = 11", "PREV_DAY_DELN_FK = 12", "PREV_DAY_DELN_FK = 13", "PREV_DAY_DELN_FK = 14", "PREV_DAY_DELN_FK = 2", "PREV_DAY_DELN_FK = 3", "PREV_DAY_DELN_FK = 4", "PREV_DAY_DELN_FK = 5", "PREV_DAY_DELN_FK = 6", "PREV_DAY_DELN_FK = 7", "PREV_DAY_DELN_FK = 8", "PREV_DAY_DELN_FK = 9", "PREV_MONT_ACCN_IN_FORC > 0", "PREV_MONT_DELN_FK = 1", "PREV_MONT_DELN_FK = 10", "PREV_MONT_DELN_FK = 11", "PREV_MONT_DELN_FK = 12", "PREV_MONT_DELN_FK = 13", "PREV_MONT_DELN_FK = 14", "PREV_MONT_DELN_FK = 2", "PREV_MONT_DELN_FK = 3", "PREV_MONT_DELN_FK = 4", "PREV_MONT_DELN_FK = 5", "PREV_MONT_DELN_FK = 6", "PREV_MONT_DELN_FK = 7", "PREV_MONT_DELN_FK = 8", "PREV_MONT_DELN_FK = 9", "REVL_FLAG = 1", "REVL_FLAG = 2", "STAT_COD_FK = 12", "STAT_COD_FK = 2", "STAT_COD_FK = 3", "STAT_COD_FK = 4", "STAT_COD_FK = 5", "STAT_COD_FK = 6", "STAT_COD_FK = 9", "TOTL_CONT_EQU_PMT_PLAN_APR > 0", "VINT_FK = MONT_KEY"};

        int index = 1;
        for (String itr : values) {

            /* intislizing Object's field Block*/
            int conditionId = index++; //dirty code
            String conditionLeftField = "";
            String conditionRightField = "";
            String conditionRightValue = "";
            int measureOperationId = 0;
            int active = 1;//todo manupulate it accordingly as active cant be true allways
            String fullString = itr;
            /* Ends MeasureCondition */

            Matcher parts = Pattern.compile("(\\w+)\\s?(=|!=|>|<|>=|<=)\\s?(\\w+)|(\\w+)\\s(in|any|not in|between|not between|like)\\s?(\\w+)").matcher(itr);
            while (parts.find()) {
                // System.out.println("Full match: " + parts.group(0));
                conditionLeftField = parts.group(1);
                measureOperationId = logicalOperation.get(parts.group(2));
                if (Pattern.compile("[-+]?[\\d+]*\\.?[\\d+]").matcher(parts.group(3)).find()) {
                    conditionRightValue = parts.group(3);
                } else {
                    conditionRightField = parts.group(3);
                }
            }
            measureConditions.put(itr, new MeasureCondition(conditionId, conditionLeftField, conditionRightField, conditionRightValue, measureOperationId, active, fullString));
        }
        return measureConditions;
    }

    private static HashMap<String, Integer> getMeasureArithmeticOperation() {
        HashMap<String, Integer> measureArithmeticOperation = new HashMap<>();
        int index = 1;
        String[] values = new String[]{"+", "-", "/", "*", "%", "(", ")"};
        for (String itr : values) {
            measureArithmeticOperation.put(itr, index++);
        }

        return measureArithmeticOperation;
    }

    private static HashMap<String, Integer> getMeasureAggregationFunction() {
        HashMap<String, Integer> measureAggregationFunction = new HashMap<>();
        int index = 1;
        String[] values = new String[]{"avg", "count", "min", "max", "sum"};
        for (String itr : values) {
            measureAggregationFunction.put(itr, index++);
        }

        return measureAggregationFunction;
    }

    private static HashMap<String, Integer> getMeasureLogicalOperation() {
        HashMap<String, Integer> measureLogicalOperationsMap = new HashMap<>();
        int index = 1;
        String[] values = new String[]{"=", "!=", ">", "<", ">=", "<=", "in", "any", "not in", "between", "not between", "like"};
        for (String itr : values) {
            measureLogicalOperationsMap.put(itr, index++);
        }

        return measureLogicalOperationsMap;
    }


}

