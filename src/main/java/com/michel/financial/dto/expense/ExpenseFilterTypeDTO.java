package com.michel.financial.dto.expense;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.michel.financial.constants.ExpenseType;
import com.michel.financial.constants.RecipeType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ExpenseFilterTypeDTO {

    @NotBlank
    @JsonProperty(value = "expense_type")
    private ExpenseType expenseType;

}
