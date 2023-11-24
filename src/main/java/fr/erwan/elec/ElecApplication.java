package fr.erwan.elec;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import fr.erwan.elec.config.Config;
// import fr.erwan.elec.bdd.Requetes;
// import java.sql.Date;

@SpringBootApplication
public class ElecApplication {

	public static void main(String[] args) {
		Config.setEnv("dev");
		SpringApplication.run(ElecApplication.class, args);
	}
}
