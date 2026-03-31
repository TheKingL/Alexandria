package ui.gui.forms.usager;

import dao.controllers.UsagerDAO;
import ui.gui.components.ButtonComponent;
import ui.gui.components.FormComponent;

import javax.swing.*;
import java.awt.*;

/**
 * This class represents a form for adding a new user (usager).
 * It extends the UsagerForm class and provides specific functionality for adding a user.
 *
 * @version 1.0
 * @author Loris
 */
public class UsagerAddForm extends UsagerForm {

    private JTextField nomField;
    private JTextField prenomField;
    private JTextField anneeNaissanceField;
    private JCheckBox tarifReduitCheckBox;

    /**
     * Constructor for the UsagerAddForm.
     * Initializes the form with a title and a button to add a new user.
     */
    public UsagerAddForm() {
        super("Add User", "⭐ New User");

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

        anneeNaissanceField = FormComponent.createTextField("", 4, true);
        formPanel.addLabeledField("Birth year :", anneeNaissanceField);

        tarifReduitCheckBox = FormComponent.createCheckBox("Reduced rate", false, true);
        formPanel.addSpanningComponent(tarifReduitCheckBox, GridBagConstraints.WEST, GridBagConstraints.NONE);
    }

    @Override
    protected void populateActionButtons() {
        JButton addButton = ButtonComponent.createActionButton("Add User", "Create a new user", e -> performMainAction());
        buttonPanel.add(addButton);
    }

    @Override
    protected void performMainAction() {
        String nom = nomField.getText().trim();
        String prenom = prenomField.getText().trim();
        String anneeNaissanceText = anneeNaissanceField.getText().trim();
        boolean tarifReduit = tarifReduitCheckBox.isSelected();

        if (checkUsagerFields(nom, prenom, anneeNaissanceText)) return;

        UsagerDAO usagerDAO = new UsagerDAO();
        int success = usagerDAO.add(nom, prenom, Integer.parseInt(anneeNaissanceText), tarifReduit);

        if (success == 0) showErrorMessage("Error while adding user (check logs)", "Error");
        else showInfoMessage("User added successfully!", "Success");
        dispose();
    }
}