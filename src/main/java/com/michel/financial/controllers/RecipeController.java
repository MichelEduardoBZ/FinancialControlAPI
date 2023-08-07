package com.michel.financial.controllers;

import com.michel.financial.dto.expense.EditExpenseDTO;
import com.michel.financial.dto.recipe.EditRecipeDTO;
import com.michel.financial.dto.recipe.RecipeDTO;
import com.michel.financial.dto.recipe.RecipeFilterDateDTO;
import com.michel.financial.dto.recipe.RecipeFilterTypeDTO;
import com.michel.financial.services.RecipeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
@Tag(name = "recipe")
@RestController
@RequestMapping(value = "/recipe", produces = {"application/json"})
public class RecipeController {

    @Autowired
    private RecipeService service;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Add amount to account", method = "POST")
    public ResponseEntity<RecipeDTO> insertRecipe(@RequestBody RecipeDTO dto){
        dto = service.insertAccount(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(dto.getId()).toUri();
        return ResponseEntity.created(uri).body(dto);
    }

    @GetMapping(value = "/search/{id}")
    @Operation(summary = "Search recipe by ID", method = "GET")
    public ResponseEntity<RecipeDTO> searchRecipeById(@PathVariable Long id){
        RecipeDTO dto = service.searchRecipeById(id);
        return ResponseEntity.ok(dto);
    }

    @GetMapping(value = "/search")
    @Operation(summary = "Search all recipes", method = "GET")
    public ResponseEntity<Page<RecipeDTO>> searchAllRecipes(Pageable pageable){
        Page<RecipeDTO> dto = service.searchAllRecipes(pageable);
        return ResponseEntity.ok(dto);
    }

    @GetMapping(value = "/search-by-date/{id}")
    @Operation(summary = "Search all recipes by filter date", method = "GET")
    public ResponseEntity<Page<RecipeDTO>> searchAllRecipesByDate(Pageable pageable, @PathVariable Long id, @RequestBody RecipeFilterDateDTO dto){
        Page<RecipeDTO> recipeDto = service.searchAllRecipesByDate(pageable, id, dto);
        return ResponseEntity.ok(recipeDto);
    }

    @GetMapping(value = "/search-by-type/{id}")
    @Operation(summary = "Search all recipes by filter type", method = "GET")
    public ResponseEntity<Page<RecipeDTO>> searchAllRecipesByType(Pageable pageable, @PathVariable Long id, @RequestBody RecipeFilterTypeDTO dto){
        Page<RecipeDTO> recipeDto = service.searchAllRecipesByType(pageable, id, dto);
        return ResponseEntity.ok(recipeDto);
    }

    @PutMapping(value = "/edit/{id}")
    @Operation(summary = "Edit recipe by account ID", method = "PUT")
    public ResponseEntity<EditRecipeDTO> editRecipeByAccountId(@PathVariable Long id, @RequestBody EditRecipeDTO dto){
        dto = service.editRecipeByAccountId(id, dto);
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping(value = "/delete/{id}")
    @Operation(summary = "Delete recipe by ID", method = "DELETE")
    public ResponseEntity<Void> deleteRecipeById(@PathVariable Long id){
        service.deleteRecipeById(id);
        return ResponseEntity.noContent().build();
    }
}
