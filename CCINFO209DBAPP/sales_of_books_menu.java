import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class book_activity_report {
	
	public int year;
	public int month;
	
	
	public book_activity_report() {
		
	}
	
	
	public void generate_bookact_report() {
		String reportQuery = ("SELECT		b.title,"
							+ "				bs.bookstore_name,"
							+ "				ROUND(SUM(od.quantity_ordered), 2)		AS  totalquantity\r\n"
							+ "FROM			books b		JOIN publisher_books pb		ON	b.book_ID = pb.book_ID\r\n"
							+ "							JOIN order_details od		ON	pb.book_ID = od.book_ID\r\n"
							+ "							JOIN orders o				ON	od.order_number = o.order_number\r\n"
							+ "							JOIN bookstores bs			ON	o.bookstore_ID = bs.bookstore_ID\r\n"
							+ "WHERE		YEAR(o.order_date) = ? \r\n"
							+ "AND			MONTH(o.order_date) = ? \r\n"
							+ "GROUP BY		b.title, bs.bookstore_name\r\n"
							+ "ORDER BY		b.title\r\n");
		

		
		try (Connection conn = DriverManager.getConnection("jdbc:mysql://34.57.40.219:3306/CCINFO209DB?useTimezone=true&serverTimezone=UTC&user=root&password=DLSU1234!")) {
            System.out.println("Connected to the database successfully");

            PreparedStatement reportStmt = conn.prepareStatement(reportQuery);
            reportStmt.setInt(1, year);
            reportStmt.setInt(2, month);
            System.out.println("SQL Statement Prepared");

            ResultSet rs = reportStmt.executeQuery();
            System.out.printf("%-45s %-40s %-15s\n", "Title", "Bookstore Name", "Total Quantity");
            System.out.println("--------------------------------------------------------------------------------------------------------");

            while (rs.next()) {
                System.out.printf("%-45s %-45s %-10.2f\n",
                        rs.getString("title"),
                        rs.getString("bookstore_name"),
                        rs.getFloat("totalquantity"));
            }

            System.out.println("\nEnd of Report");
            rs.close();
            reportStmt.close();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
