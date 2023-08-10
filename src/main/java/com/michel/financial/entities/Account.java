package com.michel.financial.entities;

import com.michel.financial.constants.AccountType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "tb_account")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String financialInstitution;

    @Enumerated(EnumType.STRING)
    private AccountType accountType;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    private Double amount;

    @OneToMany(mappedBy = "account")
    private List<Recipe> recipes;

    @OneToMany(mappedBy = "account")
    private List<Expense> expenses;

}
