package com.example.swp391.service;

import com.example.swp391.DTO.DesignRequestDTO;
import com.example.swp391.entity.CustomerEntity;
import com.example.swp391.entity.DesignEntity;
import com.example.swp391.entity.TypeDesignEntity;
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

    /**
     * Lấy tất cả các thiết kế hiện có trong cơ sở dữ liệu.
     *
     * @return Danh sách các thiết kế.
     */
    public List<DesignEntity> getAllDesigns() {
        return designRepository.findAll(); // Sử dụng phương thức mặc định findAll() từ JpaRepository
    }

    /**
     * Tìm kiếm thiết kế dựa trên tên.
     *
     * @param name Tên thiết kế để tìm kiếm.
     * @return Danh sách các thiết kế phù hợp.
     */
    public List<DesignEntity> findDesignByName(String name) {
        return designRepository.findByDesignName(name); // Gọi repository để tìm thiết kế
    }

    /**
     * Tìm kiếm các thiết kế có sẵn (stereotype designs) dựa trên tên.
     *
     * @param designName Tên thiết kế cần tìm kiếm.
     * @return Danh sách các thiết kế có sẵn.
     */
    public List<DesignEntity> searchAvailableDesignsByName(String designName) {
        Long availableTypeDesignId = 1L; // Giả định rằng 1 là ID của "mẫu có sẵn" (stereotype design)
        return designRepository.findByDesignNameContainingIgnoreCaseAndTypeDesign_TypeDesignId(designName, availableTypeDesignId);
    }
    public Optional<DesignEntity> getDesignById(Long id) {
        return designRepository.findById(id);  // Sử dụng phương thức findById() của JpaRepository
    }

    /**
     * Tìm thiết kế theo ID.
     *
     * @param id ID của thiết kế.
     * @return DesignEntity tìm thấy hoặc ngoại lệ nếu không tồn tại.
     */
    public Optional<DesignEntity> getDesignById(Long id) {
        return designRepository.findById(id);
    }

    /**
     * Tạo yêu cầu thiết kế mới cho khách hàng.
     *
     * @param designRequestDTO Đối tượng chứa thông tin của yêu cầu thiết kế.
     * @return DesignEntity đối tượng thiết kế được tạo và lưu vào database.
     */
    public DesignEntity createDesignRequest(DesignRequestDTO designRequestDTO) {
        // Tìm khách hàng dựa trên ID từ DTO
        CustomerEntity customer = customerRepository.findByCustomerId(designRequestDTO.getCustomerId())
                .orElseThrow(() -> new IllegalArgumentException("Customer not found with ID: " + designRequestDTO.getCustomerId()));

        // Tìm TypeDesign dựa trên ID từ DTO
        TypeDesignEntity typeDesign = typeDesignRepository.findById(designRequestDTO.getTypeDesignId())
                .orElseThrow(() -> new IllegalArgumentException("TypeDesign not found with ID: " + designRequestDTO.getTypeDesignId()));

        // Tạo một đối tượng Design mới và gán các thuộc tính từ DTO
        DesignEntity design = new DesignEntity();
        design.setDesignName(designRequestDTO.getDesignName());
        design.setDescription(designRequestDTO.getDescription());
//        design.setTypeDesign(typeDesign);  // Gắn kiểu thiết kế vào design
//        design.setCustomer(customer);  // Gắn thông tin khách hàng vào thiết kế

        // Lưu thiết kế vào cơ sở dữ liệu
        return designRepository.save(design);
    }
}
