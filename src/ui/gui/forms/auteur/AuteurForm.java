package ui.gui.forms.auteur;

import ui.gui.forms.BaseFormFrame;

/**
 * Abstract class representing a form for managing authors.
 * This class extends BaseFormFrame and provides a structure for author-related forms.
 *
 * @version 1.0
 * @author Loris
 */
public abstract class AuteurForm extends BaseFormFrame {

    /**
     * Constructor for the AuteurForm class.
     *
     * @param frameTitle        the title of the frame
     * @param panelTitleText    the title text for the panel
     */
    public AuteurForm(String frameTitle, String panelTitleText) {
        super(frameTitle, panelTitleText);
    }

    @Override
    protected void populateFormFields() {}

    @Override
    protected void populateActionButtons() {}

    @Override
    protected void performMainAction() {}

    /**
     * Validates the author fields (name and first name).
     * If any field is invalid, it shows an error message and returns true.
     * SQL injection is prevented by checking for valid characters.
     *
     * @param nom    the author's name
     * @param prenom the author's first name
     * @return true if validation fails, false otherwise
     */
    protected boolean checkAuteurFields(String nom, String prenom) {
        if (nom == null || nom.trim().isEmpty()) {
            showValidationError("Author name cannot be empty.");
            return true;
        }

        if (prenom == null || prenom.trim().isEmpty()) {
            showValidationError("Author first name cannot be empty.");
            return true;
        }

        if (nom.length() > 50) {
            showValidationError("Author name cannot exceed 50 characters.");
            return true;
        }

        if (prenom.length() > 50) {
            showValidationError("Author first name cannot exceed 50 characters.");
            return true;
        }

        if (!nom.matches("[a-zA-ZÀ-ÿ\\s'-]+") || !prenom.matches("[a-zA-ZÀ-ÿ\\s'-]+")) {
            showValidationError("Invalid characters in input field. Name can contain letters, spaces, hyphens, apostrophes.");
            return true;
        }

        return false;
    }
}
