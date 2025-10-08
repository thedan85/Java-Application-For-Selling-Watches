package com.microsoft.sqlserver.DTO ;

public class employeeDTO{
    private String ID ;
    private String name ;
    private String role ;
    private String phoneNumber ; 
    private int salary ;

    public employeeDTO(){}

    public employeeDTO(String ID , String name , String role , String phoneNumber , int salary){
        this.ID = ID ;
        this.name = name ;
        this.role = role ;
        this.phoneNumber = phoneNumber ;
        this.salary = salary ;
    }

    public void setID(String ID){
        this.ID = ID ;
    }

    public void setName(String Name){
        this.name = Name ;
    }

    public void setRole(String role){
        this.role = role ;
    }

    public void setPhoneNumber(String phoneNumber){
        this.phoneNumber = phoneNumber ;
    }

    public void setSalary(int salary){
        this.salary = salary ;
    }
    
    public String getID(){
        return ID ;
    }
    
    public String getName(){
        return name ;
    }
    
    public String getRole(){
        return role ;
    }
    
    public String getPhoneNumber(){
        return phoneNumber ;
    }
    
    public int getSalary(){
        return salary ;
    }
}

