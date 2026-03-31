package ui.gui.views;

import models.Pret;
import org.jdatepicker.JDatePicker;
import ui.gui.components.FormComponent;
import ui.gui.forms.BaseFormFrame;

import javax.swing.*;

/**
 * This class represents a view for displaying the details of a loan.
 * It extends the BaseFormFrame class and provides a specific implementation for viewing a loan's details.
 *
 * @version 1.0
 * @author Loris
 */
public class PretView extends BaseFormFrame {

    private final Pret pretToView;
    private JDatePicker dateEmprunt;
    private JComponent dureeEmprunt;
    private JDatePicker dateRetourEffective;
    private JComponent livre;
    private JComponent usager;

    /**
     * Constructor for PretView.
     * Initializes the view with the details of the specified loan.
     *
     * @param pret The loan to be viewed.
     */
    public PretView(Pret pret) {
        super("Loan Details", "📚 Loan Information");
        this.pretToView = pret;

        populateFormFields();

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    @Override
    protected void populateFormFields() {
        dateEmprunt = FormComponent.createDateField(pretToView.getDateEmprunt(), 20, false);
        formPanel.addLabeledField("Loan Date :", dateEmprunt);

        dureeEmprunt = FormComponent.createTextField(String.valueOf(pretToView.getDureeEmprunt()), 5, false);
        formPanel.addLabeledField("Duration (days) :", dureeEmprunt);

        dateRetourEffective = FormComponent.createDateField(pretToView.getDateRetourEffective(), 20, false);
        formPanel.addLabeledField("Effective Return Date :", dateRetourEffective);

        livre = FormComponent.createTextField(pretToView.getLivre().toString(), 40, false);
        formPanel.addLabeledField("Book :", livre);

        usager = FormComponent.createTextField(pretToView.getUsager().toString(), 40, false);
        formPanel.addLabeledField("User :", usager);
    }

    @Override
    protected void populateActionButtons() {}

    @Override
    protected void performMainAction() {}
}
