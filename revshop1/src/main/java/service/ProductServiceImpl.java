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
        if ((productDTO.getStock() < 0 || productDTO.getStockThreshold() < 0)) {
            System.out.println("Stock and Threshold should not be zero");
            return false;
        }

        if (productDTO.getStockThreshold() > productDTO.getStock()) {

            System.out.println("Threshold value must not be greater than Stock value");
            return false; // threshold cannot exceed stock
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
        // 🔹 1. basic id validation
        if (productId <= 0 || sellerId <= 0) {
            return false;
        }

        // 🔹 2. price validation
        if (mrp <= 0 || discountedPrice <= 0) {
            return false;
        }

        // 🔹 3. selling price must be <= MRP
        if (discountedPrice > mrp) {
            System.out.println("Selling price cannot be greater than MRP");

            return false;
        }

        // 🔹 4. stock validation
        if ((stock < 0 || stockThreshold < 0)) {
            System.out.println("Stock and Threshold should not be zero");

            return false;
        }
        if (stockThreshold > stock) {
            System.out.println("Threshold value must not be greater than Stock value");

            return false; // threshold cannot exceed stock
        }

        // 🔹 5. ownership check
        if (!productRepository.isProductExistsForSeller(productId, sellerId)) {
            log.warn("Product not found for seller: {}", productId);
            return false;
        }

        // 🔹 6. update
        return productRepository.updateProductDetailsById(
                productId, mrp, discountedPrice, stock, stockThreshold);


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
