package com.sport;
//todo ; not populated yet
public class MeasureAggregationFunction implements MeasuresMicro {
    private String className = "MeasureAggregationFunction";
    private int id;
    private String value;
    private int active;

    public MeasureAggregationFunction(String className, int id, String value, int active) {
        this.id = id;
        this.value = value;
        this.active = active;
    }

    public String getClassName() {
        return className;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }
}
