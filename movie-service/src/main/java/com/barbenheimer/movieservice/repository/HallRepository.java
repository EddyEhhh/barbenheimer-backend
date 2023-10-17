package com.barbenheimer.movieservice.repository;

import com.barbenheimer.movieservice.model.Hall;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface HallRepository extends JpaRepository<Hall, Long> {

}
