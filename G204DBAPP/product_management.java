package G204DBAPP;
import java.sql.*;

public class product_management {
	
	public String	productCode;
	public String	productName;
	public String	productLine;
	public String	productScale;
	public String	productDescription;
	public String	productVendor;
	public int		quantityInStock;
	public float	buyPrice;
	public float	MSRP;
	
	public product_management() {
		productCode 		= "";
		productName			= "";
		productLine 		= "";
		productScale 		= "";
		productDescription  = "";
		productVendor		= "";
		quantityInStock		= 0;
		buyPrice			= 0;
		MSRP				= 0;
	}
	
	public int add_product() {
		try {
			// Common Algorithm when Interacting with Databases
			// 1. Connect to the MYSQL Database
			// 2. Preparing your SQL Statement
			// 3. Execute our SQL Statement (and if there are results, store it in a variable
			// 4. Close our SQL Statement
			// 5. Close our Connection to the MYSQL Database
			
			Connection conn;
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/dbsales?useTimezone=true&serverTimezone=UTC&user=ccinfomstudent&password=DLSU1234!");
			System.out.println("Connection to DB Successful");
			PreparedStatement pstmt = conn.prepareStatement("INSERT INTO products VALUES (?,?,?,?,?,?,?,?,?)");
			pstmt.setString(1, productCode);
			pstmt.setString(2, productName);
			pstmt.setString(3, productLine);
			pstmt.setString(4, productScale);
			pstmt.setString(5, productDescription);
			pstmt.setString(6, productVendor);
			pstmt.setInt   (7, quantityInStock);
			pstmt.setFloat (8, buyPrice);
			pstmt.setFloat (9, MSRP);
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
	
	public int update_product() {
		try {
			Connection conn;
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/dbsales?useTimezone=true&serverTimezone=UTC&user=ccinfomstudent&password=DLSU1234!");
			System.out.println("Connection to DB Successful");
			PreparedStatement pstmt = conn.prepareStatement("UPDATE products SET productName=?, productLine=?, productScale=?, productDescription=?, productVendor=?, quantityInStock=?, buyPrice=?, MSRP=? WHERE productCode=?");
			pstmt.setString(9, productCode);
			pstmt.setString(1, productName);
			pstmt.setString(2, productLine);
			pstmt.setString(3, productScale);
			pstmt.setString(4, productDescription);
			pstmt.setString(5, productVendor);
			pstmt.setInt   (6, quantityInStock);
			pstmt.setFloat (7, buyPrice);
			pstmt.setFloat (8, MSRP);
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
	
	public int delete_product() {
		try {
			Connection conn;
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/dbsales?useTimezone=true&serverTimezone=UTC&user=ccinfomstudent&password=DLSU1234!");
			System.out.println("Connection to DB Successful");
			PreparedStatement pstmt = conn.prepareStatement("DELETE FROM products WHERE productCode=?");
			pstmt.setString(1, productCode);
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
	
	public int get_product() {
		int recordcount = 0;
		try {
			Connection conn;
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/dbsales?useTimezone=true&serverTimezone=UTC&user=ccinfomstudent&password=DLSU1234!");
			System.out.println("Connection to DB Successful");
			PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM products WHERE productCode=?");
			pstmt.setString(1, productCode);
			System.out.println("SQL Statement Prepared");
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				recordcount++;
				productName 	   = rs.getString("productName");
				productLine 	   = rs.getString("productLine");
				productScale 	   = rs.getString("productScale");
				productDescription = rs.getString("productDescription");
				productVendor      = rs.getString("productVendor");
				quantityInStock	   = rs.getInt   ("quantityInStock");
				buyPrice		   = rs.getFloat ("buyPrice");
				MSRP			   = rs.getFloat ("MSRP");
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
