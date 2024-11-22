import java.util.Scanner;

public class stock_entry_transaction_view {
	
	public String showPublishers;
	public String showBooks;
    public stock_entry_transaction_view() {
    	showPublishers = "";
    	showBooks = "";
    }

    public int displayMenu() {
        int menuselection = 0;
        Scanner console = new Scanner(System.in);
        stock_entry_transaction set = new stock_entry_transaction();
       
        System.out.println("==============================================");
        System.out.println("      Stock Entry Transaction Menu            ");
        System.out.println("----------------------------------------------");
        System.out.println("[1] Add New Stock Entries");
        System.out.println("[2] Update Current Stock Quantity");
        System.out.println("[3] View Stock Information");
        System.out.println("[4] Delete Stock Entries");
        System.out.println("[0] Exit");
        System.out.println("==============================================");
        System.out.print("Enter your choice: ");
        menuselection = Integer.parseInt(console.nextLine());

        switch (menuselection) {
        case 1:
            // Add New Stock Entry
        	showPublishers = "";
            System.out.print("Do you want to see the existing publisher IDs? (yes/no): ");
            showPublishers = console.nextLine().trim().toLowerCase();
            if (showPublishers.equals("yes")) {
                set.displayPublishers();
            }
            System.out.print("Enter Publisher ID: ");
            set.publisherID = Integer.parseInt(console.nextLine());
            
            System.out.print("Do you want to see the existing book IDs? (yes/no): ");
            showBooks = console.nextLine().trim().toLowerCase();
            if (showBooks.equals("yes")) {
                set.displayBooks();
            }
            System.out.print("Book ID: ");
            set.bookID = Integer.parseInt(console.nextLine());
            System.out.print("Stock Quantity: ");
            set.stockQuantity = Integer.parseInt(console.nextLine());
            set.addStockEntry();
            break;

        case 2:
            // Update Stock Quantity
        	showPublishers = "";
            System.out.print("Do you want to see the existing publisher IDs? (yes/no): ");
            showPublishers = console.nextLine().trim().toLowerCase();
            if (showPublishers.equals("yes")) {
                set.displayPublishers();
            }
            System.out.print("Enter Publisher ID: ");
            set.publisherID = Integer.parseInt(console.nextLine());
            
            System.out.print("Do you want to see the existing book IDs for this Publisher? (yes/no): ");
            showBooks = console.nextLine().trim().toLowerCase();
            if (showBooks.equals("yes")) {
                set.displayBooksByPublisher();
            }
            
            System.out.print("Enter Book ID: ");
            set.bookID = Integer.parseInt(console.nextLine());
            System.out.print("Enter New Stock Quantity: ");
            set.stockQuantity = Integer.parseInt(console.nextLine());
            set.updateStockQuantity();
            break;

        case 3:
            // View Stock Information
        	showPublishers = "";
            System.out.print("Do you want to see the existing publisher IDs? (yes/no): ");
            showPublishers = console.nextLine().trim().toLowerCase();
            if (showPublishers.equals("yes")) {
                set.displayPublishers();
            }
            System.out.print("Enter Publisher ID: ");
            set.publisherID = Integer.parseInt(console.nextLine());
            
            System.out.print("Do you want to see the existing book IDs for this Publisher? (yes/no): ");
            showBooks = console.nextLine().trim().toLowerCase();
            if (showBooks.equals("yes")) {
                set.displayBooksByPublisher();
            }
            System.out.print("Enter Book ID: ");
            set.bookID = Integer.parseInt(console.nextLine());
            set.viewStockInformation();
            break;

        case 4:
            // Delete Stock Entry
        	showPublishers = "";
            System.out.print("Do you want to see the existing publisher IDs? (yes/no): ");
            showPublishers = console.nextLine().trim().toLowerCase();
            if (showPublishers.equals("yes")) {
                set.displayPublishers();
            }
            System.out.print("Enter Publisher ID: ");
            set.publisherID = Integer.parseInt(console.nextLine());
            
            System.out.print("Do you want to see the existing book IDs for this Publisher? (yes/no): ");
            showBooks = console.nextLine().trim().toLowerCase();
            if (showBooks.equals("yes")) {
                set.displayBooksByPublisher();
            }
            System.out.print("Enter Book ID: ");
            set.bookID = Integer.parseInt(console.nextLine());
            set.deleteStockEntry();
            break;

        case 0:
            System.out.println("Exiting Stock Entry Management...");
            break;

        default:
            System.out.println("Invalid selection. Please try again.");
            break;
        }
        return menuselection;
    } 
}
