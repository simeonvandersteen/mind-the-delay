package nl.simeonvandersteen.mindthedelay;

import org.kohsuke.args4j.Option;

import java.io.File;
import java.time.Duration;

public class Args {

    @Option(name = "-c", aliases = "--journey-times-config", required = true, usage = "Path to expected journey duration configuration", metaVar = "FILE")
    private File journeyTimesConfig;

    @Option(name = "-j", aliases = "--journey-history", required = true, usage = "Path to CSV journey history", metaVar = "FILE")
    private File journeyHistory;

    @Option(name = "-m", aliases = "--minimum-delay", usage = "Minimum delay in minutes for a journey to be considered as delayed")
    private int minimumDelay = 15;

    @Option(name = "-h", aliases = "--help", help = true, hidden = true)
    private boolean showUsage = false;

    public File getJourneyTimesConfig() {
        return journeyTimesConfig;
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
}
