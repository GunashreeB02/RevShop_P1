package dto;

import java.time.LocalDateTime;

public class FavouritesDTO {

    private int favouriteId;
    private int buyerId;
    private int productId;
    private LocalDateTime addedAt;

    public FavouritesDTO()
    {

    }

    public FavouritesDTO(int favouriteId, int buyerId, int productId, LocalDateTime addedAt) {
        this.favouriteId = favouriteId;
        this.buyerId = buyerId;
        this.productId = productId;
        this.addedAt = addedAt;
    }

    public int getFavouriteId() {
        return favouriteId;
    }

    public void setFavouriteId(int favouriteId) {
        this.favouriteId = favouriteId;
    }

    public int getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(int buyerId) {
        this.buyerId = buyerId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public LocalDateTime getAddedAt() {
        return addedAt;
    }

    public void setAddedAt(LocalDateTime addedAt) {
        this.addedAt = addedAt;
    }
}
