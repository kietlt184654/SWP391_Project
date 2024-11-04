package com.example.swp391.dto;

import com.example.swp391.entity.DesignEntity.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServiceRequest {
    private Size size;
    private double budget;
    private int estimatedCompletionTime;
    private String serviceType; // Loại dịch vụ
}
