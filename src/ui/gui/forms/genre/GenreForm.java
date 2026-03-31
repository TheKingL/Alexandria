package ui.gui.forms.genre;

import ui.gui.forms.BaseFormFrame;

/**
 * This abstract class serves as a base for genre-related forms in the application.
 * It extends BaseFormFrame and provides common functionality for genre forms.
 *
 * @version 1.0
 * @author Loris
 */
public abstract class GenreForm extends BaseFormFrame {

    /**
     * Constructor for the GenreForm.
     * Initializes the form with a title and a panel title text.
     *
     * @param frameTitle       the title of the frame
     * @param panelTitleText   the title text for the panel
     */
    public GenreForm(String frameTitle, String panelTitleText) {
        super(frameTitle, panelTitleText);
    }

    @Override
    protected void populateFormFields() {}

    @Override
    protected void populateActionButtons() {}

    @Override
    protected void performMainAction() {}

    /**
     * Validates the genre fields to ensure they meet the required criteria.
     * This method checks if the genre name is not empty, does not exceed 50 characters,
     * and contains only valid characters (letters, spaces, hyphens, apostrophes).
     * SQL injection is prevented by checking for valid characters.
     *
     * @param nom the genre name to validate
     * @return true if validation fails, false otherwise
     */
    protected boolean checkGenreFields(String nom) {
        if (nom == null || nom.trim().isEmpty()) {
            showValidationError("Genre name cannot be empty.");
            return true;
        }

        if (nom.length() > 50) {
            showValidationError("Genre name cannot exceed 50 characters.");
            return true;
        }

        if (!nom.matches("[a-zA-ZÀ-ÿ\\s'-]+")) {
            showValidationError("Invalid characters in input field. Name can contain letters, spaces, hyphens, apostrophes.");
            return true;
        }

        return false;
    }
}
