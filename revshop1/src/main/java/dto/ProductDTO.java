package dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ProductDTO {

    private  int productId;
    private int sellerId;
    private int categoryId;
    private String productName;
    private String description;
    private String manufacturer;
    private double mrp;
    private double sellingPrice;
    private int stock;
    private int stockThreshold;
    private String categoryName;
    private List<ReviewDTO> reviews = new ArrayList<>();

    public ProductDTO()
    {

    }

    public ProductDTO(int productId,int sellerId, String productName, String description,  String manufacturer, double mrp, double sellingPrice, int stock, int stockThreshold,int categoryId) {
        this.productId = productId;
        this.sellerId = sellerId;
        this.productName = productName;
        this.description = description;
        this.manufacturer = manufacturer;
        this.mrp = mrp;
        this.sellingPrice = sellingPrice;
        this.stock = stock;
        this.stockThreshold = stockThreshold;
        this.categoryId = categoryId;
    }

    public ProductDTO(int productId, int sellerId, int categoryId, String productName, String description, String manufacturer, double mrp, double sellingPrice, int stock, int stockThreshold, String categoryName) {
        this.productId = productId;
        this.sellerId = sellerId;
        this.categoryId = categoryId;
        this.productName = productName;
        this.description = description;
        this.manufacturer = manufacturer;
        this.mrp = mrp;
        this.sellingPrice = sellingPrice;
        this.stock = stock;
        this.stockThreshold = stockThreshold;
        this.categoryName = categoryName;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getSellerId() {
        return sellerId;
    }

    public void setSellerId(int sellerId) {
        this.sellerId = sellerId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public double getMrp() {
        return mrp;
    }

    public void setMrp(double mrp) {
        this.mrp = mrp;
    }

    public double getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(double sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public int getStockThreshold() {
        return stockThreshold;
    }

    public void setStockThreshold(int stockThreshold) {
        this.stockThreshold = stockThreshold;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void addReview(int rating, String comment) {
        if (rating > 0 && comment != null) {
            reviews.add(new ReviewDTO(rating, comment));
        }
    }

    public List<ReviewDTO> getReviews() {
        return reviews;
    }

    @Override
    public String toString() {
        return "Product Details\n" +
                "-----------------\n" +
                "Product ID      : " + productId + "\n" +
                "Name            : " + productName + "\n" +
                "Description     : " + description + "\n" +
                "Manufacturer    : " + manufacturer + "\n" +
                "MRP             : ₹" + mrp + "\n" +
                "Selling Price   : ₹" + sellingPrice + "\n" +
                "Stock           : " + stock + "\n" +
                "Category        : " + categoryName;
    }
}
