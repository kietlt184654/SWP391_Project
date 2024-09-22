package com.example.swp391.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "Rating_Feedback")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RatingFeedbackEnity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ratingFeedbackId;

    @ManyToOne
    @JoinColumn(name = "customerId")
    private CustomerEnity customer;

    @ManyToOne
    @JoinColumn(name = "projectId")
    private ProjectEnity project;

    private int rating;
    private String feedback;
}
