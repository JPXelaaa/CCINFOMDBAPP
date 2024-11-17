package G204DBAPP;
import java.io.*;
import java.util.Scanner;

public class product_management_menu {

	public product_management_menu() {
		
	}
	
	public int menu() {
		int 	menuselection = 0;
		Scanner console   	  = new Scanner(System.in);
		
		// Algorithm for Menu Processing
		// 1. Display the Menu
		// 2. Ask the user for function selection
		// 3. Depending on the selected function, ask user for input
		// 4. Perform the function
		
		System.out.println("  ");
		System.out.println("  ");
		System.out.println("=======================================================");
		System.out.println("    Product Management Menu							   ");
		System.out.println("-------------------------------------------------------");
		System.out.println("[1] Create a new Product Record						   ");
		System.out.println("[2] Update a Product Record							   ");
		System.out.println("[3] Delete a Product Record							   ");
		System.out.println("[4] View a Product Record							   ");
		System.out.println("[0] Exit Product Management							   ");
		System.out.println("=======================================================");
		
		System.out.println("Enter Selected Function: ");
		menuselection = Integer.parseInt(console.nextLine());
		
		if (menuselection==1) {
			// Adding a new Records, ask the user for the values of the record fields
			product_management p = new product_management();
			
			System.out.println ("Enter product information");
			System.out.println ("Product Code        : ");  p.productCode  		 = console.nextLine();
			System.out.println ("Product Name        : ");  p.productName  		 = console.nextLine();
			System.out.println ("Product Line        : ");  p.productLine  	     = console.nextLine();
			System.out.println ("Product Scale       : ");  p.productScale 		 = console.nextLine();
			System.out.println ("Product Description : ");  p.productDescription = console.nextLine();
			System.out.println ("Product Vendor      : ");  p.productVendor      = console.nextLine();
			System.out.println ("Initial quantity    : ");  p.quantityInStock    = Integer.parseInt(console.nextLine());
			System.out.println ("Buy Price           : ");  p.buyPrice           = Float.parseFloat(console.nextLine());
			System.out.println ("MSRP                : ");  p.MSRP 				 = Float.parseFloat(console.nextLine());
			
			p.add_product();
			
		} else if (menuselection==2) {			
			// Updating a Records, 
			// 1. Ask the user for the record to be updated
			// 2. Let the user see the old values
			// 3. Ask the user for the new values
			
			product_management p = new product_management();
			
			System.out.println ("Enter product information");
			System.out.println ("Product Code        : ");  p.productCode  		 = console.nextLine();

			if (p.get_product()==0) {
				System.out.println("That product does not exists on the records");
			} else {
				System.out.println ("Current Product information");
				System.out.println ("-------------------------------------------------------------------");
				System.out.println ("Product Code        : " + p.productCode);
				System.out.println ("Product Name        : " + p.productName);
				System.out.println ("Product Line        : " + p.productLine);
				System.out.println ("Product Scale       : " + p.productScale);
				System.out.println ("Product Description : " + p.productDescription);
				System.out.println ("Product Vendor      : " + p.productVendor);
				System.out.println ("Initial quantity    : " + p.quantityInStock);
				System.out.println ("Buy Price           : " + p.buyPrice);
				System.out.println ("MSRP                : " + p.MSRP);
	
				System.out.println ("Enter updated product information");
				System.out.println ("-------------------------------------------------------------------");
				System.out.println ("Product Name        : ");  p.productName  		 = console.nextLine();
				System.out.println ("Product Line        : ");  p.productLine  	     = console.nextLine();
				System.out.println ("Product Scale       : ");  p.productScale 		 = console.nextLine();
				System.out.println ("Product Description : ");  p.productDescription = console.nextLine();
				System.out.println ("Product Vendor      : ");  p.productVendor      = console.nextLine();
				System.out.println ("Initial quantity    : ");  p.quantityInStock    = Integer.parseInt(console.nextLine());
				System.out.println ("Buy Price           : ");  p.buyPrice           = Float.parseFloat(console.nextLine());
				System.out.println ("MSRP                : ");  p.MSRP 				 = Float.parseFloat(console.nextLine());		
			
				p.update_product();
			}
		} else if (menuselection==3) {
			product_management p = new product_management();
			
			System.out.println ("Enter product information");
			System.out.println ("Product Code        : ");  p.productCode  		 = console.nextLine();		
			
			p.delete_product();
			
		} else if (menuselection==4) {
			product_management p = new product_management();
			
			System.out.println ("Enter product information");
			System.out.println ("Product Code        : ");  p.productCode  		 = console.nextLine();

			p.get_product();
			System.out.println ("Current Product information");
			System.out.println ("-------------------------------------------------------------------");
			System.out.println ("Product Code        : " + p.productCode);
			System.out.println ("Product Name        : " + p.productName);
			System.out.println ("Product Line        : " + p.productLine);
			System.out.println ("Product Scale       : " + p.productScale);
			System.out.println ("Product Description : " + p.productDescription);
			System.out.println ("Product Vendor      : " + p.productVendor);
			System.out.println ("Initial quantity    : " + p.quantityInStock);
			System.out.println ("Buy Price           : " + p.buyPrice);
			System.out.println ("MSRP                : " + p.MSRP);
		}
 	
		return menuselection;
	}	
}
