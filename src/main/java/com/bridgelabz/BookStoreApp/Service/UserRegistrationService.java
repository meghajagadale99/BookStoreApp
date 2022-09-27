package com.bridgelabz.BookStoreApp.Service;

import com.bridgelabz.BookStoreApp.DTO.ResponseDTO;
import com.bridgelabz.BookStoreApp.DTO.UserDTO;
import com.bridgelabz.BookStoreApp.DTO.UserLoginDTO;
import com.bridgelabz.BookStoreApp.Model.UserData;
import com.bridgelabz.BookStoreApp.Exception.UserException;
import com.bridgelabz.BookStoreApp.Repository.UserRegistrationRepository;
import com.bridgelabz.BookStoreApp.Util.OtpGenerator;
import com.bridgelabz.BookStoreApp.Util.TokenGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;


@Service
public class UserRegistrationService implements IUserRegistrationService {

    @Autowired
    UserData userData;
    @Autowired
    private UserRegistrationRepository userRegistrationRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private OtpGenerator otpGenerator;

    @Autowired
    private EmailSenderService emailSenderService;

    @Autowired
    private TokenGenerator tokenGenerator;

    Long otp;


    @Override
    public UserData findUserById(long id) {
        return userRegistrationRepository.findById(id).get();
    }

    @Override
    public List<UserData> findAllUsers() {
        return userRegistrationRepository.findAll();
    }
    @Override
    public UserData registerUserInBookStore(UserDTO userDTO) {
        UserData userDataByEmail = userRegistrationRepository.findUserDataByEmail(userDTO.getEmail());
        //check for user exists
        if (userDataByEmail == null) {
//            maps the userdto with userdata class
//            userData = modelMapper.map(userDTO, UserData.class);
            userData = new UserData(userDTO);
            userData.setCreatedDate(LocalDate.now());
            //encrypting the password using password encoder
            String epassword = bCryptPasswordEncoder.encode(userDTO.getPassword());
            userData.setPassword(epassword);
            System.out.println("password is " + epassword);
            userData = userRegistrationRepository.save(userData);
            System.out.println(userData);
            otp = generateOtpAndSendEmail(userData);
            return userData;
        } else {
            throw new UserException("User not Created because user with given email already exists",
                    UserException.ExceptionType.USER_ALREADY_PRESENT);
        }

    }

    private Long generateOtpAndSendEmail(UserData userData) {
        long generatedOtp = otpGenerator.generateOTP();
        String requestUrl = "http://localhost:8080/bookstoreApi/verify/" + generatedOtp;
        System.out.println("the generated otp is " + generatedOtp);
        try {
            emailSenderService.sendEmail(
                    userData.getEmail(),
                    "Your Registration is successful",
                    requestUrl + "\n your generated otp is "
                            + generatedOtp +
                            " click on the link above to verify the user");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return generatedOtp;
    }

    @Override
    public ResponseDTO loginUser(UserLoginDTO userLoginDTO) {
        System.out.println(userLoginDTO.getEmail());
//        userData = userRegistrationRepository.findUserDataByEmail(userLoginDTO.getEmail());
        userData = userRegistrationRepository.findUserDataByEmailId(userLoginDTO.getEmail())
                .orElseThrow(()-> new UserException("Enter registered Email, Email not found", UserException.ExceptionType.EMAIL_NOT_FOUND));

        if(!userData.getEmail().equals(userLoginDTO.getEmail())){
            throw new UserException("Unauthorised user",UserException.ExceptionType.USER_UNAUTHORISED);
        }
        System.out.println(userData);

//        if (userData.equals(null)) {
//             throw new UserException("Unauthorised user",UserException.ExceptionType.USER_UNAUTHORISED);
//        }

        if (userData.getIsVerified()) {
            boolean isPassword = bCryptPasswordEncoder.matches(userLoginDTO.getPassword(),
                    userData.getPassword());
            if (!isPassword) {
                throw new UserException("Invalid Password!!!Please Enter Correct Password",
                        UserException.ExceptionType.PASSWORD_INVALID);
            }
            String jwtToken = tokenGenerator.generateLoginToken(userData);
            return new ResponseDTO("Logged in successfully", jwtToken);
        }
        otp = generateOtpAndSendEmail(userData);
        throw new UserException("Please verify your email before proceeding",
                UserException.ExceptionType.EMAIL_NOT_FOUND);
    }

    public ResponseDTO verifyEmailUsingOtp(Long mailOtp) {
        if (mailOtp.equals(otp) && userData.getIsVerified().equals(false)) {
            //setting verification to true after verification
            userData.setIsVerified(true);
            //update the userData with is verified value
            userRegistrationRepository.save(userData);
            return new ResponseDTO("otp verified", userData);
        } else if (userData.getIsVerified() && mailOtp.equals(otp)) {
            throw new UserException("otp already verified no need to verify again", UserException.ExceptionType.OTP_INVALID);
        }
        throw new UserException("Invalid otp", UserException.ExceptionType.OTP_INVALID);
    }

    @Override
    public UserData updateUserbyId(String auth, Long id, UserDTO userDTO) {
        userData = userRegistrationRepository.findById(id).get();
        userData.updateUserData(userDTO);
        return userRegistrationRepository.save(userData);
    }

    @Override
    public String deleteUserById(String auth, long id) {
        userData = findUserById(id);
        userRegistrationRepository.delete(userData);
        return userData.getEmail();
    }

    @Override
    public String resetPasswordLink(String email) {
//       UserData userData = userRegistrationRepository.findUserDataByEmail(email);
        UserData userData = userRegistrationRepository.findUserDataByEmailId(email)
                .orElseThrow(()->new UserException("Email Not found",UserException.ExceptionType.EMAIL_NOT_FOUND));
        if(userData == null){
            throw new UserException("Email Not found",UserException.ExceptionType.EMAIL_NOT_FOUND);
        }
        String token = tokenGenerator.generateLoginToken(userData);
        String urlToken = "Click on below link to Reset your Password \n" +
                "http://localhost:8080/bookstoreApi/reset/password/"+token+
                "\n The generated token is :  "+token;
        emailSenderService.sendEmail(userData.getEmail(),"Reset Password",urlToken);

        return "Reset Password Link Has Been Sent To Your Email Address : "+userData.getEmail();
    }

    @Override
    public String resetPassword(String enterPassword, String confirmPassword, String urlToken) {
        String userId = tokenGenerator.decodeJWT(urlToken);
        System.out.println(userId);
        UserData userData = findUserById(Long.parseLong(userId));
        boolean isPassword = enterPassword.equals(confirmPassword);
        if(!isPassword){
            throw new UserException("Both passwords must be same ",UserException.ExceptionType.PASSWORD_INVALID);
        }
        String encodePassword = bCryptPasswordEncoder.encode(confirmPassword);
        userData.setPassword(encodePassword);
        userRegistrationRepository.save(userData);
        return "Password Has Been Reset";
    }

}