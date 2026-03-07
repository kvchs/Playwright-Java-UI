package org.example.enums;

/**
 * 定位方式枚举
 */
public enum LocatorType {

    ID("id"),
    XPATH("xpath"),
    CSS("css"),
    NAME("name"),
    CLASS_NAME("className"),
    TEXT("text"),
    PLACEHOLDER("placeholder");

    private final String type;

    LocatorType(String type){
        this.type = type;
    }

    public String getType(){
        return type;
    }
}
