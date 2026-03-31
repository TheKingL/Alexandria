package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * This class is used to manage the connection to the database.
 * It provides a method to get a connection object.
 *
 * @version 1.0
 * @author Loris
 */
public class ConnexionDAO {
    final static String URL   = "jdbc:oracle:thin:@oracle.esigelec.fr:1521:orcl";
    final static String LOGIN = "";
    final static String PASS  = "";

    /**
     * Constructor for ConnexionDAO.
     * It loads the Oracle JDBC driver.
     */
    public ConnexionDAO() {
        try {
            Class.forName("oracle.jdbc.OracleDriver");
        } catch (ClassNotFoundException e) {
            System.err.println("Impossible de charger le pilote de BDD");
        }
    }

    /**
     * This method is used to get a connection to the database.
     *
     * @return a Connection object
     */
    public static Connection getConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(URL, LOGIN, PASS);
        } catch (SQLException e) {
            System.err.println("Erreur lors de la vérification de la connexion : " + e.getMessage());
        }
        return connection;
    }
}
