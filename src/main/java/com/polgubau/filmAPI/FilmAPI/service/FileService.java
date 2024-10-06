package com.polgubau.filmAPI.FilmAPI.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public interface FileService {
    String uploadFile(String path, MultipartFile file) throws IOException;
    String parsedPosterUrl(String poster);
    InputStream getResourceFile(String path, String fileName) throws FileNotFoundException;


}
