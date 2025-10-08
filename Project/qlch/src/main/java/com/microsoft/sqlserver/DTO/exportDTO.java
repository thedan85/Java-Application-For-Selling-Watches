package com.microsoft.sqlserver.DTO;

public class exportDTO extends receiptDTO{
    private String customerID;

    public exportDTO() {
    }

    public exportDTO(String receiptId, String receiptDate, String employeeID, String customerID) {
        super(receiptId, receiptDate, employeeID);
        this.customerID = customerID;
    }

    public String getCustomerID() {
        return customerID;
    }

    public void setCustomerID(String customerID) {
        this.customerID = customerID;
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
