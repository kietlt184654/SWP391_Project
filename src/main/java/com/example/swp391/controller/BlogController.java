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

    private final String UPLOAD_DIR = "D:\\K5\\SWP391\\Process_Img_Task";

    // Phương thức hiển thị danh sách blog cho Consulting Staff và khách hàng
    @GetMapping
    public String listBlogs(Model model, HttpSession session) {
        AccountEntity account = (AccountEntity) session.getAttribute("loggedInUser");
        model.addAttribute("blogs", blogService.getAllBlogs());

        // Kiểm tra AccountTypeID để hiển thị giao diện phù hợp
        if (account != null && "Consulting Staff".equals(account.getAccountTypeID())) {
            return "blog-list"; // Trang có nút chỉnh sửa cho Consulting Staff
        } else {
            return "customer-blog-list"; // Trang không có nút chỉnh sửa cho khách hàng
        }
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
                Path path = Paths.get(UPLOAD_DIR, imageFile.getOriginalFilename());
                Files.createDirectories(path.getParent());
                Files.write(path, imageFile.getBytes());

                imagePath = "/uploads/" + imageFile.getOriginalFilename();
                blog.setImageUrl(imagePath);
            }
        } catch (IOException e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("message", "Error uploading image.");
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

    // Hiển thị form chỉnh sửa blog cho Consulting Staff
    @GetMapping("/{id}/edit")
    public String showEditBlogForm(@PathVariable("id") int id, Model model, HttpSession session) {
        AccountEntity account = (AccountEntity) session.getAttribute("loggedInUser");

        // Kiểm tra quyền hạn của tài khoản (chỉ cho phép accountTypeID là 'Consulting Staff' chỉnh sửa)
        if (account == null || !"Consulting Staff".equals(account.getAccountTypeID())) {
            return "redirect:/blogs"; // Khách hàng hoặc người dùng khác sẽ được chuyển hướng về trang danh sách blog
        }

        BlogEntity blog = blogService.getBlogById(id);
        if (blog == null) {
            return "errorPage";
        }
        model.addAttribute("blog", blog);
        return "edit-blog";
    }

    // Xử lý cập nhật blog
    @PostMapping("/{id}/edit")
    public String updateBlog(
            @PathVariable("id") int id,
            @ModelAttribute BlogEntity blog,
            @RequestParam("imageFile") MultipartFile imageFile,
            HttpSession session,
            RedirectAttributes redirectAttributes) {

        AccountEntity account = (AccountEntity) session.getAttribute("loggedInUser");

        // Kiểm tra quyền hạn của tài khoản (chỉ cho phép accountTypeID là 'Consulting Staff' chỉnh sửa)
        if (account == null || !"Consulting Staff".equals(account.getAccountTypeID())) {
            return "redirect:/blogs";
        }

        BlogEntity existingBlog = blogService.getBlogById(id);
        if (existingBlog == null) {
            return "errorPage";
        }

        // Cập nhật các trường thông tin blog
        existingBlog.setTitle(blog.getTitle());
        existingBlog.setContent(blog.getContent());
        existingBlog.setUpdatedAt(LocalDateTime.now());

        // Xử lý upload ảnh mới nếu có
        try {
            if (!imageFile.isEmpty()) {
                Path path = Paths.get(UPLOAD_DIR, imageFile.getOriginalFilename());
                Files.createDirectories(path.getParent());
                Files.write(path, imageFile.getBytes());

                String imagePath = "/uploads/" + imageFile.getOriginalFilename();
                existingBlog.setImageUrl(imagePath);
            }
        } catch (IOException e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("message", "Error uploading image.");
            return "errorPage";
        }

        blogService.saveBlog(existingBlog);
        redirectAttributes.addFlashAttribute("message", "Blog updated successfully.");
        return "redirect:/blogs";
    }
}
