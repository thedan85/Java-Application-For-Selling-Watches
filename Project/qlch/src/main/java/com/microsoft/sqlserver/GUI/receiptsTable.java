package com.microsoft.sqlserver.GUI;
import com.microsoft.sqlserver.BUS.*;
import com.microsoft.sqlserver.DTO.* ;
import java.util.Comparator;
import java.util.Vector;
import javax.swing.* ;
import javax.swing.table.* ;
import java.awt.* ;
import java.awt.event.* ;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;


public class receiptsTable {
    private mainInt mainInt = new mainInt() ;
    private JPanel mainPanel = mainInt.getMainPanel() ;
    private int hoveredRow = -1 ;
    private JTable ReceiptsTable = new JTable() ;
    private boolean isImportMode = false;
    private boolean isExportMode = false;
    private boolean isAllMode = false;

    private String type ;
    public void typeCheck(){
        accountBUS accountBUS = new accountBUS() ;
        cusAccountBUS cusAccountBUS = new cusAccountBUS() ;

        if(accountBUS.getCurrentAccount() != null){
            type = accountBUS.getCurrentAccount().getType().trim() ;
        }
        else{
            type = cusAccountBUS.getCurrentAccount().getType().trim() ;
        }
    }
    
    public void receiptsTableInitiate(){    
        typeCheck();
        isAllMode = true;
        JScrollPane ReceiptsScrollPane = new JScrollPane(ReceiptsTable) ;
        ReceiptsTable.setDefaultRenderer(Object.class , new HoverRenderer()) ;

        ReceiptsTable.addMouseMotionListener(new MouseAdapter(){
            @Override
            public void mouseMoved(MouseEvent e){
                Point p = e.getPoint() ;
                int row = ReceiptsTable.rowAtPoint(p) ;

                if(row != hoveredRow){
                    hoveredRow = row ;
                    ReceiptsTable.repaint() ;
                }
            }
        });


        JTableHeader tableHeader =  ReceiptsTable.getTableHeader() ;
        tableHeader.setFont(new Font("Segoe UI" , Font.BOLD , 15)) ;
        tableHeader.setBackground(new Color(72, 166, 167)) ;
        tableHeader.setForeground(Color.WHITE) ;
        
        DefaultTableModel model = new DefaultTableModel() ;
        model.addColumn("ID");
        model.addColumn("Employee Name");
        model.addColumn("Date");
        model.addColumn("Total");

        JPanel searchPanel = new JPanel() ;
        JLabel search = new JLabel("Search: ") ;
        JTextField searchField = new JTextField(10); 
        
        search.setFont(new Font("Segoe UI" , Font.BOLD , 15));
        searchPanel.setBackground(new Color(245, 245, 247));
        searchPanel.add(search) ;
        searchPanel.add(searchField) ;

        Dimension size = new Dimension(103 , 30) ;

        JButton statistics = new JButton("Stats") ;
        JButton importReceipts = new JButton("View Import") ;
        JButton exportReceipts = new JButton("View Export") ;
        JButton Details = new JButton("Details") ;
        JButton All = new JButton("View All");
        JButton pending = new JButton("Pending...") ;
        
        JLabel fromLabel = new JLabel("From:");
        JTextField fromDateField = new JTextField(10); // Format: yyyy-MM-dd
        JLabel toLabel = new JLabel("To:");
        JTextField toDateField = new JTextField(10); // Format: yyyy-MM-dd
        JButton filterButton = new JButton("Filter");

        statistics.setPreferredSize(size) ;
        statistics.setMinimumSize(size) ;
        statistics.setMaximumSize(size) ;
        statistics.setBorderPainted(false) ;
        statistics.setFocusPainted(false) ;
        statistics.setBackground(new Color(6, 208, 1)) ;
        statistics.setForeground(Color.WHITE) ;

        importReceipts.setPreferredSize(size) ;
        importReceipts.setMinimumSize(size) ;
        importReceipts.setMaximumSize(size) ;
        importReceipts.setBorderPainted(false) ;
        importReceipts.setFocusPainted(false) ;
        importReceipts.setBackground(Color.BLACK) ;
        importReceipts.setForeground(Color.WHITE) ;

        exportReceipts.setPreferredSize(size) ;
        exportReceipts.setMinimumSize(size) ;
        exportReceipts.setMaximumSize(size) ;
        exportReceipts.setBorderPainted(false) ;
        exportReceipts.setFocusPainted(false) ;
        exportReceipts.setBackground(Color.BLACK) ;
        exportReceipts.setForeground(Color.WHITE) ;

        Details.setPreferredSize(size) ;
        Details.setMinimumSize(size) ;
        Details.setMaximumSize(size) ;
        Details.setBorderPainted(false) ;
        Details.setFocusPainted(false) ;
        Details.setBackground(Color.BLACK) ;
        Details.setForeground(Color.WHITE) ;

        All.setPreferredSize(size) ;
        All.setMinimumSize(size) ;
        All.setMaximumSize(size) ;
        All.setBorderPainted(false) ;
        All.setFocusPainted(false) ;
        All.setBackground(Color.BLACK) ;
        All.setForeground(Color.WHITE) ;

        pending.setPreferredSize(size) ;
        pending.setMinimumSize(size) ;
        pending.setMaximumSize(size) ;
        pending.setBorderPainted(false) ;
        pending.setFocusPainted(false) ;
        pending.setBackground(Color.BLACK) ;
        pending.setForeground(Color.WHITE) ;

        filterButton.setPreferredSize(size) ;
        filterButton.setMinimumSize(size) ;
        filterButton.setMaximumSize(size) ;
        filterButton.setBorderPainted(false) ;
        filterButton.setFocusPainted(false) ;
        filterButton.setBackground(Color.BLACK) ;
        filterButton.setForeground(Color.WHITE) ;

        GridBagConstraints mainPanelGBC = new GridBagConstraints() ;
        mainPanelGBC.insets = new Insets(7 , 7 , 7 , 7) ;
        mainPanelGBC.gridx = 0 ;
        mainPanelGBC.gridy = 0 ;
        mainPanel.add(statistics , mainPanelGBC) ;

        mainPanelGBC.gridx = 1 ;
        mainPanelGBC.gridy = 0 ;
        mainPanel.add(All , mainPanelGBC) ;

        mainPanelGBC.gridx = 2 ;
        mainPanelGBC.gridy = 0 ;
        mainPanel.add(importReceipts , mainPanelGBC) ;

        mainPanelGBC.gridx = 3 ;
        mainPanelGBC.gridy = 0 ;
        mainPanel.add(exportReceipts , mainPanelGBC) ;

        mainPanelGBC.gridx = 4 ;
        mainPanelGBC.gridy = 0 ;
        mainPanel.add(Details , mainPanelGBC) ;

        mainPanelGBC.gridx = 5 ;
        mainPanelGBC.gridy = 0 ;
        mainPanel.add(pending , mainPanelGBC) ;

        mainPanelGBC.gridx = 6 ;
        mainPanelGBC.gridy = 0 ;
        mainPanelGBC.weightx = 1 ;
        mainPanelGBC.fill = GridBagConstraints.HORIZONTAL ;
        mainPanel.add(searchPanel , mainPanelGBC) ;

        JPanel filterPanel = new JPanel();
        filterPanel.add(fromLabel);
        filterPanel.add(fromDateField);
        filterPanel.add(toLabel);
        filterPanel.add(toDateField);
        filterPanel.add(filterButton);

        mainPanelGBC.gridx = 6;
        mainPanelGBC.gridy = 1 ;
        mainPanelGBC.weightx = 1 ;

        mainPanelGBC.fill = GridBagConstraints.EAST ;

        mainPanel.add(filterPanel,mainPanelGBC);

        mainPanelGBC.gridwidth = 7 ;
        mainPanelGBC.gridx = 0 ;
        mainPanelGBC.gridy = 2 ;
        mainPanelGBC.weightx = 1 ;
        mainPanelGBC.weighty = 1 ;
        mainPanelGBC.fill = GridBagConstraints.BOTH ;
        mainPanel.add(ReceiptsScrollPane , mainPanelGBC) ;

        receiptBUS receiptBUS = new receiptBUS() ;
        Vector<receiptDTO> receiptList = receiptBUS.getAllReceipt() ;

        for(receiptDTO receiptDTO : receiptList){
            Vector<String> row = new Vector<>() ;
            row.add(receiptDTO.getReceiptId()) ;
            row.add(new employeeBUS().getEmployeeNameById(receiptDTO.getEmployeeID())) ;
            row.add(receiptDTO.getReceiptDate()) ;
            row.add(String.valueOf(new receiptDetailsBUS().getTotalPrice(receiptDTO.getReceiptId())));
            model.addRow(row) ;
        }

        if(type.equalsIgnoreCase("customer")){
            mainPanel.remove(statistics) ;
            mainPanel.remove(importReceipts) ;
            mainPanel.remove(exportReceipts) ;
            mainPanel.remove(All) ;
            model.setRowCount(0);
            Vector<receiptDTO> recList = new receiptBUS().showReceiptToCustomer();
            for(receiptDTO r:recList){
                Vector<String> row = new Vector<>();
                row.add(r.getReceiptId());
                row.add(new employeeBUS().getEmployeeNameById(r.getEmployeeID()));
                row.add(r.getReceiptDate()) ;
                row.add(String.valueOf(new receiptDetailsBUS().getTotalPrice(r.getReceiptId())));
                model.addRow(row);
            }
        }
        ReceiptsTable.setModel(model) ;
        mainPanel.revalidate(); 
        mainPanel.repaint();

        statistics.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                showStatisticsDialog();
            }
        });
        All.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e)
            {
                isImportMode = false;
                isAllMode = true;
                isExportMode = false;
                ReceiptsTable.setModel(model);
                ReceiptsTable.setRowHeight(16);
                receiptBUS receiptBUS = new receiptBUS() ;
                Vector<receiptDTO> receiptList = receiptBUS.getAllReceipt() ;
                model.setRowCount(0) ;
                for(receiptDTO receiptDTO : receiptList){
                    Vector<String> row = new Vector<>() ;
                    row.add(receiptDTO.getReceiptId()) ;
                    row.add(new employeeBUS().getEmployeeNameById(receiptDTO.getEmployeeID())) ;
                    row.add(receiptDTO.getReceiptDate()) ;
                    row.add(String.valueOf(new receiptDetailsBUS().getTotalPrice(receiptDTO.getReceiptId())));
                    model.addRow(row) ;
                }
            }
        });

        importReceipts.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                isImportMode = true;
                isAllMode = false;
                isExportMode = false;
                ReceiptsTable.setModel(model);
                ReceiptsTable.setRowHeight(16);
                receiptBUS receiptBUS = new receiptBUS() ;
                Vector<importDTO> importList = receiptBUS.viewImport() ;
                model.setRowCount(0) ;
                for(importDTO importDTO : importList){
                    Vector<String> row = new Vector<>() ;
                    row.add(importDTO.getReceiptId()) ;
                    row.add(new employeeBUS().getEmployeeNameById(importDTO.getEmployeeID())) ;
                    row.add(importDTO.getReceiptDate()) ;
                    row.add(String.valueOf(new receiptDetailsBUS().getTotalPrice(importDTO.getReceiptId())));
                    model.addRow(row) ;
                }
            }
        });

        exportReceipts.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                isImportMode = false;
                isAllMode = false;
                isExportMode = true;
                ReceiptsTable.setModel(model);
                ReceiptsTable.setRowHeight(16);
                receiptBUS receiptBUS = new receiptBUS() ;
                Vector<exportDTO> exportList = receiptBUS.viewExport() ;
                model.setRowCount(0) ;
                for(exportDTO exportDTO : exportList){
                    Vector<String> row = new Vector<>() ;
                    row.add(exportDTO.getReceiptId()) ;
                    row.add(new employeeBUS().getEmployeeNameById(exportDTO.getEmployeeID())) ;
                    row.add(exportDTO.getReceiptDate()) ;
                    row.add(String.valueOf(new receiptDetailsBUS().getTotalPrice(exportDTO.getReceiptId())));
                    model.addRow(row) ;
                }
            }
        });

        Details.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                int selectedRow = ReceiptsTable.getSelectedRow();
                if(selectedRow == -1){
                    customErrorDialog("Please select a row to view detail", null );
                }
                else
                {
                    String receiptID = (String) ReceiptsTable.getValueAt(selectedRow, 0);
                    JDialog receiptDetails = receiptDetailsPanel(receiptID) ;
                    receiptDetails.setVisible(true) ;
                }
            }
        });


        pending.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DefaultTableModel pendingModel = new DefaultTableModel() {
                    @Override
                    public boolean isCellEditable(int row, int column) {
                        return column == 3;
                    }
                };
                pendingModel.addColumn("ID");
                pendingModel.addColumn("Date");
                pendingModel.addColumn("Total");
                typeCheck();
                if(!type.equalsIgnoreCase("customer")){
                    pendingModel.addColumn("Action");
                    ReceiptsTable.setRowHeight(40);
                }

                Vector<receiptDTO> pendingList = new receiptBUS().getAllPendingReceipts();
                if(!type.equalsIgnoreCase("customer")){
                    for (receiptDTO receiptDTO : pendingList) {
                        Vector<Object> row = new Vector<>();
                        row.add(receiptDTO.getReceiptId());
                        row.add(receiptDTO.getReceiptDate());
                        row.add(String.valueOf(new receiptDetailsBUS().getTotalPrice(receiptDTO.getReceiptId())));
                        row.add("Pending");
                        pendingModel.addRow(row);
                    }
                    ReceiptsTable.setModel(pendingModel);
                }
                else{
                    Vector<receiptDTO> pendingListCus = new receiptBUS().showPendingReceiptToCustomer();
                    for (receiptDTO receiptDTO : pendingListCus) {
                        Vector<Object> row = new Vector<>();
                        row.add(receiptDTO.getReceiptId());
                        row.add(receiptDTO.getReceiptDate());
                        row.add(String.valueOf(new receiptDetailsBUS().getTotalPrice(receiptDTO.getReceiptId())));
                        row.add("Pending");
                        pendingModel.addRow(row);
                    }
                    ReceiptsTable.setModel(pendingModel); 
                    return;
                }
        

        
                ReceiptsTable.getColumn("Action").setCellRenderer(new ButtonRenderer());
                ReceiptsTable.getColumn("Action").setCellEditor(new ButtonEditor(new JCheckBox()));
                
                mainPanel.revalidate();
                mainPanel.repaint();
            }
        });
        searchField.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                String searchText = searchField.getText();

                receiptBUS receiptBUS = new receiptBUS();
                Vector<receiptDTO> receiptList = receiptBUS.searchReceipt(searchText);
                model.setRowCount(0);
                for(receiptDTO receiptDTO : receiptList){
                    Vector<String> row = new Vector<>() ;
                    row.add(receiptDTO.getReceiptId()) ;
                    row.add(new employeeBUS().getEmployeeNameById(receiptDTO.getEmployeeID())) ;
                    row.add(receiptDTO.getReceiptDate()) ;
                    row.add(String.valueOf(new receiptDetailsBUS().getTotalPrice(receiptDTO.getReceiptId())));
                    model.addRow(row) ;
                }
            }
        });
        filterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String fromDate = fromDateField.getText().trim();
                String toDate = toDateField.getText().trim();
        
                if (fromDate.isEmpty() || toDate.isEmpty()) {
                    customErrorDialog("Please enter both From and To dates.", null);
                    return;
                }
        
                try {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    LocalDate from = LocalDate.parse(fromDate, formatter);
                    LocalDate to = LocalDate.parse(toDate, formatter);
        
                    if (from.isAfter(to)) {
                        customErrorDialog("From date cannot be after To date.", null);
                        return;
                    }
        
                    receiptBUS receiptBUS = new receiptBUS();
                    DefaultTableModel model = (DefaultTableModel) ReceiptsTable.getModel();
                    model.setRowCount(0); // Clear the table before adding filtered data
        
                    if (isImportMode) {
                        // Filter import receipts
                        Vector<importDTO> importList = receiptBUS.getImportsByDateRange(from, to);
                        for (importDTO importDTO : importList) {
                            Vector<String> row = new Vector<>();
                            row.add(importDTO.getReceiptId());
                            row.add(new employeeBUS().getEmployeeNameById(importDTO.getEmployeeID()));
                            row.add(importDTO.getReceiptDate());
                            row.add(String.valueOf(new receiptDetailsBUS().getTotalPrice(importDTO.getReceiptId())));
                            model.addRow(row);
                        }
                    } else if (isExportMode) {
                        // Filter export receipts
                        Vector<exportDTO> exportList = receiptBUS.getExportsByDateRange(from, to);
                        for (exportDTO exportDTO : exportList) {
                            Vector<String> row = new Vector<>();
                            row.add(exportDTO.getReceiptId());
                            row.add(new employeeBUS().getEmployeeNameById(exportDTO.getEmployeeID()));
                            row.add(exportDTO.getReceiptDate());
                            row.add(String.valueOf(new receiptDetailsBUS().getTotalPrice(exportDTO.getReceiptId())));
                            model.addRow(row);
                        }
                    } else if (isAllMode) {
                        Vector<receiptDTO> receiptList = receiptBUS.getReceiptsByDateRange(from, to);
                        for (receiptDTO receiptDTO : receiptList) {
                            Vector<String> row = new Vector<>();
                            row.add(receiptDTO.getReceiptId());
                            row.add(new employeeBUS().getEmployeeNameById(receiptDTO.getEmployeeID()));
                            row.add(receiptDTO.getReceiptDate());
                            row.add(String.valueOf(new receiptDetailsBUS().getTotalPrice(receiptDTO.getReceiptId())));
                            model.addRow(row);
                        }
                    }
                } catch (DateTimeParseException ex) {
                    customErrorDialog("Invalid date format. Please use yyyy-MM-dd.", null);
                }
            }
        });
    }

    public JDialog receiptDetailsPanel(String receiptID) {
        JDialog receiptDetails = new JDialog();
        receiptDetails.setTitle("Receipt Details");
        receiptDetails.setSize(800, 600);
        receiptDetails.setLayout(new GridBagLayout());
        receiptDetails.setLocationRelativeTo(null);
        receiptDetails.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
    
        JLabel title = new JLabel("Receipt Details");
        title.setFont(new Font("Segoe UI", Font.BOLD, 20));
        title.setPreferredSize(new Dimension(300, 50));
    
        JLabel receiptIDLabel = new JLabel("Receipt ID: " + receiptID);
        JLabel employeeIDLabel = new JLabel();
        JLabel dateLabel = new JLabel();
        JLabel providerName = new JLabel();
        JLabel customerName = new JLabel();
        receiptIDLabel.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        employeeIDLabel.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        dateLabel.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        providerName.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        customerName.setFont(new Font("Segoe UI", Font.PLAIN, 15));
    
        JTable receiptDetailsTable = new JTable();
        JScrollPane receiptDetailsScrollPane = new JScrollPane(receiptDetailsTable);
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Product ID");
        model.addColumn("Product Name");
        model.addColumn("Quantity");
        model.addColumn("Price");
        model.addColumn("Total");

        boolean receiptType = new receiptBUS().checkReceiptType(receiptID);
        receiptDTO receipt = new receiptBUS().getReceiptDTOById(receiptID);
        employeeIDLabel.setText("Employee Name: "+ new employeeBUS().getEmployeeNameById(receipt.getEmployeeID()));
        dateLabel.setText("Date: " + receipt.getReceiptDate());
        if(receiptType){
            importDTO importDTO = (importDTO) receipt;
            String providername = new providerBUS().getProviderNameById(importDTO.getProviderID());
            providerName.setText("Provider Name: " + providername);
        }
        else
        {
            exportDTO exportDTO = (exportDTO) receipt;
            String customername = new customerBUS().getCustomerNameById(exportDTO.getCustomerID());
            customerName.setText("Customer Name: " + customername);
        }

        receiptDetailsBUS receiptDetailsBUS = new receiptDetailsBUS();
        Vector<receiptDetailsDTO> receiptDetailsList = receiptDetailsBUS.getAllReceiptDetails(receiptID);
        
        int grandTotal = 0;
        for (receiptDetailsDTO receiptDetailsDTO : receiptDetailsList) {
            Vector<String> row = new Vector<>();
            row.add(receiptDetailsDTO.getProductID());
            row.add(new productBUS().getProductNameById(receiptDetailsDTO.getProductID()));
            row.add(String.valueOf(receiptDetailsDTO.getQuantity()));
            row.add(String.valueOf(receiptDetailsDTO.getPrice()));
            int total = receiptDetailsDTO.getQuantity() * receiptDetailsDTO.getPrice();
            row.add(String.valueOf(total));
            grandTotal += total;
            model.addRow(row);
        }
        JTableHeader tableHeader = receiptDetailsTable.getTableHeader() ;
        tableHeader.setFont(new Font("Segoe UI" , Font.BOLD , 15)) ;
        tableHeader.setBackground(new Color(72, 166, 167)) ;
        tableHeader.setForeground(Color.WHITE) ;
        receiptDetailsTable.setModel(model);
    
        // Apply hover effects and custom renderers
        receiptDetailsTable.setDefaultRenderer(Object.class, new HoverRenderer());
        receiptDetailsTable.setRowHeight(40);
    
        JLabel grandTotalLabel = new JLabel("Grand Total: " + String.valueOf(grandTotal));
        grandTotalLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        grandTotalLabel.setForeground(new Color(0, 128, 0));
    
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.gridwidth = 2;
        receiptDetails.add(title, gbc);
    
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;
        receiptDetails.add(receiptIDLabel, gbc);
    
        gbc.gridy = 2;
        receiptDetails.add(employeeIDLabel, gbc);
        
        if(receiptType){
            gbc.gridy = 3;
            receiptDetails.add(providerName, gbc);
        }
        else{
            gbc.gridy = 3;
            receiptDetails.add(customerName, gbc);
        }

        gbc.gridy = 4;
        receiptDetails.add(dateLabel, gbc);
    
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1;
        receiptDetailsScrollPane.setPreferredSize(new Dimension(600, 300));
        receiptDetails.add(receiptDetailsScrollPane, gbc);
    
        gbc.gridy = 5;
        gbc.weighty = 0;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.EAST;
        receiptDetails.add(grandTotalLabel, gbc);
    
        return receiptDetails;
    }
    
    public void showStatisticsDialog() {
        JDialog statisticsDialog = new JDialog();
        statisticsDialog.setTitle("Statistics");
        statisticsDialog.setSize(400, 200);
        statisticsDialog.setLayout(new GridBagLayout());
        statisticsDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
    
        JButton byMonthButton = new JButton("By Month");
        JButton byYearButton = new JButton("By Year");
    
        byMonthButton.setPreferredSize(new Dimension(150, 40));
        byYearButton.setPreferredSize(new Dimension(150, 40));

        byMonthButton.setFocusPainted(false);
        byMonthButton.setBorderPainted(false);
        byMonthButton.setBackground(Color.BLACK);
        byMonthButton.setForeground(Color.WHITE);

        byYearButton.setFocusPainted(false);
        byYearButton.setBorderPainted(false);
        byYearButton.setBackground(Color.BLACK);
        byYearButton.setForeground(Color.WHITE);
    
        byMonthButton.addActionListener(e -> {
            statisticsDialog.dispose();
            showStatisticsByMonth();

        });
    
        byYearButton.addActionListener(e -> {
            statisticsDialog.dispose();
            showStatisticsByYear();
        });
    
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = 0;
        statisticsDialog.add(byMonthButton, gbc);
    
        gbc.gridx = 1;
        statisticsDialog.add(byYearButton, gbc);
    
        statisticsDialog.setLocationRelativeTo(null);
        statisticsDialog.setVisible(true);
    }
    
    public void showStatisticsByMonth() {
        JDialog byMonthDialog = new JDialog();
        byMonthDialog.setTitle("Statistics By Month");
        byMonthDialog.setSize(800, 600);
        byMonthDialog.setLayout(new BorderLayout());

        JPanel container = new JPanel(new GridLayout(1, 2));
        JPanel panelOne = new JPanel(new BorderLayout());
        JPanel panelTwo = new JPanel(new BorderLayout());

        //Panel 1
        JLabel title = new JLabel("Revenue Statistics By Month", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 20));
        panelOne.add(title, BorderLayout.NORTH);

        JTable statisticsTable = new JTable();
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Month");
        model.addColumn("Year");
        model.addColumn("Revenue");

        receiptBUS bus = new receiptBUS();
        Vector<Vector<Object>> revenueData = bus.getRevenueStatisticsByMonth();
        for (Vector<Object> row : revenueData) {
            model.addRow(row);
        }
        statisticsTable.setModel(model);

        JTableHeader tableHeader = statisticsTable.getTableHeader();
        tableHeader.setFont(new Font("Segoe UI", Font.BOLD, 15));
        tableHeader.setBackground(new Color(72, 166, 167));
        tableHeader.setForeground(Color.WHITE);

        statisticsTable.setDefaultRenderer(Object.class, new HoverRenderer());
        statisticsTable.setRowHeight(40);

        JScrollPane scrollPane = new JScrollPane(statisticsTable);
        panelOne.add(scrollPane, BorderLayout.CENTER);

        JButton closeButton = new JButton("Close");
        closeButton.setFocusPainted(false);
        closeButton.setBorderPainted(false);
        closeButton.setBackground(Color.BLACK);
        closeButton.setForeground(Color.WHITE);
        closeButton.addActionListener(e -> byMonthDialog.dispose());
        panelOne.add(closeButton, BorderLayout.SOUTH);

        //Panel 2
        //Graph
        GraphPanel graPanel = new GraphPanel(revenueData);
        panelTwo.add(graPanel, BorderLayout.CENTER);

        container.add(panelOne);
        container.add(panelTwo);

        byMonthDialog.add(container, BorderLayout.CENTER);
        byMonthDialog.setLocationRelativeTo(null);
        byMonthDialog.setVisible(true);


    }
    
    public void showStatisticsByYear() {
        JDialog byYearDialog = new JDialog();
        byYearDialog.setTitle("Statistics By Year");
        byYearDialog.setSize(800, 600);
        byYearDialog.setLayout(new BorderLayout());
    
        JPanel container = new JPanel(new GridLayout(1, 2));
        JPanel panelOne = new JPanel(new BorderLayout());
        JPanel panelTwo = new JPanel(new BorderLayout());

        //Panel 1
        JLabel title = new JLabel("Revenue Statistics By Year", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 20));
        panelOne.add(title, BorderLayout.NORTH);

        JTable statisticsTable = new JTable();
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Year");
        model.addColumn("Revenue");

        receiptBUS bus = new receiptBUS();
        Vector<Vector<Object>> revenueData = bus.getRevenueStatisticsByYear();
        for (Vector<Object> row : revenueData) {
            model.addRow(row);
        }
        statisticsTable.setModel(model);

        JTableHeader tableHeader = statisticsTable.getTableHeader();
        tableHeader.setFont(new Font("Segoe UI", Font.BOLD, 15));
        tableHeader.setBackground(new Color(72, 166, 167));
        tableHeader.setForeground(Color.WHITE);

        statisticsTable.setDefaultRenderer(Object.class, new HoverRenderer());
        statisticsTable.setRowHeight(40);

        JScrollPane scrollPane = new JScrollPane(statisticsTable);
        panelOne.add(scrollPane, BorderLayout.CENTER);

        JButton closeButton = new JButton("Close");
        closeButton.setFocusPainted(false);
        closeButton.setBorderPainted(false);
        closeButton.setBackground(Color.BLACK);
        closeButton.setForeground(Color.WHITE);
        closeButton.addActionListener(e -> byYearDialog.dispose());
        panelOne.add(closeButton, BorderLayout.SOUTH);

        //Panel 2
        //Graph
        YearGraphPanel graPanel = new YearGraphPanel(revenueData);
        panelTwo.add(graPanel, BorderLayout.CENTER);
        container.add(panelOne);
        container.add(panelTwo);

        byYearDialog.add(container, BorderLayout.CENTER);
        byYearDialog.setLocationRelativeTo(null);
        byYearDialog.setVisible(true);
    }
    public void customErrorDialog(String message, JDialog Dialog)
    {
        JDialog errorDialog = new JDialog();
        errorDialog.setTitle("Error");
        errorDialog.setLayout(new GridBagLayout());
        errorDialog.setSize(450,200);
        errorDialog.setLocationRelativeTo(Dialog);

        JButton okButton = new JButton("OK");
        okButton.setFocusPainted(false);
        okButton.setBorderPainted(false);
        okButton.setBackground(Color.BLACK);
        okButton.setForeground(Color.WHITE);
        okButton.setFont(new Font("Segoe UI", Font.BOLD, 14));

        JLabel messageLabel = new JLabel(message);
        messageLabel.setFont(new Font("Segoe UI", Font.BOLD, 15));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10,10,10,10);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        errorDialog.add(messageLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.NONE;
        errorDialog.add(okButton, gbc);

        errorDialog.setVisible(true);

        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                errorDialog.dispose();
            }
        });
    }
    
    public void customSuccessDialog(String message ,JDialog Dialog)
    {
        JDialog successDialog = new JDialog();
        successDialog.setTitle("Success!");
        successDialog.setLayout(new GridBagLayout());
        successDialog.setSize(300,250);
        successDialog.setLocationRelativeTo(Dialog);

        JButton okButton = new JButton("OK");
        okButton.setFocusPainted(false);
        okButton.setBorderPainted(false);
        okButton.setBackground(Color.BLACK);
        okButton.setForeground(Color.WHITE);
        okButton.setFont(new Font("Segoe UI", Font.BOLD, 14));

        JLabel messageLabel = new JLabel(message);
        messageLabel.setFont(new Font("Segoe UI", Font.BOLD, 15));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10,10,10,10);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        successDialog.add(messageLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.NONE;
        successDialog.add(okButton, gbc);

        successDialog.setVisible(true);

        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                successDialog.dispose();
            }
        });
    }
    private class HoverRenderer extends DefaultTableCellRenderer{
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            
            if(row == hoveredRow && !isSelected){
                c.setBackground(new Color(242, 239, 231)); 
            } 
            else if (isSelected){
                c.setBackground(new Color(128, 203, 196));
            } 
            else{
                c.setBackground(table.getBackground());
            }
            return c;
        }
    }

private class ButtonRenderer extends JPanel implements TableCellRenderer {
    private final JButton acceptButton = new JButton("Accept");
    private final JButton refuseButton = new JButton("Refuse");

    public ButtonRenderer() {
        setLayout(new FlowLayout(FlowLayout.CENTER, 5, 0));
        add(acceptButton);
        add(refuseButton);
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column) {
        return this;
    }
}

private class ButtonEditor extends AbstractCellEditor implements TableCellEditor {
    private final JPanel panel = new JPanel();
    private final JButton acceptButton = new JButton("Accept");
    private final JButton refuseButton = new JButton("Refuse");
    private int currentRow = ReceiptsTable.getSelectedRow();

    public ButtonEditor(JCheckBox checkBox) {
        panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 0));
        panel.add(acceptButton);
        panel.add(refuseButton);

        acceptButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                accountDTO currentAcc = new accountBUS().getCurrentAccount();
                String receiptID = ReceiptsTable.getValueAt(currentRow, 0).toString();
                int temp = new receiptBUS().acceptReceipt(receiptID, currentAcc.getUsername().trim());
                if (temp == 1) {
                    customSuccessDialog("Receipt Accepted", null);

                    DefaultTableModel model = (DefaultTableModel) ReceiptsTable.getModel();
                    model.removeRow(currentRow);
                } else {
                    customErrorDialog("Receipt Not Accepted", null);
                }
            }
        });
        refuseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                String receiptID = ReceiptsTable.getValueAt(currentRow, 0).toString();
                receiptDTO receipt = new receiptBUS().getReceiptDTOById(receiptID);
                receiptBUS recBUS = new receiptBUS();
                boolean b = recBUS.refuseReceipt(receipt);
                if(b){
                    customSuccessDialog("Receipt Refused", null);
                    DefaultTableModel model = (DefaultTableModel) ReceiptsTable.getModel();
                    model.removeRow(currentRow);
                }
            }
        });
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value,
            boolean isSelected, int row, int column) {
        currentRow = row;
        return panel;
    }

    @Override
    public Object getCellEditorValue() {
        return "";
    }
}

}
