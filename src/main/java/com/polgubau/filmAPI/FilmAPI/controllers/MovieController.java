package com.polgubau.filmAPI.FilmAPI.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.polgubau.filmAPI.FilmAPI.dto.MovieDto;
import com.polgubau.filmAPI.FilmAPI.exceptions.EmptyFileException;
import com.polgubau.filmAPI.FilmAPI.service.MovieService;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/movie")
public class MovieController {
    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @PostMapping("")
    public ResponseEntity<MovieDto> addMovieHandler(
            @RequestPart MultipartFile file,
            @RequestPart String movieDto
    ) throws IOException, EmptyFileException {
        MovieDto dto = convertToMovieDto(movieDto);
        return new ResponseEntity<>(movieService.addMovie(dto, file), HttpStatus.CREATED);
    }

    private MovieDto convertToMovieDto(String movieDtoObj) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(movieDtoObj, MovieDto.class);
    }

    @GetMapping("/{movieId}")
    public ResponseEntity<MovieDto> getMovieHandler(@PathVariable Integer movieId) {
        return ResponseEntity.ok(movieService.getMovie(movieId));
    }

    @GetMapping("")
    public ResponseEntity<List<MovieDto>> getAllMoviesHandler() {
        return ResponseEntity.ok(movieService.getAllMovies());
    }

    @PutMapping("/{movieId}")
    public ResponseEntity<MovieDto> updateMovieHandler(
            @PathVariable Integer movieId,
            @RequestPart @NotNull MultipartFile file,
            @RequestPart String movieDto
    ) throws IOException, EmptyFileException {

        if (file.isEmpty()) file = null;

        MovieDto dto = convertToMovieDto(movieDto);
        return ResponseEntity.ok(movieService.updateMovie(movieId, dto, file));

    }

    @DeleteMapping("/{movieId}")
    public ResponseEntity<String> deleteMovieHandler(@PathVariable Integer movieId) throws IOException {
        return ResponseEntity.ok(movieService.deleteMovie(movieId));
    }


}
