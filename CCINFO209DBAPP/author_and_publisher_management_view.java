import java.util.Scanner;

public class author_and_publisher_management_view {

    public author_and_publisher_management_view() {
    }

    public int menu() {
        int menuselection = 0;
        Scanner console = new Scanner(System.in);

        // Display the menu
        System.out.println("=======================================================");
        System.out.println("    Author and Publisher Management Menu               ");
        System.out.println("-------------------------------------------------------");
        System.out.println("[1]  Add a New Author Record");
        System.out.println("[2]  Update an Author Record");
        System.out.println("[3]  Delete an Author Record");
        System.out.println("[4]  View Author");
        System.out.println("[5]  View Books by a Author");
        System.out.println("[6]  Add a New Publisher Record");
        System.out.println("[7]  Update a Publisher Record");
        System.out.println("[8]  Delete a Publisher Record");
        System.out.println("[9]  View Publisher");
        System.out.println("[10] View Books by a Publisher");
        System.out.println("[0]  Exit Management Menu");
        System.out.println("=======================================================");
        System.out.println("Enter Selected Function: ");
        menuselection = Integer.parseInt(console.nextLine());

        author_and_publisher_management apm = new author_and_publisher_management();

        switch (menuselection) {
            case 1:
                // Add Author
                System.out.println("Enter Author Information:");
                System.out.print("Pen Name: ");
                apm.penName = console.nextLine();
                System.out.print("First Name: ");
                apm.firstName = console.nextLine();
                System.out.print("Middle Initial: ");
                apm.middleInitial = console.nextLine();
                System.out.print("Last Name: ");
                apm.lastName = console.nextLine();
                System.out.print("Contact Email: ");
                apm.contactEmail = console.nextLine();
                System.out.print("Phone Number: ");
                apm.phoneNumber = console.nextLine();
                apm.addAuthor();
                break;

            case 2:
                // Update Author
                System.out.print("Enter Pen Name of Author to Update: ");
                apm.penName = console.nextLine();

                System.out.println("Enter Updated Author Information:");
                System.out.print("First Name: ");
                apm.firstName = console.nextLine();
                System.out.print("Middle Initial: ");
                apm.middleInitial = console.nextLine();
                System.out.print("Last Name: ");
                apm.lastName = console.nextLine();
                System.out.print("Contact Email: ");
                apm.contactEmail = console.nextLine();
                System.out.print("Phone Number: ");
                apm.phoneNumber = console.nextLine();
                apm.update_author();
            
                break;

            case 3:
                // Delete Author
                System.out.print("Enter Pen Name of Author to Delete: ");
                apm.penName = console.nextLine();
                apm.deleteAuthor();
                break;

            case 4:
                // View Author
                System.out.print("Enter Pen Name of Author: ");
                apm.penName = console.nextLine();
                apm.getAuthor();

                System.out.println ("Current Author information");
    			System.out.println ("-------------------------------------------------------------------");
    			System.out.println ("Pen Name        		: " + apm.penName);
    			System.out.println ("First Name       		: " + apm.firstName);
    			System.out.println ("Middle Initial        		: " + apm.middleInitial);
    			System.out.println ("Last Name       		: " + apm.lastName);
    			System.out.println ("Contact Email        		: " + apm.contactEmail);
    			System.out.println ("Phone Number       		: " + apm.phoneNumber);
                break;
                
            case 5:
                // View Books by Author
                System.out.print("Enter Pen Name of Author: ");
                apm.penName = console.nextLine();
                apm.getBooksByAuthor();
                break;

            case 6:
                // Add Publisher
                System.out.println("Enter Publisher Information:");
                System.out.print("Publisher Name: ");
                apm.publisherName = console.nextLine();
                System.out.print("Publisher Address Line 1: ");
                apm.publisherAddressLine1 = console.nextLine();
                System.out.print("Publisher Address Line 1: ");
                apm.publisherAddressLine2 = console.nextLine();
                System.out.print("Publisher City: ");
                apm.publisherCity = console.nextLine();
                System.out.print("Contact Email: ");
                apm.publisherContactEmail = console.nextLine();
                System.out.print("Phone Number: ");
                apm.publisherPhoneNumber = console.nextLine();
                apm.addPublisher();
                break;

            case 7:
                // Update Publisher
                System.out.print("Enter Publisher Name to Update: ");
                apm.publisherName = console.nextLine();
                if (apm.getBooksByPublisher() == 0) {
                    System.out.println("No publisher found with that name.");
                } else {
                    System.out.println("Enter Updated Publisher Information:");
                    System.out.print("Publisher Address Line 1: ");
                    apm.publisherAddressLine1 = console.nextLine();
                    System.out.print("Publisher Address Line 2: ");
                    apm.publisherAddressLine2 = console.nextLine();
                    System.out.print("Publisher City: ");
                    apm.publisherCity = console.nextLine();
                    System.out.print("Contact Email: ");
                    apm.publisherContactEmail = console.nextLine();
                    System.out.print("Phone Number: ");
                    apm.publisherPhoneNumber = console.nextLine();
                    apm.update_publisher();
                }
                break;

            case 8:
                // Delete Publisher
                System.out.print("Enter Publisher Name to Delete: ");
                apm.publisherName = console.nextLine();
                apm.deletePublisher();
                break;

            case 9:
                // View Publisher
                System.out.print("Enter Publisher Name: ");
                apm.publisherName = console.nextLine();
                apm.getPublisher();
                
                System.out.println ("Current Publisher information");
    			System.out.println ("-------------------------------------------------------------------");
    			System.out.println ("Publisher Name        			 : " + apm.publisherName);
    			System.out.println ("Publisher Address Line 1        	 : " + apm.publisherAddressLine1);
    			System.out.println ("Publisher Address Line 2    	 	 : " + apm.publisherAddressLine2);
    			System.out.println ("Publisher City       			 : " + apm.publisherCity);
    			System.out.println ("Publisher Contact Email    	 	 : " + apm.publisherContactEmail);
    			System.out.println ("Publisher Phone Number    		 : " + apm.publisherPhoneNumber);
                break;

            case 10:
                // View Books by Publisher
                System.out.print("Enter Publisher Name: ");
                apm.publisherName = console.nextLine();
                apm.getBooksByPublisher();
                break;

            case 0:
                System.out.println("Exiting Management Menu...");
                break;

            default:
                System.out.println("Invalid selection. Please try again.");
                break;
        }

        return menuselection;
    }
}
