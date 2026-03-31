package ui.gui.forms.livre;

import dao.controllers.AuteurDAO;
import dao.controllers.GenreDAO;
import dao.controllers.LivreDAO;
import models.Auteur;
import models.Genre;
import models.Livre;
import ui.gui.components.ButtonComponent;
import ui.gui.components.FormComponent;

import javax.swing.*;

/**
 * This class represents a form for modifying an existing book (Livre).
 * It extends the LivreForm class and provides specific functionality for modifying a book's details.
 *
 * @version 1.0
 * @author Loris
 */
public class LivreModifyForm extends LivreForm {

    private final Livre livreToModify;
    private JComponent isbn;
    private JComponent titre;
    private JComponent auteur;
    private JComponent nbPages;
    private JComponent genre;
    private JComponent nbExamplaires;

    /**
     * Constructor for the LivreModifyForm.
     * Initializes the form with a title and a button to modify the specified book.
     *
     * @param livre the book to be modified
     */
    public LivreModifyForm(Livre livre) {
        super("Modify Book", "\uD83D\uDD8A Modify Book");
        this.livreToModify = livre;

        populateFormFields();
        populateActionButtons();

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    @Override
    protected void populateFormFields() {
        isbn = FormComponent.createTextField(String.valueOf(livreToModify.getIsbn()), 13, false);
        formPanel.addLabeledField("ISBN :", isbn);

        titre = FormComponent.createTextField(livreToModify.getTitre(), 20, true);
        formPanel.addLabeledField("Title :", titre);

        auteur = FormComponent.createComboBox(new AuteurDAO().getList(), true);
        ((JComboBox<?>) auteur).setSelectedItem(livreToModify.getAuteur());
        formPanel.addLabeledField("Author :", auteur);

        nbPages = FormComponent.createTextField(String.valueOf(livreToModify.getNbPages()), 5, true);
        formPanel.addLabeledField("Number of Pages :", nbPages);

        genre = FormComponent.createComboBox(new GenreDAO().getList(), true);
        ((JComboBox<?>) genre).setSelectedItem(livreToModify.getGenre());
        formPanel.addLabeledField("Genre :", genre);

        nbExamplaires = FormComponent.createTextField(String.valueOf(livreToModify.getNbExamplaires()), 5, true);
        formPanel.addLabeledField("Number of Copies :", nbExamplaires);
    }

    @Override
    protected void populateActionButtons() {
        JButton modifyButton = ButtonComponent.createActionButton("Modify Book", "Modify this book", e -> performMainAction());
        buttonPanel.add(modifyButton);
    }

    @Override
    protected void performMainAction() {
        String isbnValue = ((JTextField) isbn).getText().trim(); // Should not change, but getting it for consistency
        String titreValue = ((JTextField) titre).getText().trim();
        Auteur auteurValue = (Auteur) ((JComboBox<?>) auteur).getSelectedItem();
        String nbPagesValue = ((JTextField) nbPages).getText().trim();
        Genre genreValue = (Genre) ((JComboBox<?>) genre).getSelectedItem();
        String nbExamplairesValue = ((JTextField) nbExamplaires).getText().trim();

        if (checkLivreFields(isbnValue, titreValue, nbPagesValue, nbExamplairesValue)) return;

        LivreDAO livreDAO = new LivreDAO();
        Livre updatedLivre = new Livre(Long.parseLong(isbnValue), titreValue, auteurValue, Integer.parseInt(nbPagesValue), genreValue, Integer.parseInt(nbExamplairesValue));

        if (!livreDAO.update(updatedLivre)) showErrorMessage("Error while modifying book (check logs)", "Error");
        else showInfoMessage("Book modified successfully!", "Success");

        dispose();
    }
}
