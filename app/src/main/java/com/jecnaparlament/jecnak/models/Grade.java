package com.jecnaparlament.jecnak.models;

import java.io.Serializable;

import com.jecnaparlament.jecnak.enums.GradeSize;
import com.jecnaparlament.jecnak.helpers.RegexHelper;

public class Grade implements Serializable {

    private int value;
    private String title;
    private GradeSize weight;
    private String date;
    private String teacher;
    private boolean isUncountable;
    private int ymd; // used for sorting
        private String subject;

    /**
     * Constructs a new Grade. Content must contain date and teacher in brackets seperated by comma.
     * @param value     Grade value (1, 2, 3, 4, 5, N, U)
     * @param content   Grade content (e.g. title (02.05.2022, teacher))
     * @param weight    Grade weight (GradeSize enum)
     */
    public Grade(String value, String content, GradeSize weight, String subject) {
        RegexHelper r = new RegexHelper(content);
        this.subject = subject;

        setValue(value);
        setTitle(r.group(1));
        setSize(weight);
        setDate(r.group(2).split(", "));
        setTeacher(r.group(2).split(", "));
        setIsUncountable();

    }

    public void setValue(String value) {
        if(value.equalsIgnoreCase("N") || value.equalsIgnoreCase("U")) {
            this.value = 0;
        }else {
            this.value = Integer.parseInt(value);
        }
    }

    public void setValue(int value) {
        this.value = value;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setSize(GradeSize weight) {
        this.weight = weight;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setDate(String[] arr) {
        this.date = arr[0];
        String[] date = arr[0].split("\\.");
        this.ymd = Integer.parseInt(date[2]+date[1]+date[0]);
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public void setTeacher(String[] arr) {
        this.teacher = arr[1];
    }

    public void setIsUncountable() {
        this.isUncountable = this.value == 0;
    }

    public int getValue() {
        return value;
    }

    public String getTitle() {
        return title;
    }

    public GradeSize getSize() {
        return weight;
    }

    public String getDate() {
        return date;
    }

    public String getTeacher() {
        return teacher;
    }

    public int getYmd() {
        return ymd;
    }

    public String getSubject() {
        return subject;
    }

    public boolean isUncountable() {
        return isUncountable;
    }

    @Override
    public String toString() {
        if(value == 0) {
            return "N - "+title+" ("+date+", "+teacher+")";
        }
        return value+" - "+title+" ("+date+", "+teacher+")";
    }

}
