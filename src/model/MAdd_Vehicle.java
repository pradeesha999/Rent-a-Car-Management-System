/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;

/**
 *
 * @author prade
 */
public class MAdd_Vehicle {
    public void addVehicle(String VehicleID,String RegistrationPlateNumber,String VehicleType,String Model,int SeatingCapacity,Double KmPerLiter,String FuelType,int Availability,Double DailyPrice,Double OverduePrice)
    {
        try
        {
            Statement st = DBConnection.connectDB().createStatement();
            int row_count = st.executeUpdate("INSERT INTO Vehicle (VehicleID, RegistrationPlateNumber, VehicleType, Model, SeatingCapacity, KmPerLiter, FuelType, Availability, DailyPrice, OverduePrice) VALUES ('"+VehicleID+"','"+RegistrationPlateNumber+"','"+VehicleType+"', '"+Model+"','"+SeatingCapacity+"','"+KmPerLiter+"','"+FuelType+"','"+Availability+"','"+DailyPrice+"','"+OverduePrice+"');");
            if(row_count > 0)
            {
                JOptionPane.showMessageDialog(null,"Vehicle Successfully Added");
            }
        }
        catch(SQLException e)
        {
            JOptionPane.showMessageDialog(null,e.getMessage());
        }
    }
    
    public ResultSet getBookingDetails()
    {
        ResultSet rt = null;
        try
        {
            Statement st = DBConnection.connectDB().createStatement();
            rt =  st.executeQuery("SELECT MAX(VehicleID) FROM Vehicle;");
        }
        catch(SQLException e)
        {
            JOptionPane.showMessageDialog(null, e);
        }
        return rt;
    }
}
