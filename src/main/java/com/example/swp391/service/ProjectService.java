package com.example.swp391.service;

import com.example.swp391.entity.*;
import com.example.swp391.repository.CustomerRepository;
import com.example.swp391.repository.PaymentRepository;
import com.example.swp391.repository.PointRepository;
import com.example.swp391.repository.ProjectRepository;
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

//    @Autowired
//    private StaffRepository staffRepository;
//
//    @Autowired
//    private StaffProjectRepository staffProjectRepository;

    // Hàm để lấy danh sách tất cả các dự án
    public List<ProjectEntity> findAll() {
        return projectRepository.findAll();
    }

//    @Autowired
//    private StaffRepository staffRepository;
//    @Autowired
//    private StaffProjectRepository staffProjectRepository;



//    // Thêm staff vào dự án
//    public void addStaffToProject(Integer staffID, Integer projectID) {
//        // Tìm kiếm staff và project
//        StaffEntity staff = staffRepository.findById(staffID).orElseThrow(() -> new IllegalArgumentException("Invalid staff ID"));
//        ProjectEntity project = projectRepository.findById(projectID).orElseThrow(() -> new IllegalArgumentException("Invalid project ID"));
//
//        // Tạo đối tượng StaffProjectEntity để lưu vào bảng trung gian
//        StaffProjectId staffProjectId = new StaffProjectId(staffID, projectID);
//        StaffProjectEntity staffProject = new StaffProjectEntity(staffProjectId, staff, project, new Date(), null);
//        staffProjectRepository.save(staffProject);
//    }
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

            // Tính điểm tích lũy dựa trên chi phí gốc của dự án (không giảm giá)
            int pointsEarned = (int) (originalProjectCost / 100);
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

            int pointsEarned = (int) (originalProjectCost / 100);
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




}
