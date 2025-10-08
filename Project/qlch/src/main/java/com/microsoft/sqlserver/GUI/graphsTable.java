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


public class graphsTable {
    private mainInt mainInt = new mainInt() ;
    private JPanel mainPanel = mainInt.getMainPanel() ;
    private int hoveredRow = -1 ;
    private String type ;
    private JPanel contentPanel; 
    private boolean monthgraph,yeargraph;

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
    
    public void graphsTableInitiate() {
        typeCheck();
        
        JButton byMonthButton = new JButton("By Month");
        JButton byYearButton = new JButton("By Year");
        
        GridBagConstraints mainPanelGBC = new GridBagConstraints() ;
        mainPanelGBC.insets = new Insets(7 , 7 , 7 , 7) ;
        mainPanelGBC.gridx = 0 ;
        mainPanelGBC.gridy = 0 ;
        mainPanel.add(byMonthButton , mainPanelGBC) ;

        mainPanelGBC.insets = new Insets(7 , 7 , 7 , 7) ;
        mainPanelGBC.gridx = 1 ;
        mainPanelGBC.gridy = 0 ;
        mainPanel.add(byYearButton , mainPanelGBC) ;


        // Create and add content panel
        contentPanel = new JPanel(new BorderLayout());
        mainPanelGBC.gridwidth = 4 ;
        mainPanelGBC.gridx = 0 ;
        mainPanelGBC.gridy = 1 ;
        mainPanelGBC.weightx = 1 ;
        mainPanelGBC.weighty = 1 ;
        mainPanelGBC.fill = GridBagConstraints.BOTH ;
        mainPanel.add(contentPanel,mainPanelGBC);

        mainPanel.revalidate();
        mainPanel.repaint();


        byMonthButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                if(monthgraph == false){
                    contentPanel.removeAll() ;
                    showStatisticsByMonth();
                    monthgraph = true;
                    yeargraph = false;

                }
            }
        }
        );

        byYearButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                if(yeargraph == false){
                    contentPanel.removeAll() ;
                    showStatisticsByYear();
                    monthgraph = false;
                    yeargraph = true;
                }
            }
        }
        );
    }

    public void showStatisticsByMonth() {
        contentPanel.removeAll(); // Remove old content

        JPanel byMonthDialog = new JPanel(new BorderLayout());
        JPanel container = new JPanel(new GridLayout(1, 2));
        JPanel panelOne = new JPanel(new BorderLayout());
        JPanel panelTwo = new JPanel(new BorderLayout());
    
        // Panel 1
        JLabel title = new JLabel("Revenue Statistics By Month", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 20));
        panelOne.add(title, BorderLayout.NORTH);
    
        JTable statisticsTable = new JTable();
        DefaultTableModel model = new DefaultTableModel();

        model.addColumn("Month");
        model.addColumn("Revenue");
        model.addColumn("Year");
    
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
        closeButton.addActionListener(e -> {
            monthgraph = false;
            contentPanel.removeAll() ;
            mainPanel.revalidate();
            mainPanel.repaint();
        });
        panelOne.add(closeButton, BorderLayout.SOUTH);
    
        // Panel 2 - Graph
        GraphPanel graPanel = new GraphPanel(revenueData);
        panelTwo.add(graPanel, BorderLayout.CENTER);

        container.add(panelOne);
        container.add(panelTwo);
        byMonthDialog.add(container, BorderLayout.CENTER);
    
        contentPanel.add(byMonthDialog, BorderLayout.CENTER);
        contentPanel.revalidate();
        contentPanel.repaint();
    }
    
    
    public void showStatisticsByYear() {

        contentPanel.removeAll(); // Remove old content

        JPanel byyearDialog = new JPanel(new BorderLayout());
        JPanel container = new JPanel(new GridLayout(1, 2));
        JPanel panelOne = new JPanel(new BorderLayout());
        JPanel panelTwo = new JPanel(new BorderLayout());
    
        // Panel 1
        JLabel title = new JLabel("Revenue Statistics By year", SwingConstants.CENTER);
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
        closeButton.addActionListener(e -> {
            yeargraph = false;
            contentPanel.removeAll() ;
            mainPanel.revalidate();
            mainPanel.repaint();
        });
        panelOne.add(closeButton, BorderLayout.SOUTH);
    
        // Panel 2 - Graph
        YearGraphPanel graPanel = new YearGraphPanel(revenueData);
        panelTwo.add(graPanel, BorderLayout.CENTER);

        container.add(panelOne);
        container.add(panelTwo);
        byyearDialog.add(container, BorderLayout.CENTER);
    
        contentPanel.add(byyearDialog, BorderLayout.CENTER);
        contentPanel.revalidate();
        contentPanel.repaint();
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
