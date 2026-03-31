package ui.gui.forms.livre;

import ui.gui.forms.BaseFormFrame;

/**
 * This abstract class serves as a base for book-related forms in the application.
 * It extends BaseFormFrame and provides common functionality for book forms.
 *
 * @version 1.0
 * @author Loris
 */
public abstract class LivreForm extends BaseFormFrame {

    /**
     * Constructor for the LivreForm.
     * Initializes the form with a title and a panel title text.
     *
     * @param frameTitle       the title of the frame
     * @param panelTitleText   the title text for the panel
     */
    public LivreForm(String frameTitle, String panelTitleText) {
        super(frameTitle, panelTitleText);
    }

    @Override
    protected void populateFormFields() {}

    @Override
    protected void populateActionButtons() {}

    @Override
    protected void performMainAction() {}

    /**
     * Validates the book fields to ensure they meet the required criteria.
     * This method checks if the ISBN, title, number of pages, and number of copies
     * are not empty, do not exceed specified limits, and contain only valid characters.
     * SQL injection is prevented by checking for valid characters.
     *
     * @param isbn          the ISBN of the book
     * @param titre         the title of the book
     * @param nbPages       the number of pages in the book
     * @param nbExamplaires the number of copies of the book
     * @return true if validation fails, false otherwise
     */
    protected boolean checkLivreFields(String isbn, String titre, String nbPages, String nbExamplaires) {
        if (isbn == null || isbn.trim().isEmpty()) {
            showValidationError("ISBN cannot be empty.");
            return true;
        }

        long newIsbn;
        try {
            newIsbn = Long.parseLong(isbn);
        } catch (NumberFormatException e) {
            showValidationError("ISBN must be a valid number.");
            return true;
        }

        if (newIsbn <= 0) {
            showValidationError("ISBN must be a positive number.");
            return true;
        }
        if (isbn.length() > 13) {
            showValidationError("ISBN cannot exceed 13 digits.");
            return true;
        }

        if (titre == null || titre.trim().isEmpty()) {
            showValidationError("Title cannot be empty.");
            return true;
        }
        if (titre.length() > 255) {
            showValidationError("Title cannot exceed 255 characters.");
            return true;
        }
        if (!titre.matches("[a-zA-ZÀ-ÿ\\s'-]+")) {
            showValidationError("Invalid characters in input field. Title can contain letters, spaces, hyphens, apostrophes.");
            return true;
        }

        if (nbPages == null || nbPages.trim().isEmpty()) {
            showValidationError("Number of pages cannot be empty.");
            return true;
        }

        int nbPagesInt;
        try {
            nbPagesInt = Integer.parseInt(nbPages);
        } catch (NumberFormatException e) {
            showValidationError("Number of pages must be a valid number.");
            return true;
        }

        if (nbPagesInt <= 0) {
            showValidationError("Number of pages must be a positive number.");
            return true;
        }
        if (nbPagesInt > 10000) {
            showValidationError("Number of pages cannot exceed 10000.");
            return true;
        }

        if (nbExamplaires == null || nbExamplaires.trim().isEmpty()) {
            showValidationError("Number of copies cannot be empty.");
            return true;
        }

        int nbExamplairesInt;
        try {
            nbExamplairesInt = Integer.parseInt(nbExamplaires);
        } catch (NumberFormatException e) {
            showValidationError("Number of copies must be a valid number.");
            return true;
        }

        if (nbExamplairesInt < 0) {
            showValidationError("Number of copies cannot be negative.");
            return true;
        }
        if (nbExamplairesInt > 1000) {
            showValidationError("Number of copies cannot exceed 1000.");
            return true;
        }

        return false;
    }

}
