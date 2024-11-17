package G204DBAPP;

import java.util.Scanner;

public class main_menu {
	
	public main_menu () {	
	}
	
	public int menu() {
		int 	menuselection = 0;
		Scanner console   	  = new Scanner(System.in);
		
		System.out.println("  ");
		System.out.println("  ");
		System.out.println("=======================================================");
		System.out.println("    Application Main Menu							   ");
		System.out.println("-------------------------------------------------------");
		System.out.println("[1] Product Management								   ");
		System.out.println("[2] Customer Management								   ");
		System.out.println("[3] Employee Management								   ");
		System.out.println("[4] Office Management								   ");
		System.out.println("[5] Order Processing								   ");
		System.out.println("[6] Payment Processing								   ");
		System.out.println("[7] Report Generation - Sales Report 1				   ");
		System.out.println("[8] Report Generation - Sales Report 2				   ");
		System.out.println("[0] Exit Application								   ");
		System.out.println("=======================================================");
		
		System.out.println("Enter Selected Function: ");
		menuselection = Integer.parseInt(console.nextLine());
		
		if (menuselection==1) {
			product_management_menu pmm = new product_management_menu();
			while (pmm.menu()!=0) {}
		} else if (menuselection==7) {
			sales_report sr = new sales_report();
			
			System.out.println ("Enter Report Parameters");
			System.out.println ("Month : "); sr.month = Integer.parseInt(console.nextLine());		
			System.out.println ("Year  : "); sr.year  = Integer.parseInt(console.nextLine());		
			
			sr.generate_salesreport1();
		}
		return menuselection;
	}
	
	
	public static void main (String args[]) {
		main_menu mm = new main_menu();
		while (mm.menu()!=0) {}
	}

}
