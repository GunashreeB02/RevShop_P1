
import org.junit.jupiter.api.Test;
import service.RegistrationService;
import service.RegistrationServiceImpl;

import static org.junit.jupiter.api.Assertions.*;

public class RegistrationServiceTest {

    RegistrationService service = new RegistrationServiceImpl();

    // ✅ 1. Login success test
    @Test
    void loginSuccessTest() {

        String result = service.login("guna@gmail.com", "guna@02");

        assertTrue(result.contains("BUYER"));
    }

    // ❌ 2. Email not found
    @Test
    void loginEmailNotFoundTest() {

        String result = service.login("wrong@gmail.com", "123456");

        assertEquals("Email does not exist", result);
    }

    // ❌ 3. Wrong password
    @Test
    void loginWrongPasswordTest() {

        String result = service.login("guna@gmail.com", "wrongpass");

        assertEquals("Incorrect password", result);
    }



    @Test
    void loginInvalidEmailFormatTest() {

        String result = service.login("guna", "123456");

        assertEquals("Invalid email format", result);
    }

    // ❌ 5. Empty password
    @Test
    void loginEmptyPasswordTest() {

        String result = service.login("guna@gmail.com", "");

        assertEquals("Password required", result);
    }
}
