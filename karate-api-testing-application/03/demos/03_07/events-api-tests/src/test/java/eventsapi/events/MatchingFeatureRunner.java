package eventsapi.events;

import com.intuit.karate.junit5.Karate;

class MatchingFeatureRunner {
    
    @Karate.Test
    Karate testMatching() {
        return Karate.run("matching").relativeTo(getClass());
    }  


}