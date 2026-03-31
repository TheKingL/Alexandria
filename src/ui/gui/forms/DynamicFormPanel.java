package ui.gui.forms;

import org.jdatepicker.JDateComponent;
import ui.gui.components.FormComponent;

import javax.swing.*;
import java.awt.*;

/**
 * This class represents a dynamic form panel that allows adding labeled fields dynamically.
 * It uses GridBagLayout to arrange components in a flexible grid.
 *
 * @version 1.0
 * @author Loris
 */
public class DynamicFormPanel extends JPanel {
    private final GridBagConstraints gbc;
    private int currentRow = 0;

    /**
     * Constructor for the DynamicFormPanel.
     * Initializes the panel with a GridBagLayout and a default set of insets.
     *
     * @param defaultInsets the default insets to be used for the components
     */
    public DynamicFormPanel(Insets defaultInsets) {
        setLayout(new GridBagLayout());
        gbc = new GridBagConstraints();
        gbc.insets = defaultInsets;
    }

    /**
     * Adds a labeled field to the form panel.
     *
     * @param labelText      the text for the label
     * @param fieldComponent the component to be labeled
     */
    public void addLabeledField(String labelText, JComponent fieldComponent) {
        addLabeledField(labelText, fieldComponent, GridBagConstraints.HORIZONTAL);
    }

    /**
     * Adds a labeled field to the form panel with a specific fill constraint.
     *
     * @param labelText      the text for the label
     * @param dateComponent  the JDateComponent to be labeled
     */
    public void addLabeledField(String labelText, JDateComponent dateComponent) {
        JComponent fieldComponent = (JComponent) dateComponent;
        addLabeledField(labelText, fieldComponent, GridBagConstraints.HORIZONTAL);
    }

    /**
     * Adds a labeled field to the form panel with a specific fill constraint.
     *
     * @param labelText      the text for the label
     * @param fieldComponent the component to be labeled
     * @param fieldFill      the fill constraint for the field component
     */
    public void addLabeledField(String labelText, JComponent fieldComponent, int fieldFill) {
        // Label
        gbc.gridx = 0;
        gbc.gridy = currentRow;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.weightx = 0.1;
        gbc.fill = GridBagConstraints.NONE;
        gbc.gridwidth = 1;
        add(FormComponent.createLabel(labelText), gbc);

        // Field
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.weightx = 0.9;
        gbc.fill = fieldFill;
        add(fieldComponent, gbc);

        currentRow++;
    }

    /**
     * Adds a component that spans the entire width of the form panel.
     *
     * @param component the component to be added
     * @param anchor    the anchor constraint for the component
     * @param fill      the fill constraint for the component
     */
    public void addSpanningComponent(JComponent component, int anchor, int fill) {
        gbc.gridx = 0;
        gbc.gridy = currentRow;
        gbc.gridwidth = 2;
        gbc.anchor = anchor;
        gbc.weightx = 1.0;
        gbc.fill = fill;
        add(component, gbc);

        currentRow++;
        gbc.gridwidth = 1;
    }
}
