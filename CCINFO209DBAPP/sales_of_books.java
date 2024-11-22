import java.sql.*;
import java.time.LocalDateTime;
import java.sql.Timestamp;


public class sales_of_books {
	
	public String 			order_number;
	public String			bookstoreID; 
	public LocalDateTime	order_date;
	public LocalDateTime	required_date;
	public LocalDateTime 	shipped_date;
	public String			status;
	public String 			remarks;
	
	public int		bookID;
	public int 		quantity_ordered;
	public int		publisherID;
	
	public sales_of_books() {
		
	}
	
	
	public boolean check_book(int bookID) {
        String query 			   = "SELECT COUNT(*) FROM books WHERE book_ID = ?";
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://34.57.40.219:3306/CCINFO209DB?useTimezone=true&serverTimezone=UTC&user=root&password=DLSU1234!")) {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, bookID);
            ResultSet rs = stmt.executeQuery();
            if (rs.next() && rs.getInt(1) == 0) {
                System.out.println("Book not found: " + bookID);
                return false;
            }
        	
        } catch (Exception e) {
        	e.printStackTrace();
            return false;
        }
        return true;
    }
	
	
	
	public boolean check_publisher(int publisherID) {
        String query 			   = "SELECT COUNT(*) FROM publishers WHERE publisher_ID = ?";
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://34.57.40.219:3306/CCINFO209DB?useTimezone=true&serverTimezone=UTC&user=root&password=DLSU1234!")) {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, publisherID);
            ResultSet rs = stmt.executeQuery();
            if (rs.next() && rs.getInt(1) == 0) {
                System.out.println("Publisher not found: " + publisherID);
                return false;
            }
        	
        } catch (Exception e) {
        	e.printStackTrace();
            return false;
        }
        return true;
    }
	
	
	
	public boolean check_bookstore(String bookstoreID) {
        String query 			   = "SELECT COUNT(*) FROM bookstores WHERE bookstore_ID = ?";
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://34.57.40.219:3306/CCINFO209DB?useTimezone=true&serverTimezone=UTC&user=root&password=DLSU1234!")) {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, bookstoreID);
            ResultSet rs = stmt.executeQuery();
            if (rs.next() && rs.getInt(1) == 0) {
                System.out.println("Book not found: " + bookstoreID);
                return false;
            }
        	
        } catch (Exception e) {
        	e.printStackTrace();
            return false;
        }
        return true;
    }
	
	
	
	public String generateOrderNumber() {
		String query 	= "SELECT order_number FROM orders ORDER BY order_number DESC LIMIT 1";
	    try (Connection conn = DriverManager.getConnection("jdbc:mysql://34.57.40.219:3306/CCINFO209DB?useTimezone=true&serverTimezone=UTC&user=root&password=DLSU1234!")){
	    	PreparedStatement stmt = conn.prepareStatement(query);
	    	ResultSet rs = stmt.executeQuery();
	    	if (rs.next()) {
	            String lastOrderNumber = rs.getString("order_number");
	            int lastNumber = Integer.parseInt(lastOrderNumber.substring(3));
	            return String.format("OD_%04d", lastNumber + 1);
	        }
	    } catch (SQLException e) {
	        System.out.println("Error retrieving last order number: " + e.getMessage());
	        e.printStackTrace();
	    }
	    return "OD_0001";
	}
	
	
	public void generateDates() {
	    LocalDateTime now = LocalDateTime.now();
	    this.order_date = now;
	    this.required_date = now.plusDays(4);
	    this.shipped_date = now.plusDays(1);
	}

	
	
	public void add_orderRecord() {
			
			this.order_number = generateOrderNumber();
		    generateDates();
		    this.status = "processing"; 
		    
		    String checkStatusQuery = "SELECT COUNT(*) FROM REF_status WHERE status = ?";
		    String checkStockQuantityQuery = "SELECT stock_quantity FROM publisher_books WHERE book_ID = ? AND publisher_ID = ?";
		    String insertOrderQuery 	   = "INSERT INTO orders (order_number, bookstore_ID, order_date, required_date, shipped_date, status, remarks) VALUES (?,?,?,?,?,?,?)";
		    String insertOrderDetailsQuery = "INSERT INTO order_details (order_number, book_ID, publisher_ID, quantity_ordered) VALUES (?,?,?,?)";
		    String updateBookStockQuery	   = "UPDATE publisher_books SET stock_quantity = stock_quantity - ? WHERE book_ID = ?";
		    
		    try (Connection conn = DriverManager.getConnection("jdbc:mysql://34.57.40.219:3306/CCINFO209DB?useTimezone=true&serverTimezone=UTC&user=root&password=DLSU1234!")) {
		        System.out.println("Connected to DB Successfully");
		        
		        PreparedStatement checkStatusStmt = conn.prepareStatement(checkStatusQuery);
		        checkStatusStmt.setString(1, this.status);
		        ResultSet statusRs = checkStatusStmt.executeQuery();
		        if (statusRs.next() && statusRs.getInt(1) == 0) {
		            System.out.println("Error: Status 'processing' does not exist in REF_status table. Aborting order creation.");
		            return;
		        }
		        
		        PreparedStatement checkStockStmt = conn.prepareStatement(checkStockQuantityQuery);
		        checkStockStmt.setInt(1, bookID);
		        checkStockStmt.setInt(2, publisherID);
		        ResultSet rs = checkStockStmt.executeQuery();

		        if (rs.next()) {
		            int stockQuantity = rs.getInt("stock_quantity");
		            if (stockQuantity < quantity_ordered) {
		                System.out.println("Error: Insufficient stock in publisher_books. Available: " + stockQuantity + ", Requested: " + quantity_ordered);
		                return;
		            }
		        } else {
		            System.out.println("Error: No record found in publisher_books for the given book_ID and publisher_ID.");
		            return;
		        }
		        
		        PreparedStatement orderStmt = conn.prepareStatement(insertOrderQuery);
		        orderStmt.setString(1, order_number);
		        orderStmt.setString(2, bookstoreID);
		        orderStmt.setTimestamp(3, Timestamp.valueOf(order_date));
		        orderStmt.setTimestamp(4, Timestamp.valueOf(required_date));
		        orderStmt.setTimestamp(5, Timestamp.valueOf(shipped_date));
		        orderStmt.setString(6, status);
		        orderStmt.setString(7, remarks);
		        orderStmt.executeUpdate();
		        System.out.println("Order record created: " + order_number);

		        PreparedStatement detailsStmt = conn.prepareStatement(insertOrderDetailsQuery);
		        detailsStmt.setString(1, order_number);
		        detailsStmt.setInt(2, bookID);
		        detailsStmt.setInt(3, publisherID);
		        detailsStmt.setInt(4, quantity_ordered);
		        detailsStmt.executeUpdate();
		        System.out.println("Order details record created");

		        PreparedStatement stockStmt = conn.prepareStatement(updateBookStockQuery);
		        stockStmt.setInt(1, quantity_ordered);
		        stockStmt.setInt(2, bookID);
		        stockStmt.executeUpdate();
		        System.out.println("Book stock updated");

		        orderStmt.close();
		        detailsStmt.close();
		        stockStmt.close();
		        conn.close();
		    } catch (SQLException e) {
		        System.out.println("Error: " + e.getMessage());
		        e.printStackTrace();
		    }
		}
		
		
	public void delete_orderRecord() {
			
			String queryRetrieve 		   =   "SELECT od.book_ID, "
											+  "od.quantity_ordered, "
											+  "pb.publisher_ID " 
											+  "FROM order_details od " 
											+  "JOIN publisher_books pb ON od.book_ID = pb.book_ID " 
											+  "WHERE od.order_number = ?";
			String updateStockQuery 	   = "UPDATE publisher_books SET stock_quantity = stock_quantity + ? WHERE book_ID = ? AND publisher_ID = ?";
	        String deleteOrderDetailsQuery = "DELETE FROM order_details WHERE order_number = ?";
	        String deleteOrderQuery 	   = "DELETE FROM orders WHERE order_number = ?";

		    try (Connection conn = DriverManager.getConnection("jdbc:mysql://34.57.40.219:3306/CCINFO209DB?useTimezone=true&serverTimezone=UTC&user=root&password=DLSU1234!")) {
		        System.out.println("Connection to DB Successful");
		        
		        PreparedStatement retrieveStmt = conn.prepareStatement(queryRetrieve);
		        retrieveStmt.setString(1, order_number);
		        ResultSet rs = retrieveStmt.executeQuery();
		        
		        PreparedStatement updateStockStmt = conn.prepareStatement(updateStockQuery);
		        while (rs.next()) {
		            int bookID = rs.getInt("book_ID");
		            int quantityOrdered = rs.getInt("quantity_ordered");
		            int publisherID = rs.getInt("publisher_ID");

		            updateStockStmt.setInt(1, quantityOrdered);
		            updateStockStmt.setInt(2, bookID);
		            updateStockStmt.setInt(3, publisherID);
		            updateStockStmt.executeUpdate();
		            System.out.println("Stock updated for book_ID: " + bookID + ", publisher_ID: " + publisherID);
		        }

		        PreparedStatement deleteOrderDetailsStmt = conn.prepareStatement(deleteOrderDetailsQuery);
		        deleteOrderDetailsStmt.setString(1, order_number);
		        deleteOrderDetailsStmt.executeUpdate();
		        System.out.println("Order details deleted for order_number: " + order_number);

		        PreparedStatement deleteOrderStmt = conn.prepareStatement(deleteOrderQuery);
		        deleteOrderStmt.setString(1, order_number);
		        deleteOrderStmt.executeUpdate();
		        System.out.println("Order deleted for order_number: " + order_number);

		        rs.close();
		        retrieveStmt.close();
		        updateStockStmt.close();
		        deleteOrderDetailsStmt.close();
		        deleteOrderStmt.close();
		        conn.close();

		    } catch (Exception e) {
		        System.out.println("Error: " + e.getMessage());
		        e.printStackTrace();
		    }
		}
	
	
	public void viewOrdersByBook(int bookID) {
		String query 					= "SELECT o.order_number, o.order_date, o.status, od.quantity_ordered " 
										+ "FROM orders o " 
										+ "JOIN order_details od ON o.order_number = od.order_number " 
										+ "WHERE od.book_ID = ?";
		try (Connection conn = DriverManager.getConnection("jdbc:mysql://34.57.40.219:3306/CCINFO209DB?useTimezone=true&serverTimezone=UTC&user=root&password=DLSU1234!")) {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, bookID);
            ResultSet rs = stmt.executeQuery();

            System.out.printf("%-15s %-15s %-15s %-15s\n", "Order Number", "Order Date", "Status", "Quantity Ordered");
            System.out.println("-------------------------------------------------------------");

            while (rs.next()) {
                System.out.printf("%-15s %-15s %-15s %-15d\n",
                        rs.getString("order_number"),
                        rs.getString("order_date"),
                        rs.getString("status"),
                        rs.getInt("quantity_ordered"));
            }

            stmt.close();
            conn.close();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
	
	public void viewOrdersByPublisher(int publisherID) {
        String query 				   = "SELECT o.order_number, o.order_date, o.status, od.quantity_ordered, od.book_ID " 
        							   + "FROM orders o " 
        							   + "JOIN order_details od ON o.order_number = od.order_number " 
        							   + "JOIN publisher_books pb ON od.book_ID = pb.book_ID " 
        							   + "WHERE pb.publisher_ID = ?";
        
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://34.57.40.219:3306/CCINFO209DB?useTimezone=true&serverTimezone=UTC&user=root&password=DLSU1234!")) {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, publisherID);
            ResultSet rs = stmt.executeQuery();

            System.out.printf("%-15s %-15s %-15s %-15s %-15s\n", "Order Number", "Order Date", "Status", "Book ID", "Quantity Ordered");
            System.out.println("--------------------------------------------------------------------------");

            while (rs.next()) {
                System.out.printf("%-15s %-15s %-15s %-15d %-15d\n",
                        rs.getString("order_number"),
                        rs.getString("order_date"),
                        rs.getString("status"),
                        rs.getInt("book_ID"),
                        rs.getInt("quantity_ordered"));
            }

            stmt.close();
            conn.close();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
	
	
	public void updateOrderStatus(String orderNumber, String newStatus) {
        String checkOrderQuery 		   = "SELECT COUNT(*) FROM orders WHERE order_number = ?";
        String checkStatusQuery 	   = "SELECT COUNT(*) FROM REF_status WHERE status = ?";

	    try (Connection conn = DriverManager.getConnection("jdbc:mysql://34.57.40.219:3306/CCINFO209DB?useTimezone=true&serverTimezone=UTC&user=root&password=DLSU1234!")) {
	        System.out.println("Connected to DB Successfully");
	        
	        PreparedStatement checkStatusStmt = conn.prepareStatement(checkStatusQuery);
	        checkStatusStmt.setString(1, newStatus);
	        ResultSet statusRs = checkStatusStmt.executeQuery();

	        if (statusRs.next() && statusRs.getInt(1) == 0) {
	            System.out.println("Error: Status '" + newStatus + "' is not valid. Please provide a valid status.");
	            return;
	        }

	        PreparedStatement checkOrderStmt = conn.prepareStatement(checkOrderQuery);
	        checkOrderStmt.setString(1, orderNumber);
	        ResultSet rs = checkOrderStmt.executeQuery();

	        if (rs.next() && rs.getInt(1) > 0) {
	            String updateStatusQuery = "UPDATE orders SET status = ? WHERE order_number = ?";
	            PreparedStatement updateStatusStmt = conn.prepareStatement(updateStatusQuery);
	            updateStatusStmt.setString(1, newStatus);
	            updateStatusStmt.setString(2, orderNumber);
	            int rowsUpdated = updateStatusStmt.executeUpdate();

	            if (rowsUpdated > 0) {
	                System.out.println("Order status updated successfully for order_number: " + orderNumber);
	            } else {
	                System.out.println("Failed to update order status for order_number: " + orderNumber);
	            }

	            updateStatusStmt.close();
	        } else {
	            System.out.println("Error: Order with order_number " + orderNumber + " does not exist.");
	        }

	        rs.close();
	        checkOrderStmt.close();
	        conn.close();

	    } catch (Exception e) {
	        System.out.println("Error: " + e.getMessage());
	        e.printStackTrace();
	    }
	}

		
		
}
