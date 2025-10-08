package com.microsoft.sqlserver.DTO;

public class accountDTO {
    private String accountID ;
    private String username ;
    private String password ;
    private String type ;

    public accountDTO(){}

    public accountDTO(String accountID , String username , String password , String type){
        this.accountID = accountID ;
        this.username = username ;
        this.password = password ;
        this.type = type ;
    }

    public void setID(String accountID){
        this.accountID = accountID ;
    }
    public void setUsername(String username){
        this.username = username ;
    }
    public void setPassword(String password){
        this.password = password ;
    }
    public void setType(String type){
        this.type = type ;
    }

    public String getID(){
        return accountID ;
    }
    public String getUsername(){
        return username ;
    }
    public String getPassword(){
        return password ;
    }
    public String getType(){
        return type ;
    }
}
