package com.microsoft.sqlserver.DTO;

public class productDTO {
    private String ProductID ;
    private String ProductName ;
    private int ProductPrice ;
    private int Quantity;
    private String ProviderID ;
    private String ProductType ;

    public productDTO() {
    }

    public productDTO(String productName, int productPrice, int quantity, String providerID, String productType) {
        ProductName = productName;
        ProductPrice = productPrice;
        Quantity = quantity;
        ProviderID = providerID;
        ProductType = productType;
    }

    public productDTO(String productID, String productName, int productPrice, int quantity, String providerID,
            String productType) {
        ProductID = productID;
        ProductName = productName;
        ProductPrice = productPrice;
        Quantity = quantity;
        ProviderID = providerID;
        ProductType = productType;
    }
    public String getProductID() {
        return ProductID;
    }
    public void setProductID(String productID) {
        ProductID = productID;
    }
    public String getProductName() {
        return ProductName;
    }
    public void setProductName(String productName) {
        ProductName = productName;
    }
    public int getProductPrice() {
        return ProductPrice;
    }
    public void setProductPrice(int productPrice) {
        ProductPrice = productPrice;
    }
    public int getQuantity() {
        return Quantity;
    }
    public void setQuantity(int quantity) {
        Quantity = quantity;
    }
    public String getProviderID() {
        return ProviderID;
    }
    public void setProviderID(String providerID) {
        ProviderID = providerID;
    }
    public String getProductType() {
        return ProductType;
    }
    public void setProductType(String productType) {
        ProductType = productType;
    }
}
