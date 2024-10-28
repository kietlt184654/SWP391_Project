package com.example.swp391.service;

import com.example.swp391.entity.*;
import com.example.swp391.repository.MaterialChangeLogRepository;
import com.example.swp391.repository.MaterialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class MaterialService {

    @Autowired
    private MaterialRepository materialRepository;

    @Autowired
    private MaterialChangeLogRepository materialChangeLogRepository;

    /**
     * Lấy danh sách tất cả nguyên vật liệu
     */
    public List<MaterialEntity> findAllMaterials() {
        return materialRepository.findAll();
    }

    /**
     * Tìm nguyên vật liệu theo ID
     *
     * @param materialId ID của nguyên vật liệu
     * @return Đối tượng MaterialEntity hoặc ném IllegalArgumentException nếu không tìm thấy
     */
    public MaterialEntity findById(Long materialId) {
        return materialRepository.findById(materialId)
                .orElseThrow(() -> new IllegalArgumentException("Material not found"));
    }

    /**
     * Cập nhật số lượng nguyên vật liệu và ghi log thay đổi
     *
     * @param materialId     ID của nguyên vật liệu cần cập nhật
     * @param quantityChange Số lượng thay đổi
     * @param reason         Lý do thay đổi số lượng
     */
    public void updateMaterialQuantity(Long materialId, int quantityChange, String reason) {
        MaterialEntity material = findById(materialId);

        int newQuantity = material.getStockQuantity() + quantityChange;
        if (newQuantity < 0) {
            throw new IllegalArgumentException("Số lượng nguyên vật liệu không đủ!");
        }

        // Cập nhật số lượng và lưu bản ghi log
        material.setStockQuantity(newQuantity);
        materialRepository.save(material);

        MaterialChangeLogEntity changeLog = new MaterialChangeLogEntity();
        changeLog.setMaterial(material);
        changeLog.setQuantityChanged(quantityChange);
        changeLog.setReason(reason);
        changeLog.setChangeDate(new Date());
        materialChangeLogRepository.save(changeLog);
    }
    /**
     * Kiểm tra nguyên vật liệu cho tất cả thiết kế trong giỏ hàng
     */
    public boolean checkMaterialsForCart(CartEntity cart) {
        for (Map.Entry<DesignEntity, Integer> entry : cart.getDesignItems().entrySet()) {
            DesignEntity design = entry.getKey();
            int quantityInCart = entry.getValue();

            // Gọi phương thức checkMaterialsForDesign cho từng thiết kế
            if (!checkMaterialsForDesign(design, quantityInCart)) {
                return false; // Nếu bất kỳ thiết kế nào không đủ nguyên vật liệu, trả về false
            }
        }
        return true; // Nếu tất cả thiết kế đều có đủ nguyên vật liệu, trả về true
    }

    /**
     * Kiểm tra nguyên vật liệu cho một sản phẩm với số lượng nhất định
     */
    private boolean checkMaterialsForDesign(DesignEntity design, int quantityInCart) {
        for (DesignMaterialQuantity designMaterial : design.getMaterialQuantities()) {
            MaterialEntity material = designMaterial.getMaterial();
            int totalRequiredQuantity = designMaterial.getQuantityNeeded() * quantityInCart;

            // Kiểm tra xem có đủ nguyên vật liệu không
            if (material.getStockQuantity() < totalRequiredQuantity) {
                return false;
            }
        }
        return true;
    }

    /**
     * Xử lý giảm số lượng nguyên vật liệu trong kho sau khi thanh toán thành công
     */
    public void updateMaterialsAfterCheckout(CartEntity cart) {
        for (Map.Entry<DesignEntity, Integer> entry : cart.getDesignItems().entrySet()) {
            DesignEntity design = entry.getKey();
            int quantityInCart = entry.getValue();

            for (DesignMaterialQuantity designMaterial : design.getMaterialQuantities()) {
                MaterialEntity material = designMaterial.getMaterial();
                int totalRequiredQuantity = designMaterial.getQuantityNeeded() * quantityInCart;

                material.setStockQuantity(material.getStockQuantity() - totalRequiredQuantity);
                materialRepository.save(material);
            }
        }
    }
}
