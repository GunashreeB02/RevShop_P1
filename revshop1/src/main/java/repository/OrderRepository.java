package repository;

import dto.*;

import java.util.List;

public interface OrderRepository {
    int createOrder(int buyerId, double totalAmount);

    void addOrderItem(OrderItemDTO dto);

    void reduceStock(int productId, int qty);

    void clearCart(int buyerId);

    void addOrderAddress(OrderAddressDTO dto);

    void addPayment(PaymentDTO dto);

    List<OrderDTO> getOrdersByBuyer(int buyerId);


    List<SellerOrderDTO> getOrdersForSeller(int sellerId);
}
