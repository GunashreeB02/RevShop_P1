package service;

import dto.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import repository.OrderRepositoryImpl;
import repository.ReviewsRepository;
import repository.ReviewsRepositoryImpl;

import java.util.List;

public class OrderServiceImpl implements  OrderService{

    private static final Logger log =
            LoggerFactory.getLogger(OrderServiceImpl.class);
    private OrderRepositoryImpl orderRepo = new OrderRepositoryImpl();

    @Override
    public boolean placeOrder(int buyerId,
                              double totalAmount,
                              List<OrderItemDTO> items,
                              OrderAddressDTO shipping,
                              OrderAddressDTO billing,
                              PaymentDTO payment) {

        log.info("Buyer {} is placing an order. Total amount: {}", buyerId, totalAmount);

        // 1. Create order
        int orderId = orderRepo.createOrder(buyerId, totalAmount);

        if (orderId == 0) {
            log.error("Order creation failed for buyer {}", buyerId);            return false;
        }
        log.info("Order {} created successfully for buyer {}", orderId, buyerId);


        // 2. Add order items + reduce stock
        for (OrderItemDTO item : items) {
            item.setOrderId(orderId);

            orderRepo.addOrderItem(item);
            orderRepo.reduceStock(item.getProductId(), item.getQuantity());

            log.debug("Added product {} (qty {}) to order {}",
                    item.getProductId(), item.getQuantity(), orderId);
        }

        // 3. Clear cart
        orderRepo.clearCart(buyerId);
        log.debug("Cart cleared for buyer {}", buyerId);



        // 4. Addresses
        shipping.setOrderId(orderId);
        billing.setOrderId(orderId);
        orderRepo.addOrderAddress(shipping);
        orderRepo.addOrderAddress(billing);
        log.debug("Shipping & billing address saved for order {}", orderId);

        // 5. Payment
        payment.setOrderId(orderId);
        orderRepo.addPayment(payment);

        log.info("Payment recorded for order {}", orderId);

        log.info("Order {} placed successfully by buyer {}", orderId, buyerId);



        return true;
    }


    @Override
    public List<OrderDTO> getOrdersByBuyer(int buyerId) {
        return orderRepo.getOrdersByBuyer(buyerId);
    }


    @Override
    public boolean submitReview(ReviewDTO dto) {

        ReviewsRepository repo = new ReviewsRepositoryImpl();

        log.info("Buyer {} attempting to review product {} for order {}",
                dto.getBuyerId(), dto.getProductId(), dto.getOrderId());

        if (!repo.hasPurchased(dto.getBuyerId(), dto.getProductId(),dto.getOrderId())) {
            log.warn("Review denied. Buyer {} has not purchased product {} in order {}",
                    dto.getBuyerId(), dto.getProductId(), dto.getOrderId());            return false;
        }


        boolean saved = repo.addReview(dto);

        if (saved) {
            log.info("Review saved successfully for product {}", dto.getProductId());
        } else {
            log.error("Failed to save review for product {}", dto.getProductId());
        }

        return saved;
    }


    @Override
    public List<SellerOrderDTO> viewOrders(int sellerId) {
        log.debug("Fetching orders for seller {}", sellerId);
        return orderRepo.getOrdersForSeller(sellerId);
    }


}
