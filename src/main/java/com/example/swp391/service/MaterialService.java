package com.example.swp391.service;

import com.example.swp391.entity.*;
import com.example.swp391.repository.MaterialChangeLogRepository;
import com.example.swp391.repository.MaterialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
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
     * Get all materials
     */
    public List<MaterialEntity> findAllMaterials() {
        return materialRepository.findAll();
    }

    /**
     * Find material by ID
     */
    public MaterialEntity findById(Long materialId) {
        return materialRepository.findById(materialId)
                .orElseThrow(() -> new IllegalArgumentException("Material not found"));
    }

    // Find material by name
    public MaterialEntity findByName(String materialName) {
        return materialRepository.findByMaterialName(materialName);
    }

    /**
     * Update material quantity and log the change
     */
    public void updateMaterialQuantity(Long materialId, int quantityChange, String reason) {
        MaterialEntity material = findById(materialId);

        int newQuantity = material.getStockQuantity() + quantityChange;
        if (newQuantity < 0) {
            throw new IllegalArgumentException("Insufficient material quantity!");
        }

        // Update quantity and save the change log
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
     * Check materials for all designs in the cart
     */
    public boolean checkMaterialsForCart(CartEntity cart) {
        for (Map.Entry<DesignEntity, Integer> entry : cart.getDesignItems().entrySet()) {
            DesignEntity design = entry.getKey();
            int quantityInCart = entry.getValue();

            if (!checkMaterialsForDesign(design, quantityInCart)) {
                return false; // Not enough materials for the design
            }
        }
        return true; // Sufficient materials for all designs in the cart
    }

    /**
     * Check materials for a design with a specific quantity
     */
    private boolean checkMaterialsForDesign(DesignEntity design, int quantityInCart) {
        for (DesignMaterialQuantity designMaterial : design.getMaterialQuantities()) {
            MaterialEntity material = designMaterial.getMaterial();
            int totalRequiredQuantity = designMaterial.getQuantityNeeded() * quantityInCart;

            if (material.getStockQuantity() < totalRequiredQuantity) {
                return false;
            }
        }
        return true;
    }

    /**
     * Update material quantities in stock after successful checkout for cart
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

    /**
     * Check materials for all designs in service
     */
    public boolean checkMaterialsForDesignItems(Map<DesignEntity, Integer> designItems) {
        for (Map.Entry<DesignEntity, Integer> entry : designItems.entrySet()) {
            DesignEntity design = entry.getKey();
            int quantity = entry.getValue();

            if (!checkMaterialsForDesign(design, quantity)) {
                return false; // Not enough materials for the design in the service
            }
        }
        return true; // Sufficient materials for all designs in the service
    }

    /**
     * Update material quantities in stock after successful checkout for service
     */
    public void updateMaterialsAfterCheckoutForDesignItems(Map<DesignEntity, Integer> designItems) {
        for (Map.Entry<DesignEntity, Integer> entry : designItems.entrySet()) {
            DesignEntity design = entry.getKey();
            int quantity = entry.getValue();

            for (DesignMaterialQuantity designMaterial : design.getMaterialQuantities()) {
                MaterialEntity material = designMaterial.getMaterial();
                int totalRequiredQuantity = designMaterial.getQuantityNeeded() * quantity;

                material.setStockQuantity(material.getStockQuantity() - totalRequiredQuantity);
                materialRepository.save(material);
            }
        }
    }

    public List<MaterialChangeLogEntity> getAllMaterialChangeHistory() {
        List<MaterialChangeLogEntity> changeLogs = materialChangeLogRepository.findAll();
        changeLogs.sort(Comparator.comparing(MaterialChangeLogEntity::getChangeDate).reversed());
        return changeLogs;
    }

    public void updateMaterialQuantityForDesign(Long materialId, int quantityChange ) {
        // Check if quantityChange > 0
        if (quantityChange == 0) {
            throw new IllegalArgumentException("Quantity change must not be 0");
        }

        MaterialEntity material = materialRepository.findById(materialId)
                .orElseThrow(() -> new IllegalArgumentException("Material not found"));

        // Calculate new quantity
        int newQuantity = material.getStockQuantity() + quantityChange;

        // If new quantity < 0, throw error
        if (newQuantity < 0) {
            throw new IllegalArgumentException("Insufficient material quantity for the material: " + material.getMaterialName());
        }

        // Update stock quantity
        material.setStockQuantity(newQuantity);
        materialRepository.save(material);
    }

    // Method to save MaterialEntity
    public MaterialEntity save(MaterialEntity material) {
        return materialRepository.save(material);
    }

    // Method to find all materials
    public List<MaterialEntity> findAll() {
        return materialRepository.findAll();
    }

    public void deleteMaterialById(Long materialId) {
        materialRepository.deleteById(materialId);
    }

    public boolean existsByMaterialName(String materialName) {
        return materialRepository.existsByMaterialName(materialName);
    }
}