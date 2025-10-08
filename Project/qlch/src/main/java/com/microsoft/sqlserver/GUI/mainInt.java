package com.microsoft.sqlserver.GUI;
import com.microsoft.sqlserver.BUS.accountBUS;
import com.microsoft.sqlserver.BUS.cusAccountBUS;
import com.microsoft.sqlserver.DTO.accountDTO;
import java.awt.* ;
import java.awt.event.* ;
import javax.swing.* ;
import javax.swing.border.* ;

@SuppressWarnings("FieldMayBeFinal")
public class mainInt{
    private JFrame mainFrame = new JFrame("Store Managing") ;
    private static JPanel mainPanel = new JPanel() ;
    

    private boolean productsTableInitiated = false ;
    private boolean employeesTableInitiated = false ;
    private boolean ReceiptsTableInitiated = false ;
    private boolean providersTableInitiated = false ;   
    private boolean accountsTableInitiated = false ;
    private boolean customerAccountsTableInitiated = false ;
    private boolean customersTableInitiated = false ;
    private String type ;
    
    //Getters
    public JPanel getMainPanel(){
        return mainPanel ;
    }

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

    //Main Frame Initiate
    private void mainFrameInitiate(){
        typeCheck() ;
        mainPanel.removeAll() ;
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE) ;
        mainFrame.setSize(1500 , 720) ;
        mainFrame.setLocationRelativeTo(null) ;
        mainFrame.setLayout(new GridBagLayout()) ;
        
        buttonsColumnInitiate() ;
        mainPanelInitiate() ;
        
        mainFrame.setVisible(true) ;
    }
    //end of main Frame Initiate



    //Buttons Column Initiate
    @SuppressWarnings("Convert2Lambda")
    private void buttonsColumnInitiate(){
        JPanel buttonsPanel = new JPanel() ;
        buttonsPanel.setLayout(new BoxLayout(buttonsPanel , BoxLayout.Y_AXIS)) ;
        buttonsPanel.setBackground(Color.WHITE) ;
        buttonsPanel.setBorder(new MatteBorder(0 , 0 , 0 , 1 , Color.BLACK));
        
        JButton titleButton = new JButton("Store Managing") ;
        JButton products = new JButton("Products") ;
        JButton employees = new JButton("Employees") ;
        JButton providers = new JButton("Providers") ;
        JButton customers = new JButton("Customers") ;
        JButton Receipts = new JButton("Receipts") ;
        JButton accounts = new JButton("Accounts") ;
        JButton customerAccounts = new JButton("Customer Accounts") ;
        JButton logout = new JButton("Logout") ;

        
        titleButton.setFont(new Font("Georgia" , Font.BOLD , 20)) ;
        products.setFont(new Font("Segoe UI" , Font.BOLD , 16)) ;
        employees.setFont(new Font("Segoe UI" , Font.BOLD , 16)) ;
        providers.setFont(new Font("Segoe UI" , Font.BOLD , 16)) ;
        customers.setFont(new Font("Segoe UI" , Font.BOLD , 16)) ;
        Receipts.setFont(new Font("Segoe UI" , Font.BOLD , 16)) ;
        accounts.setFont(new Font("Segoe UI" , Font.BOLD , 16)) ;
        customerAccounts.setFont(new Font("Segoe UI" , Font.BOLD , 16)) ;
        logout.setFont(new Font("Segoe UI" , Font.BOLD , 16)) ;
    
        titleButton.setMaximumSize(new Dimension(300, 50)) ;
        products.setMaximumSize(new Dimension(300, 50)) ;
        employees.setMaximumSize(new Dimension(300, 50)) ;
        providers.setMaximumSize(new Dimension(300, 50)) ;
        customers.setMaximumSize(new Dimension(300, 50)) ;
        Receipts.setMaximumSize(new Dimension(300, 50)) ;
        accounts.setMaximumSize(new Dimension(300, 50)) ;
        customerAccounts.setMaximumSize(new Dimension(300, 50)) ;
        logout.setMaximumSize(new Dimension(300, 50)) ;

        products.setHorizontalAlignment(JButton.LEFT) ;
        employees.setHorizontalAlignment(JButton.LEFT) ;
        providers.setHorizontalAlignment(JButton.LEFT) ; 
        customers.setHorizontalAlignment(JButton.LEFT) ;
        Receipts.setHorizontalAlignment(JButton.LEFT) ; 
        accounts.setHorizontalAlignment(JButton.LEFT) ;
        customerAccounts.setHorizontalAlignment(JButton.LEFT) ;
        logout.setHorizontalAlignment(JButton.LEFT) ;

        titleButton.setFocusPainted(false) ;
        products.setFocusPainted(false) ;
        employees.setFocusPainted(false) ;
        customers.setFocusPainted(false) ;
        providers.setFocusPainted(false) ;
        Receipts.setFocusPainted(false) ;
        accounts.setFocusPainted(false) ;
        customerAccounts.setFocusPainted(false) ;
        logout.setFocusPainted(false) ;

        titleButton.setBorderPainted(false) ;
        products.setBorderPainted(false) ;
        employees.setBorderPainted(false) ;
        customers.setBorderPainted(false) ;
        providers.setBorderPainted(false) ;
        Receipts.setBorderPainted(false) ;
        accounts.setBorderPainted(false) ;
        customerAccounts.setBorderPainted(false) ;
        logout.setBorderPainted(false) ;

        titleButton.setBackground(Color.WHITE) ;
        products.setBackground(Color.WHITE) ;
        customers.setBackground(Color.WHITE) ;
        employees.setBackground(Color.WHITE) ;
        providers.setBackground(Color.WHITE) ;
        Receipts.setBackground(Color.WHITE) ;
        accounts.setBackground(Color.WHITE) ;
        customerAccounts.setBackground(Color.WHITE) ;
        logout.setBackground(Color.WHITE) ;

        titleButton.setContentAreaFilled(false);

        buttonsPanel.add(titleButton) ;
        buttonsPanel.add(products) ;
        if(type.equalsIgnoreCase("admin") || type.equalsIgnoreCase("manager")){
            buttonsPanel.add(employees) ;
            accounts.setText("Accounts") ;
        }
        buttonsPanel.add(employees) ;
        buttonsPanel.add(providers) ;
        buttonsPanel.add(customers) ;
        buttonsPanel.add(Receipts) ;
        buttonsPanel.add(accounts) ;
        buttonsPanel.add(customerAccounts) ;
        buttonsPanel.add(logout) ;

        if(type.equalsIgnoreCase("staff")){
            buttonsPanel.remove(employees) ;
            accounts.setText("My account") ;
            titleButton.setText("Welcome staff") ;
        }
        if(type.equalsIgnoreCase("customer")){
            buttonsPanel.remove(employees) ;
            buttonsPanel.remove(accounts) ;
            buttonsPanel.remove(customers) ;
            buttonsPanel.remove(providers) ;
            customerAccounts.setText("My account") ;
            titleButton.setText("Welcome customer") ;
        }

        GridBagConstraints gbc = new GridBagConstraints() ;
        gbc.insets = new Insets(0 , 0 , 0 , 0) ;
        gbc.gridx = 0 ;
        gbc.gridy = 0 ;
        gbc.weightx = 0.005 ;
        gbc.weighty = 1.0 ; 
        gbc.anchor = GridBagConstraints.WEST ;
        gbc.fill = GridBagConstraints.BOTH ;

        mainFrame.add(buttonsPanel , gbc) ;

        //mouse effect
        products.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseEntered(MouseEvent e){
                products.setBackground(Color.BLACK) ;
                products.setForeground(Color.WHITE) ;
            }

            @Override
            public void mouseExited(MouseEvent e){
                products.setBackground(Color.WHITE) ;
                products.setForeground(Color.BLACK) ;
            }

        });
        
        employees.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseEntered(MouseEvent e){
                employees.setBackground(Color.BLACK) ;
                employees.setForeground(Color.WHITE) ;
            }

            @Override
            public void mouseExited(MouseEvent e){
                employees.setBackground(Color.WHITE) ;
                employees.setForeground(Color.BLACK) ;
            }
        });

        providers.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseEntered(MouseEvent e){
                providers.setBackground(Color.BLACK) ;
                providers.setForeground(Color.WHITE) ;
            }

            @Override
            public void mouseExited(MouseEvent e){
                providers.setBackground(Color.WHITE) ;
                providers.setForeground(Color.BLACK) ;
                }
        });

        customers.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseEntered(MouseEvent e){
                customers.setBackground(Color.BLACK) ;
                customers.setForeground(Color.WHITE) ;
            }

            @Override
            public void mouseExited(MouseEvent e){
                customers.setBackground(Color.WHITE) ;
                customers.setForeground(Color.BLACK) ;
            }
        });

        Receipts.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseEntered(MouseEvent e){
                Receipts.setBackground(Color.BLACK) ;
                Receipts.setForeground(Color.WHITE) ;
            }

            @Override
            public void mouseExited(MouseEvent e){
                Receipts.setBackground(Color.WHITE) ;
                Receipts.setForeground(Color.BLACK) ;
            }
        });

        accounts.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseEntered(MouseEvent e){
                accounts.setBackground(Color.BLACK) ;
                accounts.setForeground(Color.WHITE) ;
            }

            @Override
            public void mouseExited(MouseEvent e){
                accounts.setBackground(Color.WHITE) ;
                accounts.setForeground(Color.BLACK) ;
            }
        });

        customerAccounts.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseEntered(MouseEvent e){
                customerAccounts.setBackground(Color.BLACK) ;
                customerAccounts.setForeground(Color.WHITE) ;
            }

            @Override
            public void mouseExited(MouseEvent e){
                customerAccounts.setBackground(Color.WHITE) ;
                customerAccounts.setForeground(Color.BLACK) ;
            }
        });

        logout.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseEntered(MouseEvent e){
                logout.setBackground(Color.BLACK) ;
                logout.setForeground(Color.WHITE) ;
            }

            @Override
            public void mouseExited(MouseEvent e){
                logout.setBackground(Color.WHITE) ;
                logout.setForeground(Color.BLACK) ;
            }
        });
        
        //functionality
        products.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                if(productsTableInitiated == false){
                    mainPanel.removeAll();
                    productsTableInitiate() ;
                    productsTableInitiated = true ;
                    employeesTableInitiated = false ;
                    ReceiptsTableInitiated = false ;
                    providersTableInitiated = false ;
                    accountsTableInitiated = false ;
                    customerAccountsTableInitiated = false ;
                    customersTableInitiated = false ;
                }
            }
        });

        employees.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                if(employeesTableInitiated == false){
                    mainPanel.removeAll() ;
                    
                    employeeTable employeeTable = new employeeTable() ; 
                    employeeTable.employeesTableInitiate() ; 

                    employeesTableInitiated = true ;
                    productsTableInitiated = false ;
                    ReceiptsTableInitiated = false ;
                    providersTableInitiated = false ;
                    accountsTableInitiated = false ;
                    customerAccountsTableInitiated = false ;
                    customersTableInitiated = false ;
                }
            }
        });

        providers.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                if(providersTableInitiated == false){
                    mainPanel.removeAll() ;
                    providersTableInitiate() ;
                    providersTableInitiated = true ;
                    employeesTableInitiated = false ;
                    productsTableInitiated = false ;
                    ReceiptsTableInitiated = false ;
                    accountsTableInitiated = false ;
                    customerAccountsTableInitiated = false ;
                    customersTableInitiated = false ;
                }
            }
        });
        

        Receipts.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                if(ReceiptsTableInitiated == false){
                    mainPanel.removeAll() ;
                    receiptsTable recTable = new receiptsTable() ;
                    recTable.receiptsTableInitiate() ;
                    ReceiptsTableInitiated = true ;
                    employeesTableInitiated = false ;
                    productsTableInitiated = false ;
                    providersTableInitiated = false ;
                    accountsTableInitiated = false ;
                    customerAccountsTableInitiated = false ;
                    customersTableInitiated = false ;
                }
            }
        });

        accounts.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                if(accountsTableInitiated == false){
                    mainPanel.removeAll() ;
                    accountsTable accountsTable = new accountsTable() ;
                    accountsTable.accountsTableInitiate() ;
                    accountsTableInitiated = true ;
                    ReceiptsTableInitiated = false ;
                    employeesTableInitiated = false ;
                    productsTableInitiated = false ;
                    providersTableInitiated = false ;
                    customerAccountsTableInitiated = false ;
                    customersTableInitiated = false ;
                }
            }
        });

        customerAccounts.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                if(customerAccountsTableInitiated == false){
                    mainPanel.removeAll() ;
                    customerAccTable customerAccountsTable = new customerAccTable() ;
                    customerAccountsTable.accountsTableInitiate(); 
                    customerAccountsTableInitiated = true ;
                    accountsTableInitiated = false ;
                    ReceiptsTableInitiated = false ;
                    employeesTableInitiated = false ;
                    productsTableInitiated = false ;
                    providersTableInitiated = false ;
                    customersTableInitiated = false ;
                }
            }
        });

        customers.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                if(customersTableInitiated == false){
                    mainPanel.removeAll() ;
                    customerTable customersTable = new customerTable() ;
                    customersTable.customerTableInitiate() ;
                    customersTableInitiated = true ;
                    customerAccountsTableInitiated = false ;
                    accountsTableInitiated = false ;
                    ReceiptsTableInitiated = false ;
                    employeesTableInitiated = false ;
                    productsTableInitiated = false ;
                    providersTableInitiated = false ;
                }
            }
        });

        logout.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                mainFrame.dispose() ;
                LoginForm login = new LoginForm() ;
                login.run() ;
            }
        });
    }
    //end of buttons column


    //Main panel initiate
    private void mainPanelInitiate(){
        GridBagConstraints gbc = new GridBagConstraints() ;
        gbc.insets = new Insets(0, 0, 0, 0) ;
        gbc.gridx = 1 ;
        gbc.gridy = 0 ;
        gbc.weightx = 5 ;
        gbc.fill = GridBagConstraints.BOTH ;

        mainPanel.setBackground(new Color(245, 245, 247));
        mainPanel.setLayout(new GridBagLayout()) ;
        mainFrame.add(mainPanel , gbc) ;
    }
    //end of main panel initiate


    //Products table initiate
    private void productsTableInitiate(){
        productsTable productsTable = new productsTable();
        productsTable.productsTableInitiate();
    }
    //end of products table initiate

    //providers table initiate
    private void providersTableInitiate(){
        providersTable providersTable = new providersTable() ;
        providersTable.providersTableInitiate() ;
    }
    //end of providers table initiate

    public void run(){
        mainFrameInitiate() ;
    }

    public static void main(String[] args) {
        mainInt main = new mainInt() ;
        main.mainFrameInitiate() ;
        }
}