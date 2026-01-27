package service;

import dto.CartItemsDTO;
import dto.ProductDTO;
import repository.BuyerProductRepository;
import repository.BuyerProductRepositoryImpl;

import java.util.List;

public class BuyerProductServiceImpl implements BuyerProductService{

    BuyerProductRepository repo=new BuyerProductRepositoryImpl();
    @Override
    public List<ProductDTO> viewAllProducts() {
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
        return repo.addToFavourites(buyerId,productId);
    }

    @Override
    public boolean addToCart(int buyerId, int productId)
    {
        int availableStock = repo.getStockByProductId(productId);
        System.out.println(availableStock+"-----------");
        int cartQty = repo.getCartQuantity(buyerId, productId);

        if (cartQty + 1 > availableStock) {
            System.out.println("Only " + availableStock + " items available in stock");
            return false;

        }

        return repo.addToCart(buyerId,productId);
    }

    @Override
    public List<CartItemsDTO> viewCart(int buyerId) {
        return repo.viewCart(buyerId);
    }

    @Override
    public boolean increaseQuantity(int buyerId, int productId) {

        int availableStock = repo.getStockByProductId(productId);
        int cartQty = repo.getCartQuantity(buyerId, productId);
        System.out.println(availableStock+"-----------");


        if (cartQty + 1 > availableStock) {
            System.out.println("Only " + availableStock + " items available in stock");
            return false;
        }

          boolean success=  repo.increaseQuantity(buyerId,productId);
        return  true;


    }

    @Override
    public void decreaseQuantity(int buyerId, int productId) {

        repo.decreaseQuantity(buyerId, productId)
;
    }

    @Override
    public void removeItem(int buyerId, int productId) {

        if(repo.removeFromCart(buyerId,productId))
        {
            System.out.println("removed from cart successfully");
        }
        else
        {
            System.out.println("failed");
        }
    }

    @Override
    public List<ProductDTO> viewFavourites(int buyerId) {
        return repo.viewFavourites(buyerId);
    }

}
