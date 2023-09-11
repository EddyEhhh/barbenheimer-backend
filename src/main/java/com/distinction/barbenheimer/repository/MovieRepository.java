package com.distinction.barbenheimer.repository;

import com.distinction.barbenheimer.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;


@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {

    public List<Movie> findAll();

    public List<Movie> findByLastShowingDateAfter(LocalDateTime after);
    
    public Movie findByTitle(String movieTitle);

    public List<Movie> findByTitleContaining(String value);

}
