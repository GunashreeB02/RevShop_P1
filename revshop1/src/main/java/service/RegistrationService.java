package service;

import dto.BuyerDTO;
import dto.SecurityQuestionDTO;
import dto.SellerDTO;

import java.util.List;

public interface RegistrationService {

    List<SecurityQuestionDTO> getAllQuestions();

    boolean registerBuyer(BuyerDTO dto);
    boolean registerSeller(SellerDTO dto);

    String login(String email,String password);

    void setNewPassword(int userId, String currentPassword, String newPassword, String confirmPassword);

    String getQuestionByEmail(String email);

    boolean verifyAnswer(String email, String answer);

    boolean resetPassword(String email, String newPassword);
}
