package com.ecommerce.project.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {

    @Override
    public String uploadImage(String path, MultipartFile file) throws IOException {
        String originalFileName = file.getOriginalFilename();
        String randomId = UUID.randomUUID().toString();
        String fileName = randomId.concat(originalFileName.substring(originalFileName.lastIndexOf('.')));
        String filePath = path + File.separator + fileName;

        File folder = new File(path);
        if (!folder.exists())
            folder.mkdir();

        Files.copy(file.getInputStream(), Paths.get(filePath));
        return fileName;
    }

    @Override
    public String getImage(String path, String image) {
        String filePath = path + File.separator + image;
        File file=new File(filePath);
        String encodedString = null;
        try {
            byte[] fileContent = Files.readAllBytes(file.toPath());
            encodedString = Base64.getEncoder().encodeToString(fileContent);
            System.out.println("Base64 encoded image: " + encodedString);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return encodedString;
    }
}
