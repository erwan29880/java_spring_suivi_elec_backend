package fr.erwan.elec.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.erwan.elec.security.TokenDto;
import fr.erwan.elec.security.UserAuthenticationProvider;
import fr.erwan.elec.security.UserDto;

import java.util.List;

/**
 * controlleur
 * gère le routing et le lien avec le service  
 * 
 * Routes de sécurité (la première seule est en libre accès)
 * /elec/signIn
 * /elec/jwt 
 * 
 * Routes get :
 * /elec/all 
 * /elec/all/lag
 * /elec/{id}
 * /elec/d/{dat}
 * 
 * Routes post :
 * /elec/save 
 * 
 * Routes delete : 
 * /elec/delete/d/{dat}
 * /elec/delete/{id}
 * 
 * Routes put 
 * /elec/update
 * 
 * @author erwan tanguy$
 * @see fr.erwan.elec.rest.Services
 * @See fr.erwan.elec.bdd.Requetes
 * @See fr.erwan.elec.security.SecurityConfig
 */
@RestController()
@RequestMapping("/elec")
public class Controlleur {

    @Autowired
    private Services serv;

    @Autowired 
    private UserAuthenticationProvider userAuthenticationProvider;
 
    @Value("${application.login}")
    private String login;


    /*
     * SECURITY ------------------------------------------------------------------
     */

    /**
     * méthode de loggin, en accès authorisé
     * @param user {pseudo:String, password:String}
     * @return le token jwt ou une erreur
     */
    @PostMapping("/signIn")
    @CrossOrigin(origins = {"http://localhost:3000"})
    public ResponseEntity<?> signIn(@AuthenticationPrincipal UserDto user) {
        try {
            user.setpassword(userAuthenticationProvider.createToken(user.getLogin()));
            return ResponseEntity.ok(new TokenDto(user.getpassword()));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new MessageFront("bad credentials"));
        }
    }

    /**
     * méthode de vérification pour le token JWT venant du front-end
     * @return une réponse par défaut, et un code d'erreur géré par Spring security
     */
    @PostMapping("/jwt")
    @CrossOrigin(origins = {"http://localhost:3000"}, exposedHeaders = {"Authorization"})
    public ResponseEntity<String> checkJwt() {
        return ResponseEntity.ok("ok");
    }



    /*
     * CRUD ------------------------------------------------------------------
     */


    /**
     * Récupérer toutes les entrées de la table elec
     * @return toutes les données
     */
    @GetMapping("/all")
    @CrossOrigin(origins = {"http://localhost:3000"}, exposedHeaders = {"Authorization"})
    public List<Model> getData() {
        return this.serv.getData();
    }

    /**
     * Récupérer toutes les requêtes de la table elec avec hp et hc non cumulés
     * @return les données sauf la première entrée
     */
    @GetMapping("/all/lag")
    @CrossOrigin(origins = {"http://localhost:3000"}, exposedHeaders = {"Authorization"})
    public List<Model> getDataLagged() {
        return this.serv.getDataLagged();
    }

    
    /**
     * récupérer une entrée de la table elec par id
     * @param id l'id de l'enregistrement
     * @return l'entrée demandée ou un objet vide
     */
    @GetMapping("/{id}")
    @CrossOrigin(origins = {"http://localhost:3000"}, exposedHeaders = {"Authorization"})
    public Model getDataById(@PathVariable final long id) {
        return this.serv.getDataById(id);
    }

    /**
     * Récupérer une entrée de la table elec par date
     * @param dat date au format YYYY-MM-DDThh:mm:ss
     * @return l'entrée demandée ou un objet vide
     */
    @GetMapping("/d/{dat}")
    @CrossOrigin(origins = {"http://localhost:3000"}, exposedHeaders = {"Authorization"})
    public Model getDataByDate(@PathVariable final String dat) {
        return this.serv.getDataByDate(dat);
    }

    /**
     * Enregistrer des données dans la table elec
     * @param model @see classe ModelFront
     * @return un message et un status code
     */
    @PostMapping("/save")
    @CrossOrigin(origins = {"http://localhost:3000"}, exposedHeaders = {"Authorization"})
    public ResponseEntity<MessageFront> save(@RequestBody final ModelFront model) {
        boolean check = this.serv.save(model);
        if (check) {
            return new ResponseEntity<MessageFront>(new MessageFront("ok"), HttpStatus.CREATED);
        } else {
            return new ResponseEntity<MessageFront>(new MessageFront("oups"), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * supprimer une entrée par date
     * @param s date au format YYYY-MM-DDThh:mm-ss
     * @return un message et un status code
     */
    @DeleteMapping("/delete/d/{dat}")
    @CrossOrigin(origins = {"http://localhost:3000"}, exposedHeaders = {"Authorization"})
    public ResponseEntity<MessageFront> deleteByDate(@PathVariable final String s) {
        boolean check = this.serv.delete(s);
        if (check) {
            return new ResponseEntity<MessageFront>(new MessageFront("ok"), HttpStatus.OK);
        } else {
            return new ResponseEntity<MessageFront>(new MessageFront("oups"), HttpStatus.BAD_REQUEST);
        }
    }


    /**
     * supprimer une entrée par id
     * @param id l'id de l'enregistrement
     * @return un message et un status code
     */
    @DeleteMapping("/delete/{id}")
    @CrossOrigin(origins = {"http://localhost:3000"}, exposedHeaders = {"Authorization"})
    public ResponseEntity<MessageFront> deleteByDate(@PathVariable final long id) {
        boolean check = this.serv.delete(id);
        if (check) {
            return new ResponseEntity<MessageFront>(new MessageFront("ok"), HttpStatus.OK);
        } else {
            return new ResponseEntity<MessageFront>(new MessageFront("oups"), HttpStatus.BAD_REQUEST);
        }
    }


    /**
     * Effectuer une mise à jour d'un enregistrement
     * @param model @see classe ModelFront
     * @return un message et un status code
     */
    @PutMapping("/update")
    @CrossOrigin(origins = {"http://localhost:3000"}, exposedHeaders = {"Authorization"})
    public ResponseEntity<MessageFront> update(@RequestBody final ModelFront model) {
        boolean check = this.serv.update(model);
        if (check) {
            return new ResponseEntity<MessageFront>(new MessageFront("ok"), HttpStatus.OK);
        } else {
            return new ResponseEntity<MessageFront>(new MessageFront("oups"), HttpStatus.BAD_REQUEST);
        }
    }
}
