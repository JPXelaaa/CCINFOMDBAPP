import java.sql.*;

public class transaction_report {
	
	public int year;
	public int month;
	public int recordcount;
	
	public transaction_report() {
	}

	public int generate_transactionreport () {
		recordcount = 0;
		try {
			Connection conn;
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/dbsales?useTimezone=true&serverTimezone=UTC&user=root&password=..Vesperia90..");
			System.out.println("Connection to DB Successful");
            PreparedStatement pstmt = conn.prepareStatement(
										                    "SELECT b.bookstore_name, " +
										                    "       b.city, " +
										                    "       b.phone_number, " +
										                    "       o.order_number, " +
										                    "       o.order_date, " +
										                    "       SUM(od.quantity_ordered) AS total_quantity, " +
										                    "FROM   orders o " +
										                    "JOIN   order_details od ON o.order_number = od.order_number " +
										                    "JOIN   book bk ON od.ISBN = bk.ISBN " +
										                    "JOIN   bookstore b ON o.bookstore_ID = b.bookstore_ID " +
										                    "WHERE  YEAR(o.order_date) = ? " +
										                    "AND    MONTH(o.order_date) = ? " +
										                    "GROUP BY b.bookstore_name, o.order_number " +
										                    "ORDER BY b.bookstore_name, o.order_date;"
										                    );
			pstmt.setInt(1, year);
			pstmt.setInt(2, month);
			System.out.println("SQL Statement Prepared");
			ResultSet rs = pstmt.executeQuery();
			
			System.out.printf("%-20s %-15s %-15s %-15s %-15s %-15s %-15s\n", 
                    "Customer Name", "City", "Phone Number", "Order Number", 
                    "Order Date", "Total Quantity", "Total Sales");
			
			 while (rs.next()) {
	                System.out.printf("%-50s %-50s %-11s %-15s %-15s %-5d\n",
	                        rs.getString("bookstore_name"),
	                        rs.getString("city"),
	                        rs.getString("phone_number"),
	                        rs.getString("order_number"),
	                        rs.getDate("order_date"),
	                        rs.getInt("total_quantity"));
	            }
	            System.out.println("End of Report");

			pstmt.close();
			conn.close();
			return recordcount;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return 0;
		}
	}
}
