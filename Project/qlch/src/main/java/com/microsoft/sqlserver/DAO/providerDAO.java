
package com.microsoft.sqlserver.DAO;
import com.microsoft.sqlserver.BUS.*;
import com.microsoft.sqlserver.DTO.providerDTO;
import java.sql.*;
import java.util.Vector;
public class providerDAO {
    public Connection conn;
    providerDTO provider = new providerDTO();
    public void openConnection(){
        try{
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            String dbUrl = "jdbc:sqlserver://localhost:1433;databaseName=QLCH;encrypt=true;trustServerCertificate=true";
            String username = "sa";
            String password = "";
            conn = DriverManager.getConnection(dbUrl, username, password);
        }
        catch (ClassNotFoundException e){
            System.out.println("Khong tim thay driver");
        }
        catch (SQLException e){
            System.out.println("Khong the ket noi den CSDL");
        }
    }
    public void closeConnection(){
        try{
            if (conn != null){
                conn.close();
            }
        }
        catch (SQLException e){
            System.out.println("Khong the dong ket noi CSDL");
        }
    }
    public Vector<providerDTO> getAllProviders(){
        Vector<providerDTO> arr = new Vector<providerDTO>();
        String sql = "SELECT * FROM providers";
        try{
            openConnection();
            Statement s = conn.createStatement();
            ResultSet rs = s.executeQuery(sql);
            while(rs.next()){
                String providerID = rs.getString("ProviderID");
                String providerName = rs.getString("ProviderName");
                String providerPhone = rs.getString("ProviderPhone");
                arr.add(new providerDTO(providerID, providerName, providerPhone));
            }
        }
        catch (SQLException e){
            System.out.println("Khong the lay du lieu tu CSDL");
        }
        finally{
            closeConnection();
        }
        return arr;
    }
    public boolean hasProviderID(String providerID){
        String sql = "SELECT * FROM Providers WHERE ProviderID = ?";
        try{
            openConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1,providerID);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                return true;
            }
        }
        catch (SQLException e){
            System.out.println("Khong the kiem tra ProviderID trong CSDL");
        }
        finally{
            closeConnection();
        }
        return false;
    }
    public boolean addProvider(providerDTO provider){
        String sql = "INSERT INTO Providers (ProviderName, ProviderPhone) VALUES (?, ?)";
        boolean result = false;
        try{
            openConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1,provider.getProviderName());
            ps.setString(2,provider.getProviderPhone());
            if(ps.executeUpdate() > 0 ){
                result = true;
            }
        }
        catch(SQLException e)
        {
            System.out.println("Khong the them Provider vao CSDL");
        }
        finally{
            closeConnection();
        }
        return result;
    }
    
    public boolean updateProvider(providerDTO provider)
    {
        String sql = "update Providers set ProviderName = ?, ProviderPhone = ? where ProviderID = ?";
        try{
            openConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1,provider.getProviderName());
            ps.setString(2,provider.getProviderPhone());
            ps.setString(3,provider.getProviderID());
            if(ps.executeUpdate() > 0 ){
                return true;
            }
        }
        catch(SQLException e){
            System.out.println("Khong the cap nhat Provider vao CSDL");
        }
        finally{
            closeConnection();
        }
        return false;
    }
    
    public boolean deleteProvider(String providerID){
        String sql = "DELETE FROM Providers WHERE ProviderID = ?";
        try{
            openConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1,providerID);
            if(ps.executeUpdate() > 0 ){
                return true;
            }
        }
        catch(SQLException e){
            System.out.println("Khong the xoa Provider trong CSDL");
        }
        finally{
            closeConnection();
        }
        return false;
    }

    public providerDTO getProviderByID(String providerID){
        String sql = "SELECT * FROM Providers WHERE ProviderID = ?";
        try{
            openConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1,providerID);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                provider.setProviderID(rs.getString("ProviderID"));
                provider.setProviderName(rs.getString("ProviderName"));
                provider.setProviderPhone(rs.getString("ProviderPhone"));
            }
        }
        catch(SQLException e){
            System.out.println("Khong the lay Provider tu CSDL");
        }
        finally{
            closeConnection();
        }
        return provider;
    }
    public Vector<providerDTO> searchProvider(String text)
    {
        Vector<providerDTO> arr = new Vector<providerDTO>();
        String sql = "SELECT * FROM Providers WHERE ProviderID LIKE ? OR ProviderName LIKE ?";
        try{
            openConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1,"%"+text+"%");
            ps.setString(2,"%"+text+"%");
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                String providerID = rs.getString("ProviderID");
                String providerName = rs.getString("ProviderName");
                String providerPhone = rs.getString("ProviderPhone");
                arr.add(new providerDTO(providerID, providerName, providerPhone));
            }
        }
        catch(SQLException e){
            System.out.println("Khong the tim kiem Provider trong CSDL");
        }
        finally{
            closeConnection();
        }
        return arr;
    }
    public String getProviderNameById(String providerID)
    {
        String sql = "SELECT ProviderName FROM Providers WHERE ProviderID = ?";
        String name="";
        try{
            openConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1,providerID);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                name = rs.getString("ProviderName");
            }
        }
        catch(SQLException e){
            System.out.println(e.getMessage());
        }
        finally{
            closeConnection();
        }
        return name;
    }

    public String getProviderIdByName(String providerName){
        String sql ="SELECT ProviderID FROM Providers WHERE ProviderName = ?";
        String proID ="";
        try{
            openConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1,providerName);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                proID = rs.getString("ProviderID");
            }
        }
        catch(SQLException e){
            System.out.println(e.getMessage());
        }
        finally{
            closeConnection();
        }
        return proID;
    }
}