package com.microsoft.sqlserver.BUS;
import com.microsoft.sqlserver.DTO.*;
import com.microsoft.sqlserver.DAO.* ;
import java.util.Vector ;

public class accountBUS{
    accountDTO account = new accountDTO() ;
    static accountDTO currentAccount = new accountDTO() ;

    public accountBUS(){} ;

    public accountBUS(accountDTO account){
        this.account = account ;
    }

    public void setCurrentAccount(accountDTO account){
        accountBUS.currentAccount = account ;
    }

    public accountDTO getCurrentAccount(){
        return currentAccount ;
    }

    public Vector<accountDTO> getAccountList(){
        accountDAO accDAO = new accountDAO() ;
        Vector<accountDTO> accountList = accDAO.createAccountList() ;

        return accountList ;
    }

    public void addAccount(accountDTO account){
        accountDAO accDAO = new accountDAO() ;
        accDAO.addAccount(account) ;
    }

    public void deleteAccount(accountDTO account){
        accountDAO accDAO = new accountDAO() ;
        accDAO.deleteAccount(account) ;
    }

    public void updateAccount(accountDTO account){
        accountDAO accDAO = new accountDAO() ;
        accDAO.updateAccount(account) ;
    }

    public String checkLogin(accountDTO account){
        accountDAO accDAO = new accountDAO() ;
        String result = accDAO.checkLogin(account) ;

        return result ;
    }

    public accountDTO getAccountByUsername(String username){
        accountDAO accDAO = new accountDAO() ;
        accountDTO acc = accDAO.getAccountByUsername(username) ;

        return acc ;
    }

    public Vector<accountDTO> searchAccount(String input){
        accountDAO accDAO = new accountDAO() ;
        Vector<accountDTO> accountsList = accDAO.searchAccount(input) ;

        return accountsList ;
    }

}
