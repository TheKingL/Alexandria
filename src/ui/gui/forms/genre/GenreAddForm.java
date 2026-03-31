package ui.gui.forms.genre;

import dao.controllers.GenreDAO;
import ui.gui.components.ButtonComponent;
import ui.gui.components.FormComponent;

import javax.swing.*;

/**
 * This class represents a form for adding a new genre.
 * It extends the GenreForm class and provides specific functionality for adding a genre.
 *
 * @version 1.0
 * @author Loris
 */
public class GenreAddForm extends GenreForm {

    private JTextField nomField;

    /**
     * Constructor for the GenreAddForm.
     * Initializes the form with a title and a button to add a new genre.
     */
    public GenreAddForm() {
        super("Add New Genre", "⭐ New Genre");

        populateFormFields();
        populateActionButtons();

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    @Override
    protected void populateFormFields() {
        nomField = FormComponent.createTextField("", 20, true);
        formPanel.addLabeledField("Name :", nomField);
    }

    @Override
    protected void populateActionButtons() {
        JButton addButton = ButtonComponent.createActionButton("Add Genre", "Create a new genre", e -> performMainAction());
        buttonPanel.add(addButton);
    }

    @Override
    protected void performMainAction() {
        String nom = nomField.getText().trim();

        if (checkGenreFields(nom)) return;

        GenreDAO genreDAO = new GenreDAO();
        int success = genreDAO.add(nom);

        if (success == 0) showErrorMessage("Error while adding genre (check logs)", "Error");
        else showInfoMessage("Genre added successfully!", "Success");
        dispose();
    }
}
