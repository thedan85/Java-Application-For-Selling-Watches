package com.microsoft.sqlserver.GUI;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import com.microsoft.sqlserver.BUS.*;

public class YearGraphPanel extends JPanel {

    private int padding = 25;
    private int labelPadding = 25;
    private Color lineColor = new Color(44, 102, 230, 180);
    private Color pointColor = new Color(100, 100, 100, 180);
    private Color gridColor = new Color(200, 200, 200, 200);
    private static final Stroke GRAPH_STROKE = new BasicStroke(2f);
    private int pointWidth = 4;
    private int numberYDivisions = 10;

    Vector<Vector<Object>> revenueData;

    public YearGraphPanel(Vector<Vector<Object>> revenueData) {
        this.revenueData = revenueData;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        List<Double> values = extractRevenueValues();

        System.out.println("Extracted Revenue Values: " + values);

        if (values.size() <= 1) {
            System.out.println("Not enough data to draw the graph.");
            return;
        }

        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        double xScale = ((double) getWidth() - (2 * padding) - labelPadding) / (values.size() - 1);
        double yScale = ((double) getHeight() - 2 * padding - labelPadding) / (getMax(values) - getMin(values));

        List<Point> graphPoints = new ArrayList<>();
        for (int i = 0; i < values.size(); i++) {
            int x1 = (int) (i * xScale + padding + labelPadding);
            int y1 = (int) ((getMax(values) - values.get(i)) * yScale + padding);
            graphPoints.add(new Point(x1, y1));
        }

        // Draw background
        g2.setColor(Color.WHITE);
        g2.fillRect(padding + labelPadding, padding, getWidth() - (2 * padding) - labelPadding, getHeight() - 2 * padding - labelPadding);
        g2.setColor(Color.BLACK);

        // Y axis grid + labels
        for (int i = 0; i <= numberYDivisions; i++) {
            int y0 = getHeight() - ((i * (getHeight() - padding * 2 - labelPadding)) / numberYDivisions + padding + labelPadding);
            g2.setColor(gridColor);
            g2.drawLine(padding + labelPadding + 1 + pointWidth, y0, getWidth() - padding, y0);
            g2.setColor(Color.BLACK);
            double yValue = getMin(values) + (getMax(values) - getMin(values)) * ((double) i / numberYDivisions);
            String yLabel = formatLargeNumber(yValue);
            FontMetrics metrics = g2.getFontMetrics();
            int labelWidth = metrics.stringWidth(yLabel);
            g2.drawString(yLabel, padding + labelPadding - labelWidth - 5, y0 + (metrics.getHeight() / 2) - 3);
        }

        // X axis grid + labels
        for (int i = 0; i < values.size(); i++) {
            int x0 = i * (getWidth() - padding * 2 - labelPadding) / (values.size() - 1) + padding + labelPadding;
            int y0 = getHeight() - padding - labelPadding;

            if ((i % ((int) ((values.size() / 20.0)) + 1)) == 0) {
                g2.setColor(gridColor);
                g2.drawLine(x0, y0 - pointWidth - 1, x0, padding);
                g2.setColor(Color.BLACK);

                try {
                    Object label = revenueData.get(i).get(0); // Use year from column 0
                    System.out.println("Label: " + label); // Debug: Print the label
                    String xLabel = "Year " + label.toString();
                    FontMetrics metrics = g2.getFontMetrics();
                    int labelWidth = metrics.stringWidth(xLabel);
                    g2.drawString(xLabel, x0 - labelWidth / 2, y0 + metrics.getHeight() + 3);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        // Axes
        g2.drawLine(padding + labelPadding, getHeight() - padding - labelPadding, padding + labelPadding, padding);
        g2.drawLine(padding + labelPadding, getHeight() - padding - labelPadding, getWidth() - padding, getHeight() - padding - labelPadding);

        // Line graph
        Stroke oldStroke = g2.getStroke();
        g2.setColor(lineColor);
        g2.setStroke(GRAPH_STROKE);
        for (int i = 0; i < graphPoints.size() - 1; i++) {
            Point p1 = graphPoints.get(i);
            Point p2 = graphPoints.get(i + 1);
            g2.drawLine(p1.x, p1.y, p2.x, p2.y);
        }

        // Data points
        g2.setStroke(oldStroke);
        g2.setColor(pointColor);
        for (Point point : graphPoints) {
            g2.fillOval(point.x - pointWidth / 2, point.y - pointWidth / 2, pointWidth, pointWidth);
        }
    }

    private List<Double> extractRevenueValues() {
        List<Double> values = new ArrayList<>();
        for (Vector<Object> row : revenueData) {
            try {
                Object val = row.get(1); 
                values.add(Double.parseDouble(val.toString()));
            } catch (Exception e) {
                values.add(0.0); // If parsing fails, add 0.0
            }
        }
        return values;
    }

    private double getMin(List<Double> data) {
        return data.stream().min(Double::compareTo).orElse(0.0);
    }

    private double getMax(List<Double> data) {
        return data.stream().max(Double::compareTo).orElse(1.0);
    }

    private String formatLargeNumber(double value) {
        if (value >= 1_000_000_000) return String.format("%.1fB", value / 1_000_000_000);
        if (value >= 1_000_000) return String.format("%.1fM", value / 1_000_000);
        if (value >= 1_000) return String.format("%.1fK", value / 1_000);
        return String.format("%.0f", value);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            receiptBUS rb = new receiptBUS();
            Vector<Vector<Object>> mockData = rb.getRevenueStatisticsByYear(); // Fetch yearly revenue data
            YearGraphPanel mainPanel = new YearGraphPanel(mockData);
            mainPanel.setPreferredSize(new Dimension(800, 600));
            JFrame frame = new JFrame("Yearly Revenue Chart");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.getContentPane().add(mainPanel);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
