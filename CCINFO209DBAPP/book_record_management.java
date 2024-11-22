import java.sql.*;

public class book_record_management {

    public int bookID;
    public String title;
    public String pen_name;
    public String genre;
    public int yearWritten;;
    
    public boolean check_authors(String[] authors) {
    	String query 			   = "SELECT COUNT(*) FROM authors WHERE pen_name = ?";
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://34.57.40.219:3306/CCINFO209DB?useTimezone=true&serverTimezone=UTC&user=root&password=DLSU1234!")) {
        	for (String author : authors) {
                author = author.trim();
                PreparedStatement stmt = conn.prepareStatement(query);
                stmt.setString(1, author);
                ResultSet rs = stmt.executeQuery();
                if (rs.next() && rs.getInt(1) == 0) {
                    System.out.println("Author not found: " + author);
                    return false;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean check_genres(String[] genres) {
        String query 			   = "SELECT COUNT(*) FROM genres WHERE genre = ?";
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://34.57.40.219:3306/CCINFO209DB?useTimezone=true&serverTimezone=UTC&user=root&password=DLSU1234!")) {
        	for (String genre : genres) {
                genre = genre.trim();
                PreparedStatement stmt = conn.prepareStatement(query);
                stmt.setString(1, genre);
                ResultSet rs = stmt.executeQuery();
                if (rs.next() && rs.getInt(1) == 0) {
                    System.out.println("Genre not found: " + genre);
                    return false;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
    
    public boolean check_book(int bookID) {
        String query 			   = "SELECT COUNT(*) FROM books WHERE book_ID = ?";
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://34.57.40.219:3306/CCINFO209DB?useTimezone=true&serverTimezone=UTC&user=root&password=DLSU1234!")) {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, bookID);
            ResultSet rs = stmt.executeQuery();
            if (rs.next() && rs.getInt(1) == 0) {
                System.out.println("Book not found: " + bookID);
                return false;
            }
        	
        } catch (Exception e) {
        	e.printStackTrace();
            return false;
        }
        return true;
    }

    public void add_book(String[] authors, String[] genres) {
        String insertBookQuery 		= 	"INSERT INTO books (book_ID, title, year_written) VALUES (?, ?, ?)";
        String insertAuthorQuery 	= 	"INSERT INTO book_authors (pen_name, book_ID) VALUES (?, ?)";
        String insertGenreQuery 	= 	"INSERT INTO book_genres (genre, book_ID) VALUES (?, ?)";
        
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://34.57.40.219:3306/CCINFO209DB?useTimezone=true&serverTimezone=UTC&user=root&password=DLSU1234!")) {
            System.out.println("Connected to the database successfully");
       

            try (PreparedStatement insertBookStmt = conn.prepareStatement(insertBookQuery)) {
                insertBookStmt.setInt(1, bookID);
                insertBookStmt.setString(2, title);
                insertBookStmt.setInt(3, yearWritten);
                
                insertBookStmt.executeUpdate();
                System.out.println("Record was created successfully");
            }
            
            for (String author : authors) {
                try (PreparedStatement insertAuthorStmt = conn.prepareStatement(insertAuthorQuery)) {
                    insertAuthorStmt.setString(1, author.trim());
                    insertAuthorStmt.setInt(2, bookID);
                    insertAuthorStmt.executeUpdate();
                }
            }

            for (String genre : genres) {
                try (PreparedStatement insertGenreStmt = conn.prepareStatement(insertGenreQuery)) {
                    insertGenreStmt.setString(1, genre.trim());
                    insertGenreStmt.setInt(2, bookID);
                    insertGenreStmt.executeUpdate();
                }
            }

       

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());

        }
    }



    public void update_book(String[] authors, String[] genres) {
        String bookUpdateQuery 		= "UPDATE books SET title = ?, year_written = ? WHERE book_ID = ?";
        String deleteAuthorsQuery 	= "DELETE FROM book_authors WHERE book_ID = ?";
        String deleteGenresQuery 	= "DELETE FROM book_genres WHERE book_ID = ?";
        String insertAuthorQuery 	= "INSERT INTO book_authors (pen_name, book_ID) VALUES (?, ?)";
        String insertGenreQuery 	= "INSERT INTO book_genres (genre, book_ID) VALUES (?, ?)";

        if (!check_authors(authors)) {
            System.out.println("One or more authors do not exist. Update aborted.");
            return;
        }
        if (!check_genres(genres)) {
            System.out.println("One or more genres do not exist. Update aborted.");
            return;
        }

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://34.57.40.219:3306/CCINFO209DB?useTimezone=true&serverTimezone=UTC&user=root&password=DLSU1234!")) {
            System.out.println("Connected to the database successfully");

            // Update book information
            PreparedStatement updateStmt = conn.prepareStatement(bookUpdateQuery);
            updateStmt.setString(1, title);
            updateStmt.setInt(2, yearWritten);
            updateStmt.setInt(3, bookID);
            updateStmt.executeUpdate();

            // Delete existing authors and genres links
            PreparedStatement deleteAuthorsStmt = conn.prepareStatement(deleteAuthorsQuery);
            deleteAuthorsStmt.setInt(1, bookID);
            deleteAuthorsStmt.executeUpdate();

            PreparedStatement deleteGenresStmt = conn.prepareStatement(deleteGenresQuery);
            deleteGenresStmt.setInt(1, bookID);
            deleteGenresStmt.executeUpdate();

            // Insert updated authors and genres
            for (String author : authors) {
                try (PreparedStatement insertAuthorStmt = conn.prepareStatement(insertAuthorQuery)) {
                    insertAuthorStmt.setString(1, author.trim());
                    insertAuthorStmt.setInt(2, bookID);
                    insertAuthorStmt.executeUpdate();
                }
            }

            for (String genre : genres) {
                try (PreparedStatement insertGenreStmt = conn.prepareStatement(insertGenreQuery)) {
                    insertGenreStmt.setString(1, genre.trim());
                    insertGenreStmt.setInt(2, bookID);
                    insertGenreStmt.executeUpdate();
                }
            }

            System.out.println("Book updated successfully.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }





    public void delete_book(){
        String deletePublisherBooksQuery = "DELETE FROM publisher_books WHERE book_ID = ?";
        String deleteQuery 			= "DELETE FROM books WHERE book_ID = ?";
        String deleteAuthorsQuery 	= "DELETE FROM book_authors WHERE book_ID = ?";
        String deleteGenresQuery 	= "DELETE FROM book_genres WHERE book_ID = ?";
        
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://34.57.40.219:3306/CCINFO209DB?useTimezone=true&serverTimezone=UTC&user=root&password=DLSU1234!")){
            System.out.println("Connected to the database successfully");
            
            PreparedStatement deletePublisherBooksStmt = conn.prepareStatement(deletePublisherBooksQuery);
            deletePublisherBooksStmt.setInt(1, bookID);
            deletePublisherBooksStmt.executeUpdate();

            PreparedStatement deleteAuthorsStmt = conn.prepareStatement(deleteAuthorsQuery);
            deleteAuthorsStmt.setInt(1, bookID);
            deleteAuthorsStmt.executeUpdate();

            PreparedStatement deleteGenresStmt = conn.prepareStatement(deleteGenresQuery);
            deleteGenresStmt.setInt(1, bookID);
            deleteGenresStmt.executeUpdate();
            
        try (PreparedStatement ps = conn.prepareStatement(deleteQuery)) {
            ps.setInt(1, bookID);
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Book with BookID " + bookID + " deleted successfully.");
            } else {
                System.out.println("No book found with BookID " + bookID + ".");
            }
        }
    } catch (Exception e) {
        System.out.println("Error: " + e.getMessage());
        e.printStackTrace();
    }
}



    public boolean get_book() {
        String getQuery 			= "SELECT * FROM books WHERE book_ID = ?";
       
        
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://34.57.40.219:3306/CCINFO209DB?useTimezone=true&serverTimezone=UTC&user=root&password=DLSU1234!")) {
            System.out.println("Connected to the database successfully");
        	try (PreparedStatement ps = conn.prepareStatement(getQuery)) {
                ps.setInt(1, bookID);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        title = rs.getString("title");
                        yearWritten = rs.getInt("year_written");
                        return true;
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Error retrieving book: " + e.getMessage());
            e.printStackTrace();
        }
        return false; // Book not found
    }



    public String get_authors() {
        String authorsQuery 		= "SELECT pen_name FROM book_authors WHERE book_ID = ?";
        StringBuilder authors 		= new StringBuilder();

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://34.57.40.219:3306/CCINFO209DB?useTimezone=true&serverTimezone=UTC&user=root&password=DLSU1234!")) {
            try (PreparedStatement ps = conn.prepareStatement(authorsQuery)) {
                ps.setInt(1, bookID);
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        if (authors.length() > 0) {
                            authors.append(", ");
                        }
                        authors.append(rs.getString("pen_name"));
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving authors: " + e.getMessage());
            e.printStackTrace();
        }

        return authors.length() > 0 ? authors.toString() : "No authors found";
    }

    public String get_genres() {
        String genresQuery 		   = "SELECT genre FROM book_genres WHERE book_ID = ?";
        StringBuilder genres 	   = new StringBuilder();

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://34.57.40.219:3306/CCINFO209DB?useTimezone=true&serverTimezone=UTC&user=root&password=DLSU1234!")) {
            
        	try (PreparedStatement ps = conn.prepareStatement(genresQuery)) {
                ps.setInt(1, bookID);
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        if (genres.length() > 0) {
                            genres.append(", ");
                        }
                        genres.append(rs.getString("genre"));
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving genres: " + e.getMessage());
            e.printStackTrace();
        }
        return genres.length() > 0 ? genres.toString() : "No genres found";
    }
    
    
    public void displayExistingBookIDs() {
	    String query 				  = "SELECT book_ID, title FROM books";

	    try (Connection conn = DriverManager.getConnection("jdbc:mysql://34.57.40.219:3306/CCINFO209DB?useTimezone=true&serverTimezone=UTC&user=root&password=DLSU1234!")) {
	        PreparedStatement stmt = conn.prepareStatement(query);
	        ResultSet rs = stmt.executeQuery();

	        System.out.printf("%-10s %-50s\n", "Book ID", "Title");
	        System.out.println("------------------------------------------------------------");

	        while (rs.next()) {
	            System.out.printf("%-10d %-50s\n", rs.getInt("book_ID"), rs.getString("title"));
	        }

	        stmt.close();
	        conn.close();
	    } catch (Exception e) {
	        System.out.println("Error retrieving Book IDs and Titles: " + e.getMessage());
	    }
	}
}
