package com.microsoft.sqlserver.DAO;
import java.sql.Connection ;
import java.sql.DriverManager ;
import java.sql.PreparedStatement;
import java.sql.ResultSet ;
import java.sql.Statement ;
import java.util.Vector;
import com.microsoft.sqlserver.DTO.customerDTO;

public class customerDAO {
    String dbURL = "jdbc:sqlserver://localhost:1433;databaseName=QLCH;encrypt=true;trustServerCertificate=true" ;
    String user = "sa";
    String password = "" ;
    Connection con ;
    Statement statement ;
    
    private void openConnection(){
        try{
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver") ;
            con = DriverManager.getConnection(dbURL, user, password) ;
            statement = con.createStatement() ;
        }
        catch(Exception e){
            System.out.println(e.getMessage()) ;
        }
    }

    private void closeConnection(){
        try{
            if(statement != null){
                statement.close() ;
            }
            if(con != null){
                con.close() ;
            }
        }
        catch(Exception e){
            System.out.println(e.getMessage()) ;
        }
    }
    
    public Vector<customerDTO> createCustomerList(){
        try{
            Vector<customerDTO> customerList = new Vector<customerDTO>() ;

            openConnection() ;
            ResultSet rs = statement.executeQuery("SELECT * FROM Customers") ;
            
            while(rs.next()){
                customerDTO cus = new customerDTO() ;
                cus.setID(rs.getString("CustomersID")) ;
                cus.setName(rs.getString("CustomersName")) ;
                cus.setPhoneNumber(rs.getString("CustomersNumber")) ;

                customerList.add(cus) ;
            }
            return customerList ;
        } 
        catch (Exception e) {
            System.out.println(e.getMessage()) ;
            return null ;
        }
        finally{
            closeConnection() ;
        }
    }

    public void addcustomer(customerDTO customer){
        try{
            openConnection() ;
            
            String insertQuery = "INSERT INTO Customers(CustomersName ,  CustomersNumber) VALUES(? ,  ?)" ;
            PreparedStatement preparedStatement = con.prepareStatement(insertQuery) ;
            preparedStatement.setString(1, customer.getName()) ;
            preparedStatement.setString(2, customer.getPhoneNumber()) ;
            preparedStatement.executeUpdate() ;
            preparedStatement.close() ;
        }
        catch(Exception e){
            System.out.println(e.getMessage()) ;
        }
        finally{

            closeConnection() ;
        }
    }

    public void deletecustomer(customerDTO customer){
        try{
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver") ;
            con = DriverManager.getConnection(dbURL, user, password) ;
            statement = con.createStatement() ;
            
            String sql = "DELETE FROM Customers WHERE CustomersID = '" + customer.getID() + "'" ;
            statement.executeUpdate(sql) ;
            con.close();
        }
        catch(Exception e){
            System.out.println(e.getMessage()) ;
        }
    }

    public void updatecustomer(customerDTO customer){
        try{
            openConnection() ;
            
            String updateQuery = "UPDATE Customers SET CustomersName = ? , CustomersNumber = ? WHERE CustomersID = ?" ;
            PreparedStatement preparedStatement = con.prepareStatement(updateQuery) ;
            preparedStatement.setString(1 , customer.getName()) ;
            preparedStatement.setString(2 , customer.getPhoneNumber()) ;
            preparedStatement.setString(3 , customer.getID()) ;
            preparedStatement.executeUpdate() ;
            preparedStatement.close() ;
        }
        catch(Exception e){
            System.out.println(e.getMessage()) ;
        }
        finally{
            closeConnection() ;
        }
    }

    public String getCustomerIdByName(String name){
        try{
            openConnection() ;
            String sql = "SELECT CustomersID FROM Customers WHERE CustomersName = '" + name + "'" ;
            ResultSet rs = statement.executeQuery(sql) ;
            if(rs.next()){
                return rs.getString("CustomersID") ;
            }
        }
        catch(Exception e){
            System.out.println(e.getMessage()) ;
        }
        finally{
            closeConnection() ;
        }
        return null ;
    }

    public String getCustomerNameById(String id){
        try{
            openConnection() ;
            String sql = "SELECT CustomersName FROM Customers WHERE CustomersID = '" + id + "'" ;
            Statement statement = con.createStatement() ;
            ResultSet rs = statement.executeQuery(sql) ;
            if(rs.next()){
                return rs.getString("CustomersName") ;
            }
        }
        catch(Exception e){
            System.out.println(e.getMessage()) ;
        }
        finally{
            closeConnection() ;
        }
        return null ;
    }
}

