import java.io.*;
import java.util.Scanner;


public class book_record_management_menu {

    public book_record_management_menu(){

    }

    public int menu(){
        int menuSelection = 0;
        Scanner sc = new Scanner(System.in);

        System.out.println(" ");
        System.out.println(" ");
        System.out.println("=======================================================");
        System.out.println("    Book Management Menu						   ");
        System.out.println("-------------------------------------------------------");
        System.out.println("[1] Create a New Book Record						    ");
        System.out.println("[2] Update a Book Record						    ");
        System.out.println("[3] Delete a Book Record						    ");
        System.out.println("[4] View a Book Record						    ");
        System.out.println("[5] Exit Book Management						    ");

        System.out.println("Enter Selected Function: ");
        menuSelection = Integer.parseInt(sc.nextLine());

        while(menuSelection != 5) {
            switch (menuSelection) {
                case 1:
                    book_record_management bm = new book_record_management();

                    System.out.println("Enter Book Information:");
                    System.out.println("ISBN:");
                    System.out.println("Title:");
                    System.out.println("Author:");
                    System.out.println("Genre:");
                    System.out.println("Publisher:");
                    System.out.println("Publication Year:");
                    System.out.println("Stock Quantity:");

                    bm.add_product();

                case 2:
                    book_record_management bm = new book_record_management();
                    System.out.println("Enter Book Information:");
                    System.out.println("ISBN     :");
                    bm.ISBN = sc.nextLine();

                    if (bm.get_book() == 0) {
                        System.out.println("That product does not exist on the records");
                    } else {
                        System.out.println("Current Book Information");
                        System.out.println("-------------------------------------------------------");
                        System.out.println("ISBN             :" + bm.ISBN);
                        System.out.println("Title               :" + bm.title);
                        System.out.println("Author              :" + bm.author);
                        System.out.println("Genre               :" + bm.genre);
                        System.out.println("Publisher           :" + bm.publisherID);
                        System.out.println("Publication Year    :" + bm.publicationYear);
                        System.out.println("Stock Quantity      :" + bm.stockQuantity);

                        System.out.println("Enter Updated Book Information");
                        System.out.println("-------------------------------------------------------");
                        System.out.println("ISBN                : ");
                        bm.ISBN = sc.nextLine();
                        System.out.println("Title               : ");
                        bm.title = sc.nextLine();
                        System.out.println("Author              : ");
                        bm.author = sc.nextLine();
                        System.out.println("Genre               : ");
                        bm.genre = sc.nextLine();
                        System.out.println("Publisher           : ");
                        bm.publisherID = sc.nextLine();
                        System.out.println("Publication Year    : ");
                        bm.publicationYear = sc.next();
                        System.out.println("Stock Quantity      : ");
                        bm.stockQuantity = Integer.parseInt(sc.nextLine());

                        // bm.update_book();
                    }

                case 3:
                    book_record_management bm = new book_record_management();

                    System.out.println("Enter Book Information");
                    System.out.println("ISBN     :");
                    bm.ISBN = sc.nextLine();

                    // bm.delete_book();

                case 4:
                    book_record_management bm = new book_record_management();

                    System.out.println("Enter Book Information");
                    System.out.println("Book ID     :");
                    bm.ISBN = sc.nextLine();

                    bm.get_book();
                    System.out.println("Current Book Information");
                    System.out.println("-------------------------------------------------------");
                    System.out.println("ISBN                :" + bm.ISBN);
                    System.out.println("Title               :" + bm.title);
                    System.out.println("Author              :" + bm.author);
                    System.out.println("Genre               :" + bm.genre);
                    System.out.println("Publisher           :" + bm.publisherID);
                    System.out.println("Publication Year    :" + bm.publicationYear);
                    System.out.println("Stock Quantity      :" + bm.stockQuantity);

                default:
                    System.out.println("Invalid Selection");
            }

        }


        return menuSelection;
    }

}
