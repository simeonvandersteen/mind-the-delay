package nl.simeonvandersteen.mindthedelay.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Duration;

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

    public Duration getDuration() {
        return Duration.ofMinutes(journeyTime);
    }

    public boolean matches(Journey journey) {
        return origin.equals(journey.getOrigin()) && destination.equals(journey.getDestination()) ||
                origin.equals(journey.getDestination()) && destination.equals(journey.getOrigin());
    }
}
