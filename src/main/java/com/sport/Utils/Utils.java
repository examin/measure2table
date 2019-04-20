package com.sport.Utils;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {
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
           throw new IllegalArgumentException("Uils.19 : May be more than one aggeration finction in measure "+str);
        }
        return aggId;
    }

    public static String getMeasureFieldFromStament(String str) {
      //  https://regex101.com/r/cYejh3/1


        String regex =  "(avg|count|min|max|sum) \\((.*) \\)";
        Pattern pattern = Pattern.compile(regex, Pattern.MULTILINE);
        Matcher matcher = pattern.matcher(str);

        int countForTest = 0;
        String field = "";
        while (matcher.find()) {
            field = matcher.group(2);
            countForTest++;
        }
        if (countForTest > 1||field.isEmpty()) {
            throw new IllegalArgumentException("Uils.37 : May be more than one FIELD  in STRING "+str);
        }
        return field;
    }
}
