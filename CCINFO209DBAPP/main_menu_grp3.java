import java.util.Scanner;

public class main_menu {

    public main_menu() {
    }

    public int menu() {
        int menuselection = 0;
        Scanner console = new Scanner(System.in);

        while (true) {
            try {
                System.out.println("\n\n=======================================================");
                System.out.println("               Application Main Menu");
                System.out.println("-------------------------------------------------------");
                System.out.println("[1] Book Management");
                System.out.println("[2] Book Sales");
                System.out.println("[3] Book Report");
                System.out.println("[0] Exit");
                System.out.println("=======================================================");

                System.out.print("Enter Selected Function: ");
                menuselection = Integer.parseInt(console.nextLine());

                switch (menuselection) {
                    case 1:
                        System.out.println("\n--- Book Management ---");
                        book_record_management_menu brm = new book_record_management_menu();
                        while (brm.menu() != 5) {
                        }
                        break;

                    case 2:
                        System.out.println("\n--- Book Sales ---");
                        sales_of_books_menu salesMenu = new sales_of_books_menu();
                        while (salesMenu.menu() != 4) {
                        }
                        break;

                    case 3:
                        System.out.println("\n--- Book Report ---");
                        book_activity_report report = new book_activity_report();
                        System.out.print("Enter Year: ");
                        report.year = Integer.parseInt(console.nextLine());
                        System.out.print("Enter Month (1-12): ");
                        report.month = Integer.parseInt(console.nextLine());
                        report.generate_bookact_report();
                      
                        break;
                        
                    case 4:
                    	author_report ar = new author_report();
                    	System.out.print("Enter Year: ");
                        ar.year = Integer.parseInt(console.nextLine());
                        System.out.print("Enter Month (1-12): ");
                        ar.month = Integer.parseInt(console.nextLine());
                    	ar.generate_authorreport();

                    case 0:
                        System.out.println("\nExiting the application. Goodbye!");
                        console.close();
                        return 0;

                    default:
                        System.out.println("Invalid selection. Please try again.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: Please enter a valid number.");
            }
        }
    }

    public static void main(String args[]) {
        main_menu mm = new main_menu();
        mm.menu();
    }
}
