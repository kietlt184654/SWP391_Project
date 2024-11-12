package com.example.swp391.repository;

import com.example.swp391.entity.RatingFeedbackEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RatingFeedbackRepository extends JpaRepository<RatingFeedbackEntity, Integer> {
    RatingFeedbackEntity findByProject_ProjectID(Long projectId);
}