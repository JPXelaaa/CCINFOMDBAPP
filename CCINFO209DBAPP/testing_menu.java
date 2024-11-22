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
		System.out.println("[2] Customer Management		    		   			   ");
		System.out.println("[3] Return Processing		    		   			   ");
		System.out.println("[4] View Author Popularity Report		    		   ");
		System.out.println("[5] View Book Genre Popularity Report		    	   ");
		System.out.println("[0] Exit the Menu									   ");
		System.out.println("=======================================================");
		
		System.out.println("Enter Selected Function: ");
		menuselection = Integer.parseInt(console.nextLine());
		
		switch(menuselection) {
			case 2: 
				customer_management_menu cmm = new customer_management_menu();
				while (cmm.menu()!=0) {}
				break;
			
			case 3:
				returnProcess_menu rpm = new returnProcess_menu();
				while (rpm.menu()!=0) {}
				break;
			
			case 4:
				author_report ar = new author_report();
				ar.generate_authorreport();
				break;
			
			case 5:
				bookgenre_report bgr = new bookgenre_report();
				bgr.generate_bookgenrereport();
				
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
