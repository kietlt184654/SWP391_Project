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
    @JoinColumn(name = "DesignTemplateID")
    private List<DesignEntity> designItems = new ArrayList<>(); // Lưu các sản phẩm trong giỏ hàng

    // Constructor, getter và setter
    public void addDesign(DesignEntity design) {
        this.designItems.add(design);
    }

    public List<DesignEntity> getDesignItems() {
        return designItems;
    }

    public void setDesignItems(List<DesignEntity> designItems) {
        this.designItems = designItems;
    }
}
