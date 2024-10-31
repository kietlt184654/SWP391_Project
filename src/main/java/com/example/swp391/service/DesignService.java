package com.example.swp391.service;

import com.example.swp391.dto.ServiceRequest;
import com.example.swp391.entity.DesignEntity;
import com.example.swp391.repository.DesignRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DesignService {
    @Autowired
    private DesignRepository designRepository;

    public List<DesignEntity> getAllProducts() {
        return designRepository.findAll(); // Lấy tất cả các sản phẩm từ database
    }

    public DesignEntity findDesignById(Long id) {
        return designRepository.findById(id).orElse(null);
    }
    public Optional<DesignEntity> getProductById(Long id) {
        return designRepository.findById(id);
    }

    public DesignEntity saveProduct(DesignEntity product) {
        return designRepository.save(product);
    }
    public List<DesignEntity> getDesignsByTypeId() {
        return designRepository.findByTypeDesign_TypeDesignId(1L); // Sử dụng 1L cho giá trị typeDesignId = 1
    }
    public List<DesignEntity> getMaintenanceDesigns() {
        return designRepository.findByTypeDesign_TypeDesignId(3L);
    }

    public List<DesignEntity> getCleaningDesigns() {
        return designRepository.findByTypeDesign_TypeDesignId(4L);
    }
    // Tìm thiết kế bảo dưỡng phù hợp
    public List<DesignEntity> findSuitableMaintenanceDesigns(ServiceRequest serviceRequest) {
        return designRepository.findByTypeDesign_TypeDesignIdAndSizeAndPriceLessThanEqualAndEstimatedCompletionTimeLessThanEqual(
                3L, serviceRequest.getSize(), serviceRequest.getBudget(), serviceRequest.getEstimatedCompletionTime());
    }

    // Tìm thiết kế vệ sinh phù hợp
    public List<DesignEntity> findSuitableCleaningDesigns(ServiceRequest serviceRequest) {
        return designRepository.findByTypeDesign_TypeDesignIdAndSizeAndPriceLessThanEqualAndEstimatedCompletionTimeLessThanEqual(
                4L, serviceRequest.getSize(), serviceRequest.getBudget(), serviceRequest.getEstimatedCompletionTime());
    }
        /**
         * Lấy tất cả các thiết kế hiện có (status = Available)
         * @return Danh sách các DesignEntity có status là Available
         */
        public List<DesignEntity> getAllAvailableDesigns() {
            return designRepository.findByStatus(DesignEntity.Status.Available);
        }

        /**
         * Lấy các thiết kế dựa trên danh sách ID
         * @param designIds Danh sách các ID của thiết kế
         * @return Danh sách các DesignEntity tương ứng với các ID đã cho
         */
        public List<DesignEntity> getDesignsByIds(List<Long> designIds) {
            return designRepository.findAllById(designIds);
        }
    }

