import ui.gui.*;
import ui.gui.components.ButtonComponent;
import ui.gui.views.GenerateResultFile;
import ui.gui.views.UserByYearView;
import utils.LogManager;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * Main class for the Alexandria library management application.
 * It initializes the main window and sets up the user interface components.
 *
 * @version 1.0
 * @author Loris
 */
public class Main extends JFrame {

    /**
     * Constructor for the Main class.
     * It initializes the main window with title, icon, layout, and components.
     */
    public Main() {
        setTitle("Alexandria - Library Management");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ImageIcon icon = new ImageIcon("ressources/logo.png");
        setIconImage(icon.getImage());
        Taskbar.getTaskbar().setIconImage(icon.getImage());
        setResizable(false);

        Container mainContainer = getContentPane();
        mainContainer.setLayout(new BorderLayout(0, 15));
        mainContainer.setBackground(new Color(248, 248, 248));

        // -------- TITLE PANEL (NORTH) --------
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        titlePanel.setBorder(new EmptyBorder(20, 10, 10, 10));
        titlePanel.setOpaque(false);

        JLabel titleLabel = new JLabel("Welcome to Alexandria");
        titleLabel.setFont(new Font("Serif", Font.BOLD | Font.ITALIC, 36));
        titleLabel.setForeground(new Color(50, 50, 100));
        titlePanel.add(titleLabel);
        mainContainer.add(titlePanel, BorderLayout.NORTH);

        // -------- CENTER PANEL (CENTER) --------
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setOpaque(false);
        centerPanel.setBorder(new EmptyBorder(10, 20, 20, 20));

        // -------- MODELS MANAGEMENT SECTION --------
        JPanel modelManagementPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        modelManagementPanel.setOpaque(false);

        setupButtons(modelManagementPanel,
                "\uD83D\uDC64 User Management", "Manage library users", e -> new UsagerGUI(),
                "📚 Book Management", "Manage library books", e -> new LivreGUI(),
                "\uD83D\uDD8A Author Management", "Manage book authors", e -> new AuteurGUI(),
                "🎨 Genre Management", "Manage book genres", e -> new GenreGUI(),
                "🤝 Loan Management", "Manage book loans", e -> new PretGUI()
        );

        // -------- SPECIFIC ACTIONS SECTION --------
        JPanel specificActionsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        specificActionsPanel.setOpaque(false);

        setupButtons(specificActionsPanel,
                "🎂 Users by Birth Year", "View a histogram of users by birth year", e -> new UserByYearView(),
                "📊 Generate Result File", "Generate a file with some statistics", e -> GenerateResultFile.generate(),
                "None", "", e -> JOptionPane.showMessageDialog(this, "This feature is not implemented yet."),
                "None", "", e -> JOptionPane.showMessageDialog(this, "This feature is not implemented yet."),
                "None", "", e -> JOptionPane.showMessageDialog(this, "This feature is not implemented yet.")
        );

        // -------- PAGE BUILD (WITH SEPARATORS) --------
        centerPanel.add(Box.createVerticalStrut(15));
        centerPanel.add(modelManagementPanel);
        centerPanel.add(Box.createVerticalStrut(15));
        centerPanel.add(new JSeparator(SwingConstants.HORIZONTAL));
        centerPanel.add(Box.createVerticalStrut(15));
        centerPanel.add(specificActionsPanel);
        centerPanel.add(Box.createVerticalStrut(15));

        mainContainer.add(centerPanel, BorderLayout.CENTER);


        // -------- FOOTER PANEL (SOUTH) --------
        JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        footerPanel.setBorder(new EmptyBorder(5, 10, 5, 10));
        footerPanel.setOpaque(false);
        JLabel statusLabel = new JLabel("© 2025 Alexandria Project");
        statusLabel.setFont(new Font("Arial", Font.ITALIC, 10));
        footerPanel.add(statusLabel);
        mainContainer.add(footerPanel, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    /**
     * Sets up the buttons in the specified panel with the given parameters.
     *
     * @param panel         The panel to add the buttons to.
     * @param btn1Text      Text for the first button.
     * @param btn1Tooltip   Tooltip for the first button.
     * @param btn1Action    Action listener for the first button.
     * @param btn2Text      Text for the second button.
     * @param btn2Tooltip   Tooltip for the second button.
     * @param btn2Action    Action listener for the second button.
     * @param btn3Text      Text for the third button.
     * @param btn3Tooltip   Tooltip for the third button.
     * @param btn3Action    Action listener for the third button.
     * @param btn4Text      Text for the fourth button.
     * @param btn4Tooltip   Tooltip for the fourth button.
     * @param btn4Action    Action listener for the fourth button.
     * @param btn5Text      Text for the fifth button.
     * @param btn5Tooltip   Tooltip for the fifth button.
     * @param btn5Action    Action listener for the fifth button.
     */
    private void setupButtons(JPanel panel, String btn1Text, String btn1Tooltip, ActionListener btn1Action, String btn2Text, String btn2Tooltip, ActionListener btn2Action, String btn3Text, String btn3Tooltip, ActionListener btn3Action, String btn4Text, String btn4Tooltip, ActionListener btn4Action, String btn5Text, String btn5Tooltip, ActionListener btn5Action) {
        JButton action1Button = ButtonComponent.createManagementButton(btn1Text, btn1Tooltip, btn1Action);
        JButton action2Button = ButtonComponent.createManagementButton(btn2Text, btn2Tooltip, btn2Action);
        JButton action3Button = ButtonComponent.createManagementButton(btn3Text, btn3Tooltip, btn3Action);
        JButton action4Button = ButtonComponent.createManagementButton(btn4Text, btn4Tooltip, btn4Action);
        JButton action5Button = ButtonComponent.createManagementButton(btn5Text, btn5Tooltip, btn5Action);

        panel.add(action1Button);
        panel.add(action2Button);
        panel.add(action3Button);
        panel.add(action4Button);
        panel.add(action5Button);
    }

    /**
     * Main method to launch the Alexandria application.
     * It initializes the LogManager and creates an instance of the Main class.
     */
    public static void main(String[] args) {
        LogManager.getInstance();
        new Main();
    }
}
