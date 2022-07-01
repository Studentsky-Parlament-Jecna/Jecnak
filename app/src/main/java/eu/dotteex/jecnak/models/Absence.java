package eu.dotteex.jecnak.models;

import eu.dotteex.jecnak.helpers.RegexHelper;

public class Absence {

    private String date;
    private String dayName;
    private int hours;

    /**
     * Constructs a new Absence. Date must contain a day name in brackets.
     * @param date     Date (e.g. 02.05.2022 (pondělí))
     * @param content  Content
     */
    public Absence(String date, String content) {
        RegexHelper r = new RegexHelper(date);

        setDate(r.group(1));
        setDayName(r.group(2));
        setHours(content);
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setDayName(String dayName) {
        this.dayName = dayName;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }

    public void setHours(String content) {
        this.hours = Character.getNumericValue(content.charAt(0));
    }

    public String getDate() {
        return date;
    }

    public String getDayName() {
        return dayName;
    }

    public int getHours() {
        return hours;
    }

    @Override
    public String toString() {
        return dayName+", "+date+": "+hours;
    }

}
