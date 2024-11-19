import java.sql.*;

public class book_record_management {

    public int ISBN;
    public String title;
    public String pen_name;
    public String publisherName;
    public String genre;
    public int publicationYear;;
    public int stockQuantity;

    public boolean check_authors(String[] authors) {
    String query = "SELECT COUNT(*) FROM author WHERE pen_name = ?";
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://34.57.40.219:3306/CCINFO209DB?useTimezone=true&serverTimezone=UTC&user=root&password=DLSU1234!")) {
        	System.out.println("Connected to the database successfully");
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
        String query = "SELECT COUNT(*) FROM genre WHERE genre = ?";
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

    public void add_book(String[] authors, String[] genres) {
        String checkPublisherQuery = "SELECT COUNT(*) FROM publisher WHERE publisher_name = ?";
        String insertBookQuery = "INSERT INTO book (ISBN, title, publisher_name, publication_year, stock_quantity) VALUES (?, ?, ?, ?, ?)";
        String insertAuthorQuery = "INSERT INTO book_author (pen_name, ISBN) VALUES (?, ?)";
        String insertGenreQuery = "INSERT INTO book_genre (genre, ISBN) VALUES (?, ?)";
        
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://34.57.40.219:3306/CCINFO209DB?useTimezone=true&serverTimezone=UTC&user=root&password=DLSU1234!")) {
            System.out.println("Connected to the database successfully");
            // Check if publisher exists
            try (PreparedStatement checkPublisherStmt = conn.prepareStatement(checkPublisherQuery)) {
                checkPublisherStmt.setString(1, publisherName);
                try (ResultSet rs = checkPublisherStmt.executeQuery()) {
                    if (rs.next() && rs.getInt(1) == 0) {
                        System.out.println("Error: Publisher " + publisherName + " does not exist. Book cannot be added.");
                        return;
                    }
                }
            }
            
       

            // Insert the book if publisher exists
            try (PreparedStatement insertBookStmt = conn.prepareStatement(insertBookQuery)) {
                insertBookStmt.setInt(1, ISBN);
                insertBookStmt.setString(2, title);
                insertBookStmt.setString(3, publisherName);
                insertBookStmt.setInt(4, publicationYear);
                insertBookStmt.setInt(5, stockQuantity);

                System.out.println("SQL Statement Prepared");
                insertBookStmt.executeUpdate();
                System.out.println("Record was created successfully");
            }
            
         // Insert updated authors and genres
            for (String author : authors) {
                try (PreparedStatement insertAuthorStmt = conn.prepareStatement(insertAuthorQuery)) {
                    insertAuthorStmt.setString(1, author.trim());
                    insertAuthorStmt.setInt(2, ISBN);
                    insertAuthorStmt.executeUpdate();
                }
            }

            for (String genre : genres) {
                try (PreparedStatement insertGenreStmt = conn.prepareStatement(insertGenreQuery)) {
                    insertGenreStmt.setString(1, genre.trim());
                    insertGenreStmt.setInt(2, ISBN);
                    insertGenreStmt.executeUpdate();
                }
            }



        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());

        }
    }



    public void update_book(String[] authors, String[] genres) {
        String bookUpdateQuery = "UPDATE book SET ISBN = ?, title = ?, publisher_name = ?, publication_date = ?, stockQuantity = ? WHERE ISBN = ?";
        String deleteAuthorsQuery = "DELETE FROM book_author WHERE ISBN = ?";
        String deleteGenresQuery = "DELETE FROM book_genre WHERE ISBN = ?";
        String insertAuthorQuery = "INSERT INTO book_author (pen_name, ISBN) VALUES (?, ?)";
        String insertGenreQuery = "INSERT INTO book_genre (ISBN, genre) VALUES (?, ?)";
        String checkPublisherQuery = "SELECT COUNT(*) FROM publisher WHERE publisher_name = ?";

        // Check if authors and genres exist before proceeding
        if (!check_authors(authors)) {
            System.out.println("One or more authors do not exist. Update aborted.");
            return;
        }
        if (!check_genres(genres)) {
            System.out.println("One or more genres do not exist. Update aborted.");
            return;
        }

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://34.57.40.219:3306/CCINFO209DB?useTimezone=true&serverTimezone=UTC&user=root&password=DLSU1234!")) {

            // Check if publisher exists
            try (PreparedStatement checkPublisherStmt = conn.prepareStatement(checkPublisherQuery)) {
                checkPublisherStmt.setString(1, publisherName);
                try (ResultSet rs = checkPublisherStmt.executeQuery()) {
                    if (rs.next() && rs.getInt(1) == 0) {
                        System.out.println("Error: Publisher " + publisherName + " does not exist. Book update aborted.");
                        return;
                    }
                }
            }

            // Update book information
            PreparedStatement updateStmt = conn.prepareStatement(bookUpdateQuery);
            updateStmt.setInt(1, ISBN);
            updateStmt.setString(2, title);
            updateStmt.setString(3, publisherName);
            updateStmt.setInt(4, publicationYear);
            updateStmt.setInt(5, stockQuantity);
            updateStmt.setInt(6, ISBN); // Assuming the WHERE clause uses ISBN to find the book to update
            updateStmt.executeUpdate();

            // Delete existing authors and genres links
            PreparedStatement deleteAuthorsStmt = conn.prepareStatement(deleteAuthorsQuery);
            deleteAuthorsStmt.setInt(1, ISBN);
            deleteAuthorsStmt.executeUpdate();

            PreparedStatement deleteGenresStmt = conn.prepareStatement(deleteGenresQuery);
            deleteGenresStmt.setInt(1, ISBN);
            deleteGenresStmt.executeUpdate();

            // Insert updated authors and genres
            for (String author : authors) {
                try (PreparedStatement insertAuthorStmt = conn.prepareStatement(insertAuthorQuery)) {
                    insertAuthorStmt.setString(1, author.trim());
                    insertAuthorStmt.setInt(2, ISBN);
                    insertAuthorStmt.executeUpdate();
                }
            }

            for (String genre : genres) {
                try (PreparedStatement insertGenreStmt = conn.prepareStatement(insertGenreQuery)) {
                    insertGenreStmt.setString(1, genre.trim());
                    insertGenreStmt.setInt(2, ISBN);
                    insertGenreStmt.executeUpdate();
                }
            }

            System.out.println("Book updated successfully.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }





    public void delete_book(){
        String deleteQuery = "DELETE FROM book WHERE ISBN = ?";

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://34.57.40.219:3306/CCINFO209DB?useTimezone=true&serverTimezone=UTC&user=root&password=DLSU1234!")){
            System.out.println("Connected to the database successfully");
        try (PreparedStatement ps = conn.prepareStatement(deleteQuery)) {
            ps.setInt(1, ISBN);
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Book with ISBN " + ISBN + " deleted successfully.");
            } else {
                System.out.println("No book found with ISBN " + ISBN + ".");
            }
        }
    } catch (Exception e) {
        System.out.println("Error: " + e.getMessage());
        e.printStackTrace();
    }
}



    public boolean get_book() {
        String getQuery = "SELECT * FROM book WHERE ISBN = ?";
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://34.57.40.219:3306/CCINFO209DB?useTimezone=true&serverTimezone=UTC&user=root&password=DLSU1234!")) {
            try (PreparedStatement ps = conn.prepareStatement(getQuery)) {
                ps.setInt(1, ISBN);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        title = rs.getString("title");
                        publisherName = rs.getString("publisher_name");
                        publicationYear = rs.getInt("publication_year");
                        stockQuantity = rs.getInt("stock_quantity");
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
        String authorsQuery = "SELECT pen_name FROM book_author WHERE ISBN = ?";
        StringBuilder authors = new StringBuilder();

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://34.57.40.219:3306/CCINFO209DB?useTimezone=true&serverTimezone=UTC&user=root&password=DLSU1234!")) {
            try (PreparedStatement ps = conn.prepareStatement(authorsQuery)) {
                ps.setInt(1, ISBN);
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
        String genresQuery = "SELECT genre FROM book_genre WHERE ISBN = ?";
        StringBuilder genres = new StringBuilder();

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://34.57.40.219:3306/CCINFO209DB?useTimezone=true&serverTimezone=UTC&user=root&password=DLSU1234!")) {
            try (PreparedStatement ps = conn.prepareStatement(genresQuery)) {
                ps.setInt(1, ISBN);
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
}
