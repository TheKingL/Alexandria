package ui.gui.forms.pret;

import dao.controllers.PretDAO;
import models.Pret;
import ui.gui.components.ButtonComponent;

import javax.swing.*;
import java.awt.*;

/**
 * This class represents a form for confirming the deletion of a loan (Pret).
 * It extends the PretForm class and provides specific functionality for deleting a loan.
 *
 * @version 1.0
 * @author Loris
 */
public class PretDeleteForm extends PretForm {

    private final Pret pretToDelete;

    /**
     * Constructor for the PretDeleteForm.
     * Initializes the form with a title and a button to confirm deletion of the specified loan.
     *
     * @param pret the loan to be deleted
     */
    public PretDeleteForm(Pret pret) {
        super("Delete Loan Confirmation", "❌ Confirm Deletion");
        this.pretToDelete = pret;

        populateFormFields();
        populateActionButtons();

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    @Override
    protected void populateFormFields() {
        if (pretToDelete == null) {
            formPanel.addSpanningComponent(new JLabel("Error: No loan specified for deletion.", SwingConstants.CENTER), GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL);
            return;
        }

        JLabel messageLabel = new JLabel("<html><div style='text-align: center;'>Are you sure you want to delete the loan:<br><b>" + pretToDelete + "</b> ?</div></html>", SwingConstants.CENTER);
        messageLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        formPanel.addSpanningComponent(messageLabel, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL);
    }

    @Override
    protected void populateActionButtons() {
        JButton yesButton = ButtonComponent.createActionButton("Yes", "Confirm deletion of this loan", e -> performMainAction());
        JButton noButton = ButtonComponent.createActionButton("No", "Cancel deletion and close", e -> dispose());

        buttonPanel.add(yesButton);
        buttonPanel.add(noButton);
    }

    @Override
    protected void performMainAction() {
        if (pretToDelete == null) {
            showErrorMessage("Cannot delete: No loan was specified.", "Deletion Error");
            dispose();
            return;
        }

        PretDAO pretDAO = new PretDAO();
        pretDAO.delete(pretToDelete);
        showInfoMessage("Loan " + pretToDelete.getId() + " has been deleted successfully.", "Deletion Successful");
        dispose();
    }
}
