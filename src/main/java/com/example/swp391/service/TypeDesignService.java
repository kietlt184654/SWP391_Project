package com.example.swp391.service;

import com.example.swp391.entity.TypeDesignEntity;
import com.example.swp391.repository.TypeDesignRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TypeDesignService {

    @Autowired
    private TypeDesignRepository typeDesignRepository;

    public TypeDesignEntity findById(Long id) {
        return typeDesignRepository.findById(id).orElse(null);
    }
}