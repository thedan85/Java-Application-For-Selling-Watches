
package com.microsoft.sqlserver.BUS;

import com.microsoft.sqlserver.DTO.*;
import com.microsoft.sqlserver.DAO.*;
import java.util.Vector;
public class providerBUS {
    providerDAO proDAO = new providerDAO();
    providerDTO proDTO = new providerDTO();
    public Vector<providerDTO> getAllProviders(){
        return proDAO.getAllProviders();
    }
    public String addProvider(providerDTO provider){
        if(proDAO.addProvider(provider)){
            return "Successfully added provider";
        }
        else{
            return "Failed to add provider";
        }
    }

    public String updateProvider(providerDTO provider){
        if(proDAO.hasProviderID(provider.getProviderID())){
            if(proDAO.updateProvider(provider)){
                return "Successfully updated provider" ;
            }
            else{
                return "Failed to update provider" ;
            }
        }
        else{
            return "Provider ID does not exist";
        }
    }

    public String deleteProvider(String providerID){
        if(proDAO.hasProviderID(providerID)){
            if(proDAO.deleteProvider(providerID)){
                return "Successfully deleted provider";
            }
            else{
                return "Failed to delete provider";
            }
        }
        else{
            return "Provider ID does not exist";
        }
    }

    public providerDTO getProviderByID(String providerID){
        return proDAO.getProviderByID(providerID);
    }    

    public Vector<providerDTO> searchProvider(String text){
        return proDAO.searchProvider(text);
    }

    public String getProviderNameById(String providerID)
    {
        return proDAO.getProviderNameById(providerID);
    }
    public String getProviderIdByName(String providerName){
        return proDAO.getProviderIdByName(providerName);
    }
}