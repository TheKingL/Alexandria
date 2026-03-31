package ui.gui.components.formatters;

import javax.swing.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * DateLabelFormatter is a custom formatter for JFormattedTextField that formats
 * date values in the "yyyy-MM-dd" pattern.
 *
 * @version 1.0
 * @author Loris
 */
public class DateLabelFormatter extends JFormattedTextField.AbstractFormatter {

    private final String datePattern = "yyyy-MM-dd";
    private final SimpleDateFormat dateFormatter = new SimpleDateFormat(datePattern);

    /**
     * Convert the given string into an Object representing a date.
     *
     * @param text String to convert
     * @return Object representing the date
     * @throws ParseException if the text cannot be parsed into a date
     */
    @Override
    public Object stringToValue(String text) throws ParseException {
        return dateFormatter.parseObject(text);
    }

    /**
     * Convert the given value into a string representation.
     *
     * @param value Object to convert
     * @return String representation of the date
     */
    @Override
    public String valueToString(Object value) {
        if (value != null) {
            Calendar cal = (Calendar) value;
            return dateFormatter.format(cal.getTime());
        }

        return "";
    }
}
