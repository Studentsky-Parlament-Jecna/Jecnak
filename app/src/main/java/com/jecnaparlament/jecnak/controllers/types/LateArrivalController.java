package com.jecnaparlament.jecnak.controllers.types;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

import com.jecnaparlament.jecnak.controllers.Controller;
import com.jecnaparlament.jecnak.models.Connect;
import com.jecnaparlament.jecnak.models.LateArrival;

public class LateArrivalController implements Controller {

    private final Connect connect;
    private final ArrayList<LateArrival> lateArrivals = new ArrayList<>();

    /**
     * Constructs a new LateArrivalController.
     * @param connect Connect
     */
    public LateArrivalController(Connect connect) {
        this.connect = connect;
        this.update();
    }

    public int getCount() {
        int result = 0;
        for(LateArrival la : lateArrivals) {
            result += la.getCount();
        }
        return result;
    }

    public int getUnauthorizedCount() {
        int result = 0;
        for(LateArrival la : lateArrivals) {
            result += la.getUnauthorizedCount();
        }
        return result;
    }

    public void update() {
        Document doc = connect.getAbsence();
        if(doc == null) return;

        Elements elements = doc.select(".absence-list tbody tr");
        for(Element element : elements) {
            if(element.text().contains("pozdní příchod")) {
                lateArrivals.add(new LateArrival(element.select(".date").text(), element.select(".count").text()));
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for(LateArrival lateArrival : lateArrivals) {
            result.append(lateArrival).append("\n");
        }
        return result.toString();
    }


}
