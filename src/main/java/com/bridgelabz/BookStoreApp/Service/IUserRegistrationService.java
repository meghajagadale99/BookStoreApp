package com.bridgelabz.BookStoreApp.Service;

import com.bridgelabz.BookStoreApp.DTO.ResponseDTO;
import com.bridgelabz.BookStoreApp.DTO.UserDTO;
import com.bridgelabz.BookStoreApp.DTO.UserLoginDTO;
import com.bridgelabz.BookStoreApp.Model.UserData;

import java.util.List;


public interface IUserRegistrationService {

    UserData findUserById(long id);

    List<UserData> findAllUsers();

    UserData registerUserInBookStore(UserDTO userDTO);

    ResponseDTO loginUser(UserLoginDTO userLoginDTO);

    ResponseDTO verifyEmailUsingOtp(Long otp);

    UserData updateUserbyId(String auth, Long id, UserDTO userDTO);

    String deleteUserById(String auth, long id);

    String resetPasswordLink(String email);

    String resetPassword(String enterPassword, String confirmPassword, String urlToken);
}