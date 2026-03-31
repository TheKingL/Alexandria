package ui.gui.components;

import org.jdatepicker.JDatePicker;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;
import ui.gui.components.formatters.DateLabelFormatter;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Properties;

/**
 * This class is designed to simplify the creation of UI components for forms.
 *
 * @version 1.0
 * @author Loris
 */
public class FormComponent {

    /**
     * Creates a JLabel with the specified text.
     *
     * @param text the text to display in the label
     * @return a JLabel with the specified text
     */
    public static JLabel createLabel(String text) {
        return new JLabel(text);
    }

    /**
     * Creates a JLabel with the specified text, font size, boldness, padding, text color, and alignment.
     *
     * @param text        the text to display in the label
     * @param fontSize    the font size of the label
     * @param bold        whether the text should be bold
     * @param padding     the padding around the label
     * @param textColor   the color of the text
     * @param alignment   the alignment of the label (e.g., SwingConstants.LEFT, RIGHT, CENTER)
     * @return a JLabel with the specified properties
     */
    public static JLabel createLabel(String text, int fontSize, boolean bold, int padding, Color textColor, int alignment) {
        JLabel label = new JLabel(text, alignment);
        label.setFont(new Font("Arial", bold ? Font.BOLD : Font.PLAIN, fontSize));
        label.setBorder(BorderFactory.createEmptyBorder(padding, padding, padding, padding));
        label.setForeground(textColor);
        return label;
    }

    /**
     * Creates a JTextField with the specified initial value, number of columns, and editability.
     *
     * @param initialValue the initial text to display in the field
     * @param columns      the number of columns for the text field
     * @param editable     whether the text field is editable
     * @return a JTextField with the specified properties
     */
    public static JTextField createTextField(String initialValue, int columns, boolean editable) {
        JTextField textField = new JTextField(initialValue, columns);
        textField.setEditable(editable);
        textField.setFont(new Font("Arial", Font.PLAIN, 12));
        return textField;
    }

    /**
     * Creates a JCheckBox with the specified text, initial selection state, and enabled state.
     *
     * @param text the text to display in the checkbox
     * @param initialSelection the initial selection state of the checkbox
     * @param enabled whether the checkbox is enabled
     * @return a JCheckBox with the specified properties
     */
    public static JCheckBox createCheckBox(String text, boolean initialSelection, boolean enabled) {
        JCheckBox checkBox = new JCheckBox(text, initialSelection);
        checkBox.setEnabled(enabled);
        checkBox.setFont(new Font("Arial", Font.PLAIN, 12));
        return checkBox;
    }

    /**
     * Creates a JDatePicker with the specified initial date, number of columns, and editability.
     *
     * @param initialDate the initial date to display in the date picker
     * @param columns     the number of columns for the date field
     * @param editable    whether the date field is editable
     * @return a JDatePicker with the specified properties
     */
    public static JDatePicker createDateField(Date initialDate, int columns, boolean editable) {
        UtilDateModel model = new UtilDateModel();
        JDatePanelImpl datePanel = new JDatePanelImpl(model, new Properties());
        JDatePickerImpl datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());

        datePicker.setFont(new Font("Arial", Font.PLAIN, 12));
        if (initialDate != null) {
            model.setValue(initialDate);
        }

        datePicker.getJFormattedTextField().setEditable(editable);
        datePicker.getJFormattedTextField().setColumns(columns);
        datePicker.getJFormattedTextField().setBackground(Color.WHITE);
        datePicker.getJFormattedTextField().setForeground(Color.BLACK);
        datePicker.getJFormattedTextField().setOpaque(true);

        return datePicker;
    }

    /**
     * Creates a JDatePicker with the specified initial date, number of columns, and editability.
     *
     * @param initialDate the initial date to display in the date picker
     * @param columns     the number of columns for the date field
     * @param editable    whether the date field is editable
     * @return a JDatePicker with the specified properties
     */
    public static JDatePicker createDateField(LocalDate initialDate, int columns, boolean editable) {
        return createDateField(initialDate != null ? Date.from(initialDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()) : null, columns, editable);
    }

    /**
     * Creates a JComboBox with the specified list of items and editability.
     *
     * @param items    the list of items to display in the combo box
     * @param editable whether the combo box is editable
     * @return a JComboBox with the specified properties
     */
    public static JComboBox<?> createComboBox(List<?> items, boolean editable) {
        JComboBox<Object> comboBox = new JComboBox<>(items.toArray());
        comboBox.setEditable(editable);
        comboBox.setFont(new Font("Arial", Font.PLAIN, 12));
        comboBox.setBackground(Color.WHITE);
        comboBox.setForeground(Color.BLACK);
        comboBox.setPreferredSize(new Dimension(200, 30));
        return comboBox;
    }
}
