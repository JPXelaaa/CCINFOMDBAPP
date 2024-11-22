import java.util.Scanner;

public class sales_of_books_menu {
	
	
	public sales_of_books_menu() {
		
	}
	
	
	public int menu() {
		int menuSelection = 0;
        Scanner sc = new Scanner(System.in);
        
        
        while(menuSelection != 4) {
        	
        	System.out.println(" ");
            System.out.println(" ");
            System.out.println("=======================================================");
            System.out.println("    Order Records Menu						   ");
            System.out.println("-------------------------------------------------------");
            System.out.println("[1] Create Purchase Record						        ");
            System.out.println("[2] View Purchase Record						    	");
            System.out.println("[3] Update Status of Order						    	");
            System.out.println("[4] Exit Orders Management						        ");


            System.out.println("Enter Selected Function: ");
            menuSelection = Integer.parseInt(sc.nextLine());
        	
            switch (menuSelection) {
            	case 1:
            		sales_of_books sb = new sales_of_books();
            		
            		
            		System.out.println("Enter Purchase Information:");
                    System.out.println("BookID:");                      sb.bookID               = Integer.parseInt(sc.nextLine());
                    System.out.println("PublisherID:");                 sb.publisherID          = Integer.parseInt(sc.nextLine());
                    System.out.println("BookstoreID:");                 sb.bookstoreID          = sc.nextLine();
                    System.out.println("Quantity:");                 	sb.quantity_ordered     = Integer.parseInt(sc.nextLine());
                    System.out.println("Remarks:");                 	sb.remarks              = sc.nextLine();

                    
                    if(sb.check_book(sb.bookID) && sb.check_bookstore(sb.bookstoreID) && sb.check_publisher(sb.publisherID)) {
                    	sb.add_orderRecord();
                    } else {
                    	System.out.println("BookID/PublisherID/BookstoreID does not exist. Purchase Order addition aborted.");
                    }
                    
             
                    
            		break;
            		
            	case 2:
            		int viewSelection = 0;
                    Scanner scv = new Scanner(System.in);
            		sb = new sales_of_books();

            		
            		System.out.println(" ");
                    System.out.println(" ");
                    System.out.println("=======================================================");
                    System.out.println("    Please Select Information						   ");
                    System.out.println("-------------------------------------------------------");
                    System.out.println("[1] View Book Purchase						        ");
                    System.out.println("[2] View Publisher Purchase						    ");
                    
                    System.out.println("Enter Selected Function: ");
                    viewSelection = Integer.parseInt(scv.nextLine());
                    
                    switch (viewSelection) {
                    
                    	case 1:
                    		System.out.println("Enter Book Information:");
                            System.out.println("BookID:");                      sb.bookID          = Integer.parseInt(sc.nextLine());
                            
                            if(sb.check_book(sb.bookID)) {
                            	sb.viewOrdersByBook(sb.bookID);
                            } else {
                            	System.out.println("BookID does not exist.");

                            }
                    		
                    	break;
                    	
                    	
                    	case 2:
                    		System.out.println("Enter Book Information:");
                            System.out.println("PublisherID:");                 sb.publisherID     = Integer.parseInt(sc.nextLine());
                            
                            if(sb.check_publisher(sb.publisherID)) {
                            	sb.viewOrdersByPublisher(sb.publisherID);
                            } else {
                            	System.out.println("PublisherID does not exist");

                            }

                    	break;
                    	
                    	
                    	default:
                            System.out.println("Invalid Selection");

                    }
                    
                    
            		break;
            	case 3:
            		sb = new sales_of_books();

            		System.out.println("Enter Purchase Information:");
                    System.out.println("Order Number:");                      	  sb.order_number                 = sc.nextLine();
                    System.out.println("Status:");                      		  sb.status                       = sc.nextLine();

                    sb.updateOrderStatus(sb.order_number, sb.status);
                    
            	case 4:
            		
            		break;
            	default:
                    System.out.println("Invalid Selection");
            }
        	
        }
     
		
        return menuSelection;
		
	}
}
