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

   public List<StaffEntity> getStaffByRole(String role) {
        return staffRepository.findByRole(role);
    }

    public List<StaffEntity> getAllStaff() {
        return staffRepository.findAll();
    }

    public StaffEntity getStaffById(Integer staffId) {
        Optional<StaffEntity> staffEntity = staffRepository.findById(staffId);
        return staffEntity.orElse(null);
    }

    public StaffEntity save(StaffEntity staffEntity) {
        return staffRepository.save(staffEntity);
    }

    public void deleteStaff(Integer staffId) {
        staffRepository.deleteById(staffId);
    }
}
