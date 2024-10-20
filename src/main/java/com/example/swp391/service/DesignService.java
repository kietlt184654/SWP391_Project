package com.example.swp391.service;

import com.example.swp391.entity.DesignEntity;
import com.example.swp391.repository.DesignRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DesignService {
    @Autowired
    private DesignRepository designRepository;

    public List<DesignEntity> getAllProducts() {
        return designRepository.findAll(); // Lấy tất cả các sản phẩm từ database
    }
//    public Optional<DesignEntity> getProductById(Long id) {
//        return designRepository.findById(String.valueOf(id));
//    }
//
//    public DesignEntity saveProduct(DesignEntity product) {
//        return designRepository.save(product);
//    }
public DesignEntity findDesignById(Long id) {
    return designRepository.findById(String.valueOf(id)).orElse(null);
}
    public Optional<DesignEntity> getProductById(Long id) {
        return designRepository.findById(String.valueOf(id));
    }

    public DesignEntity saveProduct(DesignEntity product) {
        return designRepository.save(product);
    }
}