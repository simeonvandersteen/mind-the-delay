package nl.simeonvandersteen.mindthedelay;

import nl.simeonvandersteen.mindthedelay.domain.DelayedJourney;
import nl.simeonvandersteen.mindthedelay.domain.ExpectedJourney;
import nl.simeonvandersteen.mindthedelay.domain.Journey;

import java.time.Duration;
import java.util.Optional;

public class DelayFilter {

    private final ExpectedJourneyProvider expectedJourneyProvider;
    private final Duration minimumDelay;

    public DelayFilter(ExpectedJourneyProvider expectedJourneyProvider, Duration minimumDelay) {
        this.expectedJourneyProvider = expectedJourneyProvider;
        this.minimumDelay = minimumDelay;
    }

    public Optional<DelayedJourney> getIfDelayed(Journey journey) {

        Optional<ExpectedJourney> expectedJourneyOptional = expectedJourneyProvider.getExpectedJourney(journey);

        if (!expectedJourneyOptional.isPresent()) {
            return Optional.empty();
        }

        Duration expectedJourneyDuration = expectedJourneyOptional.get().getDuration();

        Duration delay = journey.getDuration().minus(expectedJourneyDuration);

        if (delay.compareTo(minimumDelay) >= 0) {
            return Optional.of(new DelayedJourney(journey, delay));
        }

        return Optional.empty();
    }
}
