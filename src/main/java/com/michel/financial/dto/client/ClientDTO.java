package com.michel.financial.dto.client;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.michel.financial.entities.Client;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ClientDTO {

    private Long id;

    @NotBlank
    @Size(min = 3, max = 80, message = "Name must be between 3 and 80 digits")
    private String name;

    @NotBlank
    @Size(min = 11, max = 14, message = "CPF must be between 11 and 14 digits")
    private String cpf;

    @NotBlank(message = "Birth date is required")
    @JsonProperty("birth_date")
    @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}", message = "Required format: yyyy-MM-dd")
    private String birthDate;

    @NotBlank
    @Email(message = "Required format: name@domain")
    private String email;

    public ClientDTO(Client client) {
        this.id = client.getId();
        this.name = client.getName();
        this.cpf = client.getCpf();
        this.birthDate = String.valueOf(client.getBirthDate());
        this.email = client.getEmail();
    }
}
