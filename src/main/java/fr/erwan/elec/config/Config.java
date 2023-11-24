package fr.erwan.elec.config;

public class Config {
    private static String env;
    private static String dbPath = System.getProperty("user.dir") + System.getProperty("file.separator") + "db.db";

    public Config() {
    }

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
