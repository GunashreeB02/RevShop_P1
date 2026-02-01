package dto;

import java.time.LocalDateTime;

public class SellerOrderDTO {


    private int orderId;
    private String buyerName;
    private String productName;
    private int quantity;
    private double price;
    private String orderStatus;
    private LocalDateTime orderDate;

    public SellerOrderDTO()
    {

    }

    public SellerOrderDTO(int orderId, String buyerName, String productName, int quantity, double price, String orderStatus, LocalDateTime orderDate) {
        this.orderId = orderId;
        this.buyerName = buyerName;
        this.productName = productName;
        this.quantity = quantity;
        this.price = price;
        this.orderStatus = orderStatus;
        this.orderDate = orderDate;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getBuyerName() {
        return buyerName;
    }

    public void setBuyerName(String buyerName) {
        this.buyerName = buyerName;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }
}
