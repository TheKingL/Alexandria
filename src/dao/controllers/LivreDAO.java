package dao.controllers;

import dao.ConnexionDAO;
import models.Auteur;
import models.Genre;
import models.Livre;
import utils.LogManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * This class is used to manage books in the database.
 * It provides methods to add, delete, update, and retrieve books.
 *
 * @version 1.0
 * @author Loris
 */
public class LivreDAO extends ConnexionDAO {

    /**
     * Constructor for LivreDAO.
     * It initializes the connection to the database.
     */
    public LivreDAO() {
        super();
    }

    /**
     * This method adds a new book to the database.
     *
     * @param livre the book to be added
     * @return true if the book was added successfully, false otherwise
     */
    public boolean add(Livre livre) {
        Connection con = null;
        PreparedStatement ps = null;
        boolean success = true;

        try {
            con = getConnection();

            ps = con.prepareStatement("CALL CREER_LIVRE(?, ?, ?, ?, ?, ?)");
            ps.setLong(1, livre.getIsbn());
            ps.setString(2, livre.getTitre());
            ps.setInt(3, livre.getGenre().getId());
            ps.setInt(4, livre.getNbPages());
            ps.setInt(5, livre.getNbExamplaires());
            ps.setInt(6, livre.getAuteur().getId());

            ps.executeUpdate();
        } catch (Exception e) {
            LogManager.getInstance().error("Erreur lors de l'ajout du livre (" + livre + ") : " + e.getMessage());
            success = false;
        } finally {
            try {
                if (ps != null) ps.close();
                if (con != null) con.close();
            } catch (Exception e) {
                LogManager.getInstance().error("Erreur lors de la fermeture de la connexion : " + e.getMessage());
            }
            if (success) LogManager.getInstance().info("Livre ajouté : " + livre);
        }
        return success;
    }

    /**
     * This method deletes a book from the database.
     *
     * @param livre the book to be deleted
     * @return the number of rows affected by the deletion
     */
    public int delete(Livre livre) {
        Connection con = null;
        PreparedStatement ps = null;
        int returnValue = 0;
        boolean success = true;

        try {
            con = getConnection();

            ps = con.prepareStatement("CALL SUPPRIMER_LIVRE(?)");
            ps.setLong(1, livre.getIsbn());

            returnValue = ps.executeUpdate();
        } catch (Exception e) {
            LogManager.getInstance().error("Erreur lors de la suppression du livre (" + livre + ") : " + e.getMessage());
            success = false;
        } finally {
            try {
                if (ps != null) ps.close();
                if (con != null) con.close();
            } catch (Exception e) {
                LogManager.getInstance().error("Erreur lors de la fermeture de la connexion : " + e.getMessage());
            }
            if (success) LogManager.getInstance().info("Livre supprimé : " + livre);
        }
        return returnValue;
    }

    /**
     * This method updates an existing book in the database.
     *
     * @param livre the book to be updated
     * @return true if the update was successful, false otherwise
     */
    public boolean update(Livre livre) {
        Connection con = null;
        PreparedStatement ps = null;
        boolean success = true;

        try {
            con = getConnection();

            ps = con.prepareStatement("CALL MODIFIER_LIVRE(?, ?, ?, ?, ?, ?)");
            ps.setLong(1, livre.getIsbn());
            ps.setString(2, livre.getTitre());
            ps.setInt(3, livre.getGenre().getId());
            ps.setInt(4, livre.getNbPages());
            ps.setInt(5, livre.getNbExamplaires());
            ps.setInt(6, livre.getAuteur().getId());

            ps.executeUpdate();
        } catch (Exception e) {
            LogManager.getInstance().error("Erreur lors de la modification du livre (" + livre + ") : " + e.getMessage());
            success = false;
        } finally {
            try {
                if (ps != null) ps.close();
                if (con != null) con.close();
            } catch (Exception e) {
                LogManager.getInstance().error("Erreur lors de la fermeture de la connexion : " +e.getMessage());
            }
            if (success) LogManager.getInstance().info("Livre modifié : " + livre);
        }
        return success;
    }

    /**
     * This method retrieves a book from the database by its ISBN.
     *
     * @param isbn the ISBN of the book to be retrieved
     * @return the book with the specified ISBN, or null if not found
     */
    public Livre get(long isbn) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet res = null;
        Livre livre = null;
        boolean success = true;

        try {
            con = getConnection();
            ps = con.prepareStatement("SELECT ISBN, TITRE, NB_PAGES, NB_EXEMPLAIRES, A.ID_AUTEUR, A.NOM AS NAME, A.PRENOM, G.ID_GENRE, G.NOM FROM LIVRE L JOIN AUTEUR A ON L.ID_AUTEUR = L.ID_AUTEUR JOIN GENRE G ON L.GENRE = G.ID_GENRE WHERE ISBN = ?");
            ps.setLong(1, isbn);
            res = ps.executeQuery();
            if (res.next()) {
                livre = new Livre(
                        res.getLong("ISBN"),
                        res.getString("TITRE"),
                        new Auteur(res.getInt("ID_AUTEUR"), res.getString("NAME"), res.getString("PRENOM")),
                        res.getInt("NB_PAGES"),
                        new Genre(res.getInt("ID_GENRE"), res.getString("NOM")),
                        res.getInt("NB_EXEMPLAIRES")
                );
            }
        } catch (Exception e) {
            LogManager.getInstance().error("Erreur lors de la récupération du livre (" + isbn + ") : " + e.getMessage());
            success = false;
        } finally {
            try {
                if (ps != null) ps.close();
                if (con != null) con.close();
                if (res != null) res.close();
            } catch (Exception e) {
                LogManager.getInstance().error("Erreur lors de la fermeture de la connexion : " + e.getMessage());
            }
            if (success) LogManager.getInstance().info("Livre récupéré : " + livre);
        }
        return livre;
    }

    /**
     * This method retrieves a list of all books from the database.
     *
     * @return a list of Livre objects
     */
    public List<Livre> getList() {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet res = null;
        List<Livre> livres = new ArrayList<>();
        boolean success = true;

        try {
            con = getConnection();
            ps = con.prepareStatement("SELECT ISBN, TITRE, NB_PAGES, NB_EXEMPLAIRES, A.ID_AUTEUR, A.NOM AS NAME, A.PRENOM, G.ID_GENRE, G.NOM FROM LIVRE L JOIN AUTEUR A ON L.ID_AUTEUR = A.ID_AUTEUR JOIN GENRE G ON L.GENRE = G.ID_GENRE ORDER BY ISBN");
            res = ps.executeQuery();
            while (res.next()) {
                Livre livre = new Livre(
                        res.getLong("ISBN"),
                        res.getString("TITRE"),
                        new Auteur(res.getInt("ID_AUTEUR"), res.getString("NAME"), res.getString("PRENOM")),
                        res.getInt("NB_PAGES"),
                        new Genre(res.getInt("ID_GENRE"), res.getString("NOM")),
                        res.getInt("NB_EXEMPLAIRES")
                );
                livres.add(livre);
            }
        } catch (Exception e) {
            LogManager.getInstance().error("Erreur lors de la récupération de la liste des livres : " + e.getMessage());
            success = false;
        } finally {
            try {
                if (ps != null) ps.close();
                if (con != null) con.close();
                if (res != null) res.close();
            } catch (Exception e) {
                LogManager.getInstance().error("Erreur lors de la fermeture de la connexion : " + e.getMessage());
            }
            if (success) LogManager.getInstance().info("Liste des livres récupérée");
        }
        return livres;
    }

    /**
     * This method counts the number of books that contain a specific word in their title.
     *
     * @param word the word to search for in book titles
     * @return the count of books containing the specified word
     */
    public int countBooksContainingWord(String word) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet res = null;
        int count = 0;
        boolean success = true;

        try {
            con = getConnection();
            ps = con.prepareStatement("SELECT COUNT(*) AS NB_LIVRES FROM LIVRE WHERE UPPER(TITRE) LIKE UPPER(?)");
            ps.setString(1, "%" + word + "%");
            res = ps.executeQuery();
            if (res.next()) {
                count = res.getInt("NB_LIVRES");
            }
        } catch (Exception e) {
            LogManager.getInstance().error("Erreur lors du comptage des livres contenant le mot (" + word + ") : " + e.getMessage());
            success = false;
        } finally {
            try {
                if (ps != null) ps.close();
                if (con != null) con.close();
                if (res != null) res.close();
            } catch (Exception e) {
                LogManager.getInstance().error("Erreur lors de la fermeture de la connexion : " + e.getMessage());
            }
            if (success) LogManager.getInstance().info("Nombre de livres contenant le mot '" + word + "' : " + count);
        }
        return count;
    }

    /**
     * Main method for testing the LivreDAO class.
     * It demonstrates adding, updating, deleting, and retrieving books.
     */
    public static void main(String[] args) {
        LivreDAO livreDAO = new LivreDAO();
        AuteurDAO auteurDAO = new AuteurDAO();
        GenreDAO genreDAO = new GenreDAO();

        List<Livre> livres = livreDAO.getList();
        System.out.println("Liste des livres :");
        for (Livre livre : livres) {
            System.out.println(livre);
        }
        System.out.println();

        Livre newLivre = new Livre(
                9782266105149L,
                "Le Seigneur des Anneaux",
                auteurDAO.get(2, true),
                1200,
                genreDAO.get(2, true),
                5
        );
        livreDAO.add(newLivre);

        newLivre.setNbExamplaires(10);
        livreDAO.update(newLivre);

        livreDAO.delete(newLivre);

        // ERRORS
        Livre l = new Livre(
                9782070612878L,
                "1984",
                auteurDAO.get(1, true),
                328,
                genreDAO.get(1, true),
                5
        );
        livreDAO.delete(l);
        livreDAO.update(l);
    }
}
