package dao.controllers;

import dao.ConnexionDAO;
import models.Auteur;
import utils.LogManager;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * This class is used to manage authors in the database.
 * It provides methods to add, delete, update, and retrieve authors.
 *
 * @version 1.0
 * @author Loris
 */
public class AuteurDAO extends ConnexionDAO {

    /**
     * Constructor for AuteurDAO.
     * It initializes the connection to the database.
     */
    public AuteurDAO() {
        super();
    }

    /**
     * This method adds a new author to the database.
     *
     * @param nom the name of the author
     * @param prenom the first name of the author
     * @return the generated ID of the new author
     */
    public int add(String nom, String prenom) {
        Connection con = null;
        CallableStatement cs = null;
        int generatedId = 0;

        try {
            con = getConnection();

            String functionCall = "{ ? = call CREER_AUTEUR(?, ?) }";
            cs = con.prepareCall(functionCall);

            cs.registerOutParameter(1, java.sql.Types.INTEGER);

            cs.setString(2, nom);
            cs.setString(3, prenom);

            cs.execute();

            generatedId = cs.getInt(1);
        } catch (Exception e) {
            LogManager.getInstance().error("Erreur lors de l'ajout de l'auteur (" + nom + " " + prenom + ") : " +e.getMessage());
        } finally {
            try {
                if (cs != null) cs.close();
                if (con != null) con.close();
            } catch (Exception e) {
                LogManager.getInstance().error("Erreur lors de la fermeture de la connexion : " + e.getMessage());
            }
            if (generatedId != 0) LogManager.getInstance().info("Auteur ajouté : " + nom + " " + prenom);
        }

        return generatedId;
    }

    /**
     * This method deletes an author from the database.
     *
     * @param auteur the author to delete
     * @return the number of rows affected (should be 1 on success)
     */
    public int delete(Auteur auteur) {
        Connection con = null;
        PreparedStatement ps = null;
        int returnValue = 0;
        boolean success = true;

        try {
            con = getConnection();

            ps = con.prepareStatement("CALL SUPPRIMER_AUTEUR(?)");
            ps.setInt(1, auteur.getId());

            returnValue = ps.executeUpdate();

        } catch (Exception e) {
            LogManager.getInstance().error("Erreur lors de la suppression de l'auteur (" + auteur + ") : " +e.getMessage());
            success = false;
        } finally {
            try {
                if (ps != null) ps.close();
                if (con != null) con.close();
            } catch (Exception e) {
                LogManager.getInstance().error("Erreur lors de la fermeture de la connexion : " + e.getMessage());
            }
            if (success) LogManager.getInstance().info("Auteur supprimé : " + auteur);
        }
        return returnValue;
    }

    /**
     * This method updates an existing author in the database.
     *
     * @param auteur the author to update
     * @return true if the update was successful, false otherwise
     */
    public boolean update(Auteur auteur) {
        Connection con = null;
        PreparedStatement ps = null;
        boolean success = true;

        try {
            con = getConnection();

            ps = con.prepareStatement("CALL MODIFIER_AUTEUR(?, ?, ?)");
            ps.setInt(1, auteur.getId());
            ps.setString(2, auteur.getNom());
            ps.setString(3, auteur.getPrenom());

            ps.executeUpdate();
        } catch (Exception e) {
            LogManager.getInstance().error("Erreur lors de la modification de l'auteur (" + auteur + ") : " + e.getMessage());
            success = false;
        } finally {
            try {
                if (ps != null) ps.close();
                if (con != null) con.close();
            } catch (Exception e) {
                LogManager.getInstance().error("Erreur lors de la fermeture de la connexion : " + e.getMessage());
            }
            if (success) LogManager.getInstance().info("Auteur modifié : " + auteur);
        }
        return success;
    }

    /**
     * This method retrieves an author by its ID.
     *
     * @param id the ID of the author
     * @param isntCalledByUser if true, no log will be generated
     * @return the Auteur object if found, null otherwise
     */
    public Auteur get(int id, boolean isntCalledByUser) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet res = null;
        Auteur auteur = null;
        boolean success = true;

        try {
            con = getConnection();
            ps = con.prepareStatement("SELECT * FROM AUTEUR WHERE ID_AUTEUR = ?");
            ps.setInt(1, id);
            res = ps.executeQuery();
            if (res.next()) {
                auteur = new Auteur(
                        res.getInt("ID_AUTEUR"),
                        res.getString("NOM"),
                        res.getString("PRENOM")
                );
            }
        } catch (Exception e) {
            LogManager.getInstance().error("Erreur lors de la récupération de l'auteur (" + id + ") : " + e.getMessage());
            success = false;
        } finally {
            try {
                if (res != null) res.close();
                if (ps != null) ps.close();
                if (con != null) con.close();
            } catch (Exception e) {
                LogManager.getInstance().error("Erreur lors de la fermeture de la connexion : " + e.getMessage());
            }
            if (success && !isntCalledByUser) LogManager.getInstance().info("Auteur récupéré : " + auteur);
        }
        return auteur;
    }

/**
     * This method retrieves a list of all authors from the database.
     *
     * @return a List of Auteur objects
     */
    public List<Auteur> getList() {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet res = null;
        List<Auteur> auteurs = new ArrayList<>();
        boolean success = true;

        try {
            con = getConnection();
            ps = con.prepareStatement("SELECT * FROM AUTEUR ORDER BY ID_AUTEUR");
            res = ps.executeQuery();
            while (res.next()) {
                Auteur auteur = new Auteur(
                        res.getInt("ID_AUTEUR"),
                        res.getString("NOM"),
                        res.getString("PRENOM")
                );
                auteurs.add(auteur);
            }
        } catch (Exception e) {
            LogManager.getInstance().error("Erreur lors de la récupération de la liste des auteurs : " + e.getMessage());
            success = false;
        } finally {
            try {
                if (res != null) res.close();
                if (ps != null) ps.close();
                if (con != null) con.close();
            } catch (Exception e) {
                LogManager.getInstance().error("Erreur lors de la fermeture de la connexion : " + e.getMessage());
            }
            if (success) LogManager.getInstance().info("Liste des auteurs récupérée");
        }
        return auteurs;
    }

    /**
     * Main method for testing the AuteurDAO class.
     * It demonstrates adding, updating, deleting, and retrieving authors.
     */
    public static void main(String[] args) {
        AuteurDAO auteurDAO = new AuteurDAO();

        List<Auteur> auteurs = auteurDAO.getList();
        System.out.println("Liste des auteurs :");
        for (Auteur auteur : auteurs) {
            System.out.println(auteur);
        }
        System.out.println();

        int generatedId = auteurDAO.add("Rowling", "J.K.");
        Auteur newAuteur = auteurDAO.get(generatedId, false);

        newAuteur.setNom("Tolkien");
        newAuteur.setPrenom("J.R.R.");
        auteurDAO.update(newAuteur);

        auteurDAO.delete(newAuteur);

        // ERRORS
        Auteur a = new Auteur(100, "Shakespeare", "William");
        auteurDAO.delete(a);
        auteurDAO.update(a);
    }
}
