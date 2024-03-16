/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;
import java.sql.*;
/**
 *
 * @author prade
 */
public class DBConnection {
    static Connection con;
    
    public static Connection connectDB()
    {
        try
        {
            String path = "jdbc:mysql://localhost:3308/coursework";
            con = DriverManager.getConnection(path,"root","");
        }
        
        catch (SQLException e)
        {
            System.err.println("Exception "+e);
        }
        
        return con;
    }
}
