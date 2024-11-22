import java.sql.*;
import java.util.Scanner;

public class author_and_publisher_management {
    Scanner console = new Scanner(System.in);

    // Author
    public String penName;
    public String firstName;
    public String middleInitial;
    public String lastName;
    public String contactEmail;
    public String phoneNumber;

    // Publisher
    public int publisherID;
    public String publisherName;
    public String publisherAddressLine1;
    public String publisherAddressLine2;
    public String publisherCity;
    public String publisherContactEmail;
    public String publisherPhoneNumber;
    public String publisherHolder;

    // Book Information
    public String ISBN;
    public String bookTitle;
    public String bookGenre;
    public String publicationDate;
    public int stockQuantity;

    public author_and_publisher_management() {
        penName = "";
        firstName = "";
        middleInitial = "";
        lastName = "";
        contactEmail = "";
        phoneNumber = "";
        publisherID = 0;
        publisherName = "";
        publisherAddressLine1 = "";
        publisherAddressLine2 = "";
        publisherCity = "";
        publisherContactEmail = "";
        publisherPhoneNumber = "";
        ISBN = "";
        bookTitle = "";
        bookGenre = "";
        publicationDate = "";
        stockQuantity = 0;
    }

    public int addAuthor() {
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://34.57.40.219:3306/CCINFO209DB?useTimezone=true&serverTimezone=UTC&user=root&password=DLSU1234!");
            System.out.println("Connection to DB Successful");

            PreparedStatement pstmt = conn.prepareStatement(
               "INSERT INTO authors (pen_name, first_name, middle_initial, last_name, contact_email, phone_number) VALUES (?, ?, ?, ?, ?, ?)"
            );
            pstmt.setString(1, penName);
            pstmt.setString(2, firstName);
            pstmt.setString(3, middleInitial);
            pstmt.setString(4, lastName);
            pstmt.setString(5, contactEmail);
            pstmt.setString(6, phoneNumber);
            pstmt.executeUpdate();
            System.out.println("Author record added");

            pstmt.close();
            conn.close();
            return 1;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return 0;
        }
    }

    public int addPublisher() {
    	try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://34.57.40.219:3306/CCINFO209DB?useTimezone=true&serverTimezone=UTC&user=root&password=DLSU1234!");
            System.out.println("Connection to DB Successful");

            PreparedStatement pstmt = conn.prepareStatement(
                "INSERT INTO publishers (publisher_id, publisher_name, addressLine1, addressLine2, city, contact_email, phone_number) VALUES (?, ?, ?, ?, ?, ?, ?)"
            );
            pstmt.setInt(1, publisherID);
            pstmt.setString(2, publisherName);
            pstmt.setString(3, publisherAddressLine1);
            pstmt.setString(4, publisherAddressLine2);
            pstmt.setString(5, publisherCity);
            pstmt.setString(6, publisherContactEmail);
            pstmt.setString(7, publisherPhoneNumber);
            pstmt.executeUpdate();
            System.out.println("Publisher record added");
            
            pstmt.close();
            conn.close();
            return 1;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return 0;
        }
    }
    
    public int update_author() {
        try {
            Connection conn = DriverManager.getConnection(
                "jdbc:mysql://34.57.40.219:3306/CCINFO209DB?useTimezone=true&serverTimezone=UTC&user=root&password=DLSU1234!"
            );
            System.out.println("Connection to DB Successful");

            // Check if the author exists
            PreparedStatement checkStmt = conn.prepareStatement(
                "SELECT COUNT(*) AS count FROM authors WHERE pen_name=?"
            );
            checkStmt.setString(1, penName);
            ResultSet rs = checkStmt.executeQuery();
            rs.next();
            int count = rs.getInt("count");
            rs.close();
            checkStmt.close();

            if (count == 0) {
                System.out.println("Author does not exist in the database. Update terminated.");
                conn.close();
                return 0;
            }

            // Proceed with the update if the author exists
            PreparedStatement pstmt = conn.prepareStatement(
                "UPDATE authors SET first_name=?, middle_initial=?, last_name=?, contact_email=?, phone_number=? WHERE pen_name=?"
            );
            pstmt.setString(1, firstName);
            pstmt.setString(2, middleInitial);
            pstmt.setString(3, lastName);
            pstmt.setString(4, contactEmail);
            pstmt.setString(5, phoneNumber);
            pstmt.setString(6, penName);

            int rowsAffected = pstmt.executeUpdate();
            System.out.println("Author record updated, rows affected: " + rowsAffected);

            pstmt.close();
            conn.close();
            return rowsAffected > 0 ? 1 : 0;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return 0;
        }
    }


    public int update_publisher() {
    	try {
            Connection conn = DriverManager.getConnection(
                "jdbc:mysql://34.57.40.219:3306/CCINFO209DB?useTimezone=true&serverTimezone=UTC&user=root&password=DLSU1234!"
            );
            System.out.println("Connection to DB Successful");

            // Check if the publisher exists
            PreparedStatement checkStmt = conn.prepareStatement(
                "SELECT COUNT(*) AS count FROM publishers WHERE publisher_name=?"
            );
            checkStmt.setString(1, publisherHolder);
            ResultSet rs = checkStmt.executeQuery();
            rs.next();
            int count = rs.getInt("count");
            rs.close();
            checkStmt.close();

            if (count == 0) {
                System.out.println("Publisher does not exist in the database. Update terminated.");
                conn.close();
                return 0;
            }

            PreparedStatement pstmt = conn.prepareStatement(
                "UPDATE publishers SET publisher_name=?, addressLine1=?, addressLine2=?, contact_email=?, phone_number=? WHERE publisher_name=?"
            );
            pstmt.setString(1, publisherName);
            pstmt.setString(2, publisherAddressLine1);
            pstmt.setString(3, publisherAddressLine2);
            pstmt.setString(4, publisherContactEmail);
            pstmt.setString(5, publisherPhoneNumber);
            pstmt.setString(6, publisherHolder); // Use publisherHolder to identify the publisher
            System.out.println("SQL Statement Prepared");

            int rowsAffected = pstmt.executeUpdate();
            System.out.println("Publisher record updated, rows affected: " + rowsAffected);

            pstmt.close();
            conn.close();
            return rowsAffected > 0 ? 1 : 0;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return 0;
        }
    }
    public int update_publisherID() {
    	try {
            Connection conn = DriverManager.getConnection(
                "jdbc:mysql://34.57.40.219:3306/CCINFO209DB?useTimezone=true&serverTimezone=UTC&user=root&password=DLSU1234!"
            );
            System.out.println("Connection to DB Successful");

            // Check if the publisher exists
            PreparedStatement checkStmt = conn.prepareStatement(
                "SELECT COUNT(*) AS count FROM publishers WHERE publisher_ID=?"
            );
            checkStmt.setInt(1, publisherID);
            ResultSet rs = checkStmt.executeQuery();
            rs.next();
            int count = rs.getInt("count");
            rs.close();
            checkStmt.close();

            if (count == 0) {
                System.out.println("Publisher does not exist in the database. Update terminated.");
                conn.close();
                return 0;
            }

            PreparedStatement pstmt = conn.prepareStatement(
                "UPDATE publishers SET publisher_name=?, addressLine1=?, addressLine2=?, contact_email=?, phone_number=? WHERE publisher_ID=?"
            );
            pstmt.setString(1, publisherName);
            pstmt.setString(2, publisherAddressLine1);
            pstmt.setString(3, publisherAddressLine2);
            pstmt.setString(4, publisherContactEmail);
            pstmt.setString(5, publisherPhoneNumber);
            pstmt.setString(6, publisherName); // Use publisherName to identify the publisher
            System.out.println("SQL Statement Prepared");

            int rowsAffected = pstmt.executeUpdate();
            System.out.println("Publisher record updated, rows affected: " + rowsAffected);

            pstmt.close();
            conn.close();
            return rowsAffected > 0 ? 1 : 0;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return 0;
        }
    }
    
    public int getAuthor() {
        int recordCount = 0;
        try {
            Connection conn = DriverManager.getConnection(
                "jdbc:mysql://34.57.40.219:3306/CCINFO209DB?useTimezone=true&serverTimezone=UTC&user=root&password=DLSU1234!"
            );
            System.out.println("Connection to DB Successful");

            // Check if the author exists
            PreparedStatement checkStmt = conn.prepareStatement(
                "SELECT COUNT(*) AS count FROM authors WHERE pen_name=?"
            );
            checkStmt.setString(1, penName);
            ResultSet r = checkStmt.executeQuery();
            r.next();
            int count = r.getInt("count");
            r.close();
            checkStmt.close();

            if (count == 0) {
                System.out.println("Author does not exist in the database. View terminated.");
                conn.close();
                return 0;
            }

            PreparedStatement pstmt = conn.prepareStatement(
                "SELECT * FROM authors WHERE pen_name=?" 
            );
            pstmt.setString(1, penName);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
				firstName 	   		= rs.getString("first_name");
				middleInitial	    = rs.getString("middle_initial");
				lastName 	  		= rs.getString("last_name");
				contactEmail 		= rs.getString("contact_email");
				phoneNumber         = rs.getString("phone_number");
				System.out.println("Record was Retrieved");
			}
            
            pstmt.close();
            conn.close();
            return recordCount;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return 0;
        }
    }
    
    public int getBooksByAuthor() {
        int recordCount = 0;
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://34.57.40.219:3306/CCINFO209DB?useTimezone=true&serverTimezone=UTC&user=root&password=DLSU1234!");
            System.out.println("Connection to DB Successful");

            PreparedStatement pstmt = conn.prepareStatement(
                "SELECT b.ISBN, b.title, b.publication_year, b.stock_quantity," +
                "GROUP_CONCAT(bg.genre SEPARATOR ', ') AS genres " +
                "FROM book b " +
                "JOIN book_authors ba ON b.ISBN = ba.ISBN " +
                "JOIN book_genres bg ON b.ISBN = bg.ISBN " +
                "JOIN genres g ON bg.genre = g.genre " +
                "WHERE ba.pen_name = ?" +
                "GROUP BY b.ISBN "
            );
            pstmt.setString(1, penName);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                recordCount++;
                ISBN = rs.getString("ISBN");
                bookTitle = rs.getString("title");
                publicationDate = rs.getString("publication_year");
                stockQuantity = rs.getInt("stock_quantity");
                bookGenre = rs.getString("genres");
                System.out.println("Book Retrieved: " + bookTitle + " (Genre: " + bookGenre + ")");
            }

            pstmt.close();
            conn.close();
            return recordCount;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return 0;
        }
    }

    public int getPublisher() {
    	int recordCount = 0;
    	try {
            Connection conn = DriverManager.getConnection(
                "jdbc:mysql://34.57.40.219:3306/CCINFO209DB?useTimezone=true&serverTimezone=UTC&user=root&password=DLSU1234!"
            );

            PreparedStatement pstmt = conn.prepareStatement(
                "SELECT * FROM publishers WHERE publisher_name=?" 
            );
            pstmt.setString(1, publisherName);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
            	publisherID						= rs.getInt("publisher_ID");
				publisherAddressLine1 	   		= rs.getString("addressLine1");
				publisherAddressLine2	    	= rs.getString("addressLine2");
				publisherCity 	  				= rs.getString("city");
				publisherContactEmail 			= rs.getString("contact_email");
				publisherPhoneNumber        	= rs.getString("phone_number");
				System.out.println("Record was Retrieved");
			}
            
            pstmt.close();
            conn.close();
            return recordCount;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return 0;
        }
    }
    
    public int getPublisherByID() {
        int recordCount = 0;
        try {
            Connection conn = DriverManager.getConnection(
                "jdbc:mysql://34.57.40.219:3306/CCINFO209DB?useTimezone=true&serverTimezone=UTC&user=root&password=DLSU1234!"
            );

            PreparedStatement pstmt = conn.prepareStatement(
                "SELECT * FROM publishers WHERE publisher_ID=?" 
            );
            pstmt.setInt(1, publisherID);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
            	publisherID						= rs.getInt("publisher_ID");
            	publisherName					= rs.getString("publisher_name");
				publisherAddressLine1 	   		= rs.getString("addressLine1");
				publisherAddressLine2	    	= rs.getString("addressLine2");
				publisherCity 	  				= rs.getString("city");
				publisherContactEmail 			= rs.getString("contact_email");
				publisherPhoneNumber        	= rs.getString("phone_number");
				System.out.println("Record was Retrieved");
			}
            
            pstmt.close();
            conn.close();
            return recordCount;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return 0;
        }
    }
    
    public int getBooksByPublisher() {
        int recordCount = 0;
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://34.57.40.219:3306/CCINFO209DB?useTimezone=true&serverTimezone=UTC&user=root&password=DLSU1234!");
            System.out.println("Connection to DB Successful");

            PreparedStatement pstmt = conn.prepareStatement(
                "SELECT b.ISBN, b.title, b.publication_year, b.stock_quantity, " +
                "GROUP_CONCAT(g.genre SEPARATOR ', ') AS genres " +
                "FROM books b " +
                "JOIN book_genres bg ON b.ISBN = bg.ISBN " +
                "JOIN genres g ON bg.genre = g.genre " +
                "WHERE b.publisher_name = ?" +
                "GROUP BY b.ISBN"
            );
            pstmt.setString(1, publisherName);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                recordCount++;
                ISBN = rs.getString("ISBN");
                bookTitle = rs.getString("title");
                publicationDate = rs.getString("publication_year");
                stockQuantity = rs.getInt("stock_quantity");
                bookGenre = rs.getString("genres");
                System.out.println("Book Retrieved: " + bookTitle + " (Genre: " + bookGenre + ")");
            }

            pstmt.close();
            conn.close();
            return recordCount;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return 0;
        }
    }
    
    public int getBooksByPublisherID() {
        int recordCount = 0;
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://34.57.40.219:3306/CCINFO209DB?useTimezone=true&serverTimezone=UTC&user=root&password=DLSU1234!");
            System.out.println("Connection to DB Successful");

            PreparedStatement pstmt = conn.prepareStatement(
                "SELECT b.ISBN, b.title, b.publication_year, b.stock_quantity, " +
                "GROUP_CONCAT(g.genre SEPARATOR ', ') AS genres " +
                "FROM books b " +
                "JOIN book_genres bg ON b.ISBN = bg.ISBN " +
                "JOIN genres g ON bg.genre = g.genre " +
                "WHERE b.publisher_ID = ?" +
                "GROUP BY b.ISBN"
            );
            pstmt.setString(1, publisherName);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                recordCount++;
                ISBN = rs.getString("ISBN");
                bookTitle = rs.getString("title");
                publicationDate = rs.getString("publication_year");
                stockQuantity = rs.getInt("stock_quantity");
                bookGenre = rs.getString("genres");
                System.out.println("Book Retrieved: " + bookTitle + " (Genre: " + bookGenre + ")");
            }

            pstmt.close();
            conn.close();
            return recordCount;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return 0;
        }
    }

    public int deleteAuthor() {
        try {
        	Connection conn = DriverManager.getConnection("jdbc:mysql://34.57.40.219:3306/CCINFO209DB?useTimezone=true&serverTimezone=UTC&user=root&password=DLSU1234!");
            System.out.println("Connection to DB Successful");

            PreparedStatement pstmt = conn.prepareStatement("DELETE FROM authors WHERE pen_name = ?");
            pstmt.setString(1, penName);
            pstmt.executeUpdate();
            System.out.println("Author record deleted");

            pstmt.close();
            conn.close();
            return 1;

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return 0;
        }
    }

    public int deletePublisher() {
        try {
        	Connection conn = DriverManager.getConnection("jdbc:mysql://34.57.40.219:3306/CCINFO209DB?useTimezone=true&serverTimezone=UTC&user=root&password=DLSU1234!");
            System.out.println("Connection to DB Successful");

            PreparedStatement pstmt = conn.prepareStatement("DELETE FROM publishers WHERE publisher_name = ?");
            pstmt.setString(1, publisherName);
            pstmt.executeUpdate();
            System.out.println("Publisher record deleted");

            pstmt.close();
            conn.close();
            return 1;

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return 0;
        }
    }
    public int deletePublisherID() {
        try {
        	Connection conn = DriverManager.getConnection("jdbc:mysql://34.57.40.219:3306/CCINFO209DB?useTimezone=true&serverTimezone=UTC&user=root&password=DLSU1234!");
            System.out.println("Connection to DB Successful");

            PreparedStatement pstmt = conn.prepareStatement("DELETE FROM publishers WHERE publisher_name = ?");
            pstmt.setString(1, publisherName);
            pstmt.executeUpdate();
            System.out.println("Publisher record deleted");

            pstmt.close();
            conn.close();
            return 1;

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return 0;
        }
    }
	    public int publisherNameChoice() {
	    	 try {
	             Connection conn = DriverManager.getConnection(
	                 "jdbc:mysql://34.57.40.219:3306/CCINFO209DB?useTimezone=true&serverTimezone=UTC&user=root&password=DLSU1234!"
	             );
	             System.out.println("Connection to DB Successful");
	
	             // Check if the publisher exists
	             PreparedStatement checkStmt = conn.prepareStatement(
	                 "SELECT COUNT(*) AS count FROM publishers WHERE publisher_name=?"
	             );
	             checkStmt.setString(1, publisherName);
	             ResultSet r = checkStmt.executeQuery();
	             r.next();
	             int count = r.getInt("count");
	             r.close();
	             checkStmt.close();
	
	             if (count == 0) {
	                 System.out.println("Publisher does not exist in the database. Update terminated.");
	                 conn.close();
	                 return 0;
	             } 
	             return 1;
	    } catch (Exception e) {
	        System.out.println(e.getMessage());
	        return 0;
	    }
	}
	    public int publisherIDChoice() {
	    	 try {
	             Connection conn = DriverManager.getConnection(
	                 "jdbc:mysql://34.57.40.219:3306/CCINFO209DB?useTimezone=true&serverTimezone=UTC&user=root&password=DLSU1234!"
	             );
	             System.out.println("Connection to DB Successful");
	
	             // Check if the publisher exists
	             PreparedStatement checkStmt = conn.prepareStatement(
	                 "SELECT COUNT(*) AS count FROM publishers WHERE publisher_name=?"
	             );
	             checkStmt.setString(1, publisherName);
	             ResultSet r = checkStmt.executeQuery();
	             r.next();
	             int count = r.getInt("count");
	             r.close();
	             checkStmt.close();
	
	             if (count == 0) {
	                 System.out.println("Publisher does not exist in the database. Update terminated.");
	                 conn.close();
	                 return 0;
	             } 
	             return 1;
	    } catch (Exception e) {
	        System.out.println(e.getMessage());
	        return 0;
	    }
	}
	    public int AuthorVerify() {
		    	 try {
		             Connection conn = DriverManager.getConnection(
		                 "jdbc:mysql://34.57.40.219:3306/CCINFO209DB?useTimezone=true&serverTimezone=UTC&user=root&password=DLSU1234!"
		             );
		             System.out.println("Connection to DB Successful");
		
		             // Check if the publisher exists
		             PreparedStatement checkStmt = conn.prepareStatement(
		                 "SELECT COUNT(*) AS count FROM authors WHERE pen_name=?"
		             );
		             checkStmt.setString(1, penName);
		             ResultSet r = checkStmt.executeQuery();
		             r.next();
		             int count = r.getInt("count");
		             r.close();
		             checkStmt.close();
		
		             if (count == 0) {
		                 System.out.println("Author does not exist in the database. Update terminated.");
		                 conn.close();
		                 return 0;
		             } 
		             return 1;
		    } catch (Exception e) {
		        System.out.println(e.getMessage());
		        return 0;
		    }
	    }
	    
}
