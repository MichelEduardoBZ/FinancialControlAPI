package com.michel.financial.services;

import com.michel.financial.constants.RecipeType;
import com.michel.financial.dto.recipe.RecipeDTO;
import com.michel.financial.dto.recipe.RecipeFilterDateDTO;
import com.michel.financial.entities.Account;
import com.michel.financial.entities.Recipe;
import com.michel.financial.repositories.AccountRepository;
import com.michel.financial.repositories.RecipeRepository;
import com.michel.financial.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
public class RecipeService {

    @Autowired
    private RecipeRepository repository;

    @Autowired
    private AccountRepository accountRepository;

    @Transactional
    public RecipeDTO insertAccount(RecipeDTO dto){
        Recipe recipe = new Recipe();
        recipe = copyDtoToEntity(dto, recipe);

        Account account = accountRepository.findById(Long.valueOf(dto.getAccountId())).orElseThrow(() -> new ResourceNotFoundException("Account not found"));
        account.setAmount(account.getAmount() + recipe.getValue());
        repository.save(recipe);

        return new RecipeDTO(recipe);
    }

    @Transactional
    public RecipeDTO searchRecipeById(Long id) {
        Recipe recipe = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Recipe not found"));
        return new RecipeDTO(recipe);
    }

    @Transactional
    public Page<RecipeDTO> searchAllRecipes(Pageable pageable) {
        Page<Recipe> recipe = repository.findAll(pageable);
        return recipe.map(RecipeDTO::new);
    }

    @Transactional
    public Recipe copyDtoToEntity(RecipeDTO dto, Recipe recipe){
        recipe.setValue(Double.parseDouble(dto.getValue()));
        recipe.setReceiptDate(LocalDate.parse(dto.getReceiptDate()));
        recipe.setExpectedReceiptDate(LocalDate.parse(dto.getExpectedReceiptDate()));
        recipe.setDescription(dto.getDescription());
        recipe.setRecipeType(RecipeType.fromValue(dto.getRecipeType().ordinal()));
        recipe.setAccount(accountRepository.findById(Long.valueOf(dto.getAccountId())).orElseThrow(() -> new ResourceNotFoundException("Account not found")));
        return recipe;
    }

    public Page<RecipeDTO> searchAllRecipesByDate(Pageable pageable, RecipeFilterDateDTO dto) {
        Page<Recipe> recipe = repository.findByReceiptDateBetween(LocalDate.parse(dto.getInitialDate()), LocalDate.parse(dto.getFinalDate()), pageable);
        return recipe.map(RecipeDTO::new);
    }
}
