package eu.dotteex.jecnak.controllers.types;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

import eu.dotteex.jecnak.controllers.Controller;
import eu.dotteex.jecnak.models.Connect;
import eu.dotteex.jecnak.models.Record;

public class RecordController implements Controller {

    private final Connect connect;
    private final ArrayList<Record> records = new ArrayList<>();

    /**
     * Constructs a new RecordController.
     * @param connect Connect
     */
    public RecordController(Connect connect) {
        this.connect = connect;
        this.update();
    }

    public ArrayList<Record> getRecords() {
        return records;
    }

    public int getCount() {
        return records.size();
    }

    public void update() {
        Document doc = connect.getRecords();
        if(doc == null) return;

        Elements elements = connect.getRecords().select(".list .item");
        for(Element element : elements) {
            records.add(new Record(element.select(".label").text()));
        }
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for(Record record : records) {
            result.append(record).append("\n");
        }
        return result.toString();
    }

}
