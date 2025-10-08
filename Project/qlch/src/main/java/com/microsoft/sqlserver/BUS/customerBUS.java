package com.microsoft.sqlserver.BUS;
import java.util.Vector;
import com.microsoft.sqlserver.DAO.customerDAO;
import com.microsoft.sqlserver.DTO.customerDTO;
    
public class customerBUS{
        customerDTO customer = new customerDTO() ;
        
        public customerBUS(){} ;
        
        public customerBUS(customerDTO customer){
            this.customer = customer ;
        }
    
        public Vector<customerDTO> getCustomerList(){
            customerDAO cusDAO = new customerDAO() ;
            Vector<customerDTO> customerList = cusDAO.createCustomerList() ;
            
            return customerList ;
        }
    
        public void addcustomer(customerDTO customer){
            customerDAO cusDAO = new customerDAO() ;
            cusDAO.addcustomer(customer) ;
        }
    
        public void deletecustomer(customerDTO customer){
            customerDAO cusDAO = new customerDAO() ;
            cusDAO.deletecustomer(customer) ;
        }
    
        public void updatecustomer(customerDTO customer){
            customerDAO cusDAO = new customerDAO() ;
            cusDAO.updatecustomer(customer) ;
        }

        public String getCustomerIdByName(String name){
            customerDAO cusDAO = new customerDAO() ;
            String id = cusDAO.getCustomerIdByName(name) ;
            return id ;
        }

        public String getCustomerNameById(String id){
            customerDAO cusDAO = new customerDAO() ;
            String name = cusDAO.getCustomerNameById(id) ;
            return name ;
        }

        public static void main(String[] args) {
            customerBUS cusBUS = new customerBUS() ;
            Vector<customerDTO> customerList = cusBUS.getCustomerList() ;
            
            for(int i = 0 ; i < customerList.size() ; i++){
                System.out.println(customerList.get(i).getID() + " " + customerList.get(i).getName() + " " + customerList.get(i).getPhoneNumber()) ;
            }
        }
    }

