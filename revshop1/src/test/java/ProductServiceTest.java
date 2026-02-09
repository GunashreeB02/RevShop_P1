import org.junit.jupiter.api.Test;
import service.ProductService;
import service.ProductServiceImpl;

import static org.junit.jupiter.api.Assertions.*;

public class ProductServiceTest {

    ProductService service = new ProductServiceImpl();

    // ✅ 1. Valid update
    @Test
    void updateProductValidTest() {

        boolean result = service.updateProductDetailsById(
                1,   // productId existing in DB
                1,   // sellerId existing in DB
                1000,
                900,
                10,
                2
        );

        assertTrue(result);
    }

    // ❌ 2. Invalid product id
    @Test
    void updateProductInvalidIdTest() {

        boolean result = service.updateProductDetailsById(
                -1,
                1,
                1000,
                900,
                10,
                2
        );

        assertFalse(result);
    }

    // ❌ 3. Selling price > MRP
    @Test
    void updateProductSellingGreaterThanMrpTest() {

        boolean result = service.updateProductDetailsById(
                1,
                1,
                1000,
                1200,
                10,
                2
        );

        assertFalse(result);
    }

    // ❌ 4. Negative price
    @Test
    void updateProductNegativePriceTest() {

        boolean result = service.updateProductDetailsById(
                1,
                1,
                -100,
                50,
                10,
                2
        );

        assertFalse(result);
    }

    // ❌ 5. Negative stock
    @Test
    void updateProductNegativeStockTest() {

        boolean result = service.updateProductDetailsById(
                1,
                1,
                1000,
                900,
                -5,
                2
        );

        assertFalse(result);
    }
}
