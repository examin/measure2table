package com.sport;


public class MeasureCondition {
    private int conditionId;
    private String conditionLeftField	;
    private String conditionRightField	;
    private String conditionRightValue	;
    private int measureOperationId	;
    private int active;
    private String fullString;

    public MeasureCondition(int conditionId, String conditionLeftField, String conditionRightField, String conditionRightValue, int measureOperationId, int active, String fullString) {
        this.conditionId = conditionId;
        this.conditionLeftField = conditionLeftField;
        this.conditionRightField = conditionRightField;
        this.conditionRightValue = conditionRightValue;
        this.measureOperationId = measureOperationId;
        this.active = active;
        this.fullString = fullString;
    }

    public int getConditionId() {
        return conditionId;
    }

    public void setConditionId(int conditionId) {
        this.conditionId = conditionId;
    }

    public String getConditionLeftField() {
        return conditionLeftField;
    }

    public void setConditionLeftField(String conditionLeftField) {
        if(conditionLeftField.isEmpty()){
            throw new IllegalArgumentException("Measure condition left field can't be empty");
        }
        this.conditionLeftField = conditionLeftField;
    }

    public String getConditionRightField() {
        return conditionRightField;
    }

    public void setConditionRightField(String conditionRightField) {
        this.conditionRightField = conditionRightField;
    }

    public String getConditionRightValue() {
        return conditionRightValue;
    }

    public void setConditionRightValue(String conditionRightValue) {
        this.conditionRightValue = conditionRightValue;
    }

    public int getMeasureOperationId() {
        return measureOperationId;
    }

    public void setMeasureOperationId(int measureOperationId) {
        this.measureOperationId = measureOperationId;
    }

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }

    public String getFullString() {
        return fullString;
    }

    public void setFullString(String fullString) {
        this.fullString = fullString;
    }
}
