package com.example.swp391.service;

import com.example.swp391.entity.CartEntity;
import com.example.swp391.entity.DesignEntity;
import com.example.swp391.entity.MaterialChangeLogEntity;
import com.example.swp391.entity.MaterialEntity;

import com.example.swp391.repository.MaterialChangeLogRepository;
import com.example.swp391.repository.MaterialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class MaterialService {

    @Autowired
    private MaterialRepository materialRepository;

    @Autowired
    private MaterialChangeLogRepository materialChangeLogRepository;
    /**
     * Lấy tất cả các nguyên vật liệu còn lại trong kho
     */
    public List<MaterialEntity> getAllMaterials() {
        return materialRepository.findAll();
    }

    /**
     * Lấy nguyên vật liệu theo ID
     */
    public MaterialEntity getMaterialById(Long materialId) {
        return materialRepository.findById(materialId)
                .orElseThrow(() -> new IllegalArgumentException("Material not found"));
    }

    /**
     * Xóa nguyên vật liệu khỏi kho
     */
    public void deleteMaterialById(Long materialId) {
        if (!materialRepository.existsById(materialId)) {
            throw new IllegalArgumentException("Material not found");
        }
        materialRepository.deleteById(materialId);
    }


    /**
     * Kiểm tra xem có đủ nguyên vật liệu cho giỏ hàng không
     */
    public boolean checkMaterialsForCart(CartEntity cart) {
        for (DesignEntity design : cart.getDesignItems()) {
            if (!checkMaterialsForDesign(design)) {
                return false;  // Một sản phẩm trong giỏ hàng không đủ nguyên vật liệu
            }
        }
        return true;  // Đủ nguyên vật liệu cho tất cả sản phẩm
    }

    /**
     * Kiểm tra nguyên vật liệu cho một sản phẩm
     */
    private boolean checkMaterialsForDesign(DesignEntity design) {
        for (Map.Entry<MaterialEntity, Integer> entry : design.getMaterialQuantities().entrySet()) {
            MaterialEntity material = entry.getKey();
            int requiredQuantity = entry.getValue();

            // Kiểm tra xem có đủ nguyên vật liệu không
            if (material.getStockQuantity() < requiredQuantity) {
                return false;  // Không đủ nguyên vật liệu
            }
        }
        return true;  // Đủ nguyên vật liệu
    }

    /**
     * Cập nhật số lượng nguyên vật liệu sau khi thanh toán thành công
     */
    public void updateMaterialsAfterCheckout(CartEntity cart) {
        for (DesignEntity design : cart.getDesignItems()) {
            for (Map.Entry<MaterialEntity, Integer> entry : design.getMaterialQuantities().entrySet()) {
                MaterialEntity material = entry.getKey();
                int requiredQuantity = entry.getValue();

                // Trừ nguyên vật liệu khỏi kho
                material.setStockQuantity(material.getStockQuantity() - requiredQuantity);
                materialRepository.save(material);  // Cập nhật nguyên vật liệu trong kho
            }
        }
    }

    /**
     * Cập nhật số lượng nguyên vật liệu theo kiểu thêm/bớt và lưu lý do thay đổi
     * @param materialId ID của nguyên vật liệu cần thay đổi
     * @param quantityChange Số lượng thay đổi (có thể âm hoặc dương)
     * @param reason Lý do thay đổi số lượng
     */
    public void updateMaterialQuantity(Long materialId, int quantityChange, String reason) {
        MaterialEntity material = materialRepository.findById(materialId)
                .orElseThrow(() -> new IllegalArgumentException("Material not found"));

        int oldQuantity = material.getStockQuantity();
        int newQuantity = oldQuantity + quantityChange;  // Thêm hoặc trừ số lượng

        if (newQuantity < 0) {
            throw new IllegalArgumentException("Số lượng nguyên vật liệu không đủ!");
        }

        // Cập nhật số lượng mới
        material.setStockQuantity(newQuantity);
        materialRepository.save(material);

        // Lưu lại log thay đổi số lượng và lý do thay đổi
        logMaterialUpdate(material, quantityChange, reason);
    }

    /**
     * Lưu log thay đổi số lượng nguyên vật liệu vào cơ sở dữ liệu
     */
    private void logMaterialUpdate(MaterialEntity material, int quantityChange, String reason) {
        MaterialChangeLogEntity log = new MaterialChangeLogEntity();
        log.setMaterial(material);
        log.setQuantityChanged(quantityChange);  // Số lượng thay đổi (thêm hoặc trừ)
        log.setReason(reason);
        log.setChangeDate(new java.util.Date());

        materialChangeLogRepository.save(log);
    }
}
