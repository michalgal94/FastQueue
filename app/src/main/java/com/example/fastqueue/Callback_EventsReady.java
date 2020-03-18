package com.example.fastqueue;

import com.alamkanak.weekview.WeekViewEvent;

import java.util.List;

public interface Callback_EventsReady {

        void eventsReady(List<WeekViewEvent> events);
        void onError();
}
