package com.example.swp391.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class AccountRegistrationDTO {

    @NotBlank(message = "Tên tài khoản không được để trống")
    @Size(max = 100, message = "Tên tài khoản không được vượt quá 100 ký tự")
    private String accountName;

    @NotBlank(message = "Mật khẩu không được để trống")
    @Size(min = 6, message = "Mật khẩu phải có ít nhất 6 ký tự")
    private String password;

    @NotBlank(message = "Email không được để trống")
    @Email(message = "Email không đúng định dạng")
    @Size(max = 100, message = "Email không được vượt quá 100 ký tự")
    private String email;

    @NotBlank(message = "Số điện thoại không được để trống")
    @Size(max = 15, message = "Số điện thoại không được vượt quá 15 ký tự")
    private String phoneNumber;

    @Size(max = 255, message = "Địa chỉ không được vượt quá 255 ký tự")
    private String address;

    @Size(max = 50, message = "Loại tài khoản không được vượt quá 50 ký tự")
    private String accountTypeID;
}
