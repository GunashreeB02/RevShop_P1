package dto;

import java.time.LocalDateTime;

public class ReviewDTO {


    private int reviewId;
    private int productId;
    private int buyerId;
    private int orderId;

    private int rating;
    private String reviewComment;
    private LocalDateTime reviewDate;

    private String productName;
    private String buyerName;
    public ReviewDTO()
    {

    }

    public ReviewDTO(int rating, String reviewComment)
    {
        this.reviewComment=reviewComment;
        this.rating = rating;

    }

    public ReviewDTO(int reviewId, int productId, int buyerId, int rating, String reviewComment, LocalDateTime reviewDate) {
        this.reviewId = reviewId;
        this.productId = productId;
        this.buyerId = buyerId;
        this.rating = rating;
        this.reviewComment = reviewComment;
        this.reviewDate = reviewDate;
    }

    public int getReviewId() {
        return reviewId;
    }

    public void setReviewId(int reviewId) {
        this.reviewId = reviewId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(int buyerId) {
        this.buyerId = buyerId;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getReviewComment() {
        return reviewComment;
    }

    public void setReviewComment(String reviewComment) {
        this.reviewComment = reviewComment;
    }

    public LocalDateTime getReviewDate() {
        return reviewDate;
    }

    public void setReviewDate(LocalDateTime reviewDate) {
        this.reviewDate = reviewDate;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setBuyerName(String buyerName) {
        this.buyerName = buyerName;
    }

    public String getBuyerName() {
        return buyerName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductName() {
        return productName;
    }
}
