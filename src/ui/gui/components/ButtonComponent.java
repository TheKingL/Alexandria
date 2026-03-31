package ui.gui.components;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * This class provides methods to create various types of buttons with different styles and functionalities.
 * It includes methods for creating icon buttons, action buttons, styled buttons, and management buttons.
 *
 * @version 1.0
 * @author Loris
 */
public class ButtonComponent {

    /**
     * Creates a button with an icon and a tooltip.
     *
     * @param iconText the text to display on the button
     * @param tooltip  the tooltip text for the button
     * @return a JButton with the specified icon and tooltip
     */
    public static JButton createIconButton(String iconText, String tooltip) {
        JButton button = new JButton(iconText);
        button.setPreferredSize(new Dimension(40, 40));
        button.setFont(new Font("Arial", Font.PLAIN, 16));
        button.setFocusPainted(false);
        button.setToolTipText(tooltip);
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return button;
    }

    /**
     * Creates a button with an icon, a tooltip, and an action listener.
     *
     * @param text           the text to display on the button
     * @param tooltip        the tooltip text for the button
     * @param actionListener the action listener to be added to the button
     * @return a JButton with the specified icon, tooltip, and action listener
     */
    public static JButton createActionButton(String text, String tooltip, ActionListener actionListener) {
        JButton button = new JButton(text);
        if (tooltip != null && !tooltip.isEmpty()) {
            button.setToolTipText(tooltip);
        }
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setFocusPainted(false);
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        if (actionListener != null) {
            button.addActionListener(actionListener);
        }
        return button;
    }

    /**
     * Creates a styled button with various customizable properties.
     *
     * @param text          the text to display on the button
     * @param tooltip       the tooltip text for the button
     * @param preferredSize the preferred size of the button
     * @param font          the font of the button text
     * @param background    the background color of the button
     * @param foreground    the foreground color of the button text
     * @param opaque        whether the button should be opaque
     * @param border        the border of the button
     * @param cursor        the cursor to use when hovering over the button
     * @param actionListener an ActionListener to be added to the button
     * @return a JButton with the specified properties
     */
    public static JButton createStyledButton(String text, String tooltip, Dimension preferredSize, Font font, Color background, Color foreground, boolean opaque, Border border, Cursor cursor, ActionListener actionListener) {
        JButton button = new JButton(text);

        if (tooltip != null && !tooltip.isEmpty()) button.setToolTipText(tooltip);
        if (preferredSize != null) button.setPreferredSize(preferredSize);
        if (font != null) button.setFont(font);
        if (background != null) button.setBackground(background);
        if (foreground != null) button.setForeground(foreground);

        button.setOpaque(opaque);

        if (border != null) {
            button.setBorder(border);
        } else {
            button.setFocusPainted(false);
        }

        if (cursor != null) button.setCursor(cursor);

        if (actionListener != null) button.addActionListener(actionListener);

        return button;
    }

    /**
     * Creates a management button with specific styling and an action listener.
     *
     * @param text           the text to display on the button
     * @param tooltip        the tooltip text for the button
     * @param actionListener the action listener to be added to the button
     * @return a JButton styled for management purposes
     */
    public static JButton createManagementButton(String text, String tooltip, ActionListener actionListener) {
        JButton button = ButtonComponent.createActionButton(text, tooltip, actionListener);
        button.setPreferredSize(new Dimension(180, 60));
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setBackground(new Color(220, 230, 240));
        button.setForeground(Color.DARK_GRAY);
        return button;
    }
}
