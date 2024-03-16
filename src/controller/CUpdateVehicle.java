/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;
import java.sql.ResultSet;
import model.MUpdateVehicle;
/**
 *
 * @author prade
 */
public class CUpdateVehicle {
    public ResultSet getVehicleData(String vehicleid)
    {
        MUpdateVehicle m1 = new MUpdateVehicle();
        return m1.getVehicleData(vehicleid);
    }
    public void updateVehicleData(String VehicleID,String RegistrationPlateNumber,String VehicleType,String Model,int SeatingCapacity,Double KmPerLiter,String FuelType,int Availability,Double DailyPrice,Double OverduePrice)
    {
        MUpdateVehicle m2 = new MUpdateVehicle();
        m2.updateVehicleData(VehicleID, RegistrationPlateNumber, VehicleType, Model, SeatingCapacity, KmPerLiter, FuelType, Availability, DailyPrice, OverduePrice);
    }
}
