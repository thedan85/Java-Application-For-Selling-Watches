package com.microsoft.sqlserver.BUS;
import com.microsoft.sqlserver.DTO.receiptDetailsDTO;
import com.microsoft.sqlserver.DAO.receiptDetailsDAO;
import java.util.Vector;
public class receiptDetailsBUS {
    receiptDetailsDAO receiptDetailsDAO = new receiptDetailsDAO();
    public Vector<receiptDetailsDTO> getAllReceiptDetails() {
        return receiptDetailsDAO.getAllReceiptDetails();
    }
    public Vector<receiptDetailsDTO> getAllReceiptDetails(String receiptID) {
        return receiptDetailsDAO.getAllReceiptDetails(receiptID);
    }

    public boolean addReceiptDetails(receiptDetailsDTO receiptDetails) {
        return receiptDetailsDAO.addReceiptDetails(receiptDetails);
    }

    public boolean addReceiptDetails(Vector<receiptDetailsDTO> receiptDetails) {
        return receiptDetailsDAO.addReceiptDetails(receiptDetails);
    }

    public int getTotalPrice(String receiptID) {
        return receiptDetailsDAO.getTotalPrice(receiptID);
    }
}
