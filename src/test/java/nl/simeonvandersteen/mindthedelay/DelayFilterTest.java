package nl.simeonvandersteen.mindthedelay;

import nl.simeonvandersteen.mindthedelay.domain.DelayedJourney;
import nl.simeonvandersteen.mindthedelay.domain.ExpectedJourney;
import nl.simeonvandersteen.mindthedelay.domain.Journey;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DelayFilterTest {

    private static final int MINIMUM_DELAY = 15;
    private static final int JOURNEY_TIME = 10;

    @Mock
    private ExpectedJourneyProvider expectedJourneyProvider;

    private DelayFilter underTest;

    @Before
    public void setUp() throws Exception {
        ExpectedJourney journey1 = new ExpectedJourney("a", "b", JOURNEY_TIME);

        underTest = new DelayFilter(expectedJourneyProvider, Duration.ofMinutes(MINIMUM_DELAY));

        when(expectedJourneyProvider.getExpectedJourney(any())).thenReturn(Optional.of(journey1));
    }

    @Test
    public void itReturnsDelayedJourneyIfDelayIsAtLeastMinimum() throws Exception {
        Journey journey = new Journey(LocalDate.now(), LocalTime.NOON,
                LocalTime.NOON.plusMinutes(JOURNEY_TIME + MINIMUM_DELAY), "a", "b");

        Optional<DelayedJourney> delayedJourney = underTest.getIfDelayed(journey);

        assertThat(delayedJourney.isPresent(), is(true));
        assertThat(delayedJourney.get().getDelay(), is(Duration.ofMinutes(MINIMUM_DELAY)));
    }

    @Test
    public void itReturnsNoDelayedJourneyIfDelayIsLessThanMinimum() throws Exception {
        Journey journey = new Journey(LocalDate.now(), LocalTime.NOON,
                LocalTime.NOON.plusMinutes(JOURNEY_TIME + MINIMUM_DELAY - 1), "a", "b");

        assertThat(underTest.getIfDelayed(journey).isPresent(), is(false));
    }

    @Test
    public void itReturnsNoJourneyIfNotConfigured() throws Exception {
        when(expectedJourneyProvider.getExpectedJourney(any())).thenReturn(Optional.empty());

        Journey journey = new Journey(LocalDate.now(), LocalTime.NOON,
                LocalTime.NOON.plusMinutes(JOURNEY_TIME + MINIMUM_DELAY), "a", "b");

        assertThat(underTest.getIfDelayed(journey).isPresent(), is(false));
    }
}