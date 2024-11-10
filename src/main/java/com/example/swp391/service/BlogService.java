package com.example.swp391.service;

import com.example.swp391.entity.BlogEntity;
import com.example.swp391.repository.BlogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class BlogService {

    @Autowired
    private BlogRepository blogRepository;

    public BlogEntity saveBlog(BlogEntity blog) {
        blog.setCreatedAt(LocalDateTime.now());
        blog.setUpdatedAt(LocalDateTime.now());
        return blogRepository.save(blog);
    }

    public List<BlogEntity> getAllBlogs() {
        return blogRepository.findAll();
    }

    public List<BlogEntity> getBlogsByCustomerId(Long customerId) {
        return blogRepository.findByCustomer_CustomerID(customerId);
    }

    public BlogEntity getBlogById(int blogID) {
        return blogRepository.findById(blogID).orElse(null);
    }

    public void deleteBlog(int blogID) {
        blogRepository.deleteById(blogID);
    }
}
