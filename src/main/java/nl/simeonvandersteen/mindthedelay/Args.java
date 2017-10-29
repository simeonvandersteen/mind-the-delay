package nl.simeonvandersteen.mindthedelay;

import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.Option;

import java.io.File;
import java.time.Duration;
import java.util.Optional;

public class Args {

    @Option(name = "-c", aliases = "--journey-times-config", usage = "File with expected journey durations in JSON format", metaVar = "FILE")
    private File journeyTimesConfig;

    @Option(name = "-m", aliases = "--minimum-delay", usage = "Minimum delay in minutes for a journey to be considered as delayed")
    private int minimumDelay = 15;

    @Option(name = "-s", aliases = "--show-expected-journey-times", usage = "Show fastest journey times from journey history")
    private boolean showExpectedJourneyTimes = false;

    @Option(name = "-h", aliases = "--help", help = true, hidden = true)
    private boolean showUsage = false;

    @Argument(required = true, usage = "File containing journey history in CSV format", metaVar = "FILE")
    private File journeyHistory;

    public Optional<File> getJourneyTimesConfig() {
        return Optional.ofNullable(journeyTimesConfig);
    }

    public File getJourneyHistory() {
        return journeyHistory;
    }

    public Duration getMinimumDelay() {
        return Duration.ofMinutes(minimumDelay);
    }

    public boolean showUsage() {
        return showUsage;
    }

    public boolean showExpectedJourneyTimes() {
        return showExpectedJourneyTimes;
    }
}
