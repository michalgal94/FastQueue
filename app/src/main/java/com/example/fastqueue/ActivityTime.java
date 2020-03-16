package com.example.fastqueue;

import androidx.appcompat.app.AppCompatActivity;

public class ActivityTime {

        private String start_date;
        private String finish_date;
        private String from_hour;
        private String until_hour;
        private String days;

        public ActivityTime(String start_date, String finish_date, String from_hour, String until_hour, String days) {
            this.start_date = start_date;
            this.finish_date = finish_date;
            this.from_hour = from_hour;
            this.until_hour = until_hour;
            this.days = days;
        }

        public ActivityTime() {

        }


    public String getFrom_hour() {
        return from_hour;
    }

    public void setFrom_hour(String from_hour) {
        this.from_hour = from_hour;
    }

    public String getUntil_hour() {
        return until_hour;
    }

    public void setUntil_hour(String until_hour) {
        this.until_hour = until_hour;
    }

    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
    }


    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getFinish_date() {
        return finish_date;
    }

    public void setFinish_date(String finish_date) {
        this.finish_date = finish_date;
    }
}


