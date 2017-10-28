package nl.simeonvandersteen.mindthedelay.domain;

import java.time.Duration;

public class DelayedJourney extends Journey {

    private final Duration delay;

    public DelayedJourney(Journey journey, Duration delay) {
        super(journey.getDate(), journey.getStart(), journey.getEnd(), journey.getOrigin(), journey.getDestination());
        this.delay = delay;
    }

    public Duration getDelay() {
        return delay;
    }
}
