package com.barbenheimer.ticket.dto;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class MovieDetailsDTO {

    private Long id;

    private String title;

    private String description;

    private int runtimeInMinute;

    private String genre;

    private String director;

    private String cast;

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

    private List<MovieScheduleDateDetailDTO> movieScheduleDates;

    private List<MovieImageDetailDTO> movieImages;
}
