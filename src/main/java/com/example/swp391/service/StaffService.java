package com.example.swp391.service;

import com.example.swp391.entity.StaffEntity;
import com.example.swp391.repository.StaffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StaffService {
    @Autowired
    private StaffRepository staffRepository;

    // Phương thức lấy nhân viên theo ID
    public List<StaffEntity> getAllStaff() {
        return staffRepository.findAll();
    }



    // Phương thức lấy tất cả các nhân viên có role là "construction staff"
    public List<StaffEntity> getConstructionStaff() {
        return staffRepository.findByRole("Construction Staff");
    }

    public Optional<StaffEntity> getStaffById(int staffId) {
        return staffRepository.findById(staffId);
    }
}
