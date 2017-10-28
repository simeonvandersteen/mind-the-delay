package nl.simeonvandersteen.mindthedelay;

import nl.simeonvandersteen.mindthedelay.domain.Journey;

import java.util.Collection;

public class MindTheDelay {

    private final JourneyParser journeyParser;
    private final ExpectedJourneyProvider expectedJourneyProvider;
    private final DelayFilter delayFilter;
    private final JourneyReporter journeyReporter;

    public MindTheDelay(JourneyParser journeyParser, ExpectedJourneyProvider expectedJourneyProvider, DelayFilter delayFilter, JourneyReporter journeyReporter) {
        this.journeyParser = journeyParser;
        this.expectedJourneyProvider = expectedJourneyProvider;
        this.delayFilter = delayFilter;
        this.journeyReporter = journeyReporter;
    }

    public void showDelayedJourneys() {
        Collection<Journey> journeys = journeyParser.parse();

        journeys.forEach(expectedJourneyProvider::analyse);

        System.out.println(expectedJourneyProvider.toString());
        System.out.println();

        journeys.forEach(journey -> delayFilter.getIfDelayed(journey).ifPresent(journeyReporter::report));
    }
}
