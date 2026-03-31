package models;

/**
 * This class represents a book in the database.
 * It contains the book's ISBN, title, author, number of pages, genre, and number of copies.
 *
 * @version 1.0
 * @author Loris
 */
public class Livre {

    private final long isbn;
    private String titre;
    private Auteur auteur;
    private int nbPages;
    private Genre genre;
    private int nbExamplaires;

    /**
     * Constructor to create a Book object with the specified ISBN, title, author, number of pages, genre, and number of copies.
     *
     * @param isbn         the unique identifier for the book
     * @param titre        the title of the book
     * @param auteur       the author of the book
     * @param nbPages      the number of pages in the book
     * @param genre        the genre of the book
     * @param nbExamplaires the number of copies available
     */
    public Livre(long isbn, String titre, Auteur auteur, int nbPages, Genre genre, int nbExamplaires) {
        this.isbn = isbn;
        this.titre = titre;
        this.auteur = auteur;
        this.nbPages = nbPages;
        this.genre = genre;
        this.nbExamplaires = nbExamplaires;
    }

    /**
     * Gets the unique identifier of the book.
     *
     * @return the ISBN of the book
     */
    public long getIsbn() {
        return isbn;
    }

    /**
     * Get the title of the book.
     *
     * @return the title of the book
     */
    public String getTitre() {
        return titre;
    }

    /**
     * Set the title of the book.
     *
     * @param titre the title to set for the book
     */
    public void setTitre(String titre) {
        this.titre = titre;
    }

    /**
     * Get the author of the book.
     *
     * @return the author of the book
     */
    public Auteur getAuteur() {
        return auteur;
    }

    /**
     * Set the author of the book.
     *
     * @param auteur the author to set for the book
     */
    public void setAuteur(Auteur auteur) {
        this.auteur = auteur;
    }

    /**
     * Get the number of pages in the book.
     *
     * @return the number of pages in the book
     */
    public int getNbPages() {
        return nbPages;
    }

    /**
     * Set the number of pages in the book.
     *
     * @param nbPages the number of pages to set for the book
     */
    public void setNbPages(int nbPages) {
        this.nbPages = nbPages;
    }

    /**
     * Get the genre of the book.
     *
     * @return the genre of the book
     */
    public Genre getGenre() {
        return genre;
    }

    /**
     * Set the genre of the book.
     *
     * @param genre the genre to set for the book
     */
    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    /**
     * Get the number of copies available for the book.
     *
     * @return the number of copies available
     */
    public int getNbExamplaires() {
        return nbExamplaires;
    }

    /**
     * Set the number of copies available for the book.
     *
     * @param nbExamplaires the number of copies to set for the book
     */
    public void setNbExamplaires(int nbExamplaires) {
        this.nbExamplaires = nbExamplaires;
    }

    /**
     * Returns a string representation of the book.
     *
     * @return a string containing the book's ISBN, title, author, and genre
     */
    @Override
    public String toString() {
        return String.format("%s, title: %s, %s, %s", getIsbn(), getTitre(), getAuteur(), getGenre());
    }
}
