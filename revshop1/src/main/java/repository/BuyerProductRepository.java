package repository;

import dto.CartItemsDTO;
import dto.ProductDTO;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface BuyerProductRepository {

    List<ProductDTO> viewAllProducts();
    ProductDTO viewProductDetails(int productId);

    List<ProductDTO> searchByCategoryName(String categoryName);

    boolean addToFavourites(int buyerId, int productId);

    List<ProductDTO> viewFavourites(int buyerId);

    boolean addToCart(int buyerId, int productId);
    List<CartItemsDTO> viewCart(int buyerId);

    boolean removeFromCart(int buyerId, int productId);

    boolean decreaseQuantity(int buyerId, int productId);

    boolean increaseQuantity(int buyerId, int productId);

    int getCartQuantity(int buyerId, int productId);

    int getStockByProductId(int productId);

    List<ProductDTO> searchByKeyword(String keyword);

    boolean isProductExists(int productId);
}
