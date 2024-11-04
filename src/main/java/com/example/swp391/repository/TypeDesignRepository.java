package com.example.swp391.repository;

import com.example.swp391.entity.TypeDesignEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TypeDesignRepository extends JpaRepository<TypeDesignEntity, Long> {
}
