package com.example.swp391.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class UserEntity {
    @Id
    private int userID;
    private String password;
    private String userName;
    private String userEmail;
    private String userPhone;
    private String userAddress;
    private String userImages;
    private String userStatus;
    private String roleID;

    public UserEntity() {
    }

    public UserEntity(String password, int userID, String userName, String userEmail, String userPhone, String userAddress, String userImages, String userStatus, String roleID) {
        this.password = password;
        this.userID = userID;
        this.userName = userName;
        this.userEmail = userEmail;
        this.userPhone = userPhone;
        this.userAddress = userAddress;
        this.userImages = userImages;
        this.userStatus = userStatus;
        this.roleID = roleID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }

    public String getUserImages() {
        return userImages;
    }

    public void setUserImages(String userImages) {
        this.userImages = userImages;
    }

    public String getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(String userStatus) {
        this.userStatus = userStatus;
    }

    public String getRoleID() {
        return roleID;
    }

    public void setRoleID(String roleID) {
        this.roleID = roleID;
    }
}
