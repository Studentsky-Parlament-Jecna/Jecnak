package eu.dotteex.jecnak.helpers;

import eu.dotteex.jecnak.helpers.exception.IllegalDateFormatException;
import eu.dotteex.jecnak.helpers.exception.IllegalDateLengthException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DateHelper {

    /**
     * Converts ymd integer into human-readable date.
     * @param ymd   date in integer format (e.g. 20220501)
     * @return      Human-readable date (e.g. 01.05.2022)
     */
    public String getDate(int ymd) {

        String date = String.valueOf(ymd);
        String result = "";

        try {
            if(date.length() == 8) {
                Pattern pattern = Pattern.compile("(\\d{4})(\\d{2})(\\d{2})", Pattern.CASE_INSENSITIVE);
                Matcher matcher = pattern.matcher(date);
                if(matcher.find()) {
                    for(int i = 3; i > 0; i--) {
                        result += matcher.group(i)+".";
                    }
                }else {
                    throw new IllegalDateFormatException("Ymd integer is in invalid format.");
                }
            }else {
                throw new IllegalDateLengthException("Ymd integer must be length of 8.");
            }
        }catch(IllegalDateLengthException e) {
            e.printStackTrace();
        }catch(IllegalDateFormatException e) {
            e.printStackTrace();
        }

        return result;

    }

}
