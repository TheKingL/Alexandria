package ui.gui;

import dao.controllers.AuteurDAO;
import models.Auteur;
import ui.gui.components.ButtonComponent;
import ui.gui.components.ListComponent;
import ui.gui.forms.auteur.AuteurAddForm;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

/**
 * This class represents the GUI for managing authors.
 * It allows users to view, add, and manage authors in a library system.
 *
 * @version 1.0
 * @author Loris
 */
public class AuteurGUI extends JFrame {

    private static JPanel auteurListDisplayPanel;
    private static final String[] headers = new String[]{"ID", "NAME", "FIRST NAME", "ACTIONS"};

    /**
     * Constructor for the AuteurGUI.
     * Initializes the GUI components and sets up the layout.
     */
    public AuteurGUI() {
        setTitle("Author Management Window");
        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // -------- MAIN CONTAINER --------
        Container mainContainer = this.getContentPane();
        mainContainer.setLayout(new BorderLayout(0, 10));
        mainContainer.setBackground(new Color(248, 248, 248));

        // -------- TITLE PANEL (NORTH) --------
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        titlePanel.setBorder(BorderFactory.createEmptyBorder(20, 10, 10, 10));
        titlePanel.setOpaque(false);

        JLabel title = new JLabel("\uD83D\uDD8A Author Management", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 28));
        title.setForeground(Color.BLACK);
        titlePanel.add(title);
        mainContainer.add(titlePanel, BorderLayout.NORTH);

        // -------- CONTROL PANEL (CENTER) --------
        JPanel topSectionPanel = new JPanel(new BorderLayout());
        topSectionPanel.setOpaque(false);
        topSectionPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 20));

        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        controlPanel.setOpaque(false);

        JButton addAuteurButton = ButtonComponent.createManagementButton("➕ Add Author", "Click to add a new author", e -> {
            AuteurAddForm auteurAddForm = new AuteurAddForm();
            auteurAddForm.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    populateAuteurListDisplayPanel();
                }
            });
        });
        controlPanel.add(addAuteurButton);
        topSectionPanel.add(controlPanel, BorderLayout.NORTH);

        // -------- AUTEUR LIST PANEL (CENTER of topSectionPanel) --------
        JPanel listAuteurPanel = new JPanel();
        listAuteurPanel.setLayout(new BoxLayout(listAuteurPanel, BoxLayout.Y_AXIS));
        listAuteurPanel.setOpaque(false);
        listAuteurPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

        // --------- HEADER LABELS PANEL --------
        JPanel labelPanel = new JPanel(new GridLayout(1, headers.length));
        labelPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        labelPanel.setBackground(new Color(220, 220, 220));

        for (String header : headers) {
            JLabel label = new JLabel(header, SwingConstants.CENTER);
            label.setFont(new Font("Arial", Font.BOLD, 14));
            label.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
            labelPanel.add(label);
        }
        listAuteurPanel.add(labelPanel);
        listAuteurPanel.add(Box.createVerticalStrut(5));

        // -------- SCROLLABLE AUTEUR LIST --------
        auteurListDisplayPanel = new JPanel();
        auteurListDisplayPanel.setLayout(new BoxLayout(auteurListDisplayPanel, BoxLayout.Y_AXIS));

        JScrollPane scrollPane = new JScrollPane(auteurListDisplayPanel);
        scrollPane.setPreferredSize(new Dimension(800, 500));
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        listAuteurPanel.add(scrollPane);
        topSectionPanel.add(listAuteurPanel, BorderLayout.CENTER);

        mainContainer.add(topSectionPanel, BorderLayout.CENTER);

        populateAuteurListDisplayPanel();

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    /**
     * Populates the author list display panel with authors from the database.
     * If no authors are found, it displays a message indicating that the list is empty.
     */
    public static void populateAuteurListDisplayPanel() {
        auteurListDisplayPanel.removeAll();
        List<Auteur> auteurs = new AuteurDAO().getList();

        if (auteurs.isEmpty()) {
            JLabel emptyLabel = new JLabel("No authors found.", SwingConstants.CENTER);
            emptyLabel.setFont(new Font("Arial", Font.ITALIC, 16));
            emptyLabel.setForeground(Color.GRAY);
            emptyLabel.setBorder(BorderFactory.createEmptyBorder(50,0,50,0));
            auteurListDisplayPanel.add(emptyLabel);
        } else {
            for (Auteur auteur : auteurs) {
                JPanel auteurRow = ListComponent.createAuteurRow(auteur, headers.length);
                auteurListDisplayPanel.add(auteurRow);
                auteurListDisplayPanel.add(Box.createVerticalStrut(2));
            }
        }

        auteurListDisplayPanel.revalidate();
        auteurListDisplayPanel.repaint();
    }
}
