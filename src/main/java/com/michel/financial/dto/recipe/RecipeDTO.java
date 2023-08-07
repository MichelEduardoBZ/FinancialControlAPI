package com.michel.financial.dto.recipe;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.michel.financial.constants.RecipeType;
import com.michel.financial.entities.Recipe;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RecipeDTO {

    private Long id;

    @NotBlank
    private String value;

    @NotBlank
    @JsonProperty(value = "receipt_date")
    @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}", message = "Required format: yyyy-MM-dd")
    private String receiptDate;

    @NotBlank
    @JsonProperty(value = "expected_receipt_date")
    @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}", message = "Required format: yyyy-MM-dd")
    private String expectedReceiptDate;

    private String description;

    @NotBlank
    @JsonProperty(value = "recipe_type")
    private String recipeType;

    @NotNull(message = "Must contain customer id")
    @JsonProperty(value = "account_id")
    private String accountId;

    public RecipeDTO(Recipe entity) {
        this.id = entity.getId();
        this.value = String.valueOf(entity.getValue());
        this.receiptDate = String.valueOf(entity.getReceiptDate());
        this.expectedReceiptDate = String.valueOf(entity.getExpectedReceiptDate());
        this.description = entity.getDescription();
        this.recipeType = String.valueOf(entity.getRecipeType());
        this.accountId = String.valueOf(entity.getAccount().getId());
    }
}
