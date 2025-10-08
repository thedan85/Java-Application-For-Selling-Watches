package com.microsoft.sqlserver.DAO;
import java.sql.Connection ;
import java.sql.DriverManager ;
import java.sql.PreparedStatement;
import java.sql.ResultSet ;
import java.sql.SQLException;
import java.sql.Statement ;
import java.util.Vector;
import com.microsoft.sqlserver.DTO.employeeDTO;

public class employeeDAO {
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
    
    public Vector<employeeDTO> createEmployeeList(){
        try{
            Vector<employeeDTO> employeeList = new Vector<employeeDTO>() ;

            openConnection() ;
            ResultSet rs = statement.executeQuery("SELECT * FROM Employees") ;
            
            while(rs.next()){
                employeeDTO emp = new employeeDTO() ;
                emp.setID(rs.getString("EmployeeID")) ;
                emp.setName(rs.getString("EmployeeName")) ;
                emp.setRole(rs.getString("EmployeeRole")) ;
                emp.setSalary(rs.getInt("EmployeeSalary")) ;
                emp.setPhoneNumber(rs.getString("EmployeeNumber")) ;

                employeeList.add(emp) ;
            }
            return employeeList ;
        } 
        catch (Exception e) {
            System.out.println(e.getMessage()) ;
            return null ;
        }
        finally{
            closeConnection() ;
        }
    }

    public void addEmployee(employeeDTO employee){
        try{
            openConnection() ;
            
            String insertQuery = "INSERT INTO Employees(EmployeeName, EmployeeRole, EmployeeSalary, EmployeeNumber) VALUES(? , ? , ? , ?)" ;
            PreparedStatement preparedStatement = con.prepareStatement(insertQuery) ;
            preparedStatement.setString(1, employee.getName()) ;
            preparedStatement.setString(2, employee.getRole()) ;
            preparedStatement.setInt(3, employee.getSalary()) ;
            preparedStatement.setString(4, employee.getPhoneNumber()) ;
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

    public void deleteEmployee(employeeDTO employee){
        try{
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver") ;
            con = DriverManager.getConnection(dbURL, user, password) ;
            statement = con.createStatement() ;
            
            String sql = "DELETE FROM Employees WHERE EmployeeID = '" + employee.getID() + "'" ;
            statement.executeUpdate(sql) ;
            con.close();
        }
        catch(Exception e){
            System.out.println(e.getMessage()) ;
        }
    }

    public void updateEmployee(employeeDTO employee){
        try{
            openConnection() ;
            
            String updateQuery = "UPDATE Employees SET EmployeeName = ? , EmployeeRole = ? , EmployeeSalary = ? , EmployeeNumber = ? WHERE EmployeeID = ?" ;
            PreparedStatement preparedStatement = con.prepareStatement(updateQuery) ;
            preparedStatement.setString(1 , employee.getName()) ;
            preparedStatement.setString(2 , employee.getRole()) ;
            preparedStatement.setInt(3 , employee.getSalary()) ;
            preparedStatement.setString(4 , employee.getPhoneNumber()) ;
            preparedStatement.setString(5 , employee.getID()) ;
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

    public Vector<employeeDTO> searchEmployee(String input){
        try{
            openConnection() ;
            String query = "SELECT * FROM Employees WHERE EmployeeID LIKE ? OR EmployeeName LIKE ? OR EmployeeRole LIKE ? OR EmployeeNumber LIKE ?" ;
            PreparedStatement preparedStatement = con.prepareStatement(query) ;
            preparedStatement.setString(1, "%" + input + "%") ;
            preparedStatement.setString(2, "%" + input + "%") ;
            preparedStatement.setString(3, "%" + input + "%") ;
            preparedStatement.setString(4, "%" + input + "%") ;
            
            ResultSet rs = preparedStatement.executeQuery() ;
            Vector<employeeDTO> employeeList = new Vector<employeeDTO>() ;
            while(rs.next()){
                employeeDTO emp = new employeeDTO() ;
                emp.setID(rs.getString("EmployeeID")) ;
                emp.setName(rs.getString("EmployeeName")) ;
                emp.setRole(rs.getString("EmployeeRole")) ;
                emp.setSalary(rs.getInt("EmployeeSalary")) ;
                emp.setPhoneNumber(rs.getString("EmployeeNumber")) ;

                employeeList.add(emp) ;
            }

            return employeeList ;
        }
        catch(Exception e){
            System.out.println(e.getMessage()) ;
            return null ;
        }
    }
    public String getEmployeeNameById(String employeeID)
    {
        String name = "";
        try{
            openConnection() ;
            String query = "SELECT EmployeeName FROM Employees WHERE EmployeeID = ?" ;
            PreparedStatement preparedStatement = con.prepareStatement(query) ;
            preparedStatement.setString(1, employeeID) ;
            
            ResultSet rs = preparedStatement.executeQuery() ;
            if(rs.next()){
                name = rs.getString("EmployeeName") ;
                return name ;
            }
        }
        catch(Exception e){
            System.out.println(e.getMessage()) ;
        }
        finally{
            closeConnection() ;
        }
        return name;
    }

    public String getEmployeeIdByName(String name){
        String sql = "SELECT EmployeeID FROM Employees WHERE EmployeeName = ?";
        String empID = "";
        try{
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1,name);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                empID = rs.getString("EmployeeID");
            }
        }
        catch(SQLException e){
            System.out.println("LOI");
        }
        finally{
            closeConnection();
        }
        return empID;
    }
}
