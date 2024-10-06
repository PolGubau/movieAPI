package com.polgubau.filmAPI.FilmAPI.service;

import com.polgubau.filmAPI.FilmAPI.dto.MovieDto;
import com.polgubau.filmAPI.FilmAPI.entities.Movie;
import com.polgubau.filmAPI.FilmAPI.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@Service

public class MovieServiceImpl implements MovieService {

    private final MovieRepository movieRepository;
    private final FileService fileService;
    @Value("${base.url}")

    private String baseUrl;

    @Value("${project.poster.path}")
    private String path;

    public MovieServiceImpl(MovieRepository movieRepository, FileService fileService) {
        this.movieRepository = movieRepository;
        this.fileService = fileService;
    }

    /**
     * @param movieDto A movieDto object
     * @param file     A MultipartFile object
     *
     * @return A MovieDto object
     */
    @Override
    public MovieDto addMovie(MovieDto movieDto, MultipartFile file) throws IOException {
        //1. upload file
        String uploadedFileName = fileService.uploadFile(path, file);

        //2. Set poster value as filename
        movieDto.setPoster(uploadedFileName);
        //3. map dto to Movie object
        Movie movie = new Movie(
                movieDto.getMovieId(),
                movieDto.getTitle(),
                movieDto.getDirector(),
                movieDto.getStudio(),
                movieDto.getMovieCast(),
                movieDto.getReleaseYear(),
                movieDto.getPoster()
        );

        //4. save movie object -> saved Movie object
        Movie savedMovie = movieRepository.save(movie);
        //5. return map saved Movie object to MovieDto object
        return buildMovieDto(savedMovie, uploadedFileName);

    }

    private MovieDto buildMovieDto(Movie movie, String rawPosterUrl) {
        String posterUrl = getPosterUrl(fileService.parsedPosterUrl(rawPosterUrl));

        return new MovieDto(
                movie.getMovieId(),
                movie.getTitle(),
                movie.getDirector(),
                movie.getStudio(),
                movie.getMovieCast(),
                movie.getReleaseYear(),
                movie.getPoster(),
                posterUrl
        );
    }


    private String getPosterUrl(String poster) {
        return baseUrl + "/file/get/" + poster;
    }


    /**
     * @param movieId An Integer object
     *
     * @return A MovieDto object
     */
    @Override
    public MovieDto getMovie(Integer movieId) {
        // 1. check the data in DB and if exists, fetch
        Movie movie = movieRepository.findById(movieId).orElseThrow(() -> new RuntimeException("Movie not found"));
        return buildMovieDto(movie, movie.getPoster());
    }

    /**
     * @return A List of MovieDto objects
     */
    @Override
    public List<MovieDto> getAllMovies() {
        List<Movie> movies = movieRepository.findAll();
        return movies.stream().map(movie -> buildMovieDto(movie, movie.getPoster())).toList();
    }


    @Override
    public MovieDto updateMovie(Integer movieId, MovieDto movieDto, MultipartFile file) throws IOException {
        //1. check if movie exists
        Movie old_movie = movieRepository.findById(movieId).orElseThrow(() -> new RuntimeException("Movie not found"));

        //2. if file is null, do nothing with the file
        // - if is not null, delete the old file and upload the new file
        String fileName = old_movie.getPoster();
        if (file != null) {
            Files.deleteIfExists(Paths.get(path + File.separator + fileName));
            String uploadedFileName = fileService.uploadFile(path, file);
            movieDto.setPoster(uploadedFileName);
        }
        //3. map dto to Movie object
        Movie movie = new Movie(
                old_movie.getMovieId(),
                movieDto.getTitle(),
                movieDto.getDirector(),
                movieDto.getStudio(),
                movieDto.getMovieCast(),
                movieDto.getReleaseYear(),
                movieDto.getPoster()
        );

        //4. save the movie object -> saved Movie object
        Movie uploadedMovie = movieRepository.save(movie);

        //5. return map saved Movie object to MovieDto object
        return buildMovieDto(uploadedMovie, uploadedMovie.getPoster());


    }

    /**
     * @param movieId An Integer object
     */
    @Override
    public String deleteMovie(Integer movieId) throws IOException {
        //1. check if movie exists
        Movie movie = movieRepository.findById(movieId).orElseThrow(() -> new RuntimeException("Movie not found"));
        Integer id = movie.getMovieId();

        Files.deleteIfExists(Paths.get(path + File.separator + movie.getPoster()));

        movieRepository.delete(movie);

        return "Movie with id " + id + " deleted successfully";
    }
}
