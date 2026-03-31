package ui.gui.forms.auteur;

import ui.gui.components.ButtonComponent;
import ui.gui.components.FormComponent;

import javax.swing.*;

/**
 * This class represents a form for adding a new author.
 * It extends the AuteurForm class and provides specific implementation for adding an author.
 *
 * @version 1.0
 * @author Loris
 */
public class AuteurAddForm extends AuteurForm {

    private JTextField nomField;
    private JTextField prenomField;

    /**
     * Constructor for the AuteurAddForm.
     * Initializes the form with a title and a button to add a new author.
     */
    public AuteurAddForm() {
        super("Add New Author", "⭐ New Author");

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

        prenomField = FormComponent.createTextField("", 20, true);
        formPanel.addLabeledField("First Name :", prenomField);
    }

    @Override
    protected void populateActionButtons() {
        JButton addButton = ButtonComponent.createActionButton("Add Author", "Create a new author", e -> performMainAction());
        buttonPanel.add(addButton);
    }

    @Override
    protected void performMainAction() {
        String nom = nomField.getText().trim();
        String prenom = prenomField.getText().trim();

        if (checkAuteurFields(nom, prenom)) return;

        dao.controllers.AuteurDAO auteurDAO = new dao.controllers.AuteurDAO();
        int success = auteurDAO.add(nom, prenom);

        if (success == 0) showErrorMessage("Error while adding author (check logs)", "Error");
        else showInfoMessage("Author added successfully!", "Success");
        dispose();
    }
}
