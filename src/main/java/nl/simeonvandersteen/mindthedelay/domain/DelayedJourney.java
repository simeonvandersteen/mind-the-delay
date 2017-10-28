package nl.simeonvandersteen.mindthedelay.domain;

import java.time.Duration;
import java.time.LocalTime;

public class DelayedJourney extends Journey {

    private final Duration delay;

    public DelayedJourney(Journey journey, Duration delay) {
        super(journey.getDate(), journey.getStart(), journey.getEnd(), journey.getOrigin(), journey.getDestination());
        this.delay = delay;
    }

    public Duration getDelay() {
        return delay;
    }

    @Override
    public String toString() {
        return String.format("%s\nDelay: %s (expected journey time: %s)",
                super.toString(),
                LocalTime.MIN.plus(delay).toString(),
                LocalTime.MIN.plus(getDuration()).minus(delay).toString()
        );
    }
}
