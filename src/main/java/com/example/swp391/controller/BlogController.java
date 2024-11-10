package com.example.swp391.controller;

import com.example.swp391.entity.AccountEntity;
import com.example.swp391.entity.BlogEntity;
import com.example.swp391.entity.CustomerEntity;
import com.example.swp391.service.BlogService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;

@Controller
@RequestMapping("/blogs")
public class BlogController {

    @Autowired
    private BlogService blogService;

    // Đường dẫn lưu trữ hình ảnh tuyệt đối
    private final String UPLOAD_DIR = "D:\\K5\\SWP391\\Process_Img_Task";

    // Hiển thị danh sách blog
    @GetMapping
    public String listBlogs(Model model) {
        model.addAttribute("blogs", blogService.getAllBlogs());
        return "blog-list";
    }

    // Hiển thị form tạo blog mới
    @GetMapping("/create")
    public String showCreateBlogForm(Model model) {
        model.addAttribute("blog", new BlogEntity());
        return "create-blog";
    }

    // Xử lý tạo blog với upload ảnh
    @PostMapping("/create")
    public String createBlog(
            @ModelAttribute BlogEntity blog,
            @RequestParam("imageFile") MultipartFile imageFile,
            HttpSession session,
            RedirectAttributes redirectAttributes) {

        AccountEntity account = (AccountEntity) session.getAttribute("loggedInUser");
        if (account == null || account.getCustomer() == null) {
            return "redirect:/login";
        }

        CustomerEntity customer = account.getCustomer();
        blog.setCustomer(customer);
        blog.setCreatedAt(LocalDateTime.now());
        blog.setUpdatedAt(LocalDateTime.now());

        // Xử lý upload ảnh
        String imagePath = null;
        try {
            if (!imageFile.isEmpty()) {
                // Lưu ảnh vào đường dẫn tuyệt đối
                Path path = Paths.get(UPLOAD_DIR, imageFile.getOriginalFilename());
                Files.createDirectories(path.getParent());
                Files.write(path, imageFile.getBytes());

                // Đặt URL cho ảnh (URL truy cập công khai)
                imagePath = "/uploads/" + imageFile.getOriginalFilename();
                blog.setImageUrl(imagePath);
            }
        } catch (IOException e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("message", "Lỗi khi tải lên hình ảnh.");
            return "errorPage";
        }

        blogService.saveBlog(blog);
        return "redirect:/blogs";
    }

    // Hiển thị chi tiết blog
    @GetMapping("/{id}")
    public String viewBlog(@PathVariable("id") int id, Model model) {
        BlogEntity blog = blogService.getBlogById(id);
        if (blog == null) {
            return "errorPage";
        }
        model.addAttribute("blog", blog);
        return "view-blog";
    }
}
