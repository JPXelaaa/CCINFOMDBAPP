import java.util.Scanner;

public class testing_menu {
	
	public testing_menu () {	
	}
	
	public int menu() {
		int 	menuselection = 0;
		Scanner console   	  = new Scanner(System.in);
		
		do {
			
		System.out.println("  ");
		System.out.println("  ");
		System.out.println("=======================================================");
		System.out.println("       Testing Application Main Menu (Trish)		   ");
		System.out.println("-------------------------------------------------------");
		System.out.println("[1] Customer Record Management		    		   			   ");
		System.out.println("[2] Return of Sold Books		    		   			   ");
		System.out.println("[3] Author and Book Genre Popularity Report	    	   ");
		System.out.println("[0] Exit the Menu									   ");
		System.out.println("=======================================================");
		
		System.out.println("Enter Selected Function: ");
		menuselection = Integer.parseInt(console.nextLine());
		
		switch(menuselection) {
			case 1: 
				customer_management_menu cmm = new customer_management_menu();
				while (cmm.menu()!=0) {}
				break;
			
			case 2:
				returnProcess_menu rpm = new returnProcess_menu();
				while (rpm.menu()!=0) {}
				break;
			
			case 3:
				System.out.println("=======================================================");
				System.out.println("       Which report would you like to generate?		   ");
				System.out.println("-------------------------------------------------------");
				System.out.println("[1] Author Popularity Report ");
				System.out.println("[2] Book Genre Report");
				System.out.println("[0] Exit the Menu									   ");
				System.out.println("=======================================================");
				
				System.out.println("Enter Selected Function: ");
				int choice = Integer.parseInt(console.nextLine());
				
					if (choice == 1) {
						author_report ar = new author_report();
						ar.generate_authorreport();
					}
					else if (choice == 2) {
						bookgenre_report bgr = new bookgenre_report();
						bgr.generate_bookgenrereport();
					}
					else {
						System.out.println("Invalid Input.");
					}
					
				break;
			
			case 0:
				break;
			
			default:
				System.out.println ("Input invalid. Try again");
				break;
				
		}
	
		return menuselection; 
		
		} while (menuselection != 0);
	}
	
	
	public static void main (String args[]) {
		testing_menu mm = new testing_menu();
		while (mm.menu()!=0) {}
	}

}
