package service;

import dto.CartItemsDTO;
import dto.ProductDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import repository.BuyerProductRepository;
import repository.BuyerProductRepositoryImpl;

import java.util.List;

public class BuyerProductServiceImpl implements BuyerProductService{

    private static final Logger log =
            LoggerFactory.getLogger(BuyerProductServiceImpl.class);


    BuyerProductRepository repo=new BuyerProductRepositoryImpl();
    @Override
    public List<ProductDTO> viewAllProducts() {
        log.info("Buyer requested to view all products");
        return repo.viewAllProducts();
    }

    @Override
    public ProductDTO viewProductDetails(int productId) {
        return repo.viewProductDetails(productId);
    }

    @Override
    public List<ProductDTO> searchByCategoryName(String categoryName) {
        return repo.searchByCategoryName(categoryName);
    }

    @Override
    public boolean addToFavourites(int buyerId, int productId) {

        log.info("Buyer {} attempting to add product {} to cart", buyerId, productId);

        // 1️⃣ Check product exists
        if (!repo.isProductExists(productId)) {
            log.warn("Cannot add to cart. Product not found: {}", productId);
            return false;
        }
        return repo.addToFavourites(buyerId,productId);
    }

    @Override
    public boolean addToCart(int buyerId, int productId)
    {

        log.info("Buyer {} attempting to add product {} to cart", buyerId, productId);

        // 1️⃣ Check product exists
        if (!repo.isProductExists(productId)) {
            log.warn("Cannot add to cart. Product not found: {}", productId);
            return false;
        }


        int availableStock = repo.getStockByProductId(productId);

        int cartQty = repo.getCartQuantity(buyerId, productId);

        if (cartQty + 1 > availableStock) {
            log.warn("Stock limit exceeded. Buyer {}, Product {}, Available {}, In Cart {}",
                    buyerId, productId, availableStock, cartQty);
            System.out.println("Only " + availableStock + " items available in stock");
            return false;

        }

        boolean success = repo.addToCart(buyerId, productId);

        if (success) {
            log.info("Product {} added to cart successfully for buyer {}", productId, buyerId);
        } else {
            log.error("Failed to add product {} to cart for buyer {}", productId, buyerId);
        }

        return success;
    }

    @Override
    public List<CartItemsDTO> viewCart(int buyerId) {
        return repo.viewCart(buyerId);
    }

    @Override
    public boolean increaseQuantity(int buyerId, int productId) {

        log.info("Buyer {} requested quantity increase for product {}", buyerId, productId);

        int availableStock = repo.getStockByProductId(productId);
        int cartQty = repo.getCartQuantity(buyerId, productId);
        System.out.println(availableStock+"-----------");


        if (cartQty + 1 > availableStock) {
            log.warn("Cannot increase quantity. Buyer {}, Product {}, Stock {}, CartQty {}",
                    buyerId, productId, availableStock, cartQty);
            System.out.println("Only " + availableStock + " items available in stock");
            return false;
        }
        boolean success = repo.increaseQuantity(buyerId, productId);

        if (success) {
            log.info("Quantity increased for product {} in buyer {} cart", productId, buyerId);
        } else {
            log.error("Failed to increase quantity for product {} in buyer {} cart",
                    productId, buyerId);
        }

        return success;


    }

    @Override
    public void decreaseQuantity(int buyerId, int productId) {


        log.info("Buyer {} requested quantity decrease for product {}", buyerId, productId);
        repo.decreaseQuantity(buyerId, productId);
;
    }

    @Override
    public void removeItem(int buyerId, int productId) {

        log.info("Buyer {} requested removal of product {} from cart", buyerId, productId);

        if (repo.removeFromCart(buyerId, productId)) {
            log.info("Product {} removed from cart for buyer {}", productId, buyerId);
            System.out.println("Removed from cart successfully");
        } else {
            log.error("Failed to remove product {} from cart for buyer {}", productId, buyerId);
            System.out.println("Failed to remove item from cart");
        }
    }

    @Override
    public List<ProductDTO> viewFavourites(int buyerId) {
        return repo.viewFavourites(buyerId);
    }

    @Override
    public List<ProductDTO> searchByKeyword(String keyword) {
        return repo.searchByKeyword(keyword);
    }


}
