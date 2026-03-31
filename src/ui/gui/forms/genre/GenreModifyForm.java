package ui.gui.forms.genre;

import dao.controllers.GenreDAO;
import models.Genre;
import ui.gui.components.ButtonComponent;
import ui.gui.components.FormComponent;

import javax.swing.*;

/**
 * This class represents a form for modifying an existing genre.
 * It extends the GenreForm class and provides specific functionality for modifying a genre.
 *
 * @version 1.0
 * @author Loris
 */
public class GenreModifyForm extends GenreForm {

    private final Genre genreToModify;
    private JTextField nomField;

    /**
     * Constructor for the GenreModifyForm.
     * Initializes the form with a title and a button to modify the genre details.
     *
     * @param genre the genre to be modified
     */
    public GenreModifyForm(Genre genre) {
        super("Modify Genre", "🛠 Modify Genre Details");
        this.genreToModify = genre;

        populateFormFields();
        populateActionButtons();

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    @Override
    protected void populateFormFields() {
        nomField = FormComponent.createTextField(genreToModify.getNom(), 20, true);
        formPanel.addLabeledField("Name :", nomField);
    }

    @Override
    protected void populateActionButtons() {
        JButton modifyButton = ButtonComponent.createActionButton("Modify Genre", "Modify the genre details", e -> performMainAction());
        buttonPanel.add(modifyButton);
    }

    @Override
    protected void performMainAction() {
        String nom = nomField.getText().trim();

        if (checkGenreFields(nom)) return;

        genreToModify.setNom(nom);

        GenreDAO genreDAO = new GenreDAO();
        if (!genreDAO.update(genreToModify)) showErrorMessage("Error while modifying genre (check logs)", "Error");
        else showInfoMessage("Genre modified successfully!", "Success");
        dispose();
    }
}
