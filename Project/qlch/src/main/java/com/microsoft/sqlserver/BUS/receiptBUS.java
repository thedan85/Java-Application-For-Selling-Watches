package com.microsoft.sqlserver.BUS;
import com.microsoft.sqlserver.DTO.*;
import com.microsoft.sqlserver.DAO.receiptDAO;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Vector;
public class receiptBUS {
    receiptDAO receiptDAO = new receiptDAO();

    public Vector<receiptDTO> getAllReceipt() {
        return receiptDAO.getAllReceipts();
    }

    public Vector<importDTO> viewImport(){
        return receiptDAO.viewImport();
    }

    public Vector<exportDTO> viewExport(){
        return receiptDAO.viewExport();
    }

    public Vector<receiptDTO> searchReceipt(String keyword) {
        return receiptDAO.searchReceipt(keyword);
    }

    public boolean checkReceiptType(String receiptID) {
        return receiptDAO.checkReceiptType(receiptID);
    }

    public Vector<receiptDTO> getAllPendingReceipts(){
        return receiptDAO.getAllPendingReceipts();
    }

    public int acceptReceipt(String receiptID,String empID){
        return receiptDAO.acceptReceipt(receiptID, empID);
    }

    public receiptDTO getReceiptDTOById(String receiptId){
        return receiptDAO.getReceiptDTOById(receiptId);
    }

    public boolean CreateImportReceipt(Vector<productDTO> product){
        return receiptDAO.CreateImportReceipt(product);
    }
    public boolean CreateExportReceipt(HashMap<String, Vector<productDTO>> productMap){
        return receiptDAO.CreateExportReceipt(productMap);
    }
    public boolean createBuyingProduct(Vector<productDTO> products, String customerID){
        return receiptDAO.createBuyingProduct(products, customerID);
    }
    public boolean refuseReceipt(receiptDTO receipt){
        return receiptDAO.refuseReceipt(receipt);
    }
    public Vector<receiptDTO> showReceiptToCustomer(){
        return receiptDAO.showReceiptToCustomer();
    }
    public Vector<receiptDTO> showPendingReceiptToCustomer(){
        return receiptDAO.showPendingReceiptToCustomer();
    }
    public Vector<Vector<Object>> getRevenueStatisticsByMonth() {
        receiptDAO dao = new receiptDAO();
        return dao.getRevenueByMonth();
    }
    
    public Vector<Vector<Object>> getRevenueStatisticsByYear() {
        receiptDAO dao = new receiptDAO();
        return dao.getRevenueByYear();
    }
    public Vector<receiptDTO> getReceiptsByDateRange(LocalDate fromDate, LocalDate toDate) {
        return receiptDAO.getReceiptsByDateRange(fromDate, toDate);
    }
    public Vector<importDTO> getImportsByDateRange(LocalDate fromDate, LocalDate toDate){
        return receiptDAO.getImportsByDateRange(fromDate, toDate);
    }
    public Vector<exportDTO> getExportsByDateRange(LocalDate fromDate, LocalDate toDate){
        return receiptDAO.getExportsByDateRange(fromDate, toDate);
    }
}
