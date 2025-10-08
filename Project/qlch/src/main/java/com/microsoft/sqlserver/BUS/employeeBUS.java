package com.microsoft.sqlserver.BUS;
import java.util.Vector;
import com.microsoft.sqlserver.DAO.*;
import com.microsoft.sqlserver.DTO.*;

public class employeeBUS{
    employeeDTO employee = new employeeDTO() ;
    
    public employeeBUS(){} ;
    
    public employeeBUS(employeeDTO employee){
        this.employee = employee ;
    }

    public Vector<employeeDTO> getEmployeeList(){
        employeeDAO empDAO = new employeeDAO() ;
        Vector<employeeDTO> employeeList = empDAO.createEmployeeList() ;
        
        return employeeList ;
    }

    public void addEmployee(employeeDTO employee){
        employeeDAO empDAO = new employeeDAO() ;
        empDAO.addEmployee(employee) ;
    }

    public void deleteEmployee(employeeDTO employee){
        employeeDAO empDAO = new employeeDAO() ;
        empDAO.deleteEmployee(employee) ;
    }

    public void updateEmployee(employeeDTO employee){
        employeeDAO empDAO = new employeeDAO() ;
        empDAO.updateEmployee(employee) ;
    }

    public Vector<employeeDTO> searchEmployee(String input){
        employeeDAO empDAO = new employeeDAO() ;
        Vector<employeeDTO> employeeList = empDAO.searchEmployee(input) ;
        
        return employeeList ;
    }

    public String getEmployeeNameById(String id){
        employeeDAO empDAO = new employeeDAO() ;
        String name = empDAO.getEmployeeNameById(id) ;
        
        return name ;
    }
}