package com.microsoft.sqlserver.DTO;

abstract public class receiptDTO {
    private String receiptId ;
    private String receiptDate ;
    private String employeeID ;

    public receiptDTO(){}

    public receiptDTO(String receiptId, String receiptDate, String employeeID){
        this.receiptId = receiptId;
        this.receiptDate = receiptDate;
        this.employeeID = employeeID;
    }

    public String getReceiptId() {
        return receiptId;
    }

    public String getEmployeeID() {
        return employeeID;
    }

    public String getReceiptDate() {
        return receiptDate;
    }

    public void setReceiptId(String receiptId) {
        this.receiptId = receiptId;
    }

    public void setReceiptDate(String receiptDate) {
        this.receiptDate = receiptDate;
    }

    public void setEmployeeID(String employeeID) {
        this.employeeID = employeeID;
    }
}
