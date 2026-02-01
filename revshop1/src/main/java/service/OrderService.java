package service;

import dto.*;

import java.util.List;

public interface OrderService {

    boolean placeOrder(int buyerId, double totalAmount, List<OrderItemDTO> orderItems, OrderAddressDTO shipping, OrderAddressDTO billing, PaymentDTO payment);


    List<OrderDTO> getOrdersByBuyer(int buyerId);

    boolean submitReview(ReviewDTO dto);

    List<SellerOrderDTO> viewOrders(int sellerId);
}
