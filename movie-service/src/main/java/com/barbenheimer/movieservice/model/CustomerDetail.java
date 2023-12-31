package com.barbenheimer.movieservice.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    
    
    @Email(message = "error.invalid.email")
    private String email;

}
