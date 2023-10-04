package com.barbenheimer.ticket.repository;

import com.barbenheimer.ticket.model.MovieImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieImageRepository extends JpaRepository<MovieImage, MovieImage> {

    public List<MovieImage> findMovieImagesByMovie_Id(Long id);
}
