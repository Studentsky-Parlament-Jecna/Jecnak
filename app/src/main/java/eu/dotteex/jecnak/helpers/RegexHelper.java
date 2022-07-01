package eu.dotteex.jecnak.helpers;

import eu.dotteex.jecnak.helpers.exception.RegexException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexHelper {

    private Pattern pattern;
    private Matcher matcher;
    private String text;

    private final String BRACKET_REGEX = "(.*)\\(([^)]*)\\)[^(]*$";

    /**
     * Constructs a new RegexHelper.
     * @param text
     */
    public RegexHelper(String text) {
        this.text = text;

        try {
            pattern = Pattern.compile(BRACKET_REGEX, Pattern.CASE_INSENSITIVE);
            matcher = pattern.matcher(text);
            if(!matcher.find()) {
                throw new RegexException("Pattern does not match with string.");
            }
        }catch(RegexException e) {
            e.printStackTrace();
        }

    }

    public String group(int i) {
        return matcher.group(i);
    }

}
