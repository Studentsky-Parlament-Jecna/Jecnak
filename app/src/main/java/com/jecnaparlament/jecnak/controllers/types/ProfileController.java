package com.jecnaparlament.jecnak.controllers.types;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

import com.jecnaparlament.jecnak.controllers.Controller;
import com.jecnaparlament.jecnak.models.Connect;

public class ProfileController implements Controller {

    private final Connect connect;
    private final ArrayList<String> data = new ArrayList<>();
    //private final Profile profile;

    public ProfileController(Connect connect) {
        this.connect = connect;
        this.update();
    }

    public ArrayList<String> getData() {
        return data;
    }

    @Override
    public void update() {
        Document doc = connect.getProfile();
        if(doc == null) return;

        Elements elements = doc.select("tbody tr .value");
        for(Element element : elements) {
            data.add(element.text());
        }

        Element img = doc.select(".image img").first();
        data.add(img.attr("src"));
    }
}
