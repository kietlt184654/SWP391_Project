package com.example.swp391.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Data
@NoArgsConstructor
public class CartEntity {

    private Long cartId; // Có thể giữ cartId cho nhận diện, nhưng sẽ không được lưu vào DB
    private Map<DesignEntity, Integer> designItems = new HashMap<>(); // Lưu các thiết kế và số lượng

    // Thêm thiết kế vào giỏ hàng
    public void addDesign(DesignEntity design) {
        designItems.put(design, designItems.getOrDefault(design, 0) + 1); // Tăng số lượng hoặc thêm mới
    }

    // Xóa thiết kế khỏi giỏ hàng
    public void removeDesign(DesignEntity design) {
        designItems.remove(design);
    }

    // Lấy danh sách thiết kế
    public Map<DesignEntity, Integer> getDesignItems() {
        return new HashMap<>(designItems); // Trả về bản sao để bảo vệ dữ liệu
    }

    // Xóa tất cả thiết kế trong giỏ hàng
    public void clear() {
        designItems.clear();
    }

    // Tính tổng số lượng các thiết kế trong giỏ hàng
    public int getTotalQuantity() {
        return designItems.values().stream().mapToInt(Integer::intValue).sum();
    }
}
