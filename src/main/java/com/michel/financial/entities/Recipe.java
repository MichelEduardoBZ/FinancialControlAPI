package com.michel.financial.entities;

import com.michel.financial.constants.RecipeType;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity(name = "tb_recipe")
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double value;

    private LocalDate receiptDate;

    private LocalDate expectedReceiptDate;

    private String description;

    private RecipeType recipeType;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;

}
