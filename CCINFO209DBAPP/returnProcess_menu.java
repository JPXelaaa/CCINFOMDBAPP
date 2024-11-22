import java.util.Scanner;

public class returnProcess_menu {

	public returnProcess_menu() {
		
	}
	
	public int menu() {
		int menuselection;
		Scanner console = new Scanner(System.in);
		
			System.out.println("  ");
			System.out.println("  ");
			System.out.println("=======================================================	");
			System.out.println("    Return Transaction Process Menu					   	");
			System.out.println("-------------------------------------------------------	");
			System.out.println("[1] Process a Return for a Book							");
			System.out.println("[2] View Return Information of a Book					");
			System.out.println("[3] View Return Information of a Publisher				");
			System.out.println("[4] Delete a Return Record							   	");
			System.out.println("[0] Exit Customer Management							");
			System.out.println("=======================================================");
			
			System.out.println("Enter Selected Function: ");
			menuselection = Integer.parseInt(console.nextLine());
			
			returnProcess rp = new returnProcess();
			switch (menuselection) 
			{
				case 1: 
					
					System.out.println ("Enter Return information");
					System.out.println ("Return ID	         : ");  rp.return_ID  	 	 = console.nextLine();
					System.out.println ("Return_reason       : ");  rp.return_reason  	 = console.nextLine();
					System.out.println ("Bookstore_ID 		 : ");  rp.bookstore_ID  	 = console.nextLine();
					System.out.println ("Book ID   			 : ");  rp.book_ID	 		 = Integer.parseInt(console.nextLine());
					System.out.println ("Publisher ID		 : ");  rp.publisher_ID 	 = Integer.parseInt(console.nextLine());
					System.out.println ("Quantity Returned   : ");  rp.quantity_returned = Integer.parseInt(console.nextLine());
					
					
					rp.add_returnRecord();
					break;
					
				case 2:
					
					System.out.println ("Enter Return information");
					System.out.println ("Book ID 		: ");  rp.book_ID = Integer.parseInt(console.nextLine());
		
					if (rp.get_returnRecordByBook()==0) {
						break;
					} else {
						System.out.println ("Current Return information");
						System.out.println ("-------------------------------------------------------------------");
						System.out.println ("Return ID        	: " + rp.return_ID);
						System.out.println ("Return Date     	: " + rp.return_date);
						System.out.println ("Return Reason 		: " + rp.return_reason);
						System.out.println ("Quantity Returned  : " + rp.quantity_returned);
			
					}
					break;
					
				case 3: 
					
					System.out.println ("Enter Return information");
					System.out.println ("Publisher ID	: ");  rp.publisher_ID = Integer.parseInt(console.nextLine());		

					if (rp.get_returnRecordByPublisher() == 1) {
					System.out.println ("Current Return information");
					System.out.println ("-------------------------------------------------------------------");
					System.out.println ("Return ID      	  	: " + rp.return_ID);
					System.out.println ("Return Date        	: " + rp.return_date);
					System.out.println ("Book ID		     	: " + rp.book_ID);
					System.out.println ("Return Reason      	: " + rp.return_reason);
					System.out.println ("Quantity Returned 		: " + rp.quantity_returned);
					}
					
					else 
						return 0;
					
					break;
					
				case 4:
					
					System.out.println ("Enter return information");
					System.out.println ("Return ID        : ");  rp.return_ID  = console.nextLine();		
					
					rp.delete_returnRecord();
					break;
					
					
				case 0:
					break;
					
				default:
					System.out.println ("Input invalid. Try again");
					break;
			}  
		return menuselection;
	}
	
}
