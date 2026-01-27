package presentation;

import dto.CartItemsDTO;
import dto.ProductDTO;
import dto.ReviewDTO;
import service.BuyerProductService;
import service.BuyerProductServiceImpl;
import service.RegistrationService;
import service.RegistrationServiceImpl;

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
            System.out.println("9. Change Password");
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
                        return;
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
                            System.out.println("No products found");
                        } else {
                            products.forEach(System.out::println);
                        }
                    break;

//
//                case 4 -> {
//                    System.out.print("Enter keyword: ");
//                    String key = sc.nextLine();
//                    service.searchByKeyword(key).forEach(System.out::println);
//                }

                case 5: addToFavourites(buyerId);
                    break;

                case 6: addProductToCart(buyerId);
                    break;

                case 7: viewFavouritesDetails(buyerId);
                    break;

                case 8: viewCartDetails(buyerId);
                    break;

                case 9: changePassword(buyerId);
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

        sc.nextLine();
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
        service.addToCart(buyerId,productId);

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
                System.out.println("4. Back to main menu ");

                int choice=sc.nextInt();
                if(choice==4)
                    return;

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

}
