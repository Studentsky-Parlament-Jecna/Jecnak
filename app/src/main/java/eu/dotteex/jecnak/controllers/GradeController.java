package eu.dotteex.jecnak.controllers;

import eu.dotteex.jecnak.models.Connect;
import eu.dotteex.jecnak.models.Grade;
import eu.dotteex.jecnak.models.Subject;
import eu.dotteex.jecnak.enums.GradeSize;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.util.ArrayList;

public class GradeController implements Controller {

    private final Connect connect;
    private final ArrayList<Subject> subjects = new ArrayList<>();

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
                        subject.addGrade(new Grade(grade.select(".value").text(), grade.attr("title"), GradeSize.SMALL));
                    }else {
                        subject.addGrade(new Grade(grade.select(".value").text(), grade.attr("title"), GradeSize.BIG));
                    }
                }
            }

            subjects.add(subject);
        }
    }

    public String avgGrades() {
        StringBuilder result = new StringBuilder();
        for(Subject subject : subjects) {
            result.append(subject.getName()).append(" | ").append(subject.getGradeAvg()).append("\n");
        }
        return result.toString();
    }

    public ArrayList<Grade> getGrades() {
        ArrayList<Grade> result = new ArrayList<>();
        for(Subject subject : subjects) {
            for(Grade grade : subject.getGradesToArray()) {
                result.add(grade);
            }
        }
        return result;
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
