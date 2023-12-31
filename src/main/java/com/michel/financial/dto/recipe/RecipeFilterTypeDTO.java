package com.michel.financial.dto.recipe;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RecipeFilterTypeDTO {

    @NotBlank
    @JsonProperty(value = "recipe_type")
    private String recipeType;

}
