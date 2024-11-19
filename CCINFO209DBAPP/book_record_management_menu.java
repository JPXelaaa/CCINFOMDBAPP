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
                    System.out.println("ISBN:");                        bm.ISBN                 = Integer.parseInt(sc.nextLine());
                    System.out.println("Title:");                       bm.title                = sc.nextLine();

                    System.out.println("Authors (comma-separated):");
                    String[] authors = sc.nextLine().split(",");

                    System.out.println("Genres: (comma-separated)");
                    String[] genres = sc.nextLine().split(",");

                    System.out.println("Publisher:");                   bm.publisherName          = sc.nextLine();
                    System.out.println("Publication Year (YYYY):");     bm.publicationYear      = Integer.parseInt(sc.nextLine());
                    System.out.println("Stock Quantity:");              bm.stockQuantity        = Integer.parseInt(sc.nextLine());


                    if(bm.check_authors(authors) && bm.check_genres(genres)){
                        bm.add_book();
                    } else {
                        System.out.println("Author or Genre does not exist. Book addition aborted.");
                    }
                    break;

                case 2:
                    bm = new book_record_management();
                    System.out.println("Enter Book Information:");
                    System.out.println("ISBN     :");                   bm.ISBN = Integer.parseInt(sc.nextLine());

                    if (!bm.get_book()) {
                        System.out.println("That product does not exist on the records");
                    } else {
                        System.out.println("Current Book Information");
                        System.out.println("-------------------------------------------------------");
                        System.out.println("ISBN             :" + bm.ISBN);
                        System.out.println("Title               :" + bm.title);
                        System.out.println("Author              :" + bm.get_authors());
                        System.out.println("Genre               :" + bm.get_genres());
                        System.out.println("Publisher           :" + bm.publisherName);
                        System.out.println("Publication Year    :" + bm.publicationYear);
                        System.out.println("Stock Quantity      :" + bm.stockQuantity);

                        System.out.println("Enter Updated Book Information");
                        System.out.println("-------------------------------------------------------");
                        System.out.println("ISBN                : ");       bm.ISBN = Integer.parseInt(sc.nextLine());
                        System.out.println("Title               : ");       bm.title = sc.nextLine();


                        System.out.println("Authors (comma-separated, must already exist): ");
                        String[] updatedAuthors = sc.nextLine().split(",");
                        if (!bm.check_authors(updatedAuthors)) {
                            System.out.println("One or more authors do not exist. Update aborted.");
                            break;
                        }


                        System.out.println("Genres (comma-separated, must already exist): ");
                        String[] updatedGenres = sc.nextLine().split(",");
                        if (!bm.check_genres(updatedGenres)) {
                            System.out.println("One or more genres do not exist. Update aborted.");
                            break;
                        }


                        System.out.println("Publisher           : ");       bm.publisherName = sc.nextLine();
                        System.out.println("Publication Year    : ");       bm.publicationYear = Integer.parseInt(sc.nextLine());
                        System.out.println("Stock Quantity      : ");       bm.stockQuantity = Integer.parseInt(sc.nextLine());

                        bm.update_book(updatedAuthors, updatedGenres);
                    }
                    break;
                case 3:
                    bm = new book_record_management();

                    System.out.println("Enter Book Information");
                    System.out.println("ISBN     :");
                    bm.ISBN = Integer.parseInt(sc.nextLine());

                    bm.delete_book();

                case 4:
                    bm = new book_record_management();

                    System.out.println("Enter Book Information");
                    System.out.println("Book ID     :");
                    bm.ISBN = Integer.parseInt(sc.nextLine());

                    bm.get_book();
                    System.out.println("Current Book Information");
                    System.out.println("-------------------------------------------------------");
                    System.out.println("ISBN                :" + bm.ISBN);
                    System.out.println("Title               :" + bm.title);
                    System.out.println("Author              :" + bm.get_authors());
                    System.out.println("Genre               :" + bm.get_genres());
                    System.out.println("Publisher           :" + bm.publisherName);
                    System.out.println("Publication Year    :" + bm.publicationYear);
                    System.out.println("Stock Quantity      :" + bm.stockQuantity);

                default:
                    System.out.println("Invalid Selection");
            }

        }


        return menuSelection;
    }

}
