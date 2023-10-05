package com.barbenheimer.mailerservice.model;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Movie {

    private long id;


    @NotEmpty
    private String title;


    @NotEmpty
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

    private List<MovieScheduleDate> movieScheduleDates;



//    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<Review> reviews;

}
