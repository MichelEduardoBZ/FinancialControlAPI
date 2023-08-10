package com.michel.financial.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.michel.financial.entities.Account;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TransferDTO {
    @NotBlank
    @JsonProperty(value = "client_id_transfer")
    private String accountSender;

    @NotBlank
    @JsonProperty(value = "client_id_receive")
    private String accountReceive;

    @NotBlank
    private String value;

    public TransferDTO(Account accountSender, Account accountReceive, String value) {
        this.accountSender = String.valueOf(accountSender.getId());
        this.accountReceive = String.valueOf(accountReceive.getId());
        this.value = value;

    }
}
