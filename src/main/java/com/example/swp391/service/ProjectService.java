package com.example.swp391.service;

import com.example.swp391.entity.*;
import com.example.swp391.repository.CustomerRepository;
import com.example.swp391.repository.PaymentRepository;
import com.example.swp391.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private CustomerRepository customerRepository;

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
    public Optional<ProjectEntity> getProjectById(Integer projectId) {
        return projectRepository.findById(projectId);
    }
    @Transactional
    public void createProjectFromCart(CartEntity cart, CustomerEntity customer, String paymentStatus, String transactionId) {
        Date paymentDate = new Date(); // Current payment date
        double totalCartAmount = 0;

        for (Map.Entry<DesignEntity, Integer> entry : cart.getDesignItems().entrySet()) {
            DesignEntity design = entry.getKey();
            int quantity = entry.getValue();

            // Calculate the total project cost based on the design and quantity
            double projectCost = design.getPrice() * quantity;
            totalCartAmount += projectCost;

            // Create a new project for each design
            ProjectEntity project = new ProjectEntity();
            project.setName("Project for Design: " + design.getDesignName());
            project.setDescription("Auto-generated project for design: " + design.getDesignName());
            project.setTotalCost(projectCost);
            project.setDesign(design);
            project.setCustomer(customer);
            project.setStartDate(new Date()); // Current start date
            project.setStatus("Pending"); // Initial project status

            projectRepository.save(project); // Save ProjectEntity first to generate ProjectID

            // Create and save the payment details
            PaymentEntity payment = new PaymentEntity();
            payment.setTransactionId(transactionId); // Transaction ID
            payment.setAmount(projectCost);
            payment.setPaymentDate(paymentDate);
            payment.setPaymentStatus(paymentStatus);
            payment.setCustomer(customer);
            payment.setProject(project); // Set the project in payment after it's saved

            paymentRepository.save(payment); // Save PaymentEntity after ProjectID is set

            // Establish two-way relationship if necessary
            project.getPayments().add(payment);
        }
    }



}
