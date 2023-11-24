package fr.erwan.elec.bdd;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import fr.erwan.elec.config.Config;

public class Connect {

    

    public Connect() {}

    protected Connection connexion() throws SQLException {
        return DriverManager.getConnection("jdbc:sqlite:" + Config.getDbPath());
    }

    public Connection getConnexion() throws SQLException {
        if (Config.getEnv() == "dev") {
            return this.connexion();
        }
        return null;
    }


}