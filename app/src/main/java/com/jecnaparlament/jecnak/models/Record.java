package com.jecnaparlament.jecnak.models;

import java.io.Serializable;

public class Record implements Serializable {

    private String type;
    private String content;
    private String date;
    private int ymd; // used for sorting

    /**
     * Constructs a new Record from string. String must contain date and content seperated by comma.
     * e.g. (5.5.2022, Sdeleni rodicum)
     * @param content  Content seperated by comma
     */
    public Record(String content) {
        setContent(content.split(", "));
        setDate(content.split(", "));
    }

    // Setters
    public void setContent(String content) {
        this.content = content;
    }

    public void setContent(String[] arr) {
        this.content = arr[1];
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setDate(String[] arr) {
        this.date = arr[0];

        String[] date = arr[0].split("\\.");
        date[0] = (date[0].length() == 1) ? "0"+date[0] : date[0];
        date[1] = (date[1].length() == 1) ? "0"+date[1] : date[1];
        this.ymd = Integer.parseInt(date[2]+date[1]+date[0]);
    }

    // Getters
    public String getContent() {
        return content;
    }

    public String getDate() {
        return date;
    }

    public int getYmd() {
        return ymd;
    }

    @Override
    public String toString() {
        return content;
    }

}
