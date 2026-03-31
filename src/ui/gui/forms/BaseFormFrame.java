package ui.gui.forms;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * This abstract class serves as a base for all form frames in the application.
 * It provides a consistent layout and structure for forms, including a title panel,
 * a dynamic form panel, and a button panel.
 *
 * @version 1.0
 * @author Loris
 */
public abstract class BaseFormFrame extends JFrame {

    protected JPanel titlePanel;
    protected DynamicFormPanel formPanel;
    protected JPanel buttonPanel;
    protected JPanel centerContentPanel;

    /**
     * Constructor for the BaseFormFrame.
     * Initializes the frame with a title and sets up the layout and components.
     *
     * @param frameTitle       the title of the frame
     * @param panelTitleText   the title text for the panel
     */
    public BaseFormFrame(String frameTitle, String panelTitleText) {
        super(frameTitle);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);

        Container mainContainer = getContentPane();
        mainContainer.setLayout(new BorderLayout(0, 10));
        mainContainer.setBackground(new Color(245, 245, 245));

        // Title Panel (NORTH)
        titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel titleLabel = new JLabel(panelTitleText);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titlePanel.setBorder(new EmptyBorder(10, 10, 0, 10));
        titlePanel.setOpaque(false);
        titlePanel.add(titleLabel);
        mainContainer.add(titlePanel, BorderLayout.NORTH);

        // Center Content Panel (CENTER) : This panel will hold the formPanel + any other content (like tables)
        centerContentPanel = new JPanel();
        centerContentPanel.setLayout(new BoxLayout(centerContentPanel, BoxLayout.Y_AXIS));
        centerContentPanel.setBorder(new EmptyBorder(10, 20, 10, 20));
        centerContentPanel.setOpaque(false);
        mainContainer.add(centerContentPanel, BorderLayout.CENTER);

        // Form Panel (added to centerContentPanel)
        formPanel = new DynamicFormPanel(new Insets(8, 8, 8, 8));
        formPanel.setOpaque(false);
        centerContentPanel.add(formPanel); // the form will always be at the top of the section

        // Button Panel (SOUTH)
        buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        buttonPanel.setBorder(new EmptyBorder(0, 20, 15, 20));
        buttonPanel.setOpaque(false);
        mainContainer.add(buttonPanel, BorderLayout.SOUTH);
    }

    /**
     * Returns the center content panel of the frame.
     *
     * @return the DynamicFormPanel instance
     */
    protected JPanel getCenterContentPanel() {
        return centerContentPanel;
    }

    /**
     * Displays a validation error message in a dialog.
     *
     * @param message the error message to display
     */
    protected void showValidationError(String message) {
        JOptionPane.showMessageDialog(this, message, "Validation Error", JOptionPane.WARNING_MESSAGE);
    }

    /**
     * Displays an information message in a dialog.
     *
     * @param message the information message to display
     * @param title   the title of the dialog
     */
    protected void showInfoMessage(String message, String title) {
        JOptionPane.showMessageDialog(this, message, title, JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Displays an error message in a dialog.
     *
     * @param message the error message to display
     * @param title   the title of the dialog
     */
    protected void showErrorMessage(String message, String title) {
        JOptionPane.showMessageDialog(this, message, title, JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Abstract method to populate the form fields.
     * This method should be implemented by subclasses to define the specific fields for the form.
     */
    protected abstract void populateFormFields();

    /**
     * Abstract method to populate the action buttons.
     * This method should be implemented by subclasses to define the specific buttons for the form.
     */
    protected abstract void populateActionButtons();

    /**
     * Abstract method to perform the main action of the form.
     * This method should be implemented by subclasses to define the specific action when the main button is clicked.
     */
    protected abstract void performMainAction();
}