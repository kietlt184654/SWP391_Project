package com.example.swp391.service;

import com.example.swp391.entity.DesignEntity;
import com.example.swp391.repository.DesignRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class DesignService {
    @Autowired
    private DesignRepository designRepository;

    public List<DesignEntity> getAllProducts() {
        return designRepository.findAll(); // Lấy tất cả các sản phẩm từ database
    }
}
