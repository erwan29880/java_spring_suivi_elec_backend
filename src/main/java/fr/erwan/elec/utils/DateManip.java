package fr.erwan.elec.utils;

import java.time.LocalDateTime;


/**
 * classe de manipulation de dates entre le format String et le format java.sql.Date 
 * le format String est YYYY-MM-DDThh:mm:ss
 * le format sql est YYYY-MM-DD
 * @see bdd.Requetes
 * 
 * @author erwan tanguy 
 */
public class DateManip {

    public DateManip() {}

    /**
     * convertir une chaîne de caractère au format java.sql.Date
     * @param d format YYYY-MM-DDThh:mm:ss
     * @return java.sql.Date YYYY-MM-DD
     */
    public static java.sql.Date toSqlDate(String d) {
        try {
            LocalDateTime d1 = LocalDateTime.parse(d);
            java.util.Date d2 = java.sql.Timestamp.valueOf(d1);
            return new java.sql.Date(d2.getTime());
        } catch (Exception e) {
            e.printStackTrace();
            return new java.sql.Date(0L);
        }
    }

    /**
     * la date courante to java.sql.Date
     * @return java.sql.Date
     */
    public static java.sql.Date toSqlDateFromToday() {
        long millis = System.currentTimeMillis();  
        return new java.sql.Date(millis);
    }

    /**
     * à partir d'une date String renvoie 
     * 1 la date du jour à minuit
     * 2 la date du jour à minuit -1
     * Utilisé pour chercher par date dans la base de données
     * @param d la date à analyser en chaîne de caractère
     * @return une liste de java.sql.Date
     */
    public static java.sql.Date[] getMinMaxDate(String d) {
        int t = d.indexOf("T");

        // en cas d'erreur de formatage de date, renvoyer une date par défaut
        if (t == -1) {
            java.sql.Date[] datess = {toSqlDate("2000-01-01T01:01:01"), toSqlDate("2000-01-01T01:01:01")};
            return datess;
        }

        // année, mois jour sans hh mm ss
        String prefix = d.substring(0, t);

        // minuit
		String minDate = new StringBuilder()
							.append(prefix)
							.append("T")
							.append("00:00:00")
							.toString(); 

        // minuit -1 seconde
		String maxDate = new StringBuilder()
					.append(prefix)
					.append("T")
					.append("23:59:59")
					.toString();

        java.sql.Date[] dats = {toSqlDate(minDate), toSqlDate(maxDate)};
        return dats;
    }

}
