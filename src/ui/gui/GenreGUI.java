package ui.gui;

import dao.controllers.GenreDAO;
import models.Genre;
import ui.gui.components.ButtonComponent;
import ui.gui.components.ListComponent;
import ui.gui.forms.genre.GenreAddForm;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

/**
 * This class represents the GUI for managing genres.
 * It allows users to view, add, and manage genres in a library system.
 *
 * @version 1.0
 * @author Loris
 */
public class GenreGUI extends JFrame {

    private static JPanel genreListDisplayPanel;
    private static final String[] headers = new String[]{"ID", "NAME", "ACTIONS"};

    /**
     * Constructor for the GenreGUI.
     * Initializes the GUI components and sets up the layout.
     */
    public GenreGUI() {
        setTitle("Genre Management Window");
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

        JLabel title = new JLabel("🎨 Genre Management", SwingConstants.CENTER);
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

        JButton addGenreButton = ButtonComponent.createManagementButton("➕ Add Genre", "Click to add a new genre", e -> {
            GenreAddForm genreAddForm = new GenreAddForm();
            genreAddForm.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    populateGenreListDisplayPanel();
                }
            });
        });
        controlPanel.add(addGenreButton);
        topSectionPanel.add(controlPanel, BorderLayout.NORTH);

        // -------- GENRE LIST PANEL (CENTER of topSectionPanel) --------
        JPanel listGenrePanel = new JPanel();
        listGenrePanel.setLayout(new BoxLayout(listGenrePanel, BoxLayout.Y_AXIS));
        listGenrePanel.setOpaque(false);
        listGenrePanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

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
        listGenrePanel.add(labelPanel);
        listGenrePanel.add(Box.createVerticalStrut(5));

        // -------- SCROLLABLE GENRE LIST --------
        genreListDisplayPanel = new JPanel();
        genreListDisplayPanel.setLayout(new BoxLayout(genreListDisplayPanel, BoxLayout.Y_AXIS));

        JScrollPane scrollPane = new JScrollPane(genreListDisplayPanel);
        scrollPane.setPreferredSize(new Dimension(800, 500));
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        listGenrePanel.add(scrollPane);
        topSectionPanel.add(listGenrePanel, BorderLayout.CENTER);

        mainContainer.add(topSectionPanel, BorderLayout.CENTER);

        populateGenreListDisplayPanel();

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    /**
     * Populates the genre list display panel with genres from the database.
     * If no genres are found, it displays a message indicating that the list is empty.
     */
    public static void populateGenreListDisplayPanel() {
        genreListDisplayPanel.removeAll();
        List<Genre> genres = new GenreDAO().getList();

        if (genres.isEmpty()) {
            JLabel emptyLabel = new JLabel("No genres found.", SwingConstants.CENTER);
            emptyLabel.setFont(new Font("Arial", Font.ITALIC, 16));
            emptyLabel.setForeground(Color.GRAY);
            emptyLabel.setBorder(BorderFactory.createEmptyBorder(50,0,50,0));
            genreListDisplayPanel.add(emptyLabel);
        } else {
            for (Genre genre : genres) {
                JPanel genreRow = ListComponent.createGenreRow(genre, headers.length);
                genreListDisplayPanel.add(genreRow);
                genreListDisplayPanel.add(Box.createVerticalStrut(2));
            }
        }

        genreListDisplayPanel.revalidate();
        genreListDisplayPanel.repaint();
    }

}
