package service;

import dto.CategoryDTO;
import dto.ProductDTO;
import repository.ProductRepository;
import repository.ProductRepositoryImpl;

import java.util.List;

public class ProductServiceImpl implements ProductService{

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
    public boolean updateProductDetailsById(int productId, double discountedPrice, int stock, int stockThreshold) {

        ProductRepository repo=new ProductRepositoryImpl();

        return repo.updateProductDetailsById(productId,discountedPrice,stock,stockThreshold);
    }

    @Override
    public boolean deleteProductById(int productId) {
        ProductRepository repo=new ProductRepositoryImpl();

        return repo.deleteProductById(productId);

    }
}
