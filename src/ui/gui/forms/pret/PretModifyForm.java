package ui.gui.forms.pret;

import dao.controllers.LivreDAO;
import dao.controllers.PretDAO;
import dao.controllers.UsagerDAO;
import models.Livre;
import models.Pret;
import models.Usager;
import org.jdatepicker.JDatePicker;
import ui.gui.components.FormComponent;

import javax.swing.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

/**
 * This class represents a form for modifying an existing loan.
 * It extends the PretForm class and provides specific functionality for modifying a loan.
 *
 * @version 1.0
 * @author Loris
 */
public class PretModifyForm extends PretForm {

    private final Pret pretToModify;
    private JDatePicker dateEmprunt;
    private JComponent dureeEmprunt;
    private JDatePicker dateRetourEffective;
    private JComponent livre;
    private JComponent usager;

    /**
     * Constructor for the PretModifyForm.
     * Initializes the form with a title and a button to modify the loan details.
     *
     * @param pret the loan to be modified
     */
    public PretModifyForm(Pret pret) {
        super("Modify Loan", "🛠 Modify Loan Details");
        this.pretToModify = pret;

        populateFormFields();
        populateActionButtons();

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    @Override
    protected void populateFormFields() {
        dateEmprunt = FormComponent.createDateField(pretToModify.getDateEmprunt(), 20, true);
        formPanel.addLabeledField("Loan Date :", dateEmprunt);

        dureeEmprunt = FormComponent.createTextField(String.valueOf(pretToModify.getDureeEmprunt()), 5, true);
        formPanel.addLabeledField("Duration (days) :", dureeEmprunt);

        dateRetourEffective = FormComponent.createDateField(pretToModify.getDateRetourEffective(), 20, true);
        formPanel.addLabeledField("Effective Return Date :", dateRetourEffective);

        livre = FormComponent.createComboBox(new LivreDAO().getList(), true);
        formPanel.addLabeledField("Book :", livre);

        usager = FormComponent.createComboBox(new UsagerDAO().getList(), true);
        formPanel.addLabeledField("User :", usager);
    }

    @Override
    protected void populateActionButtons() {
        JButton modifyButton = new JButton("Modify Loan");
        JButton cancelButton = new JButton("Cancel");

        modifyButton.addActionListener(e -> performMainAction());
        cancelButton.addActionListener(e -> dispose());

        buttonPanel.add(modifyButton);
        buttonPanel.add(cancelButton);
    }

    @Override
    protected void performMainAction() {
        Date dateEmpruntDate = (Date) dateEmprunt.getModel().getValue();
        String dureeEmpruntText = ((JTextField) dureeEmprunt).getText().trim();
        LocalDate dateRetourEffectiveText = dateRetourEffective.getModel().getValue() != null ? ((Date) dateRetourEffective.getModel().getValue()).toInstant().atZone(ZoneId.systemDefault()).toLocalDate() : null;
        Livre livreSelected = (Livre) ((JComboBox<?>) livre).getSelectedItem();
        Usager usagerSelected = (Usager) ((JComboBox<?>) usager).getSelectedItem();

        if (checkLoanFields(dateEmpruntDate, dureeEmpruntText, dateRetourEffectiveText, livreSelected, usagerSelected)) return;

        pretToModify.setDateEmprunt(dateEmpruntDate);
        pretToModify.setDureeEmprunt(Integer.parseInt(dureeEmpruntText));
        pretToModify.setDateRetourEffective(dateRetourEffectiveText);
        pretToModify.setLivre(livreSelected);
        pretToModify.setUsager(usagerSelected);

        PretDAO pretDAO = new PretDAO();
        if (!pretDAO.update(pretToModify)) showErrorMessage("Error while modifying loan (check logs)", "Error");
        else showInfoMessage("Loan modified successfully!", "Success");
        dispose();
    }
}
