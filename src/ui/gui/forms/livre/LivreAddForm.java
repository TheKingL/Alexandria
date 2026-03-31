package ui.gui.forms.livre;

import dao.controllers.AuteurDAO;
import dao.controllers.LivreDAO;
import models.Auteur;
import models.Genre;
import models.Livre;
import ui.gui.components.FormComponent;

import javax.swing.*;

/**
 * This class represents a form for adding a new book to the library system.
 * It extends the LivreForm class and provides specific functionality for adding a book.
 *
 * @version 1.0
 * @author Loris
 */
public class LivreAddForm extends LivreForm {

    private JComponent isbn;
    private JComponent titre;
    private JComponent auteur;
    private JComponent nbPages;
    private JComponent genre;
    private JComponent nbExamplaires;

    /**
     * Constructor for the LivreAddForm.
     * Initializes the form with a title and a button to add a new book.
     */
    public LivreAddForm() {
        super("Add New Book", "⭐ New Book");

        populateFormFields();
        populateActionButtons();

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    @Override
    protected void populateFormFields() {
        isbn = FormComponent.createTextField("", 13, true);
        formPanel.addLabeledField("ISBN :", isbn);

        titre = FormComponent.createTextField("", 20, true);
        formPanel.addLabeledField("Title :", titre);

        auteur = FormComponent.createComboBox(new AuteurDAO().getList(), true);
        formPanel.addLabeledField("Author :", auteur);

        nbPages = FormComponent.createTextField("", 5, true);
        formPanel.addLabeledField("Number of Pages :", nbPages);

        genre = FormComponent.createComboBox(new dao.controllers.GenreDAO().getList(), true);
        formPanel.addLabeledField("Genre :", genre);

        nbExamplaires = FormComponent.createTextField("", 5, true);
        formPanel.addLabeledField("Number of Copies :", nbExamplaires);
    }

    @Override
    protected void populateActionButtons() {
        JButton addButton = ui.gui.components.ButtonComponent.createActionButton("Add Book", "Create a new book", e -> performMainAction());
        buttonPanel.add(addButton);
    }

    @Override
    protected void performMainAction() {
        String isbnValue = ((JTextField) isbn).getText().trim();
        String titreValue = ((JTextField) titre).getText().trim();
        Auteur auteurValue = (Auteur) ((JComboBox<?>) auteur).getSelectedItem();
        String nbPagesValue = ((JTextField) nbPages).getText().trim();
        Genre genreValue = (Genre) ((JComboBox<?>) genre).getSelectedItem();
        String nbExamplairesValue = ((JTextField) nbExamplaires).getText().trim();

        if (checkLivreFields(isbnValue, titreValue, nbPagesValue, nbExamplairesValue)) return;

        LivreDAO livreDAO = new LivreDAO();
        Livre livre = new Livre(Long.parseLong(isbnValue), titreValue, auteurValue, Integer.parseInt(nbPagesValue), genreValue, Integer.parseInt(nbExamplairesValue));

        if (!livreDAO.add(livre)) showErrorMessage("Error while adding book (check logs)", "Error");
        else showInfoMessage("Book added successfully!", "Success");

        dispose();
    }
}
