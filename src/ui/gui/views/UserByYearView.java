package ui.gui.views;

import dao.controllers.UsagerDAO;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

/**
 * This class represents a view for displaying the number of users by their birth year.
 * It extends JFrame and provides a graphical representation of user data in a histogram format.
 *
 * @version 1.0
 * @author Loris
 */
public class UserByYearView extends JFrame {

    /**
     * Constructor for UserByYearView.
     * Initializes the view with a title, size, and layout, and adds a histogram panel displaying user data.
     */
    public UserByYearView() {
        setTitle("Number of Users by Birth Year");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1200, 700);
        setResizable(false);

        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("Number of Users by Birth Year");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        contentPane.add(titleLabel, BorderLayout.NORTH);

        UsagerDAO usagerDAO = new UsagerDAO();
        HistogramPanel histogramPanel = new HistogramPanel(usagerDAO.getNbUserByYear());
        contentPane.add(histogramPanel, BorderLayout.CENTER);

        setVisible(true);
    }
}

/**
 * This class represents a panel that displays a histogram of user data. (reused from APP8)
 * It extends JPanel and overrides the paintComponent method to draw the histogram.
 *
 * @version 1.0
 * @author Loris
 */
class HistogramPanel extends JPanel {
    private final Map<Integer, Integer> data;

    /**
     * Constructor for HistogramPanel.
     * Initializes the panel with the provided user data.
     *
     * @param data A map where keys are years and values are the number of users born in those years.
     */
    public HistogramPanel(Map<Integer, Integer> data) {
        this.data = data;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int width = getWidth();
        int height = getHeight();
        int barWidth = !data.isEmpty() ? width / data.size() - 2 : 0;
        int maxValue = data.values().stream().mapToInt(Integer::intValue).max().orElse(0) + 10;
        int scale = height / maxValue;
        int spacing = 2;
        int x = 0;
        String yLabel = "Number of Users";

        g.setFont(new Font("Arial", Font.BOLD, 12));
        FontMetrics fm = g.getFontMetrics();

        g.drawString(yLabel, 10, 10 + fm.getAscent());
        g.setColor(Color.BLACK);
        g.drawLine(20, height - 20, width - 20, height - 20);
        g.drawLine(20, height - 20, 20, 20);

        for (Map.Entry<Integer, Integer> entry : data.entrySet()) {
            int year = entry.getKey();
            int count = entry.getValue();
            int barHeight = count * scale;
            g.setColor(new Color(0, 128, 255));
            g.fillRect(x + 20, height - barHeight - 20, barWidth - spacing, barHeight);
            g.setColor(Color.BLACK);
            g.drawString(String.valueOf(count), x + 20 + (barWidth - spacing - fm.stringWidth(String.valueOf(count))) / 2, height - barHeight - 25);
            g.drawString(String.valueOf(year), x + 20 + (barWidth - spacing - fm.stringWidth(String.valueOf(year))) / 2, height - 5);
            x += barWidth;
        }
    }
}