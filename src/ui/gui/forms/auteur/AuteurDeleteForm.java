package ui.gui.forms.auteur;

import dao.controllers.AuteurDAO;
import models.Auteur;
import ui.gui.components.ButtonComponent;

import javax.swing.*;
import java.awt.*;

/**
 * This class represents a form for confirming the deletion of an author.
 * It extends the AuteurForm class to provide a specific UI for deletion confirmation.
 *
 * @version 1.0
 * @author Loris
 */
public class AuteurDeleteForm extends AuteurForm {

    private final Auteur auteurToDelete;

    /**
     * Constructs a new AuteurDeleteForm with the specified author to delete.
     *
     * @param auteur the author to be deleted
     */
    public AuteurDeleteForm(Auteur auteur) {
        super("Delete Author Confirmation", "❌ Confirm Deletion");
        this.auteurToDelete = auteur;

        populateFormFields();
        populateActionButtons();

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    @Override
    protected void populateFormFields() {
        if (auteurToDelete == null) {
            formPanel.addSpanningComponent(new JLabel("Error: No author specified for deletion.", SwingConstants.CENTER), GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL);
            return;
        }

        JLabel messageLabel = new JLabel("<html><div style='text-align: center;'>Are you sure you want to delete the author:<br><b>" + auteurToDelete + "</b> ?</div></html>", SwingConstants.CENTER);
        messageLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        formPanel.addSpanningComponent(messageLabel, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL);
    }

    @Override
    protected void populateActionButtons() {
        JButton yesButton = ButtonComponent.createActionButton("Yes", "Confirm deletion of this author", e -> performMainAction());
        JButton noButton = ButtonComponent.createActionButton("No", "Cancel deletion and close", e -> dispose());

        buttonPanel.add(yesButton);
        buttonPanel.add(noButton);
    }

    @Override
    protected void performMainAction() {
        if (auteurToDelete == null) {
            showErrorMessage("Cannot delete: No author was specified.", "Deletion Error");
            dispose();
            return;
        }

        AuteurDAO auteurDAO = new AuteurDAO();
        auteurDAO.delete(auteurToDelete);
        showInfoMessage("Author " + auteurToDelete.getId() + " has been deleted successfully.", "Deletion Successful");
        dispose();
    }
}
