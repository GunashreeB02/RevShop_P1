package service;

import dto.CartItemsDTO;
import dto.ProductDTO;

import java.util.List;

public interface BuyerProductService {
    List<ProductDTO> viewAllProducts();

    ProductDTO viewProductDetails(int productId);
    List<ProductDTO> searchByCategoryName(String categoryName);

    public boolean addToFavourites(int buyerId, int productId);
    public boolean addToCart(int buyerId, int productId);

    List<CartItemsDTO> viewCart(int buyerId);

    boolean increaseQuantity(int buyerId, int productId);

    void decreaseQuantity(int buyerId, int productId);

    void removeItem(int buyerId, int productId);

    List<ProductDTO> viewFavourites(int buyerId);
}
