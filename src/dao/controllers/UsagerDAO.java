package dao.controllers;

import dao.ConnexionDAO;
import models.Usager;
import utils.LogManager;

import java.sql.*;
import java.util.*;

/**
 * This class is used to manage the Usager (User) data in the database.
 * It provides methods to add, delete, update, retrieve a single Usager,
 *
 * @version 1.0
 * @author Loris
 */
public class UsagerDAO extends ConnexionDAO {

    public UsagerDAO() {
        super();
    }

    public int add(String nom, String prenom, int annee_naissance, boolean tarif_reduit) {
        Connection con = null;
        CallableStatement cs = null;
        int generatedId = 0;

        try {
            con = getConnection();

            String functionCall = "{ ? = call CREER_USAGER(?, ?, ?, ?) }";
            cs = con.prepareCall(functionCall);

            cs.registerOutParameter(1, Types.INTEGER);

            cs.setString(2, nom);
            cs.setString(3, prenom);
            cs.setInt(4, annee_naissance);
            cs.setInt(5, tarif_reduit ? 1 : 0);

            cs.execute();

            generatedId = cs.getInt(1);
        } catch (Exception e) {
            LogManager.getInstance().error("Erreur lors de l'ajout de l'usager (" + nom + " " + prenom + " - " + annee_naissance + ") : " + e.getMessage());
        } finally {
            try {
                if (cs != null) cs.close();
                if (con != null) con.close();
            } catch (Exception e) {
                LogManager.getInstance().error("Erreur lors de l'ajout de l'usager : " + e.getMessage());
            }
            if (generatedId != 0) LogManager.getInstance().info("Usager ajouté : " + nom + " " + prenom + " (" + annee_naissance + ") - TARIF REDUIT : " + (tarif_reduit ? "OUI" : "NON"));
        }
        return generatedId;
    }

    public int delete(Usager usager) {
        Connection con = null;
        PreparedStatement ps = null;
        int returnValue = 0;
        boolean success = true;

        try {
            con = getConnection();

            ps = con.prepareStatement("CALL SUPPRIMER_USAGER(?)");
            ps.setInt(1, usager.getId());

            returnValue = ps.executeUpdate();
        } catch (Exception e) {
            LogManager.getInstance().error("Erreur lors de la suppression de l'usager (" + usager + ") : " + e.getMessage());
            success = false;
        } finally {
            try {
                if (ps != null) ps.close();
                if (con != null) con.close();
            } catch (Exception e) {
                LogManager.getInstance().error("Erreur lors de la suppression de l'usager : " + e.getMessage());
            }
            if (success) LogManager.getInstance().info("Usager supprimé : " + usager);
        }
        return returnValue;
    }

    public boolean update(Usager usager) {
        Connection con = null;
        PreparedStatement ps = null;
        boolean success = true;

        try {
            con = getConnection();

            ps = con.prepareStatement("CALL MODIFIER_USAGER(?, ?, ?, ?, ?)");
            ps.setInt(1, usager.getId());
            ps.setString(2, usager.getNom());
            ps.setString(3, usager.getPrenom());
            ps.setInt(4, usager.getAnneeNaissance());
            ps.setInt(5, usager.isTarifReduit() ? 1 : 0);

            ps.executeUpdate();
        } catch (Exception e) {
            LogManager.getInstance().error("Erreur lors de la modification de l'usager (" + usager + ") : " + e.getMessage());
            success = false;
        } finally {
            try {
                if (ps != null) ps.close();
                if (con != null) con.close();
            } catch (Exception e) {
                LogManager.getInstance().error("Erreur lors de la modification de l'usager : " + e.getMessage());
            }
            if (success) LogManager.getInstance().info("Usager modifié : " + usager);
        }
        return success;
    }

    public Usager get(int id) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet res = null;
        Usager usager = null;
        boolean success = true;

        try {
            con = getConnection();
            ps = con.prepareStatement("SELECT * FROM USAGER WHERE ID_USAGER = ?");
            ps.setInt(1, id);
            res = ps.executeQuery();
            if (res.next()) {
                usager = new Usager(
                        res.getInt("ID_USAGER"),
                        res.getString("NOM"),
                        res.getString("PRENOM"),
                        res.getInt("ANNEE_NAISSANCE"),
                        res.getInt("TARIF_REDUIT") == 1
                );
            }
        } catch (Exception e) {
            LogManager.getInstance().error("Erreur lors de la récupération de l'usager (" + id + ") : " + e.getMessage());
            success = false;
        } finally {
            try {
                if (ps != null) ps.close();
                if (con != null) con.close();
                if (res != null) res.close();
            } catch (Exception e) {
                LogManager.getInstance().error("Erreur lors de la récupération de l'usager : " + e.getMessage());
            }
            if (success) LogManager.getInstance().info("Usager récupéré : " + usager);
        }
        return usager;
    }

    public List<Usager> getList() {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet res = null;
        List<Usager> usagers = new ArrayList<>();
        boolean success = true;

        try {
            con = getConnection();
            ps = con.prepareStatement("SELECT * FROM USAGER ORDER BY ID_USAGER");
            res = ps.executeQuery();
            while (res.next()) {
                Usager usager = new Usager(
                        res.getInt("ID_USAGER"),
                        res.getString("NOM"),
                        res.getString("PRENOM"),
                        res.getInt("ANNEE_NAISSANCE"),
                        res.getInt("TARIF_REDUIT") == 1
                );
                usagers.add(usager);
            }
        } catch (Exception e) {
            LogManager.getInstance().error("Erreur lors de la récupération de la liste des usagers : " + e.getMessage());
            success = false;
        } finally {
            try {
                if (ps != null) ps.close();
                if (con != null) con.close();
                if (res != null) res.close();
            } catch (Exception e) {
                LogManager.getInstance().error("Erreur lors de la récupération de la liste des usagers : " + e.getMessage());
            }
            if (success) LogManager.getInstance().info("Liste des usagers récupérée");
        }
        return usagers;
    }

    public Map<Integer, Integer> getNbUserByYear() {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet res = null;
        Map<Integer, Integer> userbyyear = new LinkedHashMap<>();
        boolean success = true;

        try {
            con = getConnection();
            ps = con.prepareStatement("SELECT ANNEE_NAISSANCE, COUNT(*) AS NB_USAGERS FROM USAGER GROUP BY ANNEE_NAISSANCE ORDER BY ANNEE_NAISSANCE");
            res = ps.executeQuery();
            while (res.next()) {
                userbyyear.put(res.getInt("ANNEE_NAISSANCE"), res.getInt("NB_USAGERS"));
            }
        } catch (Exception e) {
            LogManager.getInstance().error("Erreur lors de la récupération du nombre d'usagers par année : " + e.getMessage());
            success = false;
        } finally {
            try {
                if (ps != null) ps.close();
                if (con != null) con.close();
                if (res != null) res.close();
            } catch (Exception e) {
                LogManager.getInstance().error("Erreur lors de la récupération du nombre d'usagers par année : " + e.getMessage());
            }
            if (success) LogManager.getInstance().info("Nombre d'usagers par année récupéré");
        }
        return userbyyear;
    }

    public static void main(String[] args) {
        UsagerDAO usagerDAO = new UsagerDAO();

        List<Usager> usagers = usagerDAO.getList();
        System.out.println("Liste des usagers :");
        for (Usager usager : usagers) {
            System.out.println(usager);
        }
        System.out.println();

        int generatedId = usagerDAO.add("abbaaaaa", "aaba", 1990, true);
        Usager newUsager = usagerDAO.get(generatedId);
        System.out.println("Nouvel usager ajouté : " + newUsager + " (n°" + generatedId + ")");

        newUsager.setNom("Durand");
        newUsager.setPrenom("Pierre");
        usagerDAO.update(newUsager);

        usagerDAO.delete(newUsager);
        System.out.println("Usager supprimé : " + newUsager);

        // ERRORS
        Usager us = new Usager(100, "Dupont", "Jean", 1990, true);
        usagerDAO.delete(us);
        usagerDAO.update(us);
        usagerDAO.add("Dupont", "Emma", 1990, true);
    }
}
