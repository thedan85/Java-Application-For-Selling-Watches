package com.microsoft.sqlserver.DAO;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.io.FileReader;
import java.util.Properties;


public class connectDatabase {
    protected Connection con = null;
    private String dbUrl, username, password;

    public Connection getCon() {
        return con;
    }

    public void setCon(Connection con) {
        this.con = con;
    }

    public void readProperties() throws Exception{
        
        // create reader object
        FileReader reader = new FileReader("qlch\\src\\main\\java\\com\\microsoft\\sqlserver\\DAO\\config.properties");

        // crate properties object
        Properties properties = new Properties();
        properties.load(reader);

        // show file info
        dbUrl = properties.getProperty("dbUrl");
        username = properties.getProperty("username");
        password = properties.getProperty("password");
    }
    
    public boolean openConnectDB() throws Exception{
        readProperties();
        try {
            if (con != null && !con.isClosed()) {
                return true;
            }
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            con = DriverManager.getConnection(dbUrl, username, password);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public void closeConnectDB() throws Exception{
        try {
            if (con != null)
                con.close();
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }
}