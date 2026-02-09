package repository;

import dto.CategoryDTO;
import dto.ProductDTO;
import enumeration.ConnectionEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductRepositoryImpl implements  ProductRepository{


    private static final Logger log =
            LoggerFactory.getLogger(ProductRepositoryImpl.class);

    @Override
    public boolean saveProductDetails(ProductDTO productDTO) {

        String sql = " INSERT INTO product (seller_id, product_name, description, manufacturer, mrp, selling_price, stock, stock_threshold, category_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = DriverManager.getConnection(ConnectionEnum.URL.getValue(), ConnectionEnum.USERNAME.getValue(), ConnectionEnum.PASSWORD.getValue());
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setInt(1, productDTO.getSellerId());
            pst.setString(2, productDTO.getProductName());
            pst.setString(3, productDTO.getDescription());
            pst.setString(4, productDTO.getManufacturer());
            pst.setDouble(5, productDTO.getMrp());
            pst.setDouble(6, productDTO.getSellingPrice());
            pst.setInt(7, productDTO.getStock());
            pst.setInt(8, productDTO.getStockThreshold());
            pst.setInt(9,productDTO.getCategoryId());

            boolean saved = pst.executeUpdate() > 0;

            if (saved)
                log.info("Product saved successfully: {}", productDTO.getProductName());
            else
                log.warn("Product save failed for sellerId={}", productDTO.getSellerId());

            return saved;

        } catch (Exception e) {
            log.error("Error saving product: {}", productDTO.getProductName(), e);
        }
        return false;
    }

    @Override
    public List<CategoryDTO> getAllCategories() {
        List<CategoryDTO> categories = new ArrayList<>();

        String sql = "SELECT category_id, category_name, description FROM categories";

        try (Connection con = DriverManager.getConnection(
                ConnectionEnum.URL.getValue(),
                ConnectionEnum.USERNAME.getValue(),
                ConnectionEnum.PASSWORD.getValue());
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                CategoryDTO dto = new CategoryDTO();
                dto.setCategoryId(rs.getInt("category_id"));
                dto.setCategoryName(rs.getString("category_name"));
                dto.setDescription(rs.getString("description"));
                categories.add(dto);
            }

            log.info("Fetched {} categories", categories.size());

        } catch (SQLException e) {
            log.error("Error fetching categories", e);
        }

        return categories;
    }

    @Override
    public List<ProductDTO> getAllProducts(int sellerId) {
        List<ProductDTO> products = new ArrayList<>();

        String sql = """
        SELECT product_id, product_name, description, mrp, selling_price, stock, stock_threshold
        FROM product
        WHERE seller_id = ?
        AND is_active = 1
    """;

        try (Connection con = DriverManager.getConnection(
                ConnectionEnum.URL.getValue(),
                ConnectionEnum.USERNAME.getValue(),
                ConnectionEnum.PASSWORD.getValue());
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, sellerId);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                ProductDTO dto = new ProductDTO();
                dto.setProductId(rs.getInt("product_id"));
                dto.setProductName(rs.getString("product_name"));
                dto.setDescription(rs.getString("description"));
                dto.setMrp(rs.getDouble("mrp"));
                dto.setSellingPrice(rs.getDouble("selling_price"));
                dto.setStock(rs.getInt("stock"));
                dto.setStockThreshold(rs.getInt("stock_threshold"));

                products.add(dto);
            }
            log.info("Seller {} has {} active products", sellerId, products.size());

        } catch (Exception e) {
            log.error("Error fetching products for sellerId={}", sellerId, e);
        }

        return products;
    }

    @Override
    public boolean updateProductDetailsById(int productId,double mrp, double discountedPrice, int stock, int stockThreshold) {
        String sql = " update product set selling_price = ? , stock = ? , stock_threshold = ? , mrp = ? where product_id = ?";

        try (Connection con = DriverManager.getConnection(ConnectionEnum.URL.getValue(), ConnectionEnum.USERNAME.getValue(), ConnectionEnum.PASSWORD.getValue());
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setDouble(1, discountedPrice);
            pst.setInt(2, stock);
            pst.setInt(3, stockThreshold);
            pst.setDouble(4,mrp);
            pst.setInt(5, productId);
            int rows = pst.executeUpdate();

            if (rows > 0) {
                log.info("Product updated successfully: productId={}", productId);
                return true;
            } else {
                log.warn("Update failed — product not found: productId={}", productId);
                return false;
            }

        } catch (Exception e) {
            log.error("Error updating productId={}", productId, e);
        }

        return false;

    }

    @Override
    public boolean deleteProductById(int productId) {

        String sql = """
           UPDATE product
                                        SET is_active = 0
                                        WHERE product_id = ?
                                        
        """;

        try (Connection con = DriverManager.getConnection(
                ConnectionEnum.URL.getValue(),
                ConnectionEnum.USERNAME.getValue(),
                ConnectionEnum.PASSWORD.getValue());
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, productId);


            boolean deleted = ps.executeUpdate() > 0;

            if (deleted)
                log.info("Product soft-deleted: productId={}", productId);
            else
                log.warn("Delete failed — product not found: productId={}", productId);

            return deleted;

        } catch (Exception e) {
            log.error("Error deleting productId={}", productId, e);
        }

        return false;
    }

    @Override
    public int getSellerIdByProductId(int productId) {

        int sellerId=0;
        String sql = """
            SELECT seller_id
            FROM product
            WHERE product_id = ?
        """;

        try (Connection con = DriverManager.getConnection(
                ConnectionEnum.URL.getValue(),
                ConnectionEnum.USERNAME.getValue(),
                ConnectionEnum.PASSWORD.getValue());
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, productId);

            ResultSet rs = ps.executeQuery();


            while (rs.next()) {

                 sellerId = rs.getInt("seller_id");
                return sellerId;
            }

        } catch (Exception e) {
            log.error("Error fetching sellerId for productId={}", productId, e);
        }
        return 0;
    }

    @Override
    public boolean isProductExistsForSeller(int productId, int sellerId) {
        String sql = """
        SELECT 1 FROM product 
        WHERE product_id = ? AND seller_id = ?
    """;

        try (Connection con = DriverManager.getConnection(
                ConnectionEnum.URL.getValue(),
                ConnectionEnum.USERNAME.getValue(),
                ConnectionEnum.PASSWORD.getValue());
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, productId);
            ps.setInt(2, sellerId);

            ResultSet rs = ps.executeQuery();
            return rs.next();

        } catch (SQLException e) {
            log.error("Error checking product ownership", e);
        }
        return false;
    }


    public boolean isProductActive(int productId) {

        String sql = "SELECT is_active FROM product WHERE product_id = ?";

        try (Connection con = DriverManager.getConnection(
                ConnectionEnum.URL.getValue(),
                ConnectionEnum.USERNAME.getValue(),
                ConnectionEnum.PASSWORD.getValue());
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, productId);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt("is_active") == 1;
            }

            log.warn("Product not found while checking active status: productId={}", productId);

        } catch (Exception e) {
            log.error("Error checking active status for productId={}", productId, e);
        }

        return false;
    }



}
