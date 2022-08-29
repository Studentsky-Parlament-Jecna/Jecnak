package com.jecnaparlament.jecnak.controllers.types;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.Comparator;

import com.jecnaparlament.jecnak.controllers.Controller;
import com.jecnaparlament.jecnak.enums.GradeSize;
import com.jecnaparlament.jecnak.models.Connect;
import com.jecnaparlament.jecnak.models.Grade;
import com.jecnaparlament.jecnak.models.Subject;

public class GradeController implements Controller {

    private final Connect connect;
    private final ArrayList<Subject> subjects = new ArrayList<>();


    private ArrayList<Grade> gradesArray = new ArrayList<>();

    /**
     * Constructs a new GradeController.
     * @param connect Connect
     */
    public GradeController(Connect connect) {
        this.connect = connect;
        this.update();
    }

    public ArrayList<Subject> getSubjects() {
        return subjects;
    }

    public void update() {
        Document doc = connect.getGrades();
        if(doc == null) return;

        Elements elements = doc.select("tbody tr");
        for(Element element : elements) {
            if(element.select("th").text().equalsIgnoreCase("Chování")) continue;

            Subject subject = new Subject(element.select("th").text());

            Elements grades = element.select("td a");
            for(Element grade : grades) {
                if(grade.hasClass("scoreFinal")) {
                    // final grades
                    subject.setFinalGrade(grade.text());
                }else {
                    // normal grades
                    if(grade.select(".value").text().length() != 1) continue;

                    if(grade.hasClass("scoreSmall")) {
                        Grade tempGrade = new Grade(grade.select(".value").text(), grade.attr("title"), GradeSize.SMALL, subject.getName());
                        subject.addGrade(tempGrade);
                    }else {
                        Grade tempGrade = new Grade(grade.select(".value").text(), grade.attr("title"), GradeSize.BIG, subject.getName());
                        subject.addGrade(tempGrade);
                    }
                }
            }
            subjects.add(subject);
        }
        setGrades();
        // sort grades
        gradesArray.sort(new Comparator<Grade>() {
            @Override
            public int compare(Grade grade, Grade t1) {
                Integer ymd1 = new Integer(grade.getYmd());
                Integer ymd2 = new Integer(t1.getYmd());
                return ymd2.compareTo(ymd1);
            }
        });

    }

    public String avgGrades() {
        StringBuilder result = new StringBuilder();
        for(Subject subject : subjects) {
            result.append(subject.getName()).append(" | ").append(subject.getGradeAvg()).append("\n");
        }
        return result.toString();
    }

    public ArrayList<Grade> getGrades() {
        return gradesArray;

    }
    public void setGrades(){
        ArrayList<Grade> result = new ArrayList<>();
        for(Subject subject : subjects) {
            result.addAll(subject.getGradesToArray());
        }
        gradesArray = result;
    }

    public String getFinalGrades() {
        StringBuilder result = new StringBuilder();
        for(Subject subject : subjects) {
            result.append(subject.getName()).append(" | ").append(subject.getFinalGrade()).append("\n");
        }
        return result.toString();
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for(Subject subject : subjects) {
            result.append(subject);
        }
        return result.toString();
    }

}
