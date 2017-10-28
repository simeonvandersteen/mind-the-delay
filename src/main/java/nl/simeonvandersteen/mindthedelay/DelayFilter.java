package nl.simeonvandersteen.mindthedelay;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import nl.simeonvandersteen.mindthedelay.domain.DelayedJourney;
import nl.simeonvandersteen.mindthedelay.domain.ExpectedJourney;
import nl.simeonvandersteen.mindthedelay.domain.Journey;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.List;
import java.util.Optional;

public class DelayFilter {

    private final List<ExpectedJourney> expectedJourneys;
    private final Duration minimumDelay;


    public DelayFilter(List<ExpectedJourney> expectedJourneys, Duration minimumDelay) {
        this.expectedJourneys = expectedJourneys;
        this.minimumDelay = minimumDelay;
    }

    public Optional<DelayedJourney> getIfDelayed(Journey journey) {

        Optional<ExpectedJourney> expectedJourneyOptional = getExpectedJourney(journey);

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

    private Optional<ExpectedJourney> getExpectedJourney(Journey journey) {
        return expectedJourneys.stream()
                .filter(expectedJourney -> expectedJourney.matches(journey))
                .findFirst();
    }

    public static DelayFilter fromJson(File config, Duration minimumDelay) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            List<ExpectedJourney> expectedJourneys = mapper.readValue(config, new TypeReference<List<ExpectedJourney>>() {
            });
            return new DelayFilter(expectedJourneys, minimumDelay);
        } catch (IOException e) {
            throw new RuntimeException("Failed loading configuration file: " + e.getMessage());
        }
    }
}
