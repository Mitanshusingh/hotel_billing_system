package com.company;
public class Main {
public static void main(String[] args) {
new Hotel();
}
}
package com.company;
import com.toedter.calendar.JDateChooser;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.text.DateFormat;
import java.util.Vector;
import java.util.concurrent.TimeUnit;
public class Hotel {
private JPanel hotelPanel;
private JTextField roomNo;
private JTextField name;
private JTextField roomCharge;
private JTable table1;
private JPanel checkOutDate;
private JPanel checkInDate;
private JButton ADDButton;
JFrame hotel = new JFrame();
JDateChooser checkIn = new JDateChooser();
JDateChooser checkOut = new JDateChooser();
public Hotel(){
hotel.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
hotel.setContentPane(hotelPanel);
hotel.pack();
hotel.setLocationRelativeTo(null);
hotel.setSize(450,400);
hotel.setVisible(true);
checkInDate.add(checkIn);
checkOutDate.add(checkOut);
tableData();
ADDButton.addActionListener(new ActionListener() {
@Override
public void actionPerformed(ActionEvent e) {
if(roomNo.getText().equals("")|| name.getText().equals("")|| roomCharge.getText().equals("")|| checkIn.getDate()==null||checkOut.getDate()==null){
JOptionPane.showMessageDialog(null,"Please Fill All Fields to add Record.");
}else if(checkOut.getDate().before(checkIn.getDate())){
JOptionPane.showMessageDialog(null,"Check Out Date Cannot Be Before Check In Date!");
}else{
try {
String sql = "insert into hotel"+"(Room_No,Name,Check_In,Check_Out,Room_Service,Total_Amount)"+"values (?,?,?,?,?,?)";
Class.forName("com.mysql.cj.jdbc.Driver");
Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/intern","root","root");
PreparedStatement statement = connection.prepareStatement(sql);
// total calculation
long millies = Math.abs(checkOut.getDate().getTime()-checkIn.getDate().getTime());
long days = TimeUnit.DAYS.convert(millies,TimeUnit.MILLISECONDS);
int total = (int)days*1000 + Integer.parseInt(roomCharge.getText());
// conversion
String outDate = DateFormat.getDateInstance().format(checkOut.getDate());
String inDate = DateFormat.getDateInstance().format(checkIn.getDate());
statement.setInt(1,Integer.parseInt(roomNo.getText()));
statement.setString(2, name.getText());
statement.setString(3, inDate);
statement.setString(4,outDate);
statement.setString(5,roomCharge.getText());
statement.setInt(6,total);
statement.executeUpdate();
JOptionPane.showMessageDialog(null,"ITEM ADDED SUCCESSFULLY");
roomNo.setText("");
name.setText("");
roomCharge.setText("");
checkIn.setCalendar(null);
checkOut.setCalendar(null);
}catch (Exception ex){
JOptionPane.showMessageDialog(null,ex.getMessage());
}
tableData();
}
}
});
}
public void tableData() {
try{
String a= "Select* from hotel";
Class.forName("com.mysql.cj.jdbc.Driver");
Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/intern","root","root");
Statement statement = connection.createStatement();
ResultSet rs = statement.executeQuery(a);
// table1.setModel(new DefaultTableModel(null, new String[]{"ID", "ITEM NAME", "QUANTITY", "PRICE"}));
table1.setModel(buildTableModel(rs));
}catch (Exception ex1){
JOptionPane.showMessageDialog(null,ex1.getMessage());
}
}
public static DefaultTableModel buildTableModel(ResultSet rs)
throws SQLException {
ResultSetMetaData metaData = rs.getMetaData();
// names of columns
Vector<String> columnNames = new Vector<String>();
int columnCount = metaData.getColumnCount();
for (int column = 1; column <= columnCount; column++) {
columnNames.add(metaData.getColumnName(column));
}
// data of the table
Vector<Vector<Object>> data = new Vector<Vector<Object>>();
while (rs.next()) {
Vector<Object> vector = new Vector<Object>();
for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
vector.add(rs.getObject(columnIndex));
}
data.add(vector);
}
return new DefaultTableModel(data, columnNames);
}
}