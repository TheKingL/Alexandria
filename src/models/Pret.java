package models;

import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;

/**
 * This class represents a loan in the database.
 * It contains the loan's ID, date of borrowing, duration of the loan, effective return date,
 * the book being borrowed, and the user who borrowed it.
 *
 * @version 1.0
 * @author Loris
 */
public class Pret {

    private int id;
    private Date dateEmprunt;
    private int dureeEmprunt;
    private LocalDate dateRetourEffective;
    private Livre livre;
    private Usager usager;

    /**
     * Constructor to create a Loan object with the specified ID, date of borrowing,
     * duration of the loan, effective return date, book, and user.
     *
     * @param id                the unique identifier for the loan
     * @param dateEmprunt       the date when the book was borrowed
     * @param dureeEmprunt      the duration of the loan in days
     * @param dateRetourEffective the actual return date of the book
     * @param livre             the book being borrowed
     * @param usager            the user who borrowed the book
     */
    public Pret(int id, Date dateEmprunt, int dureeEmprunt, LocalDate dateRetourEffective, Livre livre, Usager usager) {
        this.id = id;
        this.dateEmprunt = dateEmprunt;
        this.dureeEmprunt = dureeEmprunt;
        this.dateRetourEffective = dateRetourEffective;
        this.livre = livre;
        this.usager = usager;
    }

    /**
     * Gets the unique identifier of the loan.
     *
     * @return the ID of the loan
     */
    public int getId() {
        return id;
    }

    /**
     * Set the unique identifier of the loan.
     *
     * @param id the ID to set for the loan
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets the date when the book was borrowed.
     *
     * @return the date of borrowing
     */
    public Date getDateEmprunt() {
        return dateEmprunt;
    }

    /**
     * Set the date when the book was borrowed.
     *
     * @param dateEmprunt the date to set for the borrowing
     */
    public void setDateEmprunt(Date dateEmprunt) {
        this.dateEmprunt = dateEmprunt;
    }

    /**
     * Gets the duration of the loan in days.
     *
     * @return the duration of the loan
     */
    public int getDureeEmprunt() {
        return dureeEmprunt;
    }

    /**
     * Set the duration of the loan in days.
     *
     * @param dureeEmprunt the duration to set for the loan
     */
    public void setDureeEmprunt(int dureeEmprunt) {
        this.dureeEmprunt = dureeEmprunt;
    }

    /**
     * Gets the actual return date of the book.
     *
     * @return the effective return date
     */
    public LocalDate getDateRetourEffective() {
        return dateRetourEffective;
    }

    /**
     * Set the actual return date of the book.
     *
     * @param dateRetourEffective the date to set for the effective return
     */
    public void setDateRetourEffective(LocalDate dateRetourEffective) {
        this.dateRetourEffective = dateRetourEffective;
    }

    /**
     * Gets the book being borrowed.
     *
     * @return the book being borrowed
     */
    public Livre getLivre() {
        return livre;
    }

    /**
     * Set the book being borrowed.
     *
     * @param livre the book to set for the loan
     */
    public void setLivre(Livre livre) {
        this.livre = livre;
    }

    /**
     * Gets the user who borrowed the book.
     *
     * @return the user who borrowed the book
     */
    public Usager getUsager() {
        return usager;
    }

    /**
     * Set the user who borrowed the book.
     *
     * @param usager the user to set for the loan
     */
    public void setUsager(Usager usager) {
        this.usager = usager;
    }

    /**
     * Returns a string representation of the loan.
     *
     * @return a string containing the loan's ID, book, user, and date of borrowing
     */
    @Override
    public String toString() {
        return String.format("Pret n°%s, Livre: [%s], Usager: [%s], Emprunté le: %s", getId(), getLivre(), getUsager(), getDateEmprunt());
    }
}
