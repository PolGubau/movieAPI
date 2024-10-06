package com.polgubau.filmAPI.FilmAPI.exceptions;

public class FileExistsException extends RuntimeException {
    public FileExistsException(String message) {
        super(message);
    }
}
