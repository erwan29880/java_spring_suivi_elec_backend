package fr.erwan.elec.bdd;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.Date;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import fr.erwan.elec.config.Config;
import fr.erwan.elec.errors.EnvError;
import fr.erwan.elec.rest.Model;
import fr.erwan.elec.rest.ModelFront;
import fr.erwan.elec.rest.Repo;
import fr.erwan.elec.utils.DateManip;


/**
 * la classe requêtes implémente les méthodes crud du repo, ainsi que d'autres méthodes : 
 *  - création de la table
 *  - insertion des données initiales
 *  - récupérer toutes les données
 *  - récupérer un enregistrement par date ou par id
 *  - supprimer un enregistrement par date, id ou (pour les tests) par hp et hc 
 *  - mise à jour d'un enregistrement
 * 
 * Elle utilise la connexion à la base de données Connect
 */
@Component
public class Requetes extends Connect implements Repo{

    public Requetes() {
        super();
    }

    /**
     * créer la table elec
     * méthode utilisée dans la méthode createAndFill ci-dessous, accessible uniquement en développement
     * @return un booléen
     */
    private boolean createTable() {
        String sql = "CREATE TABLE IF NOT EXISTS elec (id INTEGER PRIMARY KEY AUTOINCREMENT, hp double, hc double, insertedat date);";
        try (Connection conn = super.connexion();){
            Statement stmt  = conn.createStatement();
            stmt.execute(sql);
            super.closeConnexion(conn);
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    /**
     * supprimer la table elec
     * @return booléen
     */
    private boolean deleteTable() {
        String sql = "DROP TABLE elec;";
        try (Connection conn = super.connexion();){
            Statement stmt  = conn.createStatement();
            stmt.execute(sql);
            super.closeConnexion(conn);
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    /**
     * Insertion des données initiales
     * méthode utilisée dans la méthode createAndFill ci-dessous, uniquement accessible en développement
     * @return booléen
     */
    private boolean insertInitialData() {
        String sql = "INSERT INTO elec(hp, hc, insertedat) values (?, ?, ?);";
        try (Connection conn = super.connexion();
        ){
            PreparedStatement stmt  = conn.prepareStatement(sql);
            stmt.setDouble(1, 16d);
            stmt.setDouble(2, 8d);
            stmt.setDate(3, DateManip.toSqlDate("2023-11-23T10:30:00"));
            stmt.executeUpdate();

            stmt  = conn.prepareStatement(sql);
            stmt.setDouble(1, 34d);
            stmt.setDouble(2, 16d);
            stmt.setDate(3, DateManip.toSqlDate("2023-11-24T10:30:00"));
            stmt.executeUpdate();

            stmt  = conn.prepareStatement(sql);
            stmt.setDouble(1, 51d);
            stmt.setDouble(2, 25d);
            stmt.setDate(3, DateManip.toSqlDate("2023-11-25T10:30:00"));
            stmt.executeUpdate();
            super.closeConnexion(conn);
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }


    /**
     * Récupérer toutes les données
     * @return une liste des enregistrements
     */
    public List<Model> getData() {
        List<Model> m = new ArrayList<Model>();
        String sql = "select * from elec order by id;";
        try (Connection conn = super.connexion();
            Statement stmt  = conn.createStatement();
            ResultSet rs    = stmt.executeQuery(sql);
        ){
            while (rs.next()) {
                Model model = new Model();
                model.setId(rs.getLong("id"));
                model.setHp(rs.getDouble("hp"));
                model.setHc(rs.getDouble("hc"));
                model.setInsertedAt(rs.getDate("insertedat"));
                m.add(model);
            }
            super.closeConnexion(conn);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return m; 
    }

    
    /**
     * touver un enregistrement par id
     * @param id
     * @return un optional vide ou correspondant à l'entrée demandée
     */
    public Optional<Model> getDataById(long id) {
        Optional<Model> m = Optional.empty();
        String sql = "select * from elec where id=?;";
        try (Connection conn = super.connexion();
            PreparedStatement stmt  = conn.prepareStatement(sql);
        ){
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                Model model = new Model();
                model.setId(rs.getLong("id"));
                model.setHp(rs.getDouble("hp"));
                model.setHc(rs.getDouble("hc"));
                model.setInsertedAt(rs.getDate("insertedat"));
                m = Optional.of(model);
            }
            super.closeConnexion(conn);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return m;
    }


    /**
     * trouver le dernier enregistrement
     * @return une entrée de la bdd
     */
    public Model getDataByLastId() {
        Model m = new Model();
        String sql = "select * from elec order by id limit 1;";
        try (Connection conn = super.connexion();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
        ){
            if (rs.next()) {
                m.setId(rs.getLong("id"));
                m.setHp(rs.getDouble("hp"));
                m.setHc(rs.getDouble("hc"));
                m.setInsertedAt(rs.getDate("insertedat"));
            }
            super.closeConnexion(conn);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return m;
    }


    /**
     * trouver une entrée par date
     * la plage de date est le jour à partir de minuit -> le jour jusqu'à 23h59
     * @param date date au format string YYYY-MM-DDThh:mm:ss
     * @return un optional<Model> vide ou correspondant à une entrée
     */
    public Optional<Model> getDataByDate(String date) {
        Optional<Model> m = Optional.empty();
        String sql = "select * from elec where insertedat>=? and insertedat<=?;";
        try (Connection conn = super.connexion();
            PreparedStatement stmt  = conn.prepareStatement(sql);
        ){
            Date[] dats = DateManip.getMinMaxDate(date);
            stmt.setDate(1, dats[0]);
            stmt.setDate(2, dats[1]);

            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                Model model = new Model();
                model.setId(rs.getLong("id"));
                model.setHp(rs.getDouble("hp"));
                model.setHc(rs.getDouble("hc"));
                model.setInsertedAt(rs.getDate("insertedat"));
                m = Optional.of(model);
            }
            super.closeConnexion(conn);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return m;
    }


    /**
     * enregistrer une entrée
     * la date est la date du jour
     * @param model le modèle avec la date au format string
     * @return un boolean
     */
    public boolean save(ModelFront model) {
        Date dat = DateManip.toSqlDateFromToday();
        String sql = "INSERT INTO elec(hp, hc, insertedat) values (?, ?, ?);";
        try (Connection conn = super.connexion();
            PreparedStatement stmt  = conn.prepareStatement(sql);
        ){
            stmt.setDouble(1, model.getHp());
            stmt.setDouble(2, model.getHc());
            stmt.setDate(3, dat);
            stmt.executeUpdate();
            super.closeConnexion(conn);
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }


    /**
     * mise à jour d'une entrée par id
     * les paramètres hp, hc, dates (string) doivent être fournis
     * @param model le modèle avec la date au format string
     * @return un boolean
     */
    public boolean update(ModelFront model) {
        String sql = "UPDATE elec set hp=?, hc=?, insertedat=? where id=?;";
        try (Connection conn = super.connexion();
            PreparedStatement stmt  = conn.prepareStatement(sql);
        ){
            java.sql.Date dat = DateManip.toSqlDate(model.getInsertedAt());
            stmt.setDouble(1, model.getHp());
            stmt.setDouble(2, model.getHc());
            stmt.setDate(3, dat);
            stmt.setLong(4, model.getId());
            stmt.executeUpdate();
            super.closeConnexion(conn);
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }


    /**
     * supprimer une entrée par id
     * @param id 
     * @return un boolean
     */
    public boolean delete(long id) {
        String sql = "DELETE FROM elec where id=?;";
        try (Connection conn = super.connexion();
            PreparedStatement stmt  = conn.prepareStatement(sql);
        ){
            stmt.setLong(1, id);
            stmt.executeUpdate();
            super.closeConnexion(conn);
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    
    /**
     * supprimer une/des entrée(s) par hp, hc
     * méthode utilisée pour les tests uniquement
     * @param hp heures pleines
     * @param hc heures creuses
     * @return un boolean
     */
    public boolean delete(double hp, double hc) {
        String sql = "DELETE FROM elec where hp=? and hc=?;";
        try (Connection conn = super.connexion();
            PreparedStatement stmt  = conn.prepareStatement(sql);
        ){
            stmt.setDouble(1, hp);
            stmt.setDouble(2, hc);
            stmt.executeUpdate();
            super.closeConnexion(conn);
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    /**
     * supprimer une entrée par date
     * @param dat sous forme YYYY-MM-DDThh:mm:ss
     * @return un boolean
     */
    public boolean delete(String dat) {
        String sql = "DELETE FROM elec where insertedat>=? and insertedat<=?;";
        try (Connection conn = super.connexion();
            PreparedStatement stmt  = conn.prepareStatement(sql);
        ){
            Date[] dates = DateManip.getMinMaxDate(dat);
            stmt.setDate(1, dates[0]);
            stmt.setDate(2, dates[1]);
            stmt.executeUpdate();
            super.closeConnexion(conn);
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }


    /**
     * créer la table et la remplir 
     * méthode accessible en développement
     */
    public void createAndFillTable(final boolean del) {
        if (Config.getEnv() == "dev") {
            if (del) {
                this.deleteTable();
            }
            this.createTable();
            this.insertInitialData();
        }
    }

    /**
     * supprimer la table si besoin
     *  -> sqlite ne prend pas en compte le truncate
     * @return un boolean
     * @throws EnvError
     */
    public boolean getDeleteTable() throws EnvError {
        if (Config.getEnv() == "dev") {
            this.deleteTable();
            return true;
        } else {
            throw new EnvError("cette méthode n'est disponique que dans un environnement de développement");
        }
    }

}
