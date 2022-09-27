package com.bridgelabz.BookStoreApp.Model;

import com.bridgelabz.BookStoreApp.DTO.UserDTO;
import lombok.Data;
import lombok.ToString;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Component
@ToString
@Entity
@Table(name = "user_registration")
public class UserData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    private String firstName;
    private String lastName;
    private String kyc;
    private LocalDate dob;
    private String email;
    private String password;

    private String role;
    private LocalDate createdDate;
    private Boolean isVerified = false;


    public UserData(Long id, String firstName, String lastName, String kyc, LocalDate dob, String email, String password, String role, LocalDate createdDate, Boolean isVerified) {
        this.userId = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.kyc = kyc;
        this.dob = dob;
        this.email = email;
        this.password = password;
        this.role=role;
        this.createdDate = createdDate;
        this.isVerified = isVerified;
    }

    public UserData(UserDTO userDTO) {
        this.updateUserData(userDTO);
    }

    public UserData() {
    }

    public void updateUserData(UserDTO userDTO) {
        this.firstName = userDTO.firstName;
        this.lastName = userDTO.lastName;
        this.kyc = userDTO.kyc;
        this.dob = userDTO.dob;
        this.email = userDTO.email;
        this.password = userDTO.password;
        this.role = userDTO.role;
    }
}