package com.microsoft.sqlserver.GUI;
import com.microsoft.sqlserver.BUS.accountBUS;
import com.microsoft.sqlserver.BUS.providerBUS;
import com.microsoft.sqlserver.BUS.receiptBUS;
import com.microsoft.sqlserver.DTO.importDTO;
import com.microsoft.sqlserver.DTO.providerDTO;
import com.microsoft.sqlserver.DTO.receiptDTO;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.*;
import java.awt.event.*;
import java.util.Vector;

public class providersTable {
    mainInt mainInt = new mainInt();
    private JPanel mainPanel = mainInt.getMainPanel();
    private int hoveredRow = -1;

    private JTable providersTable = new JTable();
    DefaultTableModel model = new DefaultTableModel();

    private String type ;

    public void typeCheck(){
        accountBUS accountBUS = new accountBUS() ;
        type = accountBUS.getCurrentAccount().getType().trim() ;
    }

    public void providersTableInitiate(){
        typeCheck() ;
        JScrollPane scrollPane = new JScrollPane(providersTable);
        boolean isEmpty = true ;

        providersTable.addMouseMotionListener(new MouseAdapter(){
            @Override
            public void mouseMoved(MouseEvent e){
                Point p = e.getPoint() ;
                int row = providersTable.rowAtPoint(p) ;

                if(row != hoveredRow){
                    hoveredRow = row ;
                    providersTable.repaint() ;
                }
            }
        });

        JTableHeader tableHeader = providersTable.getTableHeader();
        tableHeader.setFont(new Font("Segoe UI", Font.BOLD, 15));
        tableHeader.setBackground(new Color(72, 166, 167));
        tableHeader.setForeground(Color.WHITE);

        model.addColumn("ID");
        model.addColumn("Name");
        model.addColumn("Phone Number");

        JButton invisible = new JButton("Invisible");

        JPanel searchPanel = new JPanel();
        JLabel search = new JLabel("Search: ");
        JTextField searchField = new JTextField(15);

        search.setFont(new Font("Segoe UI", Font.BOLD, 15));
        searchPanel.setBackground(new Color(245,245,247));
        searchPanel.add(search);
        searchPanel.add(searchField);

        Dimension size = new Dimension(90,30);

        JButton addButton = new JButton("Add");
        JButton deleteButton = new JButton("Delete");
        JButton updateButton = new JButton("Update");
        addButton.setPreferredSize(size);
        deleteButton.setPreferredSize(size);
        updateButton.setPreferredSize(size);
        invisible.setPreferredSize(size);

        addButton.setMinimumSize(size);
        deleteButton.setMinimumSize(size);
        updateButton.setMinimumSize(size);
        invisible.setMinimumSize(size);

        addButton.setMaximumSize(size);
        deleteButton.setMaximumSize(size);
        updateButton.setMaximumSize(size);
        invisible.setMaximumSize(size);

        addButton.setBorderPainted(false);
        deleteButton.setBorderPainted(false);
        updateButton.setBorderPainted(false);
        invisible.setBorderPainted(false);

        addButton.setFocusPainted(false);
        deleteButton.setFocusPainted(false);
        updateButton.setFocusPainted(false);
        invisible.setFocusPainted(false);

        addButton.setBackground(new Color(6,208,1));
        deleteButton.setBackground(new Color(255,0,0));
        updateButton.setBackground(new Color(255, 165, 0));
        invisible.setBackground(new Color(255, 255, 255));

        addButton.setForeground(Color.WHITE);
        deleteButton.setForeground(Color.WHITE);
        updateButton.setForeground(Color.WHITE);
        invisible.setForeground(Color.WHITE);

        invisible.setContentAreaFilled(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(7,7,7,7);
        gbc.gridx = 0;
        gbc.gridy = 0;
        mainPanel.add(addButton,gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        mainPanel.add(deleteButton,gbc);

        gbc.gridx = 2;
        gbc.gridy = 0;
        mainPanel.add(updateButton,gbc);

        gbc.gridx = 3;
        gbc.gridy = 0;
        mainPanel.add(invisible,gbc);

        gbc.gridx = 4;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        mainPanel.add(searchPanel,gbc);

        gbc.gridwidth = 6;

        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.gridx = 0;
        gbc.gridy = 1;
        mainPanel.add(scrollPane,gbc);

        if(isEmpty) updateTable();

        mainPanel.revalidate();
        mainPanel.repaint();

        if(type.equalsIgnoreCase("staff")){
            mainPanel.remove(deleteButton) ;
            mainPanel.remove(addButton) ;
            mainPanel.remove(updateButton) ;
        }

        addButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                addPanel();
            }
        });

        deleteButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                receiptBUS receiptBUS = new receiptBUS() ;
                Vector<importDTO> receiptsList = receiptBUS.viewImport() ;
                if(providersTable.getSelectedRow() == -1){
                    customErrorDialog("Please select a row to delete" , null) ;
                }
                else{
                    for(int i = 0 ; i < receiptsList.size() ; i++){
                        if(providersTable.getValueAt(providersTable.getSelectedRow(),0 ).equals(receiptsList.get(i).getProviderID().trim())){
                            customErrorDialog("Invalid action", null);
                            return;
                        }
                    }
                    deletePanel();
                }
            }
        });

        updateButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                updatePanel();
            }
        });
        searchField.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                String text = searchField.getText().trim();
                if(text.isEmpty()){
                    updateTable();
                    return;
                }
                else
                {
                    providerBUS proBUS = new providerBUS();
                    Vector<providerDTO> providers = proBUS.searchProvider(text);
                    model.setRowCount(0);
                    for(int i=0; i<providers.size();i++){
                        Vector<Object> row = new Vector<Object>();
                        row.add(providers.get(i).getProviderID());
                        row.add(providers.get(i).getProviderName());
                        row.add(providers.get(i).getProviderPhone());
                        model.addRow(row);
                    }
                    providersTable.setModel(model);    
                }
            }
        });
        isEmpty = false;
    }

    public void updateTable()
    {
        providerBUS proBus = new providerBUS() ;
        Vector<providerDTO> providers = proBus.getAllProviders() ;
        model.setRowCount(0) ;
        for(int i=0; i<providers.size();i++){
            Vector<Object> row = new Vector<Object>();
            row.add(providers.get(i).getProviderID());
            row.add(providers.get(i).getProviderName());
            row.add(providers.get(i).getProviderPhone());
            model.addRow(row);
        }
        providersTable.setModel(model);
    }
    public void addPanel(){
        JDialog addDialog = new JDialog();
        addDialog.setTitle("Add Provider");
        addDialog.setSize(300,200);
        addDialog.setLocationRelativeTo(mainPanel);
        addDialog.setLayout(new GridBagLayout());

        JLabel title = new JLabel("Add Provider");
        JLabel name = new JLabel("Name: ");
        JLabel phone = new JLabel("Phone Number: ");
        title.setFont(new Font("Georgia", Font.BOLD, 17));
        name.setFont(new Font("Georgia", Font.BOLD, 15));
        phone.setFont(new Font("Georgia", Font.BOLD, 15));

        JTextField nameField = new JTextField();
        JTextField phoneField = new JTextField();
        nameField.setPreferredSize(new Dimension(100,20));
        phoneField.setPreferredSize(new Dimension(100,20));

        JButton confirm = new JButton("Add");
        confirm.setFocusPainted(false);
        confirm.setBorderPainted(false);
        confirm.setBackground(Color.BLACK);
        confirm.setForeground(Color.WHITE);
        confirm.setFont(new Font("Segoe UI", Font.BOLD, 14));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(0,10,10,10);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.weighty = 0;
        addDialog.add(title, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;
        addDialog.add(name,gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.EAST;
        addDialog.add(nameField,gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.WEST;
        addDialog.add(phone,gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.EAST;
        addDialog.add(phoneField,gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        addDialog.add(confirm,gbc);

        addDialog.setVisible(true);

        confirm.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    String name = nameField.getText();
                    String phone = phoneField.getText();
                    providerBUS providersBUS = new providerBUS();
                    providerDTO provider = new providerDTO();
                    if(name.isEmpty() || phone.isEmpty()){
                        throw new Exception("Field(s) can not be empty!");
                    }
                    if(!name.matches("[a-zA-Z\\s]+")){
                        throw new Exception("Name can only contain letters!");
                    }
                    if(!phone.matches("^0\\d{9}$")){
                        throw new Exception("Phone number must be 10 digits long and start with 0");
                    }
                    provider.setProviderName(name);
                    provider.setProviderPhone(phone);
                    String result = providersBUS.addProvider(provider);
                    if(result.equals("Successfully added provider")){
                        updateTable();
                        customSuccessDialog(result,addDialog);
                        nameField.setText("");
                        phoneField.setText("");
                    }
                    else{
                        throw new Exception(result);
                    }

                }
                catch(Exception ex){
                    customErrorDialog(ex.getMessage(), addDialog);
                    nameField.setText("");
                    phoneField.setText("");
                }
            }
        });
    }

    public void deletePanel()
    {
        JDialog deleteDialog = new JDialog() ;
        deleteDialog.setTitle("Delete Provider") ;
        deleteDialog.setSize(250 , 200) ;
        deleteDialog.setLayout(new GridBagLayout()) ;
        deleteDialog.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE) ;
        deleteDialog.setLocationRelativeTo(mainPanel) ;

        JLabel message = new JLabel("Do you want to delete?") ;
        message.setFont(new Font("Segoe UI" , Font.BOLD , 15)) ;

        JButton yesButton = new JButton("Yes") ;
        JButton cancelButton = new JButton("Cancel") ;
        
        yesButton.setFocusPainted(false) ;
        yesButton.setBorderPainted(false) ;
        yesButton.setBackground(Color.green) ;
        yesButton.setForeground(Color.WHITE) ;
        yesButton.setPreferredSize(new Dimension(80 , 25));

        cancelButton.setFocusPainted(false) ;
        cancelButton.setBorderPainted(false) ;
        cancelButton.setBackground(Color.red) ;
        cancelButton.setForeground(Color.WHITE) ;

        cancelButton.setPreferredSize(new Dimension(80 , 25));

        GridBagConstraints gbc = new GridBagConstraints() ;
        gbc.insets = new Insets(10 , 10 , 20 , 10) ;

        gbc.gridx = 0 ;
        gbc.gridy = 0 ;
        gbc.gridwidth = 2 ;
        gbc.weightx = 1 ;
        gbc.anchor = GridBagConstraints.CENTER ;
        deleteDialog.add(message , gbc) ;

        gbc.gridx = 0 ;
        gbc.gridy = 1 ;
        gbc.fill = GridBagConstraints.NONE ;
        gbc.gridwidth = 1 ;
        deleteDialog.add(yesButton , gbc) ;

        gbc.gridx = 1 ;
        gbc.gridy = 1 ;
        gbc.fill = GridBagConstraints.NONE ;
        gbc.gridwidth = 1 ;
        deleteDialog.add(cancelButton , gbc) ;


        deleteDialog.setVisible(true) ;

        yesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                providerBUS providersBUS = new providerBUS();
                int selectedRow = providersTable.getSelectedRow();
                if (selectedRow != -1) {
                    String providerID = (String) providersTable.getValueAt(selectedRow, 0);
                
                    String result = providersBUS.deleteProvider(providerID);
                    if(result.equals("Successfully deleted provider"))
                    {
                        customSuccessDialog(result,deleteDialog);
                        updateTable();
                        providersTable.setModel(model);
                        deleteDialog.dispose();
                    }
                    else
                    {
                        customErrorDialog(result,deleteDialog);
                    }
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

    public void updatePanel()
    {
        JDialog updateDialog = new JDialog() ;
        int selectedRow = providersTable.getSelectedRow() ;
        if(selectedRow == -1)
        {
            customErrorDialog("You have not choosen any provider to update yet", updateDialog);
            return;
        }

        updateDialog.setTitle("Update Provider") ;
        updateDialog.setSize(300 , 200) ;
        updateDialog.setLayout(new GridBagLayout()) ;
        updateDialog.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE) ;
        updateDialog.setLocationRelativeTo(mainPanel) ;

        JLabel title = new JLabel("Update Provider") ;
        JLabel name = new JLabel("Name: ") ;
        JLabel phone = new JLabel("Phone Number: ") ;
        title.setFont(new Font("Segoe UI" , Font.BOLD , 17)) ;
        name.setFont(new Font("Segoe UI" , Font.BOLD , 15)) ;
        phone.setFont(new Font("Segoe UI" , Font.BOLD , 15)) ;

        JTextField nameField = new JTextField(providersTable.getValueAt(selectedRow, 1).toString()) ;
        JTextField phoneField = new JTextField(providersTable.getValueAt(selectedRow, 2).toString()) ;
        nameField.setPreferredSize(new Dimension(100 , 20)) ;
        phoneField.setPreferredSize(new Dimension(100 , 20)) ;

        JButton confirm = new JButton("Update") ;
        confirm.setFocusPainted(false) ;
        confirm.setBorderPainted(false) ;
        confirm.setBackground(Color.BLACK) ;
        confirm.setForeground(Color.WHITE) ;
        confirm.setFont(new Font("Segoe UI" , Font.BOLD , 14)) ;

        confirm.setPreferredSize(new Dimension(80 , 25)) ;

        GridBagConstraints gbc = new GridBagConstraints() ;
        gbc.insets = new Insets(0 , 10 , 10 , 10) ;
        gbc.gridx = 0 ;
        gbc.gridy = 0 ;
        gbc.gridwidth = 2 ;
        gbc.anchor = GridBagConstraints.CENTER ;
        gbc.weighty = 0 ;
        updateDialog.add(title , gbc) ;

        gbc.gridx = 0 ;
        gbc.gridy = 1 ;
        gbc.gridwidth = 1 ;
        gbc.anchor = GridBagConstraints.WEST ;
        updateDialog.add(name , gbc) ;

        gbc.gridx = 1 ;
        gbc.gridy = 1 ;
        gbc.anchor = GridBagConstraints.EAST ;
        updateDialog.add(nameField , gbc) ;

        gbc.gridx = 0 ;
        gbc.gridy = 2 ;
        gbc.anchor = GridBagConstraints.WEST ;
        updateDialog.add(phone , gbc) ;

        gbc.gridx = 1 ;
        gbc.gridy = 2 ;
        gbc.anchor = GridBagConstraints.EAST ;
        updateDialog.add(phoneField , gbc) ;

        gbc.gridx = 0 ;
        gbc.gridy = 3 ;
        gbc.gridwidth = 2 ;
        gbc.anchor = GridBagConstraints.CENTER ;
        gbc.fill = GridBagConstraints.HORIZONTAL ;
        updateDialog.add(confirm , gbc) ;
        updateDialog.setVisible(true) ;

        confirm.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e)
            {
                try{
                    String name = nameField.getText() ;
                    String phone = phoneField.getText() ;
                    providerBUS providersBUS = new providerBUS() ;
                    providerDTO provider = new providerDTO() ;
                    if(name.isEmpty() || phone.isEmpty()){
                        throw new Exception("Field(s) can not be empty!");
                    }
                    if(!name.matches("[a-zA-Z\\s]+")){
                        throw new Exception("Name can only contain letters!");
                    }
                    if(!phone.matches("^0\\d{9}$")){
                        throw new Exception("Phone number must be 10 digits long and start with 0");
                    }
                    provider.setProviderID((String) providersTable.getValueAt(selectedRow, 0));
                    provider.setProviderName(name);
                    provider.setProviderPhone(phone);
                    String result = providersBUS.updateProvider(provider);
                    if(result.equals("Successfully updated provider")){
                        updateTable();
                        customSuccessDialog(result,updateDialog);
                        nameField.setText("");
                        phoneField.setText("");
                    }
                    else{
                        throw new Exception(result);
                    }

                }
                catch(Exception ex){
                    customErrorDialog(ex.getMessage(), updateDialog);
                    nameField.setText("");
                    phoneField.setText("");
                }
            }
        });


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

}
