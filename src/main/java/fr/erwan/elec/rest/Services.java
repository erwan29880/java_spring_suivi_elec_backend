package fr.erwan.elec.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

import fr.erwan.elec.bdd.Requetes;
import fr.erwan.elec.utils.Verifications;

/**
 * La classe service implémente les méthodes de la classe Requetes
 * 
 * @author erwan tanguy 
 * @see classe Requetes 
 * @see classe Controlleur
 */
@Service
public class Services {
    
    @Autowired
    private Requetes req;

    public Services() {}

    /**
     * get all data
     * @return toutes les données
     */
    public List<Model> getData() {
        return this.req.getData();
    }

    public List<Model> getDataLagged() {
        return this.req.getDataLagged();
    }


    /**
     * get all data by id
     * @param id
     * @return les données demandées ou des données vides
     */
    public Model getDataById(long id) {
        return this.req.getDataById(id).orElse(new Model());
    }

    /**
     * get data by date
     * @param dat date au format YYYY-MM-DDThh:mm:ss en String
     * @return les données demandées ou des données vides
     */
    public Model getDataByDate(String dat) {
        if (Verifications.datePatternChecker(dat)) {
            return this.req.getDataByDate(dat).orElse(new Model());
        } else {
            return new Model();
        }
    }

    /**
     * insérer des données
     * @param model ModelFront -> date au format string
     * @return un booléen
     */
    public boolean save(ModelFront model) {
        return this.req.save(model);
    }

    /**
     * supprimer des données par hp, hc
     * méthode utilisée uniquement pour les tests
     * @param hp double heures pleines
     * @param hc double heures creueses 
     * @return un booléen
     */
    public boolean delete(double hp, double hc) {
        return this.req.delete(hp, hc);
    }

    /**
     * supprimer des données par date
     * @param dat date YYYY-MM-DDThh:mm:ss au format String
     * @return un booléen
     */
    public boolean delete(String dat) {
         if (Verifications.datePatternChecker(dat)) {
            return this.req.delete(dat);
        } else {
            return false;
        }
    }

    /**
     * supprimer des données par id
     * @param id
     * @return un booléen
     */
    public boolean delete(long id) {
        return this.req.delete(id);
    }

    public boolean update(ModelFront model) {
        if (Verifications.modelFrontChecker(model)) {
            return this.req.update(model);
        } else {
            return false;
        }
    }
}
