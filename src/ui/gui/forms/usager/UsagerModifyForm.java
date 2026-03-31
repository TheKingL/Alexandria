package ui.gui.forms.usager;

import dao.controllers.UsagerDAO;
import models.Usager;
import ui.gui.components.ButtonComponent;
import ui.gui.components.FormComponent;

import javax.swing.*;
import java.awt.*;

/**
 * This class represents a form for modifying an existing user.
 * It extends the UsagerForm class and provides specific functionality for modifying a user.
 *
 * @version 1.0
 * @author Loris
 */
public class UsagerModifyForm extends UsagerForm {

    private final Usager usagerToModify;
    private JTextField nomField;
    private JTextField prenomField;
    private JTextField anneeNaissanceField;
    private JCheckBox tarifReduitCheckBox;

    /**
     * Constructor for the UsagerModifyForm.
     * Initializes the form with a title and a button to modify the user details.
     *
     * @param usager the user to be modified
     */
    public UsagerModifyForm(Usager usager) {
        super("Modify User", "🛠 Modify User Details");
        this.usagerToModify = usager;

        populateFormFields();
        populateActionButtons();

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    @Override
    protected void populateFormFields() {
        nomField = FormComponent.createTextField(usagerToModify.getNom(), 20, true);
        formPanel.addLabeledField("Name :", nomField);

        prenomField = FormComponent.createTextField(usagerToModify.getPrenom(), 20, true);
        formPanel.addLabeledField("First Name :", prenomField);

        anneeNaissanceField = FormComponent.createTextField(String.valueOf(usagerToModify.getAnneeNaissance()), 4, true);
        formPanel.addLabeledField("Birth year :", anneeNaissanceField);

        tarifReduitCheckBox = FormComponent.createCheckBox("Reduced rate", usagerToModify.isTarifReduit(), true);
        formPanel.addSpanningComponent(tarifReduitCheckBox, GridBagConstraints.WEST, GridBagConstraints.NONE);
    }

    @Override
    protected void populateActionButtons() {
        JButton modifyBtn = ButtonComponent.createActionButton("Modify User", "Save changes to this user", e -> performMainAction());
        JButton cancelBtn = ButtonComponent.createActionButton("Cancel", "Discard changes and close", e -> dispose());

        buttonPanel.add(modifyBtn);
        buttonPanel.add(cancelBtn);
    }

    @Override
    protected void performMainAction() {
        String nom = nomField.getText().trim();
        String prenom = prenomField.getText().trim();
        String anneeNaissanceText = anneeNaissanceField.getText().trim();
        boolean tarifReduit = tarifReduitCheckBox.isSelected();

        if (checkUsagerFields(nom, prenom, anneeNaissanceText)) return;

        Usager updatedUsager = new Usager(usagerToModify.getId(), nom, prenom, Integer.parseInt(anneeNaissanceText), tarifReduit);
        UsagerDAO usagerDAO = new UsagerDAO();
        if (!usagerDAO.update(updatedUsager)) showErrorMessage("Error while modifying user (check logs)", "Error");
        else showInfoMessage("User modified successfully", "Success");
        dispose();
    }
}
