package com.practice.ordersystem.domain.Member.DTO;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
public class LoginReqDto {
    @Email(message = "email is not valid")
    private String email;
    @NotEmpty(message = "password is essential")
    @Size(min = 4, message = "minimum length is 4")
    private String password;
}
