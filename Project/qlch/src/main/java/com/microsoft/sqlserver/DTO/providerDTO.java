package com.microsoft.sqlserver.DTO;
public class providerDTO {
    private String providerID;
    private String providerName;
    private String providerPhone;
    
    public providerDTO(){}
    
    public providerDTO(String providerID, String providerName, String providerPhone) {
        this.providerID = providerID;
        this.providerName = providerName;
        this.providerPhone = providerPhone;
    }
    
    public String getProviderID() {
        return providerID;
    }
    
    public void setProviderID(String providerID) {
        this.providerID = providerID;
    }
    
    public String getProviderName() {
        return providerName;
    }
    
    public void setProviderName(String providerName) {
        this.providerName = providerName;
    }
    
    public String getProviderPhone() {
        return providerPhone;
    }
    
    public void setProviderPhone(String providerPhone) {
        this.providerPhone = providerPhone;
    }
}