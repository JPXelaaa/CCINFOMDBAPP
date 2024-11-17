import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;
import java.util.Scanner;

public class book_record_management {

    public String ISBN;
    public String title;
    public String author;
    public String publisherID;
    public String genre;
    public Date publicationYear;
    public int stockQuantity;


    public int add_product(){

        try{
            Connection con;
            conn = DriverManager.getConnection("");
            System.out.println("Connected to the database successfully");
            PreparedStatement ps = conn.prepareStatement("INSERT INTO Book VALUES ()");
            ps.setString(1, ISBN);
            ps.setString(2, title);
            ps.setString(3, author);
            ps.setString(4, publisherID);
            ps.setString(5, genre);
            ps.setInt(6, stockQuantity);

            return 1;
        } catch (Exception e){
            System.out.println(e.getMessage());
            return 0;
        }
    }


    public int delete_product(){

        try{
            Connection con;
            conn = DriverManager.getConnection("");
            System.out.println("Connected to the database successfully");
            PreparedStatement ps = conn.prepareStatement("DELETE FROM Book WHERE ISBN=?");
            ps.setString(1, ISBN);


            return 1;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return 0;
        }

    }



}
