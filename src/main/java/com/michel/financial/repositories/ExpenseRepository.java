package com.michel.financial.repositories;

import com.michel.financial.constants.ExpenseType;
import com.michel.financial.entities.Expense;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {

    @Query("SELECT obj FROM tb_expense obj WHERE obj.id = :id AND obj.expenseDate BETWEEN :initialDate AND :finalDate")
    Page<Expense> findByExpenseDateBetweenById(Long id, LocalDate initialDate, LocalDate finalDate, Pageable pageable);

    @Query("SELECT obj FROM tb_expense obj WHERE obj.id = :id AND obj.expenseType = :expenseType")
    Page<Expense> findByExpenseType(Long id, ExpenseType expenseType, Pageable pageable);

}
