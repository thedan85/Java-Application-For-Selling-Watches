package com.microsoft.sqlserver.DAO;
import java.sql.* ;
import java.util.Vector ;
import com.microsoft.sqlserver.DTO.* ;

public class accountDAO {
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

    public Vector<accountDTO> createAccountList(){
        try{
            Vector<accountDTO> accountList = new Vector<accountDTO>() ;

            openConnection() ;
            ResultSet rs = statement.executeQuery("SELECT * FROM Accounts") ;
            
            while(rs.next()){
                accountDTO acc = new accountDTO() ;
                acc.setID(rs.getString("AccountID")) ;
                acc.setUsername(rs.getString("Username")) ;
                acc.setPassword(rs.getString("Password")) ;
                acc.setType(rs.getString("AccountType")) ;

                accountList.add(acc) ;
            }
            return accountList ;
        } 
        catch(Exception e){
            System.out.println(e.getMessage()) ;
            return null ;
        }
        finally{
            closeConnection() ;
        }
    }

    public void addAccount(accountDTO account){
        try{
            openConnection() ;
            String sql = "insert into Accounts(Username , Password , AccountType) values (? , ?, ?)" ;
            PreparedStatement preparedStatement = con.prepareStatement(sql) ;
            preparedStatement.setString(1, account.getUsername()) ;
            preparedStatement.setString(2, account.getPassword()) ;
            preparedStatement.setString(3, account.getType()) ;
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

    public void deleteAccount(accountDTO account){
        try{
            openConnection() ;
            String sql = "delete from Accounts where Username = ?" ;
            PreparedStatement preparedStatement = con.prepareStatement(sql) ;
            preparedStatement.setString(1, account.getUsername()) ;
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

    public void updateAccount(accountDTO account){
        try{
            openConnection() ;
            String sql = "update Accounts set Password = ? , AccountType = ? where Username = ?" ;
            PreparedStatement preparedStatement = con.prepareStatement(sql) ;
            preparedStatement.setString(1, account.getPassword()) ;
            preparedStatement.setString(2, account.getType()) ;
            preparedStatement.setString(3, account.getUsername()) ;
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

    public String checkLogin(accountDTO account){
        try{
            openConnection() ;
            String selectUsername = "select * from Accounts where Username = ?" ;
            String select = "select * from Accounts where Username = ? and Password = ?" ;

            PreparedStatement preparedStatement = con.prepareStatement(selectUsername) ;
            preparedStatement.setString(1, account.getUsername()) ;
            ResultSet rs = preparedStatement.executeQuery() ;

            if(rs.next()){
                preparedStatement = con.prepareStatement(select) ;
                preparedStatement.setString(1, account.getUsername()) ;
                preparedStatement.setString(2, account.getPassword()) ;
                rs = preparedStatement.executeQuery() ;

                if(rs.next()){
                    return "login successful" ;
                }
                else{
                    return "Wrong password" ;
                }

            }
            else{
                return "Wrong username" ;
            }
        
        }
        catch(Exception e){
            System.out.println(e.getMessage()) ;
            return null ;
        }
        finally{
            closeConnection() ;
        }
    }

    public accountDTO getAccountByUsername(String username){
        try{
            openConnection() ;
            String sql = "select * from Accounts where Username = ?" ;
            PreparedStatement preparedStatement = con.prepareStatement(sql) ;
            preparedStatement.setString(1, username) ;
            ResultSet rs = preparedStatement.executeQuery() ;

            if(rs.next()){
                accountDTO acc = new accountDTO() ;
                acc.setID(rs.getString("AccountID")) ;
                acc.setUsername(rs.getString("Username")) ;
                acc.setPassword(rs.getString("Password")) ;
                acc.setType(rs.getString("AccountType")) ;

                return acc ;
            }
            else{
                return null ;
            }
        } 
        catch(Exception e){
            System.out.println(e.getMessage()) ;
            return null ;
        }
        finally{
            closeConnection() ;
        }
    }

    public Vector<accountDTO> searchAccount(String input){
        try{
            openConnection() ;
            String query = "Select * from Accounts where AccountID like ? or Username like ? " ;
            PreparedStatement ps = con.prepareStatement(query) ;
            ps.setString(1 , "%" + input + "%") ;
            ps.setString(2 , "%" + input + "%") ;

            ResultSet rs = ps.executeQuery() ;
            Vector<accountDTO> accountsList = new Vector<accountDTO>() ;
            while(rs.next()){
                accountDTO account = new accountDTO() ;
                account.setID(rs.getString("AccountID")) ;
                account.setUsername(rs.getString("Username")) ;
                account.setType(rs.getString("AccountType")) ;
                account.setPassword(rs.getString("Password")) ;

                accountsList.add(account) ;
            }

            return accountsList ;
        }
        catch(Exception e){
            System.out.println(e.getMessage()) ;
            return null ;
        }
        finally{
            closeConnection() ;
        }
    }
}
