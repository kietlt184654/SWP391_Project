package com.example.swp391.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "Customer")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CustomerID")
    private long customerID;

    @OneToOne
    @JoinColumn(name = "AccountID")  // Đảm bảo rằng cột AccountID tồn tại trong DB và ánh xạ đúng
    private AccountEntity account;

    private String additionalInfo;
    // Quan hệ một-nhiều với PointEntity
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, fetch =FetchType.EAGER)
    private List<PointEntity> pointsHistory = new ArrayList<>();

    // Phương thức để tính tổng số điểm
    public int getTotalPoints() {
        return pointsHistory.stream().mapToInt(PointEntity::getPoints).sum();
    }
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<RatingFeedbackEntity> feedbacks = new ArrayList<>();
}

