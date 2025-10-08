package com.microsoft.sqlserver.GUI;

import javax.swing.*;
import javax.swing.table.*;

import com.microsoft.sqlserver.BUS.accountBUS;
import com.microsoft.sqlserver.BUS.cusAccountBUS;
import com.microsoft.sqlserver.BUS.customerBUS;
import com.microsoft.sqlserver.BUS.productBUS;
import com.microsoft.sqlserver.BUS.providerBUS;
import com.microsoft.sqlserver.BUS.receiptDetailsBUS;
import com.microsoft.sqlserver.DAO.productDAO;
import com.microsoft.sqlserver.DTO.accountDTO;
import com.microsoft.sqlserver.DTO.customerDTO;
import com.microsoft.sqlserver.DTO.productDTO;
import com.microsoft.sqlserver.DTO.providerDTO;
import com.microsoft.sqlserver.DTO.receiptDetailsDTO;

import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import java.util.Vector;

@SuppressWarnings("FieldMayBeFinal")
public class productsTable {
    private mainInt mainInt = new mainInt();
    private JPanel mainPanel = mainInt.getMainPanel();

    private JTable productsTable = new JTable();
    DefaultTableModel model = new DefaultTableModel();

    private JDialog currentAddDialog, updateDialog, exportDetail;
    private JDialog deleteDialog;

    private JButton add = new JButton("Add");
    private JButton delete = new JButton("Delete");
    private JButton update = new JButton("Update");
    private JButton export = new JButton("Export");

    private int hoveredRow = -1;

    private JTextField ProductNameField;
    private JTextField ProductPriceField;
    private JTextField QuantityField;
    private JComboBox providercb;
    private JComboBox typeComboBox;
    
    private String type;

    public void typeCheck() {
        accountBUS accountBUS = new accountBUS();
        cusAccountBUS cusAccountBUS = new cusAccountBUS();

        if (accountBUS.getCurrentAccount() != null) {
            type = accountBUS.getCurrentAccount().getType().trim();
        } else {
            type = cusAccountBUS.getCurrentAccount().getType().trim();
        }
    }
    public void productsTableInitiate() {
        typeCheck();
        boolean isEmpty = true;
        productsTable.setShowGrid(true);
        productsTable.setGridColor(new Color(154, 203, 208));
        productsTable.setDefaultRenderer(Object.class, new HoverRenderer());

        productsTable.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                Point p = e.getPoint();
                int row = productsTable.rowAtPoint(p);

                if (row != hoveredRow) {
                    hoveredRow = row;
                    productsTable.repaint();
                }
            }
        });

        JTableHeader tableHeader = productsTable.getTableHeader();
        tableHeader.setFont(new Font("Segoe UI", Font.BOLD, 15));
        tableHeader.setBackground(new Color(72, 166, 167));
        tableHeader.setForeground(Color.WHITE);

        JScrollPane productsScrollPane = new JScrollPane(productsTable);
        
        model.addColumn("ID");
        model.addColumn("Name");
        model.addColumn("Price");
        model.addColumn("Quantity");
        model.addColumn("Type");
        model.addColumn("Provider Name");
        
        Dimension size = new Dimension(90, 30);
        add.setPreferredSize(size);
        export.setPreferredSize(size);
        update.setPreferredSize(size);
        delete.setPreferredSize(size);

        add.setMinimumSize(size);
        export.setMinimumSize(size);
        update.setMinimumSize(size);
        delete.setMinimumSize(size);

        add.setMaximumSize(size);
        export.setMaximumSize(size);
        update.setMaximumSize(size);
        delete.setMaximumSize(size);

        add.setBorderPainted(false);
        export.setBorderPainted(false);
        update.setBorderPainted(false);
        delete.setBorderPainted(false);
        
        add.setFocusPainted(false);
        export.setFocusPainted(false);
        update.setFocusPainted(false);
        delete.setFocusPainted(false);

        add.setBackground(new Color(6, 208, 1));
        export.setBackground(new Color(131, 111, 255));
        update.setBackground(new Color(238, 124, 75));
        delete.setBackground(new Color(247, 55, 79));

        add.setForeground(Color.WHITE);
        export.setForeground(Color.WHITE);
        update.setForeground(Color.WHITE);
        delete.setForeground(Color.WHITE);

        JPanel searchPanel = new JPanel();
        JLabel search = new JLabel("Search: ");
        JTextField searchField = new JTextField(15);
        
        search.setFont(new Font("Segoe UI", Font.BOLD, 15));
        searchPanel.setBackground(new Color(245, 245, 247));
        JComboBox<String> typeComboBox = new JComboBox<>();
        JComboBox<String> providerComboBox = new JComboBox<>();

        productBUS bus = new productBUS();

        Vector<String> types = bus.getProductTypes(); 
        typeComboBox.addItem("Show All");
        for (String type : types) {
            typeComboBox.addItem(type);
        }
        
        Vector<String> providers = bus.getProviderIDs(); 
        providerComboBox.addItem("Show All");
        for (String provider : providers) {
            providerComboBox.addItem(new providerBUS().getProviderNameById(provider));
        }
        
        searchPanel.setLayout(new BoxLayout(searchPanel, BoxLayout.Y_AXIS));

        JPanel searchPanel1 = new JPanel();
        searchPanel1.setBackground(new Color(245, 245, 247));
        searchPanel1.setLayout(new GridLayout(2, 2));
        GridBagConstraints span = new GridBagConstraints();
        span.insets = new Insets(7, 7, 7, 7);

        span.gridx = 0;
        span.gridy = 0;
        searchPanel1.add(search, span);

        span.gridx = 0;
        span.gridy = 1;
        searchPanel1.add(searchField, span);
        
        searchPanel.add(searchPanel1);

        JPanel comboBoxPanel = new JPanel();
        comboBoxPanel.setBackground(new Color(245, 245, 247));
        comboBoxPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        comboBoxPanel.add(typeComboBox);
        comboBoxPanel.add(providerComboBox);

        searchPanel.add(comboBoxPanel);


        JComboBox<String> priceComboBox = new JComboBox<>();
        priceComboBox.addItem("Low -> High");
        priceComboBox.addItem("High -> Low");
        priceComboBox.addItem("Price range");

        JTextField minPriceField = new JTextField(10);
        JTextField maxPriceField = new JTextField(10);

        JPanel pricePanel = new JPanel();
        pricePanel.setBackground(new Color(245, 245, 247)) ;
        pricePanel.add(new JLabel("Sort by price:"));
        pricePanel.add(priceComboBox);
        pricePanel.add(new JLabel("Price from:"));
        pricePanel.add(minPriceField);
        pricePanel.add(new JLabel("To:"));
        pricePanel.add(maxPriceField);


        GridBagConstraints mainPanelGBC = new GridBagConstraints();
        mainPanelGBC.insets = new Insets(7, 7, 7, 7);
        if (!type.equalsIgnoreCase("customer")) {
            mainPanelGBC.gridx = 0;
            mainPanelGBC.gridy = 0;
            mainPanel.add(add, mainPanelGBC);

            mainPanelGBC.gridx = 1;
            mainPanelGBC.gridy = 0;
            mainPanel.add(delete, mainPanelGBC);

            mainPanelGBC.gridx = 2;
            mainPanelGBC.gridy = 0;
            mainPanel.add(update, mainPanelGBC);

            mainPanelGBC.gridx = 3;
            mainPanelGBC.gridy = 0;
            mainPanel.add(export, mainPanelGBC);

            mainPanelGBC.gridx = 5;
            mainPanelGBC.gridy = 0;
            mainPanelGBC.weightx = 1;
            mainPanelGBC.fill = GridBagConstraints.HORIZONTAL;
            mainPanel.add(searchPanel, mainPanelGBC);

            mainPanelGBC.gridx = 6;
            mainPanelGBC.gridy = 0;
            mainPanelGBC.weightx = 1;
            mainPanelGBC.fill = GridBagConstraints.HORIZONTAL;
            mainPanel.add(pricePanel, mainPanelGBC);

            export.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    exportCreate();
                }
            });
        } else {
            export.setText("Buy");
            mainPanelGBC.gridx = 0;
            mainPanelGBC.gridy = 0;
            mainPanel.add(export, mainPanelGBC);
            
            mainPanelGBC.gridx = 5;
            mainPanelGBC.gridy = 0;
            mainPanelGBC.weightx = 1;
            mainPanelGBC.fill = GridBagConstraints.HORIZONTAL;
            mainPanel.add(searchPanel, mainPanelGBC);

            mainPanelGBC.gridx = 6;
            mainPanelGBC.gridy = 0;
            mainPanelGBC.weightx = 1;
            mainPanelGBC.fill = GridBagConstraints.HORIZONTAL;
            mainPanel.add(pricePanel, mainPanelGBC);
            
            export.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    buy();
                }
            });
        }
        mainPanelGBC.gridwidth = 10;
        mainPanelGBC.fill = GridBagConstraints.BOTH;
        mainPanelGBC.weightx = 1;
        mainPanelGBC.weighty = 1;
        mainPanelGBC.gridx = 0;
        mainPanelGBC.gridy = 1;
        mainPanel.add(productsScrollPane, mainPanelGBC);
        
        updateTable();
        
        productsTable.setModel(model);
        mainPanel.revalidate();
        mainPanel.repaint();

        add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addPanel();
            }
        });

        delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = productsTable.getSelectedRow();
                if (selectedRow == -1) {
                    customErrorDialog("Please select a product to delete.", null);
                    return;
                }
                deletePanel();
            }
        });

        update.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = productsTable.getSelectedRow();
                if (selectedRow == -1) {
                    customErrorDialog("Please select a product to update.", null);
                    return;
                }
                updatePanel();
            }
        });

        ActionListener comboFilterListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedType = typeComboBox.getSelectedItem().toString();
                String selectedProvider = providerComboBox.getSelectedItem().toString();
                String selectedPriceOption = priceComboBox.getSelectedItem().toString();
                String keyword = searchField.getText().trim();

                selectedProvider = new providerBUS().getProviderIdByName(selectedProvider);

                if (selectedType.equals("Show All")) selectedType = null;
                if (selectedProvider.equals("Show All")) selectedProvider = null;

                double minPrice = 0;
                double maxPrice = Double.MAX_VALUE;

                if (selectedPriceOption.equals("Price range")) {
                    try {
                        if (minPriceField.getText().isEmpty() || maxPriceField.getText().isEmpty()) {
                            JOptionPane.showMessageDialog(null, "Please fill in both fields", "Error", JOptionPane.ERROR_MESSAGE);
                            return; 
                        }
                        if (minPrice > maxPrice) {
                            JOptionPane.showMessageDialog(null, "Min price must be lower", "Error", JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                        } catch (NumberFormatException ex) {
                            minPrice = 0;
                            maxPrice = Double.MAX_VALUE;
                            JOptionPane.showMessageDialog(null, "Please enter a valid prize", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                        maxPrice = Double.parseDouble(maxPriceField.getText().replaceAll("\\s", ""));
                        minPrice = Double.parseDouble(minPriceField.getText().replaceAll("\\s", ""));
                    }
                
                Vector<productDTO> filteredProducts = bus.searchProductAdvanced(keyword, selectedType, selectedProvider, selectedPriceOption, minPrice, maxPrice);

                model.setRowCount(0); 
                for (productDTO pro : filteredProducts) {
                    Vector<Object> row = new Vector<>();
                    row.add(pro.getProductID());
                    row.add(pro.getProductName());
                    row.add(pro.getProductPrice());
                    row.add(pro.getQuantity());
                    row.add(pro.getProductType());
                    row.add(new providerBUS().getProviderNameById(pro.getProviderID()));
                    model.addRow(row);
                }
            }
        };

        typeComboBox.addActionListener(comboFilterListener);
        providerComboBox.addActionListener(comboFilterListener);
        priceComboBox.addActionListener(comboFilterListener);
        searchField.addActionListener(comboFilterListener);
        
    }
    // end of products table initiate
    public void addPanel() {
        if (currentAddDialog != null && currentAddDialog.isShowing()) {
            currentAddDialog.dispose();
        }

        currentAddDialog = new JDialog();
        currentAddDialog.setSize(900, 800);
        currentAddDialog.setLocationRelativeTo(mainPanel);
        currentAddDialog.setTitle("Add Product");
        currentAddDialog.setLayout(new GridBagLayout());

        JLabel title = new JLabel("Add Product");
        JLabel productName = new JLabel("Product Name: ");
        JLabel productPrice = new JLabel("Product Price: ");
        JLabel productQuantity = new JLabel("Quantity: ");
        JLabel provider = new JLabel("Provider: ");
        JLabel productType = new JLabel("Product Type: ");

        title.setFont(new Font("Georgia", Font.BOLD, 17));
        productName.setFont(new Font("Georgia", Font.BOLD, 14));
        productPrice.setFont(new Font("Georgia", Font.BOLD, 14));
        productQuantity.setFont(new Font("Georgia", Font.BOLD, 14));
        provider.setFont(new Font("Georgia", Font.BOLD, 14));
        productType.setFont(new Font("Georgia", Font.BOLD, 14));

        ProductNameField = new JTextField();
        ProductPriceField = new JTextField();
        QuantityField = new JTextField();
        providercb = new JComboBox();
        
        productBUS bus = new productBUS();

        JComboBox<String> typeComboBox = new JComboBox<>();
        Vector<String> types = bus.getProductTypes(); 
        for (String type : types) {
            typeComboBox.addItem(type);
        }

        ProductNameField.setPreferredSize(new Dimension(150, 20));
        ProductPriceField.setPreferredSize(new Dimension(150, 20));
        QuantityField.setPreferredSize(new Dimension(150, 20));
        providercb.setPreferredSize(new Dimension(150, 20));
        typeComboBox.setPreferredSize(new Dimension(150, 20));

        JButton confirm = new JButton("Done");
        JButton addMore = new JButton("Add More");
        JButton delete = new JButton("Delete");
        JButton cancel = new JButton("Cancel");

        confirm.setFocusPainted(false);
        confirm.setBorderPainted(false);
        confirm.setBackground(Color.BLACK);
        confirm.setForeground(Color.WHITE);
        confirm.setFont(new Font("Segoe UI", Font.BOLD, 14));

        addMore.setFocusPainted(false);
        addMore.setBorderPainted(false);
        addMore.setBackground(new Color(6, 208, 1));
        addMore.setForeground(Color.WHITE);
        addMore.setFont(new Font("Segoe UI", Font.BOLD, 14));

        delete.setFocusPainted(false);
        delete.setBorderPainted(false);
        delete.setBackground(new Color(247, 55, 79));
        delete.setForeground(Color.WHITE);
        delete.setFont(new Font("Segoe UI", Font.BOLD, 14));

        cancel.setFocusPainted(false);
        cancel.setBorderPainted(false);
        cancel.setBackground(new Color(169, 169, 169));
        cancel.setForeground(Color.WHITE);
        cancel.setFont(new Font("Segoe UI", Font.BOLD, 14));

        DefaultTableModel tableModel = new DefaultTableModel(new String[]{"Name", "Price", "Quantity", "Provider", "Type"}, 0);
        JTable addedProductsTable = new JTable(tableModel);
        addedProductsTable.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        addedProductsTable.setRowHeight(25);
        addedProductsTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 15));
        addedProductsTable.getTableHeader().setBackground(new Color(72, 166, 167));
        addedProductsTable.getTableHeader().setForeground(Color.WHITE);

        JScrollPane tableScrollPane = new JScrollPane(addedProductsTable);
        tableScrollPane.setPreferredSize(new Dimension(500, 150));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        currentAddDialog.add(title, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;
        currentAddDialog.add(productName, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.EAST;
        currentAddDialog.add(ProductNameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.WEST;
        currentAddDialog.add(productPrice, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.EAST;
        currentAddDialog.add(ProductPriceField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.WEST;
        currentAddDialog.add(productQuantity, gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.EAST;
        currentAddDialog.add(QuantityField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.WEST;
        currentAddDialog.add(provider, gbc);

        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.EAST;
        currentAddDialog.add(providercb, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.anchor = GridBagConstraints.WEST;
        currentAddDialog.add(productType, gbc);

        gbc.gridx = 1;
        gbc.gridy = 5;
        gbc.anchor = GridBagConstraints.EAST;
        currentAddDialog.add(typeComboBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        currentAddDialog.add(addMore, gbc);

        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        currentAddDialog.add(delete, gbc);

        gbc.gridx = 0;
        gbc.gridy = 8;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        currentAddDialog.add(confirm, gbc);

        gbc.gridx = 0;
        gbc.gridy = 9;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        currentAddDialog.add(cancel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 10;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.BOTH;
        currentAddDialog.add(tableScrollPane, gbc);

        cancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentAddDialog.dispose();
            }
        });
        providerBUS proBUS = new providerBUS();
        Vector<providerDTO> proList = proBUS.getAllProviders();
        for (providerDTO p : proList) {
            providercb.addItem(p.getProviderName());
        }

        Vector<productDTO> productToAdd = new Vector<>();

        addMore.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (ProductNameField.getText().isEmpty() || ProductPriceField.getText().isEmpty() || QuantityField.getText().isEmpty()) {
                        throw new Exception("Field(s) cannot be empty");
                    }
                    if (Integer.parseInt(ProductPriceField.getText()) < 0) {
                        throw new Exception("Price cannot be negative");
                    }

                    if (!isInteger(ProductPriceField.getText())) {
                        throw new Exception("Price must be a number");
                    }

                    if (Integer.parseInt(QuantityField.getText()) < 0) {
                        throw new Exception("Quantity cannot be negative");
                    }

                    productDTO product = new productDTO();
                    product.setProductName(ProductNameField.getText());
                    product.setProductPrice(Integer.parseInt(ProductPriceField.getText()));
                    product.setQuantity(Integer.parseInt(QuantityField.getText()));
                    product.setProviderID(new providerBUS().getProviderIdByName(providercb.getSelectedItem().toString()));
                    product.setProductType(typeComboBox.getSelectedItem().toString());

                    productToAdd.add(product);

                    // Add product to the table
                    tableModel.addRow(new Object[]{
                        product.getProductName(),
                        product.getProductPrice(),
                        product.getQuantity(),
                        providercb.getSelectedItem().toString(),
                        product.getProductType()
                    });

                    ProductNameField.setText("");
                    ProductPriceField.setText("");
                    QuantityField.setText("");
                } catch (Exception ex) {
                    customErrorDialog(ex.getMessage(), currentAddDialog);
                }
            }
        });

    delete.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            int selectedRow = addedProductsTable.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(currentAddDialog, "Please select a product to delete.", "No Selection", JOptionPane.WARNING_MESSAGE);
                return;
            }

            productToAdd.remove(selectedRow);
            tableModel.removeRow(selectedRow);
        }
    });

    confirm.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (productToAdd.size() > 0) {
                productBUS productBUS = new productBUS();
                productBUS.addProduct(productToAdd);
                customSuccessDialog(currentAddDialog);
                currentAddDialog.dispose();
                model.setRowCount(0);
                updateTable();
            } else {
                customErrorDialog("You have not added any product yet", currentAddDialog);
            }
        }
    });

    currentAddDialog.setVisible(true);
}

    @SuppressWarnings("UnnecessaryTemporaryOnConversionFromString")
    private boolean isInteger(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    @SuppressWarnings("Convert2Lambda")
    public void deletePanel() {
        if (deleteDialog != null && deleteDialog.isShowing()) {
            deleteDialog.dispose();
        }
        JDialog deleteDialog = new JDialog();
        deleteDialog.setTitle("Delete Product");
        deleteDialog.setSize(250, 200);
        deleteDialog.setLayout(new GridBagLayout());
        deleteDialog.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        deleteDialog.setLocationRelativeTo(mainPanel);

        JLabel message = new JLabel("Do you want to delete?");
        message.setFont(new Font("Segoe UI", Font.BOLD, 15));

        JButton yesButton = new JButton("Yes");
        JButton cancelButton = new JButton("Cancel");
        
        yesButton.setFocusPainted(false);
        yesButton.setBorderPainted(false);
        yesButton.setBackground(Color.green);
        yesButton.setForeground(Color.WHITE);
        yesButton.setPreferredSize(new Dimension(80, 25));

        cancelButton.setFocusPainted(false);
        cancelButton.setBorderPainted(false);
        cancelButton.setBackground(Color.red);
        cancelButton.setForeground(Color.WHITE);

        cancelButton.setPreferredSize(new Dimension(80, 25));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 20, 10);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.weightx = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        deleteDialog.add(message, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.NONE;
        gbc.gridwidth = 1;
        deleteDialog.add(yesButton, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.NONE;
        gbc.gridwidth = 1;
        deleteDialog.add(cancelButton, gbc);

        deleteDialog.setVisible(true);

        yesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = productsTable.getSelectedRow();
                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(deleteDialog, "Please select a product to delete.", "No Selection", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                productDTO product = new productDTO();

                product.setProductID(productsTable.getValueAt(selectedRow, 0).toString());
                product.setProductName(productsTable.getValueAt(selectedRow, 1).toString());

                product.setProductPrice(Integer.parseInt(productsTable.getValueAt(selectedRow, 2).toString()));
                product.setQuantity(Integer.parseInt(productsTable.getValueAt(selectedRow, 3).toString()));
    
                product.setProductType(productsTable.getValueAt(selectedRow, 4).toString());
                product.setProviderID(productsTable.getValueAt(selectedRow, 5).toString());
                if(product.getQuantity()!=0){
                    customErrorDialog("You can only delete the product when quantity reaches 0", deleteDialog);
                    deleteDialog.dispose();
                }
                else{
                    productBUS productBUS = new productBUS();
                    productBUS.deleteProduct(product);

                    customSuccessDialog(deleteDialog);
                    deleteDialog.dispose();

                    model.setRowCount(0);
                    updateTable();
                    
                    productsTable.setModel(model);
                    mainPanel.revalidate();
                    mainPanel.repaint();
                }
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteDialog.dispose();
            }
        });
    }

    public void updatePanel() {
        if (updateDialog != null && updateDialog.isShowing()) {
            updateDialog.dispose();
        }
        updateDialog = new JDialog();
        updateDialog.setSize(600, 500);
        updateDialog.setLocationRelativeTo(mainPanel);
        updateDialog.setTitle("Update Product");
        updateDialog.setLayout(new GridBagLayout());

        JLabel title = new JLabel("Update Product");
        // JLabel productID = new JLabel("Product ID: ");
        JLabel productName = new JLabel("Product Name: ");
        JLabel productPrice = new JLabel("Product Price: ");
        JLabel productQuantity = new JLabel("QuantityField: ");
        JLabel provider = new JLabel("Provider: ");
        JLabel productType = new JLabel("Product Type: ");

        title.setFont(new Font("Georgia", Font.BOLD, 17));
        // productID.setFont(new Font("Georgia", Font.BOLD, 14));
        productName.setFont(new Font("Georgia", Font.BOLD, 14));
        productPrice.setFont(new Font("Georgia", Font.BOLD, 14));
        productQuantity.setFont(new Font("Georgia", Font.BOLD, 14));
        provider.setFont(new Font("Georgia", Font.BOLD, 14));
        productType.setFont(new Font("Georgia", Font.BOLD, 14));

        // JTextField ProductID = new JTextField();
        ProductNameField = new JTextField();
        ProductPriceField = new JTextField();
        QuantityField = new JTextField();
        providercb = new JComboBox();
        typeComboBox = new JComboBox();

        // ProductID.setPreferredSize(new Dimension(150, 20));
        ProductNameField.setPreferredSize(new Dimension(150, 20));
        ProductPriceField.setPreferredSize(new Dimension(150, 20));
        QuantityField.setPreferredSize(new Dimension(150, 20));
        providercb.setPreferredSize(new Dimension(150, 20));
        typeComboBox.setPreferredSize(new Dimension(150, 20));

        ProductNameField.setText(productsTable.getValueAt(productsTable.getSelectedRow(), 1).toString());
        ProductPriceField.setText(productsTable.getValueAt(productsTable.getSelectedRow(), 2).toString());
        QuantityField.setText(productsTable.getValueAt(productsTable.getSelectedRow(), 3).toString());
        
        providerBUS proBUS = new providerBUS();
        Vector<providerDTO> proList = proBUS.getAllProviders();
        for (providerDTO p : proList) {
            providercb.addItem(p.getProviderName());
        }
        providercb.setSelectedItem(new providerBUS().getProviderNameById(productsTable.getValueAt(productsTable.getSelectedRow(), 5).toString()));

        JButton confirm = new JButton("Create");
        confirm.setFocusPainted(false);
        confirm.setBorderPainted(false);
        confirm.setBackground(Color.BLACK);
        confirm.setForeground(Color.WHITE);
        confirm.setFont(new Font("Segoe UI", Font.BOLD, 14));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(0, 10, 10, 10);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;
        updateDialog.add(productName, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.EAST;
        updateDialog.add(ProductNameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;
        updateDialog.add(productPrice, gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;
        updateDialog.add(ProductPriceField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;
        updateDialog.add(productQuantity, gbc);

        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;
        updateDialog.add(QuantityField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;
        updateDialog.add(provider, gbc);

        gbc.gridx = 1;
        gbc.gridy = 5;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;
        updateDialog.add(providercb, gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;
        updateDialog.add(productType, gbc);

        gbc.gridx = 1;
        gbc.gridy = 6;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;
        updateDialog.add(typeComboBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;
        updateDialog.add(confirm, gbc);

        updateDialog.setVisible(true);

        confirm.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (
                        ProductNameField.getText().isEmpty() ||
                        ProductPriceField.getText().isEmpty() ||
                        QuantityField.getText().isEmpty()
                    ) {
                        throw new Exception("Field(s) cannot be empty");
                    }
                    
                    if (!ProductNameField.getText().matches("[\\p{L} ]+")) {
                        throw new Exception("Name must only contain letters and spaces");
                    }
                    
                    if (Integer.parseInt(ProductPriceField.getText()) < 0) {
                        throw new Exception("Price cannot be negative");
                    }

                    if (isInteger(ProductPriceField.getText()) == false) {
                        throw new Exception("Price must be a number");
                    }

                    if (Integer.parseInt(QuantityField.getText()) < 0) {
                        throw new Exception("QuantityField cannot be negative");
                    }

                    productDTO product = new productDTO();
                    product.setProductID(productsTable.getValueAt(productsTable.getSelectedRow(), 0).toString());
                    product.setProductName(ProductNameField.getText().toString());
                    product.setProductPrice(Integer.parseInt(ProductPriceField.getText().toString()));
                    product.setQuantity(Integer.parseInt(QuantityField.getText().toString()));
                    product.setProviderID(new providerBUS().getProviderIdByName(providercb.getSelectedItem().toString()));
                    product.setProductType(typeComboBox.getSelectedItem().toString());

                    productBUS productBUS = new productBUS();
                    productBUS.updateProduct(product);

                    updateTable();
                    mainPanel.revalidate();
                    mainPanel.repaint();
                    
                    customSuccessDialog(updateDialog);
                    updateDialog.dispose();
                } catch (Exception ex) {
                    customErrorDialog(ex.getMessage(), updateDialog);
                }
            }
        });
    }

    public void exportCreate() {
        if (exportDetail != null && exportDetail.isShowing()) {
            exportDetail.dispose();
        }
    
        exportDetail = new JDialog();
        exportDetail.setSize(900, 800);
        exportDetail.setLocationRelativeTo(mainPanel);
        exportDetail.setTitle("Export Product");
        exportDetail.setLayout(new GridBagLayout());
    
        JLabel title = new JLabel("Export Product");
        title.setFont(new Font("Georgia", Font.BOLD, 17));
    
        DefaultTableModel tableModel = new DefaultTableModel(new String[]{"Name", "Quantity", "Customer"}, 0);
        JTable exportTable = new JTable(tableModel);
        exportTable.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        exportTable.setRowHeight(25);
        exportTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 15));
        exportTable.getTableHeader().setBackground(new Color(72, 166, 167));
        exportTable.getTableHeader().setForeground(Color.WHITE);
    
        JScrollPane tableScrollPane = new JScrollPane(exportTable);
        tableScrollPane.setPreferredSize(new Dimension(500, 150));
    
        JLabel productNameLabel = new JLabel("Product Name: ");
        JLabel productQuantityLabel = new JLabel("Quantity: ");
        JLabel customerLabel = new JLabel("Customer: ");
    
        productNameLabel.setFont(new Font("Georgia", Font.BOLD, 14));
        productQuantityLabel.setFont(new Font("Georgia", Font.BOLD, 14));
        customerLabel.setFont(new Font("Georgia", Font.BOLD, 14));
    
        JComboBox productNamecb = new JComboBox();
        JTextField productQuanField = new JTextField();
        JComboBox customercb = new JComboBox();
    
        productNamecb.setPreferredSize(new Dimension(150, 20));
        productQuanField.setPreferredSize(new Dimension(150, 20));
        customercb.setPreferredSize(new Dimension(150, 20));
    
        JButton addMore = new JButton("Add More");
        JButton delete = new JButton("Delete");
        JButton confirm = new JButton("Done");
        JButton cancel = new JButton("Cancel");
    
        addMore.setFocusPainted(false);
        addMore.setBorderPainted(false);
        addMore.setBackground(new Color(6, 208, 1));
        addMore.setForeground(Color.WHITE);
        addMore.setFont(new Font("Segoe UI", Font.BOLD, 14));
    
        delete.setFocusPainted(false);
        delete.setBorderPainted(false);
        delete.setBackground(new Color(247, 55, 79));
        delete.setForeground(Color.WHITE);
        delete.setFont(new Font("Segoe UI", Font.BOLD, 14));
    
        confirm.setFocusPainted(false);
        confirm.setBorderPainted(false);
        confirm.setBackground(Color.BLACK);
        confirm.setForeground(Color.WHITE);
        confirm.setFont(new Font("Segoe UI", Font.BOLD, 14));
    
        cancel.setFocusPainted(false);
        cancel.setBorderPainted(false);
        cancel.setBackground(new Color(169, 169, 169));
        cancel.setForeground(Color.WHITE);
        cancel.setFont(new Font("Segoe UI", Font.BOLD, 14));
    
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
    
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        exportDetail.add(title, gbc);
    
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;
        exportDetail.add(productNameLabel, gbc);
    
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.EAST;
        exportDetail.add(productNamecb, gbc);
    
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.WEST;
        exportDetail.add(productQuantityLabel, gbc);
    
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.EAST;
        exportDetail.add(productQuanField, gbc);
    
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.WEST;
        exportDetail.add(customerLabel, gbc);
    
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.EAST;
        exportDetail.add(customercb, gbc);
    
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        exportDetail.add(addMore, gbc);
    
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        exportDetail.add(delete, gbc);
    
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        exportDetail.add(confirm, gbc);
    
        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        exportDetail.add(cancel, gbc);
    
        gbc.gridx = 0;
        gbc.gridy = 8;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.BOTH;
        exportDetail.add(tableScrollPane, gbc);

        productBUS proBUS = new productBUS();
        Vector<productDTO> proList = proBUS.getProductList();
        for (productDTO p : proList) {
            productNamecb.addItem(p.getProductName());
        }

        customerBUS cusBUS = new customerBUS();
        Vector<customerDTO> cusList = cusBUS.getCustomerList();
        for (customerDTO p : cusList) {
            customercb.addItem(p.getName());
        }

        exportDetail.setVisible(true);
        Vector<productDTO> productToAdd = new Vector<productDTO>();
        HashMap<String, String> productMap = new HashMap<>();
        delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = exportTable.getSelectedRow();
                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(exportDetail, "Please select a row to delete.", "No Selection", JOptionPane.WARNING_MESSAGE);
                    return;
                }
        
                productDTO productToRemove = productToAdd.get(selectedRow);
                productToAdd.remove(selectedRow);
        
                productMap.remove(productToRemove.getProductID());
        
                tableModel.removeRow(selectedRow);
            }
        });
        cancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                exportDetail.dispose();
            }
        });
        addMore.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if(productQuanField.getText().isEmpty()){
                        throw new Exception("Field(s) cannot be empty");
                    } else if(Integer.parseInt(productQuanField.getText())<0){
                        throw new Exception("Quantity cannot be negative");
                    }
                    else if(Integer.parseInt(productQuanField.getText())>new productBUS().getProductQuantityById(new productBUS().getProductIdByName(productNamecb.getSelectedItem().toString()))){
                        throw new Exception("Quantity is too big");
                    } 
                    else {
                        productDTO product = new productDTO();
                        int currentRow = productNamecb.getSelectedIndex();
                        product.setProductID(productsTable.getValueAt(currentRow, 0).toString());
                        product.setProductName(productNamecb.getSelectedItem().toString());
                        product.setQuantity(Integer.parseInt(productQuanField.getText()));
                        product.setProductPrice(Integer.parseInt(productsTable.getValueAt(currentRow, 2).toString()));
                        productToAdd.add(product);
                        String customerID = new com.microsoft.sqlserver.BUS.customerBUS().getCustomerIdByName(customercb.getSelectedItem().toString());
                        
                        tableModel.addRow(new Object[]{
                            product.getProductName(),
                            product.getQuantity(),
                            customercb.getSelectedItem().toString()
                        });
                        productMap.put(product.getProductID(), customerID);

                        customSuccessDialog(exportDetail);
                        productQuanField.setText("");
                    }
                } catch (Exception ex) {
                    customErrorDialog(ex.getMessage(), exportDetail);
                    productQuanField.setText("");
                }
                
            }
        });
        confirm.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (productToAdd.size() > 0) {
                    HashMap<String, Vector<productDTO>> customerProductsMap = new HashMap<>();
                    for (productDTO product : productToAdd) {
                        String customerID = productMap.get(product.getProductID());
                        customerProductsMap.putIfAbsent(customerID, new Vector<>());
                        customerProductsMap.get(customerID).add(product);
                    }
                    customSuccessDialog(exportDetail);
                    productBUS proBUS = new productBUS();
                    proBUS.exportProduct(customerProductsMap);
                    exportDetail.dispose();
                    model.setRowCount(0);
                    updateTable();
                } else {
                    customErrorDialog("You have not added any product yet", exportDetail);
                    exportDetail.dispose();
                }
            }
        });
        cancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                exportDetail.dispose();
            }
        });
    }

    public void buy() {
        JDialog buyDialog = new JDialog();
        if (buyDialog != null && buyDialog.isShowing()) {
            buyDialog.dispose();
        }
        buyDialog.setSize(900, 800);
        buyDialog.setLocationRelativeTo(mainPanel);
        buyDialog.setTitle("Buy Product");
        buyDialog.setLayout(new GridBagLayout());
    
        JLabel title = new JLabel("Buy Product");
        title.setFont(new Font("Georgia", Font.BOLD, 17));
    
        DefaultTableModel tableModel = new DefaultTableModel(new String[]{"Name", "Quantity"}, 0);
        JTable buyTable = new JTable(tableModel);
        buyTable.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        buyTable.setRowHeight(25);
        buyTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 15));
        buyTable.getTableHeader().setBackground(new Color(72, 166, 167));
        buyTable.getTableHeader().setForeground(Color.WHITE);
    
        JScrollPane tableScrollPane = new JScrollPane(buyTable);
        tableScrollPane.setPreferredSize(new Dimension(500, 150));
    
        JLabel productNameLabel = new JLabel("Product Name: ");
        JLabel productQuantityLabel = new JLabel("Quantity: ");
    
        productNameLabel.setFont(new Font("Georgia", Font.BOLD, 14));
        productQuantityLabel.setFont(new Font("Georgia", Font.BOLD, 14));
    
        JComboBox productNamecb = new JComboBox();
        JTextField productQuanField = new JTextField();
    
        productNamecb.setPreferredSize(new Dimension(150, 20));
        productQuanField.setPreferredSize(new Dimension(150, 20));
    
        JButton addMore = new JButton("Add More");
        JButton delete = new JButton("Delete");
        JButton confirm = new JButton("Done");
        JButton cancel = new JButton("Cancel");
    
        addMore.setFocusPainted(false);
        addMore.setBorderPainted(false);
        addMore.setBackground(new Color(6, 208, 1));
        addMore.setForeground(Color.WHITE);
        addMore.setFont(new Font("Segoe UI", Font.BOLD, 14));
    
        delete.setFocusPainted(false);
        delete.setBorderPainted(false);
        delete.setBackground(new Color(247, 55, 79));
        delete.setForeground(Color.WHITE);
        delete.setFont(new Font("Segoe UI", Font.BOLD, 14));
    
        confirm.setFocusPainted(false);
        confirm.setBorderPainted(false);
        confirm.setBackground(Color.BLACK);
        confirm.setForeground(Color.WHITE);
        confirm.setFont(new Font("Segoe UI", Font.BOLD, 14));
    
        cancel.setFocusPainted(false);
        cancel.setBorderPainted(false);
        cancel.setBackground(new Color(169, 169, 169));
        cancel.setForeground(Color.WHITE);
        cancel.setFont(new Font("Segoe UI", Font.BOLD, 14));
    
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
    
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        buyDialog.add(title, gbc);
    
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;
        buyDialog.add(productNameLabel, gbc);
    
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.EAST;
        buyDialog.add(productNamecb, gbc);
    
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.WEST;
        buyDialog.add(productQuantityLabel, gbc);
    
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.EAST;
        buyDialog.add(productQuanField, gbc);
    
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        buyDialog.add(addMore, gbc);
    
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        buyDialog.add(delete, gbc);
    
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        buyDialog.add(confirm, gbc);
    
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        buyDialog.add(cancel, gbc);
    
        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.BOTH;
        buyDialog.add(tableScrollPane, gbc);
    
        Vector<productDTO> productToAdd = new Vector<>();
        productBUS proBUS = new productBUS();
        Vector<productDTO> proList = proBUS.getProductList();
        for (productDTO p : proList) {
            productNamecb.addItem(p.getProductName());
        }
    
        addMore.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (productQuanField.getText().isEmpty()) {
                        throw new Exception("Field(s) cannot be empty");
                    } else if (Integer.parseInt(productQuanField.getText()) < 0) {
                        throw new Exception("Quantity cannot be negative");
                    } else if (Integer.parseInt(productQuanField.getText()) > proBUS.getProductQuantityById(proBUS.getProductIdByName(productNamecb.getSelectedItem().toString()))) {
                        throw new Exception("Quantity is too big");
                    } else {
                        productDTO product = new productDTO();
                        int currentRow = productNamecb.getSelectedIndex();
                        product.setProductID(productsTable.getValueAt(currentRow, 0).toString());
                        product.setProductName(productNamecb.getSelectedItem().toString());
                        product.setQuantity(Integer.parseInt(productQuanField.getText()));
                        product.setProductPrice(Integer.parseInt(productsTable.getValueAt(currentRow, 2).toString()));
                        productToAdd.add(product);
    
                        tableModel.addRow(new Object[]{
                            product.getProductName(),
                            product.getQuantity()
                        });
    
                        productQuanField.setText("");
                    }
                } catch (Exception ex) {
                    customErrorDialog(ex.getMessage(), buyDialog);
                }
            }
        });
    
        delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = buyTable.getSelectedRow();
                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(buyDialog, "Please select a row to delete.", "No Selection", JOptionPane.WARNING_MESSAGE);
                    return;
                }
    
                productToAdd.remove(selectedRow);
                tableModel.removeRow(selectedRow);
            }
        });
    
        confirm.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (productToAdd.size() > 0) {
                    accountDTO currentAccount = new cusAccountBUS().getCurrentAccount();
                    proBUS.buyProduct(productToAdd, currentAccount.getUsername().trim());
                    customSuccessDialog(buyDialog);
                    buyDialog.dispose();
                    model.setRowCount(0);
                    updateTable();
                } else {
                    customErrorDialog("You have not added any product yet", buyDialog);
                }
            }
        });
    
        cancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buyDialog.dispose();
            }
        });
    
        buyDialog.setVisible(true);
    }

    private void updateTable() {
        productBUS productBUS = new productBUS();
        Vector<productDTO> productList = productBUS.getProductList();
        model.setRowCount(0);
        for (int i = 0; i < productList.size(); i++) {
            Vector<Object> row = new Vector<Object>();

            row.add(productList.get(i).getProductID());
            row.add(productList.get(i).getProductName());
            row.add(productList.get(i).getProductPrice());
            row.add(productList.get(i).getQuantity());
            row.add(productList.get(i).getProductType());
            row.add(new providerBUS().getProviderNameById(productList.get(i).getProviderID()));

            model.addRow(row);
        }
        productsTable.setModel(model);
    }

    @SuppressWarnings("Convert2Lambda")
    public void customErrorDialog(String message, JDialog Dialog) {
        JDialog customErrorDialog = new JDialog();
        customErrorDialog.setLayout(new GridBagLayout());
        customErrorDialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        customErrorDialog.setSize(450, 150);
        customErrorDialog.setTitle("Error");
        customErrorDialog.setLocationRelativeTo(Dialog);

        JButton okButton = new JButton("OK");
        okButton.setFocusPainted(false);
        okButton.setBorderPainted(false);
        okButton.setBackground(Color.BLACK);
        okButton.setForeground(Color.WHITE);
        okButton.setFont(new Font("Segoe UI", Font.BOLD, 14));

        JLabel messageLabel = new JLabel(message);
        messageLabel.setFont(new Font("Segoe UI", Font.BOLD, 15));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        customErrorDialog.add(messageLabel);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.NONE;
        customErrorDialog.add(okButton, gbc);

        customErrorDialog.setVisible(true);

        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                customErrorDialog.dispose();
            }
        });
    }

    @SuppressWarnings("Convert2Lambda")
    public void customSuccessDialog(JDialog Dialog) {
        JDialog customSuccessDialog = new JDialog();
        customSuccessDialog.setLayout(new GridBagLayout());
        customSuccessDialog.setSize(175, 150);
        customSuccessDialog.setTitle("Success");
        customSuccessDialog.setLocationRelativeTo(Dialog);

        JLabel messageLabel = new JLabel("Success!");
        messageLabel.setFont(new Font("Segoe UI", Font.BOLD, 15));

        JButton okButton = new JButton("OK");
        okButton.setFocusPainted(false);
        okButton.setBorderPainted(false);
        okButton.setBackground(Color.BLACK);
        okButton.setForeground(Color.WHITE);
        okButton.setFont(new Font("Segoe UI", Font.BOLD, 14));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        customSuccessDialog.add(messageLabel);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.NONE;
        customSuccessDialog.add(okButton, gbc);

        customSuccessDialog.setVisible(true);

        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                customSuccessDialog.dispose();
            }
        });
    }

    private class HoverRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            
            if (row == hoveredRow && !isSelected) {
                c.setBackground(new Color(242, 239, 231));
            } else if (isSelected) {
                c.setBackground(new Color(128, 203, 196));
            } else {
                c.setBackground(table.getBackground());
            }
            return c;
        }
    }
}