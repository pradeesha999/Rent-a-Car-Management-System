/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import view.VBookingForm;
import view.VAdminDash;
import view.VLogin;

/**
 *
 * @author prade
 */
public class MLogin {
    public String userid;
    public void Login(String username,String password)
    {
        ResultSet rst;
        ResultSet rst2;
        try
        {
            Statement st = DBConnection.connectDB().createStatement();
            Statement st2 = DBConnection.connectDB().createStatement();
            rst = st.executeQuery("select * from customer where Username = '"+username+"' AND Password = '"+password+"';");
            rst2 = st2.executeQuery("select * from admin where Username = '"+username+"' AND Password = '"+password+"';");
            if(rst.next())
            {
                userid = rst.getString("CustomerID");
                VBookingForm v1 = new VBookingForm();
                v1.setTitle("Book Vehicle (" + userid + ")");
                v1.setVisible(true);
                VLogin l1 = new VLogin();
                l1.dispose();
            }
            else if(rst2.next())
            {
                VAdminDash a1 = new VAdminDash();
                a1.setVisible(true);
            }
            else
            {
                JOptionPane.showMessageDialog(null, "Invalid Credentials", "ERROR", JOptionPane.ERROR_MESSAGE);
            }
        }
        catch(SQLException e)
        {
            JOptionPane.showMessageDialog(null,e.getMessage());
        }
    }
}
