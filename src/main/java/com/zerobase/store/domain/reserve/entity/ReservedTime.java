package com.zerobase.store.domain.reserve.entity;

public enum ReservedTime {
    SLOT_1("09:00 - 09:30"),
    SLOT_2("09:30 - 10:00"),
    SLOT_3("10:00 - 10:30");

    private String label;

    ReservedTime(String label){
        this.label = label;
    }
    public String getLabel(){
        return label;
    }
}
