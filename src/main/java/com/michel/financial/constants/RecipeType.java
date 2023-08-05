package com.michel.financial.constants;

public enum RecipeType {

    SALARY(0),
    GIFT(1),
    AWARD(2),
    OTHER(3);

    private Integer value;

    RecipeType(int i) {
        this.value = 1;
    }

    public int getValue() {
        return value;
    }

    public static RecipeType fromValue(int value) {
        for (RecipeType type : RecipeType.values()) {
            if (type.getValue() == value) {
                return type;
            }
        }
        throw new IllegalArgumentException("Invalid Recipe value: " + value);
    }
}

