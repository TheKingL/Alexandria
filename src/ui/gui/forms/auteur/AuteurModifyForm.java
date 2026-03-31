package ui.gui.forms.auteur;

import dao.controllers.AuteurDAO;
import models.Auteur;

import javax.swing.*;

/**
 * This class represents a form for modifying an existing author.
 * It extends the AuteurForm class to provide specific functionality for modifying an author's details.
 *
 * @version 1.0
 * @author Loris
 */
public class AuteurModifyForm extends AuteurForm {

    private final Auteur auteurToDelete;
    private JTextField nomField;
    private JTextField prenomField;

    /**
     * Constructor for the AuteurModifyForm.
     * It initializes the form with the given author details and sets up the UI components.
     *
     * @param auteur The author to be modified.
     */
    public AuteurModifyForm(Auteur auteur) {
        super("Modify Author", "\uD83D\uDD8A Modify Author");
        this.auteurToDelete = auteur;

        populateFormFields();
        populateActionButtons();

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    @Override
    protected void populateFormFields() {
        nomField = new JTextField(auteurToDelete.getNom(), 20);
        formPanel.addLabeledField("Name :", nomField);

        prenomField = new JTextField(auteurToDelete.getPrenom(), 20);
        formPanel.addLabeledField("First Name :", prenomField);
    }

    @Override
    protected void populateActionButtons() {
        JButton modifyButton = new JButton("Modify Author");
        modifyButton.addActionListener(e -> performMainAction());
        buttonPanel.add(modifyButton);
    }

    @Override
    protected void performMainAction() {
        String nom = nomField.getText().trim();
        String prenom = prenomField.getText().trim();

        if (checkAuteurFields(nom, prenom)) return;

        AuteurDAO auteurDAO = new AuteurDAO();
        auteurToDelete.setNom(nom);
        auteurToDelete.setPrenom(prenom);

        if (!auteurDAO.update(auteurToDelete)) showErrorMessage("Error while modifying author (check logs)", "Error");
        else showInfoMessage("Author modified successfully!", "Success");
        dispose();
    }
}
