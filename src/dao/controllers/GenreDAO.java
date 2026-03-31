package dao.controllers;

import dao.ConnexionDAO;
import models.Genre;
import utils.LogManager;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * This class is used to manage genres in the database.
 * It provides methods to add, delete, update, and retrieve genres.
 *
 * @version 1.0
 * @author Loris
 */
public class GenreDAO extends ConnexionDAO {

    /**
     * Constructor for GenreDAO.
     * It initializes the connection to the database.
     */
    public GenreDAO() {
        super();
    }

    /**
     * This method adds a new genre to the database.
     *
     * @param nom the name of the genre
     * @return the generated ID of the new genre
     */
    public int add(String nom) {
        Connection con = null;
        CallableStatement cs = null;
        int generatedId = 0;

        try {
            con = getConnection();

            String functionCall = "{ ? = call CREER_GENRE(?) }";
            cs = con.prepareCall(functionCall);

            cs.registerOutParameter(1, java.sql.Types.INTEGER);

            cs.setString(2, nom);

            cs.execute();

            generatedId = cs.getInt(1);
        } catch (Exception e) {
            LogManager.getInstance().error("Erreur lors de l'ajout du genre (" + nom + ") : " + e.getMessage());
        } finally {
            try {
                if (cs != null) cs.close();
                if (con != null) con.close();
            } catch (Exception e) {
                LogManager.getInstance().error("Erreur lors de la fermeture de la connexion : " + e.getMessage());
            }
            if (generatedId != 0) LogManager.getInstance().info("Genre ajouté : " + nom);
        }
        return generatedId;
    }

    /**
     * This method deletes a genre from the database.
     *
     * @param genre the genre to be deleted
     * @return the number of rows affected
     */
    public int delete(Genre genre) {
        Connection con = null;
        PreparedStatement ps = null;
        int returnValue = 0;
        boolean success = true;

        try {
            con = getConnection();

            ps = con.prepareStatement("CALL SUPPRIMER_GENRE(?)");
            ps.setInt(1, genre.getId());

            returnValue = ps.executeUpdate();
        } catch (Exception e) {
            LogManager.getInstance().error("Erreur lors de la suppression du genre (" + genre + ") : " + e.getMessage());
            success = false;
        } finally {
            try {
                if (ps != null) ps.close();
                if (con != null) con.close();
            } catch (Exception e) {
                LogManager.getInstance().error("Erreur lors de la fermeture de la connexion : " + e.getMessage());
            }
            if (success) LogManager.getInstance().info("Genre supprimé : " + genre);
        }
        return returnValue;
    }

    /**
     * This method updates an existing genre in the database.
     *
     * @param genre the genre to be updated
     * @return true if the update was successful, false otherwise
     */
    public boolean update(Genre genre) {
        Connection con = null;
        PreparedStatement ps = null;
        boolean success = true;

        try {
            con = getConnection();

            ps = con.prepareStatement("CALL MODIFIER_GENRE(?, ?)");
            ps.setInt(1, genre.getId());
            ps.setString(2, genre.getNom());

            ps.executeUpdate();
        } catch (Exception e) {
            LogManager.getInstance().error("Erreur lors de la modification du genre (" + genre + ") : " + e.getMessage());
            success = false;
        } finally {
            try {
                if (ps != null) ps.close();
                if (con != null) con.close();
            } catch (Exception e) {
                LogManager.getInstance().error("Erreur lors de la fermeture de la connexion : " + e.getMessage());
            }
            if (success) LogManager.getInstance().info("Genre modifié : " + genre);
        }
        return success;
    }

    /**
     * This method retrieves a genre by its ID.
     *
     * @param id the ID of the genre
     * @param isntCalledByUser if true, the log message will not be printed
     * @return the genre object if found, null otherwise
     */
    public Genre get(int id, boolean isntCalledByUser) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet res;
        Genre genre = null;
        boolean success = true;

        try {
            con = getConnection();
            ps = con.prepareStatement("SELECT * FROM GENRE WHERE ID_GENRE = ?");
            ps.setInt(1, id);
            res = ps.executeQuery();
            if (res.next()) {
                genre = new Genre(
                        res.getInt("ID_GENRE"),
                        res.getString("NOM")
                );
            }
        } catch (Exception e) {
            LogManager.getInstance().error("Erreur lors de la récupération du genre (" + id + ") : " + e.getMessage());
            success = false;
        } finally {
            try {
                if (ps != null) ps.close();
                if (con != null) con.close();
            } catch (Exception e) {
                LogManager.getInstance().error("Erreur lors de la fermeture de la connexion : " + e.getMessage());
            }
            if (success && !isntCalledByUser) LogManager.getInstance().info("Genre récupéré : " + genre);
        }
        return genre;
    }

/**
     * This method retrieves a list of all genres from the database.
     *
     * @return a list of Genre objects
     */
    public List<Genre> getList() {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet res;
        List<Genre> genres = new ArrayList<>();
        boolean success = true;

        try {
            con = getConnection();
            ps = con.prepareStatement("SELECT * FROM GENRE ORDER BY ID_GENRE");
            res = ps.executeQuery();
            while (res.next()) {
                Genre genre = new Genre(
                        res.getInt("ID_GENRE"),
                        res.getString("NOM")
                );
                genres.add(genre);
            }
        } catch (Exception e) {
            LogManager.getInstance().error("Erreur lors de la récupération de la liste des genres : " + e.getMessage());
            success = false;
        } finally {
            try {
                if (ps != null) ps.close();
                if (con != null) con.close();
            } catch (Exception e) {
                LogManager.getInstance().error("Erreur lors de la fermeture de la connexion : " + e.getMessage());
            }
            if (success) LogManager.getInstance().info("Liste des genres récupérée");
        }
        return genres;
    }

    /**
     * Main method for testing the GenreDAO class.
     * It demonstrates adding, updating, and deleting genres.
     */
    public static void main(String[] args) {
        GenreDAO genreDAO = new GenreDAO();

        List<Genre> genres = genreDAO.getList();
        System.out.println("Liste des genres :");
        for (Genre genre : genres) {
            System.out.println(genre);
        }
        System.out.println();

        int generatedId = genreDAO.add("Science-Fiction");
        Genre newGenre = genreDAO.get(generatedId, false);

        newGenre.setNom("Fantastique");
        genreDAO.update(newGenre);

        genreDAO.delete(newGenre);

        // ERRORS
        Genre g = new Genre(100, "Policier");
        genreDAO.delete(g);
        genreDAO.update(g);
    }
}
