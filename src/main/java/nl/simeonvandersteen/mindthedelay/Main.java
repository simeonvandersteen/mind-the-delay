package nl.simeonvandersteen.mindthedelay;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.Arrays;

public class Main {

    private static final Logger LOG = LoggerFactory.getLogger(Main.class);

    private void run(String[] args) {
        if (args.length < 2) {
            throw new RuntimeException("Missing cli arguments");
        }

        JourneyParser journeyParser = new JourneyParser(new File(args[1]));
        DelayFilter delayFilter = DelayFilter.fromJson(new File(args[0]), 15);
        JourneyReporter journeyReporter = new JourneyReporter();
        MindTheDelay mindTheDelay = new MindTheDelay(journeyParser, delayFilter, journeyReporter);

        mindTheDelay.showDelayedJourneys();
    }

    public static void main(String[] args) {
        LOG.info("Starting with cli arguments: " + Arrays.toString(args));
        try {
            new Main().run(args);
        } catch (Throwable e) {
            LOG.error(e.getMessage());
            System.exit(1);
        }
        LOG.info("Done.");
    }
}
