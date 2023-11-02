package com.distinction.barbenheimer.repository;

import com.distinction.barbenheimer.model.Movie;
import com.distinction.barbenheimer.model.MovieScheduleDate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.*;
import java.util.List;
import java.util.Optional;


@Repository
public interface MovieScheduleDateRepository extends JpaRepository<MovieScheduleDate, Long> {

    public MovieScheduleDate findByMovieAndShowDate(Optional<Movie> movie, LocalDate showDate);

}
