package com.example.swp391.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Cart")
@Getter
@Setter
@NoArgsConstructor
public class CartEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cartId;

    // Một giỏ hàng có nhiều sản phẩm
    @OneToMany
    @JoinColumn(name = "DesignID")
    private List<DesignEntity> designItems = new ArrayList<>();

    // Thêm sản phẩm vào giỏ hàng (chỉ thêm nếu sản phẩm chưa tồn tại)
    public void addDesign(DesignEntity design) {
        if (!designItems.contains(design)) {
            this.designItems.add(design);
        }
    }

    // Xóa sản phẩm khỏi giỏ hàng
    public void removeDesign(DesignEntity design) {
        designItems.remove(design);
    }

    // Tính tổng giá tiền của giỏ hàng
    public double getTotalPrice() {
        return designItems.stream()
                .mapToDouble(DesignEntity::getPrice)
                .sum();
    }
}
