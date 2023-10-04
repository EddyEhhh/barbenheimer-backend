package com.barbenheimer.ticket.repository;

import com.barbenheimer.ticket.model.Movie;
import com.barbenheimer.ticket.model.MovieScheduleDate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.*;
import java.util.Optional;


@Repository
public interface MovieScheduleDateRepository extends JpaRepository<MovieScheduleDate, Long> {

    public MovieScheduleDate findByMovieAndShowDate(Optional<Movie> movie, LocalDate showDate);

}
