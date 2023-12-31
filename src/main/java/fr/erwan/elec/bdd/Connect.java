package fr.erwan.elec.bdd;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import fr.erwan.elec.config.Config;

/**
 * Classe pour établir la connection à la base de données sqlite
 * @author erwan tanguy
 */
public class Connect {

    public Connect() {}

    /**
     * méthode accessible dans le package bdd
     * utilisée dans la classe Requetes
     * 
     * @return la connection
     * @throws SQLException connection non établie
     */
    protected Connection connexion() throws SQLException {
        return DriverManager.getConnection("jdbc:sqlite:" + Config.getDbPath());
    }

    /**
     * méthode publique pour retourner la connection
     * méthode disponible uniquement en développement pour les tests
     * 
     * @return la connection sql
     * @throws SQLException connection non établie
     */
    public Connection getConnexion() throws SQLException {
        if (Config.getEnv() == "dev") {
            return this.connexion();
        }
        return null;
    }

    /**
     * fermeture de la connection à la base de données
     * méthode utilisée dans le classe Requetes
     * 
     * @param conn connection sql
     * @throws SQLException
     */
    protected void closeConnexion(Connection conn) throws SQLException {
        conn.close();
    }
}