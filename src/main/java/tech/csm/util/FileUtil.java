package tech.csm.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class FileUtil {

    @Value("${upload.dir}")
    private String uploadDir;

    public String uploadFile(MultipartFile file) {

        if (file == null || file.isEmpty()) {
            return null;
        }

        File dir = new File(uploadDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }

       
        String cleanName = file.getOriginalFilename().replaceAll("\\s+", "_");
        String storedName = UUID.randomUUID() + "_" + cleanName;

        try {
            file.transferTo(Paths.get(uploadDir, storedName));
        } catch (IOException e) {
            throw new RuntimeException("File upload failed", e);
        }

        return storedName;
    }

    public Path resolveFile(String fileName) {

        Path path = Paths.get(uploadDir).resolve(fileName).normalize();

        if (!Files.exists(path)) {
            throw new RuntimeException("File not found: " + fileName);
        }

        return path;
    }

    
    public String getUploadDir() {
        return uploadDir;
    }
    
    public boolean deleteFile(String fileName) {

        if (fileName == null || fileName.isBlank()) {
            return false;
        }

        try {
            Path path = resolveFile(fileName);
            return Files.deleteIfExists(path);
        } catch (IOException e) {
            throw new RuntimeException("Failed to delete file: " + fileName, e);
        }
    }

}
