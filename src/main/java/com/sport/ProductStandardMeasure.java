package com.sport;

public class ProductStandardMeasure {
    private int id;;
    private String measureName;
    private int measureFunctionId;
    private String measureField;
    private String productName;
    int subscriberId;
    int active;
    /* Note : this class require all filed to be non-blank 18thApr2018*/
    public ProductStandardMeasure(int id, String measureName, int measureFunctionId, String measureField, String productName, int subscriberId, int active) {
        this.id = id;
        this.measureName = measureName;
        this.measureFunctionId = measureFunctionId;
        this.measureField = measureField;
        this.productName = productName;
        this.subscriberId = subscriberId;
        this.active = active;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMeasureName() {
        return measureName;
    }

    public void setMeasureName(String measureName) {
        this.measureName = measureName;
    }

    public int getMeasureFunctionId() {
        return measureFunctionId;
    }

    public void setMeasureFunctionId(int measureFunctionId) {
        this.measureFunctionId = measureFunctionId;
    }

    public String getMeasureField() {
        return measureField;
    }

    public void setMeasureField(String measureField) {
        this.measureField = measureField;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getSubscriberId() {
        return subscriberId;
    }

    public void setSubscriberId(int subscriberId) {
        this.subscriberId = subscriberId;
    }

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }
}
