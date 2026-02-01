package dto;

import java.time.LocalDateTime;
import java.util.List;

public class OrderDTO {


    private int orderId;
    private int buyerId;
    private LocalDateTime orderDate;
    private double totalAmount;
    private String status;

    // Not a DB column – for display
    private List<OrderItemDTO> items;

    private OrderAddressDTO shippingAddress;
    private OrderAddressDTO billingAddress;
    private String paymentMethod;
    private String paymentStatus;

    public OrderDTO() {}

    public OrderDTO(int orderId, int buyerId,
                    LocalDateTime orderDate,
                    double totalAmount,
                    String status) {
        this.orderId = orderId;
        this.buyerId = buyerId;
        this.orderDate = orderDate;
        this.totalAmount = totalAmount;
        this.status = status;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(int buyerId) {
        this.buyerId = buyerId;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<OrderItemDTO> getItems() {
        return items;
    }

    public void setItems(List<OrderItemDTO> items) {
        this.items = items;
    }

    public OrderAddressDTO getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(OrderAddressDTO shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public OrderAddressDTO getBillingAddress() {
        return billingAddress;
    }

    public void setBillingAddress(OrderAddressDTO billingAddress) {
        this.billingAddress = billingAddress;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }
}
