package repository;

import dto.ProductDTO;
import dto.ReviewDTO;

import java.util.List;

public interface ReviewsRepository {
    boolean hasPurchased(int buyerId, int productId,int orderId);

    boolean addReview(ReviewDTO dto);

    List<ReviewDTO> getReviewsByProduct(int productId);


    List<ReviewDTO> getReviewsAtSeller(int sellerId);
}
