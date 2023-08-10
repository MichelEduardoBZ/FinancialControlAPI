package com.michel.financial.dto.expense;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ExpenseFilterTypeDTO {

    @NotBlank
    @JsonProperty(value = "expense_type")
    private String expenseType;

}
