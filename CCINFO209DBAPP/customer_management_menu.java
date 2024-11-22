import java.util.Scanner;

public class customer_management_menu {

	public customer_management_menu() {
		
	}
	
	public int menu() {
		int menuselection;
		Scanner console = new Scanner(System.in);
		
			System.out.println("  ");
			System.out.println("  ");
			System.out.println("=======================================================");
			System.out.println("    Customer Management Menu							");
			System.out.println("-------------------------------------------------------");
			System.out.println("[1] Create a new Customer Record						");
			System.out.println("[2] Update a Customer Record							");
			System.out.println("[3] Delete a Customer Record							");
			System.out.println("[4] View a Customer Record							   	");
			System.out.println("[0] Exit Customer Management							");
			System.out.println("=======================================================");
			
			System.out.println("Enter Selected Function: ");
			menuselection = Integer.parseInt(console.nextLine());
			
			customer_management c = new customer_management();
			switch (menuselection) 
			{
				case 1: // adding a new Records, ask the user for the values of the record fields
					
					System.out.println ("Enter customer information");
					System.out.println ("Bookstore ID        : ");  c.bookstore_ID  		 = console.nextLine();
					System.out.println ("Bookstore Name      : ");  c.bookstore_name  		 = console.nextLine();
					System.out.println ("Contact First Name  : ");  c.contact_firstName  	 = console.nextLine();
					System.out.println ("Contact Last Name   : ");  c.contact_lastName 		 = console.nextLine();
					System.out.println ("Phone Number 		 : ");  c.phone_number 			 = console.nextLine();
					System.out.println ("Address Line 1      : ");  c.addressLine1      	 = console.nextLine();
					System.out.println ("Address Line 2    	 : ");  c.addressLine2    		 = console.nextLine();
					System.out.println ("City           	 : ");  c.city		           	 = console.nextLine();
					
					c.add_customer();
					break;
					
				case 2:
					
					System.out.println ("Enter customer information");
					System.out.println ("Bookstore ID        : ");  c.bookstore_ID  		 = console.nextLine();
		
					if (c.get_customer()==0) {
						break;
					} else {
						System.out.println ("Current Customer information");
						System.out.println ("-------------------------------------------------------------------");
						System.out.println ("Bookstore ID        	: " + c.bookstore_ID);
						System.out.println ("Bookstore Name        	: " + c.bookstore_name);
						System.out.println ("Contact First Name     : " + c.contact_firstName);
						System.out.println ("Contact Last Name      : " + c.contact_lastName);
						System.out.println ("Phone Number 			: " + c.phone_number);
						System.out.println ("Address Line 1      	: " + c.addressLine1);
						System.out.println ("Address Line 2    		: " + c.addressLine2);
						System.out.println ("City           		: " + c.city);
						
						System.out.println ("Enter updated customer information");
						System.out.println ("-------------------------------------------------------------------");
						System.out.println ("Bookstore Name        	: ");  c.bookstore_name  	= console.nextLine();
						System.out.println ("Contact First Name     : ");  c.contact_firstName 	= console.nextLine();
						System.out.println ("Contact Last Name  	: ");  c.contact_lastName 	= console.nextLine();
						System.out.println ("Phone Number      		: ");  c.phone_number      	= console.nextLine();
						System.out.println ("Address Line 1    		: ");  c.addressLine1    	= console.nextLine();
						System.out.println ("Address Line 2         : ");  c.addressLine2       = console.nextLine();
						System.out.println ("City                	: ");  c.city 				= console.nextLine();		
					
						c.update_customer();
					}
					break;
					
				case 3:
					
					System.out.println ("Enter customer information");
					System.out.println ("Bookstore ID        : ");  c.bookstore_ID  		 = console.nextLine();		
					
					c.delete_customer();
					break;
					
				case 4: 
					
					System.out.println ("Enter customer information");
					System.out.println ("Bookstore ID        : ");  c.bookstore_ID  		 = console.nextLine();		

					if (c.get_customer() == 1) {
					System.out.println ("Current Customer information");
					System.out.println ("-------------------------------------------------------------------");
					System.out.println ("Bookstore ID        	: " + c.bookstore_ID);
					System.out.println ("Bookstore Name        	: " + c.bookstore_name);
					System.out.println ("Contact First Name     : " + c.contact_firstName);
					System.out.println ("Contact Last Name      : " + c.contact_lastName);
					System.out.println ("Phone Number 			: " + c.phone_number);
					System.out.println ("Address Line 1      	: " + c.addressLine1);
					System.out.println ("Address Line 2    		: " + c.addressLine2);
					System.out.println ("City           		: " + c.city);
					}
					
					else 
						return 0;
					
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
