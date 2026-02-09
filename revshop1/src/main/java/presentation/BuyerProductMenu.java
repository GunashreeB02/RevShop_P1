package presentation;

import dto.*;
import service.*;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.List;


public class BuyerProductMenu {
    Scanner sc = new Scanner(System.in);

    public void buyerMenu(int buyerId)
    {


        BuyerProductService service = new BuyerProductServiceImpl();

        while (true) {
            System.out.println("\n===== BUYER MENU =====");
            System.out.println("1. View All Products");
            System.out.println("2. View Product Details");
            System.out.println("3. Browse Products by Category");
            System.out.println("4. Search Product");
            System.out.println("5. Add to Favourites");
            System.out.println("6. Add to Cart");
            System.out.println("7. View Favourites");
            System.out.println("8. View Cart");
            System.out.println("9. View Order History");
            System.out.println("10. Review and Rate Product");
            System.out.println("11. Change Password");
            System.out.println("0. Logout");
            int choice = sc.nextInt();
            sc.nextLine();


            switch (choice) {

                case 1 : List<ProductDTO> dtoList=service.viewAllProducts();
                    System.out.println("ID | Name | Price | Stock | Manufacturer");
                    System.out.println("-------------------------------------------");

                    for (ProductDTO dt : dtoList) {
                        System.out.println(
                                dt.getProductId() + " | " +
                                        dt.getProductName() + " | ₹" +
                                        dt.getSellingPrice() + " | " +
                                        dt.getStock() + " | " +
                                        dt.getManufacturer() + " | " +
                                        dt.getCategoryName()
                        );
                    } break;


                case 2 :
                    System.out.print("Enter Product ID: ");
                    int productId = sc.nextInt();
                    ProductDTO dto = service.viewProductDetails(productId);

                    if (dto == null) {
                        System.out.println("Product not found");
                        break;
                    }

                    System.out.println("\nProduct Details");
                    System.out.println("------------------------");
                    System.out.println("Name : " + dto.getProductName());
                    System.out.println("Category : " + dto.getCategoryName());
                    System.out.println("Manufacturer : " + dto.getManufacturer());
                    System.out.println("Mrp : "+ dto.getMrp()) ;
                    System.out.println("Price : ₹" + dto.getSellingPrice());
                    System.out.println("Stock : " + dto.getStock());
                    System.out.println("Description : " + dto.getDescription());

                    System.out.println("\nReviews:");
                    if (dto.getReviews().isEmpty()) {
                        System.out.println("No reviews yet.");
                    } else {
                        for (ReviewDTO r : dto.getReviews()) {
                            System.out.println("⭐ " + r.getRating() + " - " + r.getReviewComment());
                        }
                    }
                  break;

                case 3 :
                        System.out.println("Enter category name:");
                        String category = sc.nextLine();

                        List<ProductDTO> products = service.searchByCategoryName(category);
                        if (products.isEmpty()) {
                            System.out.println("No products found in this category");
                        } else {
                            products.forEach(System.out::println);
                        }
                    break;


                case 4 :
                    System.out.print("Enter keyword: ");
                    String key = sc.nextLine();

                    List<ProductDTO> results = service.searchByKeyword(key);

                    if (results.isEmpty()) {
                        System.out.println("No products found for keyword: " + key);
                    } else {
                        System.out.println("Search Results:");
                        System.out.println("ID | Name | Category | Price | Stock");

                        for (ProductDTO p : results) {
                            System.out.println(
                                    p.getProductId() + " | " +
                                            p.getProductName() + " | " +
                                            p.getCategoryName() + " | ₹" +
                                            p.getSellingPrice() + " | " +
                                            p.getStock()
                            );
                        }
                    }
                    break;


                    case 5: addToFavourites(buyerId);
                    break;

                case 6: addProductToCart(buyerId);
                    break;

                case 7: viewFavouritesDetails(buyerId);
                    break;

                case 8: viewCartDetails(buyerId);
                    break;

                case 9: viewOrderHistory(buyerId);
                    break;

                case 10: rateProduct(buyerId);
                    break;

                case 11: changePassword(buyerId);
                break;

                case 0 : {
                    System.out.println("Thank you!");
                    return;
                }
            }
        }

    }




    private void changePassword(int buyerId) {

        RegistrationService service=new RegistrationServiceImpl();

        System.out.println("Current Password:");
        String currentPassword= sc.nextLine();
        System.out.println("New Password:");
        String newPassword=sc.nextLine();
        System.out.println("Confirm Password:");
        String confirmPassword=sc.nextLine();
        service.setNewPassword(buyerId,currentPassword,newPassword,confirmPassword);
    }


    public void addToFavourites(int buyerId)
    {

        System.out.println("Enter the product id");
        int productId=sc.nextInt();
        BuyerProductService service=new BuyerProductServiceImpl();
        boolean isAdded=service.addToFavourites(buyerId,productId);
        if(isAdded)
            System.out.println("Added to favourites");
        else
            System.out.println("Failed to add");

    }

    public void addProductToCart(int buyerId)
    {
        System.out.println("Enter the product id");
        int productId=sc.nextInt();
        BuyerProductService service=new BuyerProductServiceImpl();
        if(service.addToCart(buyerId,productId))
        {
            System.out.println("Product Added to Cart Successfully");
        }
        else
        {
            System.out.println("Failed to add to cart");
        }

    }


    public void viewCartDetails(int buyerId)
    {
        BuyerProductService cartService = new BuyerProductServiceImpl();
        List<CartItemsDTO> cart = cartService.viewCart(buyerId);
        double cartSubtotal = 0;


        if (cart.isEmpty()) {
            System.out.println("Your cart is empty!");
        } else {
            System.out.println("YOUR CART");
            System.out.printf("%-10s %-20s %-20s %-10s %-10s %-10s%n", "ProductID", "Name","Manufacturer", "Quantity", "Price", "Item Total");
            for (CartItemsDTO item : cart) {
                System.out.printf("%-10d %-20s %-20s %-10d %-10.2f %-10.2f%n",
                        item.getProductId(),
                        item.getProductName(),
                        item.getManufacturer(),
                        item.getQuantity(),
                        item.getPrice(),
                item.getSubTotal());
                cartSubtotal += item.getSubTotal();
            }
            System.out.println("Sub Total : "+ cartSubtotal);

            while (true)
            {
                System.out.println("options:");
                System.out.println("1. Increase Quantity ");
                System.out.println("2. Decrease Quantity ");
                System.out.println("3. Remove Product ");
                System.out.println("4. Checkout");
                System.out.println("5. Back to main menu ");

                int choice=sc.nextInt();
                if(choice==5)
                    return;

                if(choice==4) {
                    checkout(buyerId);
                    return;
                }

                System.out.print("Enter Product ID: ");
                int productId = sc.nextInt();

                switch (choice)
                {

                    case 1:
                        cartService.increaseQuantity(buyerId,productId);
                        break;

                    case 2:  cartService.decreaseQuantity(buyerId,productId);
                        break;

                    case 3: cartService.removeItem(buyerId,productId);
                        break;






                }
            }


        }
    }


    public void checkout(int buyerId) {

        ProductService productService=new ProductServiceImpl();
        BuyerProductService cartService = new BuyerProductServiceImpl();
        List<CartItemsDTO> cartItems = cartService.viewCart(buyerId);

        if (cartItems.isEmpty()) {
            System.out.println("Cart is empty. Cannot checkout.");
            return;
        }

        double totalAmount = 0;

        System.out.println("\nORDER SUMMARY");
        System.out.println("--------------------------------");

        for (CartItemsDTO item : cartItems) {
            double subTotal = item.getPrice() * item.getQuantity();
            totalAmount += subTotal;

            System.out.println(item.getProductName() +
                    " | Qty: " + item.getQuantity() +
                    " | ₹" + subTotal);
        }

        System.out.println("--------------------------------");
        System.out.println("Total Amount: ₹" + totalAmount);

        System.out.print("\nConfirm Order? (yes/no): ");
        String confirm = sc.next();

        sc.nextLine();
        if (!confirm.equalsIgnoreCase("yes")) {
            System.out.println("Checkout cancelled.");
            return;
        }
        // ---------------- SHIPPING ADDRESS ----------------
        System.out.println("\nENTER SHIPPING DETAILS");

        OrderAddressDTO shipping = new OrderAddressDTO();
        shipping.setAddressType("SHIPPING");

        System.out.print("Full Name: ");
        shipping.setFullName(sc.nextLine());

        System.out.print("Phone: ");
        shipping.setPhone(sc.nextLine());

        System.out.print("Address Line 1: ");
        shipping.setAddressLine1(sc.nextLine());

        System.out.print("Address Line 2: ");
        shipping.setAddressLine2(sc.nextLine());

        System.out.print("City: ");
        shipping.setCity(sc.nextLine());

        System.out.print("State: ");
        shipping.setState(sc.nextLine());

        System.out.print("Pincode: ");
        shipping.setPincode(sc.nextLine());

        // ---------------- BILLING ADDRESS ----------------
        System.out.print("\nBilling address same as shipping? (yes/no): ");
        String same = sc.nextLine();

        OrderAddressDTO billing = new OrderAddressDTO();
        billing.setAddressType("BILLING");

        if (same.equalsIgnoreCase("yes")) {
            billing = new OrderAddressDTO();
            billing.setOrderId(shipping.getOrderId());
            billing.setFullName(shipping.getFullName());
            billing.setPhone(shipping.getPhone());
            billing.setAddressLine1(shipping.getAddressLine1());
            billing.setAddressLine2(shipping.getAddressLine2());
            billing.setCity(shipping.getCity());
            billing.setState(shipping.getState());
            billing.setPincode(shipping.getPincode());
            billing.setAddressType("BILLING");
        } else {
            System.out.println("\nENTER BILLING DETAILS");
            sc.nextLine();
            System.out.print("Full Name: ");
            billing.setFullName(sc.nextLine());

            System.out.print("Phone: ");
            billing.setPhone(sc.nextLine());

            System.out.print("Address Line 1: ");
            billing.setAddressLine1(sc.nextLine());

            System.out.print("Address Line 2: ");
            billing.setAddressLine2(sc.nextLine());

            System.out.print("City: ");
            billing.setCity(sc.nextLine());

            System.out.print("State: ");
            billing.setState(sc.nextLine());

            System.out.print("Pincode: ");
            billing.setPincode(sc.nextLine());
        }

        // ---------------- PAYMENT ----------------
        System.out.println("\nSelect Payment Method:");
        System.out.println("1. Cash on Delivery");
        System.out.println("2. UPI");
        System.out.println("3. Card");
        System.out.println("0. Payment Cancel");

        int payChoice = sc.nextInt();
        sc.nextLine();
        if (payChoice == 0) {
            System.out.println("Payment cancelled. Returning to cart...");
            return;
        }

        PaymentDTO payment = new PaymentDTO();
        payment.setAmount(totalAmount);

        switch (payChoice) {
            case 1:
                payment.setPaymentMethod("COD");
                payment.setPaymentStatus("PENDING");
                break;

            case 2:
                payment.setPaymentMethod("UPI");
                payment.setPaymentStatus("SUCCESS"); // assume success
                break;

            case 3:
                payment.setPaymentMethod("CARD");
                payment.setPaymentStatus("SUCCESS");
                break;

            default:
                System.out.println("Invalid choice");
                return;
        }

        // Convert cart → order items
        List<OrderItemDTO> orderItems = new ArrayList<>();

        for (CartItemsDTO cart : cartItems) {
            OrderItemDTO item = new OrderItemDTO();
            item.setProductId(cart.getProductId());
            item.setQuantity(cart.getQuantity());
            item.setPrice(cart.getPrice());

            // fetch seller_id from DB using product_id
            int sellerId = productService.getSellerIdByProductId(cart.getProductId());
            item.setSellerId(sellerId);

            orderItems.add(item);
        }

        OrderService orderService = new OrderServiceImpl();

        boolean success = orderService.placeOrder(
                buyerId,
                totalAmount,
                orderItems,
                shipping,
                billing,
                payment
        );
        if (success) {

            System.out.println("\n Order placed successfully!");
        } else {
            System.out.println("\n Order failed!");
        }
    }





    public  void viewFavouritesDetails(int buyerId)
    {

        BuyerProductService service=new BuyerProductServiceImpl();
        List<ProductDTO> favs = service.viewFavourites(buyerId);

        if (favs.isEmpty()) {
            System.out.println("No favourites yet");
        } else {
            System.out.println("YOUR FAVOURITES");
            System.out.println("ID | Name | Manufacturer | Price | Stock");
            System.out.println("----------------------------------------");

            for (ProductDTO p : favs) {
                System.out.println(
                        p.getProductId() + " | " +
                                p.getProductName() + " | " +
                                p.getManufacturer() + " | ₹" +
                                p.getSellingPrice() + " | " +
                                p.getStock()
                );
            }
        }

    }


    public void viewOrderHistory(int buyerId) {

        OrderService orderService = new OrderServiceImpl();
        List<OrderDTO> orders = orderService.getOrdersByBuyer(buyerId);

        if (orders.isEmpty()) {
            System.out.println("No orders found.");
            return;
        }

        System.out.println("\n========= ORDER HISTORY =========");

        for (OrderDTO order : orders) {

            System.out.println("Order ID: " + order.getOrderId());
            System.out.println("Date: " + order.getOrderDate());
            System.out.println("Total Amount: ₹" + order.getTotalAmount());
            System.out.println("Order Status: " + order.getStatus());

            System.out.println("Items:");
            for (OrderItemDTO item : order.getItems()) {
                System.out.println("  - " + item.getProductName() +
                        " | Qty: " + item.getQuantity() +
                        " | ₹" + item.getPrice());
            }

            System.out.println("Shipping Address: " +
                    (order.getShippingAddress() != null ?
                            order.getShippingAddress().getAddressLine1() : "N/A"));

            System.out.println("Billing Address: " +
                    (order.getBillingAddress() != null ?
                            order.getBillingAddress().getAddressLine1() : "N/A"));

            System.out.println("Payment Method: " + order.getPaymentMethod());
            System.out.println("Payment Status: " + order.getPaymentStatus());
            System.out.println("--------------------------------");
        }
    }


    public void rateProduct(int buyerId) {

        System.out.print("Enter Order ID: ");
        int orderId = sc.nextInt();

        System.out.print("Enter Product ID: ");
        int productId = sc.nextInt();
        sc.nextLine();

        System.out.print("Rating (1-5): ");
        int rating = sc.nextInt();
        sc.nextLine();

        System.out.print("Review: ");
        String text = sc.nextLine();

        ReviewDTO dto = new ReviewDTO();
        dto.setBuyerId(buyerId);
        dto.setProductId(productId);
        dto.setOrderId(orderId);
        dto.setRating(rating);
        dto.setReviewComment(text);

        OrderService service = new OrderServiceImpl();
        boolean success = service.submitReview(dto);

        if (success) {
            System.out.println("✅ Review submitted");
        }
    }




}
