package com.microsoft.sqlserver.DTO;

public class importDTO extends receiptDTO
{
    private String providerID;
    
    public importDTO(String receiptId, String receiptDate, String employeeID, String providerID) {
        super(receiptId, receiptDate, employeeID);
        this.providerID = providerID;
    }

    public String getProviderID() {
        return providerID;
    }

    public void setProviderID(String providerID) {
        this.providerID = providerID;
    }
    
    @Override
    public String getReceiptId() {
        return super.getReceiptId();
    }
    @Override
    public String getEmployeeID() {
        return super.getEmployeeID();
    }
    @Override
    public String getReceiptDate() {
        return super.getReceiptDate();
    }
    @Override
    public void setReceiptId(String receiptId) {
        super.setReceiptId(receiptId);
    }
    @Override
    public void setReceiptDate(String receiptDate) {
        super.setReceiptDate(receiptDate);
    }
    @Override
    public void setEmployeeID(String employeeID) {
        super.setEmployeeID(employeeID);
    }
}