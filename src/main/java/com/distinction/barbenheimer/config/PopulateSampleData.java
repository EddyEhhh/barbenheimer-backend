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
        movie1.setCast("Margot Robbie, Ryan Gosling, Will Ferrell, Kate McKinnon, America Ferrera, Ariana Greenblatt, Emma Mackey, Alexandra Shipp, Issa Rae, Simu Liu");
        movie1.setGenre("Comedy");
        movie1.setReleaseDate(LocalDate.of(2023, 7, 20));
        movie1.setLanguage("English(Sub:Chinese)");
        movie1.setAgeRestriction(1);

        Movie movie2 = new Movie();
        movie2.setTitle("One and Only");
        movie2.setDescription("Chen Shuo, who has been performing “commercial street dance” for a living, meets his best coach, Ding Lei, and joins the street dance club Exclamation Point. The dancers in the club are all different, so they have different sparks and laughs with Chen Shuo. While Chen Shuo is integrating into the group, Exclamation Point is facing disbandment and Chen Shuo receives heavy blows one after another. The love for dance and the precious emotions of sympathy between teammates support Chen Shuo and Ding Lei to go through the slump and revive. In the end, Chen Shuo and Ding Lei win the highest glory of their own.");
        movie2.setRuntimeInMinute(124);
        movie2.setDirector("Da Peng");
        movie2.setCast("Wang Yibo, Huang Bo");
        movie2.setGenre("Comedy/Drama");
        movie2.setReleaseDate(LocalDate.of(2023, 8, 24));
        movie2.setLanguage("Mandarin(Sub:English,Chinese)");
        movie2.setAgeRestriction(0);


        movieRepository.save(movie1);
        movieRepository.save(movie2);


    }


}



