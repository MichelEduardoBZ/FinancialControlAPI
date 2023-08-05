package com.michel.financial.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.michel.financial.entities.Account;
import com.michel.financial.constants.AccountType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AccountDTO {

    private Long id;

    @NotBlank
    @Size(min = 3, max = 80, message = "Financial Institution must be between 3 and 80 digits")
    @JsonProperty(value = "financial_institution")
    private String financialInstitution;

    @NotNull
    @JsonProperty(value = "account_type")
    private AccountType accountType;

    @NotNull(message = "Must contain customer id")
    @JsonProperty(value = "client_id")
    private String clientId;

    private String amount;

    public AccountDTO(Account account) {
        this.id = account.getId();
        this.financialInstitution = account.getFinancialInstitution();
        this.accountType = account.getAccountType();
        this.amount = String.valueOf(account.getAmount());
        this.clientId = String.valueOf(account.getClient().getId());
    }
}
