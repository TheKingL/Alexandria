package ui.gui.views;

import models.Livre;
import ui.gui.components.FormComponent;
import ui.gui.forms.BaseFormFrame;

import javax.swing.*;

/**
 * This class represents a view for displaying the details of a book.
 * It extends the BaseFormFrame class and provides a specific implementation for viewing a book's details.
 *
 * @version 1.0
 * @author Loris
 */
public class LivreView extends BaseFormFrame {

    private final Livre livreToView;
    private JComponent isbn;
    private JComponent titre;
    private JComponent auteur;
    private JComponent nbPages;
    private JComponent genre;
    private JComponent nbExamplaires;

    /**
     * Constructor for LivreView.
     * Initializes the view with the details of the specified book.
     *
     * @param livre The book to be viewed.
     */
    public LivreView(Livre livre) {
        super("Book Details", "📚 Book Information");
        this.livreToView = livre;

        populateFormFields();

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    @Override
    protected void populateFormFields() {
        isbn = FormComponent.createTextField(String.valueOf(livreToView.getIsbn()), 13, false);
        formPanel.addLabeledField("ISBN :", isbn);

        titre = FormComponent.createTextField(livreToView.getTitre(), 20, false);
        formPanel.addLabeledField("Title :", titre);

        auteur = FormComponent.createTextField(livreToView.getAuteur().toString(), 20, false);
        formPanel.addLabeledField("Author :", auteur);

        nbPages = FormComponent.createTextField(String.valueOf(livreToView.getNbPages()), 5, false);
        formPanel.addLabeledField("Number of Pages :", nbPages);

        genre = FormComponent.createTextField(livreToView.getGenre().toString(), 20, false);
        formPanel.addLabeledField("Genre :", genre);

        nbExamplaires = FormComponent.createTextField(String.valueOf(livreToView.getNbExamplaires()), 5, false);
        formPanel.addLabeledField("Number of Copies :", nbExamplaires);
    }

    @Override
    protected void populateActionButtons() {}

    @Override
    protected void performMainAction() {}
}
