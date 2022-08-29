package com.jecnaparlament.jecnak.models;

import java.util.ArrayList;

import com.jecnaparlament.jecnak.helpers.RegexHelper;

public class Attendance {
        private Absence absence = null;
        ArrayList<String> arrivals;
        private String date;
        private String dayName;
        private String arrival;
        private String exit;
        private int hmOfArrival;

        public Attendance(Absence absence, String date, ArrayList<String> arrivals) {
                setAbsence(absence);
                setArrivals(arrivals);
                setDateAndDay(date);
                setArrival();
                setExit();
                setHmOfArrival();
        }

        public Attendance(String date, ArrayList<String> arrivals) {
                setArrivals(arrivals);
                setDateAndDay(date);
                setArrival();
                setExit();
                setHmOfArrival();
        }

        public int getHmOfArrival() {
                return hmOfArrival;
        }

        public void setHmOfArrival() {
                if (arrival.equals("")) {
                        hmOfArrival = 0;
                } else {
                        String[] time = arrival.split(":");
                        this.hmOfArrival = Integer.parseInt(time[0]+time[1]);
                }

        }
        public void setAbsence(Absence absence) {
                this.absence = absence;
        }

        public void setArrivals(ArrayList<String> arrivals) {
                this.arrivals = arrivals;
        }

        public void setDateAndDay(String date) {
                RegexHelper r = new RegexHelper(date);
                this.date = r.group(1);
                dayName = r.group(2);
        }

        public void setArrival() {
                if (arrivals.size() > 0) {
                        this.arrival = arrivals
                                .get(0)
                                .split(", ")[0]
                                .replace("Příchod ", "");
                } else {
                        this.arrival = "";
                }
        }

        public void setExit() {
                if (arrivals.size() == 1){
                        this.exit = arrivals
                                .get(0)
                                .split(", ")[1]
                                .replace("Odchod ", "");
                }if (arrivals.size() > 1) {
                        this.exit = arrivals
                                .get(arrivals.size()-1)
                                .split(", ")[1]
                                .replace("Odchod ", "");
                }
                if (arrivals.size() == 0) {
                        this.exit = "";
                }

        }

        public Absence getAbsence() {
                return absence;
        }

        public ArrayList<String> getArrivals() {
                return arrivals;
        }

        public String getDate() {
                return date;
        }

        public String getDayName() {
                return dayName;
        }

        public String getArrival() {
                return arrival;
        }

        public String getExit() {
                return exit;
        }
}
