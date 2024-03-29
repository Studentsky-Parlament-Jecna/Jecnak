package com.jecnaparlament.jecnak.controllers.types;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

import com.jecnaparlament.jecnak.controllers.Controller;
import com.jecnaparlament.jecnak.models.Connect;
import com.jecnaparlament.jecnak.models.News;

public class NewsController implements Controller {

    private final Connect connect;
    private final ArrayList<News> news = new ArrayList<>();

    /**
     * Constructs a new NewsController.
     * @param connect Connect
     */
    public NewsController(Connect connect) {
        this.connect = connect;
        this.update();
    }

    public ArrayList<News> getNews() {
        return news;
    }

    public void update() {
        Document doc = connect.getNews();
        if(doc == null) return;

        Elements elements = doc.select(".event");
        for(Element element : elements) {
            news.add(new News(element.select(".name").text(), element.select(".text").text(), element.select(".footer").text()));
        }
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for(News news : news) {
            result.append(news).append("\n\n");
        }
        return result.toString();
    }

}
