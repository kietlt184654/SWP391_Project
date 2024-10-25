package com.example.swp391.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Cart")
public class CartEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cartId;

    @OneToMany
    @JoinColumn(name = "DesignID")
    private List<DesignEntity> designItems = new ArrayList<>(); // Lưu các sản phẩm trong giỏ hàng

    // Method to add a design only if it is not already in the cart
    public void addDesign(DesignEntity design) {
        // Check if the design is already in the cart
        if (!designItems.contains(design)) {
            this.designItems.add(design); // Add the design with a quantity of 1
        }
    }




}