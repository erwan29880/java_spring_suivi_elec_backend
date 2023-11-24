package fr.erwan.elec.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import java.util.Optional;

import fr.erwan.elec.rest.Model;
import fr.erwan.elec.bdd.Requetes;

@Service
public class Services {
    
    @Autowired
    private Requetes req;

    public Services() {}

    public List<Model> getData() {
        return this.req.getData();
    }

    public Model getDataById(long id) {
        return this.req.getDataById(id).orElse(new Model());
    }

    public Model getDataByDate(String dat) {
        return this.req.getDataByDate(dat).orElse(new Model());
    }

    public boolean save(ModelFront model) {
        return this.req.save(model);
    }

    public boolean delete(double hp, double hc) {
        return this.req.delete(hp, hc);
    }

    public boolean delete(String dat) {
        return this.req.delete(dat);
    }

    public boolean delete(long id) {
        return this.req.delete(id);
    }
    


}
