package com.microsoft.sqlserver.GUI ;
import javax.swing.* ;
import javax.swing.table.* ;
import com.microsoft.sqlserver.DTO.accountDTO;
import com.microsoft.sqlserver.BUS.accountBUS;
import com.microsoft.sqlserver.BUS.cusAccountBUS;

import java.awt.* ;
import java.awt.event.* ;
import java.util.Vector;


public class accountsTable{
    private mainInt mainInt = new mainInt() ;
    private JPanel mainPanel = mainInt.getMainPanel() ;
    private int hoveredRow = -1 ;
    private JTable accountsTable = new JTable() ;
    private DefaultTableModel model = new DefaultTableModel() ;

    
    private accountBUS accountBUS = new accountBUS() ;
    private accountDTO currentAcc = accountBUS.getCurrentAccount() ;
    private String type = currentAcc.getType() ;

    public void accountsTableInitiate(){    
        JScrollPane accountsScrollPane = new JScrollPane(accountsTable) ;

        accountsTable.setDefaultRenderer(Object.class , new HoverRenderer()) ;

        accountsTable.addMouseMotionListener(new MouseAdapter(){
            @Override
            public void mouseMoved(MouseEvent e){
                Point p = e.getPoint() ;
                int row = accountsTable.rowAtPoint(p) ;

                if(row != hoveredRow){
                    hoveredRow = row ;
                    accountsTable.repaint() ;
                }
            }
        });


        JTableHeader tableHeader =  accountsTable.getTableHeader() ;
        tableHeader.setFont(new Font("Segoe UI" , Font.BOLD , 15)) ;
        tableHeader.setBackground(new Color(72, 166, 167)) ;
        tableHeader.setForeground(Color.WHITE) ;
        
        model.addColumn("ID");
        model.addColumn("Username") ;
        model.addColumn("Password") ;
        model.addColumn("Type") ;

        JPanel searchPanel = new JPanel() ;
        JLabel search = new JLabel("Search: ") ;
        JTextField searchField = new JTextField(15); 
        
        search.setFont(new Font("Segoe UI" , Font.BOLD , 15));
        searchPanel.setBackground(new Color(245, 245, 247));
        searchPanel.add(search) ;
        searchPanel.add(searchField) ;
        if(type.equalsIgnoreCase("staff")){
            searchField.setVisible(false) ; 
            search.setVisible(false) ;
        }

        Dimension size = new Dimension(120 , 30) ;

        JButton Update = new JButton("Update") ;
        Update.setPreferredSize(size) ;
        Update.setMinimumSize(size) ;
        Update.setMaximumSize(size) ;
        Update.setBorderPainted(false) ;
        Update.setFocusPainted(false) ;
        Update.setBackground(new Color(6, 208, 1)) ;
        Update.setForeground(Color.WHITE) ;


        GridBagConstraints mainPanelGBC = new GridBagConstraints() ;
        mainPanelGBC.insets = new Insets(7 , 7 , 7 , 7) ;
        mainPanelGBC.gridx = 0 ;
        mainPanelGBC.gridy = 0 ;
        mainPanel.add(Update , mainPanelGBC) ;

        mainPanelGBC.gridx = 2 ;
        mainPanelGBC.gridy = 0 ;
        mainPanelGBC.weightx = 1 ;
        mainPanelGBC.fill = GridBagConstraints.HORIZONTAL ;
        mainPanel.add(searchPanel , mainPanelGBC) ;
        
        mainPanelGBC.gridwidth = 4 ;
        mainPanelGBC.gridx = 0 ;
        mainPanelGBC.gridy = 1 ;
        mainPanelGBC.weightx = 1 ;
        mainPanelGBC.weighty = 1 ;
        mainPanelGBC.fill = GridBagConstraints.BOTH ;
        mainPanel.add(accountsScrollPane , mainPanelGBC) ;

        if(type.equalsIgnoreCase("manager") || type.equalsIgnoreCase("admin")){
            accountBUS accountBUS = new accountBUS() ;
            Vector<accountDTO> accountList = accountBUS.getAccountList() ;
            for(int i = 0 ; i < accountList.size() ; i++){
                accountDTO account = accountList.get(i) ;
                Vector<Object> row = new Vector<>() ;
                row.add(account.getID()) ;
                row.add(account.getUsername()) ;
                row.add(account.getPassword()) ;
                row.add(account.getType()) ;

                model.addRow(row) ;
            }
        }
        else{
            Vector<Object> row = new Vector<>() ;
            row.add(currentAcc.getID()) ;
            row.add(currentAcc.getUsername()) ;
            row.add(currentAcc.getPassword()) ;
            row.add(type) ;
            model.addRow(row) ;
        }

        accountsTable.setModel(model) ;
        mainPanel.revalidate(); 
        mainPanel.repaint();


        Update.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                if(accountsTable.getSelectedRow() == -1){
                    customErrorDialog("Please select an account" , null) ;
                }
                else{
                    updatePanel() ;
                }
            }
        });

        searchField.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                accountBUS accountBUS = new accountBUS() ;
                String input = searchField.getText() ;

                Vector<accountDTO> accountsList = accountBUS.searchAccount(input) ;
                model.setRowCount(0) ;
                for(int i = 0 ; i < accountsList.size() ; i++){
                    accountDTO account = accountsList.get(i) ;
                    Vector<Object> row = new Vector<>() ;
                    row.add(account.getID()) ;
                    row.add(account.getUsername()) ;
                    row.add(account.getPassword()) ;
                    row.add(account.getType()) ;
    
                    model.addRow(row) ;
                }
                
                
            }
        });
    }

    public void updatePanel(){
        JDialog updateDialog = new JDialog() ;
        updateDialog.setSize(400 , 300) ;
        updateDialog.setLocationRelativeTo(mainPanel) ;
        updateDialog.setTitle("Update Account") ;
        updateDialog.setLayout(new GridBagLayout()) ;


        JLabel title = new JLabel("Update Account") ;
        JLabel password = new JLabel("Password: ") ;
        JLabel accountType = new JLabel("Type: ") ;
        title.setFont(new Font("Georgia" , Font.BOLD , 17)) ;
        password .setFont(new Font("Georgia" , Font.BOLD , 14)) ;
        accountType.setFont(new Font("Georgia" , Font.BOLD , 14)) ;

        String[] combo = {" " , "Manager" , "Staff"} ;
        String[] comboAdmin = {" " , "Manager" , "Staff" , "Admin"} ;
        
        if(type.equalsIgnoreCase("admin")){
            combo = comboAdmin ;
        }
        JComboBox roles = new JComboBox(combo) ;


        roles.setPreferredSize(new Dimension(150 , 25)) ;
        roles.setBackground(Color.WHITE) ;
        roles.setFont(new Font("Segoe UI" , Font.PLAIN , 13)) ;
        roles.setFocusable(false) ;
        if(type.equalsIgnoreCase("staff")){
            roles.setEnabled(false) ;
            roles.setSelectedIndex(2);
        }
        
        JTextField passwordField = new JTextField() ;
        passwordField.setPreferredSize(new Dimension(150 , 20)) ;

        passwordField.setText(accountsTable.getValueAt(accountsTable.getSelectedRow() , 2).toString().trim()) ;
        roles.setSelectedItem(accountsTable.getValueAt(accountsTable.getSelectedRow() , 3).toString().trim()) ;


        JButton confirm = new JButton("Update") ;
        confirm.setFocusPainted(false) ;
        confirm.setBorderPainted(false) ;
        confirm.setBackground(Color.BLACK) ;
        confirm.setForeground(Color.WHITE) ;
        confirm.setFont(new Font("Segoe UI" , Font.BOLD , 14)) ;

        GridBagConstraints gbc = new GridBagConstraints() ;
        gbc.insets = new Insets(0 , 10 , 10 , 10) ;

        gbc.gridx = 0 ;
        gbc.gridy = 0 ;
        gbc.gridwidth = 2 ;
        gbc.anchor = GridBagConstraints.CENTER ;
        updateDialog.add(title , gbc) ;

        gbc.gridx = 0 ;
        gbc.gridy = 1 ;
        gbc.gridwidth = 1 ;
        gbc.anchor = GridBagConstraints.WEST ;
        updateDialog.add(password, gbc) ;

        gbc.gridx = 1 ;
        gbc.gridy = 1 ;
        gbc.gridwidth = 1 ;
        gbc.anchor = GridBagConstraints.EAST ;
        updateDialog.add(passwordField, gbc) ;

        gbc.gridx = 0 ;
        gbc.gridy = 2 ;
        gbc.gridwidth = 1 ;
        gbc.anchor = GridBagConstraints.WEST ;
        updateDialog.add(accountType , gbc) ;

        gbc.gridx = 1 ;
        gbc.gridy = 2 ;
        gbc.gridwidth = 1 ;
        gbc.anchor = GridBagConstraints.EAST ;
        updateDialog.add(roles , gbc) ;

        gbc.gridx = 0 ;
        gbc.gridy = 3 ;
        gbc.gridwidth = 2 ;
        gbc.fill = GridBagConstraints.HORIZONTAL ;
        gbc.anchor = GridBagConstraints.CENTER ;
        updateDialog.add(confirm , gbc) ;

        updateDialog.setVisible(true) ;

        confirm.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                try{
                    if(passwordField.getText().isEmpty() || roles.getSelectedIndex() == 0){
                        throw new Exception("Please fill in all fields") ;
                    }
                    else if(accountsTable.getSelectedRow() == -1){
                        throw new Exception("Please select an account") ;
                    }
                    else if(accountsTable.getValueAt(accountsTable.getSelectedRow() ,3).toString().trim().equals("Manager")){
                        throw new Exception("Cannot update Manager account") ;
                    }

                    accountDTO account = new accountDTO() ;
                    account.setUsername(accountsTable.getValueAt(accountsTable.getSelectedRow() , 1).toString().trim()) ;
                    account.setType(roles.getSelectedItem().toString().trim());
                    account.setPassword(passwordField.getText()) ;

                    accountBUS accountBUS = new accountBUS() ;
                    accountBUS.updateAccount(account) ;

                    if(type.equalsIgnoreCase("Staff")){
                        model.setRowCount(0) ;
                        Vector<Object> row = new Vector<>() ;
                        row.add(currentAcc.getID()) ;
                        row.add(currentAcc.getUsername()) ;
                        row.add(currentAcc.getPassword()) ;
                        row.add(type) ;

                        model.addRow(row) ;
                    }
                    else{
                        Vector<accountDTO> accountsList = accountBUS.getAccountList() ;
                        model.setRowCount(0) ;
                        for(int i = 0 ; i < accountsList.size() ; i++){
                            Vector<Object> row = new Vector<>() ;
                            row.add(accountsList.get(i).getID()) ;
                            row.add(accountsList.get(i).getUsername()) ;
                            row.add(accountsList.get(i).getPassword()) ;
                            row.add(accountsList.get(i).getType()) ;

                            model.addRow(row) ;
                        }
                    }

                    accountsTable.setModel(model) ;
                    mainPanel.revalidate() ;
                    mainPanel.repaint() ;

                    customSuccessDialog(updateDialog) ;
                    updateDialog.dispose() ;
                }
                catch(Exception ex){
                    customErrorDialog(ex.getMessage() , updateDialog) ;
                }
            }
        });
    }

    @SuppressWarnings("Convert2Lambda")
    public void customSuccessDialog(JDialog Dialog){
        JDialog customSuccessDialog = new JDialog() ;
        customSuccessDialog.setLayout(new GridBagLayout()) ;
        customSuccessDialog.setSize(175 , 150) ;
        customSuccessDialog.setTitle("Success") ;
        customSuccessDialog.setLocationRelativeTo(Dialog) ;

        JLabel messageLabel = new JLabel("Success!") ;
        messageLabel.setFont(new Font("Segoe UI" , Font.BOLD , 15)) ;

        JButton okButton = new JButton("OK") ;
        okButton.setFocusPainted(false) ;
        okButton.setBorderPainted(false) ;
        okButton.setBackground(Color.BLACK) ;
        okButton.setForeground(Color.WHITE) ;
        okButton.setFont(new Font("Segoe UI" , Font.BOLD , 14)) ;

        GridBagConstraints gbc = new GridBagConstraints() ;
        gbc.insets = new Insets(10 , 10 , 10 , 10) ;
        gbc.gridx = 0 ;
        gbc.gridy = 0 ;
        gbc.gridwidth = 2 ;
        gbc.anchor = GridBagConstraints.CENTER ;
        gbc.fill = GridBagConstraints.HORIZONTAL ;
        customSuccessDialog.add(messageLabel) ;

        gbc.gridx = 0 ;
        gbc.gridy = 1 ;
        gbc.gridwidth = 1 ;
        gbc.anchor = GridBagConstraints.CENTER ;
        gbc.fill = GridBagConstraints.NONE ;
        customSuccessDialog.add(okButton , gbc) ;

        customSuccessDialog.setVisible(true) ;

        okButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                customSuccessDialog.dispose() ;
            }
        });
    }

    @SuppressWarnings("Convert2Lambda")
    public void customErrorDialog(String message , JDialog Dialog){
        JDialog customErrorDialog = new JDialog() ;
        customErrorDialog.setLayout(new GridBagLayout()) ;
        customErrorDialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE) ;
        customErrorDialog.setSize(450 , 150) ;
        customErrorDialog.setTitle("Error") ;
        customErrorDialog.setLocationRelativeTo(Dialog) ;

        JButton okButton = new JButton("OK") ;
        okButton.setFocusPainted(false) ;
        okButton.setBorderPainted(false) ;
        okButton.setBackground(Color.BLACK) ;
        okButton.setForeground(Color.WHITE) ;
        okButton.setFont(new Font("Segoe UI" , Font.BOLD , 14)) ;

        JLabel messageLabel = new JLabel(message) ;
        messageLabel.setFont(new Font("Segoe UI" , Font.BOLD , 15)) ;

        GridBagConstraints gbc = new GridBagConstraints() ;
        gbc.insets = new Insets(10 , 10 , 10 , 10) ;
        gbc.gridx = 0 ;
        gbc.gridy = 0 ;
        gbc.gridwidth = 2 ;
        gbc.anchor = GridBagConstraints.CENTER ;
        gbc.fill = GridBagConstraints.HORIZONTAL ;
        customErrorDialog.add(messageLabel) ;

        gbc.gridx = 0 ;
        gbc.gridy = 1 ;
        gbc.gridwidth = 1 ;
        gbc.anchor = GridBagConstraints.CENTER ;
        gbc.fill = GridBagConstraints.NONE ;
        customErrorDialog.add(okButton , gbc) ;

        customErrorDialog.setVisible(true) ;

        okButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                customErrorDialog.dispose() ;
            }
        }) ;
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