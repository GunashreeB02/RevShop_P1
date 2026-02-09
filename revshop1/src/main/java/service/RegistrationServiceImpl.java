package service;

import dto.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import repository.RegistrationRepoImpl;
import repository.RegistrationRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.regex.Pattern;

public class RegistrationServiceImpl implements RegistrationService {

    private static final Logger log =
            LoggerFactory.getLogger(RegistrationServiceImpl.class);
    private final RegistrationRepository repo = new RegistrationRepoImpl();

    // Email regex
    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");

    // Phone regex (10 digits)
    private static final Pattern PHONE_PATTERN =
            Pattern.compile("^[0-9]{10}$");

    private static final Pattern GST_PATTERN =
            Pattern.compile("^[0-9]{2}[A-Z]{5}[0-9]{4}[A-Z]{1}[1-9A-Z]{1}Z[0-9A-Z]{1}$");


    // ================= GET QUESTIONS =================
    @Override
    public List<SecurityQuestionDTO> getAllQuestions() {
        return repo.getAllQuestions();
    }


    // ================= BUYER REGISTRATION =================
    @Override
    public boolean registerBuyer(BuyerDTO dto) {

        try {

            if (dto == null) {
                log.warn("Buyer DTO is null");                return false;
            }

            if (!isValidEmail(dto.getEmail())) return false;
            if (repo.isEmailExists(dto.getEmail())) {
                log.warn("Email already registered: {}", dto.getEmail());
                return false;
            }
            if (!isValidPassword(dto.getPassword())) return false;
            if (!isValidPhone(dto.getPhone())) return false;
            if (repo.isPhoneExistsInBuyer(dto.getPhone())) {
                log.warn("Phone already registered: {}", dto.getPhone());
                return false;
            }
            if (!isValidDOB(dto.getDob())) return false;
            if (isEmpty(dto.getFullName())) return print("Full name required");
            if (isEmpty(dto.getGender())) return print("Gender required");
            if (isEmpty(dto.getSecurityAnswer())) return print("Security answer required");

            return repo.registerBuyer(dto);

        } catch (Exception e) {
            log.error("Error during buyer registration", e);            return false;
        }
    }


    // ================= SELLER REGISTRATION =================
    @Override
    public boolean registerSeller(SellerDTO dto) {

        try {

            if (dto == null) {
                log.warn("Seller DTO is null");
                return false;
            }

            if (!isValidEmail(dto.getEmail())) return false;
            if (repo.isEmailExists(dto.getEmail())) {
                log.warn("Email already registered: {}", dto.getEmail());
                return false;
            }
            if (!isValidPassword(dto.getPassword())) return false;
            if (!isValidPhone(dto.getPhone())) return false;
            if (repo.isPhoneExistsInSeller(dto.getPhone())) {
                log.warn("Phone already registered: {}", dto.getPhone());
                return false;
            }
            if (isEmpty(dto.getBusinessName())) return print("Business name required");
            if (isEmpty(dto.getGstNumber())) return print("GST required");
            if (!isValidGST(dto.getGstNumber())) return false;

            if (repo.isGstExists(dto.getGstNumber())) {
                return print("GST already registered");
            }

            return repo.registerSeller(dto);

        } catch (Exception e) {
            log.error("Error during seller registration", e);
            return false;
        }
    }


    // ================= LOGIN =================
    @Override
    public String login(String email, String password) {

        try {

            if (!isValidEmail(email)) return "Invalid email format";
            if (password == null || password.isEmpty()) return "Password required";

            LoginDTO dto = repo.login(email);

            if (dto == null || dto.getUserId() == 0) {
                log.warn("Login failed. Email not found: {}", email);
                return "Email does not exist";
            }

            if (!password.equals(dto.getPassword())) {
                log.warn("Incorrect password for email: {}", email);
                return "Incorrect Password";
            }

            String username = repo.getUserNameById(dto.getUserId(), dto.getRole());

            log.info("User logged in successfully: {}", email);
            return dto.getRole() + "," + username + "," + dto.getUserId();

        } catch (Exception e) {
            log.error("Login error for email: {}", email, e);

            return "Login error: " + e.getMessage();
        }
    }


    // ================= CHANGE PASSWORD =================
    @Override
    public void setNewPassword(int userId, String currentPassword, String newPassword, String confirmPassword) {

        try {

            String dbPassword = repo.getPasswordByUserId(userId);

            if (dbPassword == null) {
                log.warn("User not found for password change: {}", userId);
                return;
            }

            if (!currentPassword.equals(dbPassword)) {
                log.warn("Incorrect current password for user: {}", userId);
                return;
            }

            if (!isValidPassword(newPassword)) return;

            if (!newPassword.equals(confirmPassword)) {
                log.warn("Password mismatch for user: {}", userId);
                return;
            }

            if (repo.updateUserPassword(userId, newPassword)) {
                log.info("Password changed successfully for user: {}", userId);
            }

        } catch (Exception e) {
            log.error("Error changing password for user: {}", userId, e);        }
    }


    // ================= FORGOT PASSWORD =================
    @Override
    public String getQuestionByEmail(String email) {
        return repo.getQuestionByEmail(email);
    }

    @Override
    public boolean verifyAnswer(String email, String answer) {
        return repo.verifyAnswer(email, answer);
    }

    @Override
    public boolean resetPassword(String email, String newPassword) {
        if (!isValidPassword(newPassword)) return false;
        return repo.resetPassword(email, newPassword);
    }


    // =========================================================
    // 🔹 COMMON VALIDATION METHODS (BEST PRACTICE)
    // =========================================================

    private boolean isValidEmail(String email) {
        if (email == null || !EMAIL_PATTERN.matcher(email).matches()) {
            return print("Invalid email format");
        }
        return true;
    }

    private boolean isValidPassword(String password) {
        if (password == null || password.length() < 6) {
            return print("Password must be at least 6 characters");
        }
        return true;
    }

    private boolean isValidPhone(String phone) {
        if (phone == null || !PHONE_PATTERN.matcher(phone).matches()) {
            return print("Phone must be 10 digits");
        }
        return true;
    }

    private boolean isValidDOB(LocalDate dob) {
        if (dob == null || dob.isAfter(LocalDate.now().minusYears(10))) {
            return print("Invalid DOB");
        }
        return true;
    }

    private boolean isEmpty(String value) {
        return value == null || value.trim().isEmpty();
    }

    private boolean print(String msg) {
        System.out.println(msg);
        return false;
    }

    private boolean isValidGST(String gst) {
        if (gst == null || !GST_PATTERN.matcher(gst).matches()) {
            return print("Invalid GST number format");
        }
        return true;
    }
}
