package com.example.swp391.DTO;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Data // Lombok tự động tạo Getters, Setters, toString, equals, và hashCode
@NoArgsConstructor // Lombok tạo constructor không tham số
@AllArgsConstructor // Lombok tạo constructor với tất cả các tham số
public class DesignRequestDTO {

    @NotNull(message = "Customer ID cannot be null")
    private int customerId;

    @NotBlank(message = "Design name cannot be empty")
    @Size(max = 100, message = "Design name must be less than 100 characters")
    private String designName;

    @NotBlank(message = "Description cannot be empty")
    @Size(max = 500, message = "Description must be less than 500 characters")
    private String description;

    @NotNull(message = "Price cannot be null")
    private Double price;

    @NotNull(message = "Type Design ID cannot be null")
    private Long typeDesignId;
}

