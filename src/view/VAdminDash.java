/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package view;
import controller.CUpdateVehicle;
import controller.CAdminDash;
import java.awt.Component;
import java.awt.Font;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.*;
import view.VUpdateVehicle;
import javax.swing.table.DefaultTableCellRenderer;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author prade
 */
public class VAdminDash extends javax.swing.JFrame {

    /**
     * Creates new form VAdminDash
     */
    private ResultSet rst;
    private ResultSet rst2;
    
    private DefaultTableModel tableModel;
    private DefaultTableModel tableMode2;
    public VAdminDash() {
        initComponents();
        setupUI();
        populateTableWithData();
        populateTableWithData2();
        
        
        
    }
    /*private void updateLabelsFromSelectedRow(int selectedRow) {
    try {
        String value = (String) tbl_vehicle.getValueAt(selectedRow, 0);
        ResultSet rt;
        CUpdateVehicle c1 = new CUpdateVehicle();
        rt = c1.getVehicleData(value);
        
        if (rt.next()) {
            
            
            VehicleID = rt.getString("VehicleID");
            lbl_plate.setText(rt.getString("RegistrationPlateNumber"));
            lbl_vtype.setText(rt.getString("VehicleType"));
            lbl_model.setText(rt.getString("Model"));
            lbl_noofseats.setText(rt.getString("SeatingCapacity"));
            lbl_kmperliter.setText(rt.getString("KmPerLiter"));
            lbl_fueltype.setText(rt.getString("FuelType"));
            lbl_dailyprice.setText(rt.getString("DailyPrice"));
            lbl_overdueprice.setText(rt.getString("OverduePrice"));
            // Retrieve image data from the database blob field
           
        
        }
        
    } catch (SQLException e) {
        e.printStackTrace();
    }
    
    }*/
    
    private void setupUI()
    {
        String[] columnNames = {"Booking ID","Customer ID", "Vehicle ID","Pickup Date","Return Date","Purpose","Status","TotalPrice","BookDate"};
        tableModel = new DefaultTableModel();
        tableModel.setColumnIdentifiers(columnNames);
        tbl_booking.setModel(tableModel);
        
        String[] columnNames2 = {"Vehicle ID","Number Plate", "VehicleType","Model","SeatingCapacity","KmPerLiter","FuelType","Availability","DailyPrice","OverduePrice"};
        tableMode2 = new DefaultTableModel();
        tableMode2.setColumnIdentifiers(columnNames2);
        tbl_vehicle.setModel(tableMode2);
    }
    private void populateTableWithData() {
        
    // Create a font with the desired size
    Font tableFont = new Font("Segoe UI", Font.PLAIN, 36); // Change the font and size as needed

    // Create a cell renderer with the new font
    DefaultTableCellRenderer cellRenderer = new DefaultTableCellRenderer();
    cellRenderer.setFont(tableFont);

    // Apply the cell renderer to all columns
    for (int columnIndex = 0; columnIndex < tableModel.getColumnCount(); columnIndex++) {
        tbl_booking.getColumnModel().getColumn(columnIndex).setCellRenderer(cellRenderer);
    }

    
    
    CAdminDash c1 = new CAdminDash();
    rst = c1.getBookingDetails();

    
    try {
    // Clear any existing data
    tableModel.setRowCount(0);
    
    
    
    // Populate the table model with data from the result set
    while (rst.next()) {
        String BookingID = rst.getString("BookingID");
        String CustomerID = rst.getString("CustomerID");
        String VehicleID = rst.getString("VehicleID");
        Date PickupDate = rst.getDate("PickupDate");
        Date ReturnDate = rst.getDate("ReturnDate");
        String Purpose = rst.getString("Purpose");
        String Status = rst.getString("Status");
        double TotalPrice = rst.getDouble("TotalPrice");
        Date BookDate = rst.getDate("BookDate");
        

        // Add the data to the table model
        Object[] rowData = {BookingID,CustomerID, VehicleID, PickupDate, ReturnDate,Purpose,Status,TotalPrice,BookDate};
        tableModel.addRow(rowData);
    }
} catch (SQLException e) {
    e.printStackTrace();
}


    for (int row = 0; row < tbl_booking.getRowCount(); row++) {
            int rowHeight = tbl_booking.getRowHeight();

            for (int column = 0; column < tbl_booking.getColumnCount(); column++) {
                Component comp = tbl_booking.prepareRenderer(tbl_booking.getCellRenderer(row, column), row, column);
                rowHeight = Math.max(rowHeight, comp.getPreferredSize().height + 15);
            }

            tbl_booking.setRowHeight(row, rowHeight);
        }
}

    
    private void populateTableWithData2() {
        
    // Create a font with the desired size
    Font tableFont = new Font("Segoe UI", Font.PLAIN, 36); // Change the font and size as needed

    // Create a cell renderer with the new font
    DefaultTableCellRenderer cellRenderer = new DefaultTableCellRenderer();
    cellRenderer.setFont(tableFont);

    // Apply the cell renderer to all columns
    for (int columnIndex = 0; columnIndex < tableMode2.getColumnCount(); columnIndex++) {
        tbl_vehicle.getColumnModel().getColumn(columnIndex).setCellRenderer(cellRenderer);
    }

    
    
    CAdminDash c1 = new CAdminDash();
    rst = c1.getVehicleDetails();

    
    try {
    // Clear any existing data
    tableMode2.setRowCount(0);
    
    
    
    // Populate the table model with data from the result set
    while (rst.next()) {
        String VehicleID = rst.getString("VehicleID");
        String RegistrationPlateNumber = rst.getString("RegistrationPlateNumber");
        String VehicleType = rst.getString("VehicleType");
        String Model = rst.getString("Model");
        int SeatingCapacity = rst.getInt("SeatingCapacity");
        double KmPerLiter = rst.getDouble("KmPerLiter");
        String FuelType = rst.getString("FuelType");
        String Availability = null;
        if(rst.getString("Availability").equals("1"))
        {
            Availability = "Available";
        }
        else if(rst.getString("Availability").equals("0"))
        {
            Availability = "Not Available";
        }
        double DailyPrice = rst.getDouble("DailyPrice");
        double OverduePrice = rst.getDouble("OverduePrice");
        

        // Add the data to the table model
        Object[] rowData = {VehicleID,RegistrationPlateNumber, VehicleType, Model, SeatingCapacity,KmPerLiter,FuelType,Availability,DailyPrice,OverduePrice};
        tableMode2.addRow(rowData);
    }
} catch (SQLException e) {
    e.printStackTrace();
}


    for (int row = 0; row < tbl_vehicle.getRowCount(); row++) {
            int rowHeight = tbl_vehicle.getRowHeight();

            for (int column = 0; column < tbl_vehicle.getColumnCount(); column++) {
                Component comp = tbl_vehicle.prepareRenderer(tbl_vehicle.getCellRenderer(row, column), row, column);
                rowHeight = Math.max(rowHeight, comp.getPreferredSize().height + 15);
            }

            tbl_vehicle.setRowHeight(row, rowHeight);
        }
}
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbl_vehicle = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbl_booking = new javax.swing.JTable();
        btn_delete_booking = new javax.swing.JButton();
        btn_update_booking = new javax.swing.JButton();
        btn_update_vehicle = new javax.swing.JButton();
        btn_delete_vehicle = new javax.swing.JButton();
        btn_report = new javax.swing.JButton();
        btn_add_vehicle = new javax.swing.JButton();
        btn_refresh = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(0, 153, 255));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Dashboard");

        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/fleet.png"))); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel6)
                .addGap(18, 18, 18)
                .addComponent(jLabel1)
                .addGap(487, 487, 487))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addComponent(jLabel1))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel6)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tbl_vehicle.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tbl_vehicle.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_vehicleMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbl_vehicle);

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel3.setText("Booking Details");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel4.setText("Vehcile Details");

        tbl_booking.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane2.setViewportView(tbl_booking);

        btn_delete_booking.setBackground(new java.awt.Color(255, 51, 51));
        btn_delete_booking.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        btn_delete_booking.setText("DELETE");
        btn_delete_booking.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_delete_bookingActionPerformed(evt);
            }
        });

        btn_update_booking.setBackground(new java.awt.Color(0, 153, 204));
        btn_update_booking.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        btn_update_booking.setText("UPDATE STATUS");
        btn_update_booking.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_update_bookingActionPerformed(evt);
            }
        });

        btn_update_vehicle.setBackground(new java.awt.Color(0, 153, 204));
        btn_update_vehicle.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        btn_update_vehicle.setText("UPDATE");
        btn_update_vehicle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_update_vehicleActionPerformed(evt);
            }
        });

        btn_delete_vehicle.setBackground(new java.awt.Color(255, 51, 51));
        btn_delete_vehicle.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        btn_delete_vehicle.setText("DELETE");
        btn_delete_vehicle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_delete_vehicleActionPerformed(evt);
            }
        });

        btn_report.setBackground(new java.awt.Color(204, 0, 51));
        btn_report.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        btn_report.setText("REPORT");
        btn_report.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_reportActionPerformed(evt);
            }
        });

        btn_add_vehicle.setBackground(new java.awt.Color(0, 153, 255));
        btn_add_vehicle.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        btn_add_vehicle.setText("ADD");
        btn_add_vehicle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_add_vehicleActionPerformed(evt);
            }
        });

        btn_refresh.setBackground(new java.awt.Color(204, 204, 0));
        btn_refresh.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        btn_refresh.setText("REFRESH");
        btn_refresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_refreshActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(38, 38, 38)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel4)
                                    .addComponent(jLabel3)))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(63, 63, 63)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1080, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 44, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 1080, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btn_delete_booking, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btn_update_booking, javax.swing.GroupLayout.PREFERRED_SIZE, 221, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btn_report, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(104, 104, 104)
                                .addComponent(btn_delete_vehicle, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btn_update_vehicle)
                                .addGap(18, 18, 18)
                                .addComponent(btn_add_vehicle))
                            .addComponent(btn_refresh))
                        .addGap(44, 44, 44)))
                .addGap(30, 30, 30))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel3)
                    .addComponent(btn_refresh))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_delete_booking)
                    .addComponent(btn_update_booking, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(36, 36, 36)
                .addComponent(jLabel4)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btn_delete_vehicle)
                            .addComponent(btn_update_vehicle, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btn_add_vehicle, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(71, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btn_report, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(31, 31, 31))))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    
    private void btn_delete_bookingActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_delete_bookingActionPerformed
        try
        {
            int userChoice = JOptionPane.showConfirmDialog(null, "Do you wish to Delete the Booking?", "Warning", JOptionPane.ERROR_MESSAGE);
            if(userChoice == JOptionPane.YES_OPTION)
            {
                int index = tbl_booking.getSelectedRow();
                TableModel model = tbl_booking.getModel();
            
                String BookingID = model.getValueAt(index, 0).toString();
                CAdminDash c4 = new CAdminDash();
                c4.deleteBooking(BookingID);
            }
            
        }
        catch(ArrayIndexOutOfBoundsException e)
        {
            JOptionPane.showMessageDialog(null,"Please select a Booking to Delete","Error",JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btn_delete_bookingActionPerformed

    private void btn_update_bookingActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_update_bookingActionPerformed
        try
        {
            VUpdateBooking u1 = new VUpdateBooking();
            int index = tbl_booking.getSelectedRow();
            TableModel model = tbl_booking.getModel();
            String BookID = model.getValueAt(index, 0).toString();
            u1.lbl_bookid.setText(BookID);
            u1.setVisible(true);
            u1.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        }
        catch(ArrayIndexOutOfBoundsException e)
        {
            JOptionPane.showMessageDialog(null,"Please select a Booking to Update Status","Error",JOptionPane.ERROR_MESSAGE);
        }
        
    }//GEN-LAST:event_btn_update_bookingActionPerformed

    private void btn_update_vehicleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_update_vehicleActionPerformed
        
        try
        {
            VUpdateVehicle v1 = new VUpdateVehicle();
            int index = tbl_vehicle.getSelectedRow();
            TableModel model = tbl_vehicle.getModel();
            String VehicleID = model.getValueAt(index, 0).toString();
            String RegistrationPlateNumber = model.getValueAt(index, 1).toString();
            String VehicleType = model.getValueAt(index, 2).toString();
            String Model = model.getValueAt(index, 3).toString();
            String SeatingCapacity = (model.getValueAt(index, 4)).toString();
            String KmPerLiter = model.getValueAt(index, 5).toString();
            String FuelType = model.getValueAt(index, 6).toString();
            String Availability = model.getValueAt(index,7).toString();
            String DailyPrice =  model.getValueAt(index, 8).toString();
            String OverduePrice =  model.getValueAt(index, 9).toString();

            v1.lbl_vid.setText(VehicleID);
            v1.txt_numplate.setText(RegistrationPlateNumber);
            v1.cmb_vtype.setSelectedItem(VehicleType);
            v1.txt_vmodel.setText(Model);
            v1.cmb_noofseats.setSelectedItem(SeatingCapacity);
            v1.txt_kmperliter.setText(KmPerLiter);
            v1.cmb_fueltype.setSelectedItem(FuelType);
            v1.cmb_availability.setSelectedItem(Availability);
            v1.txt_priceperday.setText(DailyPrice);
            v1.txt_overdueprice.setText(OverduePrice);

            v1.setVisible(true);
            v1.pack();
            v1.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        }
        catch(ArrayIndexOutOfBoundsException e){
            JOptionPane.showMessageDialog(null,"Please select a Vehicle to Update","Error",JOptionPane.ERROR_MESSAGE);
        }
        
        
    }//GEN-LAST:event_btn_update_vehicleActionPerformed

    private void btn_delete_vehicleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_delete_vehicleActionPerformed
        try
        {
            int userChoice = JOptionPane.showConfirmDialog(null, "Do you wish to Delete the vehicle?", "Warning", JOptionPane.ERROR_MESSAGE);
            if(userChoice == JOptionPane.YES_OPTION)
            {
                int index = tbl_vehicle.getSelectedRow();
                TableModel model = tbl_vehicle.getModel();
            
                String VehicleID = model.getValueAt(index, 0).toString();
                CAdminDash c3 = new CAdminDash();
                c3.deleteVehicle(VehicleID);
            }
            
        }
        catch(ArrayIndexOutOfBoundsException e)
        {
            JOptionPane.showMessageDialog(null,"Please select a Vehicle to Delete","Error",JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btn_delete_vehicleActionPerformed

    private void btn_reportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_reportActionPerformed
        try
        {
            DBConnection.connectDB();
            JasperDesign jasDesign = JRXmlLoader.load("C:\\Users\\prade\\Downloads\\FinalCW\\CourseWork Nadith\\CourseWork\\src\\view\\report1.jrxml");
            JRDesignQuery desquery = new JRDesignQuery();   
            String sqlquery = "SELECT v.VehicleID, v.RegistrationPlateNumber AS 'Number Plate', v.VehicleType AS 'Vehicle Type', v.Model, COUNT(b.VehicleID) AS Bookings, COALESCE(SUM(b.TotalPrice), 0) AS 'Total Earnings' FROM Vehicle v LEFT JOIN Booking b ON v.VehicleID = b.VehicleID GROUP BY v.VehicleID, v.RegistrationPlateNumber, v.VehicleType, v.Model ORDER BY 'Total Earnings' DESC;";
            desquery.setText(sqlquery);
            jasDesign.setQuery(desquery);
            
            JasperReport jasreport = JasperCompileManager.compileReport(jasDesign);
            JasperPrint jasprint = JasperFillManager.fillReport(jasreport,null,DBConnection.connectDB());
            JasperViewer.viewReport(jasprint,false);
        
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }//GEN-LAST:event_btn_reportActionPerformed

    private void btn_add_vehicleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_add_vehicleActionPerformed
        VAdd_Vehicle_Form v1 = new VAdd_Vehicle_Form();
        v1.setVisible(true);
        v1.pack();
        v1.setLocationRelativeTo(null);
    }//GEN-LAST:event_btn_add_vehicleActionPerformed

    private void btn_refreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_refreshActionPerformed
        populateTableWithData();
        populateTableWithData2();
    }//GEN-LAST:event_btn_refreshActionPerformed
    VUpdateVehicle v1 = new VUpdateVehicle();
    private void tbl_vehicleMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_vehicleMouseClicked
        
    }//GEN-LAST:event_tbl_vehicleMouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(VAdminDash.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(VAdminDash.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(VAdminDash.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(VAdminDash.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new VAdminDash().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_add_vehicle;
    private javax.swing.JButton btn_delete_booking;
    private javax.swing.JButton btn_delete_vehicle;
    private javax.swing.JButton btn_refresh;
    private javax.swing.JButton btn_report;
    private javax.swing.JButton btn_update_booking;
    private javax.swing.JButton btn_update_vehicle;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable tbl_booking;
    private javax.swing.JTable tbl_vehicle;
    // End of variables declaration//GEN-END:variables
}
