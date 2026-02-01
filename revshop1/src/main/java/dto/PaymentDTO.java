package dto;

public class PaymentDTO {

    private int orderId;
    private String paymentMethod; // COD / UPI / CARD
    private String paymentStatus; // PENDING / SUCCESS
    private double amount;

    public PaymentDTO(int orderId, String paymentMethod, String paymentStatus, double amount) {
        this.orderId = orderId;
        this.paymentMethod = paymentMethod;
        this.paymentStatus = paymentStatus;
        this.amount = amount;
    }

    public PaymentDTO() {

    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
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

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
