package fr.erwan.elec.config;

/**
 * classe de configuration : 
 *  - permet de récupérer le chemin absolu de la base de données sqlite
 *  - permet de définir l'environnement : prod ou dev. Les tests ne peuvent fonctionner qu'en dev.
 *      défini dans le main.
 * 
 * @author erwan tanguy
 */
public class Config {
    // prod / dev
    private static String env;
    
    // chemin absolu de la base de données sqlite
    private static String dbPath = System.getProperty("user.dir") + System.getProperty("file.separator") + "db.db";

    public Config() {
    }

    // getters setters

    public static String getEnv() {
        return env;
    }

    public static void setEnv(String environnement) {
        env = environnement;
    }

    public static String getDbPath() {
        return dbPath;
    }
}
