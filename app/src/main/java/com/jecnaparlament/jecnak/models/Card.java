package com.jecnaparlament.jecnak.models;

import com.jecnaparlament.jecnak.enums.CardType;
import com.jecnaparlament.jecnak.helpers.DateHelper;
import java.io.Serializable;
import java.util.Objects;

public class Card implements Serializable {

    private CardType type;
    private String title;
    private String content;
    private String footer;
    private String date;
    private int ymd;

    /**
     * Constructs a new Card which is shown in app main page. It supports Grade, Record and News.
     * @param type     Card type (CardType enum)
     * @param title    Card title
     * @param content  Card content
     * @param footer   Card footer
     * @param ymd      Used for sorting cards (use in ymd format, e.g. 20220501)
     */
    public Card(CardType type, String title, String content, String footer, int ymd) {
        setType(type);
        setTitle(title);
        setContent(content);
        setFooter(footer);
        setDate(ymd);
        setYmd(ymd);
    }

    public void setType(CardType type) {
        this.type = type;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setFooter(String footer) {
        this.footer = footer;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setDate(int ymd) {
        this.date = new DateHelper().getDate(ymd);
    }

    public void setYmd(int ymd) {
        this.ymd = ymd;
    }

    public CardType getType() {
        return type;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getFooter() {
        return footer;
    }

    public String getDate() {
        return date;
    }

    public int getYmd() {
        return ymd;
    }

    @Override
    public String toString() {
        return "\n------------------------\n"+title+", "+content+"\n"+footer+", "+date+"\n------------------------\n";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Card card = (Card) o;
        return type == card.type && title.equals(card.title) && content.equals(card.content) && Objects.equals(footer, card.footer) && date.equals(card.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, title, content, footer, date);
    }


}
