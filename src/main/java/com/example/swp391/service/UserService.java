package com.example.swp391.service;

import com.example.swp391.entity.UserEntity;
import com.example.swp391.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public UserEntity userLogin(String username, String password) {
UserEntity user=userRepository.findByUserName(username);
if (user.getPassword().equals(password)&&user!=null) {
    return user;
}return null;
    }
}
