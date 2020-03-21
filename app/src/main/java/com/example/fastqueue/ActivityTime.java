package com.example.fastqueue;

import androidx.appcompat.app.AppCompatActivity;

public class ActivityTime {

        private String from_hour;
        private String until_hour;
        private String days;

        public ActivityTime(String from_hour, String until_hour, String days) {
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


}


