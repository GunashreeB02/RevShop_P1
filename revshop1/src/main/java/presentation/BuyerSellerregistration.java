package presentation;

import dto.BuyerDTO;
import dto.SecurityQuestionDTO;
import dto.SellerDTO;
import service.RegistrationService;
import service.RegistrationServiceImpl;

import java.time.LocalDate;
import java.util.Scanner;

public class BuyerSellerregistration {

    Scanner scan=new Scanner(System.in);

    public void registerBuyer()
    {
        RegistrationService service=new RegistrationServiceImpl();



        BuyerDTO dto = new BuyerDTO();

        System.out.println("--- Buyer Registration ---");
        System.out.println("Choose a security question:");
        for (SecurityQuestionDTO q : service.getAllQuestions()) {
            System.out.println(q.getQuestionId() + ". " + q.getQuestionText());
        }

        System.out.print("Email: ");
        dto.setEmail(scan.nextLine());

        System.out.print("Password: ");
        dto.setPassword(scan.nextLine());

        System.out.print("Full Name: ");
        dto.setFullName(scan.nextLine());

        System.out.print("Gender: ");
        dto.setGender(scan.nextLine());

        System.out.print("DOB (yyyy-mm-dd): ");
        dto.setDob(LocalDate.parse(scan.nextLine()));

        System.out.print("Phone: ");
        dto.setPhone(scan.nextLine());

        System.out.println("Enter Security Question Number: ");
        dto.setSecurityQuestionId(scan.nextInt());
        scan.nextLine();

        System.out.println("Enter Answer");
        dto.setSecurityAnswer(scan.nextLine());

        boolean result = service.registerBuyer(dto);

        System.out.println(result
                ? "Buyer registered successfully!"
                : "Buyer registration failed!");
    }

    public void registerSeller()
    {
        RegistrationService service=new RegistrationServiceImpl();

        SellerDTO dto = new SellerDTO();
        System.out.println("--- Seller Registration ---");
        System.out.println("Choose a security question:");
        for (SecurityQuestionDTO q : service.getAllQuestions()) {
            System.out.println(q.getQuestionId() + ". " + q.getQuestionText());
        }

        System.out.print("Email: ");
        dto.setEmail(scan.nextLine());

        System.out.print("Password: ");
        dto.setPassword(scan.nextLine());

        System.out.print("Business Name: ");
        dto.setBusinessName(scan.nextLine());

        System.out.print("GST Number: ");
        dto.setGstNumber(scan.nextLine());

        System.out.print("address: ");
        dto.setAddress(scan.nextLine());

        System.out.print("Phone: ");
        dto.setPhone(scan.nextLine());

        System.out.println("Enter Security Question Number: ");
        dto.setSecurityQuestionId(scan.nextInt());
        scan.nextLine();


        System.out.println("Enter Answer");
        dto.setSecurityAnswer(scan.nextLine());

        boolean result = service.registerSeller(dto);

        System.out.println(result
                ? "Seller registered successfully!"
                : "Seller registration failed!");
    }

    public void login()
    {

        System.out.println("--- Login ---");

        System.out.print("Email: ");
        String email = scan.nextLine();

        System.out.print("Password: ");
        String password = scan.nextLine();

        RegistrationService service=new RegistrationServiceImpl();
        String name = service.login(email,password);

        if(name.equals("Email does not exist"))
        {
            System.out.println("Email does not exist");
        }
        else if(name.equals("Incorrect Password"))
        {
            System.out.println("Password is Incorrect");
        }
        else
        {
            String[] role = name.split(",");
            System.out.println("Welcome "+role[1]  +" "+role[0]);
            if(role[0].equals("SELLER"))
            {
                SellerProductManagement productManagement=new SellerProductManagement();
                productManagement.showSellerMenu(Integer.parseInt(role[2]));
            }

            else {

                    BuyerProductMenu menu=new BuyerProductMenu();
                    menu.buyerMenu(Integer.parseInt(role[2]));
            }

        }

    }

    public void forgotPassword() {

        Scanner scan = new Scanner(System.in);
        RegistrationService service = new RegistrationServiceImpl();

        System.out.print("Enter your registered email: ");
        String email = scan.nextLine();

        String question = service.getQuestionByEmail(email);

        if (question == null) {
            System.out.println("Email not found!");
            return;
        }

        System.out.println("Security Question: " + question);
        System.out.print("Enter your answer: ");
        String answer = scan.nextLine();

        if (service.verifyAnswer(email, answer)) {
            System.out.print("Enter new password: ");
            String newPassword = scan.nextLine();

            if (service.resetPassword(email, newPassword)) {
                System.out.println("Password reset successfully");
            } else {
                System.out.println("Failed to reset password");
            }
        } else {
            System.out.println("Wrong answer");
        }


    }
}
