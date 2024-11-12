package com.example.swp391.entity;//package com.example.swp391.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor

@Table(name = "RatingFeedback")
public class RatingFeedbackEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ratingFeedbackId;

    @ManyToOne
    @JoinColumn(name = "CustomerID")
    private CustomerEntity customer;

    @OneToOne
    @JoinColumn(name = "ProjectID")
    private ProjectEntity project;

    private int rating;
    private String feedback;

    // Thêm trường để kiểm tra nếu đã đánh giá
    private boolean isReviewed;

    // Lombok sẽ tự động tạo các getter và setter cho các thuộc tính này

}