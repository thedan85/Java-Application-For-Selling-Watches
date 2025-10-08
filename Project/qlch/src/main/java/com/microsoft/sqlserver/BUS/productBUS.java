package com.microsoft.sqlserver.BUS;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import com.microsoft.sqlserver.DAO.productDAO;
import com.microsoft.sqlserver.DTO.productDTO;
import com.microsoft.sqlserver.DTO.receiptDTO;
import com.microsoft.sqlserver.DTO.receiptDetailsDTO;


public class productBUS {
    productDTO product = new productDTO() ;
    productDAO proDAO = new productDAO();
    receiptDetailsBUS receiptDetailsBUS = new receiptDetailsBUS();

    public productBUS(){} ;
    
    public productBUS(productDTO product){
        this.product = product ;
    }

    public Vector<productDTO> getProductList(){
        proDAO.createProductList() ;
        Vector<productDTO> productList = proDAO.getProductList() ;
        
        return productList ;
    }

    public void addProduct(productDTO product){
        System.out.println("Adding...") ;
        proDAO.addProduct(product);
    }

    public void deleteProduct(productDTO product){
        proDAO.deleteProduct(product);
    }

    public void updateProduct(productDTO product){
        proDAO.updateProduct(product) ;
    }
    public Vector<productDTO> searchProduct(String text){
        return proDAO.searchProduct(text);
    }

    public String getProductNameById(String productID){
        return proDAO.getProductNameById(productID) ;
    }

    public class getProductNameById {
    }

    public void addProduct(Vector<productDTO> products) {
        proDAO.addProduct(products);
    }
    public void exportProduct(HashMap<String,Vector<productDTO>> productMap){
        proDAO.exportProduct(productMap);
    }
    public void buyProduct(Vector<productDTO> products,String customerID){
        proDAO.buyProduct(products, customerID);
    }

    public String getProductIdByName(String name)
    {
        return proDAO.getProductIdByName(name);
    }
    public int getProductQuantityById(String id){
        return proDAO.getProductQuantityById(id);
    }
    public boolean updateProductAfterRefuse(Vector<receiptDetailsDTO> r){
        return proDAO.updateProductAfterRefuse(r);
    }

    public Vector<String> getProviderIDs() {
        return proDAO.getProviderIDs();
    }

    public Vector<String> getProductTypes() {
        return proDAO.getProductTypes();
    }

    public Vector<productDTO> searchProductAdvanced(String keyword, String productType, String providerID, String priceOption, double minPrice, double maxPrice) {
        return proDAO.searchProductAdvanced(keyword, productType, providerID,priceOption,minPrice,maxPrice);
    }

    public void updateProductQuantity() {
        try {
            proDAO.createProductList();
            Vector<productDTO> productList = proDAO.getProductList();
    
            // Load all products into map for easy access
            Map<String, productDTO> productMap = new HashMap<>();
            for (productDTO product : productList) {
                productMap.put(product.getProductID(), product);
            }
    
            // Map to hold total receipt quantities per product
            Map<String, Integer> receiptQuantityMap = new HashMap<>();
    
            receiptBUS receiptBUS = new receiptBUS();
            Vector<receiptDTO> receiptList = receiptBUS.getAllReceipt();
    
            // Accumulate receipt quantities
            for (receiptDTO receipt : receiptList) {
                Vector<receiptDetailsDTO> receiptDetailsList = receiptDetailsBUS.getAllReceiptDetails(receipt.getReceiptId());
    
                for (receiptDetailsDTO rd : receiptDetailsList) {
                    String productId = rd.getProductID();
                    int quantity = rd.getQuantity();
    
                    int currentTotal = receiptQuantityMap.getOrDefault(productId, 0);
                    boolean type = receiptBUS.checkReceiptType(receipt.getReceiptId());
                    if (type)
                        receiptQuantityMap.put(productId, currentTotal + quantity);
                    else
                        receiptQuantityMap.put(productId, currentTotal - quantity);

                }
            }
    
            // Recalculate final quantity: initial + receipt sum
            for (Map.Entry<String, productDTO> entry : productMap.entrySet()) {
                String productId = entry.getKey();
                productDTO product = entry.getValue();
    
                int initialQuantity = product.getQuantity(); // You must have this getter!
                int addedQuantity = receiptQuantityMap.getOrDefault(productId, 0);
                
                product.setQuantity(initialQuantity + addedQuantity);
            }
    
            // Batch update
            proDAO.updateProducts(new Vector<>(productMap.values()));
    
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    
    

}

