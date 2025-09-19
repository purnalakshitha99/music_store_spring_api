package com.musicstore.musicstore.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
@AllArgsConstructor
public class CloudinaryService {

    private final Cloudinary cloudinary;

    /**
     * Uploads a file to Cloudinary.
     * @param file The file to upload.
     * @param folderName The name of the folder in Cloudinary to store the file in.
     * @return The secure URL of the uploaded file.
     */
    public String uploadFile(MultipartFile file, String folderName) {
        try {
            // Upload the file to Cloudinary with specified options
            Map<?, ?> uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.asMap(
                    "folder", folderName
            ));

            // Return the secure URL provided by Cloudinary in the response
            return (String) uploadResult.get("secure_url");

        } catch (IOException e) {
            // In a production app, you should log this error and throw a custom, user-friendly exception
            throw new RuntimeException("Failed to upload file to Cloudinary", e);
        }
    }
}