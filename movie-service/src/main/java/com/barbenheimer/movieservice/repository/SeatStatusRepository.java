package com.barbenheimer.movieservice.repository;

import com.barbenheimer.movieservice.model.Seat;
import com.barbenheimer.movieservice.model.MovieScheduleTime;
import com.barbenheimer.movieservice.model.SeatStatus;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface SeatStatusRepository extends JpaRepository<SeatStatus, Long> {

    public Optional<SeatStatus> findByMovieScheduleTimeAndSeat(MovieScheduleTime showTime, Seat seat);

    public List<SeatStatus> findByMovieScheduleTime(MovieScheduleTime showTime);


}
