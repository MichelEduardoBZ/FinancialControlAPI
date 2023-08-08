package com.michel.financial.dto.expense;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TotalExpenseDTO {

    private String totalValue;

    public TotalExpenseDTO(Double totalValue) {
        this.totalValue = String.valueOf(totalValue);
    }
}
