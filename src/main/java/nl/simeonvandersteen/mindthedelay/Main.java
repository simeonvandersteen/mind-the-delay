package nl.simeonvandersteen.mindthedelay;

import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.PrintStream;

public class Main {

    private static final Logger LOG = LoggerFactory.getLogger(Main.class);

    private void run(Args args) {
        JourneyParser journeyParser = new JourneyParser(args.getJourneyHistory());
        DelayFilter delayFilter = DelayFilter.fromJson(args.getJourneyTimesConfig(), args.getMinimumDelay());
        JourneyReporter journeyReporter = new JourneyReporter();
        MindTheDelay mindTheDelay = new MindTheDelay(journeyParser, delayFilter, journeyReporter);

        mindTheDelay.showDelayedJourneys();
    }

    public static void main(String[] rawArgs) {
        Args args = parseArgs(rawArgs);

        try {
            new Main().run(args);

        } catch (Throwable e) {
            LOG.error(e.getMessage());
            System.exit(1);
        }
    }

    private static Args parseArgs(String[] rawArgs) {
        Args args = new Args();
        CmdLineParser cmdLineParser = new CmdLineParser(args);

        try {
            cmdLineParser.parseArgument(rawArgs);

        } catch (CmdLineException e) {
            System.err.println(e.getMessage() + "\n");
            printUsage(cmdLineParser, System.err);
            System.exit(1);
        }

        if (args.showUsage()) {
            printUsage(cmdLineParser, System.out);
            System.exit(0);
        }

        return args;
    }

    private static void printUsage(CmdLineParser cmdLineParser, PrintStream out) {
        out.println("Usage:\n");
        cmdLineParser.printUsage(out);
    }
}
