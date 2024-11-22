import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class bookgenre_report {
	
	Scanner scanner = new Scanner(System.in);
	public int year;
	public int month;
	public int recordcount;
	
	public bookgenre_report() {
	}

	public int generate_bookgenrereport () {
		recordcount = 0;
		
		System.out.print("Enter the year: ");
        year = scanner.nextInt();

        System.out.print("Enter the month (1-12): ");
        month = scanner.nextInt();
		try {
			Connection conn = DriverManager.getConnection("jdbc:mysql://34.57.40.219:3306/CCINFO209DB?useTimezone=true&serverTimezone=UTC&user=root&password=DLSU1234!");
			System.out.println("Connection to DB Successful");
			PreparedStatement pstmt = conn.prepareStatement("SELECT 	 g.genre, \r\n"
														  + "            SUM(od.quantity_ordered) 					AS numberOfBooksSold\r\n" 
														  + "FROM		 genres g		 	JOIN book_genres bg		ON g.genre = bg.genre\r\n"
														  + "								JOIN books b			ON bg.book_ID = b.book_ID\r\n"
														  + "								JOIN publisher_books pb ON b.book_ID = pb.book_ID\r\n"
														  + "								JOIN order_details od 	ON pb.book_ID = od.book_ID\r\n"
														  + "								JOIN orders o			ON od.order_number = o.order_number\r\n"
														  + "WHERE		 YEAR(o.order_date) = ? \r\n"
														  + "AND		 MONTH(o.order_date) = ? \r\n"
														  + "GROUP BY	 g.genre\r\n"
														  + "ORDER BY	 numberOfBooksSold DESC;"); 

			pstmt.setInt(1, year);
			pstmt.setInt(2, month);
			System.out.println("SQL Statement Prepared\n");
			ResultSet rs = pstmt.executeQuery();
			
			while (rs.next()) {
				recordcount++;
				System.out.printf("%-30s %d\n",rs.getString("genre"), rs.getInt("numberOfBooksSold"));
			}
			System.out.println("\nEnd of Report");

			pstmt.close();
			conn.close();
			return recordcount;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return 0;
		}
	}
}
