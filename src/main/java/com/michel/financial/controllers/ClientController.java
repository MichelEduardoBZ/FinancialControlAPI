package com.michel.financial.controllers;

import com.michel.financial.dto.client.ClientDTO;
import com.michel.financial.dto.client.EditClientDTO;
import com.michel.financial.services.ClientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@Tag(name = "client")
@RestController
@RequestMapping(value = "/client", produces = {"application/json"})
public class ClientController {

    @Autowired
    private ClientService service;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Create an account for a customer", method = "POST")
    public ResponseEntity<ClientDTO> createAccount(@RequestBody @Valid ClientDTO dto){
        service.createAccount(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(dto.getId()).toUri();
        return ResponseEntity.created(uri).body(dto);
    }

    @GetMapping(value = "/search/{id}")
    @Operation(summary = "Search a customer by ID", method = "GET")
    public ResponseEntity<ClientDTO> searchClientById(@PathVariable Long id){
            ClientDTO dto = service.searchClientById(id);
            return ResponseEntity.ok(dto);
    }

    @GetMapping(value = "/search")
    @Operation(summary = "Search all customer", method = "GET")
    public ResponseEntity<Page<ClientDTO>> searchClients(Pageable pageable){
        Page<ClientDTO> dto = service.searchClients(pageable);
        return ResponseEntity.ok(dto);
    }

    @PutMapping(value = "/edit/{id}")
    @Operation(summary = "Edit an account by ID", method = "PUT")
    public ResponseEntity<EditClientDTO> editClientById(@PathVariable Long id, @RequestBody EditClientDTO dto){
        dto = service.editClientById(id, dto);
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping(value = "/delete/{id}")
    @Operation(summary = "Delete an client by ID", method = "DELETE")
    public ResponseEntity<Void> deleteClientById(@PathVariable Long id){
        service.deleteClientById(id);
        return ResponseEntity.noContent().build();
    }

}
