package com.michel.financial.dto.expense;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.michel.financial.constants.ExpenseType;
import com.michel.financial.entities.Expense;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class EditExpenseDTO {

    private Long id;

    private String value;

    @JsonProperty(value = "expense_date")
    @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}", message = "Required format: yyyy-MM-dd")
    private String expenseDate;

    @JsonProperty(value = "expected_expense_date")
    @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}", message = "Required format: yyyy-MM-dd")
    private String expectedExpenseDate;

    @JsonProperty(value = "expense_type")
    private ExpenseType expenseType;

    public EditExpenseDTO(Expense expense) {
        this.id = expense.getId();
        this.value = String.valueOf(expense.getValue());
        this.expenseDate = String.valueOf(expense.getExpenseDate());
        this.expectedExpenseDate = String.valueOf(expense.getExpectedExpenseDate());
        this.expenseType = expense.getExpenseType();
    }
}
