package com.example.swp391.service;

import com.example.swp391.entity.*;
import com.example.swp391.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.*;

@Service
public class ProjectService {


    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private PointRepository pointRepository;
    @Autowired
    private ProjectMaterialDetailRepository projectMaterialDetailRepository;

//    @Autowired
//    private StaffRepository staffRepository;
//
//    @Autowired
//    private StaffProjectRepository staffProjectRepository;

    // Hàm để lấy danh sách tất cả các dự án
    public List<ProjectEntity> findAll() {
        return projectRepository.findAll();
    }


    public List<ProjectEntity> getAllProjectsWithFeedback() {
        return projectRepository.findAllProjectsWithFeedback();
    }
    List<ProjectEntity> getAllProjectsByCustomerId(long customerID){
        return projectRepository.findByCustomer_CustomerID(customerID);

    }
    public Optional<ProjectEntity> getProjectById(Long projectId) {
        return projectRepository.findById(projectId);
    }
    @Transactional
    public int createProjectFromCart(CartEntity cart, CustomerEntity customer, String paymentStatus, double discountRate) {
        int totalPointsEarned = 0;

        for (Map.Entry<DesignEntity, Integer> entry : cart.getDesignItems().entrySet()) {
            DesignEntity design = entry.getKey();
            int quantity = entry.getValue();

            // Tính tổng chi phí gốc của dự án
            double originalProjectCost = design.getPrice() * quantity;
            // Tính chi phí dự án sau khi áp dụng giảm giá
            double projectCost = originalProjectCost * (1 - discountRate);

            // Tạo ProjectEntity và lưu vào cơ sở dữ liệu
            ProjectEntity project = new ProjectEntity();
            project.setName(design.getDesignName());
            project.setDescription(design.getDescription());
            project.setImg(design.getImg());
            project.setTotalCost(projectCost); // Lưu chi phí sau giảm giá
            project.setDesign(design);
            project.setCustomer(customer);
            project.setStartDate(new Date());
            project.setStatus("Pending");

            projectRepository.save(project);
            List<DesignMaterialQuantity> designMaterials = design.getDesignMaterialQuantities();
            for (DesignMaterialQuantity designMaterial : designMaterials) {
                ProjectMaterialDetailEntity projectMaterialDetail = new ProjectMaterialDetailEntity();
                projectMaterialDetail.setProject(project);
                projectMaterialDetail.setMaterial(designMaterial.getMaterial());
                projectMaterialDetail.setQuantityUsed(designMaterial.getQuantityNeeded() * quantity); // Nhân số lượng theo số lượng trong giỏ hàng
                projectMaterialDetail.setUsedDate(new Date()); // Có thể thay đổi nếu cần thiết

                projectMaterialDetailRepository.save(projectMaterialDetail);
            }

            // Tính điểm tích lũy dựa trên chi phí gốc của dự án (không giảm giá)
            int pointsEarned = (int) (originalProjectCost / 1000000);
            totalPointsEarned += pointsEarned;

            // Tạo PointEntity và lưu vào cơ sở dữ liệu
            PointEntity point = new PointEntity();
            point.setCustomer(customer);
            point.setProject(project);
            point.setPoints(pointsEarned);

            pointRepository.save(point);

            // Tạo PaymentEntity và lưu vào cơ sở dữ liệu
            PaymentEntity payment = new PaymentEntity();
            payment.setTransactionId(UUID.randomUUID().toString());
            payment.setAmount(projectCost); // Gán chi phí sau giảm giá cho PaymentEntity
            payment.setPaymentDate(new Date()); // Đảm bảo thiết lập ngày thanh toán
            payment.setPaymentStatus(paymentStatus);
            payment.setCustomer(customer);
            payment.setProject(project);

            paymentRepository.save(payment);
        }

        // Trả về tổng điểm tích lũy để sử dụng trong các bước tiếp theo
        return totalPointsEarned;
    }


    @Transactional
    public int createProjectFromService(Map<DesignEntity, Integer> designItems, CustomerEntity customer, String paymentStatus, double discountRate) {
        int totalPointsEarned = 0;

        for (Map.Entry<DesignEntity, Integer> entry : designItems.entrySet()) {
            DesignEntity design = entry.getKey();
            int quantity = entry.getValue();

            double originalProjectCost = design.getPrice() * quantity;
            double projectCost = originalProjectCost * (1 - discountRate);

            ProjectEntity project = new ProjectEntity();
            project.setName(design.getDesignName());
            project.setDescription(design.getDescription());
            project.setImg(design.getImg());
            project.setTotalCost(projectCost);
            project.setDesign(design);
            project.setCustomer(customer);
            project.setStartDate(new Date());
            project.setStatus("Pending");

            projectRepository.save(project);
            List<DesignMaterialQuantity> designMaterials = design.getDesignMaterialQuantities();
            for (DesignMaterialQuantity designMaterial : designMaterials) {
                ProjectMaterialDetailEntity projectMaterialDetail = new ProjectMaterialDetailEntity();
                projectMaterialDetail.setProject(project);
                projectMaterialDetail.setMaterial(designMaterial.getMaterial());
                projectMaterialDetail.setQuantityUsed(designMaterial.getQuantityNeeded() * quantity); // Nhân số lượng theo số lượng trong giỏ hàng
                projectMaterialDetail.setUsedDate(new Date()); // Có thể thay đổi nếu cần thiết

                projectMaterialDetailRepository.save(projectMaterialDetail);
            }

            int pointsEarned = (int) (originalProjectCost / 1000000);
            totalPointsEarned += pointsEarned;

            PointEntity point = new PointEntity();
            point.setCustomer(customer);
            point.setProject(project);
            point.setPoints(pointsEarned);

            pointRepository.save(point);

            PaymentEntity payment = new PaymentEntity();
            payment.setTransactionId(UUID.randomUUID().toString());
            payment.setAmount(projectCost);
            payment.setPaymentDate(new Date());
            payment.setPaymentStatus(paymentStatus);
            payment.setCustomer(customer);
            payment.setProject(project);

            paymentRepository.save(payment);
        }

        return totalPointsEarned;
    }

    public List<ProjectEntity> findAllByProjectIdOrderByProjectIdDesc;
    public ProjectEntity save(ProjectEntity project) {
        return projectRepository.save(project);
    }
    public ProjectEntity findById(Long projectId) {
        Optional<ProjectEntity> projectOptional = projectRepository.findById(projectId);
        return projectOptional.orElse(null);
    }

    // Lấy danh sách dự án của khách hàng, bao gồm cả chi tiết nguyên vật liệu
    public List<ProjectEntity> getProjectsByCustomerId(Long customerId) {
        return projectRepository.findByCustomer_CustomerID(customerId);
    }

    public List<ProjectEntity> getAllProjectsByCustomerId(Long customerId) {
        return projectRepository.findByCustomerCustomerID(customerId);
    }
    public List<ProjectEntity> getIncompleteProjectsByCustomerId(Long customerId) {
        return projectRepository.findByCustomer_CustomerIDAndStatusNot(customerId, "COMPLETE");
    }
    @Transactional
    public void updateStatus(Long projectID, String status) {
        // Retrieve the project entity by ID
        ProjectEntity project = projectRepository.findById(projectID)
                .orElseThrow(() -> new IllegalArgumentException("Project not found with ID: " + projectID));

        // Update the status
        project.setStatus(status);

        // Save the updated project entity
        projectRepository.save(project);
    }

    @Transactional
    public void updateProjectStatus(Long projectId, String status) {
        ProjectEntity project = projectRepository.findById(projectId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid project ID"));

        project.setStatus(status);
        projectRepository.save(project);
    }
    public void cancelProject(Long projectId) {
        ProjectEntity project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found"));
        project.setStatus("Canceled"); // Cập nhật trạng thái thành "Canceled"
        projectRepository.save(project); // Lưu thay đổi vào database
    }
    public void deleteProject(Long projectId) {
        ProjectEntity project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found"));
        if (!"Canceled".equalsIgnoreCase(project.getStatus())) {
            throw new RuntimeException("Only canceled projects can be deleted.");
        }
        projectRepository.delete(project); // Delete the project from the database
    }
    public List<ProjectEntity> getCompletedProjectsByCustomerId(Long customerId) {
        return projectRepository.findByCustomer_CustomerIDAndStatus(customerId, "COMPLETE");
    }
//    public List<ProjectEntity> getIncompleteProjectsByCustomerId(Long customerId) {
//        return projectRepository.findByCustomer_CustomerIDAndStatusNot(customerId, "COMPLETE");
//    }
}