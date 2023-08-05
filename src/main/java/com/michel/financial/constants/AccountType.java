package com.michel.financial.constants;

public enum AccountType {

    WALLET(0),
    CURRENT(1),
    SAVINGS(2);

    private Integer value;

    AccountType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static AccountType fromValue(int value) {
        for (AccountType type : AccountType.values()) {
            if (type.getValue() == value) {
                return type;
            }
        }
        throw new IllegalArgumentException("Invalid AccountType value: " + value);
    }
}
