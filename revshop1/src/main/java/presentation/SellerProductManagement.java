package presentation;

import dto.*;
import repository.NotifyRepository;
import repository.NotifyRepositoryImpl;
import service.*;

import java.util.List;
import java.util.Scanner;

public class SellerProductManagement {

    Scanner sc = new Scanner(System.in);

    public void showSellerMenu(int sellerId)
    {
        int choice;

        while (true) {
            System.out.println("\n--- SELLER DASHBOARD ---");
            System.out.println("1. Add Product");
            System.out.println("2. Update Product");
            System.out.println("3. View My Products");
            System.out.println("4. Delete Product");
            System.out.println("5. View Orders for your Products");
            System.out.println("6. View Notifications");
            System.out.println("7. View Reviews and Ratings");
            System.out.println("8. Change Password");
            System.out.println("9. Logout");

            choice = sc.nextInt();

            switch (choice) {
                case 1:
                    addProduct(sellerId);
                    break;
                case 2:
                    updateProduct(sellerId);
                    break;
                case 3:
                    viewProducts(sellerId);
                    break;
                case 4:
                    deleteProduct(sellerId);
                    break;

                case 5: viewOrdersPlaced(sellerId);
                       break;

                case 6: viewnotifications(sellerId);
                break;

                case 7: viewRatingsReview(sellerId);
                break;
                case 8:
                    changePassword(sellerId);
                    break;
                case 9:
                    return;
                default:
                    System.out.println("Invalid choice");
            }
        }
    }

    private void viewRatingsReview(int sellerId) {

        ProductService service = new ProductServiceImpl();
        List<ReviewDTO> reviews = service.getReviewsAtSeller(sellerId);

        if (reviews.isEmpty()) {
            System.out.println("No reviews found.");
            return;
        }

        System.out.println("========= PRODUCT REVIEWS =========");

        for (ReviewDTO r : reviews) {
            System.out.println("Product : " + r.getProductName());
            System.out.println("Buyer   : " + r.getBuyerName());
            System.out.println("Rating  : " + "⭐".repeat(r.getRating()));
            System.out.println("Review  : " + r.getReviewComment());
            System.out.println("Date    : " + r.getReviewDate());
            System.out.println("----------------------------------");
        }
    }

    private void viewnotifications(int sellerId) {

 Scanner scanner = new Scanner(System.in);

        NotifyRepository notifyRepo = new NotifyRepositoryImpl();

        List<NotificationDTO> notifications =
                notifyRepo.getUnreadNotifications(sellerId);

        if (notifications.isEmpty()) {
            System.out.println("🔔 No new notifications");
            return;
        }

        System.out.println("\n--- 🔔 Notifications ---");

        for (NotificationDTO n : notifications) {
            System.out.println(
                    "[" + n.getNotificationId() + "] " +
                            n.getMessage() + " (" + n.getCreatedAt() + ")"
            );
        }

        System.out.print("\nEnter notification ID to mark as read (0 to skip): ");
        int nid = scanner.nextInt();

        if (nid != 0) {
            notifyRepo.markAsRead(nid);
            System.out.println("✅ Notification marked as read");
        }


    }

    private void viewOrdersPlaced(int sellerId) {
        OrderService service=new OrderServiceImpl();

        System.out.println("===== ORDERS FOR YOUR PRODUCTS =====");

        List<SellerOrderDTO> orders = service.viewOrders(sellerId);

        if (orders.isEmpty()) {
            System.out.println("No orders placed yet.");
        } else {
            for (SellerOrderDTO o : orders) {
                System.out.println("--------------------------------");
                System.out.println("Order ID     : " + o.getOrderId());
                System.out.println("Buyer        : " + o.getBuyerName());
                System.out.println("Product      : " + o.getProductName());
                System.out.println("Quantity     : " + o.getQuantity());
                System.out.println("Price        : ₹" + o.getPrice());
                System.out.println("Status       : " + o.getOrderStatus());
                System.out.println("Order Date   : " + o.getOrderDate());
            }
        }

    }

    private void changePassword(int sellerId) {

        RegistrationService service=new RegistrationServiceImpl();

        sc.nextLine();
        System.out.println("Current Password:");
        String currentPassword= sc.nextLine();
        System.out.println("New Password:");
        String newPassword=sc.nextLine();
        System.out.println("Confirm Password:");
        String confirmPassword=sc.nextLine();
        service.setNewPassword(sellerId,currentPassword,newPassword,confirmPassword);

    }

    public void addProduct(int sellerId)
    {
        ProductService service=new ProductServiceImpl();
        viewCategories();

        ProductDTO dto = new ProductDTO();

        System.out.println("\n--- ADD PRODUCT ---");
        sc.nextLine();
        dto.setSellerId(sellerId);

        System.out.print("Product Name: ");
        dto.setProductName(sc.nextLine());

        System.out.print("Description: ");
        dto.setDescription(sc.nextLine());



        System.out.print("Manufacturer: ");
        dto.setManufacturer(sc.nextLine());

        System.out.print("MRP: ");
        dto.setMrp(sc.nextDouble());

        System.out.print("Selling Price: ");
        dto.setSellingPrice(sc.nextDouble());

        System.out.print("Stock: ");
        dto.setStock(sc.nextInt());

        System.out.print("Stock Threshold: ");
        dto.setStockThreshold(sc.nextInt());

        System.out.print("Category Id: ");
        dto.setCategoryId(sc.nextInt());

        boolean result = service.addProduct(dto);

        System.out.println(
                result ? "Product added successfully"
                        : "Failed to add product"
        );
    }

    public void updateProduct(int sellerId)
    {
        System.out.println("========================================================================================================================================");
        ProductService service=new ProductServiceImpl();
        List<ProductDTO> products= service.getAllProducts(sellerId);

        if (products.isEmpty()) {
            System.out.println("No products found.");
            return;
        }

        System.out.println("\n----------------------------------------------------------");
        System.out.println("ID | Product Name | MRP | Discounted Price | Stock | Stock Threshold" );
        System.out.println("----------------------------------------------------------");

        for (ProductDTO p : products) {
            System.out.printf("%d | %s | %.2f | %.2f | %d | %d%n",
                    p.getProductId(),
                    p.getProductName(),
                    p.getMrp(),
                    p.getSellingPrice(),
                    p.getStock(),
                    p.getStockThreshold());
        }

        System.out.println("----------------------------------------------------------");
        System.out.println("Enter product id");
        int productId = sc.nextInt();
        System.out.println("Enter new discounted price");
        double discountedPrice = sc.nextDouble();
        System.out.println("Enter Mrp");
        double mrp = sc.nextDouble();

        System.out.println("Enter new stock quantity");
        int stockQuantity = sc.nextInt();
        System.out.println("Enter new stock threshold");
        int stockThreshold = sc.nextInt();
        if(discountedPrice>mrp)
        {
            System.out.println("Selling price/ Discounted price must be lesser than mrp");
            if(stockQuantity<stockThreshold)
            {
                System.out.println("Stock must be greater than Threshold");
                return;
            }
            return ;
        }
        boolean updated=service.updateProductDetailsById(productId,sellerId,mrp,discountedPrice,stockQuantity,stockThreshold);

        if(updated)
            System.out.println("updated successfully");
        else
            System.out.println("updation failed");



    }

    public void deleteProduct(int sellerId)
    {
        viewProducts(sellerId);
        System.out.print("\nEnter Product ID to delete: ");
        int productId = sc.nextInt();
        sc.nextLine();

        System.out.print("Are you sure you want to delete this product? (Y/N): ");
        String confirm = sc.nextLine();

        if (!confirm.equalsIgnoreCase("Y")) {
            System.out.println("Delete operation cancelled.");
            return;
        }
        ProductService productService=new ProductServiceImpl();
        boolean deleted = productService.deleteProductById(productId);

        if (deleted) {
            System.out.println("Product deleted successfully.");
        } else {
            System.out.println("Delete failed. Product not found or not owned by you.");
        }



    }

    public void viewProducts(int sellerId)
    {
        Scanner sc=new Scanner(System.in);
        System.out.println("========================================================================================================================================");
        ProductService service=new ProductServiceImpl();
        List<ProductDTO> products= service.getAllProducts(sellerId);

        if (products.isEmpty()) {
            System.out.println("No products found.");
            return;
        }

        System.out.println("\n----------------------------------------------------------");
        System.out.println("ID | Product Name | MRP | Discounted Price | Stock | Stock Threshold" );
        System.out.println("----------------------------------------------------------");

        for (ProductDTO p : products) {
            System.out.printf("%d | %s | %.2f | %.2f | %d | %d%n",
                    p.getProductId(),
                    p.getProductName(),
                    p.getMrp(),
                    p.getSellingPrice(),
                    p.getStock(),
                    p.getStockThreshold());
        }

        System.out.println("----------------------------------------------------------");

    }

    public void viewCategories()
    {
        ProductService categoryService = new ProductServiceImpl();
        List<CategoryDTO> categories = categoryService.getAllCategories();

        System.out.println("\nAvailable Categories:");
        System.out.println("---------------------");

        if (categories.isEmpty()) {
            System.out.println("No categories found.");
        } else {
            for (CategoryDTO dto : categories) {
                System.out.println(
                        dto.getCategoryId() + " - " +
                                dto.getCategoryName()
                );
            }
        }
    }

}


