package ui.gui;

import dao.controllers.UsagerDAO;
import models.Usager;
import ui.gui.components.ButtonComponent;
import ui.gui.components.ListComponent;
import ui.gui.forms.usager.UsagerAddForm;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

/**
 * This class represents the GUI for managing users (Usagers).
 * It allows users to view, add, and manage users in a library system.
 *
 * @version 1.0
 * @author Loris
 */
public class UsagerGUI extends JFrame {

    private static JPanel userListDisplayPanel;
    private static final String[] headers = {"ID", "NAME", "FIRST NAME", "BIRTH YEAR", "REDUCED RATE", "ACTIONS"};

    /**
     * Constructor for the UsagerGUI.
     * Initializes the GUI components and sets up the layout.
     */
    public UsagerGUI() {
        setTitle("User Management Window");
        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // -------- MAIN CONTAINER --------
        Container mainContainer = this.getContentPane();
        mainContainer.setLayout(new BorderLayout(0, 10));
        mainContainer.setBackground(new Color(248, 248, 248));

        // -------- TITLE PANEL (NORTH) --------
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        titlePanel.setBorder(new EmptyBorder(20, 10, 10, 10));
        titlePanel.setOpaque(false);

        JLabel title = new JLabel("\uD83D\uDC64 User Management", SwingConstants.CENTER);
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

        JButton addUserButton = ButtonComponent.createManagementButton("➕ Add User", "Click to add a new user", e -> {
            UsagerAddForm addForm = new UsagerAddForm();
            addForm.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    populateUserListDisplayPanel();
                }
            });
        });
        controlPanel.add(addUserButton);
        topSectionPanel.add(controlPanel, BorderLayout.NORTH);

        // -------- USER LIST PANEL (CENTER of topSectionPanel) --------
        JPanel listUserPanel = new JPanel();
        listUserPanel.setLayout(new BoxLayout(listUserPanel, BoxLayout.Y_AXIS));
        listUserPanel.setOpaque(false);
        listUserPanel.setBorder(new EmptyBorder(10, 0, 0, 0));

        // --------- HEADER LABELS PANEL --------
        JPanel labelPanel = new JPanel(new GridLayout(1, headers.length));
        labelPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        labelPanel.setBackground(new Color(220, 220, 220));

        for (String header : headers) {
            JLabel label = new JLabel(header, SwingConstants.CENTER);
            label.setFont(new Font("Arial", Font.BOLD, 14));
            label.setBorder(new EmptyBorder(5,5,5,5));
            labelPanel.add(label);
        }
        listUserPanel.add(labelPanel);
        listUserPanel.add(Box.createVerticalStrut(5));

        // -------- SCROLLABLE USER LIST --------
        userListDisplayPanel = new JPanel();
        userListDisplayPanel.setLayout(new BoxLayout(userListDisplayPanel, BoxLayout.Y_AXIS));

        JScrollPane scrollPane = new JScrollPane(userListDisplayPanel);
        scrollPane.setPreferredSize(new Dimension(1100, 500));
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        listUserPanel.add(scrollPane);
        topSectionPanel.add(listUserPanel, BorderLayout.CENTER);

        mainContainer.add(topSectionPanel, BorderLayout.CENTER);

        populateUserListDisplayPanel();

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    /**
     * Populates the user list display panel with user data from the database.
     * If no users are found, it displays a message indicating that the list is empty.
     */
    public static void populateUserListDisplayPanel() {
        userListDisplayPanel.removeAll();
        List<Usager> usagers = new UsagerDAO().getList();

        if (usagers.isEmpty()) {
            JLabel emptyLabel = new JLabel("No users found.", SwingConstants.CENTER);
            emptyLabel.setFont(new Font("Arial", Font.ITALIC, 16));
            emptyLabel.setForeground(Color.GRAY);
            emptyLabel.setBorder(new EmptyBorder(50,0,50,0));
            userListDisplayPanel.add(emptyLabel);
        } else {
            for (Usager user : usagers) {
                JPanel userRow = ListComponent.createUserRow(user, headers.length);
                userListDisplayPanel.add(userRow);
                userListDisplayPanel.add(Box.createVerticalStrut(2));
            }
        }

        userListDisplayPanel.revalidate();
        userListDisplayPanel.repaint();
    }
}