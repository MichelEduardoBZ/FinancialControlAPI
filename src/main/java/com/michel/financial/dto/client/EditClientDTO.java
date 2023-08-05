package com.michel.financial.dto.client;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.michel.financial.entities.Client;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
@AllArgsConstructor
public class EditClientDTO {

    private Long id;

    @Size(min = 3, max = 80, message = "Name must be between 3 and 80 digits")
    private String name;

    @JsonProperty("birth_date")
    @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}", message = "Required format: yyyy-MM-dd")
    private String birthDate;

    @Email(message = "Required format: name@domain")
    private String email;

    public EditClientDTO(Client client) {
        this.id = client.getId();
        this.name = client.getName();
        this.birthDate = String.valueOf(client.getBirthDate());
        this.email = client.getEmail();
    }
}
