package ui.gui.forms.pret;

import models.Livre;
import models.Usager;
import ui.gui.forms.BaseFormFrame;

import java.time.LocalDate;
import java.util.Date;

/**
 * This abstract class serves as a base for loan-related forms in the application.
 * It extends BaseFormFrame and provides common functionality for loan forms.
 *
 * @version 1.0
 * @author Loris
 */
public abstract class PretForm extends BaseFormFrame {

    /**
     * Constructor for the PretForm.
     * Initializes the form with a title and a panel title text.
     *
     * @param frameTitle       the title of the frame
     * @param panelTitleText   the title text for the panel
     */
    public PretForm(String frameTitle, String panelTitleText) {
        super(frameTitle, panelTitleText);
    }

    @Override
    protected void populateFormFields() {}

    @Override
    protected void populateActionButtons() {}

    @Override
    protected void performMainAction() {}

    /**
     * Validates the loan fields to ensure they meet the required criteria.
     * This method checks if the loan date, duration, effective return date, book, and user are valid.
     *
     * @param dateEmprunt          the loan date
     * @param dureeEmprunt         the duration of the loan
     * @param dateRetourEffective  the effective return date
     * @param livre                the book being loaned
     * @param usager               the user borrowing the book
     * @return true if validation fails, false otherwise
     */
    protected boolean checkLoanFields(Date dateEmprunt, String dureeEmprunt, LocalDate dateRetourEffective, Livre livre, Usager usager) {
        int duree;
        try {
            duree = Integer.parseInt(dureeEmprunt.trim());
        } catch (NumberFormatException e) {
            showValidationError("Duration must be a valid number.");
            return true;
        }

        if (dateEmprunt == null || duree <= 0 || livre == null || usager == null) {
            showValidationError("Please fill in all fields.");
            return true;
        }

        if (duree > 365) {
            showValidationError("Loan duration must be between 1 and 365 days.");
            return true;
        }

        if (dateRetourEffective != null && dateRetourEffective.isBefore(LocalDate.now())) {
            showValidationError("Return date cannot be in the past.");
            return true;
        }

        return false;
    }
}
