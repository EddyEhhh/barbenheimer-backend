package com.distinction.barbenheimer.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

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
    private long id;


    @NotEmpty
    private String title;


    @NotEmpty
    @Column(length = 1024)
    private String description;

    private int runtimeInMinute;

    @NotEmpty
    private String genre;

    @NotEmpty
    private String director;

    @NotEmpty
    private String cast;

    @NotEmpty
    private String language;

    private int ageRestriction;

    //must be before all(lastShowingDate, showingDate, ticketSaleDate)
    private LocalDateTime releaseDate;

    //Showing Date must be equals or after release date
    private LocalDateTime showingDate;

    //Ticket Sale Date must be before showingDate
    private LocalDateTime ticketSaleDate;


    //Last Showing Date must be after all(releaseDate, showingDate, ticketSaleDate)
    private LocalDateTime lastShowingDate;

    private float basePrice;

    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MovieScheduleDate> movieScheduleDates;

    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MovieImage> movieImages;

//    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<Review> reviews;

}
