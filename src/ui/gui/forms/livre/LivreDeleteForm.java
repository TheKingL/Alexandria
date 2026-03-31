package ui.gui.forms.livre;

import dao.controllers.LivreDAO;
import models.Livre;
import ui.gui.components.ButtonComponent;

import javax.swing.*;
import java.awt.*;

/**
 * This class represents a form for confirming the deletion of a book.
 * It extends the LivreForm class and provides specific functionality for deleting a book.
 *
 * @version 1.0
 * @author Loris
 */
public class LivreDeleteForm extends LivreForm {

    private final Livre livreToDelete;

    /**
     * Constructor for the LivreDeleteForm.
     * Initializes the form with a title and a button to confirm deletion of the specified book.
     *
     * @param livre the book to be deleted
     */
    public LivreDeleteForm(Livre livre) {
        super("Delete Book Confirmation", "❌ Confirm Deletion");
        this.livreToDelete = livre;

        populateFormFields();
        populateActionButtons();

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    @Override
    protected void populateFormFields() {
        if (livreToDelete == null) {
            formPanel.addSpanningComponent(new JLabel("Error: No book specified for deletion.", SwingConstants.CENTER), GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL);
            return;
        }

        JLabel messageLabel = new JLabel("<html><div style='text-align: center;'>Are you sure you want to delete the book:<br><b>" + livreToDelete + "</b> ?</div></html>", SwingConstants.CENTER);
        messageLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        formPanel.addSpanningComponent(messageLabel, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL);
    }

    @Override
    protected void populateActionButtons() {
        JButton yesButton = ButtonComponent.createActionButton("Yes", "Confirm deletion of this book", e -> performMainAction());
        JButton noButton = ButtonComponent.createActionButton("No", "Cancel deletion and close", e -> dispose());

        buttonPanel.add(yesButton);
        buttonPanel.add(noButton);
    }

    @Override
    protected void performMainAction() {
        if (livreToDelete == null) {
            showErrorMessage("Cannot delete: No book was specified.", "Deletion Error");
            dispose();
            return;
        }

        LivreDAO livreDAO = new LivreDAO();
        livreDAO.delete(livreToDelete);
        showInfoMessage("Book " + livreToDelete.getIsbn() + " has been deleted successfully.", "Deletion Successful");
        dispose();
    }
}
