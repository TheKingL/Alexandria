package dao.controllers;

import dao.ConnexionDAO;
import models.*;
import utils.LogManager;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This class is used to manage loans in the database.
 * It provides methods to add, delete, update, and retrieve loans.
 *
 * @version 1.0
 * @author Loris
 */
public class PretDAO extends ConnexionDAO {

    /**
     * Constructor for PretDAO.
     * It initializes the connection to the database.
     */
    public PretDAO() {
        super();
    }

    /**
     * This method adds a new loan to the database.
     *
     * @param dateEmprunt          the date of the loan
     * @param dureeEmprunt         the duration of the loan in days
     * @param dateRetourEffective  the effective return date, can be null if not yet returned
     * @param livre                the book being loaned
     * @param usager               the user borrowing the book
     * @return the generated ID of the new loan
     */
    public int add(Date dateEmprunt, int dureeEmprunt, LocalDate dateRetourEffective, Livre livre, Usager usager) {
        Connection con = null;
        CallableStatement cs = null;
        int generatedId = 0;

        try {
            con = getConnection();

            String functionCall = "{ ? = call CREER_PRET(?, ?, ?, ?, ?) }";
            cs = con.prepareCall(functionCall);

            cs.registerOutParameter(1, java.sql.Types.INTEGER);

            cs.setDate(2, new java.sql.Date(dateEmprunt.getTime()));
            cs.setInt(3, dureeEmprunt);
            cs.setDate(4, dateRetourEffective != null ? java.sql.Date.valueOf(dateRetourEffective) : null);
            cs.setLong(5, livre.getIsbn());
            cs.setInt(6, usager.getId());

            cs.execute();

            generatedId = cs.getInt(1);
        } catch (Exception e) {
            LogManager.getInstance().error("Erreur lors de la création d'un prêt (" + livre + ", " + usager + ") : " + e.getMessage());
        } finally {
            try {
                if (cs != null) cs.close();
                if (con != null) con.close();
            } catch (Exception e) {
                LogManager.getInstance().error("Erreur lors de la création d'un prêt " + e.getMessage());
            }
            if (generatedId != 0) LogManager.getInstance().info("Prêt créer : " + "Livre: " + livre + ", Usager: " + usager + ", Date d'emprunt: " + dateEmprunt + ", Durée: " + dureeEmprunt + ", Date de retour effective: " + dateRetourEffective);
        }
        return generatedId;
    }

    /**
     * This method deletes a loan from the database.
     *
     * @param pret the loan to be deleted
     * @return the number of rows affected (should be 1 on success)
     */
    public int delete(Pret pret) {
        Connection con = null;
        PreparedStatement ps = null;
        int returnValue = 0;
        boolean success = true;

        try {
            con = getConnection();

            ps = con.prepareStatement("CALL SUPPRIMER_PRET(?)");
            ps.setInt(1, pret.getId());

            returnValue = ps.executeUpdate();
        } catch (Exception e) {
            LogManager.getInstance().error("Erreur lors de la suppression d'un prêt (" + pret + ") : " + e.getMessage());
            success = false;
        } finally {
            try {
                if (ps != null) ps.close();
                if (con != null) con.close();
            } catch (Exception e) {
                LogManager.getInstance().error("Erreur lors de la suppression d'un prêt " + e.getMessage());
            }
            if (success) LogManager.getInstance().info("Prêt supprimé : " + pret);
        }
        return returnValue;
    }

    /**
     * This method updates an existing loan in the database.
     *
     * @param pret the loan to be updated
     * @return true if the update was successful, false otherwise
     */
    public boolean update(Pret pret) {
        Connection con = null;
        PreparedStatement ps = null;
        boolean success = true;

        try {
            con = getConnection();

            ps = con.prepareStatement("CALL MODIFIER_PRET(?, ?, ?, ?, ?, ?)");
            ps.setInt(1, pret.getId());
            ps.setDate(2, new java.sql.Date(pret.getDateEmprunt().getTime()));
            ps.setInt(3, pret.getDureeEmprunt());
            ps.setDate(4, pret.getDateRetourEffective() != null ? java.sql.Date.valueOf(pret.getDateRetourEffective()) : null);
            ps.setLong(5, pret.getLivre().getIsbn());
            ps.setInt(6, pret.getUsager().getId());

            ps.executeUpdate();
        } catch (Exception e) {
            LogManager.getInstance().error("Erreur lors de la modification d'un prêt (" + pret + ") : " + e.getMessage());
            success = false;
        } finally {
            try {
                if (ps != null) ps.close();
                if (con != null) con.close();
            } catch (Exception e) {
                LogManager.getInstance().error("Erreur lors de la modification d'un prêt " + e.getMessage());
            }
            if (success) LogManager.getInstance().info("Prêt modifié : " + pret);
        }
        return success;
    }

    /**
     * This method retrieves a loan by its ID from the database.
     *
     * @param id the ID of the loan to retrieve
     * @return the loan object if found, null otherwise
     */
    public Pret get(int id) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet res = null;
        Pret pret = null;
        boolean success = true;

        try {
            con = getConnection();
            ps = con.prepareStatement("SELECT ID_PRET, DATE_EMPRUNT, DUREE, DATE_RETOUR_EFFECTIVE, L.ISBN, TITRE, NB_PAGES, NB_EXEMPLAIRES, A.ID_AUTEUR, A.NOM AS NOMA, A.PRENOM AS PRENOMA, G.ID_GENRE, G.NOM AS NOMG, U.ID_USAGER, U.NOM AS NOMU, U.PRENOM AS PRENOMU, ANNEE_NAISSANCE, TARIF_REDUIT FROM PRET P JOIN LIVRE L ON P.ISBN = L.ISBN JOIN AUTEUR A ON L.ID_AUTEUR = A.ID_AUTEUR JOIN GENRE G ON L.GENRE = G.ID_GENRE JOIN USAGER U ON P.ID_USAGER = U.ID_USAGER WHERE ID_PRET = ?");
            ps.setInt(1, id);
            res = ps.executeQuery();
            if (res.next()) {
                Livre livre = new Livre(
                        res.getLong("ISBN"),
                        res.getString("TITRE"),
                        new Auteur(res.getInt("ID_AUTEUR"), res.getString("NOMA"), res.getString("PRENOMA")),
                        res.getInt("NB_PAGES"),
                        new Genre(res.getInt("ID_GENRE"), res.getString("NOMG")),
                        res.getInt("NB_EXEMPLAIRES")
                );
                Usager usager = new Usager(
                        res.getInt("ID_USAGER"),
                        res.getString("NOMU"),
                        res.getString("PRENOMU"),
                        res.getInt("ANNEE_NAISSANCE"),
                        res.getBoolean("TARIF_REDUIT")
                );
                pret = new Pret(
                        res.getInt("ID_PRET"),
                        res.getDate("DATE_EMPRUNT"),
                        res.getInt("DUREE"),
                        res.getDate("DATE_RETOUR_EFFECTIVE") != null ? res.getDate("DATE_RETOUR_EFFECTIVE").toLocalDate() : null,
                        livre,
                        usager
                );
            }
        } catch (Exception e) {
            LogManager.getInstance().error("Erreur lors de la récupération d'un prêt (" + id + ") : " + e.getMessage());
            success = false;
        } finally {
            try {
                if (ps != null) ps.close();
                if (con != null) con.close();
                if (res != null) res.close();
            } catch (Exception e) {
                LogManager.getInstance().error("Erreur lors de la récupération d'un prêt " + e.getMessage());
            }
            if (success) LogManager.getInstance().info("Prêt récupéré : " + pret);
        }
        return pret;
    }

    /**
     * This method retrieves a list of all loans from the database.
     *
     * @return a list of Pret objects representing all loans
     */
    public List<Pret> getList() {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet res = null;
        List<Pret> prets = new ArrayList<>();
        boolean success = true;

        try {
            con = getConnection();
            ps = con.prepareStatement("SELECT ID_PRET, DATE_EMPRUNT, DUREE, DATE_RETOUR_EFFECTIVE, L.ISBN, TITRE, NB_PAGES, NB_EXEMPLAIRES, A.ID_AUTEUR, A.NOM AS NOMA, A.PRENOM AS PRENOMA, G.ID_GENRE, G.NOM AS NOMG, U.ID_USAGER, U.NOM AS NOMU, U.PRENOM AS PRENOMU, ANNEE_NAISSANCE, TARIF_REDUIT FROM PRET P JOIN LIVRE L ON P.ISBN = L.ISBN JOIN AUTEUR A ON L.ID_AUTEUR = A.ID_AUTEUR JOIN GENRE G ON L.GENRE = G.ID_GENRE JOIN USAGER U ON P.ID_USAGER = U.ID_USAGER ORDER BY ID_PRET");
            res = ps.executeQuery();
            while (res.next()) {
                Livre livre = new Livre(
                        res.getLong("ISBN"),
                        res.getString("TITRE"),
                        new Auteur(res.getInt("ID_AUTEUR"), res.getString("NOMA"), res.getString("PRENOMA")),
                        res.getInt("NB_PAGES"),
                        new Genre(res.getInt("ID_GENRE"), res.getString("NOMG")),
                        res.getInt("NB_EXEMPLAIRES")
                );
                Usager usager = new Usager(
                        res.getInt("ID_USAGER"),
                        res.getString("NOMU"),
                        res.getString("PRENOMU"),
                        res.getInt("ANNEE_NAISSANCE"),
                        res.getBoolean("TARIF_REDUIT")
                );
                Pret pret = new Pret(
                        res.getInt("ID_PRET"),
                        res.getDate("DATE_EMPRUNT"),
                        res.getInt("DUREE"),
                        res.getDate("DATE_RETOUR_EFFECTIVE") != null ? res.getDate("DATE_RETOUR_EFFECTIVE").toLocalDate() : null,
                        livre,
                        usager
                );
                prets.add(pret);
            }
        } catch (Exception e) {
            LogManager.getInstance().error("Erreur lors de la récupération de la liste des prêts : " + e.getMessage());
            success = false;
        } finally {
            try {
                if (ps != null) ps.close();
                if (con != null) con.close();
                if (res != null) res.close();
            } catch (Exception e) {
                LogManager.getInstance().error("Erreur lors de la récupération de la liste des prêts " + e.getMessage());
            }
            if (success) LogManager.getInstance().info("Liste des prêts récupérée");
        }
        return prets;
    }

    /**
     * This method retrieves a list of loans for a specific user by their ID.
     *
     * @param id the ID of the user
     * @return a list of Object arrays containing loan details
     */
    public List<Object[]> getPretsParUsager(int id) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet res = null;
        List<Object[]> prets = new ArrayList<>();
        boolean success = true;

        try {
            con = getConnection();
            ps = con.prepareStatement("SELECT DATE_EMPRUNT, DATE_RETOUR_EFFECTIVE, TITRE, NOM, PRENOM FROM PRET P JOIN LIVRE L ON P.ISBN = L.ISBN JOIN AUTEUR A ON L.ID_AUTEUR = A.ID_AUTEUR WHERE ID_USAGER = ?");
            ps.setInt(1, id);
            res = ps.executeQuery();
            while (res.next()) {
                Object[] pret = new Object[6];
                pret[0] = res.getDate("DATE_EMPRUNT");
                pret[1] = res.getDate("DATE_RETOUR_EFFECTIVE");
                pret[2] = res.getString("TITRE");
                pret[3] = res.getString("NOM");
                pret[4] = res.getString("PRENOM");
                prets.add(pret);
            }
        } catch (Exception e) {
            LogManager.getInstance().error("Erreur lors de la récupération des prêts par usager (" + id + ") : " + e.getMessage());
            success = false;
        } finally {
            try {
                if (ps != null) ps.close();
                if (con != null) con.close();
                if (res != null) res.close();
            } catch (Exception e) {
                LogManager.getInstance().error("Erreur lors de la récupération des prêts par usager " + e.getMessage());
            }
            if (success) LogManager.getInstance().info("Liste des prêts par usager récupérée");
        }
        return prets;
    }

    /**
     * This method retrieves the average effective loan duration from the database.
     *
     * @return the average effective loan duration in days
     */
    public double getAverageEffectiveLoanDuration() {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet res = null;
        double averageDuration = 0;
        boolean success = true;

        try {
            con = getConnection();
            ps = con.prepareStatement("SELECT ROUND(AVG(DATE_RETOUR_EFFECTIVE - DATE_EMPRUNT), 2) AS DUREE_EFFECTIVE_MOYENNE FROM PRET WHERE DATE_RETOUR_EFFECTIVE IS NOT NULL");
            res = ps.executeQuery();
            if (res.next()) {
                averageDuration = res.getDouble("DUREE_EFFECTIVE_MOYENNE");
            }
        } catch (Exception e) {
            LogManager.getInstance().error("Erreur lors de la récupération de la durée moyenne des prêts : " + e.getMessage());
            success = false;
        } finally {
            try {
                if (ps != null) ps.close();
                if (con != null) con.close();
                if (res != null) res.close();
            } catch (Exception e) {
                LogManager.getInstance().error("Erreur lors de la récupération de la durée moyenne des prêts " + e.getMessage());
            }
            if (success) LogManager.getInstance().info("Durée moyenne des prêts récupérée : " + averageDuration);
        }
        return averageDuration;
    }

    /**
     * Main method for testing the PretDAO class.
     * It demonstrates adding, updating, deleting, and retrieving loans.
     */
    public static void main(String[] args) {
        PretDAO pretDAO = new PretDAO();
        LivreDAO livreDAO = new LivreDAO();
        UsagerDAO usagerDAO = new UsagerDAO();

        List<Pret> prets = pretDAO.getList();
        System.out.println("Liste des prêts :");
        for (Pret pret : prets) {
            System.out.println(pret);
        }
        System.out.println();

        Livre livre = livreDAO.get(9782266105149L);
        Usager usager = usagerDAO.get(1);
        Date dateEmprunt = new Date();
        int dureeEmprunt = 14;
        LocalDate dateRetourEffective = LocalDate.now().plusDays(7);

        int generatedId = pretDAO.add(dateEmprunt, dureeEmprunt, dateRetourEffective, livre, usager);
        Pret newPret = pretDAO.get(generatedId);

        newPret.setDureeEmprunt(21);
        pretDAO.update(newPret);

        pretDAO.delete(newPret);

        // ERRORS
        Pret p = new Pret(generatedId, new Date(), 14, LocalDate.now(), livre, usager);
        p.setId(1000);
        pretDAO.delete(p);
        pretDAO.update(p);
        pretDAO.add(new Date(), 14, LocalDate.now(), livre, usager);
    }
}
