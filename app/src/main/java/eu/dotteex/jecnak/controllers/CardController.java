package eu.dotteex.jecnak.controllers;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Comparator;

import eu.dotteex.jecnak.controllers.types.GradeController;
import eu.dotteex.jecnak.controllers.types.NewsController;
import eu.dotteex.jecnak.controllers.types.RecordController;
import eu.dotteex.jecnak.enums.CardType;
import eu.dotteex.jecnak.models.Card;
import eu.dotteex.jecnak.models.Grade;
import eu.dotteex.jecnak.models.News;
import eu.dotteex.jecnak.models.Record;
import eu.dotteex.jecnak.models.Subject;

public class CardController implements Controller {
    NewsController nc;
    GradeController gc;
    RecordController rc;

    ArrayList<Card> cards = new ArrayList<>();

    public CardController(NewsController nc, GradeController gc, RecordController rc) {
        this.nc = nc;
        this.gc = gc;
        this.rc = rc;
        update();
    }



    public ArrayList<Card> getCards() {
        return cards;
    }

    public void update() {

        for(Subject subject : gc.getSubjects()) {
            for(Grade grade : subject.getGradesToArray()) {
                String gradeValue = (!grade.isUncountable()) ? String.valueOf(grade.getValue()) : "N";
                String gradeFooter = (grade.getTitle().length() > 1) ? grade.getDate()+", "+grade.getTitle() : grade.getDate();
                cards.add(new Card(CardType.GRADE, subject.getName(), gradeValue, gradeFooter, grade.getYmd()));
            }
        }

        for(Record record : rc.getRecords()) {
            cards.add(new Card(CardType.RECORD, "Sdělení rodičům", record.getContent(), record.getDate(), record.getYmd()));
        }

        for(News novelty : nc.getNews()) {
            cards.add(new Card(CardType.NEWS, novelty.getTitle(), novelty.getContent(), novelty.getDate()+", "+novelty.getAuthor(), novelty.getYmd()));
        }

        cards.sort(new Comparator<Card>() {
            public int compare(Card card1, Card card2) {
                Integer ymd1 = new Integer(card1.getYmd());
                Integer ymd2 = new Integer(card2.getYmd());
                return ymd2.compareTo(ymd1);
            }
        });

    }

    public void serialize() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("cards.ser"))) {
            out.writeObject(cards);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Ready for notifications.
     */
    public void findDiff() {

        ArrayList<Card> list1 = new ArrayList<>();
        ArrayList<Card> list2 = new ArrayList<>();

        try (ObjectInputStream out = new ObjectInputStream(new FileInputStream("cards.ser"))) {
            list1 = (ArrayList<Card>) out.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        for(Card card : cards) {
            list2.add(card);
        }

        // for test purposes
        list2.add(new Card(CardType.RECORD, "test", "test", "test", 20200520));
        list2.add(new Card(CardType.RECORD, "test", "test", "test", 20200520));

        list2.removeAll(list1);

        for(Card card : list2) {
            System.out.println(card);
        }

    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for(Card card : cards) {
            result.append(card);
        }
        return result.toString();
    }


}
