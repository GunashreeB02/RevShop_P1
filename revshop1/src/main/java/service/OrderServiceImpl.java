package service;

import dto.*;
import repository.OrderRepositoryImpl;
import repository.ReviewsRepository;
import repository.ReviewsRepositoryImpl;

import java.util.List;

public class OrderServiceImpl implements  OrderService{

    private OrderRepositoryImpl orderRepo = new OrderRepositoryImpl();

    @Override
    public boolean placeOrder(int buyerId,
                              double totalAmount,
                              List<OrderItemDTO> items,
                              OrderAddressDTO shipping,
                              OrderAddressDTO billing,
                              PaymentDTO payment) {

        // 1. Create order
        int orderId = orderRepo.createOrder(buyerId, totalAmount);

        if (orderId == 0) {
            System.out.println("Order creation failed");
            return false;
        }

        // 2. Add order items + reduce stock
        for (OrderItemDTO item : items) {
            item.setOrderId(orderId);

            orderRepo.addOrderItem(item);
            orderRepo.reduceStock(item.getProductId(), item.getQuantity());
        }

        // 3. Clear cart
        orderRepo.clearCart(buyerId);



        // 4. Addresses
        shipping.setOrderId(orderId);
        billing.setOrderId(orderId);
        orderRepo.addOrderAddress(shipping);
        orderRepo.addOrderAddress(billing);

        // 5. Payment
        payment.setOrderId(orderId);
        orderRepo.addPayment(payment);


        return true;
    }


    @Override
    public List<OrderDTO> getOrdersByBuyer(int buyerId) {
        return orderRepo.getOrdersByBuyer(buyerId);
    }


    @Override
    public boolean submitReview(ReviewDTO dto) {

        ReviewsRepository repo = new ReviewsRepositoryImpl();

        if (!repo.hasPurchased(dto.getBuyerId(), dto.getProductId())) {
            System.out.println("❌ You can review only purchased products");
            return false;
        }

        return repo.addReview(dto);
    }


    @Override
    public List<SellerOrderDTO> viewOrders(int sellerId) {
        return orderRepo.getOrdersForSeller(sellerId);
    }


}
