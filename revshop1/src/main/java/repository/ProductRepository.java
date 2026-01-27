package repository;

import dto.CategoryDTO;
import dto.ProductDTO;

import java.util.List;

public interface ProductRepository {

    boolean saveProductDetails(ProductDTO productDTO);

    List<CategoryDTO> getAllCategories();

    List<ProductDTO> getAllProducts(int sellerId);
    boolean updateProductDetailsById(int productId,double discountedPrice,int stock,int stockThreshold);

    boolean deleteProductById(int productId);


}
