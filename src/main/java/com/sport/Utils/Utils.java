package com.sport.Utils;

import com.sport.MeasureCondition;
import com.sport.MeasureConditionMapping;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {
    public static int countForAtomicId = 1;
    public static TreeMap<String, Integer> atomicMeasureIdMap = new TreeMap<>();

    public static int getMeasureAggIdFromStament(String str, HashMap<String, Integer> measureAggregationFunctionMap) {
        String regex = "(avg|count|min|max|sum)";
        Pattern pattern = Pattern.compile(regex, Pattern.MULTILINE);
        Matcher matcher = pattern.matcher(str);

        int countForTest = 0;
        int aggId = 0;
        while (matcher.find()) {
            aggId = measureAggregationFunctionMap.get(matcher.group(0));
            countForTest++;
        }
        if (countForTest > 1) {
            throw new IllegalArgumentException("Uils.19 : May be more than one aggeration finction in measure " + str);
        }
        return aggId;
    }

    public static String getMeasureFieldFromStament(String str) {
        //  https://regex101.com/r/cYejh3/1


        String noThenregex = "(avg|count|min|max|sum) \\((.*) \\)";
        String regex = "(avg|count|min|max|sum) \\(.*THEN(.*)ELSE";
        Pattern pattern = Pattern.compile(regex, Pattern.MULTILINE);

        Pattern noThenpattern = Pattern.compile(noThenregex, Pattern.MULTILINE);
        Matcher matcher = pattern.matcher(str);
        Matcher noThenmatcher;
        int countForTest = 0;
        String field = "";
        if (matcher.find()) {
            field = matcher.group(2);
            countForTest++;
        }
        if(countForTest==0){
          noThenmatcher = noThenpattern.matcher(str);
          if(noThenmatcher.find())
          {
              System.out.println(noThenmatcher.group(0));
          }
          else{
              System.out.println("Failed");
          }

        }
        return field.replaceAll("\\(|\\)","").trim();
    }

    public static TreeMap<String, Integer> atomicMeasureIdMap(HashMap<String, String> atomicMeasure) {
        TreeSet<String> itr = new TreeSet<>(atomicMeasure.keySet());
        for (String curr : itr) {
            atomicMeasureIdMap.put(curr, countForAtomicId++);
        }
        return atomicMeasureIdMap;
    }

    public static boolean insert2AtomicMeasureIdMap(String curr) {
        atomicMeasureIdMap.put(curr, countForAtomicId++);
        return true;
    }

    public static HashMap<String, String> putIdsIntoCompounded(TreeMap<String, String> compoundedMeasuresStatementMap) {
        HashMap<String, String> compoundedMeasuresIdsStatementMap = new HashMap<>();
        HashSet<String> itr = new HashSet<>(compoundedMeasuresStatementMap.keySet());
        for (String str : itr) {
            Pattern pattern = Pattern.compile("(avg|count|min|max|sum)");
            Matcher matcher = pattern.matcher(str);
            StringBuilder tor = new StringBuilder();
            int nextStartTor = 0;
            while (matcher.find()) {
                int startPosition = matcher.start();
                int end = startPosition;
                StringBuilder sb = new StringBuilder();
                tor.append(str, nextStartTor, startPosition);
                Stack<Integer> stack = new Stack<>();
                char[] strChar = str.toCharArray();
                int currLength = strChar.length;
                int i = startPosition;
                for (; i < currLength; i++) {
                    char charNow = strChar[i];
                    sb.append(charNow);
                    if (charNow == '(') {
                        stack.push(1);
                        break;
                    }
                }
                while (!stack.isEmpty()) {
                    i++;
                    char charNow = strChar[i];
                    sb.append(charNow);
                    if (charNow == ')') {
                        try {
                            stack.pop();
                        } catch (Exception e) {
                            break;
                        }
                    } else {
                        if (charNow == '(') {
                            stack.push(1);
                        }
                    }
                }
                nextStartTor = i;
                // System.out.println(sb.toString());
                int id = atomicMeasureIdMap.get(sb.toString());
                // System.out.println(id);
                tor.append("[" + id + "]");
            }
            tor.append(str.substring(nextStartTor));
            // System.out.println(tor);
            compoundedMeasuresIdsStatementMap.put(str, tor.toString());
        }
        return compoundedMeasuresIdsStatementMap;
    }

    public static TreeMap<String, ArrayList<MeasureConditionMapping>> getMeasureConditionIdFromStament(HashMap<String, MeasureCondition> measureConditionMap) {
        TreeMap<String, ArrayList<MeasureConditionMapping>> measureConditionMappingIds = new TreeMap<>();
        HashSet<String> itr = new HashSet<>(atomicMeasureIdMap.keySet());
        int counter = 1;
        int mappingCounter= 1;
        for (String curr : itr) {
//            https://regex101.com/r/LfCN1S/1
            String whereCondregex = "((?<=CASE WHEN )|IF.?\\()(.*)(?=.*THEN)";
            Pattern whereCondPattern = Pattern.compile(whereCondregex, Pattern.MULTILINE);
            Matcher whereCondMatcher = whereCondPattern.matcher(curr);
            Boolean flag = false;
            while (whereCondMatcher.find()) {
                ArrayList<MeasureConditionMapping> conditionsForCurrString = new ArrayList<>();
                int index = 1;
                flag = true;
                String toMakePartsOf = whereCondMatcher.group(2);
//                System.out.println(toMakePartsOf);
                String operRegex = "(?= AND )|(?= OR )";
                String[] conditions = toMakePartsOf.split(operRegex);
                for (String currCondition : conditions) {
                    //System.out.println(currCondition);
                    int measureId = counter;
                    int measureConditionid = atomicMeasureIdMap.get(curr.replace(" (and|or) ", ""));
                    int conditionIndex = index;
                    String conditionJoin;
                    String regex =  " (AND|OR) ";
                    Pattern pattern = Pattern.compile(regex, Pattern.MULTILINE);
                    Matcher matcher = pattern.matcher(currCondition);
                    if(matcher.find()) {
                        conditionJoin = matcher.group(1);
                    }
                    else conditionJoin = "";
                    int active = 1;
                    conditionsForCurrString.add(new  MeasureConditionMapping(mappingCounter++, measureId, measureConditionid, conditionIndex, conditionJoin, active));
                    //System.out.println("* "+conditionJoin);

                }
                measureConditionMappingIds.put(curr,conditionsForCurrString);
            }
            if (flag == false) {
                //System.out.println("Statement don't have any condition\n# "+curr);
            }
            // System.out.println(curr);
            counter++;
        }

        return measureConditionMappingIds;
    }
}
