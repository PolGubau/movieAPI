package com.polgubau.filmAPI.FilmAPI.repository;

import com.polgubau.filmAPI.FilmAPI.entities.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<Movie, Integer> {

}
