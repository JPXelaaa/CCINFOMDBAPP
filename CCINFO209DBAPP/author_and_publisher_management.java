import java.sql.*;

public class author_and_publisher_management {

    // Author
    public String authorID;
    public String authorFirstName;
    public String authorLastName;
    public String authorPenName;

    // Publisher
    public String publisherID;
    public String publisherName;
    public String publisherContactNumber;
    public String publisherLocation;

    // Book Information
    public String bookID;
    public String bookTitle;
    public String bookGenre;
    public String publicationDate;
    public int stockQuantity;

    public author_and_publisher_management() {
        authorID = "";
        authorFirstName = "";
        authorLastName = "";
        authorPenName = "";
        publisherID = "";
        publisherName = "";
        publisherContactNumber = "";
        publisherLocation = "";
        bookID = "";
        bookTitle = "";
        bookGenre = "";
        publicationDate = "";
        stockQuantity = 0;
    }

    public int addAuthor() {
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/dbsales?useTimezone=true&serverTimezone=UTC&user=root&password=yourpassword");
            System.out.println("Connection to DB Successful");

            PreparedStatement pstmt = conn.prepareStatement("INSERT INTO Author (author_ID, first_name, last_name, pen_name) VALUES (?, ?, ?, ?)");
            pstmt.setString(1, authorID);
            pstmt.setString(2, authorFirstName);
            pstmt.setString(3, authorLastName);
            pstmt.setString(4, authorPenName);
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
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/dbsales?useTimezone=true&serverTimezone=UTC&user=root&password=yourpassword");
            System.out.println("Connection to DB Successful");

            PreparedStatement pstmt = conn.prepareStatement("INSERT INTO Publisher (publisher_ID, publisher_name, publisher_phone, publisher_address) VALUES (?, ?, ?, ?)");
            pstmt.setString(1, publisherID);
            pstmt.setString(2, publisherName);
            pstmt.setString(3, publisherContactNumber);
            pstmt.setString(4, publisherLocation);
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

    public int getBooksByAuthor() {
        int recordCount = 0;
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/dbsales?useTimezone=true&serverTimezone=UTC&user=root&password=yourpassword");
            System.out.println("Connection to DB Successful");

            PreparedStatement pstmt = conn.prepareStatement(
                "SELECT b.book_ID, b.title, b.publication_date, b.stock_quantity " +
                "FROM Book b " +
                "JOIN BookAuthor ba ON b.book_ID = ba.book_ID " +
                "WHERE ba.author_ID = ?");
            pstmt.setString(1, authorID);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                recordCount++;
                bookID = rs.getString("book_ID");
                bookTitle = rs.getString("title");
                publicationDate = rs.getString("publication_date");
                stockQuantity = rs.getInt("stock_quantity");
                System.out.println("Book Retrieved: " + bookTitle);
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
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/dbsales?useTimezone=true&serverTimezone=UTC&user=root&password=yourpassword");
            System.out.println("Connection to DB Successful");

            PreparedStatement pstmt = conn.prepareStatement(
                "SELECT b.book_ID, b.title, b.publication_date, b.stock_quantity " +
                "FROM Book b " +
                "WHERE b.publisher_ID = ?");
            pstmt.setString(1, publisherID);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                recordCount++;
                bookID = rs.getString("book_ID");
                bookTitle = rs.getString("title");
                publicationDate = rs.getString("publication_date");
                stockQuantity = rs.getInt("stock_quantity");
                System.out.println("Book Retrieved: " + bookTitle);
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
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/dbsales?useTimezone=true&serverTimezone=UTC&user=root&password=yourpassword");
            System.out.println("Connection to DB Successful");

            PreparedStatement pstmt = conn.prepareStatement("DELETE FROM Author WHERE author_ID = ?");
            pstmt.setString(1, authorID);
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
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/dbsales?useTimezone=true&serverTimezone=UTC&user=root&password=yourpassword");
            System.out.println("Connection to DB Successful");

            PreparedStatement pstmt = conn.prepareStatement("DELETE FROM Publisher WHERE publisher_ID = ?");
            pstmt.setString(1, publisherID);
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
}
