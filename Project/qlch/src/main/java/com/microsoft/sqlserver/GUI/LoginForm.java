package com.microsoft.sqlserver.GUI;
import com.microsoft.sqlserver.DTO.* ;
import com.microsoft.sqlserver.BUS.* ;
import java.awt.* ;
import java.awt.event.* ;
import java.util.Vector;

import javax.swing.* ;

@SuppressWarnings("FieldMayBeFinal")
public class LoginForm{
    private JFrame LoginFrame = new JFrame("Login Form") ;
    private JPanel LoginPanel = new JPanel() ;
    
    public boolean isValidName(String input) {
        return input != null && input.matches("[a-zA-Z]+");
    }
    public int isValidPhoneNumber(String phone) {
        if (phone == null) {
            return 1;  // Trả về 0 nếu số điện thoại là null
        }
    
        // Loại bỏ khoảng trắng và ký tự đặc biệt
        phone = phone.replaceAll("[^\\d+]", "");
    
        // Kiểm tra số điện thoại bắt đầu bằng 0 và có đúng 10 chữ số
        if (!phone.startsWith("0")) {
            return 1;  // Trả về 0 nếu số điện thoại không bắt đầu bằng số 0
        }
    
        if (!phone.substring(1,2).matches("[3,5,7,8,9]")) {
            return 2;  // Trả về 1 nếu số điện thoại hợp lệ
        }
        if(phone.length()!=10){
            return 3;
        } else {
            return 0;  // Trả về 0 nếu số điện thoại không hợp lệ
        }
    }
    
    private void FrameInitiate(){
        LoginFrame.setSize(900 , 500) ;
        LoginFrame.setLocationRelativeTo(null) ;
        LoginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE) ;

        BackgroundPanel bgPanel = new BackgroundPanel("bg1.jpg") ;
        bgPanel.setLayout(new GridBagLayout()) ;

        GridBagConstraints gbc = new GridBagConstraints() ;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;

        LoginPanelInitiate() ;
        bgPanel.add(LoginPanel, gbc) ;
        LoginFrame.setContentPane(bgPanel) ;
        LoginFrame.setVisible(true) ;
    }

    // @SuppressWarnings("Convert2Lambda")
    private void LoginPanelInitiate(){
        LoginPanel.setBorder(BorderFactory.createTitledBorder("Login Form")) ;
        LoginPanel.setOpaque(false) ;
        LoginPanel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints() ;
        gbc.insets = new Insets(10 , 10 , 10 , 10) ;

        JLabel username = new JLabel("Username: ") ;
        JLabel password = new JLabel("Password: ");
        
        username.setFont(new Font("Georgia" , Font.BOLD , 17)) ;
        password.setFont(new Font("Georgia" , Font.BOLD , 17)) ;

        JTextField usernameField = new JTextField() ;
        JPasswordField passwordField = new JPasswordField() ;
        usernameField.setPreferredSize(new Dimension(150 , 20)) ;
        passwordField.setPreferredSize(new Dimension(150 , 20)) ;
        usernameField.setBorder(null) ;
        passwordField.setBorder(null) ;

        JButton loginButton = new JButton("Login") ;
        loginButton.setFont(new Font("Georgia" , Font.PLAIN , 17)) ;
        loginButton.setForeground(Color.WHITE) ;
        loginButton.setBackground(new Color(77, 85, 204)) ;
        loginButton.setFocusPainted(false) ;

        JButton signupButton = new JButton("Sign Up") ;
        signupButton.setFont(new Font("Georgia" , Font.PLAIN , 17)) ;
        signupButton.setForeground(Color.WHITE) ;
        signupButton.setBackground(new Color(77, 85, 204)) ;
        signupButton.setFocusPainted(false) ;


        gbc.gridx = 0 ;
        gbc.gridy = 0 ;
        LoginPanel.add(username , gbc) ;

        gbc.gridx = 1 ;
        gbc.gridy = 0 ;
        LoginPanel.add(usernameField , gbc) ;

        gbc.gridx = 0 ;
        gbc.gridy = 1 ;
        LoginPanel.add(password , gbc) ;

        gbc.gridx = 1 ;
        gbc.gridy = 1 ;
        LoginPanel.add(passwordField , gbc) ;

        gbc.gridx = 0 ;
        gbc.gridy = 2 ;
        gbc.gridwidth = 2 ;
        gbc.fill = GridBagConstraints.HORIZONTAL ;
        LoginPanel.add(loginButton , gbc) ;

        gbc.gridx = 0 ;
        gbc.gridy = 3 ;
        gbc.gridwidth = 2 ;
        gbc.fill = GridBagConstraints.HORIZONTAL ;
        LoginPanel.add(signupButton , gbc) ;

        LoginPanel.setVisible(true) ;
        
        
        loginButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                String username = usernameField.getText() ;
                char[] password = passwordField.getPassword();
                String pass = new String(password);
                

                accountDTO account = new accountDTO() ;
                account.setUsername(username) ;
                account.setPassword(pass) ;

                accountBUS accountBUS = new accountBUS() ;
                String result = accountBUS.checkLogin(account) ;

                cusAccountBUS cusAccountBUS = new cusAccountBUS() ;
                String resultCus = cusAccountBUS.checkLogin(account) ;

                if(username.contains("NV")){
                    if(result.equalsIgnoreCase("Wrong username")){
                        customErrorDialog("Wrong username" , null) ;
                    }
                    else if(result.equalsIgnoreCase("Wrong password")){
                        customErrorDialog("Wrong password" , null) ;
                    }
                    else if(result.equalsIgnoreCase("Login successful")){
                        accountBUS.setCurrentAccount(accountBUS.getAccountByUsername(username)) ;
                        cusAccountBUS.setCurrentAccount(null) ;
                
                        
                        String type = accountBUS.getCurrentAccount().getType().trim() ;
                        LoginFrame.dispose() ;
                        
                        if(type.equalsIgnoreCase("Admin")){
                            adminInt adminInt = new adminInt() ;
                            adminInt.run() ;
                        }
                        else{
                            mainInt mainInt = new mainInt() ;
                            mainInt.run() ;
                        }
                    }
                    else{
                        customErrorDialog("Enter a valid username" , null) ;
                    }
                }
                else if(username.contains("KH")){
                    if(resultCus.equalsIgnoreCase("Wrong username")){
                        customErrorDialog("Wrong username" , null) ;
                    }
                    else if(resultCus.equalsIgnoreCase("Wrong password")){
                        customErrorDialog("Wrong password" , null) ;
                    }
                    else if(resultCus.equalsIgnoreCase("Login successful")){
                        cusAccountBUS.setCurrentAccount(cusAccountBUS.getAccountByUsername(username)) ;
                        accountBUS.setCurrentAccount(null) ;
                        
                        LoginFrame.dispose() ;

                        mainInt mainInt = new mainInt() ;
                        mainInt.run() ;
                    }
                    else{
                        customErrorDialog("Enter a valid username" , null) ;
                    }
                }
                else{
                    customErrorDialog("Enter a valid username" , null) ;
                }
            }
        });

        signupButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                addPanel() ;
            }
        }) ;
    }

    @SuppressWarnings("Convert2Lambda")
    public void customSuccessDialog(JDialog parentDialog) {
        JDialog customSuccessDialog = new JDialog(parentDialog, "Success", true);
        customSuccessDialog.setLayout(new GridBagLayout());
        customSuccessDialog.setSize(200, 150);
        customSuccessDialog.setLocationRelativeTo(parentDialog);
    
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
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        customSuccessDialog.add(messageLabel, gbc);
    
        gbc.gridy = 1;
        customSuccessDialog.add(okButton, gbc);
    
        okButton.addActionListener(e -> customSuccessDialog.dispose());
    
        customSuccessDialog.setVisible(true);
        
    }
    

    @SuppressWarnings("Convert2Lambda")
    public void customErrorDialog(String message , JDialog parentDialog){
        JDialog customErrorDialog = new JDialog(parentDialog,"Error",true) ;
        customErrorDialog.setLayout(new GridBagLayout()) ;
        customErrorDialog.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE) ;
        customErrorDialog.setSize(450 , 150) ;
        customErrorDialog.setTitle("Error") ;
        customErrorDialog.setLocationRelativeTo(parentDialog) ;

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

        okButton.addActionListener(e -> customErrorDialog.dispose());

        customErrorDialog.setVisible(true) ;
        } ;
    

    public void addPanel(){
        JDialog addDialog = new JDialog() ;
        addDialog.setSize(400 , 300) ;
        addDialog.setLocationRelativeTo(null) ;
        addDialog.setTitle("Sign Up") ;
        addDialog.setLayout(new GridBagLayout()) ;

        JLabel title = new JLabel("Sign Up") ;
        JLabel name = new JLabel("Name: ") ;;
        JLabel phoneNumber = new JLabel("Phone Number: ") ;
        JLabel password = new JLabel("Password: ") ;
        title.setFont(new Font("Georgia" , Font.BOLD , 17)) ;
        name.setFont(new Font("Georgia" , Font.BOLD , 14)) ;
        phoneNumber.setFont(new Font("Georgia" , Font.BOLD , 14)) ;
        password.setFont(new Font("Georgia" , Font.BOLD , 14)) ;

        JTextField nameField = new JTextField() ;
        JTextField phoneNumberField = new JTextField() ;
        JTextField passwordField = new JTextField() ;
        nameField.setPreferredSize(new Dimension(150 , 20)) ;
        phoneNumberField.setPreferredSize(new Dimension(150 , 20)) ;
        passwordField.setPreferredSize(new Dimension(150 , 20)) ;

        JButton confirm = new JButton("Sign Up") ;
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
        addDialog.add(phoneNumber , gbc) ;

        gbc.gridx = 1 ;
        gbc.gridy = 2 ;
        gbc.gridwidth = 1 ;
        gbc.anchor = GridBagConstraints.EAST ;
        addDialog.add(phoneNumberField , gbc) ;

        gbc.gridx = 0 ;
        gbc.gridy = 3 ;
        gbc.gridwidth = 1 ;
        gbc.anchor = GridBagConstraints.WEST ;
        addDialog.add(password , gbc) ;

        gbc.gridx = 1 ;
        gbc.gridy = 3 ;
        gbc.gridwidth = 1 ;
        gbc.anchor = GridBagConstraints.EAST ;
        addDialog.add(passwordField , gbc) ;

        gbc.gridx = 0 ;
        gbc.gridy = 4 ;
        gbc.gridwidth = 2 ;
        gbc.fill = GridBagConstraints.HORIZONTAL ;
        gbc.anchor = GridBagConstraints.CENTER ;
        addDialog.add(confirm , gbc) ;

        addDialog.setVisible(true) ;

        confirm.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = "";   // Khai báo biến name
                String phoneNumber = ""; // Khai báo biến phoneNumber
                String password = ""; // Khai báo biến password
        
                // nhập tên
                if (isValidName(nameField.getText())) {
                    name = nameField.getText();
                } else {
                    customErrorDialog("Name should only contain words", addDialog);
                    return; // Nếu tên không hợp lệ, dừng lại tại đây
                }
                //kiểm tra số dt và nhập sdt
                if (isValidPhoneNumber(phoneNumberField.getText())==0) {
                    phoneNumber = phoneNumberField.getText();
                } else if(isValidPhoneNumber(phoneNumberField.getText())==1){
                    customErrorDialog("Phone number should start with 0", addDialog);
                    return; // Nếu số điện thoại không hợp lệ, dừng lại tại đây
                }else if(isValidPhoneNumber(phoneNumberField.getText())==2){
                    customErrorDialog("Phone number must follow up 0 with 3 5 7 8 9", addDialog);
                    return;
                }else if(isValidPhoneNumber(phoneNumberField.getText())==3){
                    customErrorDialog("Phone number must contain 10 numbers", addDialog);
                    return;}
                    //nhập pass
                password = passwordField.getText();

                    //kiểm tra 3 đối số
                if (name.isEmpty() || phoneNumber.isEmpty() || password.isEmpty()) {
                    customErrorDialog("Please fill in all fields", addDialog);
                    return; // Nếu có trường trống, dừng lại tại đây
                }
        
                // Tạo đối tượng customerDTO và set giá trị
                customerDTO customer = new customerDTO();
                customer.setName(name);
                customer.setPhoneNumber(phoneNumber);
        
                // Thêm customer vào cơ sở dữ liệu
                customerBUS customerBUS = new customerBUS();
                customerBUS.addcustomer(customer);
        
                // Lấy danh sách khách hàng và lấy customer mới nhất
                Vector<customerDTO> customerList = customerBUS.getCustomerList();
                customer = customerList.lastElement();
        
                // Tạo đối tượng accountDTO và set giá trị
                accountDTO account = new accountDTO();
                account.setUsername(customer.getID());
                account.setPassword(password);
                account.setType("Customer");
        
                // Thêm tài khoản vào cơ sở dữ liệu
                cusAccountBUS cusAccountBUS = new cusAccountBUS();
                cusAccountBUS.addAccount(account);
        
                // Hiển thị dialog thành công
                    signupMessage(addDialog);
                    customSuccessDialog(addDialog);
                    addDialog.dispose();

                }
                
            }
    );
}
        

        
    

public void signupMessage(JDialog parentDialog) {
    cusAccountBUS cusAccountBUS = new cusAccountBUS();
    Vector<accountDTO> accountList = cusAccountBUS.getAccountList();

    // Lấy tài khoản mới nhất từ danh sách
    accountDTO account = accountList.lastElement();

    // Tạo dialog thông báo với phụ thuộc vào dialog cha (parentDialog)
    JDialog signupMessageDialog = new JDialog();
    signupMessageDialog.setLayout(new GridBagLayout());
    signupMessageDialog.setSize(250, 150);
    signupMessageDialog.setLocationRelativeTo(parentDialog);  // Điều chỉnh kích thước cho phù hợp
    signupMessageDialog.setTitle("Notification");
    signupMessageDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);// Đặt vị trí của dialog theo dialog cha

    // Tạo label thông báo
    JLabel messageLabel = new JLabel("Your username is: " + account.getUsername());
    messageLabel.setFont(new Font("Segoe UI", Font.BOLD, 15));

    // Tạo nút OK để đóng dialog
    JButton okButton = new JButton("OK");
    okButton.setFocusPainted(false);
    okButton.setBorderPainted(false);
    okButton.setBackground(Color.BLACK);
    okButton.setForeground(Color.WHITE);
    okButton.setFont(new Font("Segoe UI", Font.BOLD, 14));

    // Cấu hình GridBagLayout cho các thành phần
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(10, 10, 10, 10);
    gbc.gridx = 0;
    gbc.gridy = 0;
    gbc.gridwidth = 2;
    gbc.anchor = GridBagConstraints.CENTER;
    gbc.fill = GridBagConstraints.HORIZONTAL;
    signupMessageDialog.add(messageLabel, gbc);

    gbc.gridx = 0;
    gbc.gridy = 1;
    gbc.gridwidth = 2;
    gbc.anchor = GridBagConstraints.CENTER ;
    gbc.fill = GridBagConstraints.NONE ;
    signupMessageDialog.add(okButton, gbc);

    // Hiển thị dialog
    signupMessageDialog.setVisible(true);

    // Đóng dialog khi nhấn nút OK
    okButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            signupMessageDialog.dispose();
        }
    });
}

    
    public void run(){
        LoginForm loginForm = new LoginForm() ;
        loginForm.FrameInitiate() ;
    }

    public static void main(String[] args){
        LoginForm loginForm = new LoginForm() ;
        loginForm.FrameInitiate() ;
    }
}

@SuppressWarnings("FieldMayBeFinal")
class BackgroundPanel extends JPanel{
    private Image bgImg ;

    public BackgroundPanel(String imgPath){
        bgImg = new ImageIcon(imgPath).getImage() ;
    }

    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g) ;
        g.drawImage(bgImg , 0 , 0 , this.getWidth() , this.getHeight() , this) ;
    }
}
