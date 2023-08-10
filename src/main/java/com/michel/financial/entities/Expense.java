package com.michel.financial.entities;

import com.michel.financial.constants.ExpenseType;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity(name = "tb_expense")
public class Expense {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double value;

    private LocalDate expenseDate;

    private LocalDate expectedExpenseDate;

    @Enumerated(EnumType.STRING)
    private ExpenseType expenseType;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;

}
