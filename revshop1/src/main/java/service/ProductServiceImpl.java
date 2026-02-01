package service;

import dto.CategoryDTO;
import dto.ProductDTO;
import dto.ReviewDTO;
import enumeration.ConnectionEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import repository.*;

import java.sql.*;
import java.util.List;

public class ProductServiceImpl implements ProductService{

    private static final Logger log =
            LoggerFactory.getLogger(BuyerProductServiceImpl.class);

    @Override
    public boolean addProduct(ProductDTO productDTO) {

        if (productDTO.getSellingPrice() > productDTO.getMrp()) {
            System.out.println("Selling price cannot be greater than MRP");
            return false;
        }

        ProductRepository repo = new ProductRepositoryImpl();
        return repo.saveProductDetails(productDTO);
    }

    @Override
    public List<CategoryDTO> getAllCategories() {

        ProductRepository repo=new ProductRepositoryImpl();
        return repo.getAllCategories();

    }

    @Override
    public List<ProductDTO> getAllProducts(int sellerId) {

        ProductRepository repo=new ProductRepositoryImpl();
        return repo.getAllProducts(sellerId);
    }

    @Override
    public boolean updateProductDetailsById(int productId,int sellerId,double mrp, double discountedPrice, int stock, int stockThreshold) {

            ProductRepository productRepository=new ProductRepositoryImpl();
        if (!productRepository.isProductExistsForSeller(productId,sellerId)) {
            log.warn("Cannot add to cart. Product not found: {}", productId);
            return false;
        }
        ProductRepository repo=new ProductRepositoryImpl();

        return repo.updateProductDetailsById(productId,mrp,discountedPrice,stock,stockThreshold);
    }

    @Override
    public boolean deleteProductById(int productId) {
        ProductRepository repo=new ProductRepositoryImpl();

        return repo.deleteProductById(productId);

    }

    @Override
    public int getSellerIdByProductId(int productId) {
        ProductRepository repo=new ProductRepositoryImpl();
        return repo.getSellerIdByProductId(productId);
    }

    @Override
    public List<ReviewDTO> getReviewsAtSeller(int sellerId) {
        ReviewsRepository repo=new ReviewsRepositoryImpl();

        return repo.getReviewsAtSeller(sellerId);
    }



}
