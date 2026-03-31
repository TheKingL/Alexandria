package models;

/**
 * This class represents a genre in the database.
 * It contains the genre's ID and name.
 *
 * @version 1.0
 * @author Loris
 */
public class Genre {

    private int id;
    private String nom;

    /**
     * Constructor to create a Genre object with the specified ID and name.
     *
     * @param id  the unique identifier for the genre
     * @param nom the name of the genre
     */
    public Genre(int id, String nom) {
        this.id = id;
        this.nom = nom;
    }

    /**
     * Gets the unique identifier of the genre.
     *
     * @return the ID of the genre
     */
    public int getId() {
        return id;
    }

    /**
     * Get the name of the genre.
     *
     * @return the name of the genre
     */
    public String getNom() {
        return nom;
    }

    /**
     * Set the name of the genre.
     *
     * @param nom the name to set for the genre
     */
    public void setNom(String nom) {
        this.nom = nom;
    }

    /**
     * Returns a string representation of the genre.
     *
     * @return a string containing the genre's ID and name
     */
    @Override
    public String toString() {
        return String.format("Genre %s : %s", getId(), getNom());
    }
}
