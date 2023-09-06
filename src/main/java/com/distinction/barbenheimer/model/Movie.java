package com.distinction.barbenheimer.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


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
    @Column(length = 1024)
    private String description;

    private int runtimeInMinute;

    @NotEmpty
    private String director;

    @NotEmpty
    private String cast;


    @NotEmpty
    private String genre;

    private LocalDateTime ticketSaleDate;


//    @NotEmpty
    private LocalDate releaseDate;

    @NotEmpty
    private String language;

    private int ageRestriction;

    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MovieSchedule> movieSchedules;

    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MovieImage> movieImages;

    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Review> reviews;

}
