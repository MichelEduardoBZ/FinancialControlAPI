package com.michel.financial.dto.recipe;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TotalRecipeDTO {

    private String totalValue;

    public TotalRecipeDTO(Double totalValue) {
        this.totalValue = String.valueOf(totalValue);
    }
}
