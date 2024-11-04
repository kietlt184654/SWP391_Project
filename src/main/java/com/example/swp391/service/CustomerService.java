package com.example.swp391.service;

import com.example.swp391.entity.CustomerEntity;
import com.example.swp391.repository.CustomerRepository;
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

    public CustomerEntity save(CustomerEntity customerEntity) {
        customerEntity.setCustomerID(customerRepository.findTopByOrderByCustomerIDDesc().getCustomerID()+1);
        return customerRepository.save(customerEntity);
    }
    public boolean deleteCustomerById(int id) {
        if (customerRepository.existsById(id)) {
            customerRepository.deleteById(id); // Xóa khách hàng nếu tồn tại
            return true;
        } else {
            return false; // Trả về false nếu khách hàng không tồn tại
        }
    }
}
