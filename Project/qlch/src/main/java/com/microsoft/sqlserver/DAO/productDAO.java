package com.microsoft.sqlserver.DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import com.microsoft.sqlserver.BUS.receiptBUS;
import com.microsoft.sqlserver.DTO.productDTO;
import com.microsoft.sqlserver.DTO.receiptDetailsDTO;

public class productDAO {
    public connectDatabase cndb = new connectDatabase() ;
    Vector<productDTO> productList = new Vector<productDTO>() ;
    Statement statement = null ;

    public Vector<productDTO> getProductList(){
        String sql = "SELECT * FROM Products" ;
        Vector<productDTO> proList = new Vector<productDTO>() ;

        try{
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            if( cndb.openConnectDB() ) {
                statement = cndb.con.createStatement() ;
                ResultSet rs = statement.executeQuery(sql) ;

                while(rs.next()){
                    productDTO pro = new productDTO() ;
                    pro.setProductID(rs.getString("ProductID")) ;
                    pro.setProductName(rs.getString("ProductName")) ;
                    pro.setProductPrice(rs.getInt("ProductPrice")) ;
                    pro.setQuantity(rs.getInt("Quantity")) ;
                    pro.setProviderID(rs.getString("ProviderID")) ;
                    pro.setProductType(rs.getString("ProductType")) ;

                    proList.add(pro) ;
                }
            rs.close() ;
            }
        }
        catch (Exception e) {
            System.out.println(e.getMessage()) ;
        }
        return proList ;
    }

    public Vector<String> getProductTypes() {
        String sql = "SELECT DISTINCT ProductType FROM Products";
        Vector<String> productTypes = new Vector<String>();
    
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            if (cndb.openConnectDB()) {
                statement = cndb.con.createStatement();
                ResultSet rs = statement.executeQuery(sql);
    
                while (rs.next()) {
                    String type = rs.getString("ProductType");
                    productTypes.add(type);
                }
                rs.close();
            }
        } catch (Exception e) {
            System.out.println("Error fetching product types: " + e.getMessage());
        }
    
        return productTypes;
    }

    public Vector<String> getProviderIDs() {
        String sql = "SELECT DISTINCT ProviderID FROM Products";
        Vector<String> providerIDs = new Vector<String>();
    
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            if (cndb.openConnectDB()) {
                statement = cndb.con.createStatement();
                ResultSet rs = statement.executeQuery(sql);
    
                while (rs.next()) {
                    String providerID = rs.getString("ProviderID");
                    providerIDs.add(providerID);
                }
                rs.close();
            }
        } catch (Exception e) {
            System.out.println("Error fetching provider IDs: " + e.getMessage());
        }
    
        return providerIDs;
    }
    
    
    public void createProductList() {
        try{
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            if( cndb.openConnectDB() ) {
                if(productList.size() > 0){
                    productList.removeAllElements() ;
                }

                statement = cndb.con.createStatement() ;
                ResultSet rs = statement.executeQuery("SELECT * FROM Products") ;

                while(rs.next()){
                    productDTO pro = new productDTO() ;

                    pro.setProductID(rs.getString("ProductID")) ;
                    pro.setProductName(rs.getString("ProductName")) ;
                    pro.setProductPrice(rs.getInt("ProductPrice")) ;
                    pro.setQuantity(rs.getInt("Quantity")) ;
                    pro.setProviderID(rs.getString("ProviderID")) ;
                    pro.setProductType(rs.getString("ProductType")) ;

                    productList.add(pro) ;
                }
            rs.close() ;
            }
        }
        catch (Exception e) {
            System.out.println(e.getMessage()) ;
        }
    }

    public Vector<productDTO> searchProductAdvanced(String keyword, String productType, String providerID, String priceOption, double minPrice, double maxPrice) {
        Vector<productDTO> productList = new Vector<>();
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
    
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            cndb.openConnectDB();
    
            StringBuilder queryBuilder = new StringBuilder("SELECT * FROM Products WHERE 1=1");
            List<Object> params = new ArrayList<>();
    
            if (keyword != null && !keyword.trim().isEmpty()) {
                queryBuilder.append(" AND (ProductID LIKE ? OR ProductName LIKE ?)");
                String kwParam = "%" + keyword.trim() + "%";
                params.add(kwParam);
                params.add(kwParam);
            }
    
            if (productType != null && !productType.trim().isEmpty()) {
                queryBuilder.append(" AND ProductType = ?");
                params.add(productType.trim());
            }
    
            if (providerID != null && !providerID.trim().isEmpty()) {
                queryBuilder.append(" AND ProviderID = ?");
                params.add(providerID.trim());
            }

            if (priceOption != null && priceOption.equals("Price range")) {
                queryBuilder.append(" AND ProductPrice BETWEEN ? AND ?");
                params.add(minPrice);
                params.add(maxPrice);
            }
    
            if (priceOption != null) {
                if (priceOption.equals("Low -> High")) {
                    queryBuilder.append(" ORDER BY ProductPrice ASC");
                } else if (priceOption.equals("High -> Low")) {
                    queryBuilder.append(" ORDER BY ProductPrice DESC");
                }
            }
    
            preparedStatement = cndb.con.prepareStatement(queryBuilder.toString());
    
            for (int i = 0; i < params.size(); i++) {
                preparedStatement.setObject(i + 1, params.get(i));
            }
    
            rs = preparedStatement.executeQuery();
    
            while (rs.next()) {
                productDTO pro = new productDTO();
                pro.setProductID(rs.getString("ProductID"));
                pro.setProductName(rs.getString("ProductName"));
                pro.setProductPrice(rs.getInt("ProductPrice"));
                pro.setQuantity(rs.getInt("Quantity"));
                pro.setProviderID(rs.getString("ProviderID"));
                pro.setProductType(rs.getString("ProductType"));
                productList.add(pro);
            }
    
        } catch (Exception e) {
            System.err.println("Error in searchProductAdvanced: " + e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (preparedStatement != null) preparedStatement.close();
                cndb.closeConnectDB();
            } catch (Exception ex) {
                System.err.println("Error closing resources: " + ex.getMessage());
            }
        }
    
        return productList;
    }
    

    public void addProduct(productDTO prod) {
        String sqlInsert = "INSERT INTO Products (ProductName, ProductPrice,Quantity, ProviderID, ProductType) VALUES (?, ?, ?, ?, ?)";
        String sqlSelect = "SELECT Quantity FROM Products WHERE ProductName = ? AND ProviderID = ? AND ProductType = ?";
        String sqlUpdate = "UPDATE Products SET Quantity = ? WHERE ProductName = ? AND ProviderID = ? AND ProductType = ?";
    
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
    
            if (cndb.openConnectDB()) {
                PreparedStatement stmtSelect = cndb.con.prepareStatement(sqlSelect);
                stmtSelect.setString(1, prod.getProductName());
                stmtSelect.setString(2, prod.getProviderID());
                stmtSelect.setString(3, prod.getProductType());
                ResultSet rs = stmtSelect.executeQuery();
    
                if (rs.next()) {
                    int currentQuantity = rs.getInt(1)+prod.getQuantity();
                    PreparedStatement stmtUpdate = cndb.con.prepareStatement(sqlUpdate);
                    stmtUpdate.setInt(1, currentQuantity);
                    stmtUpdate.setString(2, prod.getProductName());
                    stmtUpdate.setString(3, prod.getProviderID());
                    stmtUpdate.setString(4, prod.getProductType());
                    stmtUpdate.executeUpdate();
                    System.out.println("Product updated successfully.");
                    stmtUpdate.close();
                } else {
                    PreparedStatement stmtInsert = cndb.con.prepareStatement(sqlInsert);
                    stmtInsert.setString(1, prod.getProductName());
                    stmtInsert.setInt(2, prod.getProductPrice());
                    stmtInsert.setInt(3, prod.getQuantity());
                    stmtInsert.setString(4, prod.getProviderID());
                    stmtInsert.setString(5, prod.getProductType());
                    stmtInsert.executeUpdate();
                    System.out.println("Product inserted successfully.");
                    stmtInsert.close();
                }
    
                rs.close();
                stmtSelect.close();
                cndb.con.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void addProduct(Vector<productDTO> products) {
        for (productDTO p : products) {
            addProduct(p);
        }
    
        Map<String, Vector<productDTO>> grouped = new HashMap<>();
        for (productDTO p : products) {
            grouped.computeIfAbsent(p.getProviderID(), k -> new Vector<>()).add(p);
        }
    
        receiptBUS receiptBus = new receiptBUS();
        for (Map.Entry<String, Vector<productDTO>> entry : grouped.entrySet()) {
            String providerID = entry.getKey();
            Vector<productDTO> providerProducts = entry.getValue();
    
            boolean success = receiptBus.CreateImportReceipt(providerProducts);
            if (!success) {
                System.out.println("Failed to create import receipt for ProviderID: " + providerID);
            }
        }
    }
    public void exportProduct(productDTO prod){
        String sql = "UPDATE Products SET Quantity = ? WHERE ProductID = ?";
        String sqlSelect = "SELECT Quantity FROM Products WHERE ProductID = ?";
        int currentQuan = 0;
        try{
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            
            if( cndb.openConnectDB() ){
                PreparedStatement stmt2 = cndb.con.prepareStatement(sqlSelect);
                stmt2.setString(1, prod.getProductID());
                ResultSet rs = stmt2.executeQuery();
                if(rs.next()){
                    currentQuan = rs.getInt(1);
                }
                rs.close();
                stmt2.close();
                PreparedStatement stmt = cndb.con.prepareStatement(sql);
                stmt.setInt(1,currentQuan - prod.getQuantity());
                stmt.setString(2, prod.getProductID());
                stmt.executeUpdate();
            }
            else{
                System.out.println("Failed to connect to database");
                }
            }catch(Exception e){
                System.out.println(e.getMessage()) ;
            }
    }
    public void exportProduct(HashMap<String,Vector<productDTO>> productMap){
       for(Map.Entry<String,Vector<productDTO>> entry : productMap.entrySet()){
            Vector<productDTO> products = entry.getValue();
            
            for(productDTO p:products){
                exportProduct(p);
            }
            boolean success = new receiptBUS().CreateExportReceipt(productMap);
            if (!success) {
                System.out.println("Failed to create export receipt for CustomerID: " + entry.getKey());
            }
       }
    }

    public void buyProduct(Vector<productDTO> products,String customerID)
    {
        for(productDTO p : products){
            exportProduct(p);
        }
        boolean success = new receiptBUS().createBuyingProduct(products, customerID);
        if (!success) {
            System.out.println("Failed to create buying receipt for CustomerID: " + customerID);
        }
    }
    public void deleteProduct(productDTO prod){
        try{
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            if( cndb.openConnectDB() ){
                    statement = cndb.con.createStatement() ;
                    
                    String sql = "DELETE FROM Products WHERE ProductID = '" + prod.getProductID() + "'" ;
                    statement.executeUpdate(sql) ;
                    cndb.con.close();
                }
            }
            catch(Exception e){
                    System.out.println(e.getMessage()) ;
        }
    }

    public void updateProduct(productDTO prod){
        try{
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            if( cndb.openConnectDB() ){
            
            String updateQuery = "UPDATE Products SET ProductName = ? , ProductPrice = ? , Quantity = ? ,ProviderID = ?, ProductType = ? WHERE ProductID = ?" ;
            PreparedStatement stmt = cndb.con.prepareStatement(updateQuery);
                
                stmt.setString(1, prod.getProductName());
                stmt.setInt(2, prod.getProductPrice());
                stmt.setInt(3, prod.getQuantity());
                stmt.setString(4, prod.getProviderID());
                stmt.setString(5, prod.getProductType());
                stmt.setString(6, prod.getProductID());
                stmt.executeUpdate() ;
                stmt.close() ;
            }
        }
        catch(Exception e){
            System.out.println(e.getMessage()) ;
        }
    }

    public Vector<productDTO> searchProduct(String input){
        try{
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            cndb.openConnectDB();
            String query = "SELECT * FROM Products WHERE ProductID LIKE ? OR ProductName LIKE ?";
            PreparedStatement preparedStatement = cndb.con.prepareStatement(query) ;
            preparedStatement.setString(1, "%" + input + "%") ;
            preparedStatement.setString(2, "%" + input + "%") ;
            
            ResultSet rs = preparedStatement.executeQuery() ;
            Vector<productDTO> productList = new Vector<productDTO>() ;
            while(rs.next()){
                productDTO pro = new productDTO() ;

                    pro.setProductID(rs.getString("ProductID")) ;
                    pro.setProductName(rs.getString("ProductName")) ;
                    pro.setProductPrice(rs.getInt("ProductPrice")) ;
                    pro.setQuantity(rs.getInt("Quantity")) ;
                    pro.setProviderID(rs.getString("ProviderID")) ;
                    pro.setProductType(rs.getString("ProductType")) ;

                productList.add(pro) ;
            }
            
            return productList ;
        }
    
        catch(Exception e){
            System.out.println(e.getMessage()) ;
            return null ;
        }
    }

    public String getProductNameById(String productID)
    {
        String productName = null;
        String sql = "SELECT ProductName FROM Products WHERE ProductID = ?";
        try{
            cndb.openConnectDB();
            PreparedStatement ps = cndb.con.prepareStatement(sql);
            ps.setString(1, productID);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                productName = rs.getString("ProductName");
            }
        }
        catch(Exception e){
            System.out.println("Khong the lay du lieu tu CSDL");
        }
        return productName;
    }

    public int getProductQuantityById(String productID)
    {
        int quantity = 0;
        String sql = "SELECT Quantity FROM Products WHERE ProductID = ?";
        try{
            cndb.openConnectDB();
            PreparedStatement ps = cndb.con.prepareStatement(sql);
            ps.setString(1, productID);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                quantity = rs.getInt("Quantity");
            }
        }
        catch(Exception e){
            System.out.println("Khong the lay du lieu tu CSDL");
        }
        return quantity;
    }

    public String getProductIdByName(String name){
        String productID = null;
        String sql = "SELECT ProductID FROM Products WHERE ProductName = ?";
        try{
            cndb.openConnectDB();
            PreparedStatement ps = cndb.con.prepareStatement(sql);
            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                productID = rs.getString("ProductID");
            }
        }
        catch(Exception e){
            System.out.println("Khong the lay du lieu tu CSDL");
        }
        return productID;
    }

    public void updateProducts(Vector<productDTO> products){
    String sql = "UPDATE Products SET quantity = ? WHERE productID = ?";
    
    try{
        cndb.openConnectDB();
        PreparedStatement stmt = cndb.con.prepareStatement(sql);
        for (productDTO product : products) {
            stmt.setInt(1, product.getQuantity());
            stmt.setString(2, product.getProductID());
            stmt.addBatch();
        }

        stmt.executeBatch();
    } catch (Exception e) {
        e.printStackTrace();
    }
}

    public boolean updateProductAfterRefuse(Vector<receiptDetailsDTO> receiptDetails)
    {
        String sql = "UPDATE Products SET Quantity = ? WHERE ProductID = ? ";
        int currentQuan = 0;
        try{
            cndb.openConnectDB();
            PreparedStatement ps = cndb.con.prepareStatement(sql);
            for(receiptDetailsDTO r : receiptDetails){
                currentQuan = getProductQuantityById(r.getProductID()) + r.getQuantity();
                ps.setInt(1,currentQuan);
                ps.setString(2,r.getProductID());
                ps.addBatch();
            }
            ps.executeBatch();
            return true;
        }
        catch(Exception e){
            System.out.println(e.getMessage());
            return false;
        }
    }
}
