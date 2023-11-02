package com.distinction.barbenheimer.repository;

import com.distinction.barbenheimer.model.Hall;
import com.distinction.barbenheimer.model.Movie;
import com.distinction.barbenheimer.model.Seat;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;


@Repository
public interface SeatRepository extends JpaRepository<Seat, Long> {

    public Seat findById(long id);

    public Seat findByHallAndRowCharacterAndColumnNumber(Hall hall, String rowCharacter, int columnNumber);

}
