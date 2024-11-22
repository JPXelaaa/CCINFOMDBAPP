import java.sql.*;

public class returnProcess {
	
	public String 	return_ID;
	public String	return_date; 
	public String	return_reason;
	public String	bookstore_ID;
	public String 	return_status;
	public int		book_ID;
	public int		publisher_ID;
	public int 		quantity_returned;
	
	public returnProcess() {
		return_ID			= "";
		return_date 		= "";
		return_reason		= "";
		bookstore_ID 		= "";
		return_status		= "";		
		book_ID				= 0;
		publisher_ID		= 0;
		quantity_returned	= 0;
	}
	
	/*
	public int returnIDChoice() {
	   	 try {
	            Connection conn = DriverManager.getConnection("jdbc:mysql://34.57.40.219:3306/CCINFO209DB?useTimezone=true&serverTimezone=UTC&user=root&password=DLSU1234!");
	            
	            PreparedStatement checkStmt = conn.prepareStatement(
	                "SELECT COUNT(*) AS count FROM returns WHERE return_ID=?"
	            );
	            checkStmt.setString(1, return_ID);
	            ResultSet r = checkStmt.executeQuery();
	            r.next();
	            int count = r.getInt("count");
	            r.close();
	            checkStmt.close();

	            if (count == 0) {
	                System.out.println("Return Record does not exist in the database. Update terminated.");
	                conn.close();
	                return 0;
	            } 
	            
	            return 1;
	   	 } catch (Exception e) {
	   		 System.out.println(e.getMessage());
	   		 return 0;
	   	 }
	   }
		*/
		public int add_returnRecord() {
			try {		
				Connection conn = DriverManager.getConnection("jdbc:mysql://34.57.40.219:3306/CCINFO209DB?useTimezone=true&serverTimezone=UTC&user=root&password=DLSU1234!");
				System.out.println("Connection to DB Successful");
				PreparedStatement pstmt = conn.prepareStatement(
		                "INSERT INTO returns (return_ID, return_date, return_reason, return_status, bookstore_ID) VALUES (?, CURDATE(), ?, PROCESSING, ?)");
		        pstmt.setString(1, return_ID);
		        pstmt.setString(2, return_reason);
		        pstmt.setString(3, bookstore_ID);
		        pstmt.executeUpdate(); 
				
		        PreparedStatement dStmt = conn.prepareStatement(
		                "INSERT INTO return_details (return_ID book_ID, publisher_ID, quantity_returned) VALUES (?, ?, ?, ?)");
		        dStmt.setString(1, return_ID);
		        dStmt.setInt(2, book_ID);
		        dStmt.setInt(3, publisher_ID);
		        dStmt.setInt(4, quantity_returned);
		        dStmt.executeUpdate();
		        
		        System.out.println("Return Record and Details were created, and stock updated");
		        pstmt.close();
		        dStmt.close();
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
		
				PreparedStatement pstmt = conn.prepareStatement("UPDATE returns SET return_status=? WHERE return_ID=?");
				pstmt.setString(1, return_status);
				pstmt.setString(2, return_ID);
				System.out.println("SQL Statement Prepared");
				pstmt.executeUpdate();
				System.out.println("Return Record has been updated");
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
				
				PreparedStatement dStmt = conn.prepareStatement("DELETE FROM return_details WHERE return_ID = ?");
			    dStmt.setString(1, return_ID);
			    dStmt.executeUpdate();
			    
			    PreparedStatement pstmt = conn.prepareStatement("DELETE FROM returns WHERE return_ID = ?");
		        pstmt.setString(1, return_ID);
		        pstmt.executeUpdate();
			        
		        System.out.println("Return Record and Details were deleted");
		        dStmt.close();
		        pstmt.close();
		        conn.close();
		        return 1;
		    } catch (Exception e) {
		        System.out.println(e.getMessage());
		        return 0;
			}
		}
		
		public int get_returnRecordByBook() {
			int recordcount = 0;
			try {
				
				
				Connection conn = DriverManager.getConnection("jdbc:mysql://34.57.40.219:3306/CCINFO209DB?useTimezone=true&serverTimezone=UTC&user=root&password=DLSU1234!");
				System.out.println("Connection to DB Successful");
				
				 PreparedStatement pstmt = conn.prepareStatement(
			                "SELECT r.return_ID, "
			                + "		r.return_date, "
			                + "		r.return_reason, "
			                + "		r.return_status,"
			                + "		rd.quantity_returned" 
			                + "FROM returns r 		JOIN return_details rd ON r.return_ID = rd.return_ID " 
			                + "WHERE rd.book_ID = ?");
			        pstmt.setInt(1, book_ID);
			        
				System.out.println("SQL Statement Prepared");
				ResultSet rs = pstmt.executeQuery();
				while (rs.next()) {
					recordcount++;
					return_ID		   = rs.getString("return_ID");
					return_date 	   = rs.getString("return_date");
					return_reason  	   = rs.getString("return_reason");
					return_status	   = rs.getString("return_status");
					quantity_returned  = rs.getInt("quantity_returned");
					
					System.out.println("Return Record was Retrieved");
				}
				pstmt.close();
				conn.close();
				return recordcount;
			} catch (Exception e) {
				System.out.println(e.getMessage());
				return 0;
			}
		}
		
		public int get_returnRecordByPublisher() {
			int recordcount = 0;
			try {
				Connection conn = DriverManager.getConnection("jdbc:mysql://34.57.40.219:3306/CCINFO209DB?useTimezone=true&serverTimezone=UTC&user=root&password=DLSU1234!");
				System.out.println("Connection to DB Successful");

				PreparedStatement pstmt = conn.prepareStatement(
		                "SELECT r.return_ID, "
		                + "		r.return_date, "
		                + "		rd.book_ID, "
		                + "		r.return_reason, "
		                + "		r.return_status,"
		                + "		rd.quantity_returned " 
		                + "FROM returns r 		JOIN return_details rd ON r.return_ID = rd.return_ID " 
		                + "WHERE rd.publisher_ID = ?");
		        pstmt.setInt(1, publisher_ID);
		        
				System.out.println("SQL Statement Prepared");
				ResultSet rs = pstmt.executeQuery();
				while (rs.next()) {
					recordcount++;
					return_ID		   = rs.getString("return_ID");
					return_date 	   = rs.getString("return_date");
					book_ID			   = rs.getInt("book_ID");
					return_reason  	   = rs.getString("return_reason");
					return_status	   = rs.getString("return_status");
					quantity_returned  = rs.getInt("quantity_returned");
					
					System.out.println("Return Record was Retrieved");
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

