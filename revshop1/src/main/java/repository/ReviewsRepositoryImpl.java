package repository;

import dto.ReviewDTO;
import enumeration.ConnectionEnum;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReviewsRepositoryImpl implements  ReviewsRepository{


    @Override
    public boolean hasPurchased(int buyerId, int productId) {

        String sql = """
        SELECT COUNT(*) 
        FROM order_items oi
        JOIN orders o ON oi.order_id = o.order_id
       WHERE o.buyer_id = ?
                   AND oi.product_id = ?
                   AND o.status = 'PLACED'
                   
    """;

        try (Connection con = DriverManager.getConnection(
                ConnectionEnum.URL.getValue(),
                ConnectionEnum.USERNAME.getValue(),
                ConnectionEnum.PASSWORD.getValue());
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, buyerId);
            ps.setInt(2, productId);

            ResultSet rs = ps.executeQuery();
            rs.next();
            return rs.getInt(1) > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override

    public boolean addReview(ReviewDTO dto) {

        String sql = """
        INSERT INTO reviews
        (product_id, buyer_id, order_id, rating, review_comment)
        VALUES (?, ?, ?, ?, ?)
    """;

        try (Connection con = DriverManager.getConnection(
                ConnectionEnum.URL.getValue(),
                ConnectionEnum.USERNAME.getValue(),
                ConnectionEnum.PASSWORD.getValue());
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, dto.getProductId());
            ps.setInt(2, dto.getBuyerId());
            ps.setInt(3, dto.getOrderId());
            ps.setInt(4, dto.getRating());
            ps.setString(5, dto.getReviewComment());

            ps.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.out.println("❌ You already reviewed this product.");
        }
        return false;
    }

    @Override

    public List<ReviewDTO> getReviewsByProduct(int productId) {

        List<ReviewDTO> list = new ArrayList<>();

        String sql = "SELECT * FROM reviews WHERE product_id = ?";

        try (Connection con = DriverManager.getConnection(
                ConnectionEnum.URL.getValue(),
                ConnectionEnum.USERNAME.getValue(),
                ConnectionEnum.PASSWORD.getValue());
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, productId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                ReviewDTO r = new ReviewDTO();
                r.setRating(rs.getInt("rating"));
                r.setReviewComment(rs.getString("review_comment"));
                r.setReviewDate(rs.getTimestamp("review_date").toLocalDateTime());
                list.add(r);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }


   @Override
    public List<ReviewDTO> getReviewsAtSeller(int sellerId)
   {
       List<ReviewDTO> reviews = new ArrayList<>();
       String sql =
               "SELECT p.product_name, b.full_name, r.rating, r.review_comment, r.review_date " +
                       "FROM reviews r " +
                       "JOIN product p ON r.product_id = p.product_id " +
                       "JOIN buyer_details b ON r.buyer_id = b.buyer_id " +
                       "WHERE p.seller_id = ? " +
                       "ORDER BY r.review_date DESC";
       try (Connection con = DriverManager.getConnection(
               ConnectionEnum.URL.getValue(),
               ConnectionEnum.USERNAME.getValue(),
               ConnectionEnum.PASSWORD.getValue());
            PreparedStatement ps = con.prepareStatement(sql)) {


           ps.setInt(1, sellerId);
           ResultSet rs = ps.executeQuery();

           while (rs.next()) {
               ReviewDTO dto = new ReviewDTO();

               dto.setProductName(rs.getString("product_name"));
               dto.setBuyerName(rs.getString("full_name"));
               dto.setRating(rs.getInt("rating"));
               dto.setReviewComment(rs.getString("review_comment"));
               dto.setReviewDate(rs.getTimestamp("review_date").toLocalDateTime());

               reviews.add(dto);
           }

       } catch (SQLException e) {
           e.printStackTrace();
       }

       return reviews;
   }
}
