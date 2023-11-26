package fr.erwan.elec.utils;
import org.apache.commons.text.StringEscapeUtils;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import fr.erwan.elec.rest.ModelFront;

public class Verifications {
    
    public Verifications() {}

    public static String checkHtml(String s) {
        return StringEscapeUtils.escapeHtml4(s);
    }

    public static boolean datePatternChecker(String s) {
        if (s == null) return false;
        Pattern pattern = Pattern.compile("^202[3456]{1}-[01]{1}[0-9]{1}-[0-9]{2}T[012]{1}[0-9]{1}:[0-9]{2}:[0-9]{2}$");
        Matcher matcher = pattern.matcher(s);
        return matcher.find();
    }

    public static boolean modelFrontChecker(ModelFront model) {
        return datePatternChecker(model.getInsertedAt());
    }

}
