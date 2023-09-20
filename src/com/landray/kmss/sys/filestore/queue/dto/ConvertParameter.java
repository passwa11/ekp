package com.landray.kmss.sys.filestore.queue.dto;

public class ConvertParameter {
    private String converterType;
    private String converterKey;
    private String dispenser;
    private String convertParam;

    public ConvertParameter() {

    }

    public ConvertParameter(String converterType, String converterKey, String dispenser, String convertParam) {
        this.converterType = converterType;
        this.converterKey = converterKey;
        this.dispenser = dispenser;
        this.convertParam = convertParam;
    }

    public String getConverterType() {
        return converterType;
    }

    public void setConverterType(String converterType) {
        this.converterType = converterType;
    }

    public String getConverterKey() {
        return converterKey;
    }

    public void setConverterKey(String converterKey) {
        this.converterKey = converterKey;
    }

    public String getDispenser() {
        return dispenser;
    }

    public void setDispenser(String dispenser) {
        this.dispenser = dispenser;
    }

    public String getConvertParam() {
        return convertParam;
    }

    public void setConvertParam(String convertParam) {
        this.convertParam = convertParam;
    }
}

