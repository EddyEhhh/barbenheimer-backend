package com.barbenheimer.ticket.repository;

import com.barbenheimer.ticket.model.MovieScheduleDate;
import com.barbenheimer.ticket.model.MovieScheduleTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;


@Repository
public interface MovieScheduleTimeRepository extends JpaRepository<MovieScheduleTime, Long> {

    public Optional<MovieScheduleTime> findByMovieScheduleDate_Movie_IdAndMovieScheduleDate_ShowDateAndShowTime(long id, LocalDate showDate, LocalTime showTime);
    // public List<MovieScheduleTime> findByMovieScheduleDateAndShowTime();

    public List<MovieScheduleTime> findByShowTime(LocalTime showTime);

    public MovieScheduleTime findByMovieScheduleDateAndShowTime(MovieScheduleDate movieScheduleDate, LocalTime showTime);

    public MovieScheduleTime findById(long id);
}
