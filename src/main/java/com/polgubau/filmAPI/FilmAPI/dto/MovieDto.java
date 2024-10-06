package com.polgubau.filmAPI.FilmAPI.dto;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MovieDto {
    private Integer movieId;

    @NotBlank(message = "Title is mandatory")
    private String title;

    @NotBlank(message = "Director is mandatory")
    private String director;

    @NotBlank(message = "Studio is mandatory")
    private String studio;

    private Set<String> movieCast;

    private Integer releaseYear;

    @NotBlank(message = "poster is mandatory")
    private String poster;

    @NotBlank(message = "poster's URL is mandatory")
    private String posterUrl;
}
