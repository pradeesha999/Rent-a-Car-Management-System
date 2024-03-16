/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package view;
import javax.swing.*;
import controller.CBooking;
import view.VLogin;
import java.awt.*;
import java.sql.*;
import java.sql.Date;
import java.time.LocalDate;
import java.time.ZoneId;
import javax.swing.table.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import controller.CBooking;
import datetimepicker.DatePicker;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import net.sourceforge.jdatepicker.JDatePicker;
/**
 *
 * @author prade
 */
public class VBookingForm extends javax.swing.JFrame {
    
    private DefaultTableModel tableModel;
    private JDatePicker pickupDatePicker;
    private JDatePicker returnDatePicker;
    private ResultSet rst;
    private ImageIcon format=null;
    private Date selectedPickupDate;
    private Date selectedReturnDate;
    String customer;
    String VehicleID = null;
    LocalDate datep = null;
    LocalDate dater = null;
    /**
     * Creates new form BookingForm
     */
    public VBookingForm() {
        
        initComponents();
        setupUI();
        getContentPane().setBackground(Color.WHITE);
        
        
        updateNoOfSeatsOptions((String) cmb_type.getSelectedItem());
        
        jPanel1.setBackground(Color.LIGHT_GRAY);
        getContentPane().setBackground(Color.WHITE);
        
        
        
        populateTableWithData();
        
        VehicleTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() 
        {
        @Override
        public void valueChanged(ListSelectionEvent e) {
            if (!e.getValueIsAdjusting() && VehicleTable.getSelectedRow() != -1) {
                int selectedRow = VehicleTable.getSelectedRow();
                updateLabelsFromSelectedRow(selectedRow);
            }
        }
        });   
        
}
    private Double datediff(LocalDate pickupDate,LocalDate returnDate)
    {
        Double income = null;
        long daysDifference = ChronoUnit.DAYS.between(pickupDate, returnDate);
        income = ((double) daysDifference)+1.00;
        return income;
    }
    private String extractUserIDFromTitle(String title) {
    int startIndex = title.indexOf("(");
    int endIndex = title.indexOf(")");
    
    if (startIndex != -1 && endIndex != -1 && startIndex < endIndex) {
        return title.substring(startIndex + 1, endIndex);
    } else {
        return null;
    }
}
    
    private void updateLabelsFromSelectedRow(int selectedRow) {
    try {
        String value = (String) VehicleTable.getValueAt(selectedRow, 0);
        ResultSet rt;
        CBooking c2 = new CBooking();
        rt = c2.getVehicleData(value);
        
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
    
    }
    private void setupUI()
    {
        String[] columnNames = {"Number Plate","Model", "KMs Per Liter","Price (Per day)","Overdue Price (Per Day)"};
        tableModel = new DefaultTableModel();
        tableModel.setColumnIdentifiers(columnNames);
        VehicleTable.setModel(tableModel);
    }
    
    
    private void populateTableWithData() {
        
    // Create a font with the desired size
    Font tableFont = new Font("Segoe UI", Font.PLAIN, 36); // Change the font and size as needed

    // Create a cell renderer with the new font
    DefaultTableCellRenderer cellRenderer = new DefaultTableCellRenderer();
    cellRenderer.setFont(tableFont);

    // Apply the cell renderer to all columns
    for (int columnIndex = 0; columnIndex < tableModel.getColumnCount(); columnIndex++) {
        VehicleTable.getColumnModel().getColumn(columnIndex).setCellRenderer(cellRenderer);
    }
    
    String selectedType = (String) cmb_type.getSelectedItem();
    int selectedSeats = Integer.parseInt((String) cmb_noofseats.getSelectedItem());
    String selectedFuelType = (String) cmb_fueltype.getSelectedItem();
    
    
    
    CBooking c1 = new CBooking();
    rst = c1.getFilteredVehicle(selectedType, selectedSeats, selectedFuelType);

    
    try {
    // Clear any existing data
    tableModel.setRowCount(0);
    
    
    
    // Populate the table model with data from the result set
    while (rst.next()) {
        String registrationPlateNumber = rst.getString("RegistrationPlateNumber");
        String model = rst.getString("Model");
        double kmPerLiter = rst.getDouble("KmPerLiter");
        double dailyPrice = rst.getDouble("DailyPrice");
        double overduePrice = rst.getDouble("OverduePrice");
        

        // Add the data to the table model
        Object[] rowData = {registrationPlateNumber,model, kmPerLiter, dailyPrice, overduePrice};
        tableModel.addRow(rowData);
    }
} catch (SQLException e) {
    e.printStackTrace();
}


    for (int row = 0; row < VehicleTable.getRowCount(); row++) {
            int rowHeight = VehicleTable.getRowHeight();

            for (int column = 0; column < VehicleTable.getColumnCount(); column++) {
                Component comp = VehicleTable.prepareRenderer(VehicleTable.getCellRenderer(row, column), row, column);
                rowHeight = Math.max(rowHeight, comp.getPreferredSize().height + 15);
            }

            VehicleTable.setRowHeight(row, rowHeight);
        }
}

     public class MultiLineCellRenderer extends JTextArea implements TableCellRenderer {
        public MultiLineCellRenderer() {
            setLineWrap(true);
            setWrapStyleWord(true);
            setOpaque(true);
        }

        public Component getTableCellRendererComponent(JTable table, Object value,
                                                       boolean isSelected, boolean hasFocus,
                                                       int row, int column) {
            if (isSelected) {
                setForeground(table.getSelectionForeground());
                setBackground(table.getSelectionBackground());
            } else {
                setForeground(table.getForeground());
                setBackground(table.getBackground());
            }
            setText((value == null) ? "" : value.toString());
            return this;
        }
    }
     
    //Nadith codes end

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        sample1 = new javax.swing.JLabel();
        lbl_plate = new javax.swing.JLabel();
        sample2 = new javax.swing.JLabel();
        sample3 = new javax.swing.JLabel();
        sample4 = new javax.swing.JLabel();
        sample5 = new javax.swing.JLabel();
        sample6 = new javax.swing.JLabel();
        sample7 = new javax.swing.JLabel();
        lbl_model = new javax.swing.JLabel();
        lbl_vtype = new javax.swing.JLabel();
        lbl_noofseats = new javax.swing.JLabel();
        lbl_kmperliter = new javax.swing.JLabel();
        lbl_fueltype = new javax.swing.JLabel();
        lbl_dailyprice = new javax.swing.JLabel();
        lbl_overdueprice = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        cmb_type = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        VehicleTable = new javax.swing.JTable();
        jLabel4 = new javax.swing.JLabel();
        cmb_noofseats = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        cmb_fueltype = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        lbl_fprice = new javax.swing.JLabel();
        txt_purpose = new javax.swing.JTextField();
        btn_book = new javax.swing.JButton();
        btn_clear = new javax.swing.JButton();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        btn_return = new javax.swing.JButton();
        btn_pickup = new javax.swing.JButton();
        lbl_pickup = new javax.swing.JLabel();
        lbl_return = new javax.swing.JLabel();
        lbl_cusid = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Vehicle Booking");
        setForeground(new java.awt.Color(255, 204, 204));

        jPanel1.setBackground(new java.awt.Color(204, 204, 204));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        jLabel3.setText("Selected Vehicle Information");

        sample1.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        sample1.setText("Vehicle Type");

        lbl_plate.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        lbl_plate.setText("-");

        sample2.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        sample2.setText("Seat Capacity");

        sample3.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        sample3.setText("Model");

        sample4.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        sample4.setText("Fuel Type");

        sample5.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        sample5.setText("KM Per Liter ");

        sample6.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        sample6.setText("Daily Price ");

        sample7.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        sample7.setText("Overdue Price ");

        lbl_model.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        lbl_model.setText("-");

        lbl_vtype.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        lbl_vtype.setText("-");

        lbl_noofseats.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        lbl_noofseats.setText("-");

        lbl_kmperliter.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        lbl_kmperliter.setText("-");

        lbl_fueltype.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        lbl_fueltype.setText("-");

        lbl_dailyprice.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        lbl_dailyprice.setText("-");
        lbl_dailyprice.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                lbl_dailypricePropertyChange(evt);
            }
        });

        lbl_overdueprice.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        lbl_overdueprice.setText("-");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(69, 69, 69)
                        .addComponent(jLabel3))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(sample1, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(sample3, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(sample2, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(sample5, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(sample4, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(sample6, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(sample7, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lbl_noofseats, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                            .addComponent(lbl_vtype, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbl_model, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lbl_kmperliter, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lbl_fueltype, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lbl_dailyprice, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lbl_overdueprice, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap(35, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(lbl_plate, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(146, 146, 146))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addComponent(jLabel3)
                .addGap(29, 29, 29)
                .addComponent(lbl_plate, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(sample1, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_vtype))
                .addGap(62, 62, 62)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(sample3, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_model))
                .addGap(60, 60, 60)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(sample2, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_noofseats))
                .addGap(62, 62, 62)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(sample5, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_kmperliter))
                .addGap(58, 58, 58)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(sample4, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_fueltype))
                .addGap(73, 73, 73)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(sample6, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_dailyprice))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(sample7, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_overdueprice))
                .addGap(16, 16, 16))
        );

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel2.setText("Booking Information");

        cmb_type.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        cmb_type.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Car", "MiniVan", "Van" }));
        cmb_type.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmb_typeActionPerformed(evt);
            }
        });

        VehicleTable.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        VehicleTable.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(VehicleTable);

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        jLabel4.setText("Select Number of Seats");

        cmb_noofseats.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        cmb_noofseats.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "2", "4", "5" }));
        cmb_noofseats.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmb_noofseatsActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        jLabel5.setText("Please Select a Vehicle");

        cmb_fueltype.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        cmb_fueltype.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Petrol", "Diesel" }));
        cmb_fueltype.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmb_fueltypeActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        jLabel6.setText("Select Fuel Type ");

        jLabel7.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        jLabel7.setText("Select Vehicle Type  ");

        jLabel8.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        jLabel8.setText("Select Pickup Date ");

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel9.setText("Filter Vehicles");

        jLabel10.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        jLabel10.setText("Select Return Date ");

        lbl_fprice.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        lbl_fprice.setText("-");

        txt_purpose.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

        btn_book.setBackground(new java.awt.Color(0, 153, 204));
        btn_book.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        btn_book.setText("BOOK");
        btn_book.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_bookActionPerformed(evt);
            }
        });

        btn_clear.setBackground(new java.awt.Color(255, 51, 51));
        btn_clear.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        btn_clear.setText("CLEAR");
        btn_clear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_clearActionPerformed(evt);
            }
        });

        jLabel12.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        jLabel12.setText("Purpose of booking");

        jLabel13.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        jLabel13.setText("Final Price");

        jPanel2.setBackground(new java.awt.Color(0, 153, 153));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Book a Vehicle");

        jLabel11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/booking (5).png"))); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(362, Short.MAX_VALUE)
                .addComponent(jLabel11)
                .addGap(18, 18, 18)
                .addComponent(jLabel1)
                .addGap(38, 38, 38)
                .addComponent(jLabel14)
                .addGap(309, 309, 309))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel11)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel14))
                        .addGap(6, 6, 6)))
                .addContainerGap())
        );

        btn_return.setText("Select Date");
        btn_return.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_returnActionPerformed(evt);
            }
        });

        btn_pickup.setText("Select Date");
        btn_pickup.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_pickupActionPerformed(evt);
            }
        });

        lbl_pickup.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        lbl_pickup.setText("-");
        lbl_pickup.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                lbl_pickupPropertyChange(evt);
            }
        });

        lbl_return.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        lbl_return.setText("-");
        lbl_return.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                lbl_returnPropertyChange(evt);
            }
        });

        lbl_cusid.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        lbl_cusid.setText("-");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(13, 13, 13)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel6)
                                            .addComponent(jLabel7))
                                        .addGap(31, 31, 31)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(cmb_type, 0, 158, Short.MAX_VALUE)
                                            .addComponent(cmb_fueltype, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                    .addComponent(jLabel5))
                                .addGap(85, 85, 85)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 237, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(cmb_noofseats, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(lbl_cusid, javax.swing.GroupLayout.PREFERRED_SIZE, 237, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(jLabel9)
                            .addComponent(jLabel2)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(31, 31, 31)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel8)
                                        .addGap(18, 18, 18)
                                        .addComponent(lbl_pickup, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(btn_pickup)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jLabel10)
                                        .addGap(18, 18, 18)
                                        .addComponent(lbl_return, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(btn_return))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel12)
                                        .addGap(18, 18, 18)
                                        .addComponent(txt_purpose, javax.swing.GroupLayout.PREFERRED_SIZE, 763, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addComponent(jLabel13)
                                        .addGap(43, 43, 43)
                                        .addComponent(lbl_fprice)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(btn_clear, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(36, 36, 36)
                                        .addComponent(btn_book, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(32, 32, 32)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 972, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel9)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(cmb_type, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel7))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel4)
                        .addComponent(cmb_noofseats, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cmb_fueltype, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(42, 42, 42)
                        .addComponent(lbl_cusid)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel2)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(jLabel10)
                    .addComponent(btn_return, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_pickup, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_pickup)
                    .addComponent(lbl_return))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(42, 42, 42)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txt_purpose, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel12))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 37, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btn_clear, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btn_book, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbl_fprice))
                        .addGap(31, 31, 31))
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel13)
                        .addGap(49, 49, 49))))
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cmb_typeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmb_typeActionPerformed
        
        String selectedType = (String) cmb_type.getSelectedItem();

        // Update cmb_noofseats options based on the selected type
        updateNoOfSeatsOptions(selectedType);
        populateTableWithData();
    }//GEN-LAST:event_cmb_typeActionPerformed
    
    private void updateNoOfSeatsOptions(String selectedType) 
    {
    DefaultComboBoxModel<String> noOfSeatsModel = new DefaultComboBoxModel<>();

    if ("Car".equals(selectedType)) {
        // Add Car-specific options
        noOfSeatsModel.addElement("2");
        noOfSeatsModel.addElement("4");
        noOfSeatsModel.addElement("5");
    } else if ("MiniVan".equals(selectedType)) {
        // Add MiniVan-specific options
        noOfSeatsModel.addElement("5");
        noOfSeatsModel.addElement("6");
        noOfSeatsModel.addElement("7");
        noOfSeatsModel.addElement("8");
    } else if ("Van".equals(selectedType)) {
        // Add Van-specific options
        noOfSeatsModel.addElement("8");
        noOfSeatsModel.addElement("9");
        noOfSeatsModel.addElement("10");
        noOfSeatsModel.addElement("11");
        noOfSeatsModel.addElement("12");
    }

    // Set the updated model for cmb_noofseats
    cmb_noofseats.setModel(noOfSeatsModel);
    }
    private void cmb_noofseatsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmb_noofseatsActionPerformed
        populateTableWithData();
    }//GEN-LAST:event_cmb_noofseatsActionPerformed

    private void cmb_fueltypeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmb_fueltypeActionPerformed
        populateTableWithData();
    }//GEN-LAST:event_cmb_fueltypeActionPerformed
    private static String generateNextId(String lastId) {
        int nextNumber = 1;
        if (lastId != null && lastId.startsWith("B")) {
            int lastNumber = Integer.parseInt(lastId.substring(1));
            nextNumber = lastNumber + 1;
        }
        
        String formattedNumber = String.format("%03d", nextNumber);
        return "B" + formattedNumber;
    }
    
    private String generateLastID()
    {
        String lastId = null;
        try
        {
            ResultSet resultSet;
            CBooking c2 = new CBooking();
            resultSet = c2.getBookingDetials();
            
            if (resultSet.next()) {
                lastId = resultSet.getString(1);
            }

        }
        catch(SQLException e)
        {
            JOptionPane.showMessageDialog(null, e);
        }
        return lastId;
    }
    
    
    private void btn_bookActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_bookActionPerformed
        if(lbl_plate.getText().isEmpty() || lbl_plate.getText() == "-")
        {
            JOptionPane.showMessageDialog(null, "Please select a Vehicle to Book", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
        else if(lbl_pickup.getText() == "-" || lbl_pickup.getText().isEmpty())
        {
            JOptionPane.showMessageDialog(null, "Please select Pick Up Date of Vehicle", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
        else if(lbl_return.getText() == "-" || lbl_return.getText().isEmpty())
        {
            JOptionPane.showMessageDialog(null, "Please select Return Date of Vehicle", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
        else if(txt_purpose.getText().isEmpty())
        {
            JOptionPane.showMessageDialog(null, "Please fill the purpose of booking a vehicle", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
        else
        {
            String pickString = lbl_pickup.getText();
            String returnString = lbl_return.getText();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            datep = LocalDate.parse(pickString, formatter);
            dater = LocalDate.parse(returnString, formatter);
            LocalDate currentDate = LocalDate.now();
            
            
            if(datediff(datep,dater) < 0.00)
            {
                JOptionPane.showMessageDialog(null, "Return Date cannot be the before the Pickup Date", "ERROR", JOptionPane.ERROR_MESSAGE);
            }
            else
            {
                String bookID,cusID,VehID,purpose,status;
                LocalDate pickupdate,returndate,bookdate;
                Double price;
                customer = getTitle();
                bookID = generateNextId(generateLastID());
                cusID = extractUserIDFromTitle(customer);
                VehID = VehicleID;
                pickupdate = datep;
                returndate = dater;
                purpose = txt_purpose.getText();
                status = "Pending";
                price = Double.parseDouble(lbl_dailyprice.getText()) * (datediff(datep,dater));
                bookdate = currentDate;
                
                
                CBooking cx = new CBooking();
                cx.setBooking(bookID, cusID, VehID, pickupdate, returndate, purpose, status, price, bookdate);

            }
 
        }
    }//GEN-LAST:event_btn_bookActionPerformed

    private void btn_clearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_clearActionPerformed
        
        cmb_noofseats.setSelectedIndex(0);
        cmb_type.setSelectedIndex(0);
        cmb_fueltype.setSelectedIndex(0);
        lbl_plate.setText("-");
        lbl_vtype.setText("-");
        lbl_model.setText("-");
        lbl_noofseats.setText("-");
        lbl_kmperliter.setText("-");
        lbl_fueltype.setText("-");
        lbl_dailyprice.setText("-");
        lbl_overdueprice.setText("-");
        lbl_pickup.setText("-");
        lbl_return.setText("-");
        txt_purpose.setText("");
    }//GEN-LAST:event_btn_clearActionPerformed

    private void btn_pickupActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_pickupActionPerformed
        DatePicker dtpickup = new DatePicker(this);
        lbl_pickup.setText(dtpickup.setPickedDate());
        LocalDate currentDate = LocalDate.now();

        // Calculate tomorrow's date
        LocalDate tomorrow = currentDate.plusDays(1);

        
    }//GEN-LAST:event_btn_pickupActionPerformed

    private void btn_returnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_returnActionPerformed
        DatePicker dtreturn = new DatePicker(this);
        lbl_return.setText(dtreturn.setPickedDate());
    }//GEN-LAST:event_btn_returnActionPerformed

    @SuppressWarnings("Convert2Lambda")
    private void lbl_dailypricePropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_lbl_dailypricePropertyChange
        String pickupText = lbl_pickup.getText();
        String returnText = lbl_return.getText();
        
        if ((pickupText.equals("-") || returnText.equals("-")) || (pickupText.isEmpty() || returnText.isEmpty())) {
        lbl_fprice.setText("-");
    } else {
        String dailyPriceText = lbl_dailyprice.getText();
        if (!dailyPriceText.equals("-")) {
            String pickString = lbl_pickup.getText();
            String returnString = lbl_return.getText();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            datep = LocalDate.parse(pickString, formatter);
            dater = LocalDate.parse(returnString, formatter);
            double calculatedValue = calculateValue(dailyPriceText);
            lbl_fprice.setText(String.valueOf(calculatedValue));
        }
    }
    }//GEN-LAST:event_lbl_dailypricePropertyChange

    private void lbl_pickupPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_lbl_pickupPropertyChange
        String pickupText = lbl_pickup.getText();
        String returnText = lbl_return.getText();
        
        if ((pickupText.equals("-") || returnText.equals("-")) || (pickupText.isEmpty() || returnText.isEmpty())) {
        lbl_fprice.setText("-");
    } else {
        String dailyPriceText = lbl_dailyprice.getText();
        if (!dailyPriceText.equals("-")) {
            String pickString = lbl_pickup.getText();
            String returnString = lbl_return.getText();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            datep = LocalDate.parse(pickString, formatter);
            dater = LocalDate.parse(returnString, formatter);
            double calculatedValue = calculateValue(dailyPriceText);
            if(calculatedValue < 0.00)
            {
                lbl_fprice.setText("-");
            }
            else
            {
                lbl_fprice.setText(String.valueOf(calculatedValue));
            }
        }
    }
    }//GEN-LAST:event_lbl_pickupPropertyChange

    private void lbl_returnPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_lbl_returnPropertyChange
        String pickupText = lbl_pickup.getText();
        String returnText = lbl_return.getText();
        
        if ((pickupText.equals("-") || returnText.equals("-")) || (pickupText.isEmpty() || returnText.isEmpty())) {
        lbl_fprice.setText("-");
    } else {
        String dailyPriceText = lbl_dailyprice.getText();
        if (!dailyPriceText.equals("-")) {
            String pickString = lbl_pickup.getText();
            String returnString = lbl_return.getText();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            datep = LocalDate.parse(pickString, formatter);
            dater = LocalDate.parse(returnString, formatter);
            
            double calculatedValue = calculateValue(dailyPriceText);
            if(calculatedValue < 0.00)
            {
                lbl_fprice.setText("-");
            }
            else
            {
                lbl_fprice.setText(String.valueOf(calculatedValue));
            }
            
        }
    }
    }//GEN-LAST:event_lbl_returnPropertyChange

    
    private double calculateValue(String value) {
            Double fprice = Double.parseDouble(lbl_dailyprice.getText());
            Double diff = datediff(datep,dater);
            fprice = fprice * diff;
            return fprice;
            
    
}
  
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
            java.util.logging.Logger.getLogger(VBookingForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(VBookingForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(VBookingForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(VBookingForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new VBookingForm().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable VehicleTable;
    private javax.swing.JButton btn_book;
    private javax.swing.JButton btn_clear;
    private javax.swing.JButton btn_pickup;
    private javax.swing.JButton btn_return;
    private javax.swing.JComboBox<String> cmb_fueltype;
    private javax.swing.JComboBox<String> cmb_noofseats;
    private javax.swing.JComboBox<String> cmb_type;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lbl_cusid;
    private javax.swing.JLabel lbl_dailyprice;
    private javax.swing.JLabel lbl_fprice;
    private javax.swing.JLabel lbl_fueltype;
    private javax.swing.JLabel lbl_kmperliter;
    private javax.swing.JLabel lbl_model;
    private javax.swing.JLabel lbl_noofseats;
    private javax.swing.JLabel lbl_overdueprice;
    private javax.swing.JLabel lbl_pickup;
    private javax.swing.JLabel lbl_plate;
    private javax.swing.JLabel lbl_return;
    private javax.swing.JLabel lbl_vtype;
    private javax.swing.JLabel sample1;
    private javax.swing.JLabel sample2;
    private javax.swing.JLabel sample3;
    private javax.swing.JLabel sample4;
    private javax.swing.JLabel sample5;
    private javax.swing.JLabel sample6;
    private javax.swing.JLabel sample7;
    private javax.swing.JTextField txt_purpose;
    // End of variables declaration//GEN-END:variables
}
