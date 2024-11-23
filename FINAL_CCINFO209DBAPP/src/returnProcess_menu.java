import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
					boolean isFirstReturn = true;
				    int returnCount = 0; 
				    
				    System.out.println("Enter Return Information");
				    
				    System.out.println("Would you like to view the list of Return IDs? [1] Yes / [2] No");
				    int choice = Integer.parseInt(console.nextLine());
					    if (choice == 1) {
					    	rp.displayExistingReturnIDs(); 
				        } 
				    System.out.print("Return ID            : "); 	rp.return_ID = console.nextLine();
				    System.out.print("Return Reason        : ");	rp.return_reason = console.nextLine();
				    
				    System.out.println("Would you like to view the list of Bookstore IDs? [1] Yes / [2] No");
				    choice = Integer.parseInt(console.nextLine());
					    if (choice == 1) {
					    	rp.displayExistingBookstoreIDs(); 
				        } 
				    System.out.print("Bookstore ID         : ");	rp.bookstore_ID = console.nextLine();

				    boolean addMoreBooks = true;


				    
				    while (addMoreBooks) {
				    	System.out.println("Would you like to view the list of BookIDs under each Bookstore? [1] Yes / [2] No");
					    choice = Integer.parseInt(console.nextLine());
						    if (choice == 1) {
						    	rp.displayExistingBookIDsUnderBookstore(); 
					        } 
				        System.out.print("Book ID              : ");	rp.book_ID = Integer.parseInt(console.nextLine());
				        
				        
				        System.out.println("Would you like to view the list of publisher? [1] Yes / [2] No");
					    choice = Integer.parseInt(console.nextLine());
						    if (choice == 1) {
						    	rp.displayExistingPublisherIDs(); 
					        } 
				        System.out.print("Publisher ID         : ");	rp.publisher_ID = Integer.parseInt(console.nextLine());
				        System.out.print("Quantity Returned    : ");	rp.quantity_returned = Integer.parseInt(console.nextLine());

				        int result = rp.add_returnRecord(isFirstReturn);

				        if (result == 1) {
				            System.out.println("Book return details added successfully.");
				            returnCount++;
				            isFirstReturn = false; 
				        } else if (result == -1) {
				            System.out.println("Error: Quantity returned exceeds the quantity ordered. Try again.");
				        } else {
				            System.out.println("Error occurred while processing the return.");
				        }

				        System.out.print("Return another book? [1] Yes [2] No: ");
				        choice = Integer.parseInt(console.nextLine());
				        if (choice != 1) {
				            addMoreBooks = false; 
				        }
				    }

				    if (returnCount > 0) {
				        System.out.println("Return record and details were created successfully.");
				    } else {
				        System.out.println("No record was created.");
				    }
					
					break;
					
				case 2:
					rp.displayExistingBookIDs();
					System.out.println ("\nEnter Return information");
					System.out.println ("Book ID 	: ");  rp.book_ID = Integer.parseInt(console.nextLine());
		
					if (rp.get_returnRecordByBook()==0) {
						break;
					} 
					break;
					
				case 3: 
					rp.displayExistingPublisherIDs();
					System.out.println ("Enter Return information");
					System.out.println ("Publisher ID	: ");  rp.publisher_ID = Integer.parseInt(console.nextLine());		

					if (rp.get_returnRecordByPublisher()==0) {
						break;
					}
					
					break;
				
				case 4:
					rp.displayExistingReturnIDs();
					System.out.println ("Enter Return information");
					System.out.println ("Return ID	: ");  rp.return_ID = console.nextLine();	
					
					if (rp.returnIDChoice() == 1) {
						System.out.println ("New Return Status: "); rp.return_status = console.nextLine();
						rp.update_returnRecord(); 
					} else {
						return 0;
					}
						
					break;
					
				case 5:
					rp.displayExistingReturnIDs();
					System.out.println ("Enter return information");
					System.out.println ("Return ID        : ");  rp.return_ID  = console.nextLine();		
					
					if (rp.returnIDChoice() == 1) {
						rp.delete_returnRecord();
					} else {
						return 0;
					}
					
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
