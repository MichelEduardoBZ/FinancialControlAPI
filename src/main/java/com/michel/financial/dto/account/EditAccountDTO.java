package com.michel.financial.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.michel.financial.entities.Account;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class EditAccountDTO {

    private Long id;

    @NotBlank
    @Size(min = 3, max = 80, message = "Financial Institution must be between 3 and 80 digits")
    @JsonProperty(value = "financial_institution")
    private String financialInstitution;

    public EditAccountDTO(Account account) {
        this.id = account.getId();
        this.financialInstitution = account.getFinancialInstitution();
    }

}
