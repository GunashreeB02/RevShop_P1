package dto;

public class SellerDTO {

    private String email;
    private String password;
    private String businessName;
    private String gstNumber;
    private String address;
    private String phone;
    private int securityQuestionId;
    private String securityAnswer;

    public SellerDTO()
    {

    }

    public SellerDTO(String email, String password, String businessName, String gstNumber, String address, String phone) {
        this.email = email;
        this.password = password;
        this.businessName = businessName;
        this.gstNumber = gstNumber;
        this.address = address;
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

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public String getGstNumber() {
        return gstNumber;
    }

    public void setGstNumber(String gstNumber) {
        this.gstNumber = gstNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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
