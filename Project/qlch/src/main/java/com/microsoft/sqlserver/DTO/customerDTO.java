package com.microsoft.sqlserver.DTO ;

public class customerDTO{
    private String ID ;
    private String name ;
    private String number;

    public customerDTO(){}

    public customerDTO(String ID , String name, String number){
        this.name = name ;
        this.number=number;
        this.ID=ID;
    }

    public void setID(String ID){
        this.ID = ID ;
    }

    public void setName(String Name){
        this.name = Name ;
    }

    public void setPhoneNumber(String number){
        this.number=number ;
    }

    
    public String getID(){
        return ID ;
    }
    
    public String getName(){
        return name ;
    }
    
    public String getPhoneNumber(){
        return number ;
    }
}

