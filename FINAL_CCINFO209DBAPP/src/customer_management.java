import java.sql.*;

public class customer_management {
	
	public String	bookstore_ID;
	public String	bookstore_name;
	public String	contact_firstName;
	public String	contact_lastName;
	public String	phone_number;
	public String	addressLine1;
	public String	addressLine2;
	public String	city;
	
	public String	bookstoreHolder;
	
	public customer_management() {
		bookstore_ID 		= "";
		bookstore_name		= "";
		contact_firstName 	= "";
		contact_lastName 	= "";
		phone_number  		= "";
		addressLine1		= "";
		addressLine2		= "";
		city				= "";
				
	}
	
	public int bookstoreIDChoice() {
   	 try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://34.57.40.219:3306/CCINFO209DB?useTimezone=true&serverTimezone=UTC&user=root&password=DLSU1234!");

            // check if the bookstore exists
            PreparedStatement checkStmt = conn.prepareStatement(
                "SELECT COUNT(*) AS count FROM bookstores WHERE bookstore_ID=?"
            );
            checkStmt.setString(1, bookstore_ID);
            ResultSet r = checkStmt.executeQuery();
            r.next();
            int count = r.getInt("count");
            r.close();
            checkStmt.close();

            if (count == 0) {
                System.out.println("Bookstore does not exist in the database. Update terminated.");
                conn.close();
                return 0;
            } 
            
            return 1;
   	 } catch (Exception e) {
   		 System.out.println(e.getMessage());
   		 return 0;
   	 }
   }
	
	public int add_customer() {
		try {
			Connection conn = DriverManager.getConnection("jdbc:mysql://34.57.40.219:3306/CCINFO209DB?useTimezone=true&serverTimezone=UTC&user=root&password=DLSU1234!");
			System.out.println("Connection to DB Successful");
			PreparedStatement pstmt = conn.prepareStatement(
					"INSERT INTO bookstores (bookstore_ID, bookstore_name, contact_firstName, contact_lastName, phone_number, addressLine1, addressLine2, city) VALUES (?,?,?,?,?,?,?,?)"
					);
			pstmt.setString(1, bookstore_ID);
			pstmt.setString(2, bookstore_name); 
			pstmt.setString(3, contact_firstName);
			pstmt.setString(4, contact_lastName);
			pstmt.setString(5, phone_number); 
			pstmt.setString(6, addressLine1);
			pstmt.setString(7, addressLine2); 
			pstmt.setString(8, city);
			System.out.println("SQL Statement Prepared");
			pstmt.executeUpdate();
			System.out.println("Bookstore Customer Record is added");
			
			pstmt.close();
			conn.close();
			return 1; 
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return 0;
		}
	}
	
	public int update_customer() {
		try {
			Connection conn = DriverManager.getConnection("jdbc:mysql://34.57.40.219:3306/CCINFO209DB?useTimezone=true&serverTimezone=UTC&user=root&password=DLSU1234!");
			System.out.println("Connection to DB Successful");
	
			PreparedStatement pstmt = conn.prepareStatement("UPDATE bookstores SET bookstore_name=?, contact_firstName=?, contact_lastName=?, phone_number=?, addressLine1=?, addressLine2=?, city=? WHERE bookstore_ID=?");
			pstmt.setString(1, bookstore_name);
			pstmt.setString(2, contact_firstName);
			pstmt.setString(3, contact_lastName);
			pstmt.setString(4, phone_number);
			pstmt.setString(5, addressLine1);
			pstmt.setString(6, addressLine2);
			pstmt.setString(7, city);
			pstmt.setString(8, bookstore_ID);
			System.out.println("SQL Statement Prepared");
			pstmt.executeUpdate();
			System.out.println("Bookstore Customer Record has been updated");
			pstmt.close();
			conn.close();
			return 1;
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return 0;
		}
	}
		
	public int delete_customer() {
		try {
			if (bookstoreIDChoice() == 1) {
				Connection conn = DriverManager.getConnection("jdbc:mysql://34.57.40.219:3306/CCINFO209DB?useTimezone=true&serverTimezone=UTC&user=root&password=DLSU1234!");
				System.out.println("Connection to DB Successful");
				PreparedStatement pstmt = conn.prepareStatement("DELETE FROM bookstores WHERE bookstore_ID=?");
				pstmt.setString(1, bookstore_ID);
				System.out.println("SQL Statement Prepared");
				pstmt.executeUpdate();
				System.out.println("Bookstore Customer Record was deleted");
				pstmt.close();
				conn.close();
				return 1;
			}
			
			else
				return 0;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return 0;
		}
	}
	
	public int get_customer() {
		int recordcount = 0;
		try {
			if (bookstoreIDChoice() == 1) {
				Connection conn = DriverManager.getConnection("jdbc:mysql://34.57.40.219:3306/CCINFO209DB?useTimezone=true&serverTimezone=UTC&user=root&password=DLSU1234!");
				System.out.println("Connection to DB Successful");
				
				PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM bookstores WHERE bookstore_ID=?");
				pstmt.setString(1, bookstore_ID);
				System.out.println("SQL Statement Prepared");
				ResultSet rs = pstmt.executeQuery();
				
				
				while (rs.next()) {
					recordcount++;
					bookstore_name 	   = rs.getString("bookstore_name");
					contact_firstName  = rs.getString("contact_firstName");
					contact_lastName   = rs.getString("contact_lastName");
					phone_number 	   = rs.getString("phone_number");
					addressLine1       = rs.getString("addressLine1");
					addressLine2	   = rs.getString("addressLine2");
					city		  	   = rs.getString("city");
					System.out.println("Record was Retrieved");
				}
				pstmt.close();
				conn.close();
				return recordcount;
			}
			else {
				return 0;
			}
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return 0;
		}
	}
	
	
}
