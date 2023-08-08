package com.michel.financial.controllers;

import com.michel.financial.dto.client.EditClientDTO;
import com.michel.financial.dto.expense.*;
import com.michel.financial.dto.recipe.TotalRecipeDTO;
import com.michel.financial.services.ExpenseService;
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

@Tag(name = "expense")
@RestController
@RequestMapping(value = "/expense", produces = {"application/json"})
public class ExpenseController {

    @Autowired
    private ExpenseService service;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Add expense to account", method = "POST")
    public ResponseEntity<ExpenseDTO> insertRecipe(@RequestBody ExpenseDTO dto){
        dto = service.insertExpense(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(dto.getId()).toUri();
        return ResponseEntity.created(uri).body(dto);
    }

    @GetMapping(value = "/search/{id}")
    @Operation(summary = "Search expense by ID", method = "GET")
    public ResponseEntity<ExpenseDTO> searchExpenseById(@PathVariable Long id){
        ExpenseDTO dto = service.searchExpenseById(id);
        return ResponseEntity.ok(dto);
    }

    @GetMapping(value = "/search")
    @Operation(summary = "Search all expenses", method = "GET")
    public ResponseEntity<Page<ExpenseDTO>> searchAllExpenses(Pageable pageable){
        Page<ExpenseDTO> dto = service.searchAllExpenses(pageable);
        return ResponseEntity.ok(dto);
    }

    @GetMapping(value = "/search-by-date/{id}")
    @Operation(summary = "Search all expenses by filter date", method = "GET")
    public ResponseEntity<Page<ExpenseDTO>> searchAllRecipeByDate(Pageable pageable, @PathVariable Long id, @RequestBody ExpenseFilterDateDTO dto){
        Page<ExpenseDTO> recipeDto = service.searchAllExpensesByDate(pageable, id, dto);
        return ResponseEntity.ok(recipeDto);
    }

    @GetMapping(value = "/search-by-type/{id}")
    @Operation(summary = "Search all expenses by filter type", method = "GET")
    public ResponseEntity<Page<ExpenseDTO>> searchAllExpenseByDate(Pageable pageable, @PathVariable Long id, @RequestBody ExpenseFilterTypeDTO dto){
        Page<ExpenseDTO> recipeDto = service.searchAllExpensesByType(pageable, id, dto);
        return ResponseEntity.ok(recipeDto);
    }

    @GetMapping(value = "/search-total-value/{id}")
    @Operation(summary = "Search total value of expenses", method = "GET")
    public ResponseEntity<TotalExpenseDTO> searchTotalExpenses(@PathVariable Long id){
        TotalExpenseDTO expenseDto = service.searchTotalExpenses(id);
        return ResponseEntity.ok(expenseDto);
    }

    @PutMapping(value = "/edit/{id}")
    @Operation(summary = "Edit expense by account ID", method = "PUT")
    public ResponseEntity<EditExpenseDTO> editExpenseByAccountId(@PathVariable Long id, @RequestBody EditExpenseDTO dto){
        dto = service.editExpenseByAccountId(id, dto);
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping(value = "/delete/{id}")
    @Operation(summary = "Delete expense by ID", method = "DELETE")
    public ResponseEntity<Void> deleteExpenseById(@PathVariable Long id){
        service.deleteExpenseById(id);
        return ResponseEntity.noContent().build();
    }
}
