package com.distinction.barbenheimer.config;

import com.distinction.barbenheimer.model.Movie;
import com.distinction.barbenheimer.model.MovieImage;
import com.distinction.barbenheimer.repository.MovieRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

        List<Movie> movieToCreate = new ArrayList<>();


        //Show now
        Movie movie1 = new Movie();
        movie1.setTitle("Barbie");
        movie1.setDescription("To live in Barbie Land is to be a perfect being in a perfect place. Unless you have a full-on existential crisis. Or you’re a Ken.");
        movie1.setRuntimeInMinute(114);
        movie1.setDirector("Greta Gerwig");
        movie1.setCast("Margot Robbie, Ryan Gosling, Will Ferrell, Kate McKinnon, America Ferrera, Ariana Greenblatt, Emma Mackey, Alexandra Shipp, Issa Rae, Simu Liu");
        movie1.setGenre("Comedy");
        movie1.setReleaseDate(LocalDateTime.now().minusDays(14));
        movie1.setShowingDate(LocalDateTime.now().minusDays(7));
        movie1.setTicketSaleDate(LocalDateTime.now().minusDays(14));
        movie1.setLastShowingDate(LocalDateTime.now().plusMonths(2));
        movie1.setBasePrice(8);
        movie1.setLanguage("English(Sub:Chinese)");
        movie1.setAgeRestriction(1);
        MovieImage movie1Image = new MovieImage();
        movie1Image.setImageUrl("https://barbenheimer203-movies.s3.ap-southeast-1.amazonaws.com/movie-images/Barbie/img1");
        movie1Image.setMovie(movie1);
        List<MovieImage> movie1Images = new ArrayList<>();
        movie1Images.add(movie1Image);
        movie1.setMovieImages(movie1Images);
        movieToCreate.add(movie1);

        Movie movie2 = new Movie();
        movie2.setTitle("One and Only");
        movie2.setDescription("Chen Shuo, who has been performing “commercial street dance” for a living, meets his best coach, Ding Lei, and joins the street dance club Exclamation Point. The dancers in the club are all different, so they have different sparks and laughs with Chen Shuo. While Chen Shuo is integrating into the group, Exclamation Point is facing disbandment and Chen Shuo receives heavy blows one after another. The love for dance and the precious emotions of sympathy between teammates support Chen Shuo and Ding Lei to go through the slump and revive. In the end, Chen Shuo and Ding Lei win the highest glory of their own.");
        movie2.setRuntimeInMinute(124);
        movie2.setDirector("Da Peng");
        movie2.setCast("Wang Yibo, Huang Bo");
        movie2.setGenre("Comedy/Drama");
        movie2.setReleaseDate(LocalDateTime.now().minusDays(12));
        movie2.setShowingDate(LocalDateTime.now().minusDays(5));
        movie2.setTicketSaleDate(LocalDateTime.now().minusDays(12));
        movie2.setLastShowingDate(LocalDateTime.now().plusMonths(1));
        movie2.setBasePrice(8);
        movie2.setLanguage("Mandarin(Sub:English,Chinese)");
        movie2.setAgeRestriction(0);
        MovieImage movie2Image = new MovieImage();
        movie2Image.setImageUrl("https://barbenheimer203-movies.s3.ap-southeast-1.amazonaws.com/movie-images/One+and+Only/img2");
        movie2Image.setMovie(movie2);
        List<MovieImage> movie2Images = new ArrayList<>();
        movie2Images.add(movie2Image);
        movie2.setMovieImages(movie2Images);
        movieToCreate.add(movie2);


        Movie movie3 = new Movie();
        movie3.setTitle("Oppenheimer");
        movie3.setDescription("The film follows the story of American scientist J. Robert Oppenheimer and his role in the development of the atomic bomb.");
        movie3.setRuntimeInMinute(180);
        movie3.setDirector("Christopher Nolan");
        movie3.setCast("Cillian Murphy, Emily Blunt, Robert Downey Jr., Matt Damon, Rami Malek, Florence Pugh, Benny Safdie, Michael Angarano, Josh Hartnett, Kenneth Branagh");
        movie3.setGenre("Thriller");
        movie3.setReleaseDate(LocalDateTime.now().minusDays(14));
        movie3.setShowingDate(LocalDateTime.now().minusDays(7));
        movie3.setTicketSaleDate(LocalDateTime.now().minusDays(14));
        movie3.setLastShowingDate(LocalDateTime.now().plusMonths(2));
        movie3.setBasePrice(8);
        movie3.setLanguage("English(Sub:Chinese)");
        movie3.setAgeRestriction(3);
        MovieImage movie3Image = new MovieImage();
        movie3Image.setImageUrl("https://barbenheimer203-movies.s3.ap-southeast-1.amazonaws.com/movie-images/Oppenheimer/img3");
        movie3Image.setMovie(movie3);
        List<MovieImage> movie3Images = new ArrayList<>();
        movie3Images.add(movie3Image);
        movie3.setMovieImages(movie3Images);
        movieToCreate.add(movie3);

        Movie movie4 = new Movie();
        movie4.setTitle("Mission: Impossible - Dead Reckoning Part One");
        movie4.setDescription("In Mission: Impossible – Dead Reckoning Part One, Ethan Hunt (Tom Cruise) and his IMF team embark on their most dangerous mission yet: To track down a terrifying new weapon that threatens all of humanity before it falls into the wrong hands. With control of the future and the fate of the world at stake, and dark forces from Ethan’s past closing in, a deadly race around the globe begins. Confronted by a mysterious, all-powerful enemy, Ethan is forced to consider that nothing can matter more than his mission – not even the lives of those he cares about most.");
        movie4.setRuntimeInMinute(163);
        movie4.setDirector("Christopher McQuarrie");
        movie4.setCast("Tom Cruise, Ving Rhames, Simon Pegg, Rebecca Ferguson, Vanessa Kirby, Hayley Atwell, Shea Whigham, Pom Klementieff, Esai Morales, Henry Czerny");
        movie4.setGenre("Action/Adventure");
        movie4.setReleaseDate(LocalDateTime.now().minusDays(3));
        movie4.setShowingDate(LocalDateTime.now().plusDays(5));
        movie4.setTicketSaleDate(LocalDateTime.now());
        movie4.setLastShowingDate(LocalDateTime.now().plusMonths(2));
        movie4.setBasePrice(8);
        movie4.setLanguage("English(Sub:Chinese)");
        movie4.setAgeRestriction(1);
        MovieImage movie4Image = new MovieImage();
        movie4Image.setImageUrl("https://barbenheimer203-movies.s3.ap-southeast-1.amazonaws.com/movie-images/Mission%3A+Impossible+-+Dead+Reckoning+Part+One/img4");
        movie4Image.setMovie(movie4);
        List<MovieImage> movie4Images = new ArrayList<>();
        movie4Images.add(movie4Image);
        movie4.setMovieImages(movie4Images);
        movieToCreate.add(movie4);

        Movie movie5 = new Movie();
        movie5.setTitle("Teenage Mutant Ninja Turtles: Mutant Mayhem");
        movie5.setDescription("After years of being sheltered from the human world, the Turtle brothers set out to win the hearts of New Yorkers and be accepted as normal teenagers through heroic acts. Their new friend April O’Neil helps them take on a mysterious crime syndicate, but they soon get in over their heads when an army of mutants is unleashed upon them.");
        movie5.setRuntimeInMinute(99);
        movie5.setDirector("Jeff Rowe, Kyler Spears");
        movie5.setCast("Paul Rudd, Maya Rudolph, Hannibal Buress, Rose Byrne, John Cena, Jackie Chan, Ice Cube");
        movie5.setGenre("Action/Adventure/Animation");
        movie5.setReleaseDate(LocalDateTime.now().minusDays(5));
        movie5.setShowingDate(LocalDateTime.now().minusDays(2));
        movie5.setTicketSaleDate(LocalDateTime.now().minusDays(5));
        movie5.setLastShowingDate(LocalDateTime.now().plusMonths(1));
        movie5.setBasePrice(8);
        movie5.setLanguage("English(Sub:Chinese)");
        movie5.setAgeRestriction(0);
        MovieImage movie5Image = new MovieImage();
        movie5Image.setImageUrl("https://barbenheimer203-movies.s3.ap-southeast-1.amazonaws.com/movie-images/Teenage+Mutant+Ninja+Turtles%3A+Mutant+Mayhem/img5");
        movie5Image.setMovie(movie5);
        List<MovieImage> movie5Images = new ArrayList<>();
        movie5Images.add(movie5Image);
        movie5.setMovieImages(movie5Images);
        movieToCreate.add(movie5);

        Movie movie6 = new Movie();
        movie6.setTitle("The White Storm 3: Heaven Or Hell 扫毒3：人在天涯");
        movie6.setDescription("Hong Kong police agent Cheung (Aaron Kwok) works undercover in Kang’s (Sean Lau) drug cartel. For years, he assists the notorious Thai-Chinese military veteran in operating the syndicate out of Southeast Asia before moving to Hong Kong. Following the trail of information left by Cheung, Superintendent Au (Louis Koo) leads the Narcotics Bureau to bust the syndicate, but Cheung is heavily wounded and saved by Kang in the exchange of fire which follows. Word travels fast, and it isn’t long before militia leader Dai (Lo Ka Leung) receives a tip-off about Cheung’s betrayal… What emerges in the frontline of war is a familiar face, but this time, as a friend or foe?");
        movie6.setRuntimeInMinute(125);
        movie6.setDirector("Herman Yau 邱礼涛");
        movie6.setCast("Sean Lau 刘青云, Louis Koo 古天乐, Aaron Kwok 郭富城");
        movie6.setGenre("Action/Thriller");
        movie6.setReleaseDate(LocalDateTime.now().minusDays(8));
        movie6.setShowingDate(LocalDateTime.now().minusDays(5));
        movie6.setTicketSaleDate(LocalDateTime.now().minusDays(8));
        movie6.setLastShowingDate(LocalDateTime.now().plusMonths(1));
        movie6.setBasePrice(8);
        movie6.setLanguage("Mandarin(Sub:English,Chinese)");
        movie6.setAgeRestriction(2);
        MovieImage movie6Image = new MovieImage();
        movie6Image.setImageUrl("https://barbenheimer203-movies.s3.ap-southeast-1.amazonaws.com/movie-images/The+White+Storm+3%3A+Heaven+Or+Hell+%E6%89%AB%E6%AF%923%EF%BC%9A%E4%BA%BA%E5%9C%A8%E5%A4%A9%E6%B6%AF/img6");
        movie6Image.setMovie(movie6);
        List<MovieImage> movie6Images = new ArrayList<>();
        movie6Images.add(movie6Image);
        movie6.setMovieImages(movie6Images);
        movieToCreate.add(movie6);

        Movie movie7 = new Movie();
        movie7.setTitle("Concrete Utopia");
        movie7.setDescription("The world has been reduced to rubble by a massive earthquake. While no one knows for sure how far the ruins stretch, or what the cause of the earthquake may be, in the heart of Seoul there is only one apartment building left standing. It is called Hwang Gung Apartments. As time passes, outsiders start coming in to Hwang Gung Apartments trying to escape the extreme cold. Before long, the apartment residents are unable to cope with the increasing numbers. Feeling a threat to their very survival, the residents enact a special measure.");
        movie7.setRuntimeInMinute(130);
        movie7.setDirector("Uhm Tae-Hwa");
        movie7.setCast("Lee Byung-hun, Park Seo-joon, Park Bo-young");
        movie7.setGenre("Action/Drama/Thriller");
        movie7.setReleaseDate(LocalDateTime.now().minusDays(12));
        movie7.setShowingDate(LocalDateTime.now().minusDays(5));
        movie7.setTicketSaleDate(LocalDateTime.now().minusDays(8));
        movie7.setLastShowingDate(LocalDateTime.now().plusMonths(1));
        movie7.setBasePrice(8);
        movie7.setLanguage("Korean(Sub:English,Chinese)");
        movie7.setAgeRestriction(1);
        MovieImage movie7Image = new MovieImage();
        movie7Image.setImageUrl("https://barbenheimer203-movies.s3.ap-southeast-1.amazonaws.com/movie-images/Concrete+Utopia/img7");
        movie7Image.setMovie(movie7);
        List<MovieImage> movie7Images = new ArrayList<>();
        movie7Images.add(movie7Image);
        movie7.setMovieImages(movie7Images);
        movieToCreate.add(movie7);

        Movie movie8 = new Movie();
        movie8.setTitle("The Equalizer 3");
        movie8.setDescription("Since giving up his life as a government assassin, Robert McCall (Denzel Washington) has struggled to reconcile the horrific things he’s done in the past and finds a strange solace in serving justice on behalf of the oppressed. Finding himself surprisingly at home in Southern Italy, he discovers his new friends are under the control of local crime bosses. As events turn deadly, McCall knows what he has to do: become his friends’ protector by taking on the mafia.");
        movie8.setRuntimeInMinute(109);
        movie8.setDirector("Antoine Fuqua");
        movie8.setCast("Denzel Washington, Dakota Fanning, David Denman");
        movie8.setGenre("Action/Thriller");
        movie8.setReleaseDate(LocalDateTime.now().minusDays(5));
        movie8.setShowingDate(LocalDateTime.now().minusDays(5));
        movie8.setTicketSaleDate(LocalDateTime.now().minusDays(8));
        movie8.setLastShowingDate(LocalDateTime.now().plusMonths(1));
        movie8.setBasePrice(8);
        movie8.setLanguage("English(Sub:Chinese)");
        movie8.setAgeRestriction(2);
        MovieImage movie8Image = new MovieImage();
        movie8Image.setImageUrl("https://barbenheimer203-movies.s3.ap-southeast-1.amazonaws.com/movie-images/The+Equalizer+3/img8");
        movie8Image.setMovie(movie8);
        List<MovieImage> movie8Images = new ArrayList<>();
        movie8Images.add(movie8Image);
        movie8.setMovieImages(movie8Images);
        movieToCreate.add(movie8);

        Movie movie9 = new Movie();
        movie9.setTitle("Past Lives");
        movie9.setDescription("Nora (Greta Lee) and Hae Sung (Teo Yoo), two deeply connected childhood friends, are wrest apart after Nora’s family emigrates from South Korea. Two decades later, they are reunited in New York for one fateful week as they confront notions of destiny, love, and the choices that make a life, in this heartrending modern romance.");
        movie9.setRuntimeInMinute(106);
        movie9.setDirector("Celine Song");
        movie9.setCast("Greta Lee, Teo Yoo, John Magaro");
        movie9.setGenre("Drama/Romance");
        movie9.setReleaseDate(LocalDateTime.now().minusDays(11));
        movie9.setShowingDate(LocalDateTime.now().minusDays(7));
        movie9.setTicketSaleDate(LocalDateTime.now().minusDays(9));
        movie9.setLastShowingDate(LocalDateTime.now().plusMonths(2));
        movie9.setBasePrice(8);
        movie9.setLanguage("English,Korean(Sub:English,Chinese)");
        movie9.setAgeRestriction(1);
        MovieImage movie9Image = new MovieImage();
        movie9Image.setImageUrl("https://barbenheimer203-movies.s3.ap-southeast-1.amazonaws.com/movie-images/Past+Lives/img9");
        movie9Image.setMovie(movie9);
        List<MovieImage> movie9Images = new ArrayList<>();
        movie9Images.add(movie9Image);
        movie9.setMovieImages(movie9Images);
        movieToCreate.add(movie9);

        Movie movie10 = new Movie();
        movie10.setTitle("Disney And Pixar's Elemental");
        movie10.setDescription("Disney and Pixar's \"Elemental\", an all-new, original feature film set in Element City, where fire, water, land and air residents live together. The story introduces Ember, a tough, quick-witted and fiery young woman, whose friendship with a fun, sappy, go with the flow guy named Wade challenges her beliefs about the world they live in.");
        movie10.setRuntimeInMinute(109);
        movie10.setDirector("Peter Sohn");
        movie10.setCast("Leah Lewis, Mamoudou Athie");
        movie10.setGenre("Animation");
        movie10.setReleaseDate(LocalDateTime.now().minusDays(8));
        movie10.setShowingDate(LocalDateTime.now().minusDays(5));
        movie10.setTicketSaleDate(LocalDateTime.now().minusDays(8));
        movie10.setLastShowingDate(LocalDateTime.now().plusMonths(1));
        movie10.setBasePrice(8);
        movie10.setLanguage("English(Sub:Chinese)");
        movie10.setAgeRestriction(1);
        MovieImage movie10Image = new MovieImage();
        movie10Image.setImageUrl("https://barbenheimer203-movies.s3.ap-southeast-1.amazonaws.com/movie-images/Disney+And+Pixar's+Elemental/img10");
        movie10Image.setMovie(movie10);
        List<MovieImage> movie10Images = new ArrayList<>();
        movie10Images.add(movie10Image);
        movie10.setMovieImages(movie10Images);
        movieToCreate.add(movie10);

        Movie movie11 = new Movie();
        movie11.setTitle("YOU SHOULD NOT SEE THIS MOVIE");
        movie11.setDescription("THIS MOVIE SHOULD NOT BE SEEN");
        movie11.setRuntimeInMinute(300);
        movie11.setDirector("No One");
        movie11.setCast("No One");
        movie11.setGenre("Action");
        movie11.setReleaseDate(LocalDateTime.now().minusMonths(2));
        movie11.setShowingDate(LocalDateTime.now().minusDays(50));
        movie11.setTicketSaleDate(LocalDateTime.now().minusDays(48));
        movie11.setLastShowingDate(LocalDateTime.now().minusMonths(1));
        movie11.setBasePrice(8);
        movie11.setLanguage("English(Sub:Chinese)");
        movie11.setAgeRestriction(1);
        movieToCreate.add(movie11);

        movieRepository.saveAll(movieToCreate);






//        This is a template.
//        Movie movie6 = new Movie();
//        movie6.setTitle("");
//        movie6.setDescription("");
//        movie6.setRuntimeInMinute();
//        movie6.setDirector("");
//        movie6.setCast("");
//        movie6.setGenre("");
//        movie6.setReleaseDate();
//        movie6.setShowingDate();
//        movie6.setTicketSaleDate();
//        movie6.setLastShowingDate();
//        movie6.setBasePrice();
//        movie6.setLanguage("");
//        movie6.setAgeRestriction();


    }



}



