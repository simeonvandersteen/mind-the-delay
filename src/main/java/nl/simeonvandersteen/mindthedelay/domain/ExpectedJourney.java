package nl.simeonvandersteen.mindthedelay.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Duration;

//TODO should extend journey?
public class ExpectedJourney {

    private String origin;
    private String destination;
    @JsonProperty("journey_time")
    private int journeyTime;

    private ExpectedJourney() {
    }

    public ExpectedJourney(String origin, String destination, int journeyTime) {
        this.origin = origin;
        this.destination = destination;
        this.journeyTime = journeyTime;
    }

    public String getOrigin() {
        return origin;
    }

    public String getDestination() {
        return destination;
    }

    public Duration getJourneyTime() {
        return Duration.ofMinutes(journeyTime);
    }
}
