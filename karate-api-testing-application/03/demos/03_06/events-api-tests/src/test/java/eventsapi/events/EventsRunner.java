package eventsapi.events;

import com.intuit.karate.junit5.Karate;

class EventsRunner {
    
    @Karate.Test
    Karate testEvents() {
        return Karate.run("events").relativeTo(getClass());
    }  


}