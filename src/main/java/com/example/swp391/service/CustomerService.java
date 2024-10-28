package com.example.swp391.service;

import com.example.swp391.entity.AccountEntity;
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
}
