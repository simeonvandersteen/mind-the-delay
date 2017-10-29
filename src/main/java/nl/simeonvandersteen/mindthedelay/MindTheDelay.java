package nl.simeonvandersteen.mindthedelay;

import nl.simeonvandersteen.mindthedelay.domain.Journey;

import java.util.Collection;

public class MindTheDelay {

    private final Args args;
    private final JourneyParser journeyParser;
    private final ExpectedJourneyProvider expectedJourneyProvider;
    private final DelayFilter delayFilter;
    private final JourneyReporter journeyReporter;

    public MindTheDelay(Args args, JourneyParser journeyParser, ExpectedJourneyProvider expectedJourneyProvider, DelayFilter delayFilter, JourneyReporter journeyReporter) {
        this.args = args;
        this.journeyParser = journeyParser;
        this.expectedJourneyProvider = expectedJourneyProvider;
        this.delayFilter = delayFilter;
        this.journeyReporter = journeyReporter;
    }

    public void showDelayedJourneys() {
        Collection<Journey> journeys = journeyParser.parse();

        if (journeys.isEmpty()) {
            System.out.println("No journeys were parsed from CSV file.");
            return;
        }

        journeys.forEach(expectedJourneyProvider::analyse);

        if (args.showExpectedJourneyTimes()) {
            System.out.println(expectedJourneyProvider.getExpectedJourneyTimes());
            System.out.println();
        }

        journeys.forEach(journey -> delayFilter.getIfDelayed(journey).ifPresent(journeyReporter::report));
    }
}
