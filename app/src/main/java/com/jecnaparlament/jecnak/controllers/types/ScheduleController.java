package com.jecnaparlament.jecnak.controllers.types;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

import com.jecnaparlament.jecnak.controllers.Controller;
import com.jecnaparlament.jecnak.models.Connect;

public class ScheduleController implements Controller {

    private final Connect connect;
    private String timetable;

    public ScheduleController(Connect connect) {
        this.connect = connect;
        this.update();
    }

    public String getTimetable() {
        return timetable;
    }

    @Override
    public void update() {
        Document doc = connect.getSchedule();
        if(doc == null) return;

        timetable = doc.select("div.timetable").first().html();
    }
}
