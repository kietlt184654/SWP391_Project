package com.example.swp391.service;

import com.example.swp391.entity.AccountEntity;
import com.example.swp391.entity.CustomerEntity;
import com.example.swp391.entity.PointEntity;
import com.example.swp391.entity.ProjectEntity;
import com.example.swp391.repository.CustomerRepository;
import com.example.swp391.repository.PointRepository;
import com.example.swp391.repository.ProjectRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class CustomerService {
    @PersistenceContext
    private EntityManager entityManager;
@Autowired
    private CustomerRepository customerRepository;
@Autowired
private PointRepository pointRepository;
@Autowired
private ProjectRepository projectRepository;

    public long countUsers() {
        return customerRepository.count();
    }
//    public CustomerEntity save(CustomerEntity customerEntity) {
//        customerEntity.setCustomerID(customerRepository.findTopByOrderByCustomerIDDesc().getCustomerID()+1);
//        return customerRepository.save(customerEntity);
//    }
    public List<Object[]> getAllCustomersWithAccountInfo() {
        return entityManager.createQuery(
                        "SELECT c.customerID, a.accountName, a.accountTypeID, a.phoneNumber, a.address, a.images " +
                                "FROM CustomerEntity c JOIN c.account a", Object[].class)
                .getResultList();
    }


    public boolean existsByAccount(AccountEntity account) {
        return customerRepository.existsByAccount(account);
    }

    public void save(CustomerEntity customer) {
        customerRepository.save(customer);
    }
    public void updateCustomerPoints(CustomerEntity customer, int pointsEarned) {
        // Tạo một đối tượng PointEntity mới để lưu điểm tích lũy thêm vào
        PointEntity newPoint = new PointEntity();
        newPoint.setCustomer(customer);
        newPoint.setPoints(pointsEarned);

        // Thêm điểm mới vào danh sách điểm tích lũy của khách hàng
        customer.getPointsHistory().add(newPoint);

        // Lưu khách hàng và điểm mới vào cơ sở dữ liệu
        customerRepository.save(customer);
        pointRepository.save(newPoint);
    }
    // Phương thức tính tổng điểm của khách hàng
    public int calculateTotalPoints(CustomerEntity customer) {
        return pointRepository.findTotalPointsByCustomer(customer.getCustomerID());
    }

    // Phương thức tính mức giảm giá dựa trên điểm tích lũy của khách hàng
    public double calculatePointDiscount(CustomerEntity customer) {
        int totalPoints = calculateTotalPoints(customer);
        if (totalPoints >= 10000) {
            return 0.15; // Giảm 15%
        } else if (totalPoints >= 5000) {
            return 0.10; // Giảm 10%
        } else if (totalPoints >= 10) {
            return 0.05; // Giảm 5%
        } else {
            return 0.0; // Không giảm giá
        }
    }
    public CustomerEntity findById(Long customerId) {
        return customerRepository.findById(customerId)
                .orElseThrow(() -> new IllegalArgumentException("Khách hàng không tồn tại với ID: " + customerId));
    }


}
