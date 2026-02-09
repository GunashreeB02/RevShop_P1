package repository;

import dto.BuyerDTO;
import dto.LoginDTO;
import dto.SecurityQuestionDTO;
import dto.SellerDTO;

import java.util.List;

public interface RegistrationRepository {

    List<SecurityQuestionDTO> getAllQuestions();

    boolean registerBuyer(BuyerDTO dto);
    boolean registerSeller(SellerDTO dto);

    LoginDTO login(String email);
    String getUserNameById(int userId,String role);

    String getPasswordByUserId(int userId);

    boolean updateUserPassword(int userId, String newPassword);



    String getQuestionByEmail(String email);

    boolean verifyAnswer(String email, String answer);

    boolean resetPassword(String email, String newPassword);

    boolean isEmailExists(String email);

    boolean isPhoneExistsInSeller(String phone);
    boolean isPhoneExistsInBuyer(String phone);
    boolean isGstExists(String gstNumber);



}
