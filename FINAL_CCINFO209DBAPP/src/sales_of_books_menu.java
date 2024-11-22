import java.util.Scanner;

public class sales_of_books_menu {

    public sales_of_books_menu() {
    }

    public int menu() {
        int menuSelection = 0;
        Scanner sc = new Scanner(System.in);

        while (menuSelection != 4) {
            System.out.println(" ");
            System.out.println(" ");
            System.out.println("=======================================================");
            System.out.println("    Order Records Menu");
            System.out.println("-------------------------------------------------------");
            System.out.println("[1] Create Purchase Record");
            System.out.println("[2] View Purchase Record");
            System.out.println("[3] Update Status of Order");
            System.out.println("[4] Exit Orders Management");
            System.out.println("=======================================================");

            System.out.print("Enter Selected Function: ");
            menuSelection = Integer.parseInt(sc.nextLine());

            sales_of_books sb = new sales_of_books();

            switch (menuSelection) {
                case 1:
                	System.out.println("Would you like to see existing Book IDs? (y/n): ");
                    if (sc.nextLine().equalsIgnoreCase("y")) {
                        sb.displayBooksPublishersAndStock();
                    }
            	
                	System.out.println("Would you like to see existing Bookstore IDs? (y/n): ");
                	if (sc.nextLine().equalsIgnoreCase("y")) {
                	    System.out.println(" ");
                	    System.out.println(" ");
                	    sb.displayExistingBookstoreIDs();
                	}
                   
                    System.out.println(" ");
                    System.out.println(" ");
                    System.out.println("Enter Purchase Information:");
                    System.out.print("BookID: ");
                    sb.bookID = Integer.parseInt(sc.nextLine());
                    System.out.print("PublisherID: ");
                    sb.publisherID = Integer.parseInt(sc.nextLine());
                    System.out.print("BookstoreID: ");
                    sb.bookstoreID = sc.nextLine();
                    System.out.print("Quantity: ");
                    sb.quantity_ordered = Integer.parseInt(sc.nextLine());
                    System.out.print("Remarks: ");
                    sb.remarks = sc.nextLine();

                    if (sb.check_book(sb.bookID) && sb.check_bookstore(sb.bookstoreID) && sb.check_publisher(sb.publisherID)) {
                        sb.add_orderRecord();
                    } else {
                        System.out.println("BookID/PublisherID/BookstoreID does not exist. Purchase Order addition aborted.");
                    }
                    break;

                case 2:
                    System.out.println(" ");
                    System.out.println("=======================================================");
                    System.out.println("    Please Select Information");
                    System.out.println("-------------------------------------------------------");
                    System.out.println("[1] View Book Purchase");
                    System.out.println("[2] View Publisher Purchase");
                    System.out.println("=======================================================");
                    System.out.print("Enter Selected Function: ");
                    int viewSelection = Integer.parseInt(sc.nextLine());

                    switch (viewSelection) {
                        case 1:
                            System.out.println("Would you like to see existing Book IDs? (y/n): ");
                            if (sc.nextLine().equalsIgnoreCase("y")) {
                            	sb.displayExistingBookIDs();
                                System.out.println(" ");
                                System.out.println(" ");

                            }
                            
                           
                            System.out.println(" ");
                            System.out.println(" ");

                           
                            System.out.print("Enter BookID: ");
                            sb.bookID = Integer.parseInt(sc.nextLine());
                            if (sb.check_book(sb.bookID)) {
                                sb.viewOrdersByBook(sb.bookID);
                            } else {
                                System.out.println("BookID does not exist.");
                            }
                            break;

                        case 2:
                            System.out.println("Would you like to see existing Publisher IDs? (y/n): ");
                            if (sc.nextLine().equalsIgnoreCase("y")) {
                                sb.displayExistingPublisherIDs();
                            }
                            System.out.println(" ");
                            System.out.println(" ");
                            System.out.print("Enter PublisherID: ");
                            sb.publisherID = Integer.parseInt(sc.nextLine());
                            if (sb.check_publisher(sb.publisherID)) {
                                sb.viewOrdersByPublisher(sb.publisherID);
                            } else {
                                System.out.println("PublisherID does not exist.");
                            }
                            break;

                        default:
                            System.out.println("Invalid Selection");
                    }
                    break;

                case 3:
                    System.out.println("Would you like to see existing Order Numbers? (y/n): ");
                    if (sc.nextLine().equalsIgnoreCase("y")) {
                        sb.displayExistingOrderNumbers();
                    }

                    System.out.print("Enter Order Number: ");
                    sb.order_number = sc.nextLine();
                    System.out.print("Enter New Status: ");
                    sb.status = sc.nextLine();

                    sb.updateOrderStatus(sb.order_number, sb.status);
                    break;

                case 4:
                    System.out.println("Exiting Orders Management...");
                    break;

                default:
                    System.out.println("Invalid Selection");
            }
        }

        return menuSelection;
    }
}
