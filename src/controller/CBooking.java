/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.sql.*;
import java.time.LocalDate;
import model.MBookingForm;

/**
 *
 * @author prade
 */
public class CBooking {
    public ResultSet getBookingDetials()
    {
        MBookingForm m4 = new MBookingForm();
        return m4.getBookingDetails();
    }
    public void setBooking(String bookid,String cusid,String vehid, LocalDate PickupDate,LocalDate ReturnDate,String purpose,String status, Double price,LocalDate BookDate)
    {
        MBookingForm m1 = new MBookingForm();
        m1.setBooking(bookid, cusid, vehid, PickupDate, ReturnDate, purpose, status, price, BookDate);
    }
    public ResultSet getFilteredVehicle(String selectedType,int selectedSeats,String selectedFuelType)
    {
        MBookingForm m2 = new MBookingForm();
        return m2.getFilteredVehicle(selectedType, selectedSeats, selectedFuelType);
    }
    public ResultSet getVehicleData(String numberPlate)
    {
        MBookingForm m3 = new MBookingForm();
        return m3.getVehicleData(numberPlate);
    }
}
