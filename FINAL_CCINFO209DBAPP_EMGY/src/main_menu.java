import java.util.Scanner;

public class main_menu {
	
	public main_menu () {	
	}
	
	public int menu() {
		int 	menuselection = 0;
		Scanner console   	  = new Scanner(System.in);
		
		System.out.println("  ");
		System.out.println("  ");
		System.out.println("===========================================================================");
		System.out.println("     			  Application Main Menu									   ");
		System.out.println("---------------------------------------------------------------------------");
		System.out.println("[1] Book Record Management");
		System.out.println("[2] Customer Record Management");
		System.out.println("[3] Author and Publisher Management ");
		System.out.println("[4] Sales of Books as a Transaction");
		System.out.println("[5] Return of Sold Books as a Transaction");
		System.out.println("[6] Stock Entry of Purchased Books from the Publisher as a Transaction");
		System.out.println("[7] Book Activity Report");
		System.out.println("[8] Author and Book Genre Popularity Report");
		System.out.println("[9] Transaction Report");
		System.out.println("===========================================================================");
		
		System.out.print("Enter Selected Function: ");
		menuselection = Integer.parseInt(console.nextLine());
		
		if (menuselection==3) {
			author_and_publisher_management_view apmv = new author_and_publisher_management_view();
			while (apmv.menu()!=0) {}
		} 
		if (menuselection==6) {
			stock_entry_transaction_view setv = new stock_entry_transaction_view();
			while (setv.displayMenu()!=0) {}
		} 
		if (menuselection==9) {
			transaction_report tr = new transaction_report();
			while (tr.generate_transactionreport()!=0) {}
		} 
		if (menuselection==1) {
            System.out.println("\n--- Book Management ---");
            book_record_management_menu brm = new book_record_management_menu();
            while (brm.menu()!=5) {}
		} 
		if (menuselection==4) {
			System.out.println("\n--- Book Sales ---");
            sales_of_books_menu salesMenu = new sales_of_books_menu();
            while (salesMenu.menu() != 4) {}
		} 
		if (menuselection==7) {
			System.out.println("\n--- Book Report ---");
            book_activity_report report = new book_activity_report();
            System.out.print("Enter Year: ");
            report.year = Integer.parseInt(console.nextLine());
            System.out.print("Enter Month (1-12): ");
            report.month = Integer.parseInt(console.nextLine());
            report.generate_bookact_report();
		} 
		if (menuselection==2) {
			customer_management_menu cmm = new customer_management_menu();
			while (cmm.menu()!=0) {}
		} 
		if (menuselection==5) {
			returnProcess_menu rpm = new returnProcess_menu();
			while (rpm.menu()!=0) {}
		} 
		if (menuselection==8) {
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
		} 
		return menuselection;
	}
	
	
	public static void main (String args[]) {
		
		main_menu mm = new main_menu();
		while (mm.menu()!=0) {}
	}

}
