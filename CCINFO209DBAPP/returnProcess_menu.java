import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class returnProcess_menu {
	public String 	return_ID;
	
	public returnProcess_menu() {
		return_ID			= "";
	}
	
	public int returnIDChoice() {
	   	 try {
	            Connection conn = DriverManager.getConnection("jdbc:mysql://34.57.40.219:3306/CCINFO209DB?useTimezone=true&serverTimezone=UTC&user=root&password=DLSU1234!");
	            
	            PreparedStatement checkStmt = conn.prepareStatement(
	                "SELECT COUNT(*) AS count FROM returns WHERE return_ID=?"
	            );
	            checkStmt.setString(1, return_ID);
	            ResultSet r = checkStmt.executeQuery();
	            r.next();
	            int count = r.getInt("count");
	            r.close();
	            checkStmt.close();

	            if (count == 0) {
	                System.out.println("Return Record does not exist in the database. Update terminated.");
	                conn.close();
	                return 0;
	            } 
	            
	            return 1;
	   	 } catch (Exception e) {
	   		 System.out.println(e.getMessage());
	   		 return 0;
	   	 }
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
			System.out.println("[4] Update a Return Status");
			System.out.println("[5] Delete a Return Record");
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
					System.out.println ("Book ID 	: ");  rp.book_ID = Integer.parseInt(console.nextLine());
		
					if (rp.get_returnRecordByBook()==0) {
						System.out.println("Return Record does not exist in the database. Update terminated.");
						break;
					} else {
						System.out.println ("Current Return information");
						System.out.println ("-------------------------------------------------------------------");
						System.out.println ("Return ID        	: " + rp.return_ID);
						System.out.println ("Return Date     	: " + rp.return_date);
						System.out.println ("Return Reason 		: " + rp.return_reason);
						System.out.println ("Return Status		: " + rp.return_status);
						System.out.println ("Quantity Returned  	: " + rp.quantity_returned);
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
					System.out.println ("Return Status		: " + rp.return_status);
					System.out.println ("Quantity Returned 	: " + rp.quantity_returned);
					}
					
					else {
						System.out.println("Return Record does not exist in the database. Update terminated.");
						return 0;
					}
					
					break;
				
				case 4:
					System.out.println ("Enter Return information");
					System.out.println ("Return ID	: ");  rp.return_ID = console.nextLine();	
					
					if (returnIDChoice() == 1) {
						System.out.println ("New Return Status: "); rp.return_status = console.nextLine();
						rp.update_returnRecord(); 
					} else {
						return 0;
					}
						
					break;
					
				case 5:
					
					System.out.println ("Enter return information");
					System.out.println ("Return ID        : ");  rp.return_ID  = console.nextLine();		
					
					if (returnIDChoice() == 1) {
						System.out.println ("New Return Status: "); rp.return_status = console.nextLine();
						rp.update_returnRecord(); 
					} else {
						return 0;
					}
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
