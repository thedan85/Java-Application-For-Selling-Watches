package com.microsoft.sqlserver.DAO;
import com.microsoft.sqlserver.DTO.*;
import com.microsoft.sqlserver.BUS.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.sql.*;
import java.util.Vector;
import java.util.HashMap;


public class receiptDAO {
    private Connection conn = null;

    public void openConnection() {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            String dbUrl = "jdbc:sqlserver://localhost:1433;databaseName=QLCH;encrypt=true;trustServerCertificate=true";
            String username = "sa";
            String password = "";
            conn = DriverManager.getConnection(dbUrl, username, password);
        }catch (ClassNotFoundException e) {
            System.out.println("Khong tim thay driver");
        }catch (SQLException e) {
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

    public Vector<receiptDTO> getAllReceipts() {
        Vector<receiptDTO> arr = new Vector<receiptDTO>();
        String sql = "SELECT * FROM Receipts WHERE EmployeeID IS NOT NULL";
        try {
            openConnection();
            Statement s = conn.createStatement();
            ResultSet rs = s.executeQuery(sql);
            while (rs.next()) {
                String receiptID = rs.getString("ReceiptID");
                String receiptDate = rs.getString("ReceiptDate");
                String employeeID = rs.getString("EmployeeID");
                boolean receiptType = rs.getBoolean("ReceiptType");
                
                if(receiptType)
                {
                    String providerID = rs.getString("ProviderID");
                    arr.add(new importDTO(receiptID, receiptDate, employeeID, providerID));
                }
                else
                {
                    String customerID = rs.getString("CustomersID");
                    arr.add(new exportDTO(receiptID, receiptDate, employeeID, customerID));
                }
            }
        } catch (SQLException e) {
            System.out.println("Khong the lay du lieu tu CSDL");
        } finally {
            closeConnection();
        }
        return arr;
    }

    public Vector<receiptDTO> getAllPendingReceipts(){
        Vector<receiptDTO> arr = new Vector<receiptDTO>();
        String sql = "SELECT * FROM Receipts WHERE EmployeeID IS NULL";
        try {
            openConnection();
            Statement s = conn.createStatement();
            ResultSet rs = s.executeQuery(sql);
            while (rs.next()) {
                String receiptID = rs.getString("ReceiptID");
                String receiptDate = rs.getString("ReceiptDate");
                boolean receiptType = rs.getBoolean("ReceiptType");
                
                if(receiptType)
                {
                    String providerID = rs.getString("ProviderID");
                    arr.add(new importDTO(receiptID, receiptDate, null, providerID));
                }
                else
                {
                    String customerID = rs.getString("CustomersID");
                    arr.add(new exportDTO(receiptID, receiptDate, null, customerID));
                }
            }
        } catch (SQLException e) {
            System.out.println("Khong the lay du lieu tu CSDL");
        } finally {
            closeConnection();
        }
        return arr;
    }

    public Vector<receiptDTO> showReceiptToCustomer()
    {

        accountDTO currentAccount = new cusAccountBUS().getCurrentAccount();
        Vector<receiptDTO> list = new Vector<receiptDTO>();
        String sql = "SELECT * From Receipts WHERE CustomersID = ? AND EmployeeID IS NOT NULL";
        try{
            openConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1,currentAccount.getUsername().trim());
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                String recID = rs.getString("ReceiptID");
                String recDate = rs.getString("ReceiptDate");
                String empID = rs.getString("EmployeeID");
                String cusID = rs.getString("CustomersID");
                list.add(new exportDTO(recID,recDate,empID,cusID));
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        finally{
            closeConnection();
        }
        return list;
    }

    public Vector<receiptDTO> showPendingReceiptToCustomer()
    {
        accountDTO currentAccount = new cusAccountBUS().getCurrentAccount();
        Vector<receiptDTO> list = new Vector<receiptDTO>();
        String sql = "SELECT * From Receipts WHERE CustomersID = ? AND EmployeeID IS NULL";
        try{
            openConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1,currentAccount.getUsername().trim());
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                String recID = rs.getString("ReceiptID");
                String recDate = rs.getString("ReceiptDate");
                String cusID = rs.getString("CustomersID");
                list.add(new exportDTO(recID,recDate,null,cusID));
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        finally{
            closeConnection();
        }
        return list;
    }
    
    public Vector<importDTO> viewImport()
    {
        Vector<importDTO> arr = new Vector<importDTO>();
        String sql = "SELECT * FROM Receipts WHERE ReceiptType = 1 AND EmployeeID IS NOT NULL";
        try{
            openConnection();
            Statement s = conn.createStatement();
            ResultSet rs = s.executeQuery(sql);
            while(rs.next()){
                String receiptID = rs.getString("ReceiptID");
                String receiptDate = rs.getString("ReceiptDate");
                String employeeID = rs.getString("EmployeeID");
                String providerID = rs.getString("ProviderID");
                arr.add(new importDTO(receiptID, receiptDate, employeeID, providerID));
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

    public Vector<exportDTO> viewExport()
    {
        Vector<exportDTO> arr = new Vector<exportDTO>();
        String sql = "SELECT * FROM Receipts WHERE ReceiptType = 0 AND EmployeeID IS NOT NULL";
        try{
            openConnection();
            Statement s = conn.createStatement();
            ResultSet rs = s.executeQuery(sql);
            while(rs.next()){
                String receiptID = rs.getString("ReceiptID");
                String receiptDate = rs.getString("ReceiptDate");
                String employeeID = rs.getString("EmployeeID");
                String customerID = rs.getString("CustomersID");
                arr.add(new exportDTO(receiptID, receiptDate, employeeID, customerID));
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
    public boolean checkReceiptType(String receiptID)
    {
        boolean receiptType = false;
        String sql = "SELECT ReceiptType FROM Receipts WHERE ReceiptID = ?";
        try{
            openConnection();
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, receiptID);
        ResultSet rs = ps.executeQuery();
        if (rs.next())
            receiptType = rs.getBoolean("ReceiptType");
        }
        catch(SQLException e){
            System.out.println(e);
        }
        finally{
            closeConnection();
        }
        return receiptType;
    }

    public Vector<receiptDTO> searchReceipt(String keyword) {
        Vector<receiptDTO> arr = new Vector<receiptDTO>();
        String sql = "SELECT * FROM Receipts WHERE ReceiptID LIKE ?";
        try {
            openConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, "%" + keyword + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String receiptID = rs.getString("ReceiptID");
                String receiptDate = rs.getString("ReceiptDate");
                String employeeID = rs.getString("EmployeeID");
                boolean receiptType = rs.getBoolean("ReceiptType");
                
                if(receiptType)
                {
                    String providerID = rs.getString("ProviderID");
                    arr.add(new importDTO(receiptID, receiptDate, employeeID, providerID));
                }
                else
                {
                    String customerID = rs.getString("CustomersID");
                    arr.add(new exportDTO(receiptID, receiptDate, employeeID, customerID));
                }
            }
        } catch (SQLException e) {
            System.out.println("Khong the lay du lieu tu CSDL");
        } finally {
            closeConnection();
        }
        return arr;
    }
    public boolean addCustomersReceipt(receiptDTO receipt){
        String sql = "INSERT INTO Receipts(ReceiptDate,ReceiptType,CustomersID) VALUES(?, ?, ?)";
        try{
            openConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, receipt.getReceiptDate());
            ps.setBoolean(2, false);
            ps.setString(3, ((exportDTO)receipt).getCustomerID());
            ps.executeUpdate();
            return true;
        }
        catch (SQLException e) {
            System.out.println("Khong the them du lieu vao CSDL");
            return false;
        }finally{
            closeConnection();
        }
    }

public boolean CreateImportReceipt(Vector<productDTO> product) {
    if (product == null || product.isEmpty()) {
        System.out.println("Product list is empty.");
        return false;
    }

    String sql = "INSERT INTO Receipts(ReceiptDate,ReceiptType,EmployeeID,ProviderID) VALUES (?,?,?,?)";   
    LocalDateTime now = LocalDateTime.now();
    Timestamp timestamp = Timestamp.valueOf(now);

    try {
        int receiptNum = 0;
        String recID = "";
        accountDTO currentAcc = new accountBUS().getCurrentAccount();

        openConnection();
        PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        ps.setTimestamp(1, timestamp);
        ps.setBoolean(2, true);
        ps.setString(3, currentAcc.getUsername().trim());
        ps.setString(4, product.get(0).getProviderID());
        ps.executeUpdate();
        System.out.println("Receipt inserted successfully.");

        ResultSet rs = ps.getGeneratedKeys();
        if (rs.next()) {
            receiptNum = rs.getInt(1);
            System.out.println("Generated ReceiptIDNum: " + receiptNum);
        } else {
            System.out.println("Failed to retrieve generated keys.");
            return false;
        }

        String sql2 = "SELECT ReceiptID FROM Receipts WHERE ReceiptIDNum = ?";
        PreparedStatement ps2 = conn.prepareStatement(sql2);
        ps2.setInt(1, receiptNum);
        ResultSet rs2 = ps2.executeQuery();
        if (rs2.next()) {
            recID = rs2.getString(1);
            System.out.println("Generated ReceiptID: " + recID);
        } else {
            System.out.println("No ReceiptID found for ReceiptIDNum: " + receiptNum);
            return false;
        }

        Vector<receiptDetailsDTO> recDList = new Vector<>();
        for (productDTO p : product) {
            receiptDetailsDTO r = new receiptDetailsDTO(recID, new productBUS().getProductIdByName(p.getProductName()), p.getQuantity(), p.getProductPrice());
            recDList.add(r);
        }

        boolean b = new receiptDetailsBUS().addReceiptDetails(recDList);
        if (!b) {
            System.out.println("Failed to add receipt details.");
        }
        return b;

    } catch (SQLException e) {
        System.out.println("SQL Exception: " + e.getMessage());
    } finally {
        closeConnection();
    }
    return false;
}

    public receiptDTO getReceiptDTOById(String receiptId)
    {
        String sql = "SELECT * FROM Receipts WHERE ReceiptID = ?";
        try{
            openConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, receiptId);
            ResultSet rs = ps.executeQuery();
            if(rs.next())
            {
                String receiptDate = rs.getString("ReceiptDate");
                String employeeID = rs.getString("EmployeeID");
                boolean receiptType = rs.getBoolean("ReceiptType");
                if(receiptType)
                {
                    String providerID = rs.getString("ProviderID");
                    return new importDTO(receiptId, receiptDate, employeeID, providerID);
                }
                else
                {
                    String customerID = rs.getString("CustomersID");
                    return new exportDTO(receiptId, receiptDate, employeeID, customerID);
                }
            }
        }
        catch(SQLException e){
            System.out.println(e.getMessage());
        }
        finally{
            closeConnection();
        }
        return null;
    }
    public boolean CreateExportReceipt(HashMap<String, Vector<productDTO>> productMap) {
        String sql = "INSERT INTO Receipts(ReceiptDate, ReceiptType, EmployeeID, CustomersID) VALUES (?, ?, ?, ?)";
        accountDTO currentAcc = new accountBUS().getCurrentAccount();
        LocalDateTime now = LocalDateTime.now();
        Timestamp timestamp = Timestamp.valueOf(now);
        String recID = "";
        try {
            openConnection();
            for (String customerID : productMap.keySet()) {
                PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ps.setTimestamp(1, timestamp);
                ps.setBoolean(2, false);
                ps.setString(3, currentAcc.getUsername().trim());
                ps.setString(4, customerID);
                ps.executeUpdate();
    
                ResultSet rs = ps.getGeneratedKeys();
                int receiptIDNum = 0;
                if (rs.next()) {
                    receiptIDNum = rs.getInt(1);
                    System.out.println("Generated ReceiptID: " + receiptIDNum);
                } else {
                    System.out.println("Failed to retrieve generated ReceiptID for CustomerID: " + customerID);
                    return false;
                }
                String sql2 = "SELECT ReceiptID FROM Receipts WHERE ReceiptIDNum = ?";
                PreparedStatement ps2 = conn.prepareStatement(sql2);
                ps2.setInt(1, receiptIDNum);
                ResultSet rs2 = ps2.executeQuery();
                if (rs2.next()) {
                    recID = rs2.getString(1);
                    System.out.println("Generated ReceiptID: " + recID);
                } else {
                    System.out.println("No ReceiptID found for ReceiptIDNum: " + receiptIDNum);
                    return false;
                }
                
                Vector<receiptDetailsDTO> recDList = new Vector<>();
                Vector<productDTO> products = productMap.get(customerID);
                for (productDTO product : products) {
                    receiptDetailsDTO r = new receiptDetailsDTO(recID, product.getProductID(), product.getQuantity(), product.getProductPrice());
                    recDList.add(r);
                }
                boolean b = new receiptDetailsBUS().addReceiptDetails(recDList);
                if (!b) {
                    System.out.println("Failed to add receipt details for CustomerID: " + customerID);
                    return false;
                }
                ps.close();
                rs.close();
            }
    
            return true;
    
        } catch (SQLException e) {
            System.out.println("SQL Exception: " + e.getMessage());
            return false;
        } finally {
            closeConnection();
        }
    }

    public boolean createBuyingProduct(Vector<productDTO> products, String customerID)
    {
        String sql = "INSERT INTO Receipts(ReceiptDate, ReceiptType, CustomersID) VALUES (?, ?, ?)";
        LocalDateTime now = LocalDateTime.now();
        Timestamp timestamp = Timestamp.valueOf(now);
        String recID = "";
        try {
            openConnection();
            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setTimestamp(1, timestamp);
            ps.setBoolean(2, false);
            ps.setString(3, customerID);
            ps.executeUpdate();
    
            ResultSet rs = ps.getGeneratedKeys();
            int receiptIDNum = 0;
            if (rs.next()) {
                receiptIDNum = rs.getInt(1);
                System.out.println("Generated ReceiptID: " + receiptIDNum);
            } else {
                System.out.println("Failed to retrieve generated ReceiptID for CustomerID: " + customerID);
                return false;
            }
            String sql2 = "SELECT ReceiptID FROM Receipts WHERE ReceiptIDNum = ?";
            PreparedStatement ps2 = conn.prepareStatement(sql2);
            ps2.setInt(1, receiptIDNum);
            ResultSet rs2 = ps2.executeQuery();
            if (rs2.next()) {
                recID = rs2.getString(1);
                System.out.println("Generated ReceiptID: " + recID);
            }else {
                System.out.println("No ReceiptID found for ReceiptIDNum: " + receiptIDNum);
                return false;
            }
                
                Vector<receiptDetailsDTO> recDList = new Vector<>();
                for (productDTO product : products) {
                    receiptDetailsDTO r = new receiptDetailsDTO(recID, product.getProductID(), product.getQuantity(), product.getProductPrice());
                    recDList.add(r);
                }
                boolean b = new receiptDetailsBUS().addReceiptDetails(recDList);
                if (!b) {
                    System.out.println("Failed to add receipt details for CustomerID: " + customerID);
                    return false;
                }
                ps.close();
                rs.close();
                return true;
            }
            catch (SQLException e) {
            System.out.println("SQL Exception: " + e.getMessage());
            return false;
        } finally {
            closeConnection();
        }
    }

    public int acceptReceipt(String receiptID,String empID){
        String sql = "UPDATE Receipts SET EmployeeID = ? WHERE ReceiptID = ?";
        try{
            openConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1,empID);
            ps.setString(2,receiptID);
            ps.executeUpdate();
            return 1;
        }
        catch(SQLException e){
            System.out.println(e.getMessage());
        }
        finally{
            closeConnection();
        }
        return 0;
    }

    public boolean refuseReceipt(receiptDTO receipt){
        String sql = "DELETE FROM Receipts WHERE ReceiptID = ?";
        String detailssql = "DELETE FROM ReceiptsDetails WHERE ReceiptID = ?";
        try{
            openConnection();
            Vector<receiptDetailsDTO> recDList = new receiptDetailsBUS().getAllReceiptDetails(receipt.getReceiptId());
            if(new productBUS().updateProductAfterRefuse(recDList))
            {
                PreparedStatement ps2 = conn.prepareStatement(detailssql);
                ps2.setString(1,receipt.getReceiptId());
                ps2.executeUpdate();
                ps2.close();

                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setString(1,receipt.getReceiptId());
                ps.executeUpdate();
                return true;
            }
            else return false;
        }catch(SQLException e){
            System.out.println(e.getMessage());
            return false;
        }
        finally{
            closeConnection();
        }
    }
    public Vector<Vector<Object>> getRevenueByMonth() {
        Vector<Vector<Object>> revenueData = new Vector<>();
        String sql = "SELECT YEAR(ReceiptDate) AS Year, MONTH(ReceiptDate) AS Month, ReceiptID " +
                     "FROM Receipts WHERE ReceiptType = 0 AND EmployeeID IS NOT NULL ORDER BY Year, Month";
        try {
            openConnection();
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            receiptDetailsDAO detailsDAO = new receiptDetailsDAO();
            int currentYear = -1;
            int currentMonth = -1;
            int monthRevenue = 0;
            
            while (rs.next()) {
                int year = rs.getInt("Year");
                int month = rs.getInt("Month");
                String receiptID = rs.getString("ReceiptID");
        
                int totalPrice = detailsDAO.getTotalPrice(receiptID);
                
                if (year != currentYear || month != currentMonth) {
                    if (currentYear != -1 && currentMonth != -1) {
                        Vector<Object> row = new Vector<>();
                        row.add(currentMonth);
                        row.add(currentYear);
                        row.add(monthRevenue);
                        revenueData.add(row);
                    }
    
                    currentYear = year;
                    currentMonth = month;
                    monthRevenue = 0;
                }
    
                monthRevenue += totalPrice;
            }

            if (currentYear != -1 && currentMonth != -1) {
                Vector<Object> row = new Vector<>();
                row.add(currentMonth);
                row.add(currentYear);
                row.add(monthRevenue);
                revenueData.add(row);
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving revenue data by month: " + e.getMessage());
        } finally {
            closeConnection();
        }
        return revenueData;
    }
    
    public Vector<Vector<Object>> getRevenueByYear() {
        Vector<Vector<Object>> revenueData = new Vector<>();
        String sql = "SELECT YEAR(ReceiptDate) AS Year, ReceiptID " +
                     "FROM Receipts WHERE ReceiptType = 0 AND EmployeeID IS NOT NULL ORDER BY Year";
        try{
            openConnection();
            int currentYear = -1;
            int yearRevenue = 0;
            
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            receiptDetailsDAO detailsDAO = new receiptDetailsDAO();
            while (rs.next()) {
                int year = rs.getInt("Year");
                String receiptID = rs.getString("ReceiptID");

                int totalPrice = detailsDAO.getTotalPrice(receiptID);
                if(year!=currentYear){
                    if(currentYear!=-1){
                        Vector<Object> row = new Vector<>();
                        row.add(currentYear);
                        row.add(yearRevenue);
                        revenueData.add(row);
                    }
                    currentYear = year;
                    yearRevenue = 0;
                }
                yearRevenue += totalPrice;
            }
            if(currentYear!=-1){
                Vector<Object> row = new Vector<>();
                row.add(currentYear);
                row.add(yearRevenue);
                revenueData.add(row);
            }
        }catch (SQLException e) {
        System.out.println("Error retrieving revenue data by year: " + e.getMessage());
    } finally {
        closeConnection();
    }
        return revenueData;
    }

    public Vector<receiptDTO> getReceiptsByDateRange(LocalDate fromDate, LocalDate toDate) {
        Vector<receiptDTO> filteredReceipts = new Vector<>();
        String query = "SELECT * FROM receipts WHERE CAST(receiptDate AS DATE) >= ? AND CAST(receiptDate AS DATE) <= ?";
        try{
            openConnection();
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setDate(1, java.sql.Date.valueOf(fromDate));
            stmt.setDate(2, java.sql.Date.valueOf(toDate));
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String receiptID = rs.getString("ReceiptID");
                String receiptDate = rs.getString("ReceiptDate");
                String employeeID = rs.getString("EmployeeID");
                boolean receiptType = rs.getBoolean("ReceiptType");
                receiptDTO receipt;
                if(receiptType)
                {
                    String providerID = rs.getString("ProviderID");
                    receipt = new importDTO(receiptID, receiptDate, employeeID, providerID);
                }
                else
                {
                    String customerID = rs.getString("CustomersID");
                    receipt = new exportDTO(receiptID, receiptDate, employeeID, customerID);
                }
                filteredReceipts.add(receipt);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return filteredReceipts;
    }
    public Vector<importDTO> getImportsByDateRange(LocalDate fromDate, LocalDate toDate) {
        Vector<importDTO> filteredReceipts = new Vector<>();
        String query = "SELECT * FROM receipts WHERE CAST(receiptDate AS DATE) >= ? AND CAST(receiptDate AS DATE) <= ? AND ReceiptType = 1";
        try{
            openConnection();
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setDate(1, java.sql.Date.valueOf(fromDate));
            stmt.setDate(2, java.sql.Date.valueOf(toDate));
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String receiptID = rs.getString("ReceiptID");
                String receiptDate = rs.getString("ReceiptDate");
                String employeeID = rs.getString("EmployeeID");
                String providerID = rs.getString("ProviderID");
                
                filteredReceipts.add(new importDTO(receiptID, receiptDate, employeeID, providerID));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return filteredReceipts;
    }
    public Vector<exportDTO> getExportsByDateRange(LocalDate fromDate, LocalDate toDate) {
        Vector<exportDTO> filteredReceipts = new Vector<>();
        String query = "SELECT * FROM receipts WHERE CAST(receiptDate AS DATE) >= ? AND CAST(receiptDate AS DATE) <= ? AND ReceiptType = 0";
        try{
            openConnection();
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setDate(1, java.sql.Date.valueOf(fromDate));
            stmt.setDate(2, java.sql.Date.valueOf(toDate));
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String receiptID = rs.getString("ReceiptID");
                String receiptDate = rs.getString("ReceiptDate");
                String employeeID = rs.getString("EmployeeID");
                String customerID = rs.getString("CustomersID");
                
                filteredReceipts.add(new exportDTO(receiptID, receiptDate, employeeID, customerID));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return filteredReceipts;
    }

    
}
