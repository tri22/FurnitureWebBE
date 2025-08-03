package com.example.myfurniture.service;

import com.example.myfurniture.dto.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class UploadService {

    // path is /public/uploads/avatars/ or /public/uploads/products/
    public ApiResponse<?> uploadImage(MultipartFile file, String path) {
        try {
            if (file.isEmpty()) {
                return ApiResponse.builder()
                        .code(HttpStatus.BAD_REQUEST.value())
                        .message("Empty image file")
                        .build();
            }
            // Tạo thư mục nếu chưa có
            File uploadDir = new File(path);
            if (!uploadDir.exists()) uploadDir.mkdirs();

            // Tên file duy nhất
            String filename = UUID.randomUUID() + "_" + file.getOriginalFilename();
            Path filepath = Paths.get(path, filename);

            // Lưu file vật lý
            Files.write(filepath, file.getBytes());
            String imgUrl = path + filename;

            return ApiResponse.builder()
                    .code(HttpStatus.OK.value())
                    .message("Upload thành công")
                    .result(imgUrl)
                    .build();
        } catch (IOException e) {
            return ApiResponse.builder()
                    .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .message("Upload thất bại: " + e.getMessage())
                    .build();
        }
    }
}
