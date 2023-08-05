package com.michel.financial.repositories;

import com.michel.financial.entities.Recipe;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long> {

    Page<Recipe> findByReceiptDateBetween(LocalDate initialDate, LocalDate finalDate, Pageable pageable);

}
