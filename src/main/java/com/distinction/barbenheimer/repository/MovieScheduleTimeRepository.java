package com.distinction.barbenheimer.repository;

import com.distinction.barbenheimer.model.Movie;
import com.distinction.barbenheimer.model.MovieScheduleTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;


@Repository
public interface MovieScheduleTimeRepository extends JpaRepository<MovieScheduleTime, Long> {


}
