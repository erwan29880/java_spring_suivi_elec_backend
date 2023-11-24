package fr.erwan.elec.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * controlleur
 * gère le routing et le lien avec le service 
 */
@RestController()
@RequestMapping("/elec")
public class Controlleur {
    @Autowired
    private Services serv;

    /**
     * toutes les entrées de la table elec
     * @return
     */
    @GetMapping("/all")
    public List<Model> getData() {
        return this.serv.getData();
    }

    /**
     * trouver une entrée de la table elec par id
     * @param id
     * @return l'entrée demandée ou un objet vide
     */
    @GetMapping("/{id}")
    public Model getDataById(@PathVariable final long id) {
        return this.serv.getDataById(id);
    }

    /**
     * trouver une entrée de la table elec par date
     * @param dat date au format YYYY-MM-DDThh:mm:ss
     * @return l'entrée demandée ou un objet vide
     */
    @GetMapping("/d/{dat}")
    public Model getDataByDate(@PathVariable final String dat) {
        return this.serv.getDataByDate(dat);
    }

}
