package com.michel.financial.services;

import com.michel.financial.constants.AccountType;
import com.michel.financial.dto.account.AccountDTO;
import com.michel.financial.dto.account.EditAccountDTO;
import com.michel.financial.entities.Account;
import com.michel.financial.repositories.AccountRepository;
import com.michel.financial.repositories.ClientRepository;
import com.michel.financial.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AccountService {

    @Autowired
    private AccountRepository repository;

    @Autowired
    private ClientRepository clientRepository;

    @Transactional
    public AccountDTO createAccount(AccountDTO dto) {
        Account account = new Account();
        account = copyDtoToEntity(dto, account);
        repository.save(account);
        return new AccountDTO(account);
    }
    @Transactional
    public AccountDTO searchAccountById(Long id) {
        Account account = repository.findById(id).orElseThrow(() -> new RuntimeException("No account found"));
        return new AccountDTO(account);
    }
    @Transactional
    public Page<AccountDTO> searchAccounts(Pageable pageable) {
        Page<Account> account = repository.findAll(pageable);
        return account.map(AccountDTO::new);
    }

    public void deleteAccount(Long id) {
        try {
            repository.deleteById(id);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Account does not exist");
        }
    }

    public Account copyDtoToEntity(AccountDTO dto, Account account) {

        if (dto.getAmount() != null) {
            account.setAmount(Double.parseDouble(dto.getAmount()));
        } else {
            account.setAmount(0.0);
        }

        account.setFinancialInstitution(dto.getFinancialInstitution());
        account.setAccountType(AccountType.valueOf(dto.getAccountType()));
        account.setClient(clientRepository.findById(Long.parseLong(dto.getClientId())).orElseThrow(() -> new ResourceNotFoundException("No client found")));
        return account;
    }

    @Transactional
    public EditAccountDTO editAccount(Long id, EditAccountDTO dto) {
        Account account = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No account found"));
        account = copyDtoToEntityEdit(dto, account);
        repository.save(account);
        return new EditAccountDTO(account);
    }

    public Account copyDtoToEntityEdit(EditAccountDTO dto, Account account) {

        if (dto.getFinancialInstitution() != null) {
            account.setFinancialInstitution(dto.getFinancialInstitution());
        }
        return account;
    }
}