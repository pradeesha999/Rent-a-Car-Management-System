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
public class MUpdateVehicle {
    public ResultSet getVehicleData(String vehicleid)
    {
        ResultSet rt = null;
        try
        {
            Statement st = DBConnection.connectDB().createStatement();
            rt =  st.executeQuery("select * from vehicle where VehicleID = '"+vehicleid+"'");
        }
        catch(SQLException e)
        {
            JOptionPane.showMessageDialog(null, e);
        }
        return rt;  
    }
    public void updateVehicleData(String VehicleID,String RegistrationPlateNumber,String VehicleType,String Model,int SeatingCapacity,Double KmPerLiter,String FuelType,int Availability,Double DailyPrice,Double OverduePrice)
    {
        try
        {
            Statement st = DBConnection.connectDB().createStatement();
            int row_count = st.executeUpdate("UPDATE vehicle SET RegistrationPlateNumber = '"+RegistrationPlateNumber+"', VehicleType = '"+VehicleType+"', Model = '"+Model+"', SeatingCapacity = '"+SeatingCapacity+"', KmPerLiter = '"+KmPerLiter+"', FuelType = '"+FuelType+"', Availability = '"+Availability+"', DailyPrice = '"+DailyPrice+"', OverduePrice = '"+OverduePrice+"' WHERE VehicleID = '"+VehicleID+"';");
            if(row_count > 0)
            {
                JOptionPane.showMessageDialog(null,"Vehicle Deitals Updated");
            }
        }
        catch(SQLException e)
        {
            JOptionPane.showMessageDialog(null,e.getMessage());
        }
    }
}
