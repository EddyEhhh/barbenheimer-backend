package com.distinction.barbenheimer.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@IdClass(MovieImage.class)
public class MovieImage implements Serializable {

    @Id
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "movie_id")
    private Movie movie;

    @Id
    private String imageUrl;


    @Override
    public String toString(){
        return movie.getTitle() + " | " +  imageUrl;
    }
}
