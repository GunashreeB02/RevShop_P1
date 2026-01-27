package presentation;

import java.util.Scanner;

public class RevShopRunner {
    public static void main(String[] args)  {

        int choice;
        Scanner scanner=new Scanner(System.in);
        BuyerSellerregistration register=new BuyerSellerregistration();

        while(true) {

            System.out.println("WELCOME TO REVSHOP");
            System.out.println("==========================");
            System.out.println("1. Register as Buyer");
            System.out.println("2. Register as Seller");
            System.out.println("3. Login");
            System.out.println("4. Forgot Password");
            System.out.println("==========================");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    register.registerBuyer();
                    break;
                case 2:
                    register.registerSeller();
                    break;
                case 3:
                    register.login();
                    break;
                case 4:
                    register.forgotPassword();

            }

        }
    }
}