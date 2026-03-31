package ui.gui.forms.genre;

import dao.controllers.GenreDAO;
import models.Genre;
import ui.gui.components.ButtonComponent;

import javax.swing.*;
import java.awt.*;

/**
 * This class represents a form for confirming the deletion of a genre.
 * It extends the GenreForm class and provides specific functionality for deleting a genre.
 *
 * @version 1.0
 * @author Loris
 */
public class GenreDeleteForm extends GenreForm {

    private final Genre genreToDelete;

    /**
     * Constructor for the GenreDeleteForm.
     * Initializes the form with a title and a button to confirm deletion of the specified genre.
     *
     * @param genre the genre to be deleted
     */
    public GenreDeleteForm(Genre genre) {
        super("Delete Genre Confirmation", "❌ Confirm Deletion");
        this.genreToDelete = genre;

        populateFormFields();
        populateActionButtons();

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    @Override
    protected void populateFormFields() {
        if (genreToDelete == null) {
            formPanel.addSpanningComponent(new JLabel("Error: No genre specified for deletion.", SwingConstants.CENTER), GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL);
            return;
        }

        JLabel messageLabel = new JLabel("<html><div style='text-align: center;'>Are you sure you want to delete the genre:<br><b>" + genreToDelete + "</b> ?</div></html>", SwingConstants.CENTER);
        messageLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        formPanel.addSpanningComponent(messageLabel, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL);
    }

    @Override
    protected void populateActionButtons() {
        JButton yesButton = ButtonComponent.createActionButton("Yes", "Confirm deletion of this genre", e -> performMainAction());
        JButton noButton = ButtonComponent.createActionButton("No", "Cancel deletion and close", e -> dispose());

        buttonPanel.add(yesButton);
        buttonPanel.add(noButton);
    }

    @Override
    protected void performMainAction() {
        if (genreToDelete == null) {
            showErrorMessage("Cannot delete: No genre was specified.", "Deletion Error");
            dispose();
            return;
        }

        GenreDAO genreDAO = new GenreDAO();
        genreDAO.delete(genreToDelete);
        showInfoMessage("Genre " + genreToDelete.getId() + " has been deleted successfully.", "Deletion Successful");
        dispose();
    }
}
