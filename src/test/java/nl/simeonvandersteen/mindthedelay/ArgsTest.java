package nl.simeonvandersteen.mindthedelay;

import org.junit.Before;
import org.junit.Test;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class ArgsTest {

    public static final String JOURNEY_HISTORY_PATH = "/path/to/some-history-file";
    public static final String JOURNEY_TIMES_PATH = "/path/to/some-config-file";
    private Args args;
    private CmdLineParser cmdLineParser;

    @Before
    public void setUp() throws Exception {
        args = new Args();
        cmdLineParser = new CmdLineParser(args);
    }

    @Test
    public void itProvidesDefaultArgs() throws Exception {
        String[] rawArgs = new String[]{"-c", JOURNEY_TIMES_PATH, "-j", JOURNEY_HISTORY_PATH};

        cmdLineParser.parseArgument(rawArgs);

        assertThat(args.getJourneyTimesConfig().getAbsolutePath(), is(JOURNEY_TIMES_PATH));
        assertThat(args.getJourneyHistory().getAbsolutePath(), is(JOURNEY_HISTORY_PATH));
        assertThat(args.getMinimumDelay().getSeconds(), is(15 * 60L));
    }

    @Test
    public void itOverridesDefaultArgs() throws Exception {
        String[] rawArgs = new String[]{"-c", JOURNEY_TIMES_PATH, "-j", JOURNEY_HISTORY_PATH, "-m", "10"};

        cmdLineParser.parseArgument(rawArgs);

        assertThat(args.getMinimumDelay().getSeconds(), is(10 * 60L));
    }

    @Test(expected = CmdLineException.class)
    public void itThrowsIfMissingRequiredArgs() throws Exception {
        String[] rawArgs = new String[]{};

        cmdLineParser.parseArgument(rawArgs);
    }

    @Test
    public void itShowsUsage() throws Exception {
        String[] rawArgs = new String[]{"--help"};

        cmdLineParser.parseArgument(rawArgs);

        assertThat(args.showUsage(), is(true));
    }
}