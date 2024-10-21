//package com.example.swp391.controller;
//import com.example.swp391.entity.AccountEntity;
//import com.example.swp391.service.AccountService;
//import jakarta.servlet.http.HttpSession;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.multipart.MultipartFile;
//import org.springframework.web.servlet.mvc.support.RedirectAttributes;
//
//
//import java.io.File;
//import java.io.IOException;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//
//@Controller
//public class ProfileController {
//    @Autowired
//    private AccountService accountService;
//
//    private static final String UPLOAD_DIR = "/path/to/upload/dir"; // Change this to the actual path where you store images
//
//    @PostMapping("/upload-profile-image")
//    public String uploadProfileImage(@RequestParam("profileImage") MultipartFile profileImage, HttpSession session, RedirectAttributes redirectAttributes) {
//        AccountEntity loggedInUser = (AccountEntity) session.getAttribute("loggedInUser");
//        if (loggedInUser == null) {
//            redirectAttributes.addFlashAttribute("message", "Please log in first.");
//            return "redirect:/login";
//        }
//
//        if (!profileImage.isEmpty()) {
//            try {
//                // Create a file path for the uploaded image
//                String fileName = profileImage.getOriginalFilename();
//                Path uploadPath = Paths.get(UPLOAD_DIR, fileName);
//
//                // Save the uploaded file to the server
//                profileImage.transferTo(uploadPath.toFile());
//
//                // Update the account's image path in the database
//                loggedInUser.setImages("/uploads/" + fileName); // Update this to the actual file path in the app
//                accountService.updateAccount(loggedInUser);
//
//                // Update the session with the new user info
//                session.setAttribute("loggedInUser", loggedInUser);
//
//                redirectAttributes.addFlashAttribute("message", "Profile image updated successfully!");
//            } catch (IOException e) {
//                redirectAttributes.addFlashAttribute("message", "Failed to upload image.");
//                e.printStackTrace();
//            }
//        } else {
//            redirectAttributes.addFlashAttribute("message", "Please choose an image to upload.");
//        }
//
//        return "redirect:/profile"; // Redirect back to profile page after uploading
//    }
//}
