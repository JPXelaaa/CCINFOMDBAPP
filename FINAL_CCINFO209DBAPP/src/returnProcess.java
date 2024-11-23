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

	public int add_returnRecord(boolean isFirstReturn) {
	    this.return_status = "P"; 

	    try (Connection conn = DriverManager.getConnection(
	            "jdbc:mysql://34.57.40.219:3306/CCINFO209DB?useTimezone=true&serverTimezone=UTC&user=root&password=DLSU1234!")) {

	        System.out.println("Connection to DB Successful");
	        
	        int quantityOrdered = 0;
	        PreparedStatement validateStmt = conn.prepareStatement(
	                "SELECT quantity_ordered FROM order_details WHERE book_ID = ? AND publisher_ID = ?");
	        validateStmt.setInt(1, book_ID); 
	        validateStmt.setInt(2, publisher_ID); 
	        ResultSet rs = validateStmt.executeQuery();

	        if (rs.next()) {
	            quantityOrdered = rs.getInt("quantity_ordered"); 
	        } else {
	            System.out.println("Error: No order found for the specified book and publisher.");
	            rs.close();
	            validateStmt.close();
	            conn.close();
	            return 0; 
	        }

	        rs.close();
	        validateStmt.close();
	        
	        if (quantity_returned > quantityOrdered) {
	            conn.close();
	            return -1;
	        }

	        if (isFirstReturn) {
	            PreparedStatement pstmt = conn.prepareStatement(
	                    "INSERT INTO returns (return_ID, return_date, return_reason, bookstore_ID, return_status) VALUES (?, CURDATE(), ?, ?, ?)");
	            pstmt.setString(1, return_ID);
	            pstmt.setString(2, return_reason);
	            pstmt.setString(3, bookstore_ID);
	            pstmt.setString(4, this.return_status);
	            pstmt.executeUpdate();
	            pstmt.close();
	        }

	        PreparedStatement dStmt = conn.prepareStatement(
	                "INSERT INTO return_details (return_ID, book_ID, publisher_ID, quantity_returned) VALUES (?, ?, ?, ?)");
	        dStmt.setString(1, return_ID);
	        dStmt.setInt(2, book_ID);
	        dStmt.setInt(3, publisher_ID);
	        dStmt.setInt(4, quantity_returned);
	        dStmt.executeUpdate();
	        dStmt.close();

	        return 1; 

	    } catch (Exception e) {
	    	System.out.println(e.getMessage());
	        return 0; 
	    }
	}

		
		public int update_returnRecord() {
			try {
				Connection conn = DriverManager.getConnection(
		                "jdbc:mysql://34.57.40.219:3306/CCINFO209DB?useTimezone=true&serverTimezone=UTC&user=root&password=DLSU1234!");
		        System.out.println("Connection to DB Successful");

		        PreparedStatement pstmt = conn.prepareStatement("UPDATE returns SET return_status = ? WHERE return_ID = ?");
		        pstmt.setString(1, return_status); 
		        pstmt.setString(2, return_ID);    
		        pstmt.executeUpdate();

		        System.out.println("Return Record has been updated with valid status.");
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
		    int recordCount = 0;

		    try (Connection conn = DriverManager.getConnection(
		            "jdbc:mysql://34.57.40.219:3306/CCINFO209DB?useTimezone=true&serverTimezone=UTC&user=root&password=DLSU1234!")) {

		        System.out.println("Connection to DB Successful");

		        String query = """
		                SELECT DISTINCT r.return_ID, 
		                                r.return_date, 
		                                r.return_reason, 
		                                r.return_status,
		                                rd.quantity_returned
		                FROM returns r
		                JOIN return_details rd ON r.return_ID = rd.return_ID
		                JOIN publisher_books pb ON rd.book_ID = pb.book_ID
		                JOIN books b ON rd.book_ID = b.book_ID
		                WHERE b.book_ID = ?
		                ORDER BY r.return_ID, r.return_date;
		                """;

		        PreparedStatement pstmt = conn.prepareStatement(query);
		        pstmt.setInt(1, book_ID);

		        System.out.println("SQL Statement Prepared");

		        ResultSet rs = pstmt.executeQuery();

		        System.out.println("Return Records for Book ID: " + book_ID);
		        System.out.println("------------------------------------------------------");

		        while (rs.next()) {
		            recordCount++;
		            String returnID = rs.getString("return_ID");
		            String returnDate = rs.getString("return_date");
		            String returnReason = rs.getString("return_reason");
		            String returnStatus = rs.getString("return_status");
		            int quantityReturned = rs.getInt("quantity_returned");
		            
		            System.out.printf(
		                "Return ID: %s, Date: %s, Reason: %s, Status: %s, Quantity Returned: %d%n",
		                returnID, returnDate, returnReason, returnStatus, quantityReturned
		            );
		        }

		        if (recordCount == 0) {
		            System.out.println("No return records found for the specified book.");
		        }

		        System.out.println("------------------------------------------------------");
		        rs.close();
		        pstmt.close();

		    } catch (Exception e) {
		        System.out.println("Error: " + e.getMessage());
		        return 0;
		    }

		    System.out.println("Total Records Found: " + recordCount);
		    return recordCount; 
		}

		public int get_returnRecordByPublisher() {
		    int recordCount = 0;

		    try (Connection conn = DriverManager.getConnection(
		            "jdbc:mysql://34.57.40.219:3306/CCINFO209DB?useTimezone=true&serverTimezone=UTC&user=root&password=DLSU1234!")) {

		        System.out.println("Connection to DB Successful");

		        String query = """
		                SELECT DISTINCT r.return_ID, 
		                                r.return_date, 
		                                rd.book_ID, 
		                                r.return_reason, 
		                                r.return_status,
		                                rd.quantity_returned
		                FROM returns r
		                JOIN return_details rd ON r.return_ID = rd.return_ID
		                WHERE rd.publisher_ID = ?
		                ORDER BY r.return_ID, r.return_date;
		                """;

		        PreparedStatement pstmt = conn.prepareStatement(query);
		        pstmt.setInt(1, publisher_ID);

		        System.out.println("SQL Statement Prepared");

		        ResultSet rs = pstmt.executeQuery();

		        System.out.println("Return Records for Publisher ID: " + publisher_ID);
		        System.out.println("------------------------------------------------------");

		        while (rs.next()) {
		            recordCount++;
		            String returnID = rs.getString("return_ID");
		            String returnDate = rs.getString("return_date");
		            int bookID = rs.getInt("book_ID");
		            String returnReason = rs.getString("return_reason");
		            String returnStatus = rs.getString("return_status");
		            int quantityReturned = rs.getInt("quantity_returned");

		            System.out.printf(
		                "Return ID: %s, Date: %s, Book ID: %d, Reason: %s, Status: %s, Quantity Returned: %d%n",
		                returnID, returnDate, bookID, returnReason, returnStatus, quantityReturned
		            );
		        }

		        if (recordCount == 0) {
		            System.out.println("No return records found for the specified publisher.");
		        }

		        System.out.println("------------------------------------------------------");
		        rs.close();
		        pstmt.close();

		    } catch (Exception e) {
		        System.out.println("Error: " + e.getMessage());
		        return 0; 
		    }

		    System.out.println("Total Records Found: " + recordCount);
		    return recordCount;
		}
		
		public void displayExistingReturnIDs() {
			String query = """
					SELECT r.return_ID, 
		                   bs.bookstore_name, 
		                   r.return_status 
		            FROM returns r
		            JOIN bookstores bs ON r.bookstore_ID = bs.bookstore_ID
		            ORDER BY r.return_ID;
		            """;

		    try (Connection conn = DriverManager.getConnection(
		            "jdbc:mysql://34.57.40.219:3306/CCINFO209DB?useTimezone=true&serverTimezone=UTC&user=root&password=DLSU1234!");
		        PreparedStatement stmt = conn.prepareStatement(query);
		        ResultSet rs = stmt.executeQuery()) {

		        System.out.printf("%-15s %-30s %-20s\n", "Return ID", "Bookstore Name", "Return Status");
		        System.out.println("------------------------------------------------------------");

		        while (rs.next()) {
		            System.out.printf("%-15s %-30s %-20s\n",
		                    rs.getString("return_ID"),
		                    rs.getString("bookstore_name"),
		                    rs.getString("return_status"));
		        }

		    } catch (Exception e) {
		        System.out.println("Error retrieving Return IDs: " + e.getMessage());
		        e.printStackTrace();
		    }
		}
 
		 public void displayExistingBookstoreIDs() {
			 String query = """
			 		SELECT bookstore_ID, 
			 			   bookstore_name 
			        FROM bookstores
			        ORDER BY bookstore_ID;
			        """;

			 try (Connection conn = DriverManager.getConnection(
					 "jdbc:mysql://34.57.40.219:3306/CCINFO209DB?useTimezone=true&serverTimezone=UTC&user=root&password=DLSU1234!");
			 PreparedStatement stmt = conn.prepareStatement(query);
			 ResultSet rs = stmt.executeQuery()) {

			 System.out.printf("%-15s %-30s\n", "Bookstore ID", "Bookstore Name");
			 System.out.println("------------------------------------------------------------");

			 while (rs.next()) {
				 System.out.printf("%-15s %-30s\n",
				 rs.getString("bookstore_ID"),
			     rs.getString("bookstore_name"));
			 }

			 } catch (SQLException e) {
				 System.out.println("Error retrieving Bookstore IDs: " + e.getMessage());
			     e.printStackTrace();
			 }
		}

		 public void displayExistingBookIDsUnderBookstore() {
			    String query = """
			            SELECT b.book_ID, 
			                   b.title, 
			                   bs.bookstore_name, 
			                   SUM(od.quantity_ordered) AS total_quantity_ordered
			            FROM books b
			            JOIN order_details od ON b.book_ID = od.book_ID
			            JOIN orders o ON od.order_number = o.order_number
			            JOIN bookstores bs ON o.bookstore_ID = bs.bookstore_ID
			            GROUP BY b.book_ID, b.title, bs.bookstore_name
			            ORDER BY bs.bookstore_name, b.book_ID;
			            """;

			    try (Connection conn = DriverManager.getConnection(
			            "jdbc:mysql://34.57.40.219:3306/CCINFO209DB?useTimezone=true&serverTimezone=UTC&user=root&password=DLSU1234!");
			         PreparedStatement stmt = conn.prepareStatement(query);
			         ResultSet rs = stmt.executeQuery()) {

			        System.out.printf("%-10s %-50s %-30s %-20s\n", "Book ID", "Title", "Bookstore Name", "Total Ordered");
			        System.out.println("---------------------------------------------------------------------------------------------");

			        while (rs.next()) {
			            System.out.printf("%-10d %-50s %-30s %-20d\n",
			                    rs.getInt("book_ID"),
			                    rs.getString("title"),
			                    rs.getString("bookstore_name"),
			                    rs.getInt("total_quantity_ordered"));
			        }

			    } catch (Exception e) {
			        System.out.println("Error retrieving Book IDs, Titles, and Orders: " + e.getMessage());
			    }
			}
		 
		 public void displayExistingBookIDs() {
			    String query = """
			    		SELECT book_ID, title 
			    		FROM books;
			    		""";

			    try (Connection conn = DriverManager.getConnection("jdbc:mysql://34.57.40.219:3306/CCINFO209DB?useTimezone=true&serverTimezone=UTC&user=root&password=DLSU1234!")) {
			        PreparedStatement stmt = conn.prepareStatement(query);
			        ResultSet rs = stmt.executeQuery();

			        System.out.printf("%-10s %-50s\n", "Book ID", "Title");
			        System.out.println("------------------------------------------------------------");

			        while (rs.next()) {
			            System.out.printf("%-10d %-50s\n", rs.getInt("book_ID"), rs.getString("title"));
			        }

			        stmt.close();
			        conn.close();
			    } catch (Exception e) {
			        System.out.println("Error retrieving Book IDs and Titles: " + e.getMessage());
			    }
			}


		public void displayExistingPublisherIDs() {
			String query = """
		            SELECT publisher_ID, 
		                   publisher_name 
		            FROM publishers
		            ORDER BY publisher_ID;
		            """;
		
		    try (Connection conn = DriverManager.getConnection(
		            "jdbc:mysql://34.57.40.219:3306/CCINFO209DB?useTimezone=true&serverTimezone=UTC&user=root&password=DLSU1234!");
		        PreparedStatement stmt = conn.prepareStatement(query);
		        ResultSet rs = stmt.executeQuery()) {
		
		        System.out.printf("%-15s %-30s\n", "Publisher ID", "Publisher Name");
		        System.out.println("------------------------------------------------------------");
		
		        while (rs.next()) {
		            System.out.printf("%-15d %-30s\n",
		                    rs.getInt("publisher_ID"),
		                    rs.getString("publisher_name"));
		        }
		
		    } catch (Exception e) {
		        System.out.println("Error retrieving Publisher IDs and Names: " + e.getMessage());
		    }
		}

}

