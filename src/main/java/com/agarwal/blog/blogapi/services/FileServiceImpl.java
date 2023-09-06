package com.agarwal.blog.blogapi.services;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService{

    @Override
    public String uploadImage(String path, MultipartFile file) throws IOException {
         //File name
        String fileName = file.getOriginalFilename();
        //abc.png

        //random name generate file
        String randomId = UUID.randomUUID().toString();
        String fileNewName = randomId.concat(fileName.substring(fileName.lastIndexOf(".")));

        //full path
        String fullPath = path + File.separator + fileNewName;

        //create folder if not created
        File f = new File(path);

        if(!f.exists()) {
            f.mkdir();
        }

        //file copy
        Files.copy(file.getInputStream(), Paths.get(fullPath));
        return fileNewName;
    }

    @Override
    public InputStream getResource(String path, String fileName) throws FileNotFoundException {
        String fullPath = path + File.separator + fileName;
        return new FileInputStream(fullPath);
    }
}
