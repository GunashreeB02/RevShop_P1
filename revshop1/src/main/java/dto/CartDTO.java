package dto;

import java.time.LocalDateTime;

public class CartDTO {

    private int favId;
    private int userId;
    private int productId;
    private LocalDateTime addedOn;

    public CartDTO()
    {

    }

    public CartDTO(int favId, int userId, int productId, LocalDateTime addedOn) {
        this.favId = favId;
        this.userId = userId;
        this.productId = productId;
        this.addedOn = addedOn;
    }

    public int getFavId() {
        return favId;
    }

    public void setFavId(int favId) {
        this.favId = favId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public LocalDateTime getAddedOn() {
        return addedOn;
    }

    public void setAddedOn(LocalDateTime addedOn) {
        this.addedOn = addedOn;
    }
}
