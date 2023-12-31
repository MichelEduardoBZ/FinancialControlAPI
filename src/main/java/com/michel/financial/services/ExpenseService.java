package com.michel.financial.services;

import com.michel.financial.constants.ExpenseType;
import com.michel.financial.dto.expense.*;
import com.michel.financial.entities.Account;
import com.michel.financial.entities.Expense;
import com.michel.financial.repositories.AccountRepository;
import com.michel.financial.repositories.ExpenseRepository;
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
public class ExpenseService {

    @Autowired
    private ExpenseRepository repository;

    @Autowired
    private AccountRepository accountRepository;

    @Transactional
    public ExpenseDTO insertExpense(ExpenseDTO dto){

        if(LocalDate.parse(dto.getExpenseDate()).isAfter(LocalDate.now())){
            throw new ImpracticableDateException("Unable to put future dates");
        }

        Expense expense = new Expense();
        expense = copyDtoToEntity(dto, expense);

        Account account = accountRepository.findById(Long.valueOf(dto.getAccountId())).orElseThrow(() -> new ResourceNotFoundException("Account not found"));
        System.out.println(account.getAmount());
        System.out.println(expense.getValue());
        account.setAmount(account.getAmount() - Math.abs(expense.getValue()));

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

        if(LocalDate.parse(dto.getInitialDate()).isAfter(LocalDate.now()) || LocalDate.parse(dto.getFinalDate()).isAfter(LocalDate.now())){
            throw new ImpracticableDateException("Unable to put future dates");
        }

        Page<Expense> expenses = repository.findByExpenseDateBetweenById(id, LocalDate.parse(dto.getInitialDate()), LocalDate.parse(dto.getFinalDate()), pageable);
        return expenses.map(ExpenseDTO::new);
    }

    public Page<ExpenseDTO> searchAllExpensesByType(Pageable pageable, Long id, ExpenseFilterTypeDTO dto) {
        Page<Expense> expenses = repository.findByExpenseType(id, ExpenseType.valueOf(dto.getExpenseType()), pageable);
        return expenses.map(ExpenseDTO::new);
    }

    @Transactional
    public TotalExpenseDTO searchTotalExpenses(Long id) {
        Account account = accountRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Account not found"));
        List<Expense> expenses = repository.findAll();

        double totalValue = 0;
        for(Expense x : expenses){
            if(x.getAccount().getId().equals(account.getId())){
                totalValue += x.getValue();
            }
        }
        return new TotalExpenseDTO(totalValue);
    }

    @Transactional
    public void deleteExpenseById(Long id) {
        Expense expense = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Expense not found"));

        Account account = accountRepository.findById(expense.getAccount().getId()).orElseThrow(() -> new ResourceNotFoundException("Account not found"));
        account.setAmount(account.getAmount() + expense.getValue());
        repository.deleteById(expense.getId());
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

    public Expense copyDtoToEntity(ExpenseDTO dto, Expense expense){
        expense.setValue(Double.parseDouble(dto.getValue()));
        expense.setExpenseDate(LocalDate.parse(dto.getExpenseDate()));
        expense.setExpectedExpenseDate(LocalDate.parse(dto.getExpectedExpenseDate()));
        expense.setExpenseType(ExpenseType.valueOf(dto.getExpenseType()));
        expense.setAccount(accountRepository.findById(Long.valueOf(dto.getAccountId())).orElseThrow(() -> new ResourceNotFoundException("Account not found")));
        return expense;
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
            expense.setExpenseType(ExpenseType.valueOf(dto.getExpenseType()));
        }
        return expense;
    }
}
