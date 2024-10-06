package com.polgubau.filmAPI.FilmAPI.service;

import com.polgubau.filmAPI.FilmAPI.dto.MovieDto;
import com.polgubau.filmAPI.FilmAPI.dto.MoviePageResponse;
import com.polgubau.filmAPI.FilmAPI.exceptions.EmptyFileException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface MovieService {
    MovieDto addMovie(MovieDto movieDto, MultipartFile file) throws IOException, EmptyFileException;

    MovieDto getMovie(Integer movieId);

    /**
     * Retrieves a list of all movies.
     *
     * @return a list of MovieDto objects representing all movies.
     */
    List<MovieDto> getAllMovies();

    MovieDto updateMovie(Integer movieId, MovieDto movieDto, MultipartFile file) throws IOException, EmptyFileException;

    String deleteMovie(Integer movieId) throws IOException;

    MoviePageResponse getAllMoviesPaginatedAndSorted(Integer pageNumber, Integer pageSize, String sortBy, String dir);

}
