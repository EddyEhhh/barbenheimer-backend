package com.distinction.barbenheimer.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Customer {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;


    @NotEmpty
    private String username;

    @NotEmpty
    private String email;

    @NotEmpty
    private String defaultPayment;

    @NotEmpty
    private LocalDate birthDate;

    private boolean isDeleted;

    @OneToMany(mappedBy = "customer")
    private List<Purchase> purchases;



}
