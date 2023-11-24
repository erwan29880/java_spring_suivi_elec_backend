package fr.erwan.elec.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import java.util.Optional;

import fr.erwan.elec.rest.Model;

@RestController()
@RequestMapping("/elec")
public class Controlleur {
    @Autowired
    private Services serv;

    @GetMapping("/all")
    public List<Model> getData() {
        return this.serv.getData();
    }

    @GetMapping("/{id}")
    public Model getDataById(@PathVariable final long id) {
        return this.serv.getDataById(id);
    }

    @GetMapping("/d/{dat}")
    public Model getDataByDate(@PathVariable final String dat) {
        return this.serv.getDataByDate(dat);
    }

}
