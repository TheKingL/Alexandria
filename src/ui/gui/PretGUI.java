package ui.gui;

import dao.controllers.PretDAO;
import models.Pret;
import ui.gui.components.ButtonComponent;
import ui.gui.components.ListComponent;
import ui.gui.forms.pret.PretAddForm;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

/**
 * This class represents the GUI for managing loans (Pret).
 * It allows users to view, add, and manage loans in a library system.
 *
 * @version 1.0
 * @author Loris
 */
public class PretGUI extends JFrame {

    private static JPanel pretListDisplayPanel;
    private static final String[] headers = new String[]{"ID", "LOAN DATE", "DURATION", "EFFECTIVE RETURN DATE", "BOOK TITLE", "USER NAME", "USER FIRST NAME", "ACTIONS"};
    private static final int[] columnWidths = new int[]{25, 75, 25, 75, 150, 100, 100, 100};

    /**
     * Constructor for the PretGUI.
     * Initializes the GUI components and sets up the layout.
     */
    public PretGUI() {
        setTitle("Loan Management Window");
        setResizable(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // -------- MAIN CONTAINER --------
        Container mainContainer = this.getContentPane();
        mainContainer.setLayout(new BorderLayout(0, 10));
        mainContainer.setBackground(new Color(248, 248, 248));

        // -------- TITLE PANEL (NORTH) --------
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        titlePanel.setBorder(new EmptyBorder(20, 10, 10, 10));
        titlePanel.setOpaque(false);

        JLabel title = new JLabel("\uD83D\uDC64 Loan Management", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 28));
        title.setForeground(Color.BLACK);
        titlePanel.add(title);
        mainContainer.add(titlePanel, BorderLayout.NORTH);

        // -------- CONTROL PANEL (CENTER) --------
        JPanel topSectionPanel = new JPanel(new BorderLayout());
        topSectionPanel.setOpaque(false);
        topSectionPanel.setBorder(new EmptyBorder(10, 20, 20, 20));

        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        controlPanel.setOpaque(false);

        JButton addPretButton = ButtonComponent.createManagementButton("➕ Add Loan", "Click to add a new pret", e -> {
            PretAddForm pretAddForm = new PretAddForm();
            pretAddForm.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    populateLoanListDisplayPanel();
                }
            });
        });
        controlPanel.add(addPretButton);
        topSectionPanel.add(controlPanel, BorderLayout.NORTH);

        mainContainer.add(topSectionPanel, BorderLayout.CENTER);

        // -------- LIST PRET DISPLAY PANEL (SOUTH) --------
        JPanel listPretPanel = new JPanel();
        listPretPanel.setLayout(new BoxLayout(listPretPanel, BoxLayout.Y_AXIS));
        listPretPanel.setOpaque(false);
        listPretPanel.setBorder(new EmptyBorder(10, 20, 20, 20));

        // --------- HEADER LABELS PANEL --------
        JPanel labelPanel = new JPanel(new GridLayout(1, headers.length));
        labelPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        labelPanel.setBackground(new Color(220, 220, 220));

        for (int i = 0; i < headers.length; i++) {
            JLabel label = new JLabel(headers[i], SwingConstants.CENTER);
            label.setPreferredSize(new Dimension(columnWidths[i], 30));
            label.setFont(new Font("Arial", Font.BOLD, 14));
            label.setForeground(Color.BLACK);
            label.setBorder(BorderFactory.createLineBorder(Color.GRAY));
            labelPanel.add(label);
        }
        listPretPanel.add(labelPanel);
        listPretPanel.add(Box.createVerticalStrut(5));

        // --------- SCROLLABLE LOAN LIST ---------
        pretListDisplayPanel = new JPanel();
        pretListDisplayPanel.setLayout(new BoxLayout(pretListDisplayPanel, BoxLayout.Y_AXIS));

        JScrollPane scrollPane = new JScrollPane(pretListDisplayPanel);
        scrollPane.setPreferredSize(new Dimension(1000, 500));
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        listPretPanel.add(scrollPane);
        topSectionPanel.add(listPretPanel, BorderLayout.CENTER);

        mainContainer.add(topSectionPanel, BorderLayout.CENTER);

        populateLoanListDisplayPanel();

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    /**
     * Populates the loan list display panel with the current list of loans.
     * If there are no loans, it displays a message indicating that no loans are available.
     */
    public static void populateLoanListDisplayPanel() {
        pretListDisplayPanel.removeAll();
        List<Pret> loans = new PretDAO().getList();

        if (loans.isEmpty()) {
            JLabel emptyLabel = new JLabel("No loans available", SwingConstants.CENTER);
            emptyLabel.setFont(new Font("Arial", Font.ITALIC, 16));
            emptyLabel.setForeground(Color.GRAY);
            emptyLabel.setBorder(new EmptyBorder(50, 0, 50, 0));
            pretListDisplayPanel.add(emptyLabel);
        } else {
            for (Pret loan : loans) {
                JPanel loanRow = ListComponent.createLoanRow(loan, columnWidths);
                pretListDisplayPanel.add(loanRow);
                pretListDisplayPanel.add(Box.createVerticalStrut(2));
            }
        }

        pretListDisplayPanel.revalidate();
        pretListDisplayPanel.repaint();
    }
}
