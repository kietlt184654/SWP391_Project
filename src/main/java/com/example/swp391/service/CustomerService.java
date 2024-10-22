package com.example.swp391.service;

import com.example.swp391.repository.CustomerRepository;

public class CustomerService {
    private CustomerRepository customerRepository;
    public long countUsers() {
        return customerRepository.count();
    }
}
