import dto.BuyerDTO;
import org.junit.jupiter.api.Test;
import service.RegistrationService;
import service.RegistrationServiceImpl;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class BuyerRegistrationTest {

    RegistrationService service = new RegistrationServiceImpl();

    // 1️⃣ Null DTO
    @Test
    void registerBuyerNullTest() {
        assertFalse(service.registerBuyer(null));
    }

    // 2️⃣ Invalid email
    @Test
    void registerBuyerInvalidEmailTest() {
        BuyerDTO dto = new BuyerDTO();
        dto.setEmail("guna");   // invalid

        assertFalse(service.registerBuyer(dto));
    }

    // 3️⃣ Short password
    @Test
    void registerBuyerShortPasswordTest() {
        BuyerDTO dto = new BuyerDTO();
        dto.setEmail("test@gmail.com");
        dto.setPassword("123"); // < 6 chars

        assertFalse(service.registerBuyer(dto));
    }

    // 4️⃣ Invalid phone
    @Test
    void registerBuyerInvalidPhoneTest() {
        BuyerDTO dto = new BuyerDTO();
        dto.setEmail("test@gmail.com");
        dto.setPassword("123456");
        dto.setPhone("999"); // invalid

        assertFalse(service.registerBuyer(dto));
    }

    // 5️⃣ Future DOB
    @Test
    void registerBuyerInvalidDOBTest() {
        BuyerDTO dto = new BuyerDTO();
        dto.setEmail("test@gmail.com");
        dto.setPassword("123456");
        dto.setPhone("9876543210");
        dto.setDob(LocalDate.now()); // age < 10

        assertFalse(service.registerBuyer(dto));
    }
}
