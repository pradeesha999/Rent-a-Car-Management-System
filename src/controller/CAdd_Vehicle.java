/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;
import model.MAdd_Vehicle;
import java.sql.ResultSet;

/**
 *
 * @author prade
 */
public class CAdd_Vehicle {
    public void addVehicle(String VehicleID,String RegistrationPlateNumber,String VehicleType,String Model,int SeatingCapacity,Double KmPerLiter,String FuelType,int Availability,Double DailyPrice,Double OverduePrice)
    {
        MAdd_Vehicle m1 = new MAdd_Vehicle();
        m1.addVehicle(VehicleID, RegistrationPlateNumber, VehicleType, Model, SeatingCapacity, KmPerLiter, FuelType, Availability, DailyPrice, OverduePrice);
    }
    
    public ResultSet getVehicleDetails()
    {
        MAdd_Vehicle m2 = new MAdd_Vehicle();
        return m2.getBookingDetails();
    }
}

