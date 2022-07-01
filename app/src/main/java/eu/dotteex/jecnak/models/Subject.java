package eu.dotteex.jecnak.models;

import eu.dotteex.jecnak.enums.GradeSize;
import eu.dotteex.jecnak.helpers.RegexHelper;
import java.util.ArrayList;

public class Subject {

    private String name;
    private String abbr;
    private ArrayList<Grade> grades = new ArrayList<>();
    private String finalGrade;

    /**
     * Constructs a new Subject from subject name. Name must have abbreviation in brackets otherwise it will not work.
     * @param name  Subject name with abbreviation in brackets
     */
    public Subject(String name) {
        System.out.println(name);

        RegexHelper r = new RegexHelper(name);

        setName(r.group(1).trim());
        setAbbr(r.group(2));
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAbbr(String abbr) {
        this.abbr = abbr;
    }

    public void setGrades(ArrayList<Grade> grades) {
        this.grades = grades;
    }

    public void setFinalGrade(String finalGrade) {
        this.finalGrade = finalGrade;
    }

    public String getName() {
        return name;
    }

    public String getAbbr() {
        return abbr;
    }

    public ArrayList<Grade> getGradesToArray() {
        return grades;
    }

    public String getGrades() {
        StringBuilder result = new StringBuilder();
        for(Grade grade : grades) {
            result.append(grade).append("\n");
        }

        return result.toString();
    }

    public String getFinalGrade() {
        return finalGrade;
    }

    /**
     * Add grade to subject grade list.
     * @param grade Grade
     */
    public void addGrade(Grade grade) {
        grades.add(grade);
    }

    /**
     * Get subject grade count. Does not count uncountable grades.
     * @return Grade count
     */
    public int gradeCount() {
        int count = 0;
        for(Grade grade : grades) {
            if(!grade.isUncountable()) {
                count++;
            }
        }
        return count;
    }

    /**
     * Get subject grade average.
     * @return Grade average
     */
    public double getGradeAvg() {
        double gradesValue = 0;
        int gradeCount = gradeCount();
        for(Grade grade : grades) {
            if(!grade.isUncountable()) {
                // Grade with bigger weight counts as two small grades
                if(grade.getSize() == GradeSize.BIG) {
                    gradesValue += grade.getValue()*2;
                    gradeCount++;
                }else {
                    gradesValue += grade.getValue();
                }
            }
        }
        return Math.round((gradesValue/gradeCount) * 100.0) / 100.0;
    }

    @Override
    public String toString() {
        return getName()+" ("+getAbbr()+"):\n"+getGrades()+"\n";
    }


}
