//package com.example.swp391.controller;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.multipart.MultipartFile;
//import org.springframework.web.servlet.mvc.support.RedirectAttributes;
//
//import java.io.File;
//import java.io.IOException;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//
//@RestController
//@RequestMapping("/upload")
//public class ImageUploadController {
//
//    // Đường dẫn lưu trữ hình ảnh trên server
//    @Value("${upload.path}")
//    private String uploadDir;
//
//    @PostMapping("/image")
//    public String uploadImage(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) {
//        if (file.isEmpty()) {
//            return "File không có dữ liệu!";
//        }
//
//        try {
//            // Lưu file vào thư mục trên server
//            byte[] bytes = file.getBytes();
//            Path path = Paths.get(uploadDir + file.getOriginalFilename());
//            Files.write(path, bytes);
//
//            // Trả về đường dẫn của file để lưu vào database
//            return "/images/" + file.getOriginalFilename();
//
//        } catch (IOException e) {
//            e.printStackTrace();
//            return "Lỗi khi tải file!";
//        }
//    }
//}
//
//
