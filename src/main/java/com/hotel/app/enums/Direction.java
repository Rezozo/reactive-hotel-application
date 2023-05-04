package com.hotel.app.enums;

public enum Direction {
    ASC(1),
    DESC(2),
    UNDEFINED(4);
    private final int value;
    Direction(int value) {
        this.value = value;
    }
    public int getValue() {
        return value;
    }
}
