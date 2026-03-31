package ui.gui.components;

import models.*;
import ui.gui.*;
import ui.gui.forms.auteur.AuteurDeleteForm;
import ui.gui.forms.auteur.AuteurModifyForm;
import ui.gui.forms.genre.GenreDeleteForm;
import ui.gui.forms.genre.GenreModifyForm;
import ui.gui.forms.livre.LivreDeleteForm;
import ui.gui.forms.livre.LivreModifyForm;
import ui.gui.forms.pret.PretDeleteForm;
import ui.gui.forms.pret.PretModifyForm;
import ui.gui.forms.usager.UsagerDeleteForm;
import ui.gui.forms.usager.UsagerModifyForm;
import ui.gui.views.LivreView;
import ui.gui.views.PretView;
import ui.gui.views.UsagerView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * This class provides methods to create various UI components for displaying lists of users, loans, books, genres, and authors.
 * It includes methods to create rows for each type of entity with appropriate actions.
 *
 * @version 1.0
 * @author Loris
 */
public class ListComponent {

    /**
     * Creates a row for displaying user information.
     *
     * @param user    the user to display
     * @param nbCols  the number of columns in the row
     * @return a JPanel containing the user information and action buttons
     */
    public static JPanel createUserRow(Usager user, int nbCols) {
        JPanel userPanel = new JPanel(new GridLayout(1, nbCols));
        userPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));
        userPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        userPanel.setBackground(new Color(248, 248, 248));

        userPanel.add(new JLabel(String.valueOf(user.getId()), SwingConstants.CENTER));
        userPanel.add(new JLabel(user.getNom().length() > 20 ? user.getNom().substring(0, 20) + "..." : user.getNom(), SwingConstants.CENTER));
        userPanel.add(new JLabel(user.getPrenom().length() > 20 ? user.getPrenom().substring(0, 20) + "..." : user.getPrenom(), SwingConstants.CENTER));
        userPanel.add(new JLabel(String.valueOf(user.getAnneeNaissance()), SwingConstants.CENTER));
        userPanel.add(new JLabel(user.isTarifReduit() ? "Yes" : "No", SwingConstants.CENTER));

        JButton viewButton = ButtonComponent.createIconButton("👁", "View user");
        viewButton.addActionListener(e -> new UsagerView(user));

        JButton editButton = ButtonComponent.createIconButton("\uD83D\uDD8A", "Edit user");
        editButton.addActionListener(e -> {
            UsagerModifyForm usagerModifyForm = new UsagerModifyForm(user);
            usagerModifyForm.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    UsagerGUI.populateUserListDisplayPanel();
                }
            });
        });

        JButton deleteButton = ButtonComponent.createIconButton("❌", "Delete user");
        deleteButton.addActionListener(e -> {
            UsagerDeleteForm usagerDeleteForm = new UsagerDeleteForm(user);
            usagerDeleteForm.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    UsagerGUI.populateUserListDisplayPanel();
                }
            });
        });

        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 5));
        actionPanel.setBackground(new Color(240, 240, 240));
        actionPanel.add(viewButton);
        actionPanel.add(editButton);
        actionPanel.add(deleteButton);

        userPanel.add(actionPanel);
        return userPanel;
    }

    /**
     * Creates a row for displaying loan information.
     *
     * @param loan           the loan to display
     * @param columnWidths   an array of integers representing the preferred widths of each column
     * @return a JPanel containing the loan information and action buttons
     */
    public static JPanel createLoanRow(Pret loan, int[] columnWidths) {
        JPanel loanPanel = new JPanel(new GridLayout(1, columnWidths.length, 2, 0));
        loanPanel.setPreferredSize(new Dimension(1000, 50));
        loanPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        loanPanel.setBackground(new Color(248, 248, 248));

        addCellToPanel(loanPanel, String.valueOf(loan.getId()), columnWidths[0]);
        addCellToPanel(loanPanel, loan.getDateEmprunt().toString(), columnWidths[1]);
        addCellToPanel(loanPanel, String.valueOf(loan.getDureeEmprunt()), columnWidths[2]);
        addCellToPanel(loanPanel, loan.getDateRetourEffective() != null ? loan.getDateRetourEffective().toString() : "Not returned", columnWidths[3]);

        String titre = loan.getLivre().getTitre();
        addCellToPanel(loanPanel, titre.length() > 25 ? titre.substring(0, 22) + "..." : titre, columnWidths[4]);

        String nomUsager = loan.getUsager().getNom();
        addCellToPanel(loanPanel, nomUsager.length() > 15 ? nomUsager.substring(0, 12) + "..." : nomUsager, columnWidths[5]);

        String prenomUsager = loan.getUsager().getPrenom();
        addCellToPanel(loanPanel, prenomUsager.length() > 15 ? prenomUsager.substring(0, 12) + "..." : prenomUsager, columnWidths[6]);


        // Panneau d'actions
        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0));
        actionPanel.setOpaque(false);

        JButton viewButton = ButtonComponent.createIconButton("👁", "View loan");
        viewButton.addActionListener(e -> new PretView(loan));

        JButton editButton = ButtonComponent.createIconButton("\uD83D\uDD8A", "Edit loan");
        editButton.addActionListener(e -> {
            PretModifyForm pretModifyForm = new PretModifyForm(loan);
            pretModifyForm.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    PretGUI.populateLoanListDisplayPanel();
                }
            });
        });

        JButton deleteButton = ButtonComponent.createIconButton("❌", "Delete loan");
        deleteButton.addActionListener(e -> {
            PretDeleteForm pretDeleteForm = new PretDeleteForm(loan);
            pretDeleteForm.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    PretGUI.populateLoanListDisplayPanel();
                }
            });
        });

        actionPanel.add(viewButton);
        actionPanel.add(editButton);
        actionPanel.add(deleteButton);

        JPanel actionCellPanel = new JPanel(new BorderLayout());
        actionCellPanel.setPreferredSize(new Dimension(columnWidths[columnWidths.length-1], 45));
        actionCellPanel.setOpaque(false);
        actionCellPanel.add(actionPanel, BorderLayout.CENTER);
        loanPanel.add(actionCellPanel);

        return loanPanel;
    }

    /**
     * Creates a row for displaying book information.
     *
     * @param livre   the book to display
     * @param nbCols  the number of columns in the row
     * @return a JPanel containing the book information and action buttons
     */
    public static JPanel createLivreRow(Livre livre, int nbCols) {
        JPanel livrePanel = new JPanel(new GridLayout(1, nbCols));
        livrePanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));
        livrePanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        livrePanel.setBackground(new Color(248, 248, 248));

        livrePanel.add(new JLabel(String.valueOf(livre.getIsbn()), SwingConstants.CENTER));
        livrePanel.add(new JLabel(livre.getTitre().length() > 20 ? livre.getTitre().substring(0, 20) + "..." : livre.getTitre(), SwingConstants.CENTER));
        livrePanel.add(new JLabel(livre.getAuteur().getNom().charAt(0) + ". " + livre.getAuteur().getPrenom(), SwingConstants.CENTER));
        livrePanel.add(new JLabel(String.valueOf(livre.getNbPages()), SwingConstants.CENTER));
        livrePanel.add(new JLabel(livre.getGenre().getNom(), SwingConstants.CENTER));
        livrePanel.add(new JLabel(String.valueOf(livre.getNbExamplaires()), SwingConstants.CENTER));

        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 5));
        actionPanel.setBackground(new Color(240, 240, 240));

        JButton viewButton = ButtonComponent.createIconButton("👁", "View book");
        viewButton.addActionListener(e -> new LivreView(livre));
        actionPanel.add(viewButton);

        JButton editButton = ButtonComponent.createIconButton("\uD83D\uDD8A", "Edit book");
        editButton.addActionListener(e -> {
             LivreModifyForm livreModifyForm = new LivreModifyForm(livre);
             livreModifyForm.addWindowListener(new WindowAdapter() {
                 @Override
                 public void windowClosed(WindowEvent e) {
                     LivreGUI.populateLivreListDisplayPanel();
                 }
             });
        });

        JButton deleteButton = ButtonComponent.createIconButton("❌", "Delete book");
        deleteButton.addActionListener(e -> {
             LivreDeleteForm livreDeleteForm = new LivreDeleteForm(livre);
             livreDeleteForm.addWindowListener(new WindowAdapter() {
                 @Override
                 public void windowClosed(WindowEvent e) {
                     LivreGUI.populateLivreListDisplayPanel();
                 }
             });
        });

        actionPanel.add(editButton);
        actionPanel.add(deleteButton);

        livrePanel.add(actionPanel);
        return livrePanel;
    }

    /**
     * Creates a row for displaying genre information.
     *
     * @param genre   the genre to display
     * @param nbCols  the number of columns in the row
     * @return a JPanel containing the genre information and action buttons
     */
    public static JPanel createGenreRow(Genre genre, int nbCols) {
        JPanel genrePanel = new JPanel(new GridLayout(1, nbCols));
        genrePanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));
        genrePanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        genrePanel.setBackground(new Color(248, 248, 248));

        genrePanel.add(new JLabel(String.valueOf(genre.getId()), SwingConstants.CENTER));
        genrePanel.add(new JLabel(genre.getNom(), SwingConstants.CENTER));

        JButton editButton = ButtonComponent.createIconButton("\uD83D\uDD8A", "Edit genre");
        editButton.addActionListener(e -> {
            GenreModifyForm genreModifyForm = new GenreModifyForm(genre);
            genreModifyForm.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    GenreGUI.populateGenreListDisplayPanel();
                }
            });
        });

        JButton deleteButton = ButtonComponent.createIconButton("❌", "Delete genre");
        deleteButton.addActionListener(e -> {
            GenreDeleteForm genreDeleteForm = new GenreDeleteForm(genre);
            genreDeleteForm.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    GenreGUI.populateGenreListDisplayPanel();
                }
            });
        });

        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 5));
        actionPanel.setBackground(new Color(240, 240, 240));
        actionPanel.add(editButton);
        actionPanel.add(deleteButton);

        genrePanel.add(actionPanel);
        return genrePanel;
    }

    /**
     * Creates a row for displaying author information.
     *
     * @param auteur  the author to display
     * @param nbCols  the number of columns in the row
     * @return a JPanel containing the author information and action buttons
     */
    public static JPanel createAuteurRow(Auteur auteur, int nbCols) {
        JPanel auteurPanel = new JPanel(new GridLayout(1, nbCols));
        auteurPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));
        auteurPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        auteurPanel.setBackground(new Color(248, 248, 248));

        auteurPanel.add(new JLabel(String.valueOf(auteur.getId()), SwingConstants.CENTER));
        auteurPanel.add(new JLabel(auteur.getNom(), SwingConstants.CENTER));
        auteurPanel.add(new JLabel(auteur.getPrenom(), SwingConstants.CENTER));

        JButton editButton = ButtonComponent.createIconButton("\uD83D\uDD8A", "Edit author");
        editButton.addActionListener(e -> {
            AuteurModifyForm auteurModifyForm = new AuteurModifyForm(auteur);
            auteurModifyForm.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    AuteurGUI.populateAuteurListDisplayPanel();
                }
            });
        });

        JButton deleteButton = ButtonComponent.createIconButton("❌", "Delete author");
        deleteButton.addActionListener(e -> {
            AuteurDeleteForm auteurDeleteForm = new AuteurDeleteForm(auteur);
            auteurDeleteForm.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    AuteurGUI.populateAuteurListDisplayPanel();
                }
            });
        });

        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 5));
        actionPanel.setBackground(new Color(240, 240, 240));
        actionPanel.add(editButton);
        actionPanel.add(deleteButton);

        auteurPanel.add(actionPanel);
        return auteurPanel;
    }

    /**
     * Adds a cell with the specified text to the parent panel with a preferred width.
     *
     * @param parentPanel    the parent panel to which the cell will be added
     * @param text           the text to display in the cell
     * @param preferredWidth the preferred width of the cell
     */
    private static void addCellToPanel(JPanel parentPanel, String text, int preferredWidth) {
        JPanel cellPanel = new JPanel(new BorderLayout());
        cellPanel.setPreferredSize(new Dimension(preferredWidth, 50));
        cellPanel.setOpaque(false);

        JLabel label = new JLabel(text, SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.PLAIN, 12));
        cellPanel.add(label, BorderLayout.CENTER);
        parentPanel.add(cellPanel);
    }
}
