package repository;

import dto.CartItemsDTO;
import dto.ProductDTO;
import enumeration.ConnectionEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BuyerProductRepositoryImpl implements  BuyerProductRepository{

    private static final Logger log =
            LoggerFactory.getLogger(BuyerProductRepositoryImpl.class);
    @Override
    public List<ProductDTO> viewAllProducts() {
        log.debug("Fetching all products from database");
        List<ProductDTO> list = new ArrayList<>();

        String sql = """
        SELECT p.product_id, p.product_name, p.manufacturer,
               p.selling_price, p.stock, c.category_name
        FROM product p
        JOIN categories c ON p.category_id = c.category_id
    """;

        try (Connection con = DriverManager.getConnection(
                ConnectionEnum.URL.getValue(),
                ConnectionEnum.USERNAME.getValue(),
                ConnectionEnum.PASSWORD.getValue());
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                ProductDTO dto = new ProductDTO();
                dto.setProductId(rs.getInt("product_id"));
                dto.setProductName(rs.getString("product_name"));
                dto.setManufacturer(rs.getString("manufacturer"));
                dto.setSellingPrice(rs.getDouble("selling_price"));
                dto.setStock(rs.getInt("stock"));
                dto.setCategoryName(rs.getString("category_name"));
                list.add(dto);
            }
        } catch (Exception e) {
            log.error("Database error while <doing action>", e);
        }
        log.debug("Total products fetched: {}", list.size());
        return list;
    }

    @Override
    public ProductDTO viewProductDetails(int productId) {
        log.debug("Fetching product details for productId {}", productId);
        String sql = """
        SELECT p.product_name, p.description, p.manufacturer,
               p.mrp, p.selling_price, p.stock,
               c.category_name,
               r.rating, r.review_comment
        FROM product p
        JOIN categories c ON p.category_id = c.category_id
        LEFT JOIN reviews r ON p.product_id = r.product_id
        WHERE p.product_id = ?
    """;

        ProductDTO dto = null;

        try (Connection con = DriverManager.getConnection(
                ConnectionEnum.URL.getValue(),
                ConnectionEnum.USERNAME.getValue(),
                ConnectionEnum.PASSWORD.getValue());
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, productId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                if (dto == null) {
                    dto = new ProductDTO();
                    dto.setProductName(rs.getString("product_name"));
                    dto.setDescription(rs.getString("description"));
                    dto.setManufacturer(rs.getString("manufacturer"));
                    dto.setMrp(rs.getDouble("mrp"));
                    dto.setSellingPrice(rs.getDouble("selling_price"));
                    dto.setStock(rs.getInt("stock"));
                    dto.setCategoryName(rs.getString("category_name"));
                }
                dto.addReview(
                        rs.getInt("rating"),
                        rs.getString("review_comment")
                );
            }
        } catch (Exception e) {
            log.error("Error fetching product details for productId {}", productId, e);
        }
        return dto;
    }


    @Override
    public List<ProductDTO> searchByCategoryName(String categoryName) {
        List<ProductDTO> list = new ArrayList<>();

        String sql = """
        SELECT p.product_id, p.product_name, p.selling_price, p.stock,
               p.manufacturer, c.category_name, p.description, p.mrp
        FROM product p
        JOIN categories c ON p.category_id = c.category_id
        WHERE LOWER(c.category_name) = ?
    """;

        try (Connection con = DriverManager.getConnection(
                ConnectionEnum.URL.getValue(),
                ConnectionEnum.USERNAME.getValue(),
                ConnectionEnum.PASSWORD.getValue());PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, categoryName.toLowerCase());

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ProductDTO dto = new ProductDTO();
                dto.setProductId(rs.getInt("product_id"));
                dto.setProductName(rs.getString("product_name"));
                dto.setSellingPrice(rs.getDouble("selling_price"));
                dto.setStock(rs.getInt("stock"));
                dto.setManufacturer(rs.getString("manufacturer"));
                dto.setCategoryName(rs.getString("category_name"));
                dto.setDescription(rs.getString("description"));
                dto.setMrp(rs.getDouble("mrp"));


                list.add(dto);
            }
        } catch (SQLException e) {
            log.error("Database error while <doing action>", e);
        }
        return list;
    }


    @Override
    public boolean addToFavourites(int buyerId, int productId) {


        String checkSql =
                "SELECT favourite_id FROM favourites WHERE buyer_id = ? AND product_id = ?";

        String sql = """
        INSERT INTO favourites (buyer_id, product_id)
        VALUES (?, ?)
    """;

        try (Connection con = DriverManager.getConnection(
                ConnectionEnum.URL.getValue(),
                ConnectionEnum.USERNAME.getValue(),
                ConnectionEnum.PASSWORD.getValue());
            ) {

            PreparedStatement checkPs = con.prepareStatement(checkSql);
            checkPs.setInt(1, buyerId);
            checkPs.setInt(2, productId);

            ResultSet rs = checkPs.executeQuery();
            if (rs.next()) {
                log.warn("Product {} already in favourites for buyer {}", productId, buyerId);
                System.out.println("⚠ Product already in favourites");
                return false;
            }

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, buyerId);
            ps.setInt(2, productId);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            // duplicate favourite → ignore gracefully
            if (e.getErrorCode() == 1) {
                System.out.println("Already in favourites");
            }
            log.error("Database error while <doing action>", e);
        }
        return false;
    }


    @Override
    public List<ProductDTO> viewFavourites(int buyerId) {

        List<ProductDTO> favourites = new ArrayList<>();

        String sql = """
        SELECT p.product_id,
               p.product_name,
               p.manufacturer,
               p.selling_price,
               p.stock
        FROM favourites f
        JOIN product p ON f.product_id = p.product_id
        WHERE f.buyer_id = ?
        """;

        try (Connection con = DriverManager.getConnection(
                ConnectionEnum.URL.getValue(),
                ConnectionEnum.USERNAME.getValue(),
                ConnectionEnum.PASSWORD.getValue());
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, buyerId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                ProductDTO dto = new ProductDTO();
                dto.setProductId(rs.getInt("product_id"));
                dto.setProductName(rs.getString("product_name"));
                dto.setManufacturer(rs.getString("manufacturer"));
                dto.setSellingPrice(rs.getDouble("selling_price"));
                dto.setStock(rs.getInt("stock"));

                favourites.add(dto);
            }

        } catch (SQLException e) {
            log.error("Database error while <doing action>", e);
        }

        return favourites;
    }


    private int getOrCreateCart(int buyerId, Connection con) throws SQLException {

        String selectSql = "SELECT cart_id FROM cart WHERE buyer_id = ?";
        String insertSql = "INSERT INTO cart (buyer_id) VALUES (?)";

        try (PreparedStatement ps = con.prepareStatement(selectSql)) {
            ps.setInt(1, buyerId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt("cart_id");
            }
        }

        // create cart if not exists
        try (PreparedStatement ps = con.prepareStatement(insertSql, new String[]{"cart_id"})) {
            ps.setInt(1, buyerId);
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1);
            }
        }

        throw new SQLException("Unable to create cart");
    }



    @Override
    public boolean addToCart(int buyerId, int productId) {

        String checkSql =
                "SELECT quantity FROM cart_items WHERE cart_id = ? AND product_id = ?";

        String insertSql =
                "INSERT INTO cart_items (cart_id, product_id, quantity) VALUES (?, ?, 1)";

        String updateSql =
                "UPDATE cart_items SET quantity = quantity + 1 WHERE cart_id = ? AND product_id = ?";

        String deleteFavSql =
                "DELETE FROM favourites WHERE buyer_id = ? AND product_id = ?";

        try (Connection con = DriverManager.getConnection(
                ConnectionEnum.URL.getValue(),
                ConnectionEnum.USERNAME.getValue(),
                ConnectionEnum.PASSWORD.getValue())) {

            int cartId = getOrCreateCart(buyerId, con);

            PreparedStatement checkPs = con.prepareStatement(checkSql);
            checkPs.setInt(1, cartId);
            checkPs.setInt(2, productId);

            ResultSet rs = checkPs.executeQuery();

            if (rs.next()) {
                PreparedStatement updatePs = con.prepareStatement(updateSql);
                updatePs.setInt(1, cartId);
                updatePs.setInt(2, productId);
                updatePs.executeUpdate();
            } else {
                PreparedStatement insertPs = con.prepareStatement(insertSql);
                insertPs.setInt(1, cartId);
                insertPs.setInt(2, productId);
                insertPs.executeUpdate();
            }

            // remove from favourites if present
            PreparedStatement deleteFavPs = con.prepareStatement(deleteFavSql);
            deleteFavPs.setInt(1, buyerId);
            deleteFavPs.setInt(2, productId);
            deleteFavPs.executeUpdate();

            return true;

        } catch (Exception e) {
            log.error("Database error while <doing action>", e);
        }

        return false;
    }

    @Override
    public List<CartItemsDTO> viewCart(int buyerId) {
        List<CartItemsDTO> cartItems = new ArrayList<>();

        String sql = """
            SELECT ci.product_id, p.product_name, p.manufacturer, ci.quantity, p.selling_price
            FROM cart_items ci
            JOIN product p ON ci.product_id = p.product_id
            WHERE ci.cart_id = ?
            """;

        try (Connection con = DriverManager.getConnection(
                ConnectionEnum.URL.getValue(),
                ConnectionEnum.USERNAME.getValue(),
                ConnectionEnum.PASSWORD.getValue())) {

            int cartId = getOrCreateCart(buyerId, con);

            try (PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setInt(1, cartId);
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    int qty = rs.getInt("quantity");
                    double price = rs.getDouble("selling_price");

                    double itemTotal = qty * price;

                    CartItemsDTO dto = new CartItemsDTO(
                            rs.getInt("product_id"),
                            rs.getString("product_name"),
                            rs.getString("manufacturer"),
                            qty,
                            price
                    );

                    dto.setSubTotal(itemTotal);
                    cartItems.add(dto);
                }
            }

        } catch (SQLException e) {
            log.error("Failed to retrieve cart items for buyer {}", buyerId, e);
            System.out.println("Failed to retrieve cart items.");
        }

        return cartItems;
    }



    @Override
    public boolean removeFromCart(int buyerId, int productId) {
        String sql = "DELETE FROM cart_items WHERE cart_id = ? AND product_id = ?";

        try (Connection con = DriverManager.getConnection(
                ConnectionEnum.URL.getValue(),
                ConnectionEnum.USERNAME.getValue(),
                ConnectionEnum.PASSWORD.getValue())) {

            int cartId = getOrCreateCart(buyerId, con);

            try (PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setInt(1, cartId);
                ps.setInt(2, productId);
                int deleted = ps.executeUpdate();
                return deleted > 0;
            }

        } catch (SQLException e) {
            log.error("Database error while <doing action>", e);
            return false;
        }
    }


    @Override
    public boolean decreaseQuantity(int buyerId, int productId) {
        String sql = "UPDATE cart_items SET quantity = quantity - 1 " +
                "WHERE cart_id = ? AND product_id = ? AND quantity > 1";

        try (Connection con = DriverManager.getConnection(
                ConnectionEnum.URL.getValue(),
                ConnectionEnum.USERNAME.getValue(),
                ConnectionEnum.PASSWORD.getValue())) {

            int cartId = getOrCreateCart(buyerId, con);

            try (PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setInt(1, cartId);
                ps.setInt(2, productId);
                int updated = ps.executeUpdate();

                // If quantity was 1, remove the product
                if (updated == 0) {
                    return removeFromCart(buyerId, productId);
                }

                return true;
            }

        } catch (SQLException e) {
            log.error("Database error while <doing action>", e);
            return false;
        }
    }


    @Override
    public boolean increaseQuantity(int buyerId, int productId) {
        String sql = "UPDATE cart_items SET quantity = quantity + 1 " +
                "WHERE cart_id = ? AND product_id = ?";

        try (Connection con = DriverManager.getConnection(
                ConnectionEnum.URL.getValue(),
                ConnectionEnum.USERNAME.getValue(),
                ConnectionEnum.PASSWORD.getValue())) {

            int cartId = getOrCreateCart(buyerId, con);

            try (PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setInt(1, cartId);
                ps.setInt(2, productId);
                int updated = ps.executeUpdate();
                return updated > 0; // true if quantity was increased
            }

        } catch (SQLException e) {
            log.error("Database error while <doing action>", e);
            return false;
        }
    }


    @Override
    public int getCartQuantity(int buyerId, int productId) {

        String sql = """
        SELECT ci.quantity
        FROM cart_items ci
        JOIN cart c ON ci.cart_id = c.cart_id
        WHERE c.buyer_id = ? AND ci.product_id = ?
        """;

        int quantity = 0;

        try (Connection con = DriverManager.getConnection(
                ConnectionEnum.URL.getValue(),
                ConnectionEnum.USERNAME.getValue(),
                ConnectionEnum.PASSWORD.getValue());
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, buyerId);
            ps.setInt(2, productId);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                quantity = rs.getInt("quantity");
            }

        } catch (SQLException e) {
            log.error("Database error while <doing action>", e);
        }

        return quantity; // 0 means not in cart yet
    }


    @Override
    public int getStockByProductId(int productId) {

        log.debug("Fetching stock for product {}", productId);


        String sql = "SELECT stock FROM product WHERE product_id = ?";
        int stock = 0;

        try (Connection con = DriverManager.getConnection(
                ConnectionEnum.URL.getValue(),
                ConnectionEnum.USERNAME.getValue(),
                ConnectionEnum.PASSWORD.getValue());
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, productId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                stock = rs.getInt("stock");
            }

        } catch (SQLException e) {
            log.error("Error fetching stock for product {}", productId, e);
        }

        return stock; // 0 means out of stock or product not found
    }


    @Override
    public List<ProductDTO> searchByKeyword(String keyword) {

        List<ProductDTO> list = new ArrayList<>();

        String sql = """
        SELECT p.product_id,
               p.product_name,
               p.description,
               p.selling_price,
               p.stock,
               p.manufacturer,
               c.category_name
        FROM product p
        JOIN categories c ON p.category_id = c.category_id
        WHERE LOWER(p.product_name) LIKE ?
           OR LOWER(p.description) LIKE ?
           OR LOWER(p.manufacturer) LIKE ?
           OR LOWER(c.category_name) LIKE ?
    """;

        try (Connection con = DriverManager.getConnection(
                ConnectionEnum.URL.getValue(),
                ConnectionEnum.USERNAME.getValue(),
                ConnectionEnum.PASSWORD.getValue());
             PreparedStatement ps = con.prepareStatement(sql)) {

            String key = "%" + keyword.toLowerCase() + "%";

            ps.setString(1, key);
            ps.setString(2, key);
            ps.setString(3, key);
            ps.setString(4, key);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                ProductDTO dto = new ProductDTO();
                dto.setProductId(rs.getInt("product_id"));
                dto.setProductName(rs.getString("product_name"));
                dto.setDescription(rs.getString("description"));
                dto.setSellingPrice(rs.getDouble("selling_price"));
                dto.setStock(rs.getInt("stock"));
                dto.setManufacturer(rs.getString("manufacturer"));
                dto.setCategoryName(rs.getString("category_name"));

                list.add(dto);
            }

        } catch (SQLException e) {
            log.error("Database error while <doing action>", e);
        }
        return list;
    }

    @Override
    public boolean isProductExists(int productId) {
        String sql = "SELECT 1 FROM product WHERE product_id = ?";

        try (Connection con = DriverManager.getConnection( ConnectionEnum.URL.getValue(),
                ConnectionEnum.USERNAME.getValue(),
                ConnectionEnum.PASSWORD.getValue());

             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, productId);

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }

        } catch (SQLException e) {
            log.error("Error checking product existence", e);
        }

        return false;
    }


}
