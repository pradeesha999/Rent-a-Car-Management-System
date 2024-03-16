/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;
import java.sql.ResultSet;
import model.MAdminDash;
/**
 *
 * @author prade
 */
public class CAdminDash {
    public ResultSet getBookingDetails()
    {
        MAdminDash m1 = new MAdminDash();
        return m1.getBookingDetails();
    }
    public ResultSet getVehicleDetails()
    {
        MAdminDash m2 = new MAdminDash();
        return m2.getVehicleDetails();
    }
    public void deleteVehicle(String VehicleID)
    {
        MAdminDash m3 = new MAdminDash();
        m3.deleteVehicle(VehicleID);
    }
    public void deleteBooking(String BookingID)
    {
        MAdminDash m4 = new MAdminDash();
        m4.deleteBooking(BookingID);
    }
}
