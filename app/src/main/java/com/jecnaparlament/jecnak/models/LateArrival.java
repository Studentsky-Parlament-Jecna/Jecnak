package com.jecnaparlament.jecnak.models;

import com.jecnaparlament.jecnak.helpers.RegexHelper;

public class LateArrival {

    private String date;
    private String dayName;
    private boolean isLate;
    private int count; // počet pozdních příchodů za daný den
    private int unauthorizedCount; // počet neomluvených pozdních příchodů

    /**
     * Constructs a new Late Arrival. Date must contain a day name in brackets.
     * @param date     Date (e.g. 02.05.2022 (pondělí))
     * @param content  Content
     */
    public LateArrival(String date, String content) {
        RegexHelper r = new RegexHelper(date);

        setDate(r.group(1));
        setDayName(r.group(2));
        setCount(content);
        setUnauthorizedCount(content);
    }



    public void setDate(String date) {
        this.date = date;
    }

    public void setDayName(String dayName) {
        this.dayName = dayName;
    }

    public void setCount(String content) {
        this.count = Character.getNumericValue(content.charAt(0));
    }

    public void setUnauthorizedCount(String content) {
        if(content.length() > 17) {
            this.unauthorizedCount = Character.getNumericValue(content.charAt(24)); //počet neomluvených pozdních příchodů na 24. místě v textu
        }else {
            this.unauthorizedCount = 0;
        }
    }

    public String getDate() {
        return date;
    }

    public String getDayName() {
        return dayName;
    }

    public int getCount() {
        return count;
    }

    public int getUnauthorizedCount() {
        return unauthorizedCount;
    }

    public boolean getIsLate() {
        return count > 0;
    }

    @Override
    public String toString() {
        return dayName+", "+date+": "+count+" pozdní příchod ("+unauthorizedCount+" neomluvený)";
    }


}
