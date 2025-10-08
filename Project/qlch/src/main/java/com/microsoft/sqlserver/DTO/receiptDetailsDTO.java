package com.microsoft.sqlserver.DTO;

public class receiptDetailsDTO
{
    private String receiptID;
    private String productID;
    private int quantity;
    private int price;

    public receiptDetailsDTO(String receiptID, String productID, int quantity, int price) {
        this.receiptID = receiptID;
        this.productID = productID;
        this.quantity = quantity;
        this.price = price;
    }
    
    public String getReceiptID() {
        return receiptID;
    }
    public void setReceiptID(String receiptID) {
        this.receiptID = receiptID;
    }
    public String getProductID() {
        return productID;
    }
    public void setProductID(String productID) {
        this.productID = productID;
    }
    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    public int getPrice() {
        return price;
    }
    public void setPrice(int price) {
        this.price = price;
    }
    

}