package eventsapi.events;

import com.intuit.karate.junit5.Karate;

class RunAll {
	@Karate.Test
    Karate testAllFeatures() {
        return Karate.run().relativeTo(getClass());
    }

}

 
