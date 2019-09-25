package com.mclans.mod.dlamp.protocol;

public enum  DataType {
    CONTROL(0x01), ALERT(0x02), SETUP(0x03);
    private int value;
    DataType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static DataType getDataType(int value) {
        for(DataType dataType : DataType.values()) {
            if(dataType.getValue() == value) {
                return dataType;
            }
        }
        return null;
    }
}
