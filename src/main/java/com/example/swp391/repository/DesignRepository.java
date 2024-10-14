package com.example.swp391.repository;
import com.example.swp391.entity.TypeDesignEnity;
import com.example.swp391.entity.DesignEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DesignRepository extends JpaRepository<DesignEnity, Long> {
    List<DesignEnity> findByDesignName(String designName);
    List<DesignEnity> findByDesignNameContainingIgnoreCaseAndTypeDesign_TypeDesignId(String designName, Long typeDesignId);
}

