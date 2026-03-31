package ui.gui.forms.usager;

import ui.gui.forms.BaseFormFrame;

import java.time.Year;

/**
 * This abstract class serves as a base for user-related forms in the application.
 * It extends BaseFormFrame and provides common functionality for user forms.
 *
 * @version 1.0
 * @author Loris
 */
public abstract class UsagerForm extends BaseFormFrame {

    /**
     * Constructor for the UsagerForm.
     * Initializes the form with a title and a panel title text.
     *
     * @param frameTitle       the title of the frame
     * @param panelTitleText   the title text for the panel
     */
    public UsagerForm(String frameTitle, String panelTitleText) {
        super(frameTitle, panelTitleText);
    }

    @Override
    protected void populateFormFields() {}

    @Override
    protected void populateActionButtons() {}

    @Override
    protected void performMainAction() {}

    /**
     * Validates the user fields to ensure they meet the required criteria.
     * This method checks if the name, first name, and birth year are not empty,
     * do not exceed 50 characters, and contain only valid characters (letters, spaces, hyphens, apostrophes).
     * SQL injection is prevented by checking for valid characters.
     *
     * @param nom                the user's last name
     * @param prenom             the user's first name
     * @param anneeNaissanceText the user's birth year as a string
     * @return true if validation fails, false otherwise
     */
    protected boolean checkUsagerFields(String nom, String prenom, String anneeNaissanceText) {
        if (nom.isEmpty() || prenom.isEmpty() || anneeNaissanceText.isEmpty()) {
            showValidationError("Please fill in all fields.");
            return true;
        }

        if (nom.length() > 50 || prenom.length() > 50) {
            showValidationError("Name and First Name must not exceed 50 characters.");
            return true;
        }

        int anneeNaissance;
        try {
            anneeNaissance = Integer.parseInt(anneeNaissanceText);
            if (anneeNaissance < Year.now().getValue() - 120 || anneeNaissance > Year.now().getValue()) {
                showValidationError("Birth year is invalid.");
                return true;
            }
        } catch (NumberFormatException ex) {
            showValidationError("Birth year must be a valid number.");
            return true;
        }

        // Check for invalid characters (SQL injection prevention)
        if (!nom.matches("[a-zA-ZÀ-ÿ\\s'-]+") || !prenom.matches("[a-zA-ZÀ-ÿ\\s'-]+") || !anneeNaissanceText.matches("\\d{4}")) {
            showValidationError("Invalid characters in input fields. Name/First Name can contain letters, spaces, hyphens, apostrophes. Year must be 4 digits.");
            return true;
        }

        return false;
    }
}
