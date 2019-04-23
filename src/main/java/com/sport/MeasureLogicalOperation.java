package com.sport;
// todo  : populate it soon ,not used yet
public class MeasureLogicalOperation implements MeasuresMicro{
    private  int id;
    private  String value;
    private  int active;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String logicalOperation) {
        this.value = logicalOperation;
    }

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }
}
