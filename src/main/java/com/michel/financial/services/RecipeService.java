package com.michel.financial.services;

import com.michel.financial.constants.RecipeType;
import com.michel.financial.dto.recipe.*;
import com.michel.financial.entities.Account;
import com.michel.financial.entities.Recipe;
import com.michel.financial.repositories.AccountRepository;
import com.michel.financial.repositories.RecipeRepository;
import com.michel.financial.services.exceptions.ResourceNotFoundException;
import com.michel.financial.services.exceptions.ImpracticableDateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class RecipeService {

    @Autowired
    private RecipeRepository repository;

    @Autowired
    private AccountRepository accountRepository;

    @Transactional
    public RecipeDTO insertRecipe(RecipeDTO dto){

        if(LocalDate.parse(dto.getReceiptDate()).isAfter(LocalDate.now())){
            throw new ImpracticableDateException("Unable to put future dates");
        }

        Recipe recipe = new Recipe();
        recipe = copyDtoToEntity(dto, recipe);

        Account account = accountRepository.findById(Long.valueOf(dto.getAccountId())).orElseThrow(() -> new ResourceNotFoundException("Account not found"));

        if(recipe.getValue() < 0){
            throw  new ResourceNotFoundException("The value cannot be less than zero");
        }

        account.setAmount(account.getAmount() + Math.abs(recipe.getValue()));

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
        Page<Recipe> recipes = repository.findAll(pageable);
        return recipes.map(RecipeDTO::new);
    }

    public Page<RecipeDTO> searchAllRecipesByDate(Pageable pageable,Long id, RecipeFilterDateDTO dto) {

        if(LocalDate.parse(dto.getInitialDate()).isAfter(LocalDate.now()) || LocalDate.parse(dto.getFinalDate()).isAfter(LocalDate.now())){
            throw new ImpracticableDateException("Unable to put future dates");
        }

        Page<Recipe> recipes = repository.findByReceiptDateBetween(id, LocalDate.parse(dto.getInitialDate()), LocalDate.parse(dto.getFinalDate()), pageable);
        return recipes.map(RecipeDTO::new);
    }

    public Page<RecipeDTO> searchAllRecipesByType(Pageable pageable, Long id, RecipeFilterTypeDTO dto) {
        Page<Recipe> recipes = repository.findByRecipeType(id, RecipeType.valueOf(dto.getRecipeType()), pageable);
        return recipes.map(RecipeDTO::new);
    }

    @Transactional
    public TotalRecipeDTO searchTotalRecipes(Long id) {
        Account account = accountRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Account not found"));
        List<Recipe> recipes = repository.findAll();

        double totalValue = 0;
        for(Recipe x : recipes){
            if(x.getAccount().getId().equals(account.getId())){
                totalValue += x.getValue();
            }
        }
        return new TotalRecipeDTO(totalValue);
    }

    @Transactional
    public EditRecipeDTO editRecipeByAccountId(Long id, EditRecipeDTO dto) {

        if(Double.parseDouble(dto.getValue()) < 0){
            throw  new ResourceNotFoundException("The value cannot be less than zero");
        }

        Recipe recipe = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Expense not found"));

        Account account = accountRepository.findById(recipe.getAccount().getId()).orElseThrow(() -> new ResourceNotFoundException("Account not found"));

        account.setAmount(account.getAmount() - recipe.getValue());
        recipe = copyDtoToEntityEdit(dto, recipe);
        account.setAmount(account.getAmount() + recipe.getValue());
        repository.save(recipe);

        return new EditRecipeDTO(recipe);
    }

    @Transactional
    public void deleteRecipeById(Long id) {
        Recipe recipe = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Recipe not found"));

        Account account = accountRepository.findById(recipe.getAccount().getId()).orElseThrow(() -> new ResourceNotFoundException("Account not found"));
        account.setAmount(account.getAmount() - recipe.getValue());
        repository.deleteById(recipe.getId());
    }

    public Recipe copyDtoToEntityEdit(EditRecipeDTO dto, Recipe recipe){
        if(dto.getValue() != null){
            recipe.setValue(Double.valueOf(dto.getValue()));
        }

        if(dto.getReceiptDate() != null){
            recipe.setReceiptDate(LocalDate.parse(dto.getReceiptDate()));
        }

        if(dto.getReceiptExpenseDate() != null){
            recipe.setExpectedReceiptDate(LocalDate.parse(dto.getReceiptExpenseDate()));
        }

        if(dto.getRecipeType() != null){
            recipe.setRecipeType(RecipeType.valueOf(dto.getRecipeType()));
        }
        return recipe;
    }


    public Recipe copyDtoToEntity(RecipeDTO dto, Recipe recipe){
        recipe.setValue(Double.parseDouble(dto.getValue()));
        recipe.setReceiptDate(LocalDate.parse(dto.getReceiptDate()));
        recipe.setExpectedReceiptDate(LocalDate.parse(dto.getExpectedReceiptDate()));
        recipe.setDescription(dto.getDescription());
        recipe.setRecipeType(RecipeType.valueOf(dto.getRecipeType()));
        recipe.setAccount(accountRepository.findById(Long.valueOf(dto.getAccountId())).orElseThrow(() -> new ResourceNotFoundException("Account not found")));
        return recipe;
    }
}
