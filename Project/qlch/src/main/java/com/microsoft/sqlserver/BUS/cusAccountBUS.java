package com.microsoft.sqlserver.BUS;

import java.util.Vector;
import com.microsoft.sqlserver.DAO.*;
import com.microsoft.sqlserver.DTO.* ;

public class cusAccountBUS {
    accountDTO account = new accountDTO() ;
    static accountDTO cusCurrentAccount = new accountDTO() ;


    public cusAccountBUS(){} ;

    public cusAccountBUS(accountDTO account){
        this.account = account ;
    }

    public void setCurrentAccount(accountDTO account){
        cusAccountBUS.cusCurrentAccount = account ;
    }

    public accountDTO getCurrentAccount(){
        return cusCurrentAccount ;
    }

    public Vector<accountDTO> getAccountList(){
        cusAccountDAO accDAO = new cusAccountDAO() ;
        Vector<accountDTO> accountList = accDAO.createAccountList() ;

        return accountList ;
    }

    public void addAccount(accountDTO account){
        cusAccountDAO accDAO = new cusAccountDAO() ;
        accDAO.addAccount(account) ;
    }

    public void deleteAccount(accountDTO account){
        cusAccountDAO accDAO = new cusAccountDAO() ;
        accDAO.deleteAccount(account) ;
    }

    public void updateAccount(accountDTO account){
        cusAccountDAO accDAO = new cusAccountDAO() ;
        accDAO.updateAccount(account) ;
    }

    public String checkLogin(accountDTO account){
        cusAccountDAO accDAO = new cusAccountDAO() ;
        String result = accDAO.checkLogin(account) ;

        return result ;
    }

    public accountDTO getAccountByUsername(String username){
        cusAccountDAO accDAO = new cusAccountDAO() ;
        accountDTO acc = accDAO.getAccountByUsername(username) ;

        return acc ;
    }

    public Vector<accountDTO> searchAccount(String input){
        cusAccountDAO accDAO = new cusAccountDAO() ;
        Vector<accountDTO> accountsList = accDAO.searchAccount(input) ;

        return accountsList ;
    }

}
