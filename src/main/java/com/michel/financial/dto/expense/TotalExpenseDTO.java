package com.michel.financial.dto.expense;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TotalExpenseDTO {

    private String totalValue;

    public TotalExpenseDTO(Double totalValue) {
        this.totalValue = String.valueOf(totalValue);
    }
}
