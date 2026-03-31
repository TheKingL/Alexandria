package ui.gui.forms.usager;

import dao.controllers.UsagerDAO;
import models.Usager;
import ui.gui.components.ButtonComponent;

import javax.swing.*;
import java.awt.*;

/**
 * This class represents a form for confirming the deletion of a user.
 * It extends the UsagerForm class and provides specific functionality for deleting a user.
 *
 * @version 1.0
 * @author Loris
 */
public class UsagerDeleteForm extends UsagerForm {

    private final Usager usagerToDelete;

    /**
     * Constructor for the UsagerDeleteForm.
     * Initializes the form with a title and a button to confirm deletion of the specified user.
     *
     * @param usager the user to be deleted
     */
    public UsagerDeleteForm(Usager usager) {
        super("Delete User Confirmation", "❌ Confirm Deletion");
        this.usagerToDelete = usager;

        populateFormFields();
        populateActionButtons();

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    @Override
    protected void populateFormFields() {
        if (usagerToDelete == null) {
            formPanel.addSpanningComponent(new JLabel("Error: No user specified for deletion.", SwingConstants.CENTER), GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL);
            return;
        }

        JLabel messageLabel = new JLabel("<html><div style='text-align: center;'>Are you sure you want to delete the user:<br><b>" + usagerToDelete + "</b> ?</div></html>", SwingConstants.CENTER);
        messageLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        formPanel.addSpanningComponent(messageLabel, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL);
    }

    @Override
    protected void populateActionButtons() {
        JButton yesButton = ButtonComponent.createActionButton("Yes", "Confirm deletion of this user", e -> performMainAction());
        JButton noButton = ButtonComponent.createActionButton("No", "Cancel deletion and close", e -> dispose());

        buttonPanel.add(yesButton);
        buttonPanel.add(noButton);
    }

    @Override
    protected void performMainAction() {
        if (usagerToDelete == null) {
            showErrorMessage("Cannot delete: No user was specified.", "Deletion Error");
            dispose();
            return;
        }

        UsagerDAO usagerDAO = new UsagerDAO();
        usagerDAO.delete(usagerToDelete);
        showInfoMessage("User " + usagerToDelete.getNom() + " has been deleted successfully.", "Deletion Successful");
        dispose();
    }
}