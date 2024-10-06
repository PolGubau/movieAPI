package com.polgubau.filmAPI.FilmAPI.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer movieId;

    @Column(nullable = false, length = 200)
    @NotBlank(message = "Title is mandatory")
    private String title;

    @Column(nullable = false)
    @NotBlank(message = "Director is mandatory")
    private String director;

    @Column(nullable = false)
    @NotBlank(message = "Studio is mandatory")
    private String studio;

    @ElementCollection
    @CollectionTable(name = "movie_cast", joinColumns = @JoinColumn(name = "movie_id"))
    private Set<String> movieCast;

    @Column(nullable = false)
    @NotNull(message = "Release year is mandatory")
    @Min(value = 1888, message = "Release year should not be less than 1888") // The first film was made in 1888
    private Integer releaseYear;

    @Column(nullable = false)
    @NotBlank(message = "poster is mandatory")
    private String poster;
}
