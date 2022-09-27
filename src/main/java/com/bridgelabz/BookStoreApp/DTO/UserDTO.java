package com.bridgelabz.BookStoreApp.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;

@Data
public class UserDTO {
    @Pattern(regexp = "^[A-Z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$", message = "please enter valid first name")
    @NotBlank(message = "First name cannot be blank")
    public String firstName;
    @Pattern(regexp = "^[A-Z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$", message = "please enter valid last name")
    @NotBlank(message = "Last name cannot be blank")
    public String lastName;

    @NotBlank(message = "kyc should not be blank")
    public String kyc;


    @JsonFormat(pattern = "yyyy-MM-dd")
    public LocalDate dob;

    @Pattern(regexp = "^[A-Za-z0-9!#$%&'*+/=?^_`{|}~-]+(?:.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?",
            message = "please do enter the valid email id")
    @NotBlank(message = "Email cannot be blank")
    public String email;

    @Pattern(regexp = "^.*(?=.{8,})(?=..*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=]).*$",
            message = "please enter correct password")
    @NotBlank(message = "Please Do  Enter Password!")
    public String password;

    @NotBlank
    public String role;



    public UserDTO(String firstName, String lastName, String kyc, LocalDate dob, String email, String password, String role) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.kyc = kyc;
        this.dob = dob;
        this.email = email;
        this.password = password;
        this.role = role;
    }
}
