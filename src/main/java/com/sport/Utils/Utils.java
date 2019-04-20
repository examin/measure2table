package com.sport.Utils;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Stack;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {
    public static int countForAtomicId = 1;
    public static HashMap<String, Integer> atomicMeasureIdMap = new HashMap<>();

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


        String regex = "(avg|count|min|max|sum) \\((.*) \\)";
        Pattern pattern = Pattern.compile(regex, Pattern.MULTILINE);
        Matcher matcher = pattern.matcher(str);

        int countForTest = 0;
        String field = "";
        while (matcher.find()) {
            field = matcher.group(2);
            countForTest++;
        }
        if (countForTest > 1 || field.isEmpty()) {
            throw new IllegalArgumentException("Uils.37 : May be more than one FIELD  in STRING " + str);
        }
        return field;
    }

    public static HashMap<String, Integer> atomicMeasureIdMap(HashMap<String, String> atomicMeasure) {
        HashSet<String> itr = new HashSet<>(atomicMeasure.keySet());
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
                tor.append(str.substring(nextStartTor,startPosition));
                Stack<Integer> stack = new Stack<>();
                char[] strChar = str.toCharArray();
                int currLength = strChar.length;
                int i = startPosition;
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
                nextStartTor = i;
               // System.out.println(sb.toString());
                int id = atomicMeasureIdMap.get(sb.toString());
               // System.out.println(id);
                tor.append("["+id+"]");
            }
            tor.append(str.substring(nextStartTor));
           // System.out.println(tor);
            compoundedMeasuresIdsStatementMap.put(str,tor.toString());
        }



        return compoundedMeasuresIdsStatementMap;
    }
}
