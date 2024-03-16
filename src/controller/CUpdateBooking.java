/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;
import model.MUpdateBooking;
/**
 *
 * @author prade
 */
public class CUpdateBooking {
    public void updateBookStatus(String BookingID,String Status)
    {
        MUpdateBooking m1 = new MUpdateBooking();
        m1.updateBookStatus(BookingID, Status);
    }
}
