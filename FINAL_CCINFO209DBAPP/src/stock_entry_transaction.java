import java.sql.*;

public class stock_entry_transaction {

    // Stock Information
    public int bookID;
    public int publisherID;
    public int stockQuantity;

    public int validationStatus;

    public stock_entry_transaction() {
        bookID = 0;
        publisherID = 0;
        stockQuantity = 0;
        validationStatus = 0;
    }

    private void validatePublisherAndBook() {
        try {
            validationStatus = 0; // Reset validation status
            Connection conn = DriverManager.getConnection(
                "jdbc:mysql://34.57.40.219:3306/CCINFO209DB?useTimezone=true&serverTimezone=UTC&user=root&password=DLSU1234!"
            );
            System.out.println("Connection to DB Successful");

            // Validate if publisher_ID exists in publishers table
            PreparedStatement pstmtPublisher = conn.prepareStatement(
                "SELECT COUNT(*) AS count FROM publishers WHERE publisher_ID = ?"
            );
            pstmtPublisher.setInt(1, publisherID);
            ResultSet rsPublisher = pstmtPublisher.executeQuery();
            rsPublisher.next(); // Move to the first result
            if (rsPublisher.getInt("count") == 0) {
                System.out.println("Validation failed: Publisher ID does not exist.");
                validationStatus = 1;
            }

            // Validate if book_ID exists in books table
            PreparedStatement pstmtBook = conn.prepareStatement(
                "SELECT COUNT(*) AS count FROM books WHERE book_ID = ?"
            );
            pstmtBook.setInt(1, bookID);
            ResultSet rsBook = pstmtBook.executeQuery();
            rsBook.next(); // Move to the first result
            if (rsBook.getInt("count") == 0) {
                System.out.println("Validation failed: Book ID does not exist.");
                validationStatus = 1;
            }

            // Close resources
            rsPublisher.close();
            pstmtPublisher.close();
            rsBook.close();
            pstmtBook.close();
            conn.close();

        } catch (Exception e) {
            System.out.println("Validation error: " + e.getMessage());
            validationStatus = 1;
        }
    }

    public int addStockEntry() {
        validatePublisherAndBook();
        if (validationStatus == 1) {
            return 0; // Exit early if validation fails
        }

        try {
            Connection conn = DriverManager.getConnection(
                "jdbc:mysql://34.57.40.219:3306/CCINFO209DB?useTimezone=true&serverTimezone=UTC&user=root&password=DLSU1234!"
            );
            System.out.println("Connection to DB Successful");

            String isbn = publisherID + "-" + bookID;

            PreparedStatement pstmt = conn.prepareStatement(
                "INSERT INTO publisher_books (publisher_ID, book_ID, stock_quantity, ISBN) VALUES (?, ?, ?, ?)"
            );
            pstmt.setInt(1, publisherID);
            pstmt.setInt(2, bookID);
            pstmt.setInt(3, stockQuantity);
            pstmt.setString(4, isbn);
            pstmt.executeUpdate();
            System.out.println("Stock entry added successfully.");

            pstmt.close();
            conn.close();
            return 1;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return 0;
        }
    }

    public int updateStockQuantity() {
        validatePublisherAndBook();
        if (validationStatus == 1) {
            return 0; // Exit early if validation fails
        }

        try {
            Connection conn = DriverManager.getConnection(
                "jdbc:mysql://34.57.40.219:3306/CCINFO209DB?useTimezone=true&serverTimezone=UTC&user=root&password=DLSU1234!"
            );
            System.out.println("Connection to DB Successful");

            String isbn = publisherID + "-" + bookID;

            // Update the stock quantity and ISBN in the publisher_books table
            PreparedStatement pstmt = conn.prepareStatement(
                "UPDATE publisher_books SET stock_quantity = ?, ISBN = ? WHERE publisher_ID = ? AND book_ID = ?"
            );
            pstmt.setInt(1, stockQuantity);
            pstmt.setString(2, isbn);
            pstmt.setInt(3, publisherID);
            pstmt.setInt(4, bookID);
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Stock quantity updated successfully.");
            } else {
                System.out.println("No record found for the given Publisher ID and Book ID.");
            }

            pstmt.close();
            conn.close();
            return rowsAffected;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return 0;
        }
    }

    public int deleteStockEntry() {
        validatePublisherAndBook();
        if (validationStatus == 1) {
            return 0; // Exit early if validation fails
        }

        try {
            Connection conn = DriverManager.getConnection(
                "jdbc:mysql://34.57.40.219:3306/CCINFO209DB?useTimezone=true&serverTimezone=UTC&user=root&password=DLSU1234!"
            );
            System.out.println("Connection to DB Successful");

            PreparedStatement pstmt = conn.prepareStatement(
                "DELETE FROM publisher_books WHERE publisher_ID = ? AND book_ID = ?"
            );
            pstmt.setInt(1, publisherID);
            pstmt.setInt(2, bookID);
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Stock entry deleted successfully.");
            } else {
                System.out.println("No record found for the given Publisher ID and Book ID.");
            }

            pstmt.close();
            conn.close();
            return rowsAffected;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return 0;
        }
    }

    public int viewStockInformation() {
        validatePublisherAndBook();
        if (validationStatus == 1) {
            return 0; // Exit early if validation fails
        }

        try {
            Connection conn = DriverManager.getConnection(
                "jdbc:mysql://34.57.40.219:3306/CCINFO209DB?useTimezone=true&serverTimezone=UTC&user=root&password=DLSU1234!"
            );
            System.out.println("Connection to DB Successful");

            PreparedStatement pstmt = conn.prepareStatement(
                "SELECT * FROM publisher_books WHERE publisher_ID = ? AND book_ID = ?"
            );
            pstmt.setInt(1, publisherID);
            pstmt.setInt(2, bookID);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                System.out.println("========================================");
                System.out.println("Publisher ID: " + rs.getInt("publisher_ID"));
                System.out.println("Book ID: " + rs.getInt("book_ID"));
                System.out.println("Stock Quantity: " + rs.getInt("stock_quantity"));
                System.out.println("ISBN: " + rs.getString("ISBN"));
                System.out.println("========================================");
            } else {
                System.out.println("No stock information found for the given Publisher ID and Book ID.");
            }

            pstmt.close();
            conn.close();
            return 1;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return 0;
        }
    }
    public void displayPublishers() {
        try {
            Connection conn = DriverManager.getConnection(
                "jdbc:mysql://34.57.40.219:3306/CCINFO209DB?useTimezone=true&serverTimezone=UTC&user=root&password=DLSU1234!"
            );
            System.out.println("Connection to DB Successful");

            PreparedStatement pstmt = conn.prepareStatement(
                "SELECT publisher_ID, publisher_name FROM publishers"
            );
            ResultSet rs = pstmt.executeQuery();

            System.out.println("========================================");
            System.out.println("Existing Publishers:");
            while (rs.next()) {
                System.out.println("Publisher ID: " + rs.getInt("publisher_ID") +
                                   ", Publisher Name: " + rs.getString("publisher_name"));
            }
            System.out.println("========================================");

            rs.close();
            pstmt.close();
            conn.close();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    public void displayBooksByPublisher() {
        try {
            Connection conn = DriverManager.getConnection(
                "jdbc:mysql://34.57.40.219:3306/CCINFO209DB?useTimezone=true&serverTimezone=UTC&user=root&password=DLSU1234!"
            );
            System.out.println("Connection to DB Successful");

            PreparedStatement pstmt = conn.prepareStatement(
                "SELECT b.book_ID, b.title " +
                "FROM books b " +
                "JOIN publisher_books pb ON b.book_ID = pb.book_ID " +
                "WHERE pb.publisher_ID = ?"
            );
            pstmt.setInt(1, publisherID);
            ResultSet rs = pstmt.executeQuery();

            System.out.println("========================================");
            System.out.println("Books Available for Publisher ID " + publisherID + ":");
            boolean found = false;
            while (rs.next()) {
                found = true;
                System.out.println("Book ID: " + rs.getInt("book_ID") +
                                   ", Title: " + rs.getString("title"));
            }
            if (!found) {
                System.out.println("No books found for the given Publisher ID.");
            }
            System.out.println("========================================");

            rs.close();
            pstmt.close();
            conn.close();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    public void displayBooks() {
        try {
            Connection conn = DriverManager.getConnection(
                "jdbc:mysql://34.57.40.219:3306/CCINFO209DB?useTimezone=true&serverTimezone=UTC&user=root&password=DLSU1234!"
            );
            System.out.println("Connection to DB Successful");

            PreparedStatement pstmt = conn.prepareStatement(
                "SELECT book_ID, title FROM books"
            );
            ResultSet rs = pstmt.executeQuery();

            System.out.println("========================================");
            System.out.println("Existing Books:");
            while (rs.next()) {
                System.out.println("Book ID: " + rs.getInt("book_ID") +
                                   ", Title: " + rs.getString("title"));
            }
            System.out.println("========================================");

            rs.close();
            pstmt.close();
            conn.close();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    public void viewStockByPublisher() {
        try {
            Connection conn = DriverManager.getConnection(
                "jdbc:mysql://34.57.40.219:3306/CCINFO209DB?useTimezone=true&serverTimezone=UTC&user=root&password=DLSU1234!"
            );
            System.out.println("Connection to DB Successful");

            PreparedStatement pstmt = conn.prepareStatement(
                "SELECT b.book_ID, b.title, p.publisher_ID, p.publisher_name, pb.stock_quantity " +
                "FROM books b " +
                "JOIN publisher_books pb ON b.book_ID = pb.book_ID " +
                "JOIN publishers p ON pb.publisher_ID = p.publisher_ID " +
                "WHERE p.publisher_ID = ?"
            );
            pstmt.setInt(1, publisherID);
            ResultSet rs = pstmt.executeQuery();

            System.out.println("========================================");
            System.out.println("Stock Information for Publisher ID: " + publisherID);
            while (rs.next()) {
                System.out.println("Book ID: " + rs.getInt("book_ID") +
                                   ", Title: " + rs.getString("title") +
                                   ", Publisher Name: " + rs.getString("publisher_name") +
                                   ", Stock Quantity: " + rs.getInt("stock_quantity"));
            }
            System.out.println("========================================");

            rs.close();
            pstmt.close();
            conn.close();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void viewStockByBook() {
        try {
            Connection conn = DriverManager.getConnection(
                "jdbc:mysql://34.57.40.219:3306/CCINFO209DB?useTimezone=true&serverTimezone=UTC&user=root&password=DLSU1234!"
            );
            System.out.println("Connection to DB Successful");

            PreparedStatement pstmt = conn.prepareStatement(
                "SELECT p.publisher_ID, p.publisher_name, b.book_ID, b.title, pb.stock_quantity " +
                "FROM publishers p " +
                "JOIN publisher_books pb ON p.publisher_ID = pb.publisher_ID " +
                "JOIN books b ON pb.book_ID = b.book_ID " +
                "WHERE b.book_ID = ?"
            );
            pstmt.setInt(1, bookID);
            ResultSet rs = pstmt.executeQuery();

            System.out.println("========================================");
            System.out.println("Stock Information for Book ID: " + bookID);
            while (rs.next()) {
                System.out.println("Publisher ID: " + rs.getInt("publisher_ID") +
                                   ", Publisher Name: " + rs.getString("publisher_name") +
                                   ", Book Title: " + rs.getString("title") +
                                   ", Stock Quantity: " + rs.getInt("stock_quantity"));
            }
            System.out.println("========================================");

            rs.close();
            pstmt.close();
            conn.close();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

}
