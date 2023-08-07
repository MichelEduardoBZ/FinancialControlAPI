package com.michel.financial.services;

import com.michel.financial.constants.ExpenseType;
import com.michel.financial.dto.expense.EditExpenseDTO;
import com.michel.financial.dto.expense.ExpenseDTO;
import com.michel.financial.dto.expense.ExpenseFilterDateDTO;
import com.michel.financial.dto.expense.ExpenseFilterTypeDTO;
import com.michel.financial.entities.Account;
import com.michel.financial.entities.Expense;
import com.michel.financial.repositories.AccountRepository;
import com.michel.financial.repositories.ExpenseRepository;
import com.michel.financial.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
public class ExpenseService {

    @Autowired
    private ExpenseRepository repository;

    @Autowired
    private AccountRepository accountRepository;

    @Transactional
    public ExpenseDTO insertExpense(ExpenseDTO dto){
        Expense expense = new Expense();
        expense = copyDtoToEntity(dto, expense);

        Account account = accountRepository.findById(Long.valueOf(dto.getAccountId())).orElseThrow(() -> new ResourceNotFoundException("Account not found"));
        account.setAmount(account.getAmount() - expense.getValue());
        repository.save(expense);

        return new ExpenseDTO(expense);
    }

    @Transactional
    public ExpenseDTO searchExpenseById(Long id) {
        Expense expense = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Expense not found"));
        return new ExpenseDTO(expense);
    }

    @Transactional
    public Page<ExpenseDTO> searchAllExpenses(Pageable pageable) {
        Page<Expense> expenses = repository.findAll(pageable);
        return expenses.map(ExpenseDTO::new);
    }

    public Page<ExpenseDTO> searchAllExpensesByDate(Pageable pageable, Long id, ExpenseFilterDateDTO dto) {
        Page<Expense> expenses = repository.findByExpenseDateBetweenById(id, LocalDate.parse(dto.getInitialDate()), LocalDate.parse(dto.getFinalDate()), pageable);
        return expenses.map(ExpenseDTO::new);
    }

    public Page<ExpenseDTO> searchAllExpensesByType(Pageable pageable, Long id, ExpenseFilterTypeDTO dto) {
        Page<Expense> expenses = repository.findByExpenseType(id, dto.getExpenseType(), pageable);
        return expenses.map(ExpenseDTO::new);
    }

    public Expense copyDtoToEntity(ExpenseDTO dto, Expense expense){
        expense.setValue(Double.parseDouble(dto.getValue()));
        expense.setExpenseDate(LocalDate.parse(dto.getExpenseDate()));
        expense.setExpectedExpenseDate(LocalDate.parse(dto.getExpectedExpenseDate()));
        expense.setExpenseType(ExpenseType.fromValue(dto.getExpenseType().ordinal()));
        expense.setAccount(accountRepository.findById(Long.valueOf(dto.getAccountId())).orElseThrow(() -> new ResourceNotFoundException("Account not found")));
        return expense;
    }

    @Transactional
    public EditExpenseDTO editExpenseByAccountId(Long id, EditExpenseDTO dto) {

        Expense expense = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Expense not found"));

        Account account = accountRepository.findById(expense.getAccount().getId()).orElseThrow(() -> new ResourceNotFoundException("Account not found"));

        account.setAmount(account.getAmount() - expense.getValue());
        expense = copyDtoToEntityEdit(dto, expense);
        account.setAmount(account.getAmount() + expense.getValue());
        repository.save(expense);

        return new EditExpenseDTO(expense);
    }

    public Expense copyDtoToEntityEdit(EditExpenseDTO dto, Expense expense){
        if(dto.getValue() != null){
            expense.setValue(Double.valueOf(dto.getValue()));
        }

        if(dto.getExpenseDate() != null){
            expense.setExpenseDate(LocalDate.parse(dto.getExpenseDate()));
        }

        if(dto.getExpectedExpenseDate() != null){
            expense.setExpectedExpenseDate(LocalDate.parse(dto.getExpectedExpenseDate()));
        }

        if(dto.getExpenseType() != null){
            expense.setExpenseType(ExpenseType.fromValue(dto.getExpenseType().ordinal()));
        }
        return expense;
    }

    @Transactional
    public void deleteExpenseById(Long id) {
        Expense expense = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Expense not found"));

        Account account = accountRepository.findById(expense.getAccount().getId()).orElseThrow(() -> new ResourceNotFoundException("Account not found"));
        account.setAmount(account.getAmount() + expense.getValue());
        repository.deleteById(expense.getId());
    }
}
