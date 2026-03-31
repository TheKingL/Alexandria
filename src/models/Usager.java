package models;

/**
 * This class represents a user in the database.
 * It contains the user's ID, last name, first name, year of birth, and whether they are eligible for a reduced rate.
 *
 * @version 1.0
 * @author Loris
 */
public class Usager {

    private int id;
    private String nom;
    private String prenom;
    private int anneeNaissance;
    private boolean tarifReduit;

    /**
     * Constructor to create a User object with the specified ID, last name, first name, year of birth, and reduced rate status.
     *
     * @param id            the unique identifier for the user
     * @param nom           the last name of the user
     * @param prenom        the first name of the user
     * @param anneeNaissance the year of birth of the user
     * @param tarifReduit   whether the user is eligible for a reduced rate
     */
    public Usager(int id, String nom, String prenom, int anneeNaissance, boolean tarifReduit) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.anneeNaissance = anneeNaissance;
        this.tarifReduit = tarifReduit;
    }

    /**
     * Gets the unique identifier of the user.
     *
     * @return the ID of the user
     */
    public int getId() {
        return id;
    }

    /**
     * Get the last name of the user.
     *
     * @return the last name of the user
     */
    public String getNom() {
        return nom;
    }

    /**
     * Set the last name of the user.
     *
     * @param nom the last name to set for the user
     */
    public void setNom(String nom) {
        this.nom = nom;
    }

    /**
     * Get the first name of the user.
     *
     * @return the first name of the user
     */
    public String getPrenom() {
        return prenom;
    }

    /**
     * Set the first name of the user.
     *
     * @param prenom the first name to set for the user
     */
    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    /**
     * Get the year of birth of the user.
     *
     * @return the year of birth of the user
     */
    public int getAnneeNaissance() {
        return anneeNaissance;
    }

    /**
     * Set the year of birth of the user.
     *
     * @param anneeNaissance the year of birth to set for the user
     */
    public void setAnneeNaissance(int anneeNaissance) {
        this.anneeNaissance = anneeNaissance;
    }

    /**
     * Check if the user is eligible for a reduced rate.
     *
     * @return true if the user is eligible for a reduced rate, false otherwise
     */
    public boolean isTarifReduit() {
        return tarifReduit;
    }

    /**
     * Set the reduced rate status of the user.
     *
     * @param tarifReduit true if the user is eligible for a reduced rate, false otherwise
     */
    public void setTarifReduit(boolean tarifReduit) {
        this.tarifReduit = tarifReduit;
    }

    /**
     * Returns a string representation of the user.
     *
     * @return a string containing the user's last name, first name, and year of birth
     */
    @Override
    public String toString() {
        return String.format("%s %s (%d)", getNom(), getPrenom(), getAnneeNaissance());
    }
}
