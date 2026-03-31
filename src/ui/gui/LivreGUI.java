package ui.gui;

import dao.controllers.LivreDAO;
import models.Livre;
import ui.gui.components.ButtonComponent;
import ui.gui.components.ListComponent;
import ui.gui.forms.livre.LivreAddForm;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

/**
 * This class represents the GUI for managing books.
 * It allows users to view, add, and manage books in a library system.
 *
 * @version 1.0
 * @author Loris
 */
public class LivreGUI extends JFrame {

    private static JPanel livreListDisplayPanel;
    private static final String[] headers = new String[]{"ISBN", "TITRE", "AUTEUR", "NB PAGES", "GENRE", "NB EXEMPLAIRES", "ACTIONS"};

    /**
     * Constructor for the LivreGUI.
     * Initializes the GUI components and sets up the layout.
     */
    public LivreGUI() {
        setTitle("Book Management Window");
        setResizable(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // -------- MAIN CONTAINER --------
        Container mainContainer = this.getContentPane();
        mainContainer.setLayout(new BorderLayout(0, 10));
        mainContainer.setBackground(new Color(248, 248, 248));

        // -------- TITLE PANEL (NORTH) --------
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        titlePanel.setBorder(BorderFactory.createEmptyBorder(20, 10, 10, 10));
        titlePanel.setOpaque(false);

        JLabel title = new JLabel("📚 Book Management", SwingConstants.CENTER);
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

        // Implement "Add Book" button similar to other GUIs
        JButton addLivreButton =  ButtonComponent.createManagementButton("➕ Add Book", "Click to add a new book", e -> {
             LivreAddForm livreAddForm = new LivreAddForm();
             livreAddForm.addWindowListener(new WindowAdapter() {
                 @Override
                 public void windowClosed(WindowEvent e) {
                     populateLivreListDisplayPanel();
                 }
             });
        });

        controlPanel.add(addLivreButton);
        topSectionPanel.add(controlPanel, BorderLayout.NORTH);

        // -------- LIVRE LIST PANEL (CENTER of topSectionPanel) --------
        JPanel listLivrePanel = new JPanel();
        listLivrePanel.setLayout(new BoxLayout(listLivrePanel, BoxLayout.Y_AXIS));
        listLivrePanel.setOpaque(false);
        listLivrePanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

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
        listLivrePanel.add(labelPanel);
        listLivrePanel.add(Box.createVerticalStrut(5));

        // -------- SCROLLABLE LIVRE LIST --------
        livreListDisplayPanel = new JPanel();
        livreListDisplayPanel.setLayout(new BoxLayout(livreListDisplayPanel, BoxLayout.Y_AXIS));

        JScrollPane scrollPane = new JScrollPane(livreListDisplayPanel);
        scrollPane.setPreferredSize(new Dimension(1200, 300));
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        listLivrePanel.add(scrollPane);
        topSectionPanel.add(listLivrePanel, BorderLayout.CENTER);

        mainContainer.add(topSectionPanel, BorderLayout.CENTER);

        populateLivreListDisplayPanel();

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    /**
     * Populates the livre list display panel with book entries.
     * It retrieves the list of books from the database and displays them in the panel.
     */
    public static void populateLivreListDisplayPanel() {
        livreListDisplayPanel.removeAll();
        List<Livre> livres = new LivreDAO().getList();

        if (livres.isEmpty()) {
            JLabel emptyLabel = new JLabel("No books found.", SwingConstants.CENTER);
            emptyLabel.setFont(new Font("Arial", Font.ITALIC, 16));
            emptyLabel.setForeground(Color.GRAY);
            emptyLabel.setBorder(BorderFactory.createEmptyBorder(50,0,50,0));
            livreListDisplayPanel.add(emptyLabel);
        } else {
            for (Livre livre : livres) {
                JPanel livreRow = ListComponent.createLivreRow(livre, headers.length);
                livreListDisplayPanel.add(livreRow);
                livreListDisplayPanel.add(Box.createVerticalStrut(2));
            }
        }

        livreListDisplayPanel.revalidate();
        livreListDisplayPanel.repaint();
    }
}
