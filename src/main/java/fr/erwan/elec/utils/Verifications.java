package fr.erwan.elec.utils;
import org.apache.commons.text.StringEscapeUtils;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import fr.erwan.elec.rest.ModelFront;

/**
 * Classe de vérifications des données provenant du front-end
 * @see rest.Services
 */
public class Verifications {
    
    public Verifications() {}

    /**
     * méthode d'échappement des caractères html < > etc
     * @param s la donnée à échapper
     * @return la chaîne de caractère échappée
     */
    public static String checkHtml(String s) {
        return StringEscapeUtils.escapeHtml4(s);
    }

    /**
     * Vérifie le format de la date YYYY-MM-DDThh:mm:ss
     * @param s la date au format chaîne de caractère
     * @return true ou false
     */
    public static boolean datePatternChecker(String s) {
        if (s == null) return false;
        Pattern pattern = Pattern.compile("^202[3456]{1}-[01]{1}[0-9]{1}-[0-9]{2}T[012]{1}[0-9]{1}:[0-9]{2}:[0-9]{2}$");
        Matcher matcher = pattern.matcher(s);
        return matcher.find();
    }

    /**
     * Vérification de données : 
     * @see rest.ModelFront
     * @see datePatternChecker
     * @param model
     * @return true ou false
     */
    public static boolean modelFrontChecker(ModelFront model) {
        return datePatternChecker(model.getInsertedAt());
    }

}
