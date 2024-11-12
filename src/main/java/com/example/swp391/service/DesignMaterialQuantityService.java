package com.example.swp391.service;

import com.example.swp391.entity.DesignEntity;
import com.example.swp391.entity.DesignMaterialQuantity;
import com.example.swp391.entity.MaterialEntity;
import com.example.swp391.repository.DesignMaterialQuantityRepository;
import com.example.swp391.repository.DesignRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DesignMaterialQuantityService {

    @Autowired
    private DesignMaterialQuantityRepository designMaterialQuantityRepository;
    @Autowired
    private MaterialService materialService;
    @Autowired
    private DesignRepository designRepository;
    // Phương thức lưu DesignMaterialQuantity
    public void save(DesignMaterialQuantity designMaterialQuantity) {
        designMaterialQuantityRepository.save(designMaterialQuantity);
    }
    public void addMaterialsToDesign(Long designId, List<DesignMaterialQuantity> materialQuantities) {
        DesignEntity design = designRepository.findById(designId)
                .orElseThrow(() -> new IllegalArgumentException("Design not found"));

        for (DesignMaterialQuantity materialQuantity : materialQuantities) {
            MaterialEntity material = materialQuantity.getMaterial();
            int quantityNeeded = materialQuantity.getQuantityNeeded();

            // Kiểm tra xem kho có đủ số lượng hay không
            if (material.getStockQuantity() < quantityNeeded) {
                throw new IllegalArgumentException("Insufficient material quantity for the item: " + material.getMaterialName());
            }

            // Sử dụng phương thức updateMaterialQuantity để trừ đi số lượng cần dùng
            materialService.updateMaterialQuantityForDesign(material.getMaterialId(), -quantityNeeded);

            // Thiết lập mối quan hệ giữa Design và MaterialQuantity
            materialQuantity.setDesign(design);

            // Lưu thông tin nguyên vật liệu cần thiết cho thiết kế
            designMaterialQuantityRepository.save(materialQuantity);
        }

    }
    // Phương thức lấy danh sách DesignMaterialQuantity dựa trên DesignEntity
    public List<DesignMaterialQuantity> findByDesign(DesignEntity design) {
        return designMaterialQuantityRepository.findByDesign(design);
    }
}