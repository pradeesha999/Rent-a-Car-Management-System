/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import javax.swing.JOptionPane;

/**
 *
 * @author prade
 */
public class MBookingForm {
    public ResultSet getBookingDetails()
    {
        ResultSet rt = null;
        try
        {
            Statement st = DBConnection.connectDB().createStatement();
            rt =  st.executeQuery("SELECT MAX(BookingID) FROM Booking;");
        }
        catch(SQLException e)
        {
            JOptionPane.showMessageDialog(null, e);
        }
        return rt;
    }
    public void setBooking(String BookingID,String CustomerID,String VehicleID,LocalDate PickupDate,LocalDate ReturnDate,String Purpose,String Status,Double TotalPrice,LocalDate BookDate)
    {
        try
        {
            Statement st = DBConnection.connectDB().createStatement();
            Statement st2 = DBConnection.connectDB().createStatement();
            int row_count = st.executeUpdate("INSERT INTO Booking (BookingID, CustomerID, VehicleID, PickupDate, ReturnDate, Purpose, Status, TotalPrice, BookDate) VALUES ('"+BookingID+"', '"+CustomerID+"', '"+VehicleID+"', '"+PickupDate+"', '"+ReturnDate+"', '"+Purpose+"', '"+Status+"', '"+TotalPrice+"', '"+BookDate+"');");
            int row_count2 = st2.executeUpdate("UPDATE vehicle SET Availability = 0 where VehicleID = '"+VehicleID+"'");
            if(row_count > 0 && row_count2 > 0)
            {
                JOptionPane.showMessageDialog(null,"Vehicle Successfully Booked");
            }
        }
        catch(SQLException e)
        {
            JOptionPane.showMessageDialog(null,e.getMessage());
        }
    }
    
    public ResultSet getFilteredVehicle(String selectedType,int selectedSeats,String selectedFuelType)
    {
        ResultSet rst = null;
        try
        {
            Statement st = DBConnection.connectDB().createStatement();
            rst =  st.executeQuery("SELECT * FROM Vehicle WHERE VehicleType = '"+selectedType+"' AND SeatingCapacity = '"+selectedSeats+"' AND FuelType = '"+selectedFuelType+"' AND Availability = 1");
        }
        catch(SQLException e)
        {
            JOptionPane.showMessageDialog(null, e);
        }
        return rst;        
    }
    
    public ResultSet getVehicleData(String numberPlate)
    {
        ResultSet rt = null;
        try
        {
            Statement st = DBConnection.connectDB().createStatement();
            rt =  st.executeQuery("SELECT * FROM Vehicle WHERE RegistrationPlateNumber = '"+numberPlate+"'");
        }
        catch(SQLException e)
        {
            JOptionPane.showMessageDialog(null, e);
        }
        return rt;  
    }
}
