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
        String query 			   			= "SELECT COUNT(*) FROM publishers WHERE publisher_ID = ?";
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
        String query 			   			= "SELECT COUNT(*) FROM bookstores WHERE bookstore_ID = ?";
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
	
	public boolean checkBookUnderPublisher(int bookID, int publisherID) {
	    String query 						= "SELECT COUNT(*) FROM publisher_books WHERE book_ID = ? AND publisher_ID = ?";
	    try (Connection conn = DriverManager.getConnection("jdbc:mysql://34.57.40.219:3306/CCINFO209DB?useTimezone=true&serverTimezone=UTC&user=root&password=DLSU1234!")) {
	        PreparedStatement stmt = conn.prepareStatement(query);
	        stmt.setInt(1, bookID);
	        stmt.setInt(2, publisherID);
	        ResultSet rs = stmt.executeQuery();
	        if (rs.next() && rs.getInt(1) > 0) {
	            return true;
	        }
	    } catch (SQLException e) {
	        System.out.println("Error checking book under publisher: " + e.getMessage());
	    }
	    return false;
	}
	
	
	
	public String generateOrderNumber() {
		String query 			   			= "SELECT order_number FROM orders ORDER BY order_number DESC LIMIT 1";
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

	
	public int getStockQuantity(int bookID, int publisherID) {
	    String query 						= "SELECT stock_quantity FROM publisher_books WHERE book_ID = ? AND publisher_ID = ?";

	    try (Connection conn = DriverManager.getConnection("jdbc:mysql://34.57.40.219:3306/CCINFO209DB?useTimezone=true&serverTimezone=UTC&user=root&password=DLSU1234!");
	         PreparedStatement stmt = conn.prepareStatement(query)) {

	        stmt.setInt(1, bookID);
	        stmt.setInt(2, publisherID);
	        ResultSet rs = stmt.executeQuery();

	        if (rs.next()) {
	            return rs.getInt("stock_quantity");
	        } else {
	            System.out.println("Error: No record found for book_ID: " + bookID + " and publisher_ID: " + publisherID);
	            return -1;
	        }

	    } catch (SQLException e) {
	        System.out.println("Error retrieving stock quantity: " + e.getMessage());
	        return -1;
	    }
	}
	public String addNewOrderRecord() {
	    this.order_number = generateOrderNumber();
	    generateDates();
	    this.status = "Processing";
	    String insertOrderQuery 				= "INSERT INTO orders (order_number, bookstore_ID, order_date, required_date, shipped_date, status, remarks) VALUES (?,?,?,?,?,?,?)";
	    
	    if (!checkBookUnderPublisher(bookID, publisherID)) {
	        System.out.println("Error: The book_ID and publisher_ID pair does not exist in publisher_books. Order creation aborted.");
	        return null;
	    }
	    
	   
	    try (Connection conn = DriverManager.getConnection("jdbc:mysql://34.57.40.219:3306/CCINFO209DB?useTimezone=true&serverTimezone=UTC&user=root&password=DLSU1234!")) {
	        PreparedStatement orderStmt = conn.prepareStatement(insertOrderQuery);
	        orderStmt.setString(1, order_number);
	        orderStmt.setString(2, bookstoreID);
	        orderStmt.setTimestamp(3, Timestamp.valueOf(order_date));
	        orderStmt.setTimestamp(4, Timestamp.valueOf(required_date));
	        orderStmt.setTimestamp(5, Timestamp.valueOf(shipped_date));
	        orderStmt.setString(6, status);
	        orderStmt.setString(7, remarks);
	        orderStmt.executeUpdate();

	        System.out.println("New order record created: " + order_number);
	        return order_number;

	    } catch (SQLException e) {
	        System.out.println("Error creating order: " + e.getMessage());
	        return null;
	    }
	}

	public void addOrderDetails(String orderNumber) {
	    String insertOrderDetailsQuery 			= "INSERT INTO order_details (order_number, book_ID, publisher_ID, quantity_ordered) VALUES (?,?,?,?)";
	    String updateBookStockQuery 			= "UPDATE publisher_books SET stock_quantity = stock_quantity - ? WHERE book_ID = ? AND publisher_ID = ?";

	    if (!checkBookUnderPublisher(bookID, publisherID)) {
	        System.out.println("Error: The book_ID and publisher_ID pair does not exist. Order details cannot be added.");
	        return;
	    }
	    
	    try (Connection conn = DriverManager.getConnection("jdbc:mysql://34.57.40.219:3306/CCINFO209DB?useTimezone=true&serverTimezone=UTC&user=root&password=DLSU1234!")) {
	        PreparedStatement detailsStmt = conn.prepareStatement(insertOrderDetailsQuery);
	        detailsStmt.setString(1, orderNumber);
	        detailsStmt.setInt(2, bookID);
	        detailsStmt.setInt(3, publisherID);
	        detailsStmt.setInt(4, quantity_ordered);
	        detailsStmt.executeUpdate();

	        PreparedStatement stockStmt = conn.prepareStatement(updateBookStockQuery);
	        stockStmt.setInt(1, quantity_ordered);
	        stockStmt.setInt(2, bookID);
	        stockStmt.setInt(3, publisherID);
	        stockStmt.executeUpdate();

	    } catch (SQLException e) {
	        System.out.println("Error adding order details: " + e.getMessage());
	    }
	}

		
	public void delete_orderRecord(String order_number) {
	    String checkStatusQuery 		    = "SELECT status FROM orders WHERE order_number = ?";
	    String queryRetrieve 				= "SELECT od.book_ID, od.quantity_ordered, pb.publisher_ID " 
	    									+ "FROM order_details od " 
	    									+ "JOIN publisher_books pb ON od.book_ID = pb.book_ID " 
	    									+ "WHERE od.order_number = ?";
	    String updateStockQuery 			= "UPDATE publisher_books SET stock_quantity = stock_quantity + ? WHERE book_ID = ? AND publisher_ID = ?";
	    String deleteOrderDetailsQuery 		= "DELETE FROM order_details WHERE order_number = ?";
	    String deleteOrderQuery 			= "DELETE FROM orders WHERE order_number = ?";

	    try (Connection conn = DriverManager.getConnection("jdbc:mysql://34.57.40.219:3306/CCINFO209DB?useTimezone=true&serverTimezone=UTC&user=root&password=DLSU1234!")) {
	        System.out.println("Connection to DB Successful");

	        // Check order status
	        PreparedStatement checkStatusStmt = conn.prepareStatement(checkStatusQuery);
	        checkStatusStmt.setString(1, order_number);
	        ResultSet statusRs = checkStatusStmt.executeQuery();

	        if (statusRs.next()) {
	            String status = statusRs.getString("status");
	            if (!status.equalsIgnoreCase("Processing")) {
	                System.out.println("Error: Order cannot be deleted. Current status: " + status);
	                return;
	            }
	        } else {
	            System.out.println("Error: Order with order_number " + order_number + " does not exist.");
	            return;
	        }

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
	            }

	        PreparedStatement deleteOrderDetailsStmt = conn.prepareStatement(deleteOrderDetailsQuery);
	        deleteOrderDetailsStmt.setString(1, order_number);
	        deleteOrderDetailsStmt.executeUpdate();
	        System.out.println("Order details deleted for order_number: " + order_number);

	        PreparedStatement deleteOrderStmt = conn.prepareStatement(deleteOrderQuery);
	        deleteOrderStmt.setString(1, order_number);
	        deleteOrderStmt.executeUpdate();
	        System.out.println("Order deleted for order_number: " + order_number);

	        statusRs.close();
	        checkStatusStmt.close();
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
	    String query 						= "SELECT o.order_number, o.order_date, o.status, od.quantity_ordered, bs.bookstore_name " 
	    									+ "FROM orders o JOIN order_details od ON o.order_number = od.order_number " 
	    									+ "JOIN publisher_books pb ON od.book_ID = pb.book_ID " 
	    									+ "JOIN bookstores bs ON o.bookstore_ID = bs.bookstore_ID " 
	    									+ "WHERE pb.book_ID = ?";


	    try (Connection conn = DriverManager.getConnection("jdbc:mysql://34.57.40.219:3306/CCINFO209DB?useTimezone=true&serverTimezone=UTC&user=root&password=DLSU1234!")) {
	        PreparedStatement stmt = conn.prepareStatement(query);
	        stmt.setInt(1, bookID);
	        ResultSet rs = stmt.executeQuery();

	        System.out.printf("%-15s %-20s %-20s %-25s %-35s\n", 
	                          "Order Number", "Order Date", "Status", "Quantity Ordered", "Bookstore Name");
	        System.out.println("----------------------------------------------------------------------------------------------------");

	        while (rs.next()) {
	            System.out.printf("%-15s %-20s %-20s %-25d %-30s\n",
	                              rs.getString("order_number"),
	                              rs.getString("order_date"),
	                              rs.getString("status"),
	                              rs.getInt("quantity_ordered"),
	                              rs.getString("bookstore_name"));
	        }

	        stmt.close();
	        conn.close();
	    } catch (Exception e) {
	        System.out.println("Error: " + e.getMessage());
	        e.printStackTrace();
	    }
	}
	
	public void viewOrdersByPublisher(int publisherID) {
        String query 				   = "SELECT o.order_number, o.order_date, o.status, od.quantity_ordered, pb.book_ID, bs.bookstore_name " 
        								+ "FROM orders o " 
        								+ "JOIN order_details od ON o.order_number = od.order_number " 
        								+ "JOIN publisher_books pb ON od.book_ID = pb.book_ID " 
        								+ "JOIN bookstores bs ON o.bookstore_ID = bs.bookstore_ID " 
        								+ "WHERE pb.publisher_ID = ?";
        
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://34.57.40.219:3306/CCINFO209DB?useTimezone=true&serverTimezone=UTC&user=root&password=DLSU1234!")) {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, publisherID);
            ResultSet rs = stmt.executeQuery();

            System.out.printf("%-15s %-20s %-15s %-20s %-25s %-35s\n", 
                              "Order Number", "Order Date", "Status", "Book ID", "Quantity Ordered", "Bookstore Name");
            System.out.println("----------------------------------------------------------------------------------------------------------------");

            while (rs.next()) {
                System.out.printf("%-15s %-20s %-15s %-20d %-25d %-35s\n",
                                  rs.getString("order_number"),
                                  rs.getString("order_date"),
                                  rs.getString("status"),
                                  rs.getInt("book_ID"),
                                  rs.getInt("quantity_ordered"),
                                  rs.getString("bookstore_name"));
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
	
	
	
	public void displayExistingBookIDs() {
	    String query 				  = "SELECT book_ID, title FROM books";

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
	    String query 				= "SELECT publisher_ID, publisher_name FROM publishers";

	    try (Connection conn = DriverManager.getConnection("jdbc:mysql://34.57.40.219:3306/CCINFO209DB?useTimezone=true&serverTimezone=UTC&user=root&password=DLSU1234!")) {
	        PreparedStatement stmt = conn.prepareStatement(query);
	        ResultSet rs = stmt.executeQuery();

	        System.out.printf("%-15s %-30s\n", "Publisher ID", "Publisher Name");
	        System.out.println("------------------------------------------------------------");

	        while (rs.next()) {
	            System.out.printf("%-15d %-30s\n", rs.getInt("publisher_ID"), rs.getString("publisher_name"));
	        }

	        stmt.close();
	        conn.close();
	    } catch (Exception e) {
	        System.out.println("Error retrieving Publisher IDs and Names: " + e.getMessage());
	    }
	}
	
	public void displayExistingOrderNumbers() {
	    String query 			   = "SELECT o.order_number, bs.bookstore_name, o.status " 
	    							+ "FROM orders o " 
	    							+ "JOIN bookstores bs ON o.bookstore_ID = bs.bookstore_ID " 
	    							+ "ORDER BY o.order_number";

	    try (Connection conn = DriverManager.getConnection("jdbc:mysql://34.57.40.219:3306/CCINFO209DB?useTimezone=true&serverTimezone=UTC&user=root&password=DLSU1234!");
	         PreparedStatement stmt = conn.prepareStatement(query);
	         ResultSet rs = stmt.executeQuery()) {

	        System.out.printf("%-15s %-30s %-20s\n", "Order Number", "Bookstore Name", "Order Status");
	        System.out.println("------------------------------------------------------------------");

	        while (rs.next()) {
	            System.out.printf("%-15s %-30s %-20s\n",
	                    rs.getString("order_number"),
	                    rs.getString("bookstore_name"),
	                    rs.getString("status"));
	        }

	    } catch (Exception e) {
	        System.out.println("Error retrieving Order Numbers: " + e.getMessage());
	        e.printStackTrace();
	    }
	}
	
	public void displayExistingBookstoreIDs() {
	    String query 			  = "SELECT bookstore_ID, bookstore_name FROM bookstores";
	    try (Connection conn = DriverManager.getConnection("jdbc:mysql://34.57.40.219:3306/CCINFO209DB?useTimezone=true&serverTimezone=UTC&user=root&password=DLSU1234!");
	         PreparedStatement stmt = conn.prepareStatement(query);
	         ResultSet rs = stmt.executeQuery()) {

	        System.out.printf("%-15s %-30s\n", "Bookstore ID", "Bookstore Name");
	        System.out.println("-------------------------------------------------");
	        while (rs.next()) {
	            System.out.printf("%-15s %-30s\n",
	                    rs.getString("bookstore_ID"),
	                    rs.getString("bookstore_name"));
	        }
	    } catch (SQLException e) {
	        System.out.println("Error retrieving bookstore IDs: " + e.getMessage());
	        e.printStackTrace();
	    }
	}
	
	public void displayBooksPublishersAndStock() {
	    String query 			= "SELECT b.book_ID, b.title, p.publisher_ID, p.publisher_name, pb.stock_quantity " 
	    						+ "FROM books b " 
	    						+ "JOIN publisher_books pb ON b.book_ID = pb.book_ID " 
	    						+ "JOIN publishers p ON pb.publisher_ID = p.publisher_ID " 
	    						+ "ORDER BY b.book_ID, p.publisher_ID";

	    try (Connection conn = DriverManager.getConnection( "jdbc:mysql://34.57.40.219:3306/CCINFO209DB?useTimezone=true&serverTimezone=UTC&user=root&password=DLSU1234!");
	         PreparedStatement stmt = conn.prepareStatement(query);
	         ResultSet rs = stmt.executeQuery()) {

	        System.out.printf("%-10s %-45s %-25s %-45s %-15s\n", "Book ID", "Book Title", "Publisher ID", "Publisher Name", "Stock Quantity");
	        System.out.println("-------------------------------------------------------------------------------------------------------------------------------------------------");
	        while (rs.next()) {
	            System.out.printf("%-10d %-45s %-25d %-45s %-15d\n",
	                    rs.getInt("book_ID"),
	                    rs.getString("title"),
	                    rs.getInt("publisher_ID"),
	                    rs.getString("publisher_name"),
	                    rs.getInt("stock_quantity"));
	        }
	    } catch (SQLException e) {
	        System.out.println("Error retrieving books, publishers, and stock quantities: " + e.getMessage());
	        e.printStackTrace();
	    }
	}
		
		
}
