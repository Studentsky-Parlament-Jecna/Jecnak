package com.jecnaparlament.jecnak.controllers.types;

import androidx.annotation.NonNull;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.HashMap;

import com.jecnaparlament.jecnak.controllers.Controller;
import com.jecnaparlament.jecnak.models.Absence;
import com.jecnaparlament.jecnak.models.Attendance;
import com.jecnaparlament.jecnak.models.Connect;

public class AbsenceController implements Controller {

    private final Connect connect;
    private final ArrayList<Absence> absence = new ArrayList<>();
    private final HashMap<String,Absence> absenceHashMap = new HashMap();
    private final ArrayList<Attendance> attendace = new ArrayList<>();

    /**
     * Constructs a new AbsenceController.
     * @param connect Connect
     */
    public AbsenceController(Connect connect) {
        this.connect = connect;
        this.update();
    }

    public int getAbsenceHours() {
        int result = 0;
        for(Absence absence : absence) {
            result += absence.getHours();
        }
        return result;
    }



    public void update() {
        Document doc;

        doc = connect.getAbsence();
        if(doc == null) return;
        Elements elements = doc.select(".absence-list tbody tr");
        for(Element element : elements) {
            if(!element.text().contains("pozdní příchod")) {
                Absence tempAbsence = new Absence(element.select(".date").text(), element.select(".count").text());
                absence.add(tempAbsence);
                absenceHashMap.put(element.select(".date").text(), tempAbsence);
            }
        }

        doc = connect.getPassing(5,13);
        if(doc == null) return;
        Elements elements2 = doc.select(".absence-list tbody tr");
        for(Element element : elements2) {
            Elements elements3 = element.select("td p");
            ArrayList<String> arrivals = new ArrayList<>();
            for(Element arrival : elements3) {
                arrivals.add(arrival.text());
            }
            String date = element.select(".date").text();
            if (absenceHashMap.containsKey(date))
                attendace.add(new Attendance(absenceHashMap.get(date), date, arrivals));
            else
                attendace.add(new Attendance(date, arrivals));
        }




    }
    public ArrayList<Attendance> getAttendace() {
        return attendace;
    }
    @NonNull
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for(Absence absence : absence) {
            result.append(absence).append("\n");
        }
        return result.toString();
    }

}
