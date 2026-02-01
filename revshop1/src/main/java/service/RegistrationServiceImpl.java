package service;

import dto.BuyerDTO;
import dto.LoginDTO;
import dto.SecurityQuestionDTO;
import dto.SellerDTO;
import repository.RegistrationRepoImpl;
import repository.RegistrationRepository;

import java.util.List;

public class RegistrationServiceImpl implements RegistrationService{

    @Override
    public List<SecurityQuestionDTO> getAllQuestions() {
        RegistrationRepository repo=new RegistrationRepoImpl();
        return repo.getAllQuestions();
    }

    @Override
    public boolean registerBuyer(BuyerDTO dto) {

        if (dto.getEmail() == null || !dto.getEmail().contains("@")) {
            System.out.println("Invalid email");
            return false;
        }

        if (dto.getPassword().length() < 6) {
            System.out.println("Password too short");
            return false;
        }

        if (dto.getPhone().length()!=10)
        {
            System.out.println("Phone number must be 10 digits");
            return false;
        }
        RegistrationRepository repo=new RegistrationRepoImpl();
        boolean saved= repo.registerBuyer(dto);
        return saved;
    }

    @Override
    public boolean registerSeller(SellerDTO dto) {
        if (dto.getEmail() == null || !dto.getEmail().contains("@")) {
            System.out.println("Invalid email");
            return false;
        }


        if (dto.getPassword().length() < 6) {
            System.out.println("Password too short");
            return false;
        }

        if (dto.getPhone().length()!=10)
        {
            System.out.println("Phone number must be 10 digits");
            return false;
        }
        RegistrationRepository repo=new RegistrationRepoImpl();
        boolean saved= repo.registerSeller(dto);
        return saved;
    }

    @Override
    public String login(String email, String password) {

        RegistrationRepository repo=new RegistrationRepoImpl();
        LoginDTO dto= repo.login(email);
        if(dto.getUserId()==0)
        {
            System.out.println("------");
            return "Email does not exists";
        }
        else
        {
            if(password.equals(dto.getPassword()))
            {
                String username=  repo.getUserNameById(dto.getUserId(),dto.getRole());


                return dto.getRole() +","+ username +","+dto.getUserId();
            }

            return "Password is Incorrect";
        }

    }

    @Override
    public void setNewPassword(int userid, String currentPassword, String newPassword, String confirmPassword) {

        RegistrationRepository repo=new RegistrationRepoImpl();
        String password=repo.getPasswordByUserId(userid);
        System.out.println(userid);
        if(password!=null)
        {

            if(currentPassword.equals(password))
            {

                if(newPassword.equals(confirmPassword)) {
                   if( repo.updateUserPassword(userid, newPassword))
                   {
                       System.out.println("Password Changed Successfully");
                   }

                }
                else
                    System.out.println("New password and Confirm Password must be same");


            }
            else
                System.out.println("Passwords does not match");

        }
    }

    @Override
    public String getQuestionByEmail(String email) {
        RegistrationRepository repo=new RegistrationRepoImpl();
        return repo.getQuestionByEmail(email);
    }

    @Override
    public boolean verifyAnswer(String email, String answer) {
        RegistrationRepository repo=new RegistrationRepoImpl();

        return repo.verifyAnswer(email, answer);
    }

    @Override
    public boolean resetPassword(String email, String newPassword) {
        RegistrationRepository repo=new RegistrationRepoImpl();

        return repo.resetPassword(email, newPassword);
    }
}
