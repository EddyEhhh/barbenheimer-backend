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

    
    /** 
     * @param event
     */
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

        Movie movie3 = new Movie();
        movie3.setTitle("Oppenheimer");
        movie3.setDescription("The film follows the story of American scientist J. Robert Oppenheimer and his role in the development of the atomic bomb.");
        movie3.setRuntimeInMinute(180);
        movie3.setDirector("Christopher Nolan");
        movie3.setCast("Cillian Murphy, Emily Blunt, Robert Downey Jr., Matt Damon, Rami Malek, Florence Pugh, Benny Safdie, Michael Angarano, Josh Hartnett, Kenneth Branagh");
        movie3.setGenre("Thriller");
        movie3.setReleaseDate(LocalDate.of(2023, 7, 20));
        movie3.setLanguage("English(Sub:Chinese)");
        movie3.setAgeRestriction(3);

        Movie movie4 = new Movie();
        movie4.setTitle("Mission: Impossible - Dead Reckoning Part One");
        movie4.setDescription("In Mission: Impossible – Dead Reckoning Part One, Ethan Hunt (Tom Cruise) and his IMF team embark on their most dangerous mission yet: To track down a terrifying new weapon that threatens all of humanity before it falls into the wrong hands. With control of the future and the fate of the world at stake, and dark forces from Ethan’s past closing in, a deadly race around the globe begins. Confronted by a mysterious, all-powerful enemy, Ethan is forced to consider that nothing can matter more than his mission – not even the lives of those he cares about most.");
        movie4.setRuntimeInMinute(163);
        movie4.setDirector("Christopher McQuarrie");
        movie4.setCast("Tom Cruise, Ving Rhames, Simon Pegg, Rebecca Ferguson, Vanessa Kirby, Hayley Atwell, Shea Whigham, Pom Klementieff, Esai Morales, Henry Czerny");
        movie4.setGenre("Action/Adventure");
        movie4.setReleaseDate(LocalDate.of(2023, 7, 13));
        movie4.setLanguage("English(Sub:Chinese)");
        movie4.setAgeRestriction(1);

        Movie movie5 = new Movie();
        movie5.setTitle("Teenage Mutant Ninja Turtles: Mutant Mayhem");
        movie5.setDescription("After years of being sheltered from the human world, the Turtle brothers set out to win the hearts of New Yorkers and be accepted as normal teenagers through heroic acts. Their new friend April O’Neil helps them take on a mysterious crime syndicate, but they soon get in over their heads when an army of mutants is unleashed upon them.");
        movie5.setRuntimeInMinute(99);
        movie5.setDirector("Jeff Rowe, Kyler Spears");
        movie5.setCast("Paul Rudd, Maya Rudolph, Hannibal Buress, Rose Byrne, John Cena, Jackie Chan, Ice Cube");
        movie5.setGenre("Action/Adventure/Animation");
        movie5.setReleaseDate(LocalDate.of(2023, 8, 31));
        movie5.setLanguage("English(Sub:Chinese)");
        movie5.setAgeRestriction(0);

        Movie movie6 = new Movie();
        movie6.setTitle("The White Storm 3: Heaven Or Hell 扫毒3：人在天涯");
        movie6.setDescription("Hong Kong police agent Cheung (Aaron Kwok) works undercover in Kang’s (Sean Lau) drug cartel. For years, he assists the notorious Thai-Chinese military veteran in operating the syndicate out of Southeast Asia before moving to Hong Kong. Following the trail of information left by Cheung, Superintendent Au (Louis Koo) leads the Narcotics Bureau to bust the syndicate, but Cheung is heavily wounded and saved by Kang in the exchange of fire which follows. Word travels fast, and it isn’t long before militia leader Dai (Lo Ka Leung) receives a tip-off about Cheung’s betrayal… What emerges in the frontline of war is a familiar face, but this time, as a friend or foe?");
        movie6.setRuntimeInMinute(125);
        movie6.setDirector("Herman Yau 邱礼涛");
        movie6.setCast("Sean Lau 刘青云, Louis Koo 古天乐, Aaron Kwok 郭富城");
        movie6.setGenre("Action/Thriller");
        movie6.setReleaseDate(LocalDate.of(2023, 7, 20));
        movie6.setLanguage("Mandarin(Sub:English,Chinese)");
        movie6.setAgeRestriction(2);

        Movie movie7 = new Movie();
        movie7.setTitle("Concrete Utopia");
        movie7.setDescription("The world has been reduced to rubble by a massive earthquake. While no one knows for sure how far the ruins stretch, or what the cause of the earthquake may be, in the heart of Seoul there is only one apartment building left standing. It is called Hwang Gung Apartments. As time passes, outsiders start coming in to Hwang Gung Apartments trying to escape the extreme cold. Before long, the apartment residents are unable to cope with the increasing numbers. Feeling a threat to their very survival, the residents enact a special measure.");
        movie7.setRuntimeInMinute(130);
        movie7.setDirector("Uhm Tae-Hwa");
        movie7.setCast("Lee Byung-hun, Park Seo-joon, Park Bo-young");
        movie7.setGenre("Action/Drama/Thriller");
        movie7.setReleaseDate(LocalDate.of(2023, 8, 24));
        movie7.setLanguage("Korean(Sub:English,Chinese)");
        movie7.setAgeRestriction(1);

        Movie movie8 = new Movie();
        movie8.setTitle("The Equalizer 3");
        movie8.setDescription("Since giving up his life as a government assassin, Robert McCall (Denzel Washington) has struggled to reconcile the horrific things he’s done in the past and finds a strange solace in serving justice on behalf of the oppressed. Finding himself surprisingly at home in Southern Italy, he discovers his new friends are under the control of local crime bosses. As events turn deadly, McCall knows what he has to do: become his friends’ protector by taking on the mafia.");
        movie8.setRuntimeInMinute(109);
        movie8.setDirector("Antoine Fuqua");
        movie8.setCast("Denzel Washington, Dakota Fanning, David Denman");
        movie8.setGenre("Action/Thriller");
        movie8.setReleaseDate(LocalDate.of(2023, 8, 31));
        movie8.setLanguage("English(Sub:Chinese)");
        movie8.setAgeRestriction(2);

        Movie movie9 = new Movie();
        movie9.setTitle("Past Lives");
        movie9.setDescription("Nora (Greta Lee) and Hae Sung (Teo Yoo), two deeply connected childhood friends, are wrest apart after Nora’s family emigrates from South Korea. Two decades later, they are reunited in New York for one fateful week as they confront notions of destiny, love, and the choices that make a life, in this heartrending modern romance.");
        movie9.setRuntimeInMinute(106);
        movie9.setDirector("Celine Song");
        movie9.setCast("Greta Lee, Teo Yoo, John Magaro");
        movie9.setGenre("Drama/Romance");
        movie9.setReleaseDate(LocalDate.of(2023, 8, 24));
        movie9.setLanguage("English,Korean(Sub:English,Chinese)");
        movie9.setAgeRestriction(1);

        Movie movie10 = new Movie();
        movie10.setTitle("Disney And Pixar's Elemental");
        movie10.setDescription("Disney and Pixar's \"Elemental\", an all-new, original feature film set in Element City, where fire, water, land and air residents live together. The story introduces Ember, a tough, quick-witted and fiery young woman, whose friendship with a fun, sappy, go with the flow guy named Wade challenges her beliefs about the world they live in.");
        movie10.setRuntimeInMinute(109);
        movie10.setDirector("Peter Sohn");
        movie10.setCast("Leah Lewis, Mamoudou Athie");
        movie10.setGenre("Animation");
        movie10.setReleaseDate(LocalDate.of(2023, 6, 15));
        movie10.setLanguage("English(Sub:Chinese)");
        movie10.setAgeRestriction(1);

        movieRepository.save(movie1);
        movieRepository.save(movie2);
        movieRepository.save(movie3);
        movieRepository.save(movie4);
        movieRepository.save(movie5);
        movieRepository.save(movie6);
        movieRepository.save(movie7);
        movieRepository.save(movie8);
        movieRepository.save(movie9);
        movieRepository.save(movie10);


//        Movie movie6 = new Movie();
//        movie6.setTitle("The White Storm 3: Heaven Or Hell");
//        movie6.setDescription("");
//        movie6.setRuntimeInMinute(163);
//        movie6.setDirector("Christopher McQuarrie");
//        movie6.setCast("");
//        movie6.setGenre("Action/Adventure");
//        movie6.setReleaseDate(LocalDate.of(2023, 7, 13));
//        movie6.setLanguage("English(Sub:Chinese)");
//        movie6.setAgeRestriction(1);


    }


}



