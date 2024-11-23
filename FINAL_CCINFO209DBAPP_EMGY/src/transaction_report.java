import java.sql.*;
import java.util.Scanner;
public class transaction_report {
	Scanner scanner = new Scanner(System.in);
    public int year;
    public int month;
    public int recordcount;

    public transaction_report() {
    }

    public int generate_transactionreport() {
        recordcount = 0;
        System.out.println("=== Transaction Report ===");
        System.out.print("Enter the year: ");
        year = scanner.nextInt();

        System.out.print("Enter the month (1-12): ");
        month = scanner.nextInt();
        try {
            Connection conn = DriverManager.getConnection(
            	"jdbc:mysql://34.57.40.219:3306/CCINFO209DB?useTimezone=true&serverTimezone=UTC&user=root&password=DLSU1234!"
            );
            System.out.println("Connection to DB Successful");

            // Updated SQL query to filter by year and month
            PreparedStatement pstmt = conn.prepareStatement(
											                "SELECT b.bookstore_ID, " +
											                "       b.bookstore_name, " +
											                "       b.phone_number, " +
											                "       o.order_number, " +
											                "       o.order_date, " +
											                "       SUM(od.quantity_ordered) AS total_quantity " +
											                "FROM   orders o " +
											                "JOIN   order_details od ON o.order_number = od.order_number " +
											                "JOIN   publisher_books pb ON od.book_id = pb.book_id " + 
											                "JOIN   books bk ON pb.book_id = bk.book_id " + 
											                "JOIN   bookstores b ON o.bookstore_id = b.bookstore_id " + 
											                "WHERE  YEAR(o.order_date) = ? " +
											                "AND    MONTH(o.order_date) = ? " +
											                "GROUP BY b.bookstore_ID, o.order_number " +
											                "ORDER BY b.bookstore_name, o.order_date;"
											            );
            pstmt.setInt(1, year);
            pstmt.setInt(2, month);
            System.out.println("SQL Statement Prepared");

            ResultSet rs = pstmt.executeQuery();

            // Output headers for the report
            System.out.printf("%-15s %-30s %-15s %-15s %-15s %-10s\n", 
                              "Bookstore ID", "Bookstore Name", "Phone Number", "Order Number", 
                              "Order Date", "Total Quantity");

            while (rs.next()) {
                System.out.printf("%-15s %-30s %-15s %-15s %-15s %-10d\n",
                                  rs.getString("bookstore_ID"),
                                  rs.getString("bookstore_name"),
                                  rs.getString("phone_number"),
                                  rs.getString("order_number"),
                                  rs.getDate("order_date"),
                                  rs.getInt("total_quantity"));
                recordcount++;
            }
            System.out.println("End of Report");

            pstmt.close();
            conn.close();           
            return 0;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return 0;
        }
       
    }
}
