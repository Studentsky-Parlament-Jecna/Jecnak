package eu.dotteex.jecnak.models;

import java.io.Serializable;
import java.util.Calendar;

public class News implements Serializable {

    private String title;
    private String author; //teacher
    private String date;
    private String content;
    private int ymd; // used for sorting

    /**
     * Constructs new News. Footer must contain date and author.
     * @param title    News title
     * @param content  News content
     * @param footer   News footer (date | author)
     */
    public News(String title, String content, String footer) {
        setTitle(title);
        setContent(content);
        setAuthor(footer.split("\\s\\|\\s"));
        setDate(footer.split("\\s\\|\\s"));
    }

    // Setters
    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setAuthor(String[] footer) {
        this.author = footer[1];
    }

    public void setDate(String[] footer) {
        this.date = footer[0].replace(".", ". ");
        String[] date = footer[0].split("\\.");

        String day = (date[0].length() == 1) ? "0"+date[0] : date[0];
        String month;
        String year = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));

        switch(date[1]) {
            case "prosince":
                month = "12";
                break;
            case "listopadu":
                month = "11";
                break;
            case "října":
                month = "10";
                break;
            case "září":
                month = "09";
                break;
            case "srpna":
                month = "08";
                break;
            case "července":
                month = "07";
                break;
            case "června":
                month = "06";
                break;
            case "května":
                month = "05";
                break;
            case "dubna":
                month = "04";
                break;
            case "března":
                month = "03";
                break;
            case "února":
                month = "02";
                break;
            default:
                month = "01";
                break;
        };

        this.ymd = Integer.parseInt(year+month+day);
    }

    // Getters
    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getDate() {
        return date;
    }

    public String getContent() {
        return content;
    }

    public int getYmd() {
        return ymd;
    }

    @Override
    public String toString() {
        return author+", "+date+"\n"+title+"\n\n"+content;
    }

}
