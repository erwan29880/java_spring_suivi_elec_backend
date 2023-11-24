package fr.erwan.elec.bdd;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import fr.erwan.elec.config.Config;

public class Connect {

    public Connect() {}

    /**
     * méthode accessible dans le package bdd
     * utilisée dans la classe Requetes
     * @return la connection
     * TODO : close connection
     * @throws SQLException
     */
    protected Connection connexion() throws SQLException {
        return DriverManager.getConnection("jdbc:sqlite:" + Config.getDbPath());
    }

    /**
     * méthode publique pour retourner la connection
     * méthode disponible uniquement en développement pour les tests
     * @return la connection
     * @throws SQLException
     */
    public Connection getConnexion() throws SQLException {
        if (Config.getEnv() == "dev") {
            return this.connexion();
        }
        return null;
    }


}