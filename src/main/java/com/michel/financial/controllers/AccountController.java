package com.michel.financial.controllers;

import com.michel.financial.dto.account.AccountDTO;
import com.michel.financial.dto.account.EditAccountDTO;
import com.michel.financial.services.AccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "account")
@RestController
@RequestMapping(value = "/account", produces = {"application/json"})
public class AccountController {

    @Autowired
    private AccountService service;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Create an account for a customer", method = "POST")
    public ResponseEntity<AccountDTO> insertAccount(@RequestBody AccountDTO dto) {
        dto = service.createAccount(dto);
        return ResponseEntity.ok(dto);
    }

    @GetMapping(value = "/search/{id}")
    @Operation(summary = "Search accounts by ID", method = "GET")
    public ResponseEntity<AccountDTO> searchAccountById(@PathVariable Long id) {
        AccountDTO dto = service.searchAccountById(id);
        return ResponseEntity.ok(dto);
    }

    @GetMapping(value = "/search")
    @Operation(summary = "Search all registered accounts", method = "GET")
    public ResponseEntity<Page<AccountDTO>> searchAccountById(Pageable pageable) {
        Page<AccountDTO> dto = service.searchAccounts(pageable);
        return ResponseEntity.ok(dto);
    }

    @PutMapping(value = "/edit/{id}")
    @Operation(summary = "Edit account by ID", method = "PUT")
    public ResponseEntity<EditAccountDTO> editAccount(@PathVariable Long id, @RequestBody EditAccountDTO dto) {
        dto = service.editAccount(id, dto);
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping(value = "/delete/{id}")
    @Operation(summary = "Delete account by ID", method = "DELETE")
    public ResponseEntity<Void> deleteAccount(@PathVariable Long id) {
        service.deleteAccount(id);
        return ResponseEntity.noContent().build();
    }
}
