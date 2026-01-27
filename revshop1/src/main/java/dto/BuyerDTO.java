package dto;

import java.time.LocalDate;

public class BuyerDTO {

    private String email;
    private String password;
    private String fullName;
    private String gender;
    private LocalDate dob;
    private String phone;
    private int securityQuestionId;
    private String securityAnswer;

    public BuyerDTO()
    {

    }

    public BuyerDTO(String email, String password, String fullName, String gender, LocalDate dob, String phone) {
        this.email = email;
        this.password = password;
        this.fullName = fullName;
        this.gender = gender;
        this.dob = dob;
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setSecurityQuestionId(int securityQuestionId) {
        this.securityQuestionId = securityQuestionId;
    }

    public int getSecurityQuestionId() {
        return securityQuestionId;
    }

    public void setSecurityAnswer(String securityAnswer) {
        this.securityAnswer = securityAnswer;
    }

    public String getSecurityAnswer() {
        return securityAnswer;
    }
}
