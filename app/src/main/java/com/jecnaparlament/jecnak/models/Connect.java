package com.jecnaparlament.jecnak.models;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.jecnaparlament.jecnak.helpers.exception.LoginException;

public class Connect {

    private final String user;
    private final String pass;
    private Map<String, String> session;

    /**
     * Contructs a new Connect from username and password.
     * @param user  Student's username
     * @param pass  Student's password
     */
    public Connect(String user, String pass) throws LoginException, IOException {
        this.user = user;
        this.pass = pass;
        this.session = new HashMap<>();

        setSession();
    }

    /**
     * Log into web through HTTP POST Request and save/set session cookies
     * @throws Exception
     */
    private void setSession() throws LoginException, IOException {

        Connection.Response res = Jsoup.connect("https://www.spsejecna.cz/user/login")
                .data("user", user)
                .data("pass", pass)
                .method(Connection.Method.POST)
                .execute();

        Document doc = res.parse();
        Element loginMessage = doc.select(".message").first();
        if(loginMessage != null) {
            if(loginMessage.text().equalsIgnoreCase("Uživatelské jméno nebo heslo není platné.")) {
                throw new LoginException("Login exception");
            }
        }

        session = res.cookies();
        session.put("role", "student"); //cookies for reading student news

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
            try {
                this.setSession();
            } catch (LoginException | IOException ex) {
                ex.printStackTrace();
            }
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
            try {
                this.setSession();
            } catch (LoginException | IOException ex) {
                ex.printStackTrace();
            }
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
            try {
                this.setSession();
            } catch (LoginException | IOException ex) {
                ex.printStackTrace();
            }
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
            try {
                this.setSession();
            } catch (LoginException | IOException ex) {
                ex.printStackTrace();
            }
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
            try {
                this.setSession();
            } catch (LoginException | IOException ex) {
                ex.printStackTrace();
            }
        }
        return doc;
    }
    public Document getPassing(int month, int yearId) {
        Document doc = null;
        try {
            doc = Jsoup.connect("https://www.spsejecna.cz/absence/passing-student")
                    .data("schoolYearId", String.valueOf(yearId))
                    .data("schoolYearPartMonthId", String.valueOf(month))
                    .cookies(session)
                    .get();
        } catch (Exception e) {
            try {
                this.setSession();
            } catch (LoginException | IOException ex) {
                ex.printStackTrace();
            }
        }
        return doc;
    }

    /**
     * Profile
     * @return Document
     */
    public Document getProfile() {
        Document doc = null;
        try {
            doc = Jsoup.connect("https://www.spsejecna.cz/student/" + user).cookies(session).get();
        } catch (Exception e) {
            try {
                this.setSession();
            } catch (LoginException | IOException ex) {
                ex.printStackTrace();
            }
        }
        return doc;
    }

}
