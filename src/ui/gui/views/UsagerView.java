package ui.gui.views;

import dao.controllers.PretDAO;
import models.Usager;
import ui.gui.forms.BaseFormFrame;
import ui.gui.components.FormComponent;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 * This class represents a view for displaying the details of a user.
 * It extends the BaseFormFrame class and provides a specific implementation for viewing a user's details.
 *
 * @version 1.0
 * @author Loris
 */
public class UsagerView extends BaseFormFrame {

    private final Usager usagerToView;
    private JTextField nomField;
    private JTextField prenomField;
    private JTextField anneeNaissanceField;
    private JCheckBox tarifReduitCheckBox;
    private JTable loansTable;
    private DefaultTableModel loansTableModel;

    /**
     * Constructor for UsagerView.
     * Initializes the view with the details of the specified user.
     *
     * @param usager The user to be viewed.
     */
    public UsagerView(Usager usager) {
        super("User Details", "👨 User Information");
        this.usagerToView = usager;

        populateFormFields();

        getCenterContentPanel().add(Box.createVerticalStrut(20));
        JLabel loansLabel = FormComponent.createLabel("Loans", 16, true, 0, Color.BLACK, SwingConstants.LEFT);
        JPanel loansLabelPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        loansLabelPanel.setOpaque(false);
        loansLabelPanel.add(loansLabel);
        loansLabelPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, loansLabel.getPreferredSize().height));
        getCenterContentPanel().add(loansLabelPanel);

        populateLoansTable(usagerToView.getId());

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    @Override
    protected void populateFormFields() {
        // Use formPanel (from BaseFormFrame) to add user detail fields
        // Fields are non-editable for a view
        nomField = FormComponent.createTextField(usagerToView.getNom(), 20, false);
        formPanel.addLabeledField("Name :", nomField);

        prenomField = FormComponent.createTextField(usagerToView.getPrenom(), 20, false);
        formPanel.addLabeledField("First Name :", prenomField);

        anneeNaissanceField = FormComponent.createTextField(String.valueOf(usagerToView.getAnneeNaissance()), 4, false);
        formPanel.addLabeledField("Birth year :", anneeNaissanceField);

        tarifReduitCheckBox = FormComponent.createCheckBox("Reduced rate", usagerToView.isTarifReduit(), false);
        formPanel.addSpanningComponent(tarifReduitCheckBox, GridBagConstraints.WEST, GridBagConstraints.NONE);
    }

    @Override
    protected void populateActionButtons() {}

    @Override
    protected void performMainAction() {}

    /**
     * Populates the loans table with data related to the specified user.
     *
     * @param userId The ID of the user whose loans are to be displayed.
     */
    private void populateLoansTable(int userId) {
        String[] columnNames = {"DATE_EMPRUNT", "DATE_RETOUR_EFFECTIVE", "TITRE", "NOM", "PRENOM"};
        loansTableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        loansTable = new JTable(loansTableModel);
        loansTable.getTableHeader().setReorderingAllowed(false);

        PretDAO pretDAO = new PretDAO();
        List<Object[]> loansData = pretDAO.getPretsParUsager(userId);
        for (Object[] rowData : loansData) loansTableModel.addRow(rowData);

        JScrollPane scrollPane = new JScrollPane(loansTable);
        scrollPane.setPreferredSize(new Dimension(600, 200));
        scrollPane.setMaximumSize(new Dimension(Integer.MAX_VALUE, 400));

        getCenterContentPanel().add(scrollPane);
    }
}
