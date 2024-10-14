package com.example.swp391.service;

import com.example.swp391.DTO.DesignRequestDTO;
import com.example.swp391.entity.CustomerEnity;
import com.example.swp391.entity.DesignEnity;
import com.example.swp391.entity.ProjectMaterialDetailEnity;
import com.example.swp391.entity.TypeDesignEnity;
import com.example.swp391.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DesignService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private DesignRepository designRepository;

    @Autowired
    private TypeDesignRepository typeDesignRepository;
    @Autowired
    private ProjectMaterialDetailRepository projectMaterialDetailRepository;
    @Autowired
    private MaterialRepository materialRepository;

    @Transactional
    public void processPayment(Long projectId) {
        // Lấy danh sách vật liệu đã sử dụng trong dự án
        List<ProjectMaterialDetailEnity> materialDetails = projectMaterialDetailRepository.findByProject_ProjectId(projectId);

        // Lặp qua mỗi vật liệu đã sử dụng và giảm tồn kho
        for (ProjectMaterialDetailEnity detail : materialDetails) {
            Long materialId = (long) detail.getMaterial().getMaterialId();
            // Sửa lại cách lấy materialId
            int usedQuantity = detail.getQuantity(); // Lấy số lượng vật liệu đã sử dụng

            // Cập nhật số lượng tồn kho của vật liệu
            materialRepository.reduceMaterialStock(materialId, usedQuantity);
        }
    }


    /**
     * Lấy tất cả các thiết kế hiện có trong cơ sở dữ liệu.
     *
     * @return Danh sách các thiết kế.
     */
    public List<DesignEnity> getAllDesigns() {
        return designRepository.findAll(); // Sử dụng phương thức mặc định findAll() từ JpaRepository
    }

    /**
     * Tìm kiếm thiết kế dựa trên tên.
     *
     * @param name Tên thiết kế để tìm kiếm.
     * @return Danh sách các thiết kế phù hợp.
     */
    public List<DesignEnity> findDesignByName(String name) {
        return designRepository.findByDesignName(name); // Gọi repository để tìm thiết kế
    }

    /**
     * Tìm kiếm các thiết kế có sẵn (stereotype designs) dựa trên tên.
     *
     * @param designName Tên thiết kế cần tìm kiếm.
     * @return Danh sách các thiết kế có sẵn.
     */
    public List<DesignEnity> searchAvailableDesignsByName(String designName) {
        Long availableTypeDesignId = 1L; // Giả định rằng 1 là ID của "mẫu có sẵn" (stereotype design)
        return designRepository.findByDesignNameContainingIgnoreCaseAndTypeDesign_TypeDesignId(designName, availableTypeDesignId);
    }

    /**
     * Tạo yêu cầu thiết kế mới cho khách hàng.
     *
     * @param designRequestDTO Đối tượng chứa thông tin của yêu cầu thiết kế.
     * @return DesignEntity đối tượng thiết kế được tạo và lưu vào database.
     */
    public DesignEnity createDesignRequest(DesignRequestDTO designRequestDTO) {
        // Tìm khách hàng dựa trên ID từ DTO
        Optional<CustomerEnity> customerOpt = customerRepository.findByCustomerId(designRequestDTO.getCustomerId());
        if (!customerOpt.isPresent()) {
            throw new IllegalArgumentException("Customer not found with ID: " + designRequestDTO.getCustomerId());
        }

        CustomerEnity customer = customerOpt.get();

        // Tìm TypeDesign dựa trên ID từ DTO
        Optional<TypeDesignEnity> typeDesignOpt = typeDesignRepository.findById(designRequestDTO.getTypeDesignId());
        if (!typeDesignOpt.isPresent()) {
            throw new IllegalArgumentException("TypeDesign not found with ID: " + designRequestDTO.getTypeDesignId());
        }

        TypeDesignEnity typeDesign = typeDesignOpt.get();

        // Tạo một đối tượng Design mới và gán các thuộc tính từ DTO
        DesignEnity design = new DesignEnity();
        design.setDesignName(designRequestDTO.getDesignName());
        design.setDescription(designRequestDTO.getDescription());
        design.setTypeDesign(typeDesign); // Gắn kiểu thiết kế vào design
//        design.setCustomer(customer); // Gắn thông tin khách hàng vào thiết kế (nếu cần)

        // Lưu thiết kế vào cơ sở dữ liệu
        return designRepository.save(design);
    }

}
