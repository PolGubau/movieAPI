package com.polgubau.filmAPI.FilmAPI.service;

import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

@Service
public class FileServiceImpl implements FileService {

    @Override
    public String uploadFile(String path, @NotNull MultipartFile file) throws IOException {
        // get name of file
        String fileName = file.getOriginalFilename();

        if (fileName == null) {
            throw new RuntimeException("File name is null");
        }

        boolean itExists = Files.exists(Paths.get(path + File.separator + fileName));
        if (itExists) {
            throw new RuntimeException("File already exists, please rename it");
        }


        // get input stream
        InputStream inputStream = file.getInputStream();
        // get path of file
        String filePath = path + File.separator + parsedPosterUrl(fileName);

        // create file object
        File fileObj = new File(path);
        // if file does not exist, create it
        if (!fileObj.exists()) {
            fileObj.mkdir();
        }

        // copy file to path
        Files.copy(inputStream, Paths.get(filePath));

        return fileName;
    }

    public String parsedPosterUrl(String poster) {
        // poster could be a really weird string,
        // so we need to parse it to get the correct url (dashes instead of spaces, etc)

        return poster.replaceAll(" ", "-");

    }

    @Override
    public InputStream getResourceFile(String path, String fileName) throws FileNotFoundException {
        String filePath = path + File.separator + fileName;
        try {
            return new FileInputStream(filePath);
        } catch (IOException e) {
            throw new FileNotFoundException("File not found");
        }
    }
}
