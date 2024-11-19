import java.sql.*;

public class returnProcess {
	
	public String 	return_ID;
	public String	return_date; 
	public String	return_reason;
	public String	bookstore_ID;
	
	public int		ISBN;
	public int 		quantity_returned;
	
	public returnProcess() {
		return_ID			= "";
		return_date 		= "";
		return_reason		= "";
		bookstore_ID 		= "";
				
		ISBN				= 0;
		quantity_returned	= 0;
	}
	
	
	// all functions below are for return table
		public int add_returnRecord() {
			try {
				Connection conn = DriverManager.getConnection("jdbc:mysql://34.57.40.219:3306/CCINFO209DB?useTimezone=true&serverTimezone=UTC&user=root&password=DLSU1234!");
				System.out.println("Connection to DB Successful");
				PreparedStatement pstmt = conn.prepareStatement(
						"INSERT INTO return (return_ID, return_date, return_reason, bookstore_ID) VALUES (?,?,?,?)"
						);
				pstmt.setString(1, return_ID);
				pstmt.setString(2, return_date);
				pstmt.setString(3, return_reason); 
				pstmt.setString(4, bookstore_ID);
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
		
		public int update_returnRecord() {
			try {
				Connection conn = DriverManager.getConnection("jdbc:mysql://34.57.40.219:3306/CCINFO209DB?useTimezone=true&serverTimezone=UTC&user=root&password=DLSU1234!");
				System.out.println("Connection to DB Successful");
				PreparedStatement pstmt = conn.prepareStatement("UPDATE return SET return_date=?, return_reason=?, bookstore_ID=? WHERE return_ID=?");
				
				pstmt.setString(1, return_date);
				pstmt.setString(2, return_reason);
				pstmt.setString(3, bookstore_ID);
				pstmt.setString(4, return_ID);
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
		
		public int delete_returnRecord() {
			try {
				Connection conn = DriverManager.getConnection("jdbc:mysql://34.57.40.219:3306/CCINFO209DB?useTimezone=true&serverTimezone=UTC&user=root&password=DLSU1234!");
				System.out.println("Connection to DB Successful");
				PreparedStatement pstmt = conn.prepareStatement("DELETE FROM return WHERE return_ID=?");
				pstmt.setString(1, return_ID);
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
		
		public int get_returnRecord() {
			int recordcount = 0;
			try {
				Connection conn = DriverManager.getConnection("jdbc:mysql://34.57.40.219:3306/CCINFO209DB?useTimezone=true&serverTimezone=UTC&user=root&password=DLSU1234!");
				System.out.println("Connection to DB Successful");
				PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM return WHERE return_ID=?");
				pstmt.setString(1, return_ID);
				System.out.println("SQL Statement Prepared");
				ResultSet rs = pstmt.executeQuery();
				while (rs.next()) {
					recordcount++;
					return_date 	   = rs.getString("return_date");
					return_reason  	   = rs.getString("return_reason");
					bookstore_ID       = rs.getString("bookstore_ID");
					
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
		
		// all functions below are for return_details table
		public int add_orderDetails() {
			try {
				Connection conn = DriverManager.getConnection("jdbc:mysql://34.57.40.219:3306/CCINFO209DB?useTimezone=true&serverTimezone=UTC&user=root&password=DLSU1234!");
				System.out.println("Connection to DB Successful");
				PreparedStatement pstmt = conn.prepareStatement(
						"INSERT INTO return_details (return_ID, ISBN, quantity_returned) VALUES (?,?,?)"
						);
				pstmt.setString(1, return_ID);
				pstmt.setInt(2, ISBN);
				pstmt.setInt(3, quantity_returned); 
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
				PreparedStatement pstmt = conn.prepareStatement("UPDATE return_details SET quantity_returned=? WHERE return_ID=? AND ISBN=?");
				
				pstmt.setInt(1, quantity_returned);
				pstmt.setString(2, return_ID);
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
		
		public int delete_returnDetails() {
			try {
				Connection conn = DriverManager.getConnection("jdbc:mysql://34.57.40.219:3306/CCINFO209DB?useTimezone=true&serverTimezone=UTC&user=root&password=DLSU1234!");
				System.out.println("Connection to DB Successful");
				PreparedStatement pstmt = conn.prepareStatement("DELETE FROM return_details WHERE return_ID=? AND ISBN=?");
				pstmt.setString(1, return_ID);
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
		
		public int get_returnDetails() {
			int recordcount = 0;
			try {
				Connection conn = DriverManager.getConnection("jdbc:mysql://34.57.40.219:3306/CCINFO209DB?useTimezone=true&serverTimezone=UTC&user=root&password=DLSU1234!");
				System.out.println("Connection to DB Successful");
				PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM return_details WHERE return_ID=? AND ISBN=?");
				pstmt.setString(1, return_ID);
				pstmt.setInt(2, ISBN);
				System.out.println("SQL Statement Prepared");
				ResultSet rs = pstmt.executeQuery();
				while (rs.next()) {
					recordcount++;
					quantity_returned	   	= rs.getInt("quantity_returned");
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

