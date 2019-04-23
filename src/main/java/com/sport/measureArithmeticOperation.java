package com.sport;

public class measureArithmeticOperation implements MeasuresMicro {
    private String className = "measureArithmeticOperation";
    private int id;
    private String value;
    private int active;

    public measureArithmeticOperation(String className, int id, String value, int active) {
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
