package com.polgubau.filmAPI.FilmAPI.service;

import com.polgubau.filmAPI.FilmAPI.exceptions.EmptyFileException;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public interface FileService {
    String uploadFile(String path, MultipartFile file) throws IOException, EmptyFileException;

    String parsedPosterUrl(String poster);

    InputStream getResourceFile(String path, String fileName) throws FileNotFoundException;


}
