import java.sql.*;

public class orderProcess {
	
	public String 	order_number;
	public String	bookstore_ID; 
	public String	order_date;
	public String	required_date;
	public String 	shipped_date;
	public String	status;
	public String 	remarks;
	
	public int		ISBN;
	public int 		quantity_ordered;
	
	public orderProcess() {
		order_number 		= "";
		bookstore_ID		= "";
		order_date			= "";
		required_date 		= "";
		shipped_date 		= "";
		status  			= "";
		remarks				= "";
		
		ISBN				= 0;
		quantity_ordered	= 0;
	}
	
	
	// all functions below are for orders table
		public int add_orderRecord() {
			try {
				Connection conn = DriverManager.getConnection("jdbc:mysql://34.57.40.219:3306/CCINFO209DB?useTimezone=true&serverTimezone=UTC&user=root&password=DLSU1234!");
				System.out.println("Connection to DB Successful");
				PreparedStatement pstmt = conn.prepareStatement(
						"INSERT INTO orders (order_number, bookstore_ID, order_date, required_date, shipped_date, status, remarks) VALUES (?,?,?,?,?,?,?)"
						);
				pstmt.setString(1, order_number);
				pstmt.setString(2, bookstore_ID);
				pstmt.setString(3, order_date); 
				pstmt.setString(4, required_date);
				pstmt.setString(5, shipped_date);
				pstmt.setString(6, status); 
				pstmt.setString(7, remarks);
				System.out.println("SQL Statement Prepared");
				pstmt.executeUpdate();
				System.out.println("Record was created");
				pstmt.close();
				conn.close();
				return 1; 
			} catch (Exception e) {
				System.out.println(e.getMessage());
				return 0;
			}
		}
		
		public int update_orderRecord() {
			try {
				Connection conn = DriverManager.getConnection("jdbc:mysql://34.57.40.219:3306/CCINFO209DB?useTimezone=true&serverTimezone=UTC&user=root&password=DLSU1234!");
				System.out.println("Connection to DB Successful");
				PreparedStatement pstmt = conn.prepareStatement("UPDATE orders SET bookstore_ID=?, order_date=?, required_date=?, shipped_date=?, status=?, remarks=? WHERE order_number=?");
				
				pstmt.setString(1, bookstore_ID);
				pstmt.setString(2, order_date);
				pstmt.setString(3, required_date);
				pstmt.setString(4, shipped_date);
				pstmt.setString(5, status);
				pstmt.setString(6, remarks);
				pstmt.setString(7, order_number);
				System.out.println("SQL Statement Prepared");
				pstmt.executeUpdate();
				System.out.println("Record was updated");
				pstmt.close();
				conn.close();
				return 1;
			} catch (Exception e) {
				System.out.println(e.getMessage());
				return 0;
			}
		}
		
		public int delete_orderRecord() {
			try {
				Connection conn = DriverManager.getConnection("jdbc:mysql://34.57.40.219:3306/CCINFO209DB?useTimezone=true&serverTimezone=UTC&user=root&password=DLSU1234!");
				System.out.println("Connection to DB Successful");
				PreparedStatement pstmt = conn.prepareStatement("DELETE FROM order WHERE order_number=?");
				pstmt.setString(1, order_number);
				System.out.println("SQL Statement Prepared");
				pstmt.executeUpdate();
				System.out.println("Record was deleted");
				pstmt.close();
				conn.close();
				return 1;
			} catch (Exception e) {
				System.out.println(e.getMessage());
				return 0;
			}
		}
		
		public int get_orderRecord() {
			int recordcount = 0;
			try {
				Connection conn = DriverManager.getConnection("jdbc:mysql://34.57.40.219:3306/CCINFO209DB?useTimezone=true&serverTimezone=UTC&user=root&password=DLSU1234!");
				System.out.println("Connection to DB Successful");
				PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM orders WHERE order_number=?");
				pstmt.setString(1, order_number);
				System.out.println("SQL Statement Prepared");
				ResultSet rs = pstmt.executeQuery();
				while (rs.next()) {
					recordcount++;
					bookstore_ID 	   = rs.getString("bookstore_ID");
					order_date  	   = rs.getString("order_date");
					required_date      = rs.getString("required_date");
					shipped_date 	   = rs.getString("shipped_date");
					status       	   = rs.getString("status");
					remarks	   		   = rs.getString("remarks");
					
					System.out.println("Record was Retrieved");
				}
				pstmt.close();
				conn.close();
				return recordcount;
			} catch (Exception e) {
				System.out.println(e.getMessage());
				return 0;
			}
		}
		
		// all functions below are for order_details table
		public int add_orderDetails() {
			try {
				Connection conn = DriverManager.getConnection("jdbc:mysql://34.57.40.219:3306/CCINFO209DB?useTimezone=true&serverTimezone=UTC&user=root&password=DLSU1234!");
				System.out.println("Connection to DB Successful");
				PreparedStatement pstmt = conn.prepareStatement(
						"INSERT INTO order_details (order_number, ISBN, quantity_ordered) VALUES (?,?,?)"
						);
				pstmt.setString(1, order_number);
				pstmt.setInt(2, ISBN);
				pstmt.setInt(3, quantity_ordered); 
				System.out.println("SQL Statement Prepared");
				pstmt.executeUpdate();
				System.out.println("Record was created");
				pstmt.close();
				conn.close();
				return 1; 
			} catch (Exception e) {
				System.out.println(e.getMessage());
				return 0;
			}
		}
		
		public int update_orderDetails() {
			try {
				Connection conn = DriverManager.getConnection("jdbc:mysql://34.57.40.219:3306/CCINFO209DB?useTimezone=true&serverTimezone=UTC&user=root&password=DLSU1234!");
				System.out.println("Connection to DB Successful");
				PreparedStatement pstmt = conn.prepareStatement("UPDATE order_details SET quantity_ordered=? WHERE order_number=? AND ISBN=?");
				
				pstmt.setInt(1, quantity_ordered);
				pstmt.setString(2, order_number);
				pstmt.setInt(3, ISBN);
				System.out.println("SQL Statement Prepared");
				pstmt.executeUpdate();
				System.out.println("Record was updated");
				pstmt.close();
				conn.close();
				return 1;
			} catch (Exception e) {
				System.out.println(e.getMessage());
				return 0;
			}
		}
		
		public int delete_orderDetails() {
			try {
				Connection conn = DriverManager.getConnection("jdbc:mysql://34.57.40.219:3306/CCINFO209DB?useTimezone=true&serverTimezone=UTC&user=root&password=DLSU1234!");
				System.out.println("Connection to DB Successful");
				PreparedStatement pstmt = conn.prepareStatement("DELETE FROM order_details WHERE order_number=? AND ISBN=?");
				pstmt.setString(1, order_number);
				pstmt.setInt(2, ISBN);
				System.out.println("SQL Statement Prepared");
				pstmt.executeUpdate();
				System.out.println("Record was deleted");
				pstmt.close();
				conn.close();
				return 1;
			} catch (Exception e) {
				System.out.println(e.getMessage());
				return 0;
			}
		}
		
		public int get_orderDetails() {
			int recordcount = 0;
			try {
				Connection conn = DriverManager.getConnection("jdbc:mysql://34.57.40.219:3306/CCINFO209DB?useTimezone=true&serverTimezone=UTC&user=root&password=DLSU1234!");
				System.out.println("Connection to DB Successful");
				PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM order_details WHERE order_number=? AND ISBN=?");
				pstmt.setString(1, order_number);
				pstmt.setInt(2, ISBN);
				System.out.println("SQL Statement Prepared");
				ResultSet rs = pstmt.executeQuery();
				while (rs.next()) {
					recordcount++;
					quantity_ordered	   	= rs.getInt("quantity_ordered");
					System.out.println("Record was Retrieved");
				}
				pstmt.close();
				conn.close();
				return recordcount;
			} catch (Exception e) {
				System.out.println(e.getMessage());
				return 0;
			}
		}
		
}

