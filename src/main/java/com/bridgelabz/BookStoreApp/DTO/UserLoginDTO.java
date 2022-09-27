package com.bridgelabz.BookStoreApp.DTO;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
public class UserLoginDTO {
    @Pattern(regexp = "^[A-Za-z0-9!#$%&'*+/=?^_`{|}~-]+(?:.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?",
            message = "please do enter the valid email id")
    @NotBlank(message = "Email cannot be blank")
    private String email;

    @Pattern(regexp = "^.*(?=.{8,})(?=..*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=]).*$",
            message = "please enter correct password")
    @NotBlank(message = "Please Do  Enter Password!")
    private String password;

    @NotBlank
    private String role;


    public UserLoginDTO(String email, String password, String role) {
        this.email = email;
        this.password = password;
        this.role = role;
    }
}
