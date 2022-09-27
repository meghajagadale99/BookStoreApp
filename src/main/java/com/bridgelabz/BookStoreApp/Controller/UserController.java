package com.bridgelabz.BookStoreApp.Controller;

import com.bridgelabz.BookStoreApp.DTO.ResponseDTO;
import com.bridgelabz.BookStoreApp.DTO.UserDTO;
import com.bridgelabz.BookStoreApp.DTO.UserLoginDTO;
import com.bridgelabz.BookStoreApp.Model.UserData;
import com.bridgelabz.BookStoreApp.Service.EmailSenderService;
import com.bridgelabz.BookStoreApp.Service.IUserRegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping("/bookstoreApi")
public class UserController {


    @Autowired
    private EmailSenderService senderService;

    @Autowired
    private IUserRegistrationService userRegistrationService;


    @GetMapping({"/welcome", "/", ""})
    public String welcomeApi() {
        return "Welcome To BookStore App";
    }


    @GetMapping("/get/{id}")
    public ResponseEntity<ResponseDTO> getUserByID(@PathVariable(value = "id") long id) {
        UserData user = userRegistrationService.findUserById(id);
        ResponseDTO responseDTO = new ResponseDTO("The user found successfully", user);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @GetMapping("/get")
    public ResponseEntity<ResponseDTO> getUsers() {
        List<UserData> userDataList = userRegistrationService.findAllUsers();
        ResponseDTO responseDTO = new ResponseDTO("The List of users found", userDataList);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<ResponseDTO> register(@Valid @RequestBody UserDTO userDTO) {
        UserData userData = userRegistrationService.registerUserInBookStore(userDTO);
        ResponseDTO responseDTO = new ResponseDTO("User created successfully", userData);
        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }

    @GetMapping("/verify/{otp}")
    public ResponseEntity<ResponseDTO> verify(@PathVariable Long otp) {
        ResponseDTO responseDTO = userRegistrationService.verifyEmailUsingOtp(otp);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<ResponseDTO> login(@RequestBody UserLoginDTO userLoginDTO,
                                             HttpServletResponse httpServletResponse) {
        ResponseDTO responseDTO = userRegistrationService.loginUser(userLoginDTO);
        String token = (String) responseDTO.getData();
        httpServletResponse.setHeader("Authorization", token);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);

    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ResponseDTO> update(@RequestHeader(value = "Authorization") String auth,
                                              @PathVariable Long id, @Valid @RequestBody UserDTO userDTO) {
        UserData userData = userRegistrationService.updateUserbyId(auth, id, userDTO);
        ResponseDTO responseDTO = new ResponseDTO("Updated successfully", userData);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ResponseDTO> delete(@RequestHeader(value = "Authorization") String auth, @PathVariable(value = "id") long id) {
        String email = userRegistrationService.deleteUserById(auth, id);
        ResponseDTO responseDTO = new ResponseDTO("The data with " + id + " is deleted and the deleted is ", email);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @PostMapping("/forgot/password")
    public ResponseEntity<ResponseDTO> forgetPassword(@RequestParam String email) {
        String link = userRegistrationService.resetPasswordLink(email);
        ResponseDTO responseDTO = new ResponseDTO("link for reset password", link);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @PostMapping("/reset/password/{urlToken}")
    public ResponseEntity<ResponseDTO> resetPassword(@RequestParam String enterPassword,
                                                     @RequestParam String confirmPassword,
                                                     @PathVariable String urlToken) {
        String resetPassword = userRegistrationService.resetPassword(enterPassword, confirmPassword, urlToken);
        ResponseDTO responseDTO = new ResponseDTO("password reset successfully", resetPassword);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @GetMapping("/email/{toEmail}/{subject}/{body}")
    public ResponseEntity<ResponseDTO> sendTestEmail(@PathVariable String toEmail,
                                                     @PathVariable String subject,
                                                     @PathVariable String body) {
        senderService.sendEmail(toEmail,
                subject,
                body);
        ResponseDTO responseDTO = new ResponseDTO("Sent Email Successfully", toEmail);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @GetMapping("/emailAttached")
    public ResponseEntity<ResponseDTO> sendEmailWithAttachment() throws MessagingException {
        senderService.sendEmailWithAttachment("pjagadale1997@gmail.com",
                "This is subject",
                "Megha Jagadale find the attachment below",
                "C:\\Users\\www.abcom.in\\Desktop\\Megha\\Welcome.png");
        ResponseDTO responseDTO = new ResponseDTO("Sent Email Successfully", "toEmail");
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

}