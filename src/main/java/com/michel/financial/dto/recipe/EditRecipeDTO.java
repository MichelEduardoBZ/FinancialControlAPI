package com.michel.financial.dto.recipe;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.michel.financial.constants.RecipeType;
import com.michel.financial.entities.Recipe;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class EditRecipeDTO {

    private Long id;

    private String value;

    @JsonProperty(value = "receipt_date")
    @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}", message = "Required format: yyyy-MM-dd")
    private String receiptDate;

    @JsonProperty(value = "expected_receipt_date")
    @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}", message = "Required format: yyyy-MM-dd")
    private String receiptExpenseDate;

    @JsonProperty(value = "recipe_type")
    private String recipeType;

    public EditRecipeDTO(Recipe recipe) {
        this.id = recipe.getId();
        this.value = String.valueOf(recipe.getValue());
        this.receiptDate = String.valueOf(recipe.getReceiptDate());
        this.receiptExpenseDate = String.valueOf(recipe.getExpectedReceiptDate());
        this.recipeType = String.valueOf(recipe.getRecipeType());
    }
}
