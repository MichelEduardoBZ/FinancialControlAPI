package com.michel.financial.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity(name = "tb_client")
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String cpf;

    private LocalDate birthDate;

    private String email;

    @OneToMany(mappedBy = "client")
    private List<Account> accounts = new ArrayList<>();

}
