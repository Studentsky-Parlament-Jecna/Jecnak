package eu.dotteex.jecnak.models;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.HashMap;
import java.util.Map;

public class Connect {

    private final String user;
    private final String pass;
    private Map<String, String> session;

    /**
     * Contructs a new Connect from username and password.
     * @param user  Student's username
     * @param pass  Student's password
     */
    public Connect(String user, String pass) {
        this.user = user;
        this.pass = pass;
        this.session = new HashMap<>();

        try { setSession(); } catch (Exception e) {
            // internet issues
        }
    }

    /**
     * Log into web through HTTP POST Request and save/set session cookies
     * @throws Exception
     */
    private void setSession() {

        Connection.Response res = null;
        try {
            res = Jsoup.connect("https://www.spsejecna.cz/user/login")
                    .data("user", user)
                    .data("pass", pass)
                    .method(Connection.Method.POST)
                    .execute();

            session = res.cookies();
            session.put("role", "student"); //cookies for reading student news
        }catch (Exception e) {
            //System.out.println("Exception: some problem occuried - maybe internet issues?");
        }

    }

    /**
     * Grades
     * @return Document
     */
    public Document getGrades() {
        Document doc = null;
        try {
            doc = Jsoup.connect("https://www.spsejecna.cz/score/student").cookies(session).get();
        } catch (Exception e) {
            this.setSession();
        }
        return doc;
    }

    /**
     * News
     * @return Document
     */
    public Document getNews() {
        Document doc = null;
        try {
            doc = Jsoup.connect("https://www.spsejecna.cz/").cookies(session).get();
        } catch (Exception e) {
            this.setSession();
        }
        return doc;
    }

    /**
     * Student Records (Sdělení rodičům)
     * @return Document
     */
    public Document getRecords() {
        Document doc = null;
        try {
            doc = Jsoup.connect("https://www.spsejecna.cz/user-student/record-list").cookies(session).get();
        } catch (Exception e) {
            this.setSession();
        }
        return doc;
    }

    /**
     * Absence List
     * @return Document
     */
    public Document getAbsence() {
        final Document[] doc = new Document[1];
        try {
            doc[0] = Jsoup.connect("https://www.spsejecna.cz/absence/student").cookies(session).get();
        } catch (Exception e) {
            setSession();
        }
        return doc[0];
    }

    /**
     * Late arrivals
     * @return Document
     */
    public Document getPassing() {
        Document doc = null;
        try {
            doc = Jsoup.connect("https://www.spsejecna.cz/absence/passing-student").cookies(session).get();
        } catch (Exception e) {
            this.setSession();
        }
        return doc;
    }


    public Document getProfile(){
        Document doc = null;
        try {
            doc = Jsoup.connect("https://www.spsejecna.cz/student/" + user).cookies(session).get();
        } catch (Exception e) {
            this.setSession();
        }
        return doc;
    }

}
