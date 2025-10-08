package com.microsoft.sqlserver.GUI;
import java.awt.* ;
import java.awt.event.* ;
import java.util.concurrent.Flow;

import javax.swing.* ;
import javax.swing.border.* ;

public class adminInt {
    private JFrame mainFrame = new JFrame("Admin Interface") ;
    
    private mainInt mainInt = new mainInt() ;
    private JPanel mainPanel = mainInt.getMainPanel() ;

    private boolean productsTableInitiated = false ;
    private boolean employeesTableInitiated = false ;
    private boolean ReceiptsTableInitiated = false ;
    private boolean providersTableInitiated = false ;
    private boolean accountsTableInitiated = false ;
    private boolean customerAccountsTableInitiated = false ;
    private boolean customersTableInitiated = false ;
    private boolean graphTableInitiated = false ;
    
    //Getters
    public JPanel getMainPanel(){
        return mainPanel ;
    }

    private void mainFrameInitiate(){

        mainPanel.removeAll() ;
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE) ;
        mainFrame.setSize(1500 , 720) ;
        mainFrame.setLocationRelativeTo(null) ;
        mainFrame.setLayout(new GridBagLayout()) ;
        
        buttonsColumnInitiate() ;
        mainPanelInitiate() ;
        
        mainFrame.setVisible(true) ;
        welcomeDialog(mainFrame) ;
    }

    @SuppressWarnings("Convert2Lambda")
    private void buttonsColumnInitiate(){
        JPanel buttonsPanel = new JPanel() ;
        buttonsPanel.setLayout(new BoxLayout(buttonsPanel , BoxLayout.Y_AXIS)) ;
        buttonsPanel.setBackground(new Color(34, 40, 49)) ;
        
        JButton titleButton = new JButton("Welcome admin") ;
        JButton products = new JButton("Manage Products") ;
        JButton employees = new JButton("Manage Employees") ;
        JButton providers = new JButton("Manage Providers") ;
        JButton customers = new JButton("Manage Customers") ;
        JButton Receipts = new JButton("Manage Receipts") ;
        JButton accounts = new JButton("Manage Accounts") ;
        JButton customerAccounts = new JButton("Customer Accounts") ;
        JButton graphs = new JButton("Statistics") ;
        JButton logout = new JButton("Logout") ;

        
        titleButton.setFont(new Font("Georgia" , Font.BOLD , 20)) ;
        products.setFont(new Font("Segoe UI" , Font.BOLD , 16)) ;
        employees.setFont(new Font("Segoe UI" , Font.BOLD , 16)) ;
        providers.setFont(new Font("Segoe UI" , Font.BOLD , 16)) ;
        customers.setFont(new Font("Segoe UI" , Font.BOLD , 16)) ;
        Receipts.setFont(new Font("Segoe UI" , Font.BOLD , 16)) ;
        accounts.setFont(new Font("Segoe UI" , Font.BOLD , 16)) ;
        customerAccounts.setFont(new Font("Segoe UI" , Font.BOLD , 16)) ;
        graphs.setFont(new Font("Segoe UI" , Font.BOLD , 16)) ;
        logout.setFont(new Font("Segoe UI" , Font.BOLD , 16)) ;

        titleButton.setForeground(new Color(238, 238, 238)) ;
        products.setForeground(new Color(238, 238, 238)) ;
        employees.setForeground(new Color(238, 238, 238)) ;
        providers.setForeground(new Color(238, 238, 238)) ;
        customers.setForeground(new Color(238, 238, 238)) ;
        Receipts.setForeground(new Color(238, 238, 238)) ;
        accounts.setForeground(new Color(238, 238, 238)) ;
        customerAccounts.setForeground(new Color(238, 238, 238)) ;
        graphs.setForeground(new Color(238, 238, 238)) ;
        logout.setForeground(new Color(238, 238, 238)) ;
    
        titleButton.setMaximumSize(new Dimension(300, 50)) ;
        products.setMaximumSize(new Dimension(300, 50)) ;
        employees.setMaximumSize(new Dimension(300, 50)) ;
        providers.setMaximumSize(new Dimension(300, 50)) ;
        customers.setMaximumSize(new Dimension(300, 50)) ;
        Receipts.setMaximumSize(new Dimension(300, 50)) ;
        accounts.setMaximumSize(new Dimension(300, 50)) ;
        graphs.setMaximumSize(new Dimension(300, 50)) ;
        customerAccounts.setMaximumSize(new Dimension(300, 50)) ;

        logout.setMaximumSize(new Dimension(300, 50)) ;

        products.setHorizontalAlignment(JButton.LEFT) ;
        employees.setHorizontalAlignment(JButton.LEFT) ;
        providers.setHorizontalAlignment(JButton.LEFT) ; 
        customers.setHorizontalAlignment(JButton.LEFT) ;
        Receipts.setHorizontalAlignment(JButton.LEFT) ; 
        accounts.setHorizontalAlignment(JButton.LEFT) ;
        customerAccounts.setHorizontalAlignment(JButton.LEFT) ;
        graphs.setHorizontalAlignment(JButton.LEFT) ;

        logout.setHorizontalAlignment(JButton.LEFT) ;

        titleButton.setFocusPainted(false) ;
        products.setFocusPainted(false) ;
        employees.setFocusPainted(false) ;
        customers.setFocusPainted(false) ;
        providers.setFocusPainted(false) ;
        Receipts.setFocusPainted(false) ;
        accounts.setFocusPainted(false) ;
        customerAccounts.setFocusPainted(false) ;
        graphs.setFocusPainted(false) ;

        logout.setFocusPainted(false) ;

        titleButton.setBorderPainted(false) ;
        products.setBorderPainted(false) ;
        employees.setBorderPainted(false) ;
        customers.setBorderPainted(false) ;
        providers.setBorderPainted(false) ;
        Receipts.setBorderPainted(false) ;
        accounts.setBorderPainted(false) ;
        customerAccounts.setBorderPainted(false) ;
        graphs.setBorderPainted(false) ;

        logout.setBorderPainted(false) ;

        titleButton.setBackground(new Color(34, 40, 49)) ;
        products.setBackground(new Color(34, 40, 49)) ;
        customers.setBackground(new Color(34, 40, 49)) ;
        employees.setBackground(new Color(34, 40, 49)) ;
        providers.setBackground(new Color(34, 40, 49)) ;
        Receipts.setBackground(new Color(34, 40, 49)) ;
        accounts.setBackground(new Color(34, 40, 49)) ;
        customerAccounts.setBackground(new Color(34, 40, 49)) ;
        graphs.setBackground(new Color(34, 40, 49)) ;

        logout.setBackground(new Color(34, 40, 49)) ;

        titleButton.setContentAreaFilled(false);

        buttonsPanel.add(titleButton) ;
        buttonsPanel.add(products) ;
        buttonsPanel.add(employees) ;
        buttonsPanel.add(providers) ;
        buttonsPanel.add(customers) ;
        buttonsPanel.add(Receipts) ;
        buttonsPanel.add(accounts) ;
        buttonsPanel.add(graphs) ;
        buttonsPanel.add(customerAccounts) ;

        buttonsPanel.add(logout) ;

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
                products.setBackground(new Color(0, 173, 181)) ;
                products.setForeground(new Color(238, 238, 238)) ;
            }

            @Override
            public void mouseExited(MouseEvent e){
                products.setBackground(new Color(34, 40, 49)) ;
                products.setForeground(new Color(238, 238, 238)) ;
            }

        });
        
        employees.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseEntered(MouseEvent e){
                employees.setBackground(new Color(0, 173, 181)) ;
                employees.setForeground(new Color(238, 238, 238)) ;
            }

            @Override
            public void mouseExited(MouseEvent e){
                employees.setBackground(new Color(34, 40, 49)) ;
                employees.setForeground(new Color(238, 238, 238)) ;
            }
        });

        providers.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseEntered(MouseEvent e){
                providers.setBackground(new Color(0, 173, 181)) ;
                providers.setForeground(new Color(238, 238, 238)) ;
            }

            @Override
            public void mouseExited(MouseEvent e){
                providers.setBackground(new Color(34, 40, 49)) ;
                providers.setForeground(new Color(238, 238, 238)) ;
                }
        });

        customers.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseEntered(MouseEvent e){
                customers.setBackground(new Color(0, 173, 181)) ;
                customers.setForeground(new Color(238, 238, 238)) ;
            }

            @Override
            public void mouseExited(MouseEvent e){
                customers.setBackground(new Color(34, 40, 49)) ;
                customers.setForeground(new Color(238, 238, 238)) ;
            }
        });

        Receipts.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseEntered(MouseEvent e){
                Receipts.setBackground(new Color(0, 173, 181)) ;
                Receipts.setForeground(new Color(238, 238, 238)) ;
            }

            @Override
            public void mouseExited(MouseEvent e){
                Receipts.setBackground(new Color(34, 40, 49)) ;
                Receipts.setForeground(new Color(238, 238, 238)) ;
            }
        });

        accounts.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseEntered(MouseEvent e){
                accounts.setBackground(new Color(0, 173, 181)) ;
                accounts.setForeground(new Color(238, 238, 238)) ;
            }

            @Override
            public void mouseExited(MouseEvent e){
                accounts.setBackground(new Color(34, 40, 49)) ;
                accounts.setForeground(new Color(238, 238, 238)) ;
            }
        });

        graphs.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseEntered(MouseEvent e){
                graphs.setBackground(new Color(0, 173, 181)) ;
                graphs.setForeground(new Color(238, 238, 238)) ;
            }

            @Override
            public void mouseExited(MouseEvent e){
                graphs.setBackground(new Color(34, 40, 49)) ;
                graphs.setForeground(new Color(238, 238, 238)) ;
            }
        });

        customerAccounts.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseEntered(MouseEvent e){
                customerAccounts.setBackground(new Color(0, 173, 181)) ;
                customerAccounts.setForeground(new Color(238, 238, 238)) ;
            }

            @Override
            public void mouseExited(MouseEvent e){
                customerAccounts.setBackground(new Color(34, 40, 49)) ;
                customerAccounts.setForeground(new Color(238, 238, 238)) ;
            }
        });

        logout.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseEntered(MouseEvent e){
                logout.setBackground(new Color(0, 173, 181)) ;
                logout.setForeground(new Color(238, 238, 238)) ;
            }

            @Override
            public void mouseExited(MouseEvent e){
                logout.setBackground(new Color(34, 40, 49)) ;
                logout.setForeground(new Color(238, 238, 238)) ;
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
                    customersTableInitiated = false ;
                    customerAccountsTableInitiated = false ;
                    graphTableInitiated = false;
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
                    customersTableInitiated = false ;
                    customerAccountsTableInitiated = false ;
                    graphTableInitiated = false;
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
                    customersTableInitiated = false ;
                    customerAccountsTableInitiated = false ;
                    graphTableInitiated = false;
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
                    customersTableInitiated = false ;
                    customerAccountsTableInitiated = false ;
                    graphTableInitiated = false;
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
                    graphTableInitiated = false;
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
                    graphTableInitiated = false;
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
                    customersTableInitiated = false ;
                    customerAccountsTableInitiated = false ;
                    graphTableInitiated = false;
                }
            }
        });

        graphs.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                if(graphTableInitiated == false){
                    mainPanel.removeAll() ;
                    graphsTable graphsTable = new graphsTable() ;
                    graphsTable.graphsTableInitiate() ;
                    accountsTableInitiated = false ;
                    ReceiptsTableInitiated = false ;
                    employeesTableInitiated = false ;
                    productsTableInitiated = false ;
                    providersTableInitiated = false ;
                    customersTableInitiated = false ;
                    customerAccountsTableInitiated = false ;
                    graphTableInitiated = true;
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

    private void welcomeDialog(JFrame mainFrame){
        JDialog welcome = new JDialog() ;
        welcome.setTitle("Welcome") ;
        welcome.setLayout(new GridBagLayout()) ;
        welcome.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE) ;
        welcome.setSize(350 , 250) ;
        welcome.setLocationRelativeTo(mainFrame) ;
        welcome.getContentPane().setBackground(new Color(57, 62, 70));

        JLabel label = new JLabel("Welcome admin") ;
        label.setFont(new Font("Segoe UI" , Font.BOLD , 14));
        label.setForeground(new Color(238 , 238 , 238)) ;

        JButton OK = new JButton("OK") ;
        OK.setBackground(new Color(238 , 238 , 238)) ;
        OK.setForeground(new Color(57, 62, 70)) ;
        OK.setFont(new Font("Segoe UI", Font.BOLD, 14)) ;
        OK.setFocusPainted(false) ;
        OK.setBorderPainted(false) ;

        GridBagConstraints gbc = new GridBagConstraints() ;
        gbc.insets = new Insets(10,10,10,10);
        gbc.gridx = 0;
        gbc.gridy = 0;
        welcome.add(label , gbc) ;

        gbc.gridx = 0 ;
        gbc.gridy = 1 ;
        welcome.add(OK , gbc) ;

        welcome.setVisible(true) ;

        OK.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                welcome.dispose() ;
            }
        });
    }

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
        adminInt main = new adminInt() ;
        main.mainFrameInitiate() ;
    }
}

