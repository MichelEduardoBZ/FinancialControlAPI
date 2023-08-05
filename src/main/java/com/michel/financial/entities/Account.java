package com.michel.financial.entities;

import com.michel.financial.constants.AccountType;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity(name = "tb_account")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String financialInstitution;

    @Enumerated(EnumType.ORDINAL)
    private AccountType accountType;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    private Double amount;

}
