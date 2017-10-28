package nl.simeonvandersteen.mindthedelay;

import com.google.common.collect.ImmutableList;
import nl.simeonvandersteen.mindthedelay.domain.DelayedJourney;
import nl.simeonvandersteen.mindthedelay.domain.ExpectedJourney;
import nl.simeonvandersteen.mindthedelay.domain.Journey;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertNotNull;

public class DelayFilterTest {

    private static final int MINIMUM_DELAY = 15;
    private static final int JOURNEY_TIME = 10;

    private DelayFilter underTest;

    @Before
    public void setUp() throws Exception {
        ExpectedJourney journey1 = new ExpectedJourney("a", "b", JOURNEY_TIME);

        underTest = new DelayFilter(ImmutableList.of(journey1), Duration.ofMinutes(MINIMUM_DELAY));
    }

    @Test
    public void itReturnsDelayedJourneyIfDelayIsMoreThanMinimum() throws Exception {
        Journey journey = new Journey(LocalDate.now(), LocalTime.NOON,
                LocalTime.NOON.plusMinutes(JOURNEY_TIME + MINIMUM_DELAY + 1), "a", "b");

        Optional<DelayedJourney> delayedJourney = underTest.getIfDelayed(journey);

        assertThat(delayedJourney.isPresent(), is(true));
        assertThat(delayedJourney.get().getDelay(), is(Duration.ofMinutes(MINIMUM_DELAY + 1)));
    }

    @Test
    public void itReturnsNoDelayedJourneyIfDelayIsLessThanOrEqualToMinimum() throws Exception {
        Journey journey = new Journey(LocalDate.now(), LocalTime.NOON,
                LocalTime.NOON.plusMinutes(JOURNEY_TIME + MINIMUM_DELAY), "a", "b");

        assertThat(underTest.getIfDelayed(journey).isPresent(), is(false));
    }

    @Test
    public void itReturnsNoJourneyIfNotConfigured() throws Exception {
        Journey journey = new Journey(LocalDate.now(), LocalTime.NOON,
                LocalTime.NOON.plusMinutes(JOURNEY_TIME + MINIMUM_DELAY + 1), "a", "d");

        assertThat(underTest.getIfDelayed(journey).isPresent(), is(false));
    }

    @Test
    public void itParsesExpectedJourneyTimesConfig() throws Exception {
        File configFile = new File(getClass().getClassLoader().getResource("expected-journey-times.json").getFile());

        assertNotNull(DelayFilter.fromJson(configFile, Duration.ofMinutes(MINIMUM_DELAY)));
    }

    @Test(expected = RuntimeException.class)
    public void itThrowsIfIncorrectSyntaxInConfigFile() throws Exception {

        assertNotNull(DelayFilter.fromJson(new File("no-json"), Duration.ofMinutes(MINIMUM_DELAY)));
    }
}