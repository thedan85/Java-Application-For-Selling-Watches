package com.microsoft.sqlserver.GUI ;
import com.microsoft.sqlserver.DTO.* ;
import com.microsoft.sqlserver.BUS.* ;
import java.util.Vector;
import javax.swing.*;
import javax.swing.table.* ;
import java.awt.* ;
import java.awt.event.* ;


@SuppressWarnings("FieldMayBeFinal")
public class employeeTable {
    private mainInt mainInt = new mainInt() ;
    private JPanel mainPanel = mainInt.getMainPanel() ;
    
    private String combo[] = {" ","Manager" , "Staff"} ;
    private String comboTemp[] = {} ;
    private String comboAdmin[] = {" " , "Manager" , "Staff" , "Admin"} ;
    private JComboBox roles = new JComboBox(combo) ;
    
    private JTable employeesTable = new JTable() ;
    DefaultTableModel model = new DefaultTableModel() ;
    private int hoveredRow = -1 ;
    private String type ;

    public void typeCheck(){
        accountBUS accountBUS = new accountBUS() ;
        type = accountBUS.getCurrentAccount().getType().trim() ;
    }
    
    //employees table initiate
    public void employeesTableInitiate(){
        typeCheck() ;
        employeesTable.setShowGrid(false) ;
        JScrollPane employeesScrollPane = new JScrollPane(employeesTable) ;

        employeesTable.setDefaultRenderer(Object.class , new HoverRenderer()) ;
        employeesTable.addMouseMotionListener(new MouseAdapter(){
            @Override
            public void mouseMoved(MouseEvent e){
                Point p = e.getPoint() ;
                int row = employeesTable.rowAtPoint(p) ;

                if(row != hoveredRow){
                    hoveredRow = row ;
                    employeesTable.repaint() ;
                }
            }
        });

        JTableHeader tableHeader = employeesTable.getTableHeader() ;
        tableHeader.setFont(new Font("Segoe UI" , Font.BOLD , 15)) ;
        tableHeader.setBackground(new Color(72, 166, 167)) ;
        tableHeader.setForeground(Color.WHITE) ;
        

        model.addColumn("ID");
        model.addColumn("Name");
        model.addColumn("Role");
        model.addColumn("Salary");
        model.addColumn("Phone number");

        JButton invisible = new JButton("Invisible") ;

        JPanel searchPanel = new JPanel() ;
        JLabel search = new JLabel("Search: ") ;
        JTextField searchField = new JTextField(15); 
        
        search.setFont(new Font("Segoe UI" , Font.BOLD , 15));
        searchPanel.setBackground(new Color(245, 245, 247));
        searchPanel.add(search) ;
        searchPanel.add(searchField) ;

        Dimension size = new Dimension(90 , 30) ;
        
        JButton add = new JButton("Add") ;
        JButton delete = new JButton("Delete") ;
        JButton update = new JButton("Update") ;
        add.setPreferredSize(size) ;
        delete.setPreferredSize(size) ;
        update.setPreferredSize(size) ;
        invisible.setPreferredSize(size) ;

        add.setMinimumSize(size) ;
        delete.setMinimumSize(size) ;
        update.setMinimumSize(size) ;
        invisible.setMinimumSize(size) ;

        add.setMaximumSize(size) ;
        delete.setMaximumSize(size) ;
        update.setMaximumSize(size) ;
        invisible.setMaximumSize(size) ;

        add.setBorderPainted(false) ;
        delete.setBorderPainted(false) ;
        update.setBorderPainted(false) ;
        invisible.setBorderPainted(false) ;

        add.setFocusPainted(false) ;
        delete.setFocusPainted(false) ;
        update.setFocusPainted(false) ;
        invisible.setFocusPainted(false) ;

        add.setBackground(new Color(6, 208, 1)) ;
        delete.setBackground(new Color(247, 55, 79)) ;
        update.setBackground(new Color(238, 124, 75)) ;
        invisible.setBackground(new Color(245, 245, 247)) ;

        add.setForeground(Color.WHITE) ;
        delete.setForeground(Color.WHITE) ;
        update.setForeground(Color.WHITE) ;
        invisible.setForeground(Color.WHITE) ;

        invisible.setContentAreaFilled(false);

        GridBagConstraints mainPanelGBC = new GridBagConstraints() ;
        mainPanelGBC.insets = new Insets(7 , 7 , 7 , 7) ;
        mainPanelGBC.gridx = 0 ;
        mainPanelGBC.gridy = 0 ;
        mainPanel.add(add , mainPanelGBC) ;

        mainPanelGBC.gridx = 1 ;
        mainPanelGBC.gridy = 0 ;
        mainPanel.add(delete , mainPanelGBC) ;

        mainPanelGBC.gridx = 2 ;
        mainPanelGBC.gridy = 0 ;
        mainPanel.add(update , mainPanelGBC) ;

        mainPanelGBC.gridx = 3 ;
        mainPanelGBC.gridy = 0 ;
        mainPanel.add(invisible , mainPanelGBC) ;

        mainPanelGBC.gridx = 4 ;
        mainPanelGBC.gridy = 0 ; 
        mainPanelGBC.weightx = 1 ;  
        mainPanelGBC.fill = GridBagConstraints.HORIZONTAL; 
        mainPanel.add(searchPanel, mainPanelGBC);

        mainPanelGBC.gridwidth = 6 ;

        mainPanelGBC.fill = GridBagConstraints.BOTH ;
        mainPanelGBC.weightx = 1 ;
        mainPanelGBC.weighty = 1 ;
        mainPanelGBC.gridx = 0 ;
        mainPanelGBC.gridy = 1 ;
        mainPanel.add(employeesScrollPane , mainPanelGBC) ;

        employeeBUS employeeBUS = new employeeBUS() ;
        Vector<employeeDTO> employeeList = employeeBUS.getEmployeeList() ;
        for(int i = 0 ; i < employeeList.size() ; i++){
            Vector<Object> row = new Vector<Object>() ;
            row.add(employeeList.get(i).getID()) ;
            row.add(employeeList.get(i).getName()) ;
            row.add(employeeList.get(i).getRole()) ;
            row.add(employeeList.get(i).getSalary()) ;
            row.add(employeeList.get(i).getPhoneNumber()) ;

            model.addRow(row) ;
        }
        

        employeesTable.setModel(model) ;
        mainPanel.revalidate(); 
        mainPanel.repaint();

        add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                addPanel() ;
            }
        });

        delete.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                accountBUS accountBUS = new accountBUS() ;
                accountDTO currentAcc = accountBUS.getCurrentAccount() ;
                
                receiptBUS receiptBUS = new receiptBUS() ;
                Vector<receiptDTO> receiptsList = receiptBUS.getAllReceipt() ;

                if(employeesTable.getSelectedRow() == -1){
                    customErrorDialog("Please select a row to delete" , null) ;
                }
                
                if(employeesTable.getValueAt(employeesTable.getSelectedRow(), 2).toString().trim().equalsIgnoreCase("manager") && type.trim().equalsIgnoreCase("manager")){
                    customErrorDialog("You cannot delete a manager" , null) ;
                }
                else if(employeesTable.getValueAt(employeesTable.getSelectedRow(), 2).toString().trim().equalsIgnoreCase("admin")){
                    customErrorDialog("You cannot delete an admin" , null) ;
                }
                else if(employeesTable.getValueAt(employeesTable.getSelectedRow() , 0).toString().trim().equals(currentAcc.getUsername().trim())){
                    customErrorDialog("You cannot delete yourself", null) ;
                }
                else{                    
                    for(int i = 0 ; i < receiptsList.size() ; i++){
                        if(employeesTable.getValueAt(employeesTable.getSelectedRow() , 0).toString().trim().equals(receiptsList.get(i).getEmployeeID().trim())){
                            customErrorDialog("Invalid action", null);
                            return;
                        }
                    }
    
                    deletePanel() ;
                }
            }
        });

        update.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                accountBUS accountBUS = new accountBUS() ;

                if(employeesTable.getSelectedRow() == -1){
                    customErrorDialog("Please select a row to update" , null) ;
                }

                if(((employeesTable.getValueAt(employeesTable.getSelectedRow(), 2)).toString()).trim().equalsIgnoreCase("manager") 
                    && type.equalsIgnoreCase("manager")
                    && !employeesTable.getValueAt(employeesTable.getSelectedRow(), 0).toString().trim().equals(accountBUS.getCurrentAccount().getUsername().trim())){
                    customErrorDialog("You cannot update a manager's information" , null) ;
                    System.out.println(accountBUS.getCurrentAccount().getUsername().trim()) ;
                }
                else if(employeesTable.getValueAt(employeesTable.getSelectedRow(), 2).toString().trim().equalsIgnoreCase("admin") && type.equalsIgnoreCase("manager")){
                    customErrorDialog("You cannot update an admin's information" , null) ;
                }
                else{
                    updatePanel() ;
                }
            }
        });

        searchField.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                String input = searchField.getText() ;
                
                employeeBUS employeeBUS = new employeeBUS() ;
                Vector<employeeDTO> employeeList = employeeBUS.searchEmployee(input) ;

                model.setRowCount(0) ;
                for(int i = 0 ; i < employeeList.size() ; i++){
                    Vector<Object> row = new Vector<Object>() ;
                    row.add(employeeList.get(i).getID()) ;
                    row.add(employeeList.get(i).getName()) ;
                    row.add(employeeList.get(i).getRole()) ;
                    row.add(employeeList.get(i).getSalary()) ;
                    row.add(employeeList.get(i).getPhoneNumber()) ;

                    model.addRow(row) ;
                }
                employeesTable.setModel(model) ;
                mainPanel.revalidate();
                mainPanel.repaint();
            }
        });
    }
    //end of employees table initiate

    @SuppressWarnings("Convert2Lambda")
    public void addPanel(){
        JDialog addDialog = new JDialog() ;
        addDialog.setSize(400 , 300) ;
        addDialog.setLocationRelativeTo(mainPanel) ;
        addDialog.setTitle("Add Employee") ;
        addDialog.setLayout(new GridBagLayout()) ;

        JLabel title = new JLabel("Add Employee") ;
        JLabel name = new JLabel("Name: ") ;
        JLabel role = new JLabel("Role: ") ;
        JLabel salary = new JLabel("Salary: ") ;
        JLabel phoneNumber = new JLabel("Phone Number: ") ;
        title.setFont(new Font("Georgia" , Font.BOLD , 17)) ;
        name.setFont(new Font("Georgia" , Font.BOLD , 14)) ;
        role.setFont(new Font("Georgia" , Font.BOLD , 14)) ;
        salary.setFont(new Font("Georgia" , Font.BOLD , 14)) ;
        phoneNumber.setFont(new Font("Georgia" , Font.BOLD , 14)) ;

        roles.setPreferredSize(new Dimension(150 , 25)) ;
        roles.setBackground(Color.WHITE) ;
        roles.setFont(new Font("Segoe UI" , Font.PLAIN , 13)) ;
        roles.setFocusable(false) ;

        JTextField nameField = new JTextField() ;
        JTextField salaryField = new JTextField() ;
        JTextField phoneNumberField = new JTextField() ;
        nameField.setPreferredSize(new Dimension(150 , 25)) ;
        salaryField.setPreferredSize(new Dimension(150 , 25)) ;
        phoneNumberField.setPreferredSize(new Dimension(150 , 25)) ;

        JButton confirm = new JButton("Add") ;
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
        gbc.anchor = GridBagConstraints.NORTH ;
        gbc.weighty = 0 ;
        addDialog.add(title , gbc) ;

        gbc.gridx = 0 ;
        gbc.gridy = 1 ;
        gbc.gridwidth = 1 ;
        gbc.anchor = GridBagConstraints.WEST ;
        addDialog.add(name , gbc) ;

        gbc.gridx = 1 ;
        gbc.gridy = 1 ;
        gbc.gridwidth = 1 ;
        gbc.anchor = GridBagConstraints.EAST ;
        addDialog.add(nameField , gbc) ;

        gbc.gridx = 0 ;
        gbc.gridy = 2 ;
        gbc.gridwidth = 1 ;
        gbc.anchor = GridBagConstraints.WEST ;
        addDialog.add(role , gbc) ;

        gbc.gridx = 1 ;
        gbc.gridy = 2 ;
        gbc.gridwidth = 1 ;
        gbc.anchor = GridBagConstraints.EAST ;
        addDialog.add(roles , gbc) ;

        gbc.gridx = 0 ;
        gbc.gridy = 3 ;
        gbc.gridwidth = 1 ;
        gbc.anchor = GridBagConstraints.WEST ;
        addDialog.add(salary , gbc) ;

        gbc.gridx = 1 ;
        gbc.gridy = 3 ;
        gbc.gridwidth = 1 ;
        gbc.anchor = GridBagConstraints.EAST ;
        addDialog.add(salaryField , gbc) ;

        gbc.gridx = 0 ;
        gbc.gridy = 4 ;
        gbc.gridwidth = 1 ;
        gbc.anchor = GridBagConstraints.WEST ;
        addDialog.add(phoneNumber , gbc) ;

        gbc.gridx = 1 ;
        gbc.gridy = 4 ;
        gbc.gridwidth = 1 ;
        gbc.anchor = GridBagConstraints.EAST ;
        addDialog.add(phoneNumberField , gbc) ;

        gbc.gridx = 0 ;
        gbc.gridy = 5 ;
        gbc.gridwidth = 2 ;
        gbc.fill = GridBagConstraints.HORIZONTAL ;
        gbc.anchor = GridBagConstraints.CENTER ;
        addDialog.add(confirm , gbc) ;

        addDialog.setVisible(true) ;

        confirm.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                try{
                    if(
                        nameField.getText().isEmpty() ||
                        salaryField.getText().isEmpty() ||
                        phoneNumberField.getText().isEmpty()
                    ){
                        throw new Exception("Field(s) cannot be empty") ;
                    }
                    
                    if(!nameField.getText().matches("[a-zA-Z ]+")) {
                        throw new Exception("Name must only contain letters and spaces");
                    }
                    
                    if(Integer.parseInt(salaryField.getText()) < 0){
                        throw new Exception("Salary cannot be negative") ;
                    }

                    if(isInteger(salaryField.getText()) == false){
                        throw new Exception("Salary must be a number") ;
                    }
                    
                    if(!phoneNumberField.getText().matches("^0\\d{9}$")){
                        throw new Exception("Phone number must be 10 digits long and start with 0") ;
                    }

                    employeeDTO employee = new employeeDTO() ;
                    employee.setName(nameField.getText()) ;
                    employee.setRole(roles.getSelectedItem().toString()) ;
                    employee.setSalary(Integer.parseInt(salaryField.getText())) ;
                    employee.setPhoneNumber(phoneNumberField.getText()) ;

                    employeeBUS employeeBUS = new employeeBUS() ;
                    employeeBUS.addEmployee(employee) ;

                    Vector<employeeDTO> employeeList = employeeBUS.getEmployeeList() ;
                    model.setRowCount(0) ;
                    for(int i = 0 ; i < employeeList.size() ; i++){
                        Vector<Object> row = new Vector<Object>() ;
                        row.add(employeeList.get(i).getID()) ;
                        row.add(employeeList.get(i).getName()) ;
                        row.add(employeeList.get(i).getRole()) ;
                        row.add(employeeList.get(i).getSalary()) ;
                        row.add(employeeList.get(i).getPhoneNumber()) ;

                        model.addRow(row) ;
                    }

                    employee = employeeList.get(employeeList.size() - 1) ;
                    accountDTO account = new accountDTO() ;
                    account.setUsername(employee.getID()) ;
                    account.setPassword("123") ;
                    account.setType(employee.getRole()) ; 

                    accountBUS accountBUS = new accountBUS() ;
                    accountBUS.addAccount(account) ;

                    employeesTable.setModel(model) ;
                    mainPanel.revalidate(); 
                    mainPanel.repaint();
                    
                    customSuccessDialog(addDialog) ;
                    addDialog.dispose() ;
                }

                catch(Exception ex){
                    customErrorDialog(ex.getMessage() , addDialog) ;
                    nameField.setText("") ;
                    roles.setSelectedIndex(0) ;
                    salaryField.setText("") ;
                    phoneNumberField.setText("") ;
                }
            }
        });       
    }

    @SuppressWarnings("UnnecessaryTemporaryOnConversionFromString")
    private boolean isInteger(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } 
        catch (NumberFormatException e) {
            return false;
        }
    }

    //@SuppressWarnings("Convert2Lambda")
    public void deletePanel(){
        JDialog deleteDialog = new JDialog() ;
        deleteDialog.setTitle("Delete Employee") ;
        deleteDialog.setSize(250 , 200) ;
        deleteDialog.setLayout(new GridBagLayout()) ;
        deleteDialog.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE) ;
        deleteDialog.setLocationRelativeTo(mainPanel) ;

        JLabel message = new JLabel("Do you want to delete?") ;
        message.setFont(new Font("Segoe UI" , Font.BOLD , 15)) ;

        JButton confirmButton = new JButton("Yes") ;
        JButton cancelButton = new JButton("Cancel") ;
        
        confirmButton.setFocusPainted(false) ;
        confirmButton.setBorderPainted(false) ;
        confirmButton.setBackground(Color.green) ;
        confirmButton.setForeground(Color.WHITE) ;
        confirmButton.setPreferredSize(new Dimension(80 , 25));

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
        deleteDialog.add(confirmButton , gbc) ;

        gbc.gridx = 1 ;
        gbc.gridy = 1 ;
        gbc.fill = GridBagConstraints.NONE ;
        gbc.gridwidth = 1 ;
        deleteDialog.add(cancelButton , gbc) ;


        deleteDialog.setVisible(true) ;

        confirmButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                int selectedRow = employeesTable.getSelectedRow() ;
                
                employeeDTO employee = new employeeDTO() ;
                employee.setID(employeesTable.getValueAt(selectedRow , 0).toString()) ;

                accountDTO account = new accountDTO() ;
                account.setUsername(employee.getID());
                accountBUS accountBUS = new accountBUS() ;
                accountBUS.deleteAccount(account) ;

                employeeBUS employeeBUS = new employeeBUS() ;
                employeeBUS.deleteEmployee(employee) ;

                customSuccessDialog(deleteDialog) ;
                deleteDialog.dispose() ;

                model.setRowCount(0) ;
                Vector<employeeDTO> employeeList = employeeBUS.getEmployeeList() ;
                for(int i = 0 ; i < employeeList.size() ; i++){
                    Vector<Object> row = new Vector<Object>() ;
                    row.add(employeeList.get(i).getID()) ;
                    row.add(employeeList.get(i).getName()) ;
                    row.add(employeeList.get(i).getRole()) ;
                    row.add(employeeList.get(i).getSalary()) ;
                    row.add(employeeList.get(i).getPhoneNumber()) ;
        
                    model.addRow(row) ;
                }
                
                employeesTable.setModel(model) ;
                mainPanel.revalidate(); 
                mainPanel.repaint();
                
            }
        });

        cancelButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                deleteDialog.dispose() ;
            }
        });
    }

    public void updatePanel(){
        JDialog updateDialog = new JDialog() ;
        updateDialog.setSize(400 , 300) ;
        updateDialog.setLocationRelativeTo(mainPanel) ;
        updateDialog.setTitle("Update Employee") ;
        updateDialog.setLayout(new GridBagLayout()) ;


        JLabel title = new JLabel("Update Employee") ;
        JLabel name = new JLabel("Name: ") ;
        JLabel role = new JLabel("Role: ") ;
        JLabel salary = new JLabel("Salary: ") ;
        JLabel phoneNumber = new JLabel("Phone Number: ") ;
        title.setFont(new Font("Georgia" , Font.BOLD , 17)) ;
        name.setFont(new Font("Georgia" , Font.BOLD , 14)) ;
        role.setFont(new Font("Georgia" , Font.BOLD , 14)) ;
        salary.setFont(new Font("Georgia" , Font.BOLD , 14)) ;
        phoneNumber.setFont(new Font("Georgia" , Font.BOLD , 14)) ;

        JComboBox roles = new JComboBox(combo) ;
        roles.setPreferredSize(new Dimension(150 , 25)) ;
        roles.setBackground(Color.WHITE) ;
        roles.setFont(new Font("Segoe UI" , Font.PLAIN , 13)) ;
        roles.setFocusable(false) ;

        JTextField nameField = new JTextField() ;
        JTextField salaryField = new JTextField() ;
        JTextField phoneNumberField = new JTextField() ;
        nameField.setPreferredSize(new Dimension(150 , 20)) ;
        salaryField.setPreferredSize(new Dimension(150 , 20)) ;
        phoneNumberField.setPreferredSize(new Dimension(150 , 20)) ;

        nameField.setText(employeesTable.getValueAt(employeesTable.getSelectedRow() , 1).toString()) ;
        salaryField.setText(employeesTable.getValueAt(employeesTable.getSelectedRow() , 3).toString()) ;
        phoneNumberField.setText(employeesTable.getValueAt(employeesTable.getSelectedRow() , 4).toString()) ;

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
        updateDialog.add(name , gbc) ;

        gbc.gridx = 1 ;
        gbc.gridy = 1 ;
        gbc.gridwidth = 1 ;
        gbc.anchor = GridBagConstraints.EAST ;
        updateDialog.add(nameField , gbc) ;

        gbc.gridx = 0 ;
        gbc.gridy = 2 ;
        gbc.gridwidth = 1 ;
        gbc.anchor = GridBagConstraints.WEST ;
        updateDialog.add(role , gbc) ;

        gbc.gridx = 1 ;
        gbc.gridy = 2 ;
        gbc.gridwidth = 1 ;
        gbc.anchor = GridBagConstraints.EAST ;
        updateDialog.add(roles , gbc) ;

        gbc.gridx = 0 ;
        gbc.gridy = 3 ;
        gbc.gridwidth = 1 ;
        gbc.anchor = GridBagConstraints.WEST ;
        updateDialog.add(salary , gbc) ;

        gbc.gridx = 1 ;
        gbc.gridy = 3 ;
        gbc.gridwidth = 1 ;
        gbc.anchor = GridBagConstraints.EAST ;
        updateDialog.add(salaryField , gbc) ;

        gbc.gridx = 0 ;
        gbc.gridy = 4 ;
        gbc.gridwidth = 1 ;
        gbc.anchor = GridBagConstraints.WEST ;
        updateDialog.add(phoneNumber , gbc) ;

        gbc.gridx = 1 ;
        gbc.gridy = 4 ;
        gbc.gridwidth = 1 ;
        gbc.anchor = GridBagConstraints.EAST ;
        updateDialog.add(phoneNumberField , gbc) ;

        gbc.gridx = 0 ;
        gbc.gridy = 5 ;
        gbc.gridwidth = 2 ;
        gbc.fill = GridBagConstraints.HORIZONTAL ;
        gbc.anchor = GridBagConstraints.CENTER ;
        updateDialog.add(confirm , gbc) ;

        updateDialog.setVisible(true) ;

        accountBUS accountBUS = new accountBUS() ;
        if(employeesTable.getValueAt(employeesTable.getSelectedRow() , 0).toString().trim().equalsIgnoreCase(accountBUS.getCurrentAccount().getUsername().trim())){
            comboTemp = combo ;
            combo = comboAdmin ;
            for(String item : combo){
                roles.addItem(item) ;
            }

            roles.setEnabled(false) ;
            roles.setSelectedItem(type) ;
            salaryField.setEnabled(false);
            
            combo = comboTemp ;
        }

        confirm.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                try{
                    if(
                        nameField.getText().isEmpty() ||
                        roles.getSelectedIndex() == 0 ||
                        salaryField.getText().isEmpty() ||
                        phoneNumberField.getText().isEmpty()
                    ){
                        throw new Exception("Field(s) cannot be empty") ;
                    }
                    
                    if(!nameField.getText().matches("[a-zA-Z ]+")) {
                        throw new Exception("Name must only contain letters and spaces");
                    }
                    
                    if(Integer.parseInt(salaryField.getText()) < 0){
                        throw new Exception("Salary cannot be negative") ;
                    }

                    if(isInteger(salaryField.getText()) == false){
                        throw new Exception("Salary must be a number") ;
                    }
                    
                    if(!phoneNumberField.getText().matches("^0\\d{9}$")){
                        throw new Exception("Phone number must be 10 digits long and start with 0") ;
                    }

                    employeeDTO employee = new employeeDTO() ;
                    employee.setID(employeesTable.getValueAt(employeesTable.getSelectedRow() , 0).toString()) ;
                    employee.setName(nameField.getText()) ;
                    employee.setRole(roles.getSelectedItem().toString()) ;
                    employee.setSalary(Integer.parseInt(salaryField.getText())) ;
                    employee.setPhoneNumber(phoneNumberField.getText()) ;

                    employeeBUS employeeBUS = new employeeBUS() ;
                    employeeBUS.updateEmployee(employee) ;

                    Vector<employeeDTO> employeeList = employeeBUS.getEmployeeList() ;
                    model.setRowCount(0) ;
                    for(int i = 0 ; i < employeeList.size() ; i++){
                        Vector<Object> row = new Vector<Object>() ;
                        row.add(employeeList.get(i).getID()) ;
                        row.add(employeeList.get(i).getName()) ;
                        row.add(employeeList.get(i).getRole()) ;
                        row.add(employeeList.get(i).getSalary()) ;
                        row.add(employeeList.get(i).getPhoneNumber()) ;

                        model.addRow(row) ;
                    }
                    
                    employeesTable.setModel(model) ;
                    mainPanel.revalidate(); 
                    mainPanel.repaint();
                    

                    customSuccessDialog(updateDialog) ;
                    updateDialog.dispose() ;
                }

                catch(Exception ex){
                    customErrorDialog(ex.getMessage() , updateDialog) ;
                }
            }
        });

    }

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


