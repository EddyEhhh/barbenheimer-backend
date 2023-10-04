package com.barbenheimer.ticket.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class MovieShortDTO {

    private Long id;

    private String title;

    private int runtimeInMinute;

    private String genre;

    private int ageRestriction;

    //must be before all(lastShowingDate, showingDate, ticketSaleDate)
    private LocalDateTime releaseDate;

    //Showing Date must be equals or after release date
    private LocalDateTime showingDate;

    //Ticket Sale Date must be before showingDate
    private LocalDateTime ticketSaleDate;


    //Last Showing Date must be after all(releaseDate, showingDate, ticketSaleDate)
    private LocalDateTime lastShowingDate;

    private List<MovieImageDetailDTO> movieImages;

}
