package com.michel.financial.constants;

public enum ExpenseType {

    FOOD(0),
    EDUCATION(1),
    LAZE(2),
    HOME(3),
    CLOUTHES(4),
    HEALTH(5),
    TRANSPORT(6),
    OTHER(7);

    private Integer value;

    ExpenseType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static ExpenseType fromValue(int value) {
        for (ExpenseType type : ExpenseType.values()) {
            if (type.getValue() == value) {
                return type;
            }
        }
        throw new IllegalArgumentException("Invalid Expense value: " + value);
    }
}

