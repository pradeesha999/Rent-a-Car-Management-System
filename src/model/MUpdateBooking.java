/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;

/**
 *
 * @author prade
 */
public class MUpdateBooking {
    public void updateBookStatus(String BookingID,String Status)
    {
        try
        {
            Statement st = DBConnection.connectDB().createStatement();
            int row_count = st.executeUpdate("UPDATE booking SET Status = '"+Status+"' WHERE BookingID = '"+BookingID+"';");
            if(row_count > 0)
            {
                JOptionPane.showMessageDialog(null,"Booking Status Updated");
            }
        }
        catch(SQLException e)
        {
            JOptionPane.showMessageDialog(null,e.getMessage());
        }
    }
}
