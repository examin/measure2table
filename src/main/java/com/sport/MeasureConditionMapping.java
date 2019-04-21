package com.sport;

public class MeasureConditionMapping {
   private  int id;
   private  int measureId;
   private  int measureConditionid;
   private  int conditionIndex;
   private  String conditionJoin;
   private  int active;

    public MeasureConditionMapping(int id, int measureId, int measureConditionid, int conditionIndex, String conditionJoin, int active) {
        this.id = id;
        this.measureId = measureId;
        this.measureConditionid = measureConditionid;
        this.conditionIndex = conditionIndex;
        this.conditionJoin = conditionJoin;
        this.active = active;
    }

    public int getID() {
        return id;
    }

    public void setID(int ID) {
        this.id = ID;
    }

    public int getMeasureId() {
        return measureId;
    }

    public void setMeasureId(int measureId) {
        this.measureId = measureId;
    }

    public int getMeasureConditionid() {
        return measureConditionid;
    }

    public void setMeasureConditionid(int measureConditionid) {
        this.measureConditionid = measureConditionid;
    }

    public int getConditionIndex() {
        return conditionIndex;
    }

    public void setConditionIndex(int conditionIndex) {
        this.conditionIndex = conditionIndex;
    }

    public String getConditionJoin() {
        return conditionJoin;
    }

    public void setConditionJoin(String conditionJoin) {
        this.conditionJoin = conditionJoin;
    }

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }
}
