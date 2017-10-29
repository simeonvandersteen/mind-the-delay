package nl.simeonvandersteen.mindthedelay;

import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;

import java.io.PrintStream;

public class Main {

    private void run(Args args) {
        JourneyParser journeyParser = new JourneyParser(args.getJourneyHistory());

        ExpectedJourneyProvider expectedJourneyProvider = args.getJourneyTimesConfig().isPresent() ?
                ExpectedJourneyProvider.fromJson(args.getJourneyTimesConfig().get()) : new ExpectedJourneyProvider();

        DelayFilter delayFilter = new DelayFilter(expectedJourneyProvider, args.getMinimumDelay());

        JourneyReporter journeyReporter = new JourneyReporter();

        MindTheDelay mindTheDelay = new MindTheDelay(args, journeyParser, expectedJourneyProvider, delayFilter, journeyReporter);

        mindTheDelay.showDelayedJourneys();
    }

    public static void main(String[] rawArgs) {
        Args args = parseArgs(rawArgs);

        try {
            new Main().run(args);

        } catch (Throwable e) {
            System.err.println(e.getMessage());
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
        out.println("Usage: mind-the-delay [options] FILE\n");
        cmdLineParser.printUsage(out);
    }
}
