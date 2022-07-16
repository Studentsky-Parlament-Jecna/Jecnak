package eu.dotteex.jecnak.controllers.types;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

import eu.dotteex.jecnak.controllers.Controller;
import eu.dotteex.jecnak.models.Absence;
import eu.dotteex.jecnak.models.Connect;

public class AbsenceController implements Controller {

    private final Connect connect;
    private final ArrayList<Absence> absence = new ArrayList<>();

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
        Document doc = connect.getAbsence();
        if(doc == null) return;

        Elements elements = doc.select(".absence-list tbody tr");
        for(Element element : elements) {
            if(!element.text().contains("pozdní příchod")) {
                absence.add(new Absence(element.select(".date").text(), element.select(".count").text()));
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for(Absence absence : absence) {
            result.append(absence).append("\n");
        }
        return result.toString();
    }

}
