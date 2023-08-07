package com.michel.financial.repositories;

import com.michel.financial.constants.RecipeType;
import com.michel.financial.entities.Recipe;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long> {

    @Query("SELECT obj FROM tb_recipe obj WHERE obj.id = :id AND obj.receiptDate BETWEEN :initialDate AND :finalDate")
    Page<Recipe> findByReceiptDateBetween(Long id, LocalDate initialDate, LocalDate finalDate, Pageable pageable);

    @Query("SELECT obj FROM tb_recipe obj WHERE obj.id = :id AND obj.recipeType = :recipeType")
    Page<Recipe> findByRecipeType(Long id, RecipeType recipeType, Pageable pageable);
}
