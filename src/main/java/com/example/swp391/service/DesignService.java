package com.example.swp391.service;

import com.example.swp391.dto.ServiceRequest;
import com.example.swp391.entity.DesignEntity;
import com.example.swp391.entity.DesignMaterialQuantity;
import com.example.swp391.entity.MaterialEntity;
import com.example.swp391.entity.ProjectEntity;
import com.example.swp391.repository.DesignRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class DesignService {
    @Autowired
    private DesignRepository designRepository;
    @Autowired
    private DesignMaterialQuantityService designMaterialQuantityService;

    public List<DesignEntity> getDesignsByTypeId3Or4() {
        return designRepository.findDesignsByTypeId3Or4();
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
     *
     * @return Danh sách các DesignEntity có status là Available
     */
    public List<DesignEntity> getAllAvailableDesigns() {
        return designRepository.findByStatus(DesignEntity.Status.Available);
    }

    /**
     * Lấy các thiết kế dựa trên danh sách ID
     *
     * @param designIds Danh sách các ID của thiết kế
     * @return Danh sách các DesignEntity tương ứng với các ID đã cho
     */
    public List<DesignEntity> getDesignsByIds(List<Long> designIds) {
        return designRepository.findAllById(designIds);
    }

    public DesignEntity findById(Long id) {
        Optional<DesignEntity> design = designRepository.findById(id);
        return design.orElse(null);
    }

    public void save(DesignEntity design) {
        designRepository.save(design);
    }

    public void deleteById(Long id) {
        designRepository.deleteById(id);
    }

    // Phương thức để tìm các thiết kế có trạng thái và loại thiết kế nhất định
    public List<DesignEntity> findByStatusAndTypeDesignId(DesignEntity.Status status, long typeDesignId) {
        return designRepository.findByStatusAndTypeDesign_TypeDesignId(status, typeDesignId);
    }

    public List<DesignEntity> findDesignsByCustomerReference(Long customerId) {
        return designRepository.findByCustomerReference(customerId);
    }



    // Phương thức tìm thiết kế theo trạng thái
    public List<DesignEntity> findByStatus(DesignEntity.Status status) {
        return designRepository.findByStatus(status);
    }

    public void addMaterialToDesign(DesignMaterialQuantity designMaterialQuantity) {
        designMaterialQuantityService.save(designMaterialQuantity);
    }
    public List<DesignEntity> findNeedToPaymentDesignsByCustomerReference(Long customerReference) {
        return designRepository.findByCustomerReferenceAndStatus(customerReference, DesignEntity.Status.NeedToPayment);
    }
//    public Map<Long, Long> getDesignCountsByTypeId() {
//        List<Object[]> results = designRepository.countDesignsByTypeId();
//        System.out.println("Results from repository: " + results); // Kiểm tra dữ liệu từ repository
//
//        Map<Long, Long> designCounts = new HashMap<>();
//        for (Object[] result : results) {
//            Long typeDesignId = ((Number) result[0]).longValue();
//            Long count = ((Number) result[1]).longValue();
//            designCounts.put(typeDesignId, count);
//        }
//
//        System.out.println("Design Counts By Type ID: " + designCounts); // Kiểm tra kết quả cuối cùng
//        return designCounts;
//    }
    public Map<String, Long> getDesignCountsByTypeName() {
        List<Object[]> results = designRepository.countDesignsByTypeIdWithNames();
        Map<String, Long> designCounts = new HashMap<>();

        for (Object[] result : results) {
            String typeDesignName = (String) result[1];
            Long count = ((Number) result[2]).longValue();
            designCounts.put(typeDesignName, count);
        }

        return designCounts;
    }

}

