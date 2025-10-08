package com.microsoft.sqlserver.DAO;
import com.microsoft.sqlserver.DTO.*;
import java.sql.*;
import java.util.Vector;
public class receiptDetailsDAO {
    private Connection conn = null;
    public void openConnection() {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            String dbUrl = "jdbc:sqlserver://localhost:1433;databaseName=QLCH;encrypt=true;trustServerCertificate=true";
            String username = "sa";
            String password = "";
            conn = DriverManager.getConnection(dbUrl, username, password);
        } catch (ClassNotFoundException e) {
            System.out.println("Khong tim thay driver");
        } catch (SQLException e) {
            System.out.println("Khong the ket noi den CSDL");
        }
    }

    public void closeConnection() {
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            System.out.println("Khong the dong ket noi CSDL");
        }
    }

    public Vector<receiptDetailsDTO> getAllReceiptDetails() {
        Vector<receiptDetailsDTO> arr = new Vector<receiptDetailsDTO>();
        String sql = "SELECT * FROM ReceiptsDetails";
        try {
            openConnection();
            Statement s = conn.createStatement();
            ResultSet rs = s.executeQuery(sql);
            while (rs.next()) {
                String receiptID = rs.getString("ReceiptID");
                String productID = rs.getString("ProductID");
                int quantity = rs.getInt("Quantity");
                int price = rs.getInt("Price");
                arr.add(new receiptDetailsDTO(receiptID, productID, quantity, price));
            }
        } catch (SQLException e) {
            System.out.println("Khong the lay du lieu tu CSDL");
        } finally {
            closeConnection();
        }
        return arr;
    }

    public Vector<receiptDetailsDTO> getAllReceiptDetails(String receiptID){
        Vector<receiptDetailsDTO> arr = new Vector<receiptDetailsDTO>();
        String sql = "SELECT * FROM ReceiptsDetails WHERE ReceiptID = ?";
        try{
            openConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, receiptID);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                String productID = rs.getString("ProductID");
                int quantity = rs.getInt("Quantity");
                int price = rs.getInt("Price");
                arr.add(new receiptDetailsDTO(receiptID, productID, quantity, price));
            }
        }
        catch(SQLException e){
            System.out.println("Khong the lay du lieu tu CSDL");
        }
        finally{
            closeConnection();
        }
        return arr;
    }

    public boolean addReceiptDetails(receiptDetailsDTO receiptDetails) {
        String sql = "INSERT INTO ReceiptsDetails(ReceiptID, ProductID, Quantity, Price) VALUES(?, ?, ?, ?)";
        try{
            openConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, receiptDetails.getReceiptID());
            ps.setString(2, receiptDetails.getProductID());
            ps.setInt(3, receiptDetails.getQuantity());
            ps.setInt(4, receiptDetails.getPrice());
            ps.executeUpdate();
            return true;
        }catch (SQLException e) {
            System.out.println("Khong the them du lieu vao CSDL");
            return false;
        }finally{
            closeConnection();
        }
    }

    public boolean addReceiptDetails(Vector<receiptDetailsDTO> receiptDetails) {
        String sql = "INSERT INTO ReceiptsDetails(ReceiptID, ProductID, Quantity, Price) VALUES(?, ?, ?, ?)";
        PreparedStatement ps = null;
    
        try {
            openConnection();
            conn.setAutoCommit(false);
    
            ps = conn.prepareStatement(sql);
            for (receiptDetailsDTO detail : receiptDetails) {
                ps.setString(1, detail.getReceiptID());
                ps.setString(2, detail.getProductID());
                ps.setInt(3, detail.getQuantity());
                ps.setInt(4, detail.getPrice());
                ps.addBatch();
            }
    
            ps.executeBatch(); 
            conn.commit(); 
            conn.setAutoCommit(true); 
            System.out.println("Receipt details added successfully.");
            return true;
    
        } catch (SQLException e) {
            try {
                if (conn != null) {
                    conn.rollback(); 
                    System.out.println("Transaction rolled back.");
                }
            } catch (SQLException rollbackEx) {
                System.out.println("Failed to rollback transaction: " + rollbackEx.getMessage());
            }
            e.printStackTrace(); 
            return false;
    
        } finally {
            try {
                if (ps != null) {
                    ps.close(); 
                }
                closeConnection(); 
            } catch (SQLException closeEx) {
                System.out.println("Failed to close resources: " + closeEx.getMessage());
            }
        }
    }

    public int getTotalPrice(String receiptID) {
        int totalprice = 0;
        String sql = "SELECT SUM(Quantity * Price) AS TotalPrice FROM ReceiptsDetails WHERE ReceiptID = ?";
        try {
            openConnection();
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, receiptID);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        totalprice = rs.getInt("TotalPrice");
                        if (rs.wasNull()) {
                            totalprice = 0;
                        }
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving total price from database: " + e.getMessage());
        } finally {
            closeConnection();
        }
        return totalprice;
    }

}
