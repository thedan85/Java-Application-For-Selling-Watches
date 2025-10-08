package com.microsoft.sqlserver.GUI ;
import com.toedter.calendar.JDateChooser;
import javax.swing.*;
import java.awt.*;

public class DatePickerExample {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("JCalendar Date Picker");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(300, 200);
            frame.setLayout(new FlowLayout());

            // Create JDateChooser
            JDateChooser dateChooser = new JDateChooser();
            frame.add(dateChooser);

            // Add a button to print the selected date
            JButton button = new JButton("Get Date");
            button.addActionListener(e -> {
                java.util.Date selectedDate = dateChooser.getDate();
                JOptionPane.showMessageDialog(frame, "Selected Date: " + selectedDate);
            });
            frame.add(button);

            frame.setVisible(true);
        });
    }
}