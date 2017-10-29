package nl.simeonvandersteen.mindthedelay;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import nl.simeonvandersteen.mindthedelay.domain.ExpectedJourney;
import nl.simeonvandersteen.mindthedelay.domain.Journey;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ExpectedJourneyProvider {

    private final List<ExpectedJourney> expectedJourneys;

    private ExpectedJourneyProvider(List<ExpectedJourney> expectedJourneys) {
        this.expectedJourneys = expectedJourneys;
    }

    public ExpectedJourneyProvider() {
        expectedJourneys = new ArrayList<>();
    }

    public void analyse(Journey journey) {
        Optional<ExpectedJourney> expectedJourneyOptional = getExpectedJourney(journey);

        if (expectedJourneyOptional.isPresent()) {
            ExpectedJourney expectedJourney = expectedJourneyOptional.get();

            if (journey.getDuration().compareTo(expectedJourney.getDuration()) < 0) {
                expectedJourneys.remove(expectedJourney);
                expectedJourneys.add(toExpectedJourney(journey));
            }
        } else {
            expectedJourneys.add(
                    toExpectedJourney(journey)
            );
        }
    }

    public Optional<ExpectedJourney> getExpectedJourney(Journey journey) {
        return expectedJourneys.stream()
                .filter(expectedJourney -> expectedJourney.matches(journey))
                .findFirst();
    }

    public static ExpectedJourneyProvider fromJson(File configFile) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            List<ExpectedJourney> expectedJourneys = mapper.readValue(configFile, new TypeReference<List<ExpectedJourney>>() {
            });
            return new ExpectedJourneyProvider(expectedJourneys);
        } catch (IOException e) {
            throw new RuntimeException("Failed loading configuration file: " + e.getMessage());
        }
    }

    public String getExpectedJourneyTimes() {
        return expectedJourneys.stream().map(ExpectedJourney::toString).collect(Collectors.joining("\n"));
    }

    private ExpectedJourney toExpectedJourney(Journey journey) {
        return new ExpectedJourney(journey.getOrigin(), journey.getDestination(), (int) journey.getDuration().toMinutes());
    }
}
