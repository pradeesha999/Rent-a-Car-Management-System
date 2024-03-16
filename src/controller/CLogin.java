/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;
import model.MLogin;

/**
 *
 * @author prade
 */
public class CLogin {
    public void Login(String username,String password)
    {
        MLogin m1 = new MLogin();
        m1.Login(username, password);
    }
}
