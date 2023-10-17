package com.barbenheimer.movieservice.repository;

import com.barbenheimer.movieservice.model.Hall;
import com.barbenheimer.movieservice.model.Seat;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface SeatRepository extends JpaRepository<Seat, Long> {

    public Seat findById(long id);

    public Seat findByHallAndRowCharacterAndColumnNumber(Hall hall, String rowCharacter, int columnNumber);

}
