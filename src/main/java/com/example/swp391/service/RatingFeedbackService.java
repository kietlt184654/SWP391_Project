package com.example.swp391.service;

import com.example.swp391.entity.RatingFeedbackEntity;
import com.example.swp391.repository.RatingFeedbackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RatingFeedbackService {

    @Autowired
    private RatingFeedbackRepository ratingFeedbackRepository;

    public void saveFeedback(RatingFeedbackEntity feedback) {
        ratingFeedbackRepository.save(feedback);
    }
    public RatingFeedbackEntity findByProjectId(Long projectId) {
        return ratingFeedbackRepository.findByProject_ProjectID(projectId);
    }
}
