package nl.simeonvandersteen.mindthedelay;

public class MindTheDelay {

    private final JourneyParser journeyParser;
    private final DelayFilter delayFilter;
    private final JourneyReporter journeyReporter;

    public MindTheDelay(JourneyParser journeyParser, DelayFilter delayFilter, JourneyReporter journeyReporter) {
        this.journeyParser = journeyParser;
        this.delayFilter = delayFilter;
        this.journeyReporter = journeyReporter;
    }

    public void showDelayedJourneys() {
        journeyParser.parse().forEach(journey -> {
            delayFilter.getIfDelayed(journey).ifPresent(journeyReporter::report);
        });
    }
}
