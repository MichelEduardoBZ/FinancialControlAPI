package com.michel.financial.dto.recipe;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TotalRecipeDTO {

    private String totalValue;

    public TotalRecipeDTO(Double totalValue) {
        this.totalValue = String.valueOf(totalValue);
    }
}
