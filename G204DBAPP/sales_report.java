package G204DBAPP;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class sales_report {
	
	public int year;
	public int month;
	public int recordcount;
	
	public sales_report() {
	}

	public int generate_salesreport1 () {
		recordcount = 0;
		try {
			Connection conn;
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/dbsales?useTimezone=true&serverTimezone=UTC&user=ccinfomstudent&password=DLSU1234!");
			System.out.println("Connection to DB Successful");
			PreparedStatement pstmt = conn.prepareStatement("SELECT 	 od.productCode, \r\n"
														  + "			 ROUND(SUM(od.quantityOrdered),2)				AS  totalquantity,\r\n"
														  + "            ROUND(AVG(od.priceEach),2)						AS	averageprice,\r\n"
														  + "            ROUND(MAX(od.priceEach),2)						AS	maxprice,\r\n"
														  + "            ROUND(MIN(od.priceEach),2)						AS	minprice,\r\n"
														  + "            ROUND(SUM(od.priceEach*od.quantityOrdered),2)	AS	totalsales\r\n"
														  + "FROM		 orders o 	JOIN orderdetails od ON o.orderNumber=od.orderNumber\r\n"
														  + "WHERE		 YEAR(o.orderdate) = ? \r\n"
														  + "AND		 MONTH(o.orderdate) = ? \r\n"
														  + "GROUP BY	 od.productCode\r\n"
														  + "ORDER BY	 od.productCode;");

			pstmt.setInt(1, year);
			pstmt.setInt(2, month);
			System.out.println("SQL Statement Prepared");
			ResultSet rs = pstmt.executeQuery();
			
			while (rs.next()) {
				recordcount++;
				System.out.printf("%-10s %10s %10.2f %10.2f %10.2f %10.2f\n",rs.getString("productCode"), rs.getFloat("totalquantity"), rs.getFloat("averageprice"), rs.getFloat("maxprice"), rs.getFloat("minprice"), rs.getFloat("totalsales"));
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
