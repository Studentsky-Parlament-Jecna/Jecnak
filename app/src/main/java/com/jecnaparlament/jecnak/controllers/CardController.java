package com.jecnaparlament.jecnak.controllers;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Comparator;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jecnaparlament.jecnak.controllers.types.GradeController;
import com.jecnaparlament.jecnak.controllers.types.NewsController;
import com.jecnaparlament.jecnak.controllers.types.RecordController;
import com.jecnaparlament.jecnak.enums.CardType;
import com.jecnaparlament.jecnak.models.Card;
import com.jecnaparlament.jecnak.models.Grade;
import com.jecnaparlament.jecnak.models.News;
import com.jecnaparlament.jecnak.models.Record;
import com.jecnaparlament.jecnak.models.Subject;

public class CardController implements Controller {
    NewsController nc;
    GradeController gc;
    RecordController rc;
    Context context;

    ArrayList<Card> cards = new ArrayList<>();

    public CardController(NewsController nc, GradeController gc, RecordController rc, Context context) {
        this.nc = nc;
        this.gc = gc;
        this.rc = rc;
        this.context = context;
        update();
        saveCards();
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

    public void saveCards(){
        Type cardsType = new TypeToken<ArrayList<Card>>() {}.getType();

        //Create a string with serialized ArrayList<Card> (in JSON format)
        Gson gson = new Gson();
        String json = gson.toJson(cards, cardsType);

        // Save (json) data into shared preferences
        SharedPreferences sharedPreferences = context.getSharedPreferences("school_data", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("data",json);
        editor.apply();
    }

    public ArrayList<Card> getOldCards(){
        Type cardsType = new TypeToken<ArrayList<Card>>() {}.getType();

        //Get (json) data from shared preferences
        SharedPreferences sharedPreferences = context.getSharedPreferences("school_data", MODE_PRIVATE);
        String json = sharedPreferences.getString("data", "");

        //Deserialize (json) data into ArrayList<Card>
        Gson gson = new Gson();
        return gson.fromJson(json, cardsType);
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
