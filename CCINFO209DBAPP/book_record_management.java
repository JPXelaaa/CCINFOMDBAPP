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
        System.out.println("Connected to the database successfully");
        String query = "SELECT COUNT(*) FROM Author WHERE pen_name = ?";
        try (Connection conn = DatabaseConnection.getConnection("")) {
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
        String query = "SELECT COUNT(*) FROM BookGenre WHERE genre = ?";
        try (Connection conn = DatabaseConnection.getConnection()) {
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

    public void add_book() {
        String checkPublisherQuery = "SELECT COUNT(*) FROM Publisher WHERE publisher_name = ?";
        String insertBookQuery = "INSERT INTO books (ISBN, publisher_name, title, publication_year, stock_quantity) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection("your_connection_string_here")) {
            System.out.println("Connected to the database successfully");

            // Check if publisher exists
            try (PreparedStatement checkPublisherStmt = conn.prepareStatement(checkPublisherQuery)) {
                checkPublisherStmt.setString(1, publisherName);
                try (ResultSet rs = checkPublisherStmt.executeQuery()) {
                    if (rs.next() && rs.getInt(1) == 0) {
                        System.out.println("Error: Publisher ID " + publisherName + " does not exist. Book cannot be added.");
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



        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());

        }
    }



    public void update_book(String[] authors, String[] genres) {
        String bookUpdateQuery = "UPDATE Book SET ISBN = ?, title = ?, publisher_ID = ?, publication_date = ?, stockQuantity = ? WHERE ISBN = ?";
        String deleteAuthorsQuery = "DELETE FROM BookAuthor WHERE ISBN = ?";
        String deleteGenresQuery = "DELETE FROM BookGenre WHERE ISBN = ?";
        String insertAuthorQuery = "INSERT INTO BookAuthor (pen_name, ISBN) VALUES (?, ?)";
        String insertGenreQuery = "INSERT INTO BookGenre (ISBN, genre) VALUES (?, ?)";
        String checkPublisherQuery = "SELECT COUNT(*) FROM Publisher WHERE publisher_name = ?";

        // Check if authors and genres exist before proceeding
        if (!check_authors(authors)) {
            System.out.println("One or more authors do not exist. Update aborted.");
            return;
        }
        if (!check_genres(genres)) {
            System.out.println("One or more genres do not exist. Update aborted.");
            return;
        }

        try (Connection conn = DatabaseConnection.getConnection("")) {

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
        String deleteQuery = "DELETE FROM Book WHERE ISBN = ?";

        try (Connection conn = DatabaseConnection.getConnection("")){
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
        String getQuery = "SELECT * FROM Book WHERE ISBN = ?";
        try (Connection conn = DatabaseConnection.getConnection("")) {
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
        String authorsQuery = "SELECT pen_name FROM BookAuthor WHERE ISBN = ?";
        StringBuilder authors = new StringBuilder();

        try (Connection conn = DatabaseConnection.getConnection("")) {
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
        String genresQuery = "SELECT genre FROM BookGenre WHERE ISBN = ?";
        StringBuilder genres = new StringBuilder();

        try (Connection conn = DatabaseConnection.getConnection("")) {
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
