package com.distinction.barbenheimer.config;

import com.distinction.barbenheimer.model.Movie;
import com.distinction.barbenheimer.repository.MovieRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@Slf4j
public class PopulateSampleData {

    @Autowired
    private MovieRepository movieRepository;

    @EventListener
    public void onApplicationEvent(ApplicationReadyEvent event) {
        autoGenerateMovie();

    }

    public void autoGenerateMovie(){
        Movie movie1 = new Movie();
        movie1.setTitle("Barbie");
        movie1.setDescription("To live in Barbie Land is to be a perfect being in a perfect place. Unless you have a full-on existential crisis. Or you’re a Ken.");
        movie1.setRuntimeInMinute(114);
        movie1.setDirector("Greta Gerwig");
        movie1.setGenre("Comedy");
        movie1.setReleaseDate(LocalDate.of(2023, 7, 20));
        movie1.setLanguage("English(Sub:Chinese)");
        movie1.setAgeRestriction("PG13");

        movieRepository.save(movie1);

    }


}



