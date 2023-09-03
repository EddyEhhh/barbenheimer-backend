package com.distinction.barbenheimer.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import java.time.LocalDate;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Movie {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;


    @NotEmpty
    private String title;


    @NotEmpty
    private String description;

    private int runtimeInMinute;

    @NotEmpty
    private String directors;


    @NotEmpty
    private String genre;


//    @NotEmpty
    private LocalDate releaseDate;


    @NotEmpty
    private String language;


    @NotEmpty
    private String ageRestriction;


}
