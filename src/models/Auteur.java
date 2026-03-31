package models;

/**
 * This class represents an author in the database
 * It contains the author's ID, last name, and first name.
 *
 * @version 1.0
 * @author Loris
 */
public class Auteur {

    private int id;
    private String nom;
    private String prenom;

    /**
     * Constructor to create an Auteur object with the specified ID, last name, and first name.
     *
     * @param id     the unique identifier for the author
     * @param nom    the last name of the author
     * @param prenom the first name of the author
     */
    public Auteur(int id, String nom, String prenom) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
    }

    /**
     * Gets the unique identifier of the author.
     *
     * @return the ID of the author
     */
    public int getId() {
        return id;
    }

    /**
     * Get the last name of the author.
     *
     * @return the last name of the author
     */
    public String getNom() {
        return nom;
    }

    /**
     * Set the last name of the author.
     *
     * @param nom the last name to set for the author
     */
    public void setNom(String nom) {
        this.nom = nom;
    }

    /**
     * Get the first name of the author.
     *
     * @return the first name of the author
     */
    public String getPrenom() {
        return prenom;
    }

    /**
     * Set the first name of the author.
     *
     * @param prenom the first name to set for the author
     */
    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    /**
     * Returns a string representation of the author.
     *
     * @return a string containing the author's ID, last name, and first name
     */
    @Override
    public String toString() {
        return String.format("Auteur %s : %s %s", getId(), getNom(), getPrenom());
    }
}