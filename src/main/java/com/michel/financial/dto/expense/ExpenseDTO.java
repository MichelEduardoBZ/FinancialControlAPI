package com.michel.financial.dto.expense;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.michel.financial.entities.Expense;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ExpenseDTO {

    private Long id;

    @NotBlank
    private String value;

    @NotBlank
    @JsonProperty(value = "receipt_date")
    @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}", message = "Required format: yyyy-MM-dd")
    private String expenseDate;

    @NotBlank
    @JsonProperty(value = "expected_receipt_date")
    @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}", message = "Required format: yyyy-MM-dd")
    private String expectedExpenseDate;

    @NotBlank
    @JsonProperty(value = "expense_type")
    private String expenseType;

    @NotNull(message = "Must contain customer id")
    @JsonProperty(value = "account_id")
    private String accountId;

    public ExpenseDTO(Expense entity) {
        this.id = entity.getId();
        this.value = String.valueOf(entity.getValue());
        this.expenseDate = String.valueOf(entity.getExpenseDate());
        this.expectedExpenseDate = String.valueOf(entity.getExpectedExpenseDate());
        this.expenseType = String.valueOf(entity.getExpenseType());
        this.accountId = String.valueOf(entity.getAccount().getId());
    }

}
