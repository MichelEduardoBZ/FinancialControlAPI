package com.michel.financial.services;

import com.michel.financial.dto.client.ClientDTO;
import com.michel.financial.dto.client.EditClientDTO;
import com.michel.financial.entities.Client;
import com.michel.financial.repositories.ClientRepository;
import com.michel.financial.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
public class ClientService {

    @Autowired
    private ClientRepository repository;

    public ClientDTO createAccount(ClientDTO dto) {
        Client client = new Client();
        client = copyDtoToEntity(dto, client);
        repository.save(client);
        return new ClientDTO(client);
    }

    @Transactional
    public ClientDTO searchClientById(Long id) {
        Client client = repository.findById(id).orElseThrow(() -> new RuntimeException("No client found"));
        return new ClientDTO(client);
    }

    @Transactional
    public Page<ClientDTO> searchClients(Pageable pageable) {
        Page<Client> clients = repository.findAll(pageable);
        return clients.map(ClientDTO::new);
    }


    @Transactional
    public void deleteClientById(Long id) {
        try {
            repository.deleteById(id);
        } catch (EntityNotFoundException e){
           throw new ResourceNotFoundException("Client does not exist");
        }
    }

    @Transactional
    public Client copyDtoToEntity(ClientDTO dto, Client client){
        client.setName(dto.getName());
        client.setCpf(dto.getCpf());
        client.setBirthDate(LocalDate.parse(dto.getBirthDate()));
        client.setEmail(dto.getEmail());
        return client;
    }

    @Transactional
    public EditClientDTO editClientById(Long id, EditClientDTO clientDTO) {
        Client client = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No client found"));
        client = copyDtoToEntityEdit(clientDTO, client);
        repository.save(client);
        return new EditClientDTO(client);
    }

    @Transactional
    public Client copyDtoToEntityEdit(EditClientDTO dto, Client client){

        if(dto.getName() != null){
            client.setName(dto.getName());
        }

        if(dto.getBirthDate() != null){
            client.setBirthDate(LocalDate.parse(dto.getBirthDate()));
        }

        if(dto.getEmail() != null){
            client.setEmail(dto.getEmail());
        }
        return client;
    }
}
