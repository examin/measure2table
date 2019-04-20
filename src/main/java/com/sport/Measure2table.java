package com.sport;

import com.sport.Utils.Utils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Measure2table {
    public static int fakeNamePointer = 0;

    public static void main(String[] args) {
        HashMap<String, String> allMeasures = getAllMeasures();

        //todo 1: MeasureLogicalOperations worksheet
        HashMap<String, Integer> measureLogicalOperationMap = getMeasureLogicalOperation();

        //todo 2:  measureAggregationFunction workheet
        HashMap<String, Integer> measureAggregationFunctionMap = getMeasureAggregationFunction();

        //todo 3:  measureArithmeticOperation worksheet
        HashMap<String, Integer> measureArithmeticOperationMap = getMeasureArithmeticOperation();

        //this getMeasureCondition read and then use logicalOperations
        //todo 4 : MeasureConditionMap have MeasureCondition... worksheet
        HashMap<String, MeasureCondition> measureConditionMap = getMeasureCondition(measureLogicalOperationMap);

        TreeMap<String, String>[] dividedAtomicAndCompundedArray = divideAtomicAndCompunded(allMeasures);

        //todo 5 : Make MeasureConditionMapping For Atomic Measure from atomicMeasuresStatement
        TreeMap<String, String> atomicMeasuresStatementMap = dividedAtomicAndCompundedArray[0];
        //todo 6 : ProductStandardMeasure worksheet
        TreeMap<String,ProductStandardMeasure> productStandardMeasures = atomicMeasuresStatementMap2ProductStandardSheet(atomicMeasuresStatementMap, measureAggregationFunctionMap);

        //todo 7 : compoundedMeasuresStatement worksheet
        TreeMap<String, String> compoundedMeasuresStatementMap = dividedAtomicAndCompundedArray[1];



//        HashMap<String, ProductStandardMeasure>[] productStandardMeasureMap = getProductStandardMeasureMap(allMeasures);
        System.out.println("ddvd");


    }

    private static TreeMap<String, ProductStandardMeasure> atomicMeasuresStatementMap2ProductStandardSheet(TreeMap<String, String> atomicMeasuresStatementMap, HashMap<String, Integer> measureAggregationFunctionMap) {
    TreeMap<String,ProductStandardMeasure>  productStandardMeasuresMap=new TreeMap<>();
    HashSet<String> iterate = new HashSet<>(atomicMeasuresStatementMap.keySet());
    int idCount =1;
    for(String curr : iterate){

        int id = idCount++;
        String measureName = atomicMeasuresStatementMap.get(curr);
        int measureFunctionId = 0 ;
        String measureField = "";
        try {
            measureFunctionId = Utils.getMeasureAggIdFromStament(curr,measureAggregationFunctionMap);
        }
        catch(IllegalArgumentException e){
            e.printStackTrace();
            continue;
        }
        try {
            measureField = Utils.getMeasureFieldFromStament(curr);
        }catch (IllegalArgumentException e){
            e.printStackTrace();
        }
        String productName = "portfolio_daily";
        int subscriberId = 1;
        int active = 1;
        String fullString = curr;

        productStandardMeasuresMap.put(curr,new ProductStandardMeasure(id,measureName,measureFunctionId,measureField,productName,subscriberId,active) );
    }

    return  productStandardMeasuresMap;
    }

    private static TreeMap<String, String>[] divideAtomicAndCompunded(HashMap<String, String> allMeasures) {
        TreeMap<String, String> atomicMeasures = new TreeMap<>();
        TreeMap<String, String> compoundedMeasures = new TreeMap<>();
        HashSet<Solution> measuresFullSring = new HashSet(allMeasures.keySet());
        Iterator itr = measuresFullSring.iterator();
        while (itr.hasNext()) {
            String curr = itr.next().toString();
            String[] toFInd = new String[]{"sum", "count", "min", "max", "avg"};
            int countOfAggInMeasure = 0;
            for (String pattern : toFInd) {
                countOfAggInMeasure += new KMPStringMatching().KMPSearch(pattern, curr);
            }
            if (countOfAggInMeasure == 1) {
                //were 48
                //System.out.println(curr);
                atomicMeasures.put(curr, allMeasures.get(curr));
            } else {
                // todo : Note if something is compounded break it into parts and assign them new fake names and also keep track wee need them
                //119
                //System.out.println("# " + curr);
                compoundedMeasures.put(curr, allMeasures.get(curr));
                atomicMeasures.putAll(getFakeNamedMeasure(curr,atomicMeasures));
            }
        }
        return new TreeMap[]{atomicMeasures, compoundedMeasures};
    }

    private static HashMap<String, String> getFakeNamedMeasure(String str,TreeMap<String,String> atomicMeasures) {
        HashMap<String, String> atomicMeasure = new HashMap<>();
        Pattern pattern = Pattern.compile("(avg|count|min|max|sum)");  // insert your pattern here
        Matcher matcher = pattern.matcher(str);
        int numOfAgg = 0;
        while (matcher.find()) {
            int position = matcher.start();
            numOfAgg++;
            //System.out.print("  position: " + position);
            StringBuilder sb = new StringBuilder();
            Stack<Integer> stack = new Stack<>();
            char[] strChar = str.toCharArray();
            int currLength= strChar.length;
            int i =position;
            for (;i<currLength;i++) {
                char charNow =  strChar[i];
                sb.append(charNow);
                if(charNow=='(') {
                    stack.push(1);
                    break;
                }
            }
            while (!stack.isEmpty()){
                i++;
                char charNow =  strChar[i];
                sb.append(charNow);
                if(charNow==')') {
                    try {
                        stack.pop();
                    }
                    catch (Exception e){
                        break;
                    }
                }
                else{
                    if(charNow=='(') {
                        stack.push(1);
                    }
                }
            }
            System.out.println(sb.toString());
            if(!atomicMeasures.containsKey(sb.toString())){
                atomicMeasure.put(sb.toString(),"Measure"+fakeNamePointer++);
            }

         }
        if (!(numOfAgg > 0)) {
            System.out.println("divide atomic ad compunded error ");
            System.exit(0);
        }
        //System.out.println(" num of agg " + numOfAgg);

        return atomicMeasure;
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
            System.out.println("Choore smabhal");
        }
        return measureMap;
    }

    private static HashMap<String, MeasureCondition> getMeasureCondition(HashMap<String, Integer> logicalOperation) {
        HashMap<String, MeasureCondition> measureConditions = new HashMap<>();
        // String regex = "(\\w+)\\s{0,1}(=|!=|>|<|>=|<=|in|any|not in|between|not between|like)\\s{0,1}(\\w+)";
        //todo :  replace the next string with connection to xml2csv tool and using https://regex101.com/r/8NJFLE/2/ one can use group 1,2 and 3 to make objects of measureconditions.
        String[] values = new String[]{"ACCN_IN_FORC = 0", "ACCN_IN_FORC > 0", "ACCN_WITH_CASH_SAL_IN_MONT > 0", "ACCN_WITH_RETL_SALE_IN_MONT > 0", "ACTV_FLAG = 0", "ACTV_FLAG = 1", "APPL_DAT = STAT_DAT", "APPL_DAT_FK = STAT_DAT_FK", "APPL_SCOR < SCOR_CUTF", "APPL_SCOR = 0", "APPL_SCOR > 0", "BUR_SCOR > 0", "DECS_STAT_FK = 1", "DECS_STAT_FK = 2", "DECS_STAT_FK = 3", "DECS_STAT_FK = 4", "DELN_FK < 10", "DELN_FK < 11", "DELN_FK < 12", "DELN_FK < 13", "DELN_FK < 14", "DELN_FK < 3", "DELN_FK < 4", "DELN_FK < 5", "DELN_FK < 6", "DELN_FK < 7", "DELN_FK < 8", "DELN_FK < 9", "DELN_FK < PREV_MONT_DELN_FK", "DELN_FK = 1", "DELN_FK = 10", "DELN_FK = 11", "DELN_FK = 12", "DELN_FK = 13", "DELN_FK = 2", "DELN_FK = 3", "DELN_FK = 4", "DELN_FK = 5", "DELN_FK = 6", "DELN_FK = 7", "DELN_FK = 8", "DELN_FK = 9", "DELN_FK = PREV_MONT_DELN_FK", "DELN_FK > 1", "DELN_FK > 10", "DELN_FK > 11", "DELN_FK > 12", "DELN_FK > 13", "DELN_FK > 2", "DELN_FK > 3", "DELN_FK > 4", "DELN_FK > 5", "DELN_FK > 6", "DELN_FK > 7", "DELN_FK > 8", "DELN_FK > 9", "DELN_FK > PREV_MONT_DELN_FK", "DOCM_DU = 1", "EVR_CASH_EVR_RETL_ACCN > 0", "EVR_CASH_NEVR_RETL_ACCN > 0", "EXCP_INDC = 1", "MVL_ASSOXZ1 > 0", "MVL_ASSOXZ2 > 0", "NEVR_CASH_EVR_RETL_ACCN > 0", "NEVR_CASH_NEVR_RETL_ACCN > 0", "PREV_DAY_ACCN_IN_FORC > 0", "PREV_DAY_DELN_FK = 1", "PREV_DAY_DELN_FK = 10", "PREV_DAY_DELN_FK = 11", "PREV_DAY_DELN_FK = 12", "PREV_DAY_DELN_FK = 13", "PREV_DAY_DELN_FK = 14", "PREV_DAY_DELN_FK = 2", "PREV_DAY_DELN_FK = 3", "PREV_DAY_DELN_FK = 4", "PREV_DAY_DELN_FK = 5", "PREV_DAY_DELN_FK = 6", "PREV_DAY_DELN_FK = 7", "PREV_DAY_DELN_FK = 8", "PREV_DAY_DELN_FK = 9", "PREV_MONT_ACCN_IN_FORC > 0", "PREV_MONT_DELN_FK = 1", "PREV_MONT_DELN_FK = 10", "PREV_MONT_DELN_FK = 11", "PREV_MONT_DELN_FK = 12", "PREV_MONT_DELN_FK = 13", "PREV_MONT_DELN_FK = 14", "PREV_MONT_DELN_FK = 2", "PREV_MONT_DELN_FK = 3", "PREV_MONT_DELN_FK = 4", "PREV_MONT_DELN_FK = 5", "PREV_MONT_DELN_FK = 6", "PREV_MONT_DELN_FK = 7", "PREV_MONT_DELN_FK = 8", "PREV_MONT_DELN_FK = 9", "REVL_FLAG = 1", "REVL_FLAG = 2", "STAT_COD_FK = 12", "STAT_COD_FK = 2", "STAT_COD_FK = 3", "STAT_COD_FK = 4", "STAT_COD_FK = 5", "STAT_COD_FK = 6", "STAT_COD_FK = 9", "TOTL_CONT_EQU_PMT_PLAN_APR > 0", "VINT_FK = MONT_KEY"};

        int index = 1;
        for (String itr : values) {

            /* intializing Object's field Block*/
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

