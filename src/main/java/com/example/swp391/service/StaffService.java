package com.example.swp391.service;

import com.example.swp391.entity.StaffEntity;
import com.example.swp391.repository.StaffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StaffService {
    @Autowired
    private StaffRepository staffRepository;

    // Phương thức lấy nhân viên theo ID
    public List<StaffEntity> getAllStaff() {
        return staffRepository.findAll();
    }
    public StaffEntity getStaffById(Integer staffID) {
        return staffRepository.findById(staffID).orElseThrow(() -> new IllegalArgumentException("Invalid staff ID"));
    }

    // Lấy tất cả nhân viên có vai trò là 'Staff'
    public List<StaffEntity> getAllStaffWithRole() {
        return staffRepository.findByRole("Construction Staff");
    }
}
