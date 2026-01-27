package repository;

import dto.CategoryDTO;
import dto.ProductDTO;
import enumeration.ConnectionEnum;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductRepositoryImpl implements  ProductRepository{

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

            return pst.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
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

        } catch (SQLException e) {
            e.printStackTrace();
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

        } catch (Exception e) {
            e.printStackTrace();
        }

        return products;
    }

    @Override
    public boolean updateProductDetailsById(int productId, double discountedPrice, int stock, int stockThreshold) {
        String sql = " update product set selling_price = ? , stock = ? , stock_threshold = ? where product_id = ?";

        try (Connection con = DriverManager.getConnection(ConnectionEnum.URL.getValue(), ConnectionEnum.USERNAME.getValue(), ConnectionEnum.PASSWORD.getValue());
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setDouble(1, discountedPrice);
            pst.setInt(2, stock);
            pst.setInt(3, stockThreshold);
            pst.setInt(4, productId);


            return pst.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deleteProductById(int productId) {

        String sql = """
            DELETE FROM product
            WHERE product_id = ?
        """;

        try (Connection con = DriverManager.getConnection(
                ConnectionEnum.URL.getValue(),
                ConnectionEnum.USERNAME.getValue(),
                ConnectionEnum.PASSWORD.getValue());
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, productId);


            int rows = ps.executeUpdate();
            return rows > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }





}
