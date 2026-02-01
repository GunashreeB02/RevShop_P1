package dto;

public class OrderItemDTO {


    private int orderItemId;
    private int orderId;
    private int productId;
    private int sellerId;

    private String productName;
    private int quantity;
    private double price;      // price per unit
    private double subTotal;   // quantity * price

    public OrderItemDTO() {}

    public OrderItemDTO(int orderId, int productId, int sellerId,
                        String productName, int quantity, double price) {
        this.orderId = orderId;
        this.productId = productId;
        this.sellerId = sellerId;
        this.productName = productName;
        this.quantity = quantity;
        this.price = price;
        this.subTotal = quantity * price;
    }

    public int getOrderItemId() {
        return orderItemId;
    }

    public void setOrderItemId(int orderItemId) {
        this.orderItemId = orderItemId;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
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

    public double getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(double subTotal) {
        this.subTotal = subTotal;
    }
}
