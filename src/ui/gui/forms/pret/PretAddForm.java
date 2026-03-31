package ui.gui.forms.pret;

import dao.controllers.LivreDAO;
import dao.controllers.PretDAO;
import dao.controllers.UsagerDAO;
import models.Livre;
import models.Usager;
import org.jdatepicker.JDatePicker;
import ui.gui.components.ButtonComponent;
import ui.gui.components.FormComponent;

import javax.swing.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

/**
 * This class represents a form for adding a new loan (Pret).
 * It extends the PretForm class and provides specific functionality for adding a loan.
 *
 * @version 1.0
 * @author Loris
 */
public class PretAddForm extends PretForm {

    private JComponent dureeEmprunt;
    private JDatePicker dateRetourEffective;
    private JComponent livre;
    private JComponent usager;

    /**
     * Constructor for the PretAddForm.
     * Initializes the form with a title and a button to add a new loan.
     */
    public PretAddForm() {
        super("Add Loan", "⭐ New Loan");

        populateFormFields();
        populateActionButtons();

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    @Override
    protected void populateFormFields() {
        dureeEmprunt = FormComponent.createTextField("", 5, true);
        formPanel.addLabeledField("Duration (days) :", dureeEmprunt);

        dateRetourEffective = FormComponent.createDateField((Date) null, 20, true);
        formPanel.addLabeledField("Effective Return Date :", dateRetourEffective);

        livre = FormComponent.createComboBox(new LivreDAO().getList(), true);
        formPanel.addLabeledField("Book :", livre);

        usager = FormComponent.createComboBox(new UsagerDAO().getList(), true);
        formPanel.addLabeledField("User :", usager);
    }

    @Override
    protected void populateActionButtons() {
        JButton addButton = ButtonComponent.createActionButton("Add Loan", "Create a new loan", e -> performMainAction());
        buttonPanel.add(addButton);
    }

    @Override
    protected void performMainAction() {
        Date dateEmprunt = new Date();
        String dureeEmpruntText = ((JTextField) dureeEmprunt).getText().trim();
        LocalDate dateRetourEffectiveText = dateRetourEffective.getModel().getValue() != null ? ((Date) dateRetourEffective.getModel().getValue()).toInstant().atZone(ZoneId.systemDefault()).toLocalDate() : null;
        Livre livreSelected = (Livre) ((JComboBox<?>) livre).getSelectedItem();
        Usager usagerSelected = (Usager) ((JComboBox<?>) usager).getSelectedItem();

        if (checkLoanFields(dateEmprunt, dureeEmpruntText, dateRetourEffectiveText, livreSelected, usagerSelected)) return;

        PretDAO pretDAO = new PretDAO();
        int success = pretDAO.add(dateEmprunt, Integer.parseInt(dureeEmpruntText), dateRetourEffectiveText, livreSelected, usagerSelected);

        if (success == 0) showErrorMessage("Error while adding loan (check logs)", "Error");
        else showInfoMessage("Loan added successfully!", "Success");
        dispose();
    }
}
