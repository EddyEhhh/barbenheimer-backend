package com.distinction.barbenheimer.repository;

import com.distinction.barbenheimer.model.MovieImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieImageRepository extends JpaRepository<MovieImage, MovieImage> {

    public List<MovieImage> findMovieImagesByMovie_Id(Long id);
}
