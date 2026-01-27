package dto;

public class CartItemsDTO {

    private int cartId;
    private int buyerId;
    private int productId;

    private String productName;
    private String manufacturer;
    private double price;

    private int quantity;
    private double itemTotal;
    private double subTotal;

    public CartItemsDTO()
    {

    }

    public CartItemsDTO(int cartId, int buyerId, int productId, String productName, String manufacturer, double price, int quantity, double subTotal) {
        this.cartId = cartId;
        this.buyerId = buyerId;
        this.productId = productId;
        this.productName = productName;
        this.manufacturer = manufacturer;
        this.price = price;
        this.quantity = quantity;
        this.subTotal = subTotal;
    }

    public CartItemsDTO(int productId, String productName, String manufacturer, int quantity, double price) {

        this.productId = productId;
        this.productName = productName;
        this.manufacturer = manufacturer;
        this.price = price;
        this.quantity = quantity;
    }

    public int getCartId() {
        return cartId;
    }

    public void setCartId(int cartId) {
        this.cartId = cartId;
    }

    public int getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(int buyerId) {
        this.buyerId = buyerId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(double subTotal) {
        this.subTotal = subTotal;
    }
}
