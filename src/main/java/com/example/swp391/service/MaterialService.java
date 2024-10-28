//package com.example.swp391.service;
//
//import com.example.swp391.entity.MaterialEntity;
//import com.example.swp391.repository.MaterialRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.Optional;
//
//@Service
//public class MaterialService {
//
//    @Autowired
//    private MaterialRepository materialRepository;
//
//    // Lấy tất cả vật liệu
//    public List<MaterialEntity> getAllMaterials() {
//        return materialRepository.findAll();
//    }
//
//    // Tìm vật liệu theo ID
//    public Optional<MaterialEntity> findById(Long materialId) {
//        return materialRepository.findById(materialId);
//    }
//
//    // Tìm vật liệu theo tên
//    public MaterialEntity findByMaterialName(String materialName) {
//        return materialRepository.findByMaterialName(materialName);
//    }
//
//    // Giảm tồn kho của một vật liệu
//    public void reduceMaterialStock(Long materialId, int quantity) {
//        Optional<MaterialEntity> materialOpt = materialRepository.findById(materialId);
//        if (materialOpt.isPresent() && quantity > 0) {
//            MaterialEntity material = materialOpt.get();
//            if (material.getStockQuantity() >= quantity) {
//                material.setStockQuantity(material.getStockQuantity() - quantity);
//                materialRepository.save(material);
//            } else {
//                throw new IllegalArgumentException("Số lượng giảm vượt quá tồn kho hiện có");
//            }
//        }
//    }
//
//    // Tạo mới hoặc cập nhật thông tin vật liệu
//    public MaterialEntity saveMaterial(MaterialEntity material) {
//        return materialRepository.save(material);
//    }
//
//    // Xóa một vật liệu
//    public void deleteMaterial(Long materialId) {
//        if (materialRepository.existsById(materialId)) {
//            materialRepository.deleteById(materialId);
//        }
//    }
//}
