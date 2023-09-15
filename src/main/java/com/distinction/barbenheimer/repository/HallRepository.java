package com.distinction.barbenheimer.repository;

import com.distinction.barbenheimer.model.Hall;
import com.distinction.barbenheimer.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;


@Repository
public interface HallRepository extends JpaRepository<Hall, Long> {

}
