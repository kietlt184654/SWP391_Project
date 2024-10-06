package com.example.swp391.service;

import com.example.swp391.DTO.DesignRequestDTO;
import com.example.swp391.entity.CustomerEnity;
import com.example.swp391.entity.DesignEnity;
import com.example.swp391.entity.TypeDesignEnity;
import com.example.swp391.repository.CustomerRepository;
import com.example.swp391.repository.DesignRepository;
import com.example.swp391.repository.TypeDesignRepository;
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
    public List<DesignEnity> getAllDesigns() {
        return designRepository.findAll(); // Sử dụng phương thức mặc định findAll() từ JpaRepository
    }
    // Phương thức tìm kiếm thiết kế theo tên
    public List<DesignEnity> findDesignByName(String name) {
        return designRepository.findByDesignName(name); // Gọi repository để tìm thiết kế
    }
    public List<DesignEnity> searchAvailableDesignsByName(String designName) {
        Long availableTypeDesignId = 1L; // Giả định rằng 1 là ID của "mẫu có sẵn"
        return designRepository.findByDesignNameContainingIgnoreCaseAndTypeDesign_TypeDesignId(designName, availableTypeDesignId);
    }
    /**
     * Phương thức tạo yêu cầu thiết kế mới cho khách hàng.
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

        // Lưu thiết kế vào cơ sở dữ liệu
        return designRepository.save(design);
    }
}
